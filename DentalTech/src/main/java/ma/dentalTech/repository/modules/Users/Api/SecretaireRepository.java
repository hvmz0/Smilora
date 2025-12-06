package ma.dentalTech.repository.modules.Users.Api;

import ma.dentalTech.entities.Users.Secretaire;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SecretaireRepository extends CrudRepository<Secretaire, Long> {

    Optional<Secretaire> findByUserId(Long userId);
    Optional<Secretaire> findByNumCNSS(String numCNSS);
    List<Secretaire> findByAgendaMed(String agendaMed);
    long count();
}