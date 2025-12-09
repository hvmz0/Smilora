package ma.dentalTech.service.modules.Finance.Api;

import ma.dentalTech.entities.Finance.Statistiques;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StatistiquesService {

    List<Statistiques> getAllStatistiques() throws ServiceException;
    Optional<Statistiques> getStatistiquesById(Long id) throws ServiceException;
    Statistiques createStatistiques(Statistiques statistiques) throws ServiceException, ValidationException;
    Statistiques updateStatistiques(Statistiques statistiques) throws ServiceException, ValidationException;
    void deleteStatistiques(Long id) throws ServiceException;

    List<Statistiques> getStatistiquesByDateRange(LocalDateTime debut, LocalDateTime fin) throws ServiceException;
    Statistiques getStatistiquesByDate(LocalDateTime date) throws ServiceException;
    List<Statistiques> getStatistiquesByMonth(int year, int month) throws ServiceException;
    Double calculateBeneficeByDateRange(LocalDateTime debut, LocalDateTime fin) throws ServiceException;
    List<Statistiques> getRecentStatistiques(int limit) throws ServiceException;
}
