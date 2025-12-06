package ma.dentalTech.repository.modules.Referentiel.Api;

import ma.dentalTech.entities.Referentiel.Actes;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;

public interface ActesRepository extends CrudRepository<Actes, Long> {

    List<Actes> findByCategorie(String categorie);
    List<Actes> searchByLibelle(String keyword);
    List<Actes> findByPriceRange(Double min, Double max);
    List<String> getAllCategories();
    long count();
}