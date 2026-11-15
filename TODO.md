
# TODO — Projet Réservations Spring Boot

## PRIORITÉ : Migration UserRole vers Role

Objectif : suivre le schéma du PID avec les tables :
- users
- roles
- role_user

### 1. Base de données
- [ ] Créer la migration Flyway pour `role_user`
- [ ] Migrer les rôles existants de `users.role`
- [ ] Vérifier les clés étrangères et les données
- [ ] Supprimer l'ancienne colonne `users.role` après validation

### 2. Modèle Java
- [ ] Modifier `User.java` : utiliser une collection de `Role`
- [ ] Ajouter la relation `@ManyToMany`
- [ ] Configurer la relation dans `Role.java`

### 3. Authentification et sécurité
- [ ] Adapter `CustomUserDetailsService.java`
- [ ] Convertir les rôles de la base en autorités Spring Security
- [ ] Vérifier les autorisations ADMIN, MEMBER, etc.

### 4. Services et administration
- [ ] Adapter `UserService.java` pour attribuer MEMBER
- [ ] Adapter `AdminUserService.java` aux rôles multiples
- [ ] Adapter `AdminUserController.java` et les DTO

### 5. Templates
- [ ] Adapter les templates `users`
- [ ] Afficher plusieurs rôles par utilisateur
- [ ] Permettre l'attribution de plusieurs rôles

### 6. Tests et nettoyage
- [ ] Adapter les tests Java utilisant `UserRole`
- [ ] Tester la connexion et les autorisations
- [ ] Tester l'attribution et le retrait des rôles
- [ ] Supprimer l'enum `UserRole.java` après migration
- [ ] Vérifier la compilation et tous les tests

## IMPORTANT
Ne pas supprimer `UserRole.java` ni `users.role`
avant d'avoir terminé la migration des données
et adapté Spring Security.

Ne jamais modifier une ancienne migration Flyway
déjà exécutée.

