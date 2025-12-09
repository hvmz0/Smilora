package ma.dentalTech.service.modules.Finance.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.entities.Enums.PromoEnum;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.entities.Finance.SituationFinanciere;
import ma.dentalTech.repository.modules.Finance.Api.SituationFinanciereRepository;
import ma.dentalTech.service.modules.Finance.api.SituationFinanciereService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class SituationFinanciereServiceImpl implements SituationFinanciereService {

    private final SituationFinanciereRepository situationRepository;

    @Override
    public List<SituationFinanciere> getAllSituations() throws ServiceException {
        try {
            return situationRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des situations financières", e);
        }
    }

    @Override
    public Optional<SituationFinanciere> getSituationById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(situationRepository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la situation", e);
        }
    }

    @Override
    public SituationFinanciere getSituationByPatient(Long patientId) throws ServiceException {
        try {
            return situationRepository.findByPatientId(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la situation du patient", e);
        }
    }

    @Override
    public SituationFinanciere createSituation(SituationFinanciere situation) throws ServiceException, ValidationException {
        try {
            validateSituation(situation);

            // Calculer le crédit automatiquement
            if (situation.getCredit() == null) {
                double totalAchats = situation.getTotalDesAchats() != null ? situation.getTotalDesAchats() : 0.0;
                double totalPaye = situation.getTotalPaye() != null ? situation.getTotalPaye() : 0.0;
                situation.setCredit(totalAchats - totalPaye);
            }

            // Déterminer le statut
            if (situation.getStatut() == null) {
                situation.setStatut(situation.getCredit() > 0 ? StatutEnum.NON_PAYE : StatutEnum.PAYE);
            }

            if (situation.getPromo() == null) {
                situation.setPromo(PromoEnum.AUCUNE);
            }

            situationRepository.create(situation);
            return situation;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de la situation", e);
        }
    }

    @Override
    public SituationFinanciere updateSituation(SituationFinanciere situation) throws ServiceException, ValidationException {
        try {
            if (situation.getId() == null) {
                throw new ValidationException("L'ID de la situation est requis");
            }
            validateSituation(situation);
            situationRepository.update(situation);
            return situation;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de la situation", e);
        }
    }

    @Override
    public void deleteSituation(Long id) throws ServiceException {
        try {
            situationRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de la situation", e);
        }
    }

    @Override
    public List<SituationFinanciere> getSituationsWithCredit() throws ServiceException {
        try {
            return situationRepository.findWithCredit();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des situations avec crédit", e);
        }
    }

    @Override
    public List<SituationFinanciere> getTopDebtors(int limit) throws ServiceException {
        try {
            return situationRepository.findTopDebtors(limit);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des plus gros débiteurs", e);
        }
    }

    @Override
    public Double calculateTotalCredits() throws ServiceException {
        try {
            return situationRepository.calculateTotalCredits();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du calcul du total des crédits", e);
        }
    }

    @Override
    public void applyPromotion(Long situationId, PromoEnum promo) throws ServiceException {
        try {
            SituationFinanciere situation = situationRepository.findById(situationId);
            if (situation == null) {
                throw new ServiceException("Situation non trouvée");
            }
            situation.setPromo(promo);
            situationRepository.update(situation);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de l'application de la promotion", e);
        }
    }

    @Override
    public void updateAfterPayment(Long patientId, Double montant) throws ServiceException {
        try {
            SituationFinanciere situation = situationRepository.findByPatientId(patientId);
            if (situation == null) {
                throw new ServiceException("Situation financière non trouvée");
            }

            double nouveauTotalPaye = (situation.getTotalPaye() != null ? situation.getTotalPaye() : 0.0) + montant;
            double nouveauCredit = (situation.getTotalDesAchats() != null ? situation.getTotalDesAchats() : 0.0) - nouveauTotalPaye;

            situation.setTotalPaye(nouveauTotalPaye);
            situation.setCredit(nouveauCredit);
            situation.setStatut(nouveauCredit <= 0 ? StatutEnum.PAYE : StatutEnum.NON_PAYE);

            situationRepository.update(situation);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour après paiement", e);
        }
    }

    private void validateSituation(SituationFinanciere situation) throws ValidationException {
        if (situation.getPatientId() == null) {
            throw new ValidationException("Le patient est obligatoire");
        }
        if (situation.getTotalDesAchats() != null && situation.getTotalDesAchats() < 0) {
            throw new ValidationException("Le total des achats ne peut pas être négatif");
        }
    }
}