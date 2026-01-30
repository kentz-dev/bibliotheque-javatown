# Document des exigences

## Introduction

Cette application vise à moderniser les opérations de la bibliothèque municipale de Javatown.
Le système manuel actuel est inefficace et cause des pertes de documents ainsi qu’un mauvais
suivi des emprunts.

Le système permettra aux clients de s’inscrire, rechercher des documents, emprunter et retourner
des documents, et consulter leurs emprunts en cours. Les préposés sont responsables de la gestion
des documents et de la supervision des opérations d’emprunt.

L’objectif est de fournir une plateforme centralisée, fiable et simple pour gérer les documents,
les clients et les emprunts dans une bibliothèque unique.

## Acteurs

- Client
- Préposé

## Exigences fonctionnelles

### Exigence 1 — Inscription d’un client

**User Story**  
En tant que client, je veux m’inscrire dans le système afin de pouvoir emprunter des documents.

**Critères d’acceptation**
- LORSQU’un client fournit des informations valides  
  ALORS le système DOIT créer un nouveau dossier client.
- LORSQUE l’inscription est réussie  
  ALORS le client DOIT être disponible pour les opérations d’emprunt.

---

### Exigence 2 — Liste des clients

**User Story**  
En tant que préposé, je veux voir la liste des clients afin de gérer leurs emprunts.

**Critères d’acceptation**
- LORSQU’un préposé demande la liste  
  ALORS le système DOIT afficher tous les clients.
- LORSQU’un client a des emprunts actifs  
  ALORS le système DOIT afficher ses emprunts.

---

### Exigence 3 — Gestion des documents

**User Story**  
En tant que préposé, je veux enregistrer des documents (livres, CD, DVD) afin de les rendre disponibles à l’emprunt.

**Critères d’acceptation**
- LORSQU’un document est enregistré  
  ALORS le système DOIT sauvegarder ses informations.
- LORSQU’un document est créé  
  ALORS le système DOIT enregistrer son type (Livre, CD, DVD).
- LORSQU’un document est enregistré  
  ALORS le système DOIT suivre le nombre d’exemplaires disponibles.

---

### Exigence 4 — Recherche de documents

**User Story**  
En tant qu’utilisateur, je veux rechercher des documents selon plusieurs critères afin de trouver facilement ce que je cherche.

**Critères d’acceptation**
- LORSQU’on recherche par titre  
  ALORS la recherche DOIT être partielle (non exacte).
- LORSQU’on recherche par auteur, année ou catégorie  
  ALORS le système DOIT filtrer selon ces critères.
- LORSQUE plusieurs critères sont fournis  
  ALORS ils DOIVENT être combinés.
- LORSQU’aucun résultat n’est trouvé  
  ALORS une liste vide DOIT être retournée.

---

### Exigence 5 — Emprunt d’un document

**User Story**  
En tant que client, je veux emprunter un document afin de pouvoir l’utiliser pour une période limitée.

**Critères d’acceptation**
- LORSQU’un client demande un emprunt  
  ET qu’un exemplaire est disponible  
  ALORS le système DOIT créer un emprunt.
- LORSQU’un document est emprunté  
  ALORS le nombre disponible DOIT diminuer.
- LORSQU’aucun exemplaire n’est disponible  
  ALORS l’emprunt DOIT être refusé.
- LORSQUE l’emprunt est créé  
  ALORS une date de retour DOIT être assignée selon le type.

---

### Exigence 6 — Retour d’un document

**User Story**  
En tant que client, je veux retourner un document afin qu’il redevienne disponible.

**Critères d’acceptation**
- LORSQU’un document est retourné  
  ALORS il DOIT être marqué comme retourné.
- LORS DU RETOUR  
  ALORS le nombre disponible DOIT augmenter.
- APRÈS le retour  
  ALORS la liste d’emprunts du client DOIT être mise à jour.

---

### Exigence 7 — Consultation des emprunts

**User Story**  
En tant que client, je veux voir mes emprunts en cours afin de connaître les dates de retour.

**Critères d’acceptation**
- LORSQU’un client consulte ses emprunts  
  ALORS le système DOIT afficher les emprunts actifs.
- LORS DE L’AFFICHAGE  
  ALORS les dates de retour DOIVENT être visibles.
