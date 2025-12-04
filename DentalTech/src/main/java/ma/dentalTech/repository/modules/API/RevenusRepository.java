package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.Revenus.Revenus;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour la gestion des revenus du cabinet
 * Author : Abdo
 */
public interface RevenusRepository extends CrudRepository<Revenus, Long> {

    /**
     * Trouve les revenus par cabinet médical
     */
    List<Revenus> findByCabinetMedicaleId(Long cabinetId);

    /**
     * Trouve les revenus par titre
     */
    List<Revenus> searchByTitre(String keyword);

    /**
     * Trouve les revenus dans une plage de dates
     */
    List<Revenus> findByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Trouve les revenus dans une fourchette de montants
     */
    List<Revenus> findByMontantRange(Double montantMin, Double montantMax);

    /**
     * Calcule le total des revenus pour un cabinet
     */
    Double calculateTotalByCabinet(Long cabinetId);

    /**
     * Calcule le total des revenus pour une période
     */
    Double calculateTotalByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Calcule le total des revenus pour un cabinet et une période
     */
    Double calculateTotalByCabinetAndDateRange(Long cabinetId, LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Trouve les revenus par mois
     */
    List<Revenus> findByMonth(int year, int month);

    /**
     * Trouve les revenus par année
     */
    List<Revenus> findByYear(int year);

    /**
     * Trouve les revenus récents
     */
    List<Revenus> findRecent(int limit);

    /**
     * Vérifie si un revenu existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total de revenus
     */
    long count();

    /**
     * Récupère une page de revenus
     */
    List<Revenus> findPage(int limit, int offset);
}