package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.Charges.Charges;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour la gestion des charges du cabinet
 * Author : Walid
 */
public interface ChargesRepository extends CrudRepository<Charges, Long> {

    /**
     * Trouve les charges par cabinet médical
     */
    List<Charges> findByCabinetMedicaleId(Long cabinetId);

    /**
     * Trouve les charges par titre
     */
    List<Charges> searchByTitre(String keyword);

    /**
     * Trouve les charges dans une plage de dates
     */
    List<Charges> findByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Trouve les charges dans une fourchette de montants
     */
    List<Charges> findByMontantRange(Double montantMin, Double montantMax);

    /**
     * Calcule le total des charges pour un cabinet
     */
    Double calculateTotalByCabinet(Long cabinetId);

    /**
     * Calcule le total des charges pour une période
     */
    Double calculateTotalByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Calcule le total des charges pour un cabinet et une période
     */
    Double calculateTotalByCabinetAndDateRange(Long cabinetId, LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Trouve les charges par mois
     */
    List<Charges> findByMonth(int year, int month);

    /**
     * Trouve les charges par année
     */
    List<Charges> findByYear(int year);

    /**
     * Vérifie si une charge existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total de charges
     */
    long count();

    /**
     * Récupère une page de charges
     */
    List<Charges> findPage(int limit, int offset);
}