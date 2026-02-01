package com.javatown.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_document")
@Getter
@Setter
@NoArgsConstructor
public abstract class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private int annee;
    private String categorie;
    private int exemplairesDisponibles;

    public Document(String titre, int annee, String categorie, int exemplairesDisponibles) {
        this.titre = titre;
        this.annee = annee;
        this.categorie = categorie;
        this.exemplairesDisponibles = exemplairesDisponibles;
    }
}
