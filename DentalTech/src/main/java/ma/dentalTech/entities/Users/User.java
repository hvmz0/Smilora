package ma.dentalTech.entities.Users;

import java.time.LocalDate;

import lombok.*;
import ma.dentalTech.entities.Enums.Sexe;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class User{
    private Long id;
    private String nom;
    private String email;
    private String adresse;
    private String cin;
    private String tel;
    private Sexe sexe;
    private Roles role;
    private String login;
    private String motDePass;
    private LocalDate lastLoginDate;
    private LocalDate dateNaissance;
}