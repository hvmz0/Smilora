package ma.dentalTech.service.modules.Finance.Impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.entities.Finance.Statistiques;
import ma.dentalTech.repository.modules.Finance.Api.StatistiquesRepository;
import ma.dentalTech.service.modules.Finance.Api.StatistiquesService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class StatistiquesServiceImpl implements StatistiquesService {

    private final StatistiquesRepository statistiquesRepository;

    @Override
    public List<Statistiques> getAllStatistiques() throws ServiceException {
        try {
            return statistiquesRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des statistiques", e);
        }
    }

    @Override
    public Optional<Statistiques> getStatistiquesById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(statistiquesRepository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la statistique", e);
        }
    }

    @Override
    public Statistiques createStatistiques(Statistiques statistiques) throws ServiceException, ValidationException {
        try {
            validateStatistiques(statistiques);
            statistiquesRepository.create(user);
            return statistiques;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de la statistique", e);
        }
    }

    @Override
    public Statistiques updateStatistiques(Statistiques statistiques) throws ServiceException, ValidationException {
        try {
            if (statistiques.getId() == null) {
                throw new ValidationException("L'ID de la statistique est requis");
            }
            validateStatistiques(statistiques);
            statistiquesRepository.update(statistiques);
            return statistiques;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de la statistique", e);
        }
    }

    @Override
    public void deleteStatistiques(Long id) throws ServiceException {
        try {
            statistiquesRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de la statistique", e);
        }
    }

    @Override
    public Statistiques getStatistiquesByDate(LocalDate date) throws ServiceException {
        try {
            return statistiquesRepository.findByDate(date);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des statistiques par date", e);
        }
    }

    @Override
    public List<Statistiques> getStatistiquesByDateRange(LocalDate debut, LocalDate fin) throws ServiceException {
        try {
            return statistiquesRepository.findByDateRange(debut, fin);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des statistiques par période", e);
        }
    }

    @Override
    public Double calculateAverageDailyIncome() throws ServiceException {
        try {
            return statistiquesRepository.calculateAverageDailyIncome();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du calcul de la moyenne des revenus quotidiens", e);
        }
    }

    @Override
    public Double calculateAverageDailyExpenses() throws ServiceException {
        try {
            return statistiquesRepository.calculateAverageDailyExpenses();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du calcul de la moyenne des dépenses quotidiennes", e);
        }
    }

    private void validateStatistiques(Statistiques statistiques) throws ValidationException {
        if (statistiques.getDate() == null) {
            throw new ValidationException("La date est obligatoire");
        }
    }
}
