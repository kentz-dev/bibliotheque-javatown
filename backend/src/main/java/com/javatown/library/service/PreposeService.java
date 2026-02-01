package com.javatown.library.service;

import com.javatown.library.dto.PreposeDTO;
import com.javatown.library.model.Prepose;
import com.javatown.library.repository.PreposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreposeService {

    private final PreposeRepository preposeRepository;

    @Transactional
    public PreposeDTO inscrirePrepose(PreposeDTO dto) {
        String normalizedEmail = dto.getEmail() != null ? dto.getEmail().toLowerCase() : null;
        if (preposeRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà.");
        }
        if (preposeRepository.findByMatricule(dto.getMatricule()).isPresent()) {
            throw new RuntimeException("Un préposé avec ce matricule existe déjà.");
        }
        
        Prepose prepose = new Prepose(dto.getNom(), dto.getPrenom(), normalizedEmail, dto.getMotDePasse(), dto.getMatricule());
        Prepose saved = preposeRepository.save(prepose);
        return mapToDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<PreposeDTO> recupererTousLesPreposes() {
        return preposeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private PreposeDTO mapToDTO(Prepose p) {
        return PreposeDTO.builder()
                .id(p.getId())
                .nom(p.getNom())
                .prenom(p.getPrenom())
                .email(p.getEmail())
                .matricule(p.getMatricule())
                .build();
    }
}
