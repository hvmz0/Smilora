package ma.dentalTech.service.modules.Users.Api;

import ma.dentalTech.entities.Users.Logs;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LogsService {

    List<Logs> getAllLogs() throws ServiceException;
    Optional<Logs> getLogById(Long id) throws ServiceException;
    Logs createLog(Logs log) throws ServiceException, ValidationException;
    Logs updateLog(Logs log) throws ServiceException, ValidationException;
    void deleteLog(Long id) throws ServiceException;

    List<Logs> getLogsByUserId(Long userId) throws ServiceException;
    List<Logs> getLogsByDateRange(LocalDateTime debut, LocalDateTime fin) throws ServiceException;
    List<Logs> getActiveSessions() throws ServiceException;
    void closeSession(Long logId, LocalDateTime sessionEnd) throws ServiceException;
    long countByUserId(Long userId) throws ServiceException;
}
