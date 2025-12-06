package ma.dentalTech.repository.modules.Medecin.Api;

import ma.dentalTech.entities.Medecin.Medecin;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MedecinRepository extends CrudRepository<Medecin, Long> {

    Optional<Medecin> findByUserId(Long userId);
    List<Medecin> findBySpecialite(String specialite);
    List<Medecin> searchByNom(String keyword);
    Long getAgendaId(Long medecinId);
    long count();
}