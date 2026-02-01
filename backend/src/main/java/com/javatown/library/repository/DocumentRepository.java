package com.javatown.library.repository;

import com.javatown.library.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByTitreContainingIgnoreCase(String titre);

    List<Document> findByAnnee(int annee);

    List<Document> findByCategorieIgnoreCase(String categorie);

    @Query("SELECT d FROM Document d WHERE " +
           "(:titre IS NULL OR LOWER(d.titre) LIKE LOWER(CONCAT('%', :titre, '%'))) AND " +
           "(:annee IS NULL OR d.annee = :annee) AND " +
           "(:categorie IS NULL OR LOWER(d.categorie) = LOWER(:categorie))")
    List<Document> search(@Param("titre") String titre, 
                          @Param("annee") Integer annee, 
                          @Param("categorie") String categorie);
}
