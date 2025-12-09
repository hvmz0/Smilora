package ma.dentalTech.service.modules.Patient.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.common.validation.Validators;
import ma.dentalTech.entities.Enums.CategorieAntecedent;
import ma.dentalTech.entities.Enums.RisqueEnum;
import ma.dentalTech.entities.Patient.Antecedent;
import ma.dentalTech.entities.Patient.Patient;
import ma.dentalTech.repository.modules.Patient.Api.AntecedentRepository;
import ma.dentalTech.repository.modules.Patient.Api.PatientRepository;
import ma.dentalTech.service.modules.Patient.api.AntecedentService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AntecedentServiceImpl implements AntecedentService {

    private final AntecedentRepository antecedentRepository;
    private final PatientRepository patientRepository;

    @Override
    public List<Antecedent> getAllAntecedents() throws ServiceException {
        try {
            return antecedentRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des antécédents", e);
        }
    }

    @Override
    public Optional<Antecedent> getAntecedentById(Long id) throws ServiceException {
        try {
            Antecedent antecedent = antecedentRepository.findById(id);
            return Optional.ofNullable(antecedent);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de l'antécédent", e);
        }
    }

    @Override
    public Antecedent createAntecedent(Antecedent antecedent) throws ServiceException, ValidationException {
        try {
            validateAntecedent(antecedent);
            antecedentRepository.create(antecedent);
            return antecedent;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de l'antécédent", e);
        }
    }

    @Override
    public Antecedent updateAntecedent(Antecedent antecedent) throws ServiceException, ValidationException {
        try {
            if (antecedent.getId() == null) {
                throw new ValidationException("L'ID de l'antécédent est requis");
            }
            validateAntecedent(antecedent);
            antecedentRepository.update(antecedent);
            return antecedent;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de l'antécédent", e);
        }
    }

    @Override
    public void deleteAntecedent(Long id) throws ServiceException {
        try {
            antecedentRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de l'antécédent", e);
        }
    }

    @Override
    public List<Antecedent> getAntecedentsByCategorie(String categorie) throws ServiceException {
        try {
            return antecedentRepository.findByCategorie(CategorieAntecedent.valueOf(categorie));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par catégorie", e);
        }
    }

    @Override
    public List<Antecedent> getAntecedentsByNiveauRisque(RisqueEnum niveau) throws ServiceException {
        try {
            return antecedentRepository.findByNiveauRisque(niveau);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche par niveau de risque", e);
        }
    }

    @Override
    public List<Antecedent> searchAntecedents(String keyword) throws ServiceException {
        try {
            return antecedentRepository.searchByNom(keyword);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche d'antécédents", e);
        }
    }

    @Override
    public long getTotalAntecedents() throws ServiceException {
        try {
            return antecedentRepository.count();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des antécédents", e);
        }
    }

    @Override
    public List<Patient> getPatientsWithAntecedent(Long antecedentId) throws ServiceException {
        try {
            return patientRepository.getPatientsByAntecedent(antecedentId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des patients", e);
        }
    }

    private void validateAntecedent(Antecedent antecedent) throws ValidationException {
        Validators.notBlank(antecedent.getNom(), "Le nom de l'antécédent");
        Validators.notBlank(antecedent.getCategorie(), "La catégorie");

        if (antecedent.getNiveauDeRisque() == null) {
            throw new ValidationException("Le niveau de risque est obligatoire");
        }
    }
}