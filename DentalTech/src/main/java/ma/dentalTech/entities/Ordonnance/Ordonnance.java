package ma.dentalTech.entities.Ordonnance;

import lombok.*;
import ma.dentalTech.entities.PrescriptionMedicaments.PrescriptionMedicaments;
import ma.dentalTech.entities.RDV.RDV;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Ordonnance {
    private Long idOrd;
    private LocalDate dateOrdonnance;
    private RDV rendezVous;
    private List<PrescriptionMedicaments> prescriptions;
}