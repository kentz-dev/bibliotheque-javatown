package com.javatown.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentDTO {
    private Long id;
    private String titre;
    private int annee;
    private String categorie;
    private int exemplairesDisponibles;
    private String type;
    
    // Livre specific
    private String auteur;
    private String isbn;
    
    // CD specific
    private String artiste;
    
    // DVD specific
    private String realisateur;
}
