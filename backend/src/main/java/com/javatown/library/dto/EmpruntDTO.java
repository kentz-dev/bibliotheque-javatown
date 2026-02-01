package com.javatown.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpruntDTO {
    private Long id;
    private Long clientId;
    private String clientNom;
    private String clientPrenom;
    private Long documentId;
    private String documentTitre;
    private String documentType;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourReelle;
}
