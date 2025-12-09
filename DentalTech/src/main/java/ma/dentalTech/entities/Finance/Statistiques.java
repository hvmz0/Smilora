package ma.dentalTech.entities.Finance;

import java.time.LocalDateTime;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Statistiques {
    private Long id;
    private Double totalCharges;
    private Double totalRevenus;
    private LocalDateTime date;
}