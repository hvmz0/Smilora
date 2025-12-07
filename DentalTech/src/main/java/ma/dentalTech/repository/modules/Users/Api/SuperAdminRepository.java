package ma.dentalTech.repository.modules.Users.Api;

import ma.dentalTech.entities.Users.SuperAdmin;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.Optional;

public interface SuperAdminRepository extends CrudRepository<SuperAdmin, Long> {

    Optional<SuperAdmin> findByUserId(Long userId);
}