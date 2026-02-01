package com.javatown.library.repository;

import com.javatown.library.model.Emprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
    List<Emprunt> findByClientId(Long clientId);
    List<Emprunt> findByClientIdAndDateRetourReelleIsNull(Long clientId);
}
