package ma.dentalTech.service.modules.patient.impl;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import ma.dentalTech.entities.Patient.Antecedent;
import ma.dentalTech.entities.Patient.Patient;
import ma.dentalTech.mvc.dto.PatientDTO;
import ma.dentalTech.repository.modules.API.PatientRepository;
import ma.dentalTech.repository.modules.IMPL.MySQL.PatientRepositoryImpl;
import ma.dentalTech.service.modules.patient.api.PatientService;


@Data
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

    /**
     * Formattage de date
     * @param dt : date Non Formatée
     * @return  date formatée
     */
    private static String formatDate(java.time.LocalDateTime dt) {
        return dt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    /**
     * Calculer l'âge du patient à partir de sa date de naissance
     * @param birthDate
     * @return age
     */
    private static int computeAge(java.time.LocalDate birthDate) {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    @Override
    public List<PatientDTO> getTodayPatientsAsDTO() {
        LocalDate today = LocalDate.now();
        return repository.findAll().stream()

                .filter(p -> p.getDateCreation() != null && p.getDateCreation().toLocalDate().equals(today))
                .sorted(Comparator.comparing(Patient::getDateCreation).reversed())
                .map(p -> PatientDTO.builder()
                        .nomComplet((p.getNom() == null ? "" : p.getNom().trim()) + " " + (p.getPrenom() == null ? "" : p.getPrenom().trim()))
                        .age(computeAge(p.getDateNaissance()))
                        .dateCreationFormatee(formatDate(p.getDateCreation()))
                        .build())
                .collect(Collectors.toList());
    }

    /** Charge un patient et initialise sa liste d’antécédents */
    public Patient getPatientWithAntecedents(Long patientId) {
        Patient p = repository.findById(patientId);
        if (p == null) return null;
        List<Antecedent> ant = repository.getAntecedentsOfPatient(patientId);
        p.setAntecedents(ant);
        return p;
    }

    /** Charge tous les patients avec leurs antécédents */
    public List<Patient> getAllPatientsWithAntecedents() {
        List<Patient> patients = repository.findAll();
        for (Patient p : patients) {
            p.setAntecedents(repository.getAntecedentsOfPatient(p.getId()));
        }
        return patients;
    }

    public static void main(String[] args) {

        // Test
        PatientRepository repo = new PatientRepositoryImpl();
        PatientService service = new PatientServiceImpl(repo);

        Patient patient = service.getPatientWithAntecedents(2L);

        System.out.println("Patient : " + patient.getNom());
        for (var an : patient.getAntecedents()) {
            System.out.println(" - " + an.getNom() + " (" + an.getCategorie() + ")");
        }
    }
}
