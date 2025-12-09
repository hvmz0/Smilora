package ma.dentalTech.service.modules.Finance.api;

import ma.dentalTech.entities.Finance.SituationFinanciere;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.entities.Enums.PromoEnum;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public interface SituationFinanciereService {

    List<SituationFinanciere> getAllSituations() throws ServiceException;
    Optional<SituationFinanciere> getSituationById(Long id) throws ServiceException;
    SituationFinanciere getSituationByPatient(Long patientId) throws ServiceException;
    SituationFinanciere createSituation(SituationFinanciere situation) throws ServiceException, ValidationException;
    SituationFinanciere updateSituation(SituationFinanciere situation) throws ServiceException, ValidationException;
    void deleteSituation(Long id) throws ServiceException;

    List<SituationFinanciere> getSituationsWithCredit() throws ServiceException;
    List<SituationFinanciere> getTopDebtors(int limit) throws ServiceException;

    Double calculateTotalCredits() throws ServiceException;
    void applyPromotion(Long situationId, PromoEnum promo) throws ServiceException;
    void updateAfterPayment(Long patientId, Double montant) throws ServiceException;
}