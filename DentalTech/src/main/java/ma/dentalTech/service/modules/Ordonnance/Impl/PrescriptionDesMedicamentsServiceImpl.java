package ma.dentalTech.service.modules.Ordonnance.Impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.entities.Ordonnance.PrescriptionDesMedicaments;
import ma.dentalTech.repository.modules.Ordonnance.Api.PrescriptionDesMedicamentsRepository;
import ma.dentalTech.service.modules.Ordonnance.Api.PrescriptionDesMedicamentsService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PrescriptionDesMedicamentsServiceImpl implements PrescriptionDesMedicamentsService {

    private final PrescriptionDesMedicamentsRepository prescriptionRepository;

    @Override
    public List<PrescriptionDesMedicaments> getAllPrescriptions() throws ServiceException {
        try {
            return prescriptionRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des prescriptions", e);
        }
    }

    @Override
    public Optional<PrescriptionDesMedicaments> getPrescriptionById(Long id) throws ServiceException {
        try {
            PrescriptionDesMedicaments prescription = prescriptionRepository.findById(id);
            return Optional.ofNullable(prescription);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la prescription avec l'ID: " + id, e);
        }
    }

    @Override
    public PrescriptionDesMedicaments createPrescription(PrescriptionDesMedicaments prescription) throws ServiceException, ValidationException {
        try {
            validatePrescription(prescription);
            prescriptionRepository.create(user);
            return prescription;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de la prescription", e);
        }
    }

    @Override
    public PrescriptionDesMedicaments updatePrescription(PrescriptionDesMedicaments prescription) throws ServiceException, ValidationException {
        try {
            if (prescription.getId() == null) {
                throw new ValidationException("L'ID de la prescription est requis pour la mise à jour");
            }
            validatePrescription(prescription);
            prescriptionRepository.update(prescription);
            return prescription;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de la prescription", e);
        }
    }

    @Override
    public void deletePrescription(Long id) throws ServiceException {
        try {
            PrescriptionDesMedicaments prescription = prescriptionRepository.findById(id);
            if (prescription == null) {
                throw new ServiceException("Prescription non trouvée avec l'ID: " + id);
            }
            prescriptionRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de la prescription", e);
        }
    }

    @Override
    public List<PrescriptionDesMedicaments> getPrescriptionsByOrdonnance(Long ordonnanceId) throws ServiceException {
        try {
            return prescriptionRepository.findByOrdonnanceId(ordonnanceId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des prescriptions de l'ordonnance", e);
        }
    }

    @Override
    public List<PrescriptionDesMedicaments> getPrescriptionsByMedicament(Long medicamentId) throws ServiceException {
        try {
            return prescriptionRepository.findByMedicamentId(medicamentId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des prescriptions du médicament", e);
        }
    }

    @Override
    public long countPrescriptionsByOrdonnance(Long ordonnanceId) throws ServiceException {
        try {
            return prescriptionRepository.countByOrdonnanceId(ordonnanceId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des prescriptions", e);
        }
    }

    private void validatePrescription(PrescriptionDesMedicaments prescription) throws ValidationException {
        if (prescription.getOrdonnanceId() == null) {
            throw new ValidationException("L'ID de l'ordonnance est obligatoire");
        }
        if (prescription.getMedicamentId() == null) {
            throw new ValidationException("L'ID du médicament est obligatoire");
        }
    }
}
