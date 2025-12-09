package ma.dentalTech.entities.Finance;

import java.time.LocalDateTime;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Revenues  {
    private Long id;
    private String titre;
    private String description;
    private Double montant;
    private LocalDateTime date;
    private Long statistiqueId;


}