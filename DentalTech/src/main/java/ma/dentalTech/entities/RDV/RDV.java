package ma.dentalTech.entities.RDV;

import lombok.*;
import ma.dentalTech.entities.AgendaMedecin.AgendaMedecin;
import ma.dentalTech.entities.Consultation.Consultation;
import ma.dentalTech.entities.Ordonnance.Ordonnance;
import ma.dentalTech.entities.patient.Patient;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RDV {
    private Long idRDV;
    private LocalDateTime dateHeureRDV;
    private String motif;
    private String statut;
    private String noteMedecin;

    private Patient patient; // Relation N:1
    private AgendaMedecin agenda; // Relation N:1
    private Consultation consultation; // Relation 1:1
    private Ordonnance ordonnance; // Relation 1:1
}