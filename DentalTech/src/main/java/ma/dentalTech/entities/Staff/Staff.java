package ma.dentalTech.entities.Staff;

import lombok.*;
import ma.dentalTech.entities.SuperAdmin.SuperAdmin;
import ma.dentalTech.entities.User.User;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Staff extends User {
    private Double salaire;
    private Double prime;
    private SuperAdmin superviseur; // Relation N:1
}