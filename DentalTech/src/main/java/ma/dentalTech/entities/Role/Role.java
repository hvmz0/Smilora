package ma.dentalTech.entities.Role;

import lombok.*;
import ma.dentalTech.entities.User.User;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Role {
    private Long idRole;
    private String nom;
    private List<User> users; // Relation conservée comme champ List
}
