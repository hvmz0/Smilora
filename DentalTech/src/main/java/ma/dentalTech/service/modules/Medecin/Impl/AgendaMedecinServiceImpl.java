package ma.dentalTech.service.modules.Medecin.Impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.entities.Medecin.AgendaMedecin;
import ma.dentalTech.repository.modules.Medecin.Api.AgendaMedecinRepository;
import ma.dentalTech.service.modules.Medecin.Api.AgendaMedecinService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AgendaMedecinServiceImpl implements AgendaMedecinService {

    private final AgendaMedecinRepository agendaRepository;

    @Override
    public List<AgendaMedecin> getAllAgendas() throws ServiceException {
        try {
            return agendaRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des agendas", e);
        }
    }

    @Override
    public Optional<AgendaMedecin> getAgendaById(Long id) throws ServiceException {
        try {
            AgendaMedecin agenda = agendaRepository.findById(id);
            return Optional.ofNullable(agenda);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de l'agenda avec l'ID: " + id, e);
        }
    }

    @Override
    public AgendaMedecin createAgenda(AgendaMedecin agenda) throws ServiceException, ValidationException {
        try {
            validateAgenda(agenda);
            agendaRepository.create(user);
            return agenda;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de l'agenda", e);
        }
    }

    @Override
    public AgendaMedecin updateAgenda(AgendaMedecin agenda) throws ServiceException, ValidationException {
        try {
            if (agenda.getId() == null) {
                throw new ValidationException("L'ID de l'agenda est requis pour la mise à jour");
            }
            validateAgenda(agenda);
            agendaRepository.update(agenda);
            return agenda;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de l'agenda", e);
        }
    }

    @Override
    public void deleteAgenda(Long id) throws ServiceException {
        try {
            AgendaMedecin agenda = agendaRepository.findById(id);
            if (agenda == null) {
                throw new ServiceException("Agenda non trouvé avec l'ID: " + id);
            }
            agendaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de l'agenda", e);
        }
    }

    @Override
    public Optional<Optional<AgendaMedecin>> getAgendaByMedecin(Long medecinId) throws ServiceException {
        try {
            Optional<AgendaMedecin> agenda = AgendaMedecinRepository.findByMedecinId(medecinId);
            return Optional.ofNullable(agenda);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de l'agenda du médecin", e);
        }
    }

    @Override
    public List<AgendaMedecin> searchAgendas(String keyword) throws ServiceException {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return getAllAgendas();
            }

            try {
                Long medecinId = Long.valueOf(keyword);
                Optional<AgendaMedecin> agenda = AgendaMedecinRepository.findByMedecinId(medecinId);
                return agenda.map(List::of).orElse(List.of());
            } catch (NumberFormatException e) {
                return List.of();
            }

        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche d'agendas", e);
        }
    }

    private void validateAgenda(AgendaMedecin agenda) throws ValidationException {
        if (agenda.getMedecinId() == null) {
            throw new ValidationException("L'ID du médecin est obligatoire");
        }
    }
}
