package ma.dentalTech.repository.modules.Referentiel.Api;

import ma.dentalTech.entities.Referentiel.Medicaments;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;

public interface MedicamentsRepository extends CrudRepository<Medicaments, Long> {

    List<Medicaments> searchByNom(String keyword);
    List<Medicaments> findByPriceRange(Double min, Double max);
    long count();
}