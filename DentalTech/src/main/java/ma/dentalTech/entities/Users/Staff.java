package ma.dentalTech.entities.Users;

import java.time.LocalDate;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Staff implements Comparable<Staff> {
    private Long id;
    private Double salaire;
    private Double prime;
    private LocalDate dateRecrutement;
    private Integer soldeConge;
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Staff)) return false;
        Staff that = (Staff) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        Staff {
          id = %d,
          salaire = %.2f,
          prime = %.2f,
          dateRecrutement = %s,
          soldeConge = %d
        }
        """.formatted(id, salaire, prime, dateRecrutement, soldeConge);
    }

    @Override
    public int compareTo(Staff other) {
        return id.compareTo(other.id);
    }
}