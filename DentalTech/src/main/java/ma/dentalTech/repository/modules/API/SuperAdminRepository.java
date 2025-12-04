package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.Staff.Staff;
import ma.dentalTech.entities.SuperAdmin.SuperAdmin;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des super administrateurs
 * Author : Ayoub
 */
public interface SuperAdminRepository extends CrudRepository<SuperAdmin, Long> {

    /**
     * Trouve un super admin par email
     */
    Optional<SuperAdmin> findByEmail(String email);

    /**
     * Trouve un super admin par username
     */
    Optional<SuperAdmin> findByUsername(String username);

    /**
     * Recherche des super admins par nom ou prénom
     */
    List<SuperAdmin> searchByNomPrenom(String keyword);

    /**
     * Trouve tous les super admins actifs
     */
    List<SuperAdmin> findActifs();

    /**
     * Vérifie si un super admin existe par email
     */
    boolean existsByEmail(String email);

    /**
     * Vérifie si un super admin existe par username
     */
    boolean existsByUsername(String username);

    /**
     * Vérifie si un super admin existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total de super admins
     */
    long count();

    /**
     * Récupère une page de super admins
     */
    List<SuperAdmin> findPage(int limit, int offset);

    // ---- Gestion du staff supervisé ----

    /**
     * Trouve le staff supervisé par un super admin
     */
    List<Staff> getStaffSupervise(Long superAdminId);

    /**
     * Ajoute un membre du staff à superviser
     */
    void addStaffSupervise(Long superAdminId, Long staffId);

    /**
     * Retire un membre du staff de la supervision
     */
    void removeStaffSupervise(Long superAdminId, Long staffId);

    /**
     * Compte le nombre de staff supervisé
     */
    long countStaffSupervise(Long superAdminId);
}