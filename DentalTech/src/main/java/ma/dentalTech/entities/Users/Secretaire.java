package ma.dentalTech.entities.Users;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Secretaire implements Comparable<Secretaire> {
    private Long id;
    private String numCNSS;
    private Double commission;
    private String agendaMed;
    private Long UserId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Secretaire)) return false;
        Secretaire that = (Secretaire) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        Secretaire {
          id = %d,
          numCNSS = '%s',
          commission = %.2f,
          agendaMed = '%s'
        }
        """.formatted(id, numCNSS, commission, agendaMed);
    }

    @Override
    public int compareTo(Secretaire other) {
        return id.compareTo(other.id);
    }
}