package ma.dentalTech.repository.modules.Users.Api;

import ma.dentalTech.entities.Users.Staff;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StaffRepository extends CrudRepository<Staff, Long> {

    Optional<Staff> findByUserId(Long userId);
    List<Staff> findByDateRecrutement(LocalDate date);
    List<Staff> findByDateRange(LocalDate debut, LocalDate fin);
    Double calculateTotalSalaires();
    long count();
}