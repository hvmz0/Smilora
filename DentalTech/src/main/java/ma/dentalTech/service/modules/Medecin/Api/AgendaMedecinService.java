package ma.dentalTech.service.modules.Medecin.Api;

import ma.dentalTech.entities.Medecin.AgendaMedecin;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public interface AgendaMedecinService {

    // CRUD Operations
    List<AgendaMedecin> getAllAgendas() throws ServiceException;
    Optional<AgendaMedecin> getAgendaById(Long id) throws ServiceException;
    AgendaMedecin createAgenda(AgendaMedecin agenda) throws ServiceException, ValidationException;
    AgendaMedecin updateAgenda(AgendaMedecin agenda) throws ServiceException, ValidationException;
    void deleteAgenda(Long id) throws ServiceException;

    // Recherche
    Optional<Optional<AgendaMedecin>> getAgendaByMedecin(Long medecinId) throws ServiceException;
    List<AgendaMedecin> searchAgendas(String keyword) throws ServiceException;
}
