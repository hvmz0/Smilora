package ma.dentalTech.service.modules.Finance.Api;

import ma.dentalTech.entities.Finance.Facture;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FactureService {

    List<Facture> getAllFactures() throws ServiceException;
    Optional<Facture> getFactureById(Long id) throws ServiceException;
    Facture createFacture(Facture facture) throws ServiceException, ValidationException;
    Facture updateFacture(Facture facture) throws ServiceException, ValidationException;
    void deleteFacture(Long id) throws ServiceException;

    List<Facture> getFacturesByPatient(Long patientId) throws ServiceException;
    Facture getFactureByConsultation(Long consultationId) throws ServiceException;
    List<Facture> getFacturesUnpaid() throws ServiceException;
    List<Facture> getFacturesByDate(LocalDate date) throws ServiceException;
    List<Facture> getFacturesByDateRange(LocalDateTime debut, LocalDateTime fin) throws ServiceException;

    void markAsPaid(Long factureId) throws ServiceException;
    void addPayment(Long factureId, Double montant) throws ServiceException, ValidationException;

    Double calculateTotalByPatient(Long patientId) throws ServiceException;
    Double calculateUnpaidByPatient(Long patientId) throws ServiceException;
    Double calculateTotalUnpaid() throws ServiceException;
}