package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.Secretaire.Secretaire;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des secrétaires
 * Author : Abdo
 */
public interface SecretaireRepository extends CrudRepository<Secretaire, Long> {

    /**
     * Trouve un(e) secrétaire par numéro CNSS
     */
    Optional<Secretaire> findByNumCNSS(String numCNSS);

    /**
     * Trouve les secrétaires gérant un cabinet spécifique
     */
    List<Secretaire> findByCabinetGererId(Long cabinetId);

    /**
     * Recherche des secrétaires par nom ou prénom
     */
    List<Secretaire> searchByNomPrenom(String keyword);

    /**
     * Trouve tous les secrétaires actifs
     */
    List<Secretaire> findActifs();

    /**
     * Trouve les secrétaires par fourchette de salaire
     */
    List<Secretaire> findBySalaireRange(Double salaireMin, Double salaireMax);

    /**
     * Met à jour le salaire d'un(e) secrétaire
     */
    void updateSalaire(Long id, Double newSalaire);

    /**
     * Met à jour la prime d'un(e) secrétaire
     */
    void updatePrime(Long id, Double newPrime);

    /**
     * Vérifie si un(e) secrétaire existe par numéro CNSS
     */
    boolean existsByNumCNSS(String numCNSS);

    /**
     * Vérifie si un(e) secrétaire existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total de secrétaires
     */
    long count();

    /**
     * Récupère une page de secrétaires
     */
    List<Secretaire> findPage(int limit, int offset);
}