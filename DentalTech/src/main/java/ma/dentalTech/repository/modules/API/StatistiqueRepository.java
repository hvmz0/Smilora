package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.Statistique.Statistique;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour la gestion des statistiques du cabinet
 * Author : Ayoub
 */
public interface StatistiqueRepository extends CrudRepository<Statistique, Long> {

    /**
     * Trouve les statistiques par cabinet médical
     */
    List<Statistique> findByCabinetMedicaleId(Long cabinetId);

    /**
     * Trouve les statistiques par date
     */
    List<Statistique> findByDate(LocalDateTime date);

    /**
     * Trouve les statistiques dans une plage de dates
     */
    List<Statistique> findByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Trouve les statistiques par mois
     */
    List<Statistique> findByMonth(int year, int month);

    /**
     * Trouve les statistiques par année
     */
    List<Statistique> findByYear(int year);

    /**
     * Trouve les statistiques les plus récentes pour un cabinet
     */
    List<Statistique> findRecentByCabinet(Long cabinetId, int limit);

    /**
     * Calcule le total des charges pour un cabinet
     */
    Double calculateTotalChargesByCabinet(Long cabinetId);

    /**
     * Calcule le total des revenus pour un cabinet
     */
    Double calculateTotalRevenuesByCabinet(Long cabinetId);

    /**
     * Calcule le bénéfice net (revenus - charges) pour un cabinet
     */
    Double calculateBeneficeNetByCabinet(Long cabinetId);

    /**
     * Calcule le total des charges pour une période
     */
    Double calculateTotalChargesByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Calcule le total des revenus pour une période
     */
    Double calculateTotalRevenuesByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Calcule le bénéfice net pour une période
     */
    Double calculateBeneficeNetByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Trouve les statistiques avec bénéfice positif
     */
    List<Statistique> findWithPositiveBenefice();

    /**
     * Trouve les statistiques avec déficit (charges > revenus)
     */
    List<Statistique> findWithDeficit();

    /**
     * Vérifie si une statistique existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total de statistiques
     */
    long count();

    /**
     * Récupère une page de statistiques
     */
    List<Statistique> findPage(int limit, int offset);
}