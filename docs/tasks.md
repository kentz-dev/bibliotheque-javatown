# Liste des tâches techniques — JavaTown Library

## Phase 1 : Initialisation du Socle Technique
1. - [ ] Initialiser le projet Spring Boot avec les dépendances (Web, Data JPA, H2, Lombok, DevTools) [Phase 1]
2. - [ ] Initialiser le projet Frontend React avec React Router et configurer la structure de base [Phase 1]
3. - [ ] Configurer la base de données H2 (mode mémoire) et le logging dans `application.properties` [Phase 1]
4. - [ ] Mettre en place l'arborescence des dossiers par couches (controller, service, repository, model, dto) dans le dossier backend [Phase 1]

## Phase 2 : Gestion du Catalogue de Documents (Req 3, 4)
5. - [ ] Créer l'entité abstraite `Document` avec la stratégie d'héritage `SINGLE_TABLE` et le champ `exemplairesDisponibles` [Phase 2, Req 3]
6. - [ ] Implémenter les sous-classes `Livre`, `CD` et `DVD` avec leurs attributs spécifiques [Phase 2, Req 3]
7. - [ ] Créer le `DocumentRepository` et ajouter les méthodes de recherche par titre (partiel), auteur, année et catégorie [Phase 2, Req 4]
8. - [ ] Développer `DocumentService` pour gérer la persistance et la logique de recherche multicritère combinée [Phase 2, Req 3, 4]
9. - [ ] Créer `DocumentController` et définir les DTOs nécessaires pour exposer les données du catalogue [Phase 2, Req 3, 4]

## Phase 3 : Gestion des Clients (Req 1, 2)
10. - [ ] Créer l'entité `Client` et son repository associé `ClientRepository` [Phase 3, Req 1]
11. - [ ] Implémenter `ClientService` incluant la logique d'inscription et de récupération de la liste des usagers [Phase 3, Req 1, 2]
12. - [ ] Développer `ClientController` avec les endpoints pour l'inscription et la consultation globale [Phase 3, Req 1, 2]
13. - [ ] Ajouter dans l'objet de retour Client la liste de ses emprunts actifs pour répondre à la vue préposé [Phase 3, Req 2]

## Phase 4 : Moteur de Transactions (Emprunts & Retours) (Req 5, 6, 7)
14. - [ ] Créer l'entité `Emprunt` avec les relations ManyToOne vers `Client` et `Document` [Phase 4, Req 5]
15. - [ ] Implémenter la logique d'emprunt dans `EmpruntService` : validation du stock, calcul de la date de retour selon le type de document et décrémentation de la disponibilité [Phase 4, Req 5]
16. - [ ] Implémenter la logique de retour dans `EmpruntService` : mise à jour du statut de l'emprunt et incrémentation de la disponibilité du document [Phase 4, Req 6]
17. - [ ] Créer `EmpruntController` pour exposer les endpoints de création d'emprunt et de traitement des retours [Phase 4, Req 5, 6]
18. - [ ] Développer un endpoint permettant à un client de consulter ses propres emprunts avec leurs dates de retour [Phase 4, Req 7]

## Phase 5 : Interface Utilisateur React
19. - [ ] Configurer le client Axios et créer les services d'appel API pour chaque domaine (clients, documents, emprunts) [Phase 5]
20. - [ ] Créer le composant de formulaire d'inscription client avec validation des champs [Phase 5, Req 1]
21. - [ ] Développer la galerie de recherche de documents avec filtres interactifs (titre, auteur, catégorie, etc.) [Phase 5, Req 4]
22. - [ ] Créer la vue "Mes Emprunts" pour l'interface client affichant les documents en cours et les délais [Phase 5, Req 7]
23. - [ ] Implémenter le tableau de bord pour le préposé listant tous les clients et le détail de leurs emprunts actifs [Phase 5, Req 2]

## Phase 6 : Finalisation et Tests d'Intégration
24. - [ ] Écrire des tests d'intégration (JUnit/MockMvc) couvrant le cycle complet : inscription -> recherche -> emprunt -> retour [Phase 6]
25. - [ ] Réaliser une validation finale de tous les critères d'acceptation du document des exigences [Phase 6]
