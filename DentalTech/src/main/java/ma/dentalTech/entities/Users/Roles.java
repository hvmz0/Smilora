package ma.dentalTech.entities.Users;
import ma.dentalTech.entities.Enums.Role;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Roles {
    private Long id;
    private String libelle;
}