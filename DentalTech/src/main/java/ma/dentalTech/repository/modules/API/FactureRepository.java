package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.Facture.Facture;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour la gestion des factures
 * Author : Walid
 */
public interface FactureRepository extends CrudRepository<Facture, Long> {

    /**
     * Trouve les factures par cabinet médical
     */
    List<Facture> findByCabinetMedicaleId(Long cabinetId);

    /**
     * Trouve les factures par situation financière
     */
    List<Facture> findBySituationFinanciereId(Long situationId);

    /**
     * Trouve les factures par statut
     */
    List<Facture> findByStatut(String statut);

    /**
     * Trouve les factures par date
     */
    List<Facture> findByDate(LocalDateTime date);

    /**
     * Trouve les factures dans une plage de dates
     */
    List<Facture> findByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Trouve les factures impayées (resteDu > 0)
     */
    List<Facture> findUnpaid();

    /**
     * Trouve les factures payées intégralement
     */
    List<Facture> findFullyPaid();

    /**
     * Trouve les factures par fourchette de montant
     */
    List<Facture> findByMontantRange(Double montantMin, Double montantMax);

    /**
     * Calcule le total des factures pour un cabinet
     */
    Double calculateTotalByCabinet(Long cabinetId);

    /**
     * Calcule le total payé pour un cabinet
     */
    Double calculateTotalPayeByCabinet(Long cabinetId);

    /**
     * Calcule le total du reste dû pour un cabinet
     */
    Double calculateTotalResteDuByCabinet(Long cabinetId);

    /**
     * Trouve les factures par mois
     */
    List<Facture> findByMonth(int year, int month);

    /**
     * Trouve les factures par année
     */
    List<Facture> findByYear(int year);

    /**
     * Met à jour le statut d'une facture
     */
    void updateStatut(Long id, String newStatut);

    /**
     * Met à jour le paiement d'une facture
     */
    void updatePaiement(Long id, Double montantPaye);

    /**
     * Vérifie si une facture existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total de factures
     */
    long count();

    /**
     * Compte les factures par statut
     */
    long countByStatut(String statut);

    /**
     * Récupère une page de factures
     */
    List<Facture> findPage(int limit, int offset);
}