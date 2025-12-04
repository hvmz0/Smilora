package ma.dentalTech.service.modules.patient.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import ma.dentalTech.entities.Patient.Antecedent;
import ma.dentalTech.repository.modules.API.AntecedentRepository;
import ma.dentalTech.repository.modules.API.PatientRepository;
import ma.dentalTech.repository.modules.IMPL.MySQL.AntecedentRepositoryImpl;
import ma.dentalTech.repository.modules.IMPL.MySQL.PatientRepositoryImpl;
import ma.dentalTech.service.modules.patient.api.AntecedentService;

@Data @AllArgsConstructor
public class AntecedentServiceImpl implements AntecedentService {


    private final AntecedentRepository antecedentRepo;
    private final PatientRepository patientRepo;


    public Antecedent getAntecedentWithPatients(Long antecedentId) {
        Antecedent a = antecedentRepo.findById(antecedentId);
        if (a == null) return null;
        a.setPatients(patientRepo.getPatientsByAntecedent(antecedentId)); // nécessite get/setPatients dans l’entité
        return a;
    }

    public List<Antecedent> getAllAntecedentsWithPatients() {
        List<Antecedent> list = antecedentRepo.findAll();
        for (var a : list) {
            a.setPatients(patientRepo.getPatientsByAntecedent(a.getId()));
        }
        return list;
    }

    public void assignToPatient(Long antecedentId, Long patientId) {
        patientRepo.addAntecedentToPatient(patientId, antecedentId);
    }

    public void unassignFromPatient(Long antecedentId, Long patientId) {
        patientRepo.removeAntecedentFromPatient(patientId, antecedentId);
    }

    public void replacePatients(Long antecedentId, List<Long> newPatientIds) {
        var current = patientRepo.getPatientsByAntecedent(antecedentId);
        for (var p : current) patientRepo.removeAntecedentFromPatient(p.getId(), antecedentId);
        for (Long pid : newPatientIds) patientRepo.addAntecedentToPatient(pid, antecedentId);
    }


    public static void main(String[] args) {

        var service = new AntecedentServiceImpl(
                new AntecedentRepositoryImpl(),
                new PatientRepositoryImpl()
        );

// Supposons que tu veux afficher les patients liés à l’antécédent 5 (Hypertension artérielle)
        var view = service.getAntecedentWithPatients(5L);

        System.out.println("Antécédent : " + view.getNom());
        System.out.println("Patients liés :");
        view.getPatients().forEach(p ->
                System.out.println(" - " + p.getNom() + " " + p.getPrenom())
        );

    }
}
