package ma.dentalTech.entities.Users;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Roles implements Comparable<Roles> {
    private Long id;
    private Roles libelle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Roles)) return false;
        Roles that = (Roles) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        Role {
          id = %d,
          libelle = '%s'
        }
        """.formatted(id, libelle);
    }

    @Override
    public int compareTo(Roles other) {
        return id.compareTo(other.id);
    }
}