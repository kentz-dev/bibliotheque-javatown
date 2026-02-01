package com.javatown.library.repository;

import com.javatown.library.model.Prepose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreposeRepository extends JpaRepository<Prepose, Long> {
    Optional<Prepose> findByEmail(String email);
    Optional<Prepose> findByMatricule(String matricule);
}
