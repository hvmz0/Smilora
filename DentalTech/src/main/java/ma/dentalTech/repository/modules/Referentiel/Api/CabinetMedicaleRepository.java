package ma.dentalTech.repository.modules.Referentiel.Api;

import ma.dentalTech.entities.Referentiel.CabinetMedicale;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.Optional;

public interface CabinetMedicaleRepository extends CrudRepository<CabinetMedicale, Long> {

    Optional<CabinetMedicale> findByNom(String nom);
    Optional<CabinetMedicale> findByEmail(String email);
    CabinetMedicale getInfo(); // Retourne les infos du cabinet (généralement id=1)
}