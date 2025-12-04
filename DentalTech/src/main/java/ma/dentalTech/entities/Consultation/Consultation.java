package ma.dentalTech.entities.Consultation;

import lombok.*;
import ma.dentalTech.entities.DossierMedicale.DossierMedicale;
import ma.dentalTech.entities.InterventionMedecin.InterventionMedecin;
import ma.dentalTech.entities.Medecin.Medecin;
import ma.dentalTech.entities.RDV.RDV;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Consultation {
    private Long idConsultation;
    private LocalDate dateConsultation;
    private String statut;
    private String observationMed;

    private DossierMedicale dossierMedicale;
    private Medecin medecin;
    private RDV rendezVous;
    private List<InterventionMedecin> interventions;
}