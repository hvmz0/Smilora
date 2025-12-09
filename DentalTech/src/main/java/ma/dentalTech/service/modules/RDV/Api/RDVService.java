package ma.dentalTech.service.modules.RDV.Api;

import ma.dentalTech.entities.RDV.RDV;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RDVService {

    // CRUD Operations
    List<RDV> getAllRDVs() throws ServiceException;
    Optional<RDV> getRDVById(Long id) throws ServiceException;
    RDV createRDV(RDV rdv) throws ServiceException, ValidationException;
    RDV updateRDV(RDV rdv) throws ServiceException, ValidationException;
    void deleteRDV(Long id) throws ServiceException;

    // Recherche par entité
    List<RDV> getRDVsByPatient(Long patientId) throws ServiceException;
    List<RDV> getRDVsByMedecin(Long medecinId) throws ServiceException;

    // Recherche par date
    List<RDV> getRDVsByDate(LocalDate date) throws ServiceException;
    List<RDV> getRDVsByDateRange(LocalDate debut, LocalDate fin) throws ServiceException;

    // Recherche par statut
    List<RDV> getRDVsByStatut(StatutEnum statut) throws ServiceException;

    // Gestion planning
    List<RDV> getUpcomingRDVs(Long medecinId) throws ServiceException;
    boolean isSlotAvailable(Long medecinId, LocalDateTime heure) throws ServiceException;

    // Comptage
    long countRDVsByDate(LocalDate date) throws ServiceException;
}
