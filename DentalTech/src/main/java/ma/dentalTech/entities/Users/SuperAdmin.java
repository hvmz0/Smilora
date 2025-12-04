package ma.dentalTech.entities.Users;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class SuperAdmin implements Comparable<SuperAdmin> {
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SuperAdmin)) return false;
        SuperAdmin that = (SuperAdmin) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        SuperAdmin {
          id = %d
        }
        """.formatted(id);
    }

    @Override
    public int compareTo(SuperAdmin other) {
        return id.compareTo(other.id);
    }
}