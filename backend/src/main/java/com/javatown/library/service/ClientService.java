package com.javatown.library.service;

import com.javatown.library.dto.ClientDTO;
import com.javatown.library.model.Client;
import com.javatown.library.repository.ClientRepository;
import com.javatown.library.repository.EmpruntRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final EmpruntRepository empruntRepository;

    @Transactional
    public ClientDTO inscrireClient(ClientDTO clientDTO) {
        String normalizedEmail = clientDTO.getEmail() != null ? clientDTO.getEmail().toLowerCase() : null;
        if (clientRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new RuntimeException("Un client avec cet email existe déjà.");
        }
        Client client = new Client(clientDTO.getNom(), clientDTO.getPrenom(), normalizedEmail, clientDTO.getMotDePasse());
        Client savedClient = clientRepository.save(client);
        return mapToDTO(savedClient);
    }

    @Transactional(readOnly = true)
    public List<ClientDTO> recupererTousLesClients() {
        return clientRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ClientDTO mapToDTO(Client client) {
        List<String> emprunts = empruntRepository.findByClientIdAndDateRetourReelleIsNull(client.getId())
                .stream()
                .map(e -> e.getDocument().getTitre() + " (Retour prévu : " + e.getDateRetourPrevue() + ")")
                .collect(Collectors.toList());

        return ClientDTO.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .email(client.getEmail())
                .empruntsActifs(emprunts)
                .build();
    }
}
