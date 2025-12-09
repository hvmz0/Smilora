package ma.dentalTech.service.modules.Finance.Api;

import ma.dentalTech.entities.Finance.Revenues;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RevenuesService {

    List<Revenues> getAllRevenues() throws ServiceException;
    Optional<Revenues> getRevenuesById(Long id) throws ServiceException;
    Revenues createRevenues(Revenues revenues) throws ServiceException, ValidationException;
    Revenues updateRevenues(Revenues revenues) throws ServiceException, ValidationException;
    void deleteRevenues(Long id) throws ServiceException;

    List<Revenues> getRevenuesByDate(LocalDate date) throws ServiceException;
    List<Revenues> getRevenuesByDateRange(LocalDateTime debut, LocalDateTime fin) throws ServiceException;
    List<Revenues> getRevenuesByStatistiqueId(Long statistiqueId) throws ServiceException;
    List<Revenues> searchRevenues(String keyword) throws ServiceException;
    Double calculateTotalRevenues(LocalDateTime debut, LocalDateTime fin) throws ServiceException;
    long countRevenues() throws ServiceException;
}
