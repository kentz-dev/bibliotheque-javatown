# Liste des tâches techniques — JavaTown Library

## Phase 1 : Initialisation du Socle Technique
1. - [x] Initialiser le projet Spring Boot avec les dépendances (Web, Data JPA, H2, Lombok, DevTools), le Maven Wrapper et configuration Java 23 + Lombok compatible [Phase 1]
2. - [x] Initialiser le projet Frontend React avec React Router et configurer la structure de base [Phase 1]
3. - [x] Configurer la base de données H2 (mode mémoire) et le logging dans `application.properties` [Phase 1]
4. - [x] Mettre en place l'arborescence des dossiers par couches (controller, service, repository, model, dto) dans le dossier backend [Phase 1]

## Phase 2 : Gestion du Catalogue de Documents (Req 3, 4)
5. - [x] Créer l'entité abstraite `Document` avec la stratégie d'héritage `SINGLE_TABLE` et le champ `exemplairesDisponibles` [Phase 2, Req 3]
6. - [x] Implémenter les sous-classes `Livre`, `CD` et `DVD` avec leurs attributs spécifiques [Phase 2, Req 3]
7. - [x] Créer le `DocumentRepository` et ajouter les méthodes de recherche par titre (partiel), auteur, année et catégorie [Phase 2, Req 4]
8. - [x] Développer `DocumentService` pour gérer la persistance et la logique de recherche multicritère combinée [Phase 2, Req 3, 4]
9. - [x] Créer `DocumentController` et définir les DTOs nécessaires pour exposer les données du catalogue [Phase 2, Req 3, 4]

## Phase 3 : Gestion des Clients (Req 1, 2)
10. - [x] Créer l'entité `Client` et son repository associé `ClientRepository` [Phase 3, Req 1]
11. - [x] Implémenter `ClientService` incluant la logique d'inscription et de récupération de la liste des usagers [Phase 3, Req 1, 2]
12. - [x] Développer `ClientController` avec les endpoints pour l'inscription et la consultation globale [Phase 3, Req 1, 2]
13. - [x] Ajouter dans l'objet de retour Client la liste de ses emprunts actifs pour répondre à la vue préposé [Phase 3, Req 2]

## Phase 4 : Moteur de Transactions (Emprunts & Retours) (Req 5, 6, 7)
14. - [x] Créer l'entité `Emprunt` avec les relations ManyToOne vers `Client` et `Document` [Phase 4, Req 5]
15. - [x] Implémenter la logique d'emprunt dans `EmpruntService` : validation du stock, calcul de la date de retour selon le type de document et décrémentation de la disponibilité [Phase 4, Req 5]
16. - [x] Implémenter la logique de retour dans `EmpruntService` : mise à jour du statut de l'emprunt et incrémentation de la disponibilité du document [Phase 4, Req 6]
17. - [x] Créer `EmpruntController` pour exposer les endpoints de création d'emprunt et de traitement des retours [Phase 4, Req 5, 6]
18. - [x] Développer un endpoint permettant à un client de consulter ses propres emprunts avec leurs dates de retour [Phase 4, Req 7]

## Phase 5 : Interface Utilisateur React
19. - [x] Configurer le client Axios et créer les services d'appel API pour chaque domaine (clients, documents, emprunts) [Phase 5]
20. - [x] Créer le composant de formulaire d'inscription client avec validation des champs [Phase 5, Req 1]
21. - [x] Développer la galerie de recherche de documents avec filtres interactifs (titre, auteur, catégorie, etc.) [Phase 5, Req 4]
22. - [x] Créer la vue "Mes Emprunts" pour l'interface client affichant les documents en cours et les délais [Phase 5, Req 7]
23. - [x] Implémenter le tableau de bord pour le préposé listant tous les clients et le détail de leurs emprunts actifs [Phase 5, Req 2]

## Phase 6 : Finalisation et Tests d'Intégration
24. - [x] Écrire des tests d'intégration (JUnit/MockMvc) couvrant le cycle complet : inscription -> recherche -> emprunt -> retour [Phase 6]
25. - [x] Réaliser une validation finale de tous les critères d'acceptation du document des exigences [Phase 6]

## Phase 7 : Refactorisation du Modèle Utilisateur et Rôles
26. - [x] Créer la classe abstraite `Utilisateur` (SINGLE_TABLE) et refactoriser `Client` pour en hériter [Phase 7]
27. - [x] Implémenter la classe `Prepose` avec ses attributs spécifiques (matricule) [Phase 7]
28. - [x] Créer la couche Repository, Service et Controller pour les préposés [Phase 7]
29. - [x] Restreindre la création de documents aux seuls préposés (vérification symbolique) [Phase 7, Req 3]

## Phase 8 : Authentification et Sécurité
30. - [x] Ajouter le champ `motDePasse` à `Utilisateur` et mettre à jour les DTOs/Services [Phase 8]
31. - [x] Créer `DataInitializer` pour générer un compte préposé par défaut [Phase 8]
32. - [x] Implémenter l'API de login (`/api/auth/login`) et `AuthService` [Phase 8]
33. - [x] Créer la vue de connexion et intégrer la gestion de session dans React [Phase 8]
34. - [x] Appliquer le contrôle d'accès basé sur les rôles dans le frontend (onglets, boutons) [Phase 8]

## Phase 9 : Amélioration UI/UX
35. - [x] Refonte complète du CSS pour un design moderne et "clean" (App.css) [Phase 9]
36. - [x] Amélioration du Header et de la Navigation (App.jsx) [Phase 9]
37. - [x] Modernisation de la vue Catalogue avec des cartes et badges (Catalogue.jsx) [Phase 9]
38. - [x] Optimisation des formulaires et tableaux (Login, Inscription, Dashboard, MesEmprunts) [Phase 9]

## Phase 10 : Améliorations Fonctionnelles (Batch Emprunts & Case Insensitivity)
39. - [x] Normalisation des emails en minuscules (Modèle, Services, Auth) [Phase 10]
40. - [x] Implémentation du moteur d'emprunt groupé (Backend) [Phase 10]
41. - [x] Ajout de la fonctionnalité de panier dans le Catalogue React [Phase 10]
42. - [x] Validation de l'emprunt groupé et support des retours individuels [Phase 10]
