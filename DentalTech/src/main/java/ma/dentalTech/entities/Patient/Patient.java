package ma.dentalTech.entities.Patient;
import java.time.LocalDate;
import java.util.List;
import lombok.*;
import ma.dentalTech.entities.Enums.Assurance;
import ma.dentalTech.entities.Enums.Sexe;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Patient {
    private Long id;
    private String nom;
    private LocalDate dateNaissance;
    private Sexe sexe;
    private String adresse;
    private String tel;
    private Assurance assurance;
    private List<Antecedent> antecedents;
    private String Email;
    }

