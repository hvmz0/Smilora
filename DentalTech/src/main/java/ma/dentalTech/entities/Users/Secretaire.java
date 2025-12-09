package ma.dentalTech.entities.Users;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Secretaire {
    private Long id;
    private String numCNSS;
    private Double commission;
    private String agendaMed;
    private Long UserId;


}