package ma.dentalTech.entities.Users;

import java.time.LocalDateTime;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Logs {
    private Long id;
    private Long userId;
    private LocalDateTime sessionStart;
    private LocalDateTime sessionEnd;


}