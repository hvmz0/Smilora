package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.Medecin.Medecin;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des médecins
 * Author : Hamza
 */
public interface MedecinRepository extends CrudRepository<Medecin, Long> {

    /**
     * Trouve un médecin par numéro CNSS
     */
    Optional<Medecin> findByNumCNSS(String numCNSS);

    /**
     * Trouve les médecins par spécialité
     */
    List<Medecin> findBySpecialite(String specialite);

    /**
     * Trouve les médecins par fourchette de commission
     */
    List<Medecin> findByCommissionRange(Double commissionMin, Double commissionMax);

    /**
     * Recherche des médecins par nom ou prénom
     */
    List<Medecin> searchByNomPrenom(String keyword);

    /**
     * Trouve tous les médecins actifs
     */
    List<Medecin> findActifs();

    /**
     * Trouve les médecins ayant un agenda
     */
    List<Medecin> findWithAgenda();

    /**
     * Calcule le total des commissions pour un médecin
     */
    Double calculateTotalCommission(Long medecinId);

    /**
     * Compte les médecins par spécialité
     */
    long countBySpecialite(String specialite);

    /**
     * Met à jour la commission d'un médecin
     */
    void updateCommission(Long id, Double newCommission);

    /**
     * Vérifie si un médecin existe par son numéro CNSS
     */
    boolean existsByNumCNSS(String numCNSS);

    /**
     * Vérifie si un médecin existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total de médecins
     */
    long count();

    /**
     * Récupère une page de médecins
     */
    List<Medecin> findPage(int limit, int offset);
}