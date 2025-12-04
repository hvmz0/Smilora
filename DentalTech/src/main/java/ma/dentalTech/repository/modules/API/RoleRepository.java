package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.Role.Role;
import ma.dentalTech.entities.User.User;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des rôles utilisateurs
 * Author : Abdo
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    /**
     * Trouve un rôle par son nom
     */
    Optional<Role> findByNom(String nom);

    /**
     * Recherche des rôles par nom (LIKE)
     */
    List<Role> searchByNom(String keyword);

    /**
     * Trouve tous les rôles triés par nom
     */
    List<Role> findAllOrderedByNom();

    /**
     * Vérifie si un rôle existe par son nom
     */
    boolean existsByNom(String nom);

    /**
     * Vérifie si un rôle existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total de rôles
     */
    long count();

    /**
     * Récupère une page de rôles
     */
    List<Role> findPage(int limit, int offset);

    // ---- Relations avec Users ----

    /**
     * Trouve tous les utilisateurs ayant un rôle donné
     */
    List<User> getUsersByRole(Long roleId);

    /**
     * Compte le nombre d'utilisateurs pour un rôle
     */
    long countUsersByRole(Long roleId);

    /**
     * Assigne un rôle à un utilisateur
     */
    void assignRoleToUser(Long roleId, Long userId);

    /**
     * Retire un rôle d'un utilisateur
     */
    void removeRoleFromUser(Long roleId, Long userId);
}