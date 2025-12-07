package ma.dentalTech.repository.modules.Patient.Api;

import ma.dentalTech.entities.Patient.HistoriqueMedicale;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;

public interface HistoriqueMedicaleRepository extends CrudRepository<HistoriqueMedicale, Long> {

    List<HistoriqueMedicale> findByDossierMedicaleId(Long dossierId);
    long countByDossierMedicaleId(Long dossierId);
}