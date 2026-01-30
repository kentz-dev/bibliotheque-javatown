# JavaTown Library — Système de gestion de bibliothèque

Application complète de gestion de bibliothèque développée avec Java, Spring Boot et React.  
Le projet met en œuvre une architecture en couches avec API REST, persistance Spring Data JPA et interface web React.

Le développement suit une approche **spec-driven development** assistée par IA : exigences → plan → tâches → implémentation par phases contrôlées.

---

## Objectif du projet

Moderniser le système d’une bibliothèque municipale en permettant la gestion des clients, des documents et des emprunts via une application web transactionnelle.

Ce projet démontre la capacité à :
- analyser des exigences métier
- concevoir un modèle orienté objet
- structurer une API REST
- appliquer une architecture par couches
- utiliser une méthode de développement pilotée par spécifications
- intégrer l’IA dans le workflow de développement

---

## Fonctionnalités principales

- Inscription des clients
- Consultation de la liste des clients et de leurs emprunts
- Gestion des documents :
    - Livres
    - CD
    - DVD
- Recherche multicritère :
    - titre (correspondance partielle)
    - auteur
    - année
    - catégorie
- Emprunt avec validation de disponibilité
- Retour de documents
- Consultation des emprunts actifs d’un client
- Dates de retour calculées selon le type de document

---

## Stack technique

### Backend
- Java
- Spring Boot
- Spring Data JPA
- API REST
- DTO pattern
- H2 Database (mémoire)
- Tests unitaires

### Frontend
- React
- Composants fonctionnels
- Appels REST

---

## Méthodologie — Spec-Driven Development

Le projet est construit avec une approche pilotée par spécifications :

1. Définition des exigences fonctionnelles  
   → `docs/requirements.md`

2. Génération du plan d’implémentation  
   → `docs/plan.md`

3. Découpage en tâches exécutables  
   → `docs/tasks.md`

4. Implémentation par phases contrôlées avec agent IA

Cette méthode garantit :
- clarté
- traçabilité
- contrôle du scope
- commits granulaires

---

## Structure du projet

docs/
requirements.md
plan.md
tasks.md

backend/
frontend/


---

## Points d’architecture démontrés

- Séparation des couches (Controller / Service / Repository)
- Modélisation orientée objet avec héritage (Document → Livre/CD/DVD)
- DTO pour isolation de la couche API
- Validation métier côté service
- Requêtes de recherche dynamiques
- Gestion transactionnelle des emprunts

---

## Statut

Projet en développement actif avec workflow spec-driven.
