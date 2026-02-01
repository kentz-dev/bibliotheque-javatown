package com.javatown.library.controller;

import com.javatown.library.dto.DocumentDTO;
import com.javatown.library.model.*;
import com.javatown.library.repository.PreposeRepository;
import com.javatown.library.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DocumentController {

    private final DocumentService documentService;
    private final PreposeRepository preposeRepository;

    @GetMapping
    public List<DocumentDTO> getAllDocuments() {
        return documentService.getAllDocuments().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<DocumentDTO> searchDocuments(
            @RequestParam(required = false) String titre,
            @RequestParam(required = false) String auteur,
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) String categorie) {
        return documentService.searchDocuments(titre, auteur, annee, categorie).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<DocumentDTO> createDocument(
            @RequestBody DocumentDTO dto,
            @RequestHeader(value = "X-Prepose-Email", required = false) String email) {
        
        // Vérification symbolique : seul un préposé peut créer un document
        if (email == null || preposeRepository.findByEmail(email).isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Document doc;
        if ("LIVRE".equalsIgnoreCase(dto.getType())) {
            doc = new Livre(dto.getTitre(), dto.getAnnee(), dto.getCategorie(), dto.getExemplairesDisponibles(), dto.getAuteur(), dto.getIsbn());
        } else if ("CD".equalsIgnoreCase(dto.getType())) {
            doc = new CD(dto.getTitre(), dto.getAnnee(), dto.getCategorie(), dto.getExemplairesDisponibles(), dto.getArtiste());
        } else if ("DVD".equalsIgnoreCase(dto.getType())) {
            doc = new DVD(dto.getTitre(), dto.getAnnee(), dto.getCategorie(), dto.getExemplairesDisponibles(), dto.getRealisateur());
        } else {
            return ResponseEntity.badRequest().build();
        }
        
        Document savedDoc = documentService.saveDocument(doc);
        return ResponseEntity.ok(mapToDTO(savedDoc));
    }

    private DocumentDTO mapToDTO(Document doc) {
        DocumentDTO dto = DocumentDTO.builder()
                .id(doc.getId())
                .titre(doc.getTitre())
                .annee(doc.getAnnee())
                .categorie(doc.getCategorie())
                .exemplairesDisponibles(doc.getExemplairesDisponibles())
                .build();

        if (doc instanceof Livre) {
            dto.setType("LIVRE");
            dto.setAuteur(((Livre) doc).getAuteur());
            dto.setIsbn(((Livre) doc).getIsbn());
        } else if (doc instanceof CD) {
            dto.setType("CD");
            dto.setArtiste(((CD) doc).getArtiste());
        } else if (doc instanceof DVD) {
            dto.setType("DVD");
            dto.setRealisateur(((DVD) doc).getRealisateur());
        }

        return dto;
    }
}
