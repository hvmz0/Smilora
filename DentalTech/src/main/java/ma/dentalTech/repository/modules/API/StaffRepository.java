package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.Staff.Staff;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;

/**
 * Repository pour la gestion du staff (personnel)
 * Author : Ayoub
 */
public interface StaffRepository extends CrudRepository<Staff, Long> {

    /**
     * Trouve le staff supervisé par un super admin
     */
    List<Staff> findBySuperviseurId(Long superviseurId);

    /**
     * Recherche des membres du staff par nom ou prénom
     */
    List<Staff> searchByNomPrenom(String keyword);

    /**
     * Trouve le staff par fourchette de salaire
     */
    List<Staff> findBySalaireRange(Double salaireMin, Double salaireMax);

    /**
     * Trouve le staff ayant reçu une prime
     */
    List<Staff> findWithPrime();

    /**
     * Trouve le staff par fourchette de prime
     */
    List<Staff> findByPrimeRange(Double primeMin, Double primeMax);

    /**
     * Trouve tous les membres du staff actifs
     */
    List<Staff> findActifs();

    /**
     * Calcule le total des salaires
     */
    Double calculateTotalSalaires();

    /**
     * Calcule le total des primes
     */
    Double calculateTotalPrimes();

    /**
     * Calcule le total de la masse salariale (salaires + primes)
     */
    Double calculateMasseSalariale();

    /**
     * Met à jour le salaire d'un membre du staff
     */
    void updateSalaire(Long id, Double newSalaire);

    /**
     * Met à jour la prime d'un membre du staff
     */
    void updatePrime(Long id, Double newPrime);

    /**
     * Met à jour le superviseur d'un membre du staff
     */
    void updateSuperviseur(Long staffId, Long superviseurId);

    /**
     * Compte le staff supervisé par un super admin
     */
    long countBySuperviseur(Long superviseurId);

    /**
     * Vérifie si un membre du staff existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total de membres du staff
     */
    long count();

    /**
     * Récupère une page du staff
     */
    List<Staff> findPage(int limit, int offset);
}