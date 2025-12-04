package ma.dentalTech.entities.Finance;

import java.time.LocalDateTime;
import lombok.*;
import ma.dentalTech.entities.Enums.StatutEnum;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Facture implements Comparable<Facture> {
    private Long id;
    private Double totalFacture;
    private Double totalPaye;
    private Double reste;
    private StatutEnum statut;
    private LocalDateTime dateFacture;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Facture)) return false;
        Facture that = (Facture) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        Facture {
          id = %d,
          totalFacture = %.2f,
          totalPaye = %.2f,
          reste = %.2f,
          statut = %s,
          dateFacture = %s
        }
        """.formatted(id, totalFacture, totalPaye, reste, statut, dateFacture);
    }

    @Override
    public int compareTo(Facture other) {
        return id.compareTo(other.id);
    }
}