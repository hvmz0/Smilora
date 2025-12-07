package ma.dentalTech.repository.modules.Finance.Api;

import ma.dentalTech.entities.Finance.Facture;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FactureRepository extends CrudRepository<Facture, Long> {

    // Recherches par patient
    List<Facture> findByPatientId(Long patientId);
    List<Facture> findByPatientAndStatut(Long patientId, StatutEnum statut);

    // Recherches par consultation
    Facture findByConsultationId(Long consultationId);

    // Recherches par statut
    List<Facture> findByStatut(StatutEnum statut);
    List<Facture> findUnpaid();
    List<Facture> findPaid();

    // Recherches par date
    List<Facture> findByDate(LocalDate date);
    List<Facture> findByDateRange(LocalDateTime debut, LocalDateTime fin);
    List<Facture> findByMonth(int year, int month);

    // Calculs financiers
    Double calculateTotalByPatient(Long patientId);
    Double calculateUnpaidByPatient(Long patientId);
    Double calculateTotalByDateRange(LocalDateTime debut, LocalDateTime fin);
    Double calculateTotalUnpaid();

    // Statistiques
    long countByStatut(StatutEnum statut);
    long countUnpaidByPatient(Long patientId);

    // Factures en retard
    List<Facture> findOverdueFactures(LocalDateTime beforeDate);
}
