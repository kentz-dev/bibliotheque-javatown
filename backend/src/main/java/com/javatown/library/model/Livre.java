package com.javatown.library.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("LIVRE")
@Getter
@Setter
@NoArgsConstructor
public class Livre extends Document {

    private String auteur;
    private String isbn;

    public Livre(String titre, int annee, String categorie, int exemplairesDisponibles, String auteur, String isbn) {
        super(titre, annee, categorie, exemplairesDisponibles);
        this.auteur = auteur;
        this.isbn = isbn;
    }
}
