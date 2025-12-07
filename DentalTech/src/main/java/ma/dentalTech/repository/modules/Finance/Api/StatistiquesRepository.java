package ma.dentalTech.repository.modules.Finance.Api;

import ma.dentalTech.entities.Finance.Statistiques;
import ma.dentalTech.repository.common.CrudRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface StatistiquesRepository extends CrudRepository<Statistiques, Long> {
    List<Statistiques> findByDateRange(LocalDateTime debut, LocalDateTime fin);
    Statistiques findByDate(LocalDateTime date);
    List<Statistiques> findByMonth(int year, int month);
    Double calculateBeneficeByDateRange(LocalDateTime debut, LocalDateTime fin);
    List<Statistiques> findRecent(int limit);}