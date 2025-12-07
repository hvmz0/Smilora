package ma.dentalTech.repository.modules.Finance.Api;

import ma.dentalTech.entities.Finance.Charges;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ChargesRepository extends CrudRepository<Charges, Long> {

    List<Charges> findByDate(LocalDate date);
    List<Charges> findByDateRange(LocalDateTime debut, LocalDateTime fin);
    List<Charges> findByStatistiqueId(Long statistiqueId);
    List<Charges> searchByTitre(String keyword);
    Double calculateTotal(LocalDateTime debut, LocalDateTime fin);
    long count();
}