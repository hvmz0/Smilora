package ma.dentalTech.entities.Finance;

import java.time.LocalDateTime;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Statistiques implements Comparable<Statistiques> {
    private Long id;
    private Double totalCharges;
    private Double totalRevenus;
    private LocalDateTime date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Statistiques)) return false;
        Statistiques that = (Statistiques) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        Statistiques {
          id = %d,
          totalCharges = %.2f,
          totalRevenus = %.2f,
          date = %s
        }
        """.formatted(id, totalCharges, totalRevenus, date);
    }

    @Override
    public int compareTo(Statistiques other) {
        return id.compareTo(other.id);
    }
}