package ma.dentalTech.service.modules.Finance.Api;

import ma.dentalTech.entities.Finance.Charges;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChargesService {

    List<Charges> getAllCharges() throws ServiceException;
    Optional<Charges> getChargesById(Long id) throws ServiceException;
    Charges createCharges(Charges charges) throws ServiceException, ValidationException;
    Charges updateCharges(Charges charges) throws ServiceException, ValidationException;
    void deleteCharges(Long id) throws ServiceException;

    List<Charges> getChargesByType(String type) throws ServiceException;
    List<Charges> getChargesByDate(LocalDate date) throws ServiceException;
    List<Charges> getChargesByDateRange(LocalDateTime debut, LocalDateTime fin) throws ServiceException;
    List<Charges> getChargesByStatistiqueId(Long statistiqueId) throws ServiceException;
    List<Charges> searchCharges(String keyword) throws ServiceException;
    Double calculateTotalCharges(LocalDateTime debut, LocalDateTime fin) throws ServiceException;
    Double calculateChargesByType(String type) throws ServiceException;
    long countCharges() throws ServiceException;
}
