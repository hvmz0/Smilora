package ma.dentalTech.service.modules.patient.api;

import java.util.List;
import ma.dentalTech.entities.patient.Antecedent;

 public interface AntecedentService
{
     Antecedent getAntecedentWithPatients(Long antecedentId);

     List<Antecedent> getAllAntecedentsWithPatients();

     void assignToPatient(Long antecedentId, Long patientId);
     void unassignFromPatient(Long antecedentId, Long patientId);

     void replacePatients(Long antecedentId, List<Long> newPatientIds);
}
