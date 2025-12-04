package ma.dentalTech.entities.SituationFinanciere;

import lombok.*;
import ma.dentalTech.entities.Facture.Facture;
import ma.dentalTech.entities.patient.Patient;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SituationFinanciere {
    private Long idSF;
    private Double totalFacture;
    private Double totalPaye;
    private Double resteDu;
    private Double credit;
    private String statut;
    private String etat;

    private Patient patient;
    private List<Facture> factures;
}