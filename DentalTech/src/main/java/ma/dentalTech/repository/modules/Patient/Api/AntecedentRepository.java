package ma.dentalTech.repository.modules.Patient.Api;

import ma.dentalTech.entities.Patient.Antecedent;
import ma.dentalTech.entities.Enums.CategorieAntecedent;
import ma.dentalTech.entities.Enums.RisqueEnum;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;

public interface AntecedentRepository extends CrudRepository<Antecedent, Long> {

    List<Antecedent> findByCategorie(CategorieAntecedent categorie);
    List<Antecedent> findByNiveauRisque(RisqueEnum niveau);
    List<Antecedent> searchByNom(String keyword);
    long count();
}