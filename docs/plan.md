# Plan d'implémentation — JavaTown Library

## 1. Résumé de l'objectif
L'objectif de ce projet est de concevoir et développer une application web robuste pour automatiser la gestion de la 
bibliothèque municipale de Javatown. Le système doit permettre une gestion fluide des clients, un suivi précis du 
catalogue de documents (Livres, CD, DVD) et une automatisation du cycle de vie des emprunts et retours. 
L'approche adoptée est le **Spec-Driven Development**, garantissant que chaque fonctionnalité répond strictement aux 
exigences définies.

## 2. Structure en couches (Architecture)
L'application sera structurée selon une architecture hexagonale/en couches pour assurer la maintenabilité et la testabilité :

- **Couche Domaine (Domain)** : Contient les entités métier (Client, Document, Livre, CD, DVD, Emprunt). C'est le cœur de la logique métier, indépendant des frameworks.
- **Couche Persistance (Persistence)** : Gère l'accès aux données via Spring Data JPA. Utilisation d'une base de données H2 en mémoire pour le développement.
- **Couche Service (Service)** : Implémente la logique de coordination (ex: calcul de la date de retour, validation de la disponibilité d'un exemplaire, inscription client).
- **Couche API (Web/REST)** : Expose les services via des contrôleurs REST. Utilisation de DTOs (Data Transfer Objects) pour isoler le modèle de données de l'interface publique.
- **Couche Frontend (UI)** : Interface utilisateur réactive avec React pour la consommation de l'API.

## 3. Phases d'implémentation

### Phase 1 : Initialisation du Socle Technique
Mise en place de l'environnement de développement et des structures de base.
- Configuration du projet Spring Boot (Backend) et React (Frontend).
- Mise en place de la base de données H2 et du schéma initial.
- Création des structures de dossiers par couches.

### Phase 2 : Gestion du Catalogue de Documents (Req 3, 4)
Implémentation de la gestion des ressources de la bibliothèque.
- **Backend** : Création du modèle d'héritage pour `Document` (Livre, CD, DVD).
- **Logique** : Implémentation du moteur de recherche multicritère (titre partiel, auteur, catégorie).
- **API** : Endpoints de création, consultation et recherche de documents.

### Phase 3 : Gestion des Clients (Req 1, 2)
Mise en place du référentiel des usagers.
- **Backend** : Entité `Client` et gestion de la persistance.
- **Logique** : Service d'inscription et de validation des données clients.
- **API** : Endpoints pour l'inscription et la récupération de la liste globale des clients.

### Phase 4 : Moteur de Transactions (Emprunts & Retours) (Req 5, 6, 7)
Cœur fonctionnel du système gérant les flux de documents.
- **Backend** : Entité `Emprunt` reliant Client et Document.
- **Logique** : 
    - Validation atomique de la disponibilité (gestion des stocks).
    - Calcul automatique de la date de retour selon le type de document.
    - Logique de retour (incrémentation stock, archivage de l'emprunt).
- **API** : Endpoints `/emprunts/creer`, `/emprunts/retourner` et consultation par client.

### Phase 5 : Interface Utilisateur React
Développement de l'expérience utilisateur.
- **Composants** : 
    - Formulaire d'inscription client.
    - Galerie de recherche de documents avec filtres.
    - Vue "Mes Emprunts" pour les clients.
    - Tableau de bord des clients pour les préposés.
- **Service** : Couche d'intégration API avec Axios.

### Phase 6 : Finalisation et Tests d'Intégration
Assurance qualité et déploiement.
- Tests d'intégration entre le frontend et le backend.
- Validation de tous les critères d'acceptation du `requirements.md`.

## 4. Ordre logique de réalisation
L'ordre suit une progression naturelle des données :
1. **Modèle de données & Persistance** (Backend) : Créer les tables et entités.
2. **Services Métier** (Backend) : Implémenter la logique de calcul et validation.
3. **API REST** (Backend) : Exposer les fonctionnalités.
4. **Composants UI** (Frontend) : Créer les interfaces.
5. **Intégration** : Relier l'interface aux API.

## 5. Dépendances techniques
- **Backend** : Java 17, Spring Boot (Web, Data JPA), H2 Database, Lombok, Maven.
- **Frontend** : Node.js, React, React Router, Axios, Tailwind CSS (optionnel pour le style).
- **Outils** : Git, JUnit 5 pour les tests.

## 6. Risques potentiels
- **Gestion des accès concurrents** : Risque de surendettement d'un document si deux clients empruntent simultanément le dernier exemplaire. *Solution : Utilisation de verrous optimistes/pessimistes JPA.*
- **Complexité de l'héritage** : Le mapping JPA des sous-types (Livre, CD, DVD) peut être complexe. *Solution : Utilisation de la stratégie `SINGLE_TABLE` avec une colonne discriminateur.*
- **Intégrité des données** : Suppression d'un client ayant des emprunts actifs. *Solution : Ajout de contraintes d'intégrité et de validations métier.*

## 7. Traçabilité (Matrice Phases vs Exigences)

| Phase | Exigence | Titre de l'exigence | Objectif couvert |
| :--- | :--- | :--- | :--- |
| Phase 3 | **Req 1** | Inscription d'un client | Création de dossier client valide. |
| Phase 3 | **Req 2** | Liste des clients | Affichage des usagers et de leurs emprunts actifs. |
| Phase 2 | **Req 3** | Gestion des documents | Enregistrement typé et suivi des exemplaires. |
| Phase 2 | **Req 4** | Recherche de documents | Recherche multicritère partielle et combinée. |
| Phase 4 | **Req 5** | Emprunt d'un document | Validation stock, création emprunt et date retour. |
| Phase 4 | **Req 6** | Retour d'un document | Mise à jour stock et statut de l'emprunt. |
| Phase 4 | **Req 7** | Consultation des emprunts | Affichage des dates de retour pour le client. |
