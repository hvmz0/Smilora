package ma.dentalTech.entities.Patient;
import java.time.LocalDate;
import java.util.List;
import lombok.*;
import ma.dentalTech.entities.Enums.Assurance;
import ma.dentalTech.entities.Enums.Sexe;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Patient implements Comparable<Patient> {
    private Long id;
    private String nom;
    private LocalDate dateNaissance;
    private Sexe sexe;
    private String adresse;
    private String tel;
    private Assurance assurance;
    private List<Antecedent> antecedents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient that = (Patient) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        Patient {
          id = %d,
          nom = '%s',
          dateNaissance = %s,
          sexe = %s,
          adresse = '%s',
          tel = '%s',
          assurance = %s,
          antecedentsCount = %d
        }
        """.formatted(
                id, nom, dateNaissance, sexe, adresse, tel, assurance,
                antecedents == null ? 0 : antecedents.size()
        );
    }

    @Override
    public int compareTo(Patient other) {
        return id.compareTo(other.id);
    }
}
