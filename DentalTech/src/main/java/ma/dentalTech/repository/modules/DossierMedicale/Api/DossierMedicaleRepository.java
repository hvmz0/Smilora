package ma.dentalTech.repository.modules.DossierMedicale.Api;

import ma.dentalTech.entities.DossierMedicale.DossierMedicale;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DossierMedicaleRepository extends CrudRepository<DossierMedicale, Long> {

    DossierMedicale create(DossierMedicale dm);


    // NOTE : Assurez-vous d'avoir retiré le mot clé 'static' dans l'interface pour que @Override fonctionne
    Optional<DossierMedicale> findByPatientId(Long patientId);

    List<DossierMedicale> findByDateCreation(LocalDate date);

    List<DossierMedicale> findByDateRange(LocalDate debut, LocalDate fin);

    long count();
}