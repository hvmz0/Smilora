package ma.dentalTech.service.modules.DossierMedicale.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.entities.DossierMedicale.DossierMedicale;
import ma.dentalTech.repository.modules.DossierMedicale.Api.DossierMedicaleRepository;
import ma.dentalTech.service.modules.DossierMedicale.Api.DossierMedicaleService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class DossierMedicaleServiceImpl implements DossierMedicaleService {

    private final DossierMedicaleRepository dossierRepository;

    @Override
    public List<DossierMedicale> getAllDossiers() throws ServiceException {
        try {
            return dossierRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des dossiers médicaux", e);
        }
    }

    @Override
    public Optional<DossierMedicale> getDossierById(Long id) throws ServiceException {
        try {
            DossierMedicale dossier = dossierRepository.findById(id);
            return Optional.ofNullable(dossier);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du dossier avec l'ID: " + id, e);
        }
    }

    @Override
    public DossierMedicale createDossier(DossierMedicale dossier) throws ServiceException, ValidationException {
        try {
            validateDossier(dossier);
            dossierRepository.create(user);
            return dossier;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du dossier médical", e);
        }
    }

    @Override
    public DossierMedicale updateDossier(DossierMedicale dossier) throws ServiceException, ValidationException {
        try {
            if (dossier.getId() == null) {
                throw new ValidationException("L'ID du dossier est requis pour la mise à jour");
            }
            validateDossier(dossier);
            dossierRepository.update(dossier);
            return dossier;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du dossier médical", e);
        }
    }

    @Override
    public void deleteDossier(Long id) throws ServiceException {
        try {
            DossierMedicale dossier = dossierRepository.findById(id);
            if (dossier == null) {
                throw new ServiceException("Dossier non trouvé avec l'ID: " + id);
            }
            dossierRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du dossier médical", e);
        }
    }

    @Override
    public Optional<Optional<DossierMedicale>> getDossierByPatient(Long patientId) throws ServiceException {
        try {
            Optional<DossierMedicale> dossier = DossierMedicaleRepository.findByPatientId(patientId);
            return Optional.ofNullable(dossier);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du dossier du patient", e);
        }
    }

    @Override
    public List<DossierMedicale> searchDossiers(String keyword) throws ServiceException {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return getAllDossiers();
            }

            // Convertir le keyword String en Long
            try {
                Long patientId = Long.parseLong(keyword);
                Optional<DossierMedicale> dossier = DossierMedicaleRepository.findByPatientId(patientId);
                return dossier.map(List::of).orElse(List.of());
            } catch (NumberFormatException e) {
                // Si le keyword n'est pas un nombre, retourner une liste vide
                // Ou implémenter une autre logique de recherche
                return List.of();
            }

        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de dossiers", e);
        }
    }

    @Override
    public long getTotalDossiers() throws ServiceException {
        try {
            return dossierRepository.count();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des dossiers", e);
        }
    }

    private void validateDossier(DossierMedicale dossier) throws ValidationException {
        if (dossier.getPatientId() == null) {
            throw new ValidationException("L'ID du patient est obligatoire");
        }
    }
}
