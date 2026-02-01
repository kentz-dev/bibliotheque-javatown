package com.javatown.library.service;

import com.javatown.library.dto.LoginRequest;
import com.javatown.library.dto.UtilisateurDTO;
import com.javatown.library.model.Client;
import com.javatown.library.model.Prepose;
import com.javatown.library.model.Utilisateur;
import com.javatown.library.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurDTO login(LoginRequest request) {
        String normalizedEmail = request.getEmail() != null ? request.getEmail().toLowerCase() : null;
        Utilisateur utilisateur = utilisateurRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));

        if (!utilisateur.getMotDePasse().equals(request.getMotDePasse())) {
            throw new RuntimeException("Mot de passe incorrect.");
        }

        String type = "CLIENT";
        if (utilisateur instanceof Prepose) {
            type = "PREPOSE";
        }

        return UtilisateurDTO.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .type(type)
                .build();
    }
}
