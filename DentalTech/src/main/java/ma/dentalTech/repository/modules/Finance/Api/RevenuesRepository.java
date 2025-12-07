package ma.dentalTech.repository.modules.Finance.Api;

import ma.dentalTech.entities.Finance.Revenues;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RevenuesRepository extends CrudRepository<Revenues, Long> {

    List<Revenues> findByDate(LocalDate date);
    List<Revenues> findByDateRange(LocalDateTime debut, LocalDateTime fin);
    List<Revenues> findByStatistiqueId(Long statistiqueId);
    List<Revenues> searchByTitre(String keyword);
    Double calculateTotal(LocalDateTime debut, LocalDateTime fin);
    long count();
}