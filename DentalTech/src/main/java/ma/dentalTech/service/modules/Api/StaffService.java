package ma.dentalTech.service.modules.Api;

import ma.dentalTech.entities.Users.Staff;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StaffService {

    List<Staff> getAllStaff() throws ServiceException;
    Optional<Staff> getStaffById(Long id) throws ServiceException;
    Staff createStaff(Staff staff) throws ServiceException, ValidationException;
    Staff updateStaff(Staff staff) throws ServiceException, ValidationException;
    void deleteStaff(Long id) throws ServiceException;

    Optional<Staff> getStaffByUserId(Long userId) throws ServiceException;
    List<Staff> getStaffByDateRecrutement(LocalDate date) throws ServiceException;
    List<Staff> getStaffByDateRange(LocalDate debut, LocalDate fin) throws ServiceException;
    Double calculateTotalSalaires() throws ServiceException;
    long countStaff() throws ServiceException;
}
