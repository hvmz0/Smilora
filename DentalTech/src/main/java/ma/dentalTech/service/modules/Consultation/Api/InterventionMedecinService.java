package ma.dentalTech.service.modules.Consultation.Api;

import ma.dentalTech.entities.Consultation.InterventionMedecin;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public interface InterventionMedecinService {

    // CRUD Operations
    List<InterventionMedecin> getAllInterventions() throws ServiceException;
    Optional<InterventionMedecin> getInterventionById(Long id) throws ServiceException;
    InterventionMedecin createIntervention(InterventionMedecin intervention) throws ServiceException, ValidationException;
    InterventionMedecin updateIntervention(InterventionMedecin intervention) throws ServiceException, ValidationException;
    void deleteIntervention(Long id) throws ServiceException;

    // Recherche
    List<InterventionMedecin> getInterventionsByConsultation(Long consultationId) throws ServiceException;
    List<InterventionMedecin> getInterventionsByMedecin(Long medecinId) throws ServiceException;
}
