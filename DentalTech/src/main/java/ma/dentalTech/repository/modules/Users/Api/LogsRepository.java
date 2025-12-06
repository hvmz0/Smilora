package ma.dentalTech.repository.modules.Users.Api;

import ma.dentalTech.entities.Users.Logs;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LogsRepository extends CrudRepository<Logs, Long> {

    List<Logs> findByUserId(Long userId);
    List<Logs> findByDateRange(LocalDateTime debut, LocalDateTime fin);
    List<Logs> findActiveSessions();
    void closeSession(Long logId, LocalDateTime sessionEnd);
    long countByUserId(Long userId);
}