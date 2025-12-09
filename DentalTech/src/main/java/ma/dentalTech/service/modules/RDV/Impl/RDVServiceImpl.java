package ma.dentalTech.service.modules.RDV.Impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.entities.RDV.RDV;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.repository.modules.RDV.Api.RDVRepository;
import ma.dentalTech.service.modules.RDV.Api.RDVService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class RDVServiceImpl implements RDVService {

    private final RDVRepository rdvRepository;

    @Override
    public List<RDV> getAllRDVs() throws ServiceException {
        try {
            return rdvRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des RDVs", e);
        }
    }

    @Override
    public Optional<RDV> getRDVById(Long id) throws ServiceException {
        try {
            RDV rdv = rdvRepository.findById(id);
            return Optional.ofNullable(rdv);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du RDV avec l'ID: " + id, e);
        }
    }

    @Override
    public RDV createRDV(RDV rdv) throws ServiceException, ValidationException {
        try {
            validateRDV(rdv);
            // Vérifier la disponibilité du créneau
            if (!rdvRepository.isSlotAvailable(rdv.getMedecinId(), LocalDateTime.from(rdv.getDate()))) {
                throw new ValidationException("Ce créneau n'est pas disponible");
            }
            rdvRepository.create(user);
            return rdv;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du RDV", e);
        }
    }

    @Override
    public RDV updateRDV(RDV rdv) throws ServiceException, ValidationException {
        try {
            if (rdv.getId() == null) {
                throw new ValidationException("L'ID du RDV est requis pour la mise à jour");
            }
            validateRDV(rdv);
            rdvRepository.update(rdv);
            return rdv;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du RDV", e);
        }
    }

    @Override
    public void deleteRDV(Long id) throws ServiceException {
        try {
            RDV rdv = rdvRepository.findById(id);
            if (rdv == null) {
                throw new ServiceException("RDV non trouvé avec l'ID: " + id);
            }
            rdvRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du RDV", e);
        }
    }

    @Override
    public List<RDV> getRDVsByPatient(Long patientId) throws ServiceException {
        try {
            return rdvRepository.findByPatientId(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des RDVs du patient", e);
        }
    }

    @Override
    public List<RDV> getRDVsByMedecin(Long medecinId) throws ServiceException {
        try {
            return rdvRepository.findByMedecinId(medecinId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des RDVs du médecin", e);
        }
    }

    @Override
    public List<RDV> getRDVsByDate(LocalDate date) throws ServiceException {
        try {
            return rdvRepository.findByDate(date);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des RDVs par date", e);
        }
    }

    @Override
    public List<RDV> getRDVsByDateRange(LocalDate debut, LocalDate fin) throws ServiceException {
        try {
            return rdvRepository.findByDateRange(debut, fin);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des RDVs par plage de date", e);
        }
    }

    @Override
    public List<RDV> getRDVsByStatut(StatutEnum statut) throws ServiceException {
        try {
            return rdvRepository.findByStatut(statut);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des RDVs par statut", e);
        }
    }

    @Override
    public List<RDV> getUpcomingRDVs(Long medecinId) throws ServiceException {
        try {
            return rdvRepository.findUpcoming(medecinId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des RDVs à venir", e);
        }
    }

    @Override
    public boolean isSlotAvailable(Long medecinId, LocalDateTime heure) throws ServiceException {
        try {
            return rdvRepository.isSlotAvailable(medecinId, heure);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la vérification de la disponibilité du créneau", e);
        }
    }

    @Override
    public long countRDVsByDate(LocalDate date) throws ServiceException {
        try {
            return rdvRepository.countByDate(date);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des RDVs par date", e);
        }
    }

    private void validateRDV(RDV rdv) throws ValidationException {
        if (rdv.getPatientId() == null) {
            throw new ValidationException("L'ID du patient est obligatoire");
        }
        if (rdv.getMedecinId() == null) {
            throw new ValidationException("L'ID du médecin est obligatoire");
        }
        if (rdv.getDate() == null) {
            throw new ValidationException("La date du RDV est obligatoire");
        }
    }
}
