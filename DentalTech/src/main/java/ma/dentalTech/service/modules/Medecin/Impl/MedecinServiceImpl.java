package ma.dentalTech.service.modules.Medecin.Impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.common.validation.Validators;
import ma.dentalTech.entities.Medecin.Medecin;
import ma.dentalTech.repository.modules.Medecin.Api.MedecinRepository;
import ma.dentalTech.service.modules.Medecin.Api.MedecinService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MedecinServiceImpl implements MedecinService {

    private final MedecinRepository medecinRepository;

    @Override
    public List<Medecin> getAllMedecins() throws ServiceException {
        try {
            return medecinRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des médecins", e);
        }
    }

    @Override
    public Optional<Medecin> getMedecinById(Long id) throws ServiceException {
        try {
            Medecin medecin = medecinRepository.findById(id);
            return Optional.ofNullable(medecin);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du médecin avec l'ID: " + id, e);
        }
    }

    @Override
    public Medecin createMedecin(Medecin medecin) throws ServiceException, ValidationException {
        try {
            validateMedecin(medecin);
            medecinRepository.create(user);
            return medecin;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du médecin", e);
        }
    }

    @Override
    public Medecin updateMedecin(Medecin medecin) throws ServiceException, ValidationException {
        try {
            if (medecin.getId() == null) {
                throw new ValidationException("L'ID du médecin est requis pour la mise à jour");
            }
            validateMedecin(medecin);
            medecinRepository.update(medecin);
            return medecin;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du médecin", e);
        }
    }

    @Override
    public void deleteMedecin(Long id) throws ServiceException {
        try {
            Medecin medecin = medecinRepository.findById(id);
            if (medecin == null) {
                throw new ServiceException("Médecin non trouvé avec l'ID: " + id);
            }
            medecinRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du médecin", e);
        }
    }

    @Override
    public Optional<Medecin> getMedecinByUserId(Long userId) throws ServiceException {
        try {
            return medecinRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du médecin par utilisateur", e);
        }
    }

    @Override
    public List<Medecin> getMedecinsBySpecialite(String specialite) throws ServiceException {
        try {
            return medecinRepository.findBySpecialite(specialite);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des médecins par spécialité", e);
        }
    }

    @Override
    public List<Medecin> searchMedecins(String keyword) throws ServiceException {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return getAllMedecins();
            }
            return medecinRepository.searchByNom(keyword);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de médecins", e);
        }
    }

    @Override
    public Long getMedecinAgendaId(Long medecinId) throws ServiceException {
        try {
            return medecinRepository.getAgendaId(medecinId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de l'agenda du médecin", e);
        }
    }

    @Override
    public long getTotalMedecins() throws ServiceException {
        try {
            return medecinRepository.count();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des médecins", e);
        }
    }

    private void validateMedecin(Medecin medecin) throws ValidationException {
        Validators.notBlank(String.valueOf(medecin.getId()), "L'id");
        Validators.notBlank(medecin.getSpecialite(), "La spécialité");
        if (medecin.getId() == null) {
            throw new ValidationException("L'ID utilisateur est obligatoire");
        }
    }
}
