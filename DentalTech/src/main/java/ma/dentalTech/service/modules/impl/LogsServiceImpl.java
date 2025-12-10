package ma.dentalTech.service.modules.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.entities.Users.Logs;
import ma.dentalTech.repository.modules.Users.Api.LogsRepository;
import ma.dentalTech.service.modules.Api.LogsService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LogsServiceImpl implements LogsService {

    private final LogsRepository logsRepository;

    @Override
    public List<Logs> getAllLogs() throws ServiceException {
        try {
            return logsRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des logs", e);
        }
    }

    @Override
    public Optional<Logs> getLogById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(logsRepository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du log", e);
        }
    }

    @Override
    public Logs createLog(Logs log) throws ServiceException, ValidationException {
        try {
            validateLog(log);
            logsRepository.create(user);
            return log;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du log", e);
        }
    }

    @Override
    public Logs updateLog(Logs log) throws ServiceException, ValidationException {
        try {
            if (log.getId() == null) {
                throw new ValidationException("L'ID du log est requis");
            }
            validateLog(log);
            logsRepository.update(log);
            return log;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du log", e);
        }
    }

    @Override
    public void deleteLog(Long id) throws ServiceException {
        try {
            logsRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du log", e);
        }
    }

    @Override
    public List<Logs> getLogsByUserId(Long userId) throws ServiceException {
        try {
            return logsRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des logs par utilisateur", e);
        }
    }

    @Override
    public List<Logs> getLogsByDateRange(LocalDateTime debut, LocalDateTime fin) throws ServiceException {
        try {
            return logsRepository.findByDateRange(debut, fin);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des logs par plage de dates", e);
        }
    }

    @Override
    public List<Logs> getActiveSessions() throws ServiceException {
        try {
            return logsRepository.findActiveSessions();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des sessions actives", e);
        }
    }

    @Override
    public void closeSession(Long logId, LocalDateTime sessionEnd) throws ServiceException {
        try {
            logsRepository.closeSession(logId, sessionEnd);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la fermeture de la session", e);
        }
    }

    @Override
    public long countByUserId(Long userId) throws ServiceException {
        try {
            return logsRepository.countByUserId(userId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des logs", e);
        }
    }

    private void validateLog(Logs log) throws ValidationException {
        if (log.getUserId() == null) {
            throw new ValidationException("L'utilisateur est obligatoire");
        }
    }
}
