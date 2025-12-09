package ma.dentalTech.entities.Users;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class SuperAdmin {
    private Long id;
    private Long UserId;
}