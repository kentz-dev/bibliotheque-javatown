package com.javatown.library.service;

import com.javatown.library.dto.EmpruntDTO;
import com.javatown.library.model.*;
import com.javatown.library.repository.ClientRepository;
import com.javatown.library.repository.DocumentRepository;
import com.javatown.library.repository.EmpruntRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpruntService {

    private final EmpruntRepository empruntRepository;
    private final ClientRepository clientRepository;
    private final DocumentRepository documentRepository;

    @Transactional
    public EmpruntDTO emprunter(Long clientId, Long documentId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document non trouvé"));

        if (document.getExemplairesDisponibles() <= 0) {
            throw new RuntimeException("Aucun exemplaire disponible pour ce document");
        }

        // Calcul de la date de retour selon le type
        int delaiJours = 7; // Par défaut CD/DVD
        if (document instanceof Livre) {
            delaiJours = 21;
        }

        document.setExemplairesDisponibles(document.getExemplairesDisponibles() - 1);
        documentRepository.save(document);

        Emprunt emprunt = new Emprunt(client, document, LocalDate.now(), LocalDate.now().plusDays(delaiJours));
        Emprunt savedEmprunt = empruntRepository.save(emprunt);

        return mapToDTO(savedEmprunt);
    }

    @Transactional
    public List<EmpruntDTO> emprunterPlusieurs(Long clientId, List<Long> documentIds) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        return documentIds.stream().map(documentId -> {
            Document document = documentRepository.findById(documentId)
                    .orElseThrow(() -> new RuntimeException("Document non trouvé : " + documentId));

            if (document.getExemplairesDisponibles() <= 0) {
                throw new RuntimeException("Aucun exemplaire disponible pour le document : " + document.getTitre());
            }

            int delaiJours = 7;
            if (document instanceof Livre) {
                delaiJours = 21;
            }

            document.setExemplairesDisponibles(document.getExemplairesDisponibles() - 1);
            documentRepository.save(document);

            Emprunt emprunt = new Emprunt(client, document, LocalDate.now(), LocalDate.now().plusDays(delaiJours));
            return mapToDTO(empruntRepository.save(emprunt));
        }).collect(Collectors.toList());
    }

    @Transactional
    public EmpruntDTO retourner(Long empruntId) {
        Emprunt emprunt = empruntRepository.findById(empruntId)
                .orElseThrow(() -> new RuntimeException("Emprunt non trouvé"));

        if (emprunt.getDateRetourReelle() != null) {
            throw new RuntimeException("Ce document a déjà été retourné");
        }

        emprunt.setDateRetourReelle(LocalDate.now());
        
        Document document = emprunt.getDocument();
        document.setExemplairesDisponibles(document.getExemplairesDisponibles() + 1);
        documentRepository.save(document);

        Emprunt savedEmprunt = empruntRepository.save(emprunt);
        return mapToDTO(savedEmprunt);
    }

    public List<EmpruntDTO> getEmpruntsByClient(Long clientId) {
        return empruntRepository.findByClientId(clientId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<EmpruntDTO> getEmpruntsActifsByClient(Long clientId) {
        return empruntRepository.findByClientIdAndDateRetourReelleIsNull(clientId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private EmpruntDTO mapToDTO(Emprunt emprunt) {
        String type = "DOCUMENT";
        if (emprunt.getDocument() instanceof Livre) type = "LIVRE";
        else if (emprunt.getDocument() instanceof CD) type = "CD";
        else if (emprunt.getDocument() instanceof DVD) type = "DVD";

        return EmpruntDTO.builder()
                .id(emprunt.getId())
                .clientId(emprunt.getClient().getId())
                .clientNom(emprunt.getClient().getNom())
                .clientPrenom(emprunt.getClient().getPrenom())
                .documentId(emprunt.getDocument().getId())
                .documentTitre(emprunt.getDocument().getTitre())
                .documentType(type)
                .dateEmprunt(emprunt.getDateEmprunt())
                .dateRetourPrevue(emprunt.getDateRetourPrevue())
                .dateRetourReelle(emprunt.getDateRetourReelle())
                .build();
    }
}
