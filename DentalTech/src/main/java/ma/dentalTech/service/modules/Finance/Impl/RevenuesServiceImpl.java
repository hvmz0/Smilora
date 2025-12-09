package ma.dentalTech.service.modules.Finance.Impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.entities.Finance.Revenues;
import ma.dentalTech.repository.modules.Finance.Api.RevenuesRepository;
import ma.dentalTech.service.modules.Finance.Api.RevenuesService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class RevenuesServiceImpl implements RevenuesService {

    private final RevenuesRepository revenuesRepository;

    @Override
    public List<Revenues> getAllRevenues() throws ServiceException {
        try {
            return revenuesRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des revenus", e);
        }
    }

    @Override
    public Optional<Revenues> getRevenuesById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(revenuesRepository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du revenu", e);
        }
    }

    @Override
    public Revenues createRevenues(Revenues revenues) throws ServiceException, ValidationException {
        try {
            validateRevenues(revenues);
            revenuesRepository.create(user);
            return revenues;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du revenu", e);
        }
    }

    @Override
    public Revenues updateRevenues(Revenues revenues) throws ServiceException, ValidationException {
        try {
            if (revenues.getId() == null) {
                throw new ValidationException("L'ID du revenu est requis");
            }
            validateRevenues(revenues);
            revenuesRepository.update(revenues);
            return revenues;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du revenu", e);
        }
    }

    @Override
    public void deleteRevenues(Long id) throws ServiceException {
        try {
            revenuesRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du revenu", e);
        }
    }

    @Override
    public List<Revenues> getRevenuesByDate(LocalDate date) throws ServiceException {
        try {
            return revenuesRepository.findByDate(date);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des revenus par date", e);
        }
    }

    @Override
    public List<Revenues> getRevenuesByDateRange(LocalDateTime debut, LocalDateTime fin) throws ServiceException {
        try {
            return revenuesRepository.findByDateRange(debut, fin);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des revenus par plage de dates", e);
        }
    }

    @Override
    public List<Revenues> getRevenuesByStatistiqueId(Long statistiqueId) throws ServiceException {
        try {
            return revenuesRepository.findByStatistiqueId(statistiqueId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des revenus par statistique", e);
        }
    }

    @Override
    public List<Revenues> searchRevenues(String keyword) throws ServiceException {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return getAllRevenues();
            }
            return revenuesRepository.searchByTitre(keyword);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de revenus", e);
        }
    }

    @Override
    public Double calculateTotalRevenues(LocalDateTime debut, LocalDateTime fin) throws ServiceException {
        try {
            return revenuesRepository.calculateTotal(debut, fin);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du calcul des revenus totaux", e);
        }
    }

    @Override
    public long countRevenues() throws ServiceException {
        try {
            return revenuesRepository.count();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des revenus", e);
        }
    }

    private void validateRevenues(Revenues revenues) throws ValidationException {
        if (revenues.getMontant() == null || revenues.getMontant() < 0) {
            throw new ValidationException("Le montant du revenu doit être positif");
        }
        if (revenues.getTitre() == null || revenues.getTitre().trim().isEmpty()) {
            throw new ValidationException("Le titre du revenu est obligatoire");
        }
    }
}
