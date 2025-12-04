package ma.dentalTech.mvc.dto;


import java.util.List;
import ma.dentalTech.entities.Patient.Antecedent;
import ma.dentalTech.entities.Patient.Patient;

public record AntecedentDTO(Antecedent antecedent, List<Patient> patients) {}
