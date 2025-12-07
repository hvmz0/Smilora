package ma.dentalTech.repository.modules.RDV.Api;

import ma.dentalTech.entities.RDV.RDV;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RDVRepository extends CrudRepository<RDV, Long> {

    // Recherche par entité
    List<RDV> findByPatientId(Long patientId);
    List<RDV> findByMedecinId(Long medecinId);

    // Recherche par date
    List<RDV> findByDate(LocalDate date);
    List<RDV> findByDateRange(LocalDate debut, LocalDate fin);

    // Recherche par statut
    List<RDV> findByStatut(StatutEnum statut);

    // Gestion planning
    List<RDV> findUpcoming(Long medecinId);
    boolean isSlotAvailable(Long medecinId, LocalDateTime heure);

    // Comptage
    long countByDate(LocalDate date);
}