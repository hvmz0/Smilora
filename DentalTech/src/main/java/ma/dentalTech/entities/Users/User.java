package ma.dentalTech.entities.Users;

import java.time.LocalDate;

import lombok.*;
import ma.dentalTech.entities.Enums.Sexe;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class User implements Comparable<User> {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User that = (User) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        User {
          id = %d,
          nom = '%s',
          email = '%s',
          adresse = '%s',
          cin = '%s',
          tel = '%s',
          sexe = %s,
          role = %s,
          login = '%s',
          lastLoginDate = %s,
          dateNaissance = %s
        }
        """.formatted(id, nom, email, adresse,cin, tel, sexe, role, login, lastLoginDate, dateNaissance);
    }

    @Override
    public int compareTo(User other) {
        return id.compareTo(other.id);
    }
}