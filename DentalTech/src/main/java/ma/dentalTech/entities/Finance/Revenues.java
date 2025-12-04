package ma.dentalTech.entities.Finance;

import java.time.LocalDateTime;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Revenues implements Comparable<Revenues> {
    private Long id;
    private String titre;
    private String description;
    private Double montant;
    private LocalDateTime date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Revenues)) return false;
        Revenues that = (Revenues) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        Revenues {
          id = %d,
          titre = '%s',
          description = '%s',
          montant = %.2f,
          date = %s
        }
        """.formatted(id, titre, description, montant, date);
    }

    @Override
    public int compareTo(Revenues other) {
        return id.compareTo(other.id);
    }
}