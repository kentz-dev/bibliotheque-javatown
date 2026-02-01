package com.javatown.library.service;

import com.javatown.library.model.Document;
import com.javatown.library.model.Livre;
import com.javatown.library.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Transactional(readOnly = true)
    public List<Document> searchDocuments(String titre, String auteur, Integer annee, String categorie) {
        List<Document> results = documentRepository.search(titre, annee, categorie);

        if (auteur != null && !auteur.isEmpty()) {
            return results.stream()
                    .filter(d -> d instanceof Livre && ((Livre) d).getAuteur().toLowerCase().contains(auteur.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return results;
    }

    @Transactional
    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    @Transactional(readOnly = true)
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }
}
