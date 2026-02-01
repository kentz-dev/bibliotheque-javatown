package com.javatown.library.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("PREPOSE")
@Getter
@Setter
@NoArgsConstructor
public class Prepose extends Utilisateur {

    private String matricule;

    public Prepose(String nom, String prenom, String email, String motDePasse, String matricule) {
        super(nom, prenom, email, motDePasse);
        this.matricule = matricule;
    }
}
