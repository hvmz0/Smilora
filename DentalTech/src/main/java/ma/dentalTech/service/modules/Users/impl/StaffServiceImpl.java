package ma.dentalTech.service.modules.Users.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.entities.Users.Staff;
import ma.dentalTech.repository.modules.Users.Api.StaffRepository;
import ma.dentalTech.service.modules.Users.Api.StaffService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    @Override
    public List<Staff> getAllStaff() throws ServiceException {
        try {
            return staffRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des staffs", e);
        }
    }

    @Override
    public Optional<Staff> getStaffById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(staffRepository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du staff", e);
        }
    }

    @Override
    public Staff createStaff(Staff staff) throws ServiceException, ValidationException {
        try {
            validateStaff(staff);
            staffRepository.create(user);
            return staff;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du staff", e);
        }
    }

    @Override
    public Staff updateStaff(Staff staff) throws ServiceException, ValidationException {
        try {
            if (staff.getId() == null) {
                throw new ValidationException("L'ID du staff est requis");
            }
            validateStaff(staff);
            staffRepository.update(staff);
            return staff;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du staff", e);
        }
    }

    @Override
    public void deleteStaff(Long id) throws ServiceException {
        try {
            staffRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du staff", e);
        }
    }

    @Override
    public Optional<Staff> getStaffByUserId(Long userId) throws ServiceException {
        try {
            return staffRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche du staff par utilisateur", e);
        }
    }

    @Override
    public List<Staff> getStaffByDateRecrutement(LocalDate date) throws ServiceException {
        try {
            return staffRepository.findByDateRecrutement(date);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche du staff par date de recrutement", e);
        }
    }

    @Override
    public List<Staff> getStaffByDateRange(LocalDate debut, LocalDate fin) throws ServiceException {
        try {
            return staffRepository.findByDateRange(debut, fin);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche du staff par plage de dates", e);
        }
    }

    @Override
    public Double calculateTotalSalaires() throws ServiceException {
        try {
            return staffRepository.calculateTotalSalaires();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du calcul du total des salaires", e);
        }
    }

    @Override
    public long countStaff() throws ServiceException {
        try {
            return staffRepository.count();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des staffs", e);
        }
    }

    private void validateStaff(Staff staff) throws ValidationException {
        if (staff.getUserId() == null) {
            throw new ValidationException("L'utilisateur est obligatoire");
        }
        if (staff.getDateRecrutement() == null) {
            throw new ValidationException("La date de recrutement est obligatoire");
        }
    }
}
