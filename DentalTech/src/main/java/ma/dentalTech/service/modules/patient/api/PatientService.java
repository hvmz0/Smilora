package ma.dentalTech.service.modules.patient.api;


import java.util.List;
import ma.dentalTech.entities.Patient.Patient;
import ma.dentalTech.mvc.dto.PatientDTO;

public interface PatientService {

    /**
     * Récupère les patients ajoutés aujourd'hui,
     * triés par ordre de création (plus récent -> plus ancien),
     * et les expose sous forme de PatientDTO (nom complet, âge, date formatée).
     */
    List<PatientDTO> getTodayPatientsAsDTO();


    /** Charge un patient et initialise sa liste d’antécédents */
    Patient getPatientWithAntecedents(Long patientId);

    /** Charge tous les patients avec leurs antécédents */
    List<Patient> getAllPatientsWithAntecedents();
}
