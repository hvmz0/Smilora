package ma.dentalTech.service.modules.Ordonnance.Impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.entities.Ordonnance.Ordonnance;
import ma.dentalTech.repository.modules.Ordonnance.Api.OrdonnanceRepository;
import ma.dentalTech.service.modules.Ordonnance.Api.OrdonnanceService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class OrdonnanceServiceImpl implements OrdonnanceService {

    private final OrdonnanceRepository ordonnanceRepository;

    @Override
    public List<Ordonnance> getAllOrdonnances() throws ServiceException {
        try {
            return ordonnanceRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des ordonnances", e);
        }
    }

    @Override
    public Optional<Ordonnance> getOrdonnanceById(Long id) throws ServiceException {
        try {
            Ordonnance ordonnance = ordonnanceRepository.findById(id);
            return Optional.ofNullable(ordonnance);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de l'ordonnance avec l'ID: " + id, e);
        }
    }

    @Override
    public Ordonnance createOrdonnance(Ordonnance ordonnance) throws ServiceException, ValidationException {
        try {
            validateOrdonnance(ordonnance);
            ordonnanceRepository.create(user);
            return ordonnance;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de l'ordonnance", e);
        }
    }

    @Override
    public Ordonnance updateOrdonnance(Ordonnance ordonnance) throws ServiceException, ValidationException {
        try {
            if (ordonnance.getId() == null) {
                throw new ValidationException("L'ID de l'ordonnance est requis pour la mise à jour");
            }
            validateOrdonnance(ordonnance);
            ordonnanceRepository.update(ordonnance);
            return ordonnance;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de l'ordonnance", e);
        }
    }

    @Override
    public void deleteOrdonnance(Long id) throws ServiceException {
        try {
            Ordonnance ordonnance = ordonnanceRepository.findById(id);
            if (ordonnance == null) {
                throw new ServiceException("Ordonnance non trouvée avec l'ID: " + id);
            }
            ordonnanceRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de l'ordonnance", e);
        }
    }

    @Override
    public List<Ordonnance> getOrdonnancesByPatient(Long patientId) throws ServiceException {
        try {
            return ordonnanceRepository.findByPatientId(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des ordonnances du patient", e);
        }
    }

    @Override
    public List<Ordonnance> getOrdonnancesByMedecin(Long medecinId) throws ServiceException {
        try {
            return ordonnanceRepository.findByPatientId(medecinId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des ordonnances du médecin", e);
        }
    }

    @Override
    public List<Ordonnance> getOrdonnancesByConsultation(Long consultationId) throws ServiceException {
        try {
            List<Ordonnance> ordonnances = new java.util.ArrayList<>();
            ordonnanceRepository.findByConsultationId(consultationId).ifPresent(ordonnances::add);
            return ordonnances;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des ordonnances de la consultation", e);
        }
    }

    @Override
    public List<Ordonnance> getOrdonnancesByDate(LocalDate date) throws ServiceException {
        try {
            return ordonnanceRepository.findByDate(date);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des ordonnances par date", e);
        }
    }

    @Override
    public long countOrdonnancesByPatient(Long patientId) throws ServiceException {
        try {
            return ordonnanceRepository.countByPatientId(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des ordonnances du patient", e);
        }
    }

    private void validateOrdonnance(Ordonnance ordonnance) throws ValidationException {
        if (ordonnance.getPatientId() == null) {
            throw new ValidationException("L'ID du patient est obligatoire");
        }
        if (ordonnance.getMedecinId() == null) {
            throw new ValidationException("L'ID du médecin est obligatoire");
        }
        if (ordonnance.getDate() == null) {
            throw new ValidationException("La date de l'ordonnance est obligatoire");
        }
    }
}
