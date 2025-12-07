package ma.dentalTech.repository.modules.Users.Api;

import ma.dentalTech.entities.Users.Roles;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.Optional;

public interface RolesRepository extends CrudRepository<Roles, Long> {

    Optional<Roles> findByLibelle(String libelle);
    boolean existsByLibelle(String libelle);
}