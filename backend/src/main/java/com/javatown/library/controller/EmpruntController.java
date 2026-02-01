package com.javatown.library.controller;

import com.javatown.library.dto.EmpruntDTO;
import com.javatown.library.service.EmpruntService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprunts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmpruntController {

    private final EmpruntService empruntService;

    @PostMapping
    public EmpruntDTO emprunter(@RequestParam Long clientId, @RequestParam Long documentId) {
        return empruntService.emprunter(clientId, documentId);
    }

    @PostMapping("/batch")
    public List<EmpruntDTO> emprunterPlusieurs(@RequestParam Long clientId, @RequestBody List<Long> documentIds) {
        return empruntService.emprunterPlusieurs(clientId, documentIds);
    }

    @PostMapping("/{id}/retour")
    public EmpruntDTO retourner(@PathVariable Long id) {
        return empruntService.retourner(id);
    }

    @GetMapping("/client/{clientId}")
    public List<EmpruntDTO> getEmpruntsByClient(@PathVariable Long clientId) {
        return empruntService.getEmpruntsByClient(clientId);
    }

    @GetMapping("/client/{clientId}/actifs")
    public List<EmpruntDTO> getEmpruntsActifsByClient(@PathVariable Long clientId) {
        return empruntService.getEmpruntsActifsByClient(clientId);
    }
}
