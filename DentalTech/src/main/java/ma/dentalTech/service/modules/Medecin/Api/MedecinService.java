package ma.dentalTech.service.modules.Medecin.Api;

import ma.dentalTech.entities.Medecin.Medecin;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public interface MedecinService {

    // CRUD Operations
    List<Medecin> getAllMedecins() throws ServiceException;
    Optional<Medecin> getMedecinById(Long id) throws ServiceException;
    Medecin createMedecin(Medecin medecin) throws ServiceException, ValidationException;
    Medecin updateMedecin(Medecin medecin) throws ServiceException, ValidationException;
    void deleteMedecin(Long id) throws ServiceException;

    // Recherche
    Optional<Medecin> getMedecinByUserId(Long userId) throws ServiceException;
    List<Medecin> getMedecinsBySpecialite(String specialite) throws ServiceException;
    List<Medecin> searchMedecins(String keyword) throws ServiceException;

    // Relations
    Long getMedecinAgendaId(Long medecinId) throws ServiceException;

    // Comptage
    long getTotalMedecins() throws ServiceException;
}
