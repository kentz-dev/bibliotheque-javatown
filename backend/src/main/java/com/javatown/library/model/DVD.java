package com.javatown.library.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("DVD")
@Getter
@Setter
@NoArgsConstructor
public class DVD extends Document {

    private String realisateur;

    public DVD(String titre, int annee, String categorie, int exemplairesDisponibles, String realisateur) {
        super(titre, annee, categorie, exemplairesDisponibles);
        this.realisateur = realisateur;
    }
}
