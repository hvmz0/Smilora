package ma.dentalTech.repository.modules.Users.Api;

import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByLogin(String login);
    Optional<User> findByCin(String cin);
    List<User> findByRole(Long roleId);
    List<User> searchByNom(String keyword);
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
    void updateLastLogin(Long userId);
    long count();
}