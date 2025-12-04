package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.Patient.Antecedent;
import ma.dentalTech.entities.Enums.CategorieAntecedent;
import ma.dentalTech.entities.Enums.NiveauRisque;
import ma.dentalTech.entities.Patient.Patient;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Author : Houssam
 */

public interface AntecedentRepository extends CrudRepository<Antecedent, Long> {

    Optional<Antecedent> findByNom(String nom);
    List<Antecedent> findByCategorie(CategorieAntecedent categorie);
    List<Antecedent> findByNiveauRisque(NiveauRisque niveau);
    boolean existsById(Long id);
    long count();
    List<Antecedent> findPage(int limit, int offset);

    // ---- Navigation inverse ----
    List<Patient> getPatientsHavingAntecedent(Long antecedentId);
}
