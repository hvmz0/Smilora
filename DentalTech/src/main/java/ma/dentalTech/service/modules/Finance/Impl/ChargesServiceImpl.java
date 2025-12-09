package ma.dentalTech.service.modules.Finance.Impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.entities.Finance.Charges;
import ma.dentalTech.repository.modules.Finance.Api.ChargesRepository;
import ma.dentalTech.service.modules.Finance.Api.ChargesService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ChargesServiceImpl implements ChargesService {

    private final ChargesRepository chargesRepository;

    @Override
    public List<Charges> getAllCharges() throws ServiceException {
        try {
            return chargesRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des charges", e);
        }
    }

    @Override
    public Optional<Charges> getChargesById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(chargesRepository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la charge", e);
        }
    }

    @Override
    public Charges createCharges(Charges charges) throws ServiceException, ValidationException {
        try {
            validateCharges(charges);
            chargesRepository.create(user);
            return charges;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de la charge", e);
        }
    }

    @Override
    public Charges updateCharges(Charges charges) throws ServiceException, ValidationException {
        try {
            if (charges.getId() == null) {
                throw new ValidationException("L'ID de la charge est requis");
            }
            validateCharges(charges);
            chargesRepository.update(charges);
            return charges;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de la charge", e);
        }
    }

    @Override
    public void deleteCharges(Long id) throws ServiceException {
        try {
            chargesRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de la charge", e);
        }
    }

    @Override
    public List<Charges> getChargesByDate(LocalDate date) throws ServiceException {
        try {
            return chargesRepository.findByDate(date);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des charges par date", e);
        }
    }

    @Override
    public List<Charges> getChargesByDateRange(LocalDateTime debut, LocalDateTime fin) throws ServiceException {
        try {
            return chargesRepository.findByDateRange(debut, fin);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des charges par plage de dates", e);
        }
    }

    @Override
    public List<Charges> getChargesByStatistiqueId(Long statistiqueId) throws ServiceException {
        try {
            return chargesRepository.findByStatistiqueId(statistiqueId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des charges par statistique", e);
        }
    }

    @Override
    public List<Charges> searchCharges(String keyword) throws ServiceException {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return getAllCharges();
            }
            return chargesRepository.searchByTitre(keyword);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de charges", e);
        }
    }

    @Override
    public Double calculateTotalCharges(LocalDateTime debut, LocalDateTime fin) throws ServiceException {
        try {
            return chargesRepository.calculateTotal(debut, fin);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du calcul des charges totales", e);
        }
    }

    @Override
    public long countCharges() throws ServiceException {
        try {
            return chargesRepository.count();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des charges", e);
        }
    }

    private void validateCharges(Charges charges) throws ValidationException {
        if (charges.getMontant() == null || charges.getMontant() < 0) {
            throw new ValidationException("Le montant de la charge doit être positif");
        }
        if (charges.getTitre() == null || charges.getTitre().trim().isEmpty()) {
            throw new ValidationException("Le titre de la charge est obligatoire");
        }
    }
}
