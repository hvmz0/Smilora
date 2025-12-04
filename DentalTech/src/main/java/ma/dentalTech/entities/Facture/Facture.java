package ma.dentalTech.entities.Facture;

import lombok.*;
import ma.dentalTech.entities.CabinetMedicale.CabinetMedicale;
import ma.dentalTech.entities.SituationFinanciere.SituationFinanciere;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Facture {
    private Long idFacture;
    private Double totalFacture;
    private Double totalPaye;
    private Double resteDu;
    private String statut;
    private LocalDateTime dateFacture;

    private CabinetMedicale cabinetMedicale;
    private SituationFinanciere situationFinanciere;
}