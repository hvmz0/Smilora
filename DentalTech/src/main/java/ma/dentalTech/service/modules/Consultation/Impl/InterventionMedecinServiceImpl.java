package ma.dentalTech.service.modules.Consultation.Impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.entities.Consultation.InterventionMedecin;
import ma.dentalTech.repository.modules.Consultation.Api.InterventionMedecinRepository;
import ma.dentalTech.service.modules.Consultation.Api.InterventionMedecinService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class InterventionMedecinServiceImpl implements InterventionMedecinService {

    private final InterventionMedecinRepository interventionRepository;

    @Override
    public List<InterventionMedecin> getAllInterventions() throws ServiceException {
        try {
            return interventionRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des interventions", e);
        }
    }

    @Override
    public Optional<InterventionMedecin> getInterventionById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(interventionRepository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de l'intervention avec l'ID : " + id, e);
        }
    }

    @Override
    public InterventionMedecin createIntervention(InterventionMedecin intervention)
            throws ServiceException, ValidationException {
        try {
            validateIntervention(intervention);
            interventionRepository.create(user);
            return intervention;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de l'intervention", e);
        }
    }

    @Override
    public InterventionMedecin updateIntervention(InterventionMedecin intervention)
            throws ServiceException, ValidationException {
        try {
            if (intervention.getId() == null) {
                throw new ValidationException("L'ID de l'intervention est requis pour la mise à jour");
            }

            validateIntervention(intervention);
            interventionRepository.update(intervention);
            return intervention;

        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de l'intervention", e);
        }
    }

    @Override
    public void deleteIntervention(Long id) throws ServiceException {
        try {
            InterventionMedecin intervention = interventionRepository.findById(id);

            if (intervention == null) {
                throw new ServiceException("Intervention non trouvée avec l'ID : " + id);
            }

            interventionRepository.deleteById(id);

        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de l'intervention", e);
        }
    }

    @Override
    public List<InterventionMedecin> getInterventionsByConsultation(Long consultationId) throws ServiceException {
        try {
            return interventionRepository.findByConsultationId(consultationId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des interventions de la consultation", e);
        }
    }

    @Override
    public List<InterventionMedecin> getInterventionsByMedecin(Long medecinId) throws ServiceException {
        try {
            return interventionRepository.findByActeId(medecinId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des interventions du médecin", e);
        }
    }

    private void validateIntervention(InterventionMedecin intervention) throws ValidationException {
        if (intervention.getConsultationId() == null) {
            throw new ValidationException("L'ID de la consultation est obligatoire");
        }
        if (intervention.getMedecinId() == null) {
            throw new ValidationException("L'ID du médecin est obligatoire");
        }
    }
}
