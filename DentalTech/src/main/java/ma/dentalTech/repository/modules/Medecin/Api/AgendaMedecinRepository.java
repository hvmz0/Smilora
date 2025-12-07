package ma.dentalTech.repository.modules.Medecin.Api;

import ma.dentalTech.entities.Medecin.AgendaMedecin;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendaMedecinRepository extends CrudRepository<AgendaMedecin, Long> {

    Optional<AgendaMedecin> findByMedecinId(Long medecinId);
    List<LocalDateTime> getDisponibilites(Long agendaId);
    List<String> getJoursDisponibles(Long agendaId);
    List<String> getConflits(Long agendaId);
    void addDisponibilite(Long agendaId, LocalDateTime disponibilite);
    void removeDisponibilite(Long agendaId, LocalDateTime disponibilite);
    void addConflit(Long agendaId, String conflit);
    void clearConflits(Long agendaId);
}