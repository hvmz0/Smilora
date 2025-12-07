package ma.dentalTech.repository.modules.Finance.Api;

import ma.dentalTech.entities.Finance.SituationFinanciere;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.entities.Enums.PromoEnum;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;

public interface SituationFinanciereRepository extends CrudRepository<SituationFinanciere, Long> {

    // Recherche par patient (une situation par patient normalement)
    SituationFinanciere findByPatientId(Long patientId);

    // Recherches par statut
    List<SituationFinanciere> findByStatut(StatutEnum statut);

    // Recherches par promo
    List<SituationFinanciere> findByPromo(PromoEnum promo);

    // Recherches avec crédit
    List<SituationFinanciere> findWithCredit();
    List<SituationFinanciere> findByCreditGreaterThan(Double montant);

    // Calculs agrégés
    Double calculateTotalCredits();
    Double calculateTotalAchats();
    Double calculateTotalPaye();

    // Top débiteurs
    List<SituationFinanciere> findTopDebtors(int limit);

    // Statistiques
    long countByStatut(StatutEnum statut);
    long countWithCredit();
}