package ma.dentalTech.entities.Finance;

import lombok.*;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.entities.Enums.PromoEnum;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class SituationFinanciere  {
    private Long id;
    private Double totalDesAchats;
    private Double totalPaye;
    private Double credit;
    private StatutEnum statut;
    private PromoEnum promo;
    private Long patientId;

  
}