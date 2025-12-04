package ma.dentalTech.entities.Medecin;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Medecin implements Comparable<Medecin> {
    private Long id;
    private String specialite;
    private String agenda;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medecin)) return false;
        Medecin that = (Medecin) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        Medecin {
          id = %d,
          specialite = '%s',
          agenda = '%s'
        }
        """.formatted(id, specialite, agenda);
    }

    @Override
    public int compareTo(Medecin other) {
        return id.compareTo(other.id);
    }
}