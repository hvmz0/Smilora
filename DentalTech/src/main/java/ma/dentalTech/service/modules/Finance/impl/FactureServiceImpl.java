package ma.dentalTech.service.modules.Finance.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.entities.Finance.Facture;
import ma.dentalTech.repository.modules.Finance.Api.FactureRepository;
import ma.dentalTech.service.modules.Finance.api.FactureService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class FactureServiceImpl implements FactureService {

    private final FactureRepository factureRepository;

    @Override
    public List<Facture> getAllFactures() throws ServiceException {
        try {
            return factureRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des factures", e);
        }
    }

    @Override
    public Optional<Facture> getFactureById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(factureRepository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la facture", e);
        }
    }

    @Override
    public Facture createFacture(Facture facture) throws ServiceException, ValidationException {
        try {
            validateFacture(facture);

            // Calculer le reste automatiquement
            if (facture.getReste() == null) {
                facture.setReste(facture.getTotalFacture() - (facture.getTotalPaye() != null ? facture.getTotalPaye() : 0.0));
            }

            // Déterminer le statut automatiquement
            if (facture.getStatut() == null) {
                if (facture.getReste() <= 0) {
                    facture.setStatut(StatutEnum.PAYE);
                } else if (facture.getTotalPaye() > 0) {
                    facture.setStatut(StatutEnum.EN_COURS);
                } else {
                    facture.setStatut(StatutEnum.NON_PAYE);
                }
            }

            if (facture.getDateFacture() == null) {
                facture.setDateFacture(LocalDateTime.now());
            }

            factureRepository.create(facture);
            return facture;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de la facture", e);
        }
    }

    @Override
    public Facture updateFacture(Facture facture) throws ServiceException, ValidationException {
        try {
            if (facture.getId() == null) {
                throw new ValidationException("L'ID de la facture est requis");
            }
            validateFacture(facture);
            factureRepository.update(facture);
            return facture;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de la facture", e);
        }
    }

    @Override
    public void deleteFacture(Long id) throws ServiceException {
        try {
            factureRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de la facture", e);
        }
    }

    @Override
    public List<Facture> getFacturesByPatient(Long patientId) throws ServiceException {
        try {
            return factureRepository.findByPatientId(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des factures du patient", e);
        }
    }

    @Override
    public Facture getFactureByConsultation(Long consultationId) throws ServiceException {
        try {
            return factureRepository.findByConsultationId(consultationId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la facture", e);
        }
    }

    @Override
    public List<Facture> getFacturesUnpaid() throws ServiceException {
        try {
            return factureRepository.findUnpaid();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des factures impayées", e);
        }
    }

    @Override
    public List<Facture> getFacturesByDate(LocalDate date) throws ServiceException {
        try {
            return factureRepository.findByDate(date);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des factures par date", e);
        }
    }

    @Override
    public List<Facture> getFacturesByDateRange(LocalDateTime debut, LocalDateTime fin) throws ServiceException {
        try {
            return factureRepository.findByDateRange(debut, fin);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des factures par période", e);
        }
    }

    @Override
    public void markAsPaid(Long factureId) throws ServiceException {
        try {
            Facture facture = factureRepository.findById(factureId);
            if (facture == null) {
                throw new ServiceException("Facture non trouvée");
            }
            facture.setTotalPaye(facture.getTotalFacture());
            facture.setReste(0.0);
            facture.setStatut(StatutEnum.PAYE);
            factureRepository.update(facture);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du marquage de la facture comme payée", e);
        }
    }

    @Override
    public void addPayment(Long factureId, Double montant) throws ServiceException, ValidationException {
        try {
            if (montant == null || montant <= 0) {
                throw new ValidationException("Le montant du paiement doit être positif");
            }

            Facture facture = factureRepository.findById(factureId);
            if (facture == null) {
                throw new ServiceException("Facture non trouvée");
            }

            double nouveauTotalPaye = (facture.getTotalPaye() != null ? facture.getTotalPaye() : 0.0) + montant;
            double nouveauReste = facture.getTotalFacture() - nouveauTotalPaye;

            if (nouveauReste < 0) {
                throw new ValidationException("Le montant payé dépasse le total de la facture");
            }

            facture.setTotalPaye(nouveauTotalPaye);
            facture.setReste(nouveauReste);

            if (nouveauReste == 0) {
                facture.setStatut(StatutEnum.PAYE);
            } else {
                facture.setStatut(StatutEnum.EN_COURS);
            }

            factureRepository.update(facture);
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de l'ajout du paiement", e);
        }
    }

    @Override
    public Double calculateTotalByPatient(Long patientId) throws ServiceException {
        try {
            return factureRepository.calculateTotalByPatient(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du calcul du total", e);
        }
    }

    @Override
    public Double calculateUnpaidByPatient(Long patientId) throws ServiceException {
        try {
            return factureRepository.calculateUnpaidByPatient(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du calcul des impayés", e);
        }
    }

    @Override
    public Double calculateTotalUnpaid() throws ServiceException {
        try {
            return factureRepository.calculateTotalUnpaid();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du calcul du total impayé", e);
        }
    }

    private void validateFacture(Facture facture) throws ValidationException {
        if (facture.getTotalFacture() == null || facture.getTotalFacture() < 0) {
            throw new ValidationException("Le montant total doit être positif");
        }
        if (facture.getPatientId() == null) {
            throw new ValidationException("Le patient est obligatoire");
        }
    }
}