package ma.dentalTech.entities.SuperAdmin;

import lombok.*;
import ma.dentalTech.entities.Staff.Staff;
import ma.dentalTech.entities.User.User;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SuperAdmin extends User {
    private List<Staff> staffSupervise; // Relation 1:N
}