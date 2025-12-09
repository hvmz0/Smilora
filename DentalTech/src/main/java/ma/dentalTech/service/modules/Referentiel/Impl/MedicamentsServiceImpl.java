package ma.dentalTech.service.modules.Referentiel.Impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.common.validation.Validators;
import ma.dentalTech.entities.Referentiel.Medicaments;
import ma.dentalTech.repository.modules.Referentiel.Api.MedicamentsRepository;
import ma.dentalTech.service.modules.Referentiel.Api.MedicamentsService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MedicamentsServiceImpl implements MedicamentsService {

    private final MedicamentsRepository medicamentsRepository;

    @Override
    public List<Medicaments> getAllMedicaments() throws ServiceException {
        try {
            return medicamentsRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des médicaments", e);
        }
    }

    @Override
    public Optional<Medicaments> getMedicamentById(Long id) throws ServiceException {
        try {
            Medicaments medicament = medicamentsRepository.findById(id);
            return Optional.ofNullable(medicament);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du médicament avec l'ID: " + id, e);
        }
    }

    @Override
    public Medicaments createMedicament(Medicaments medicament) throws ServiceException, ValidationException {
        try {
            validateMedicament(medicament);
            medicamentsRepository.create(user);
            return medicament;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du médicament", e);
        }
    }

    @Override
    public Medicaments updateMedicament(Medicaments medicament) throws ServiceException, ValidationException {
        try {
            if (medicament.getId() == null) {
                throw new ValidationException("L'ID du médicament est requis pour la mise à jour");
            }
            validateMedicament(medicament);
            medicamentsRepository.update(medicament);
            return medicament;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du médicament", e);
        }
    }

    @Override
    public void deleteMedicament(Long id) throws ServiceException {
        try {
            Medicaments medicament = medicamentsRepository.findById(id);
            if (medicament == null) {
                throw new ServiceException("Médicament non trouvé avec l'ID: " + id);
            }
            medicamentsRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du médicament", e);
        }
    }

    @Override
    public List<Medicaments> searchMedicaments(String keyword) throws ServiceException {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return getAllMedicaments();
            }
            return medicamentsRepository.searchByNom(keyword);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de médicaments", e);
        }
    }

    @Override
    public long getTotalMedicaments() throws ServiceException {
        try {
            return medicamentsRepository.count();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des médicaments", e);
        }
    }

    private void validateMedicament(Medicaments medicament) throws ValidationException {
        Validators.notBlank(medicament.getNom(), "Le libellé");
    }
}
