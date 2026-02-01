package com.javatown.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatown.library.dto.ClientDTO;
import com.javatown.library.dto.DocumentDTO;
import com.javatown.library.dto.EmpruntDTO;
import com.javatown.library.dto.LoginRequest;
import com.javatown.library.dto.UtilisateurDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LibraryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFullCycle() throws Exception {
        // 1. Inscription Client
        ClientDTO clientDTO = ClientDTO.builder()
                .nom("Dupont")
                .prenom("Jean")
                .email("jean.dupont@test.com")
                .motDePasse("password123")
                .build();

        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isOk());

        // 2. Login
        LoginRequest loginRequest = new LoginRequest("jean.dupont@test.com", "password123");
        MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        UtilisateurDTO loggedUser = objectMapper.readValue(loginResult.getResponse().getContentAsString(), UtilisateurDTO.class);
        assertThat(loggedUser.getEmail()).isEqualTo("jean.dupont@test.com");
        Long clientId = loggedUser.getId();

        // 3. Création de documents par le préposé (admin@javatown.com créé par DataInitializer)
        DocumentDTO docDTO = DocumentDTO.builder()
                .titre("Java Integration Testing")
                .annee(2024)
                .categorie("Informatique")
                .exemplairesDisponibles(5)
                .type("LIVRE")
                .auteur("JUnit Expert")
                .isbn("123-456-789")
                .build();

        MvcResult docResult = mockMvc.perform(post("/api/documents")
                .header("X-Prepose-Email", "admin@javatown.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(docDTO)))
                .andExpect(status().isOk())
                .andReturn();

        DocumentDTO savedDoc = objectMapper.readValue(docResult.getResponse().getContentAsString(), DocumentDTO.class);
        Long docId = savedDoc.getId();

        // 4. Recherche de documents
        mockMvc.perform(get("/api/documents/search")
                .param("titre", "Java")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // 5. Emprunt
        MvcResult empruntResult = mockMvc.perform(post("/api/emprunts")
                .param("clientId", clientId.toString())
                .param("documentId", docId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        EmpruntDTO emprunt = objectMapper.readValue(empruntResult.getResponse().getContentAsString(), EmpruntDTO.class);
        assertThat(emprunt.getDocumentTitre()).isEqualTo("Java Integration Testing");
        Long empruntId = emprunt.getId();

        // Vérifier stock diminué
        MvcResult searchAfterEmprunt = mockMvc.perform(get("/api/documents/search")
                .param("titre", "Java")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        // On récupère la liste et on vérifie le premier (et seul) résultat
        String content = searchAfterEmprunt.getResponse().getContentAsString();
        DocumentDTO[] docs = objectMapper.readValue(content, DocumentDTO[].class);
        assertThat(docs[0].getExemplairesDisponibles()).isEqualTo(4);

        // 6. Retour
        mockMvc.perform(post("/api/emprunts/" + empruntId + "/retour")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Vérifier stock ré-augmenté
        MvcResult searchAfterRetour = mockMvc.perform(get("/api/documents/search")
                .param("titre", "Java")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        docs = objectMapper.readValue(searchAfterRetour.getResponse().getContentAsString(), DocumentDTO[].class);
        assertThat(docs[0].getExemplairesDisponibles()).isEqualTo(5);
    }
}
