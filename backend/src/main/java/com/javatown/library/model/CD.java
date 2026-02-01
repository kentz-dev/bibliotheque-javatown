package com.javatown.library.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("CD")
@Getter
@Setter
@NoArgsConstructor
public class CD extends Document {

    private String artiste;

    public CD(String titre, int annee, String categorie, int exemplairesDisponibles, String artiste) {
        super(titre, annee, categorie, exemplairesDisponibles);
        this.artiste = artiste;
    }
}
