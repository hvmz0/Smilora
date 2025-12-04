package ma.dentalTech.entities.Users;

import java.time.LocalDateTime;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Logs implements Comparable<Logs> {
    private Long id;
    private Long userId;
    private LocalDateTime sessionStart;
    private LocalDateTime sessionEnd;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Logs)) return false;
        Logs that = (Logs) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        Logs {
          id = %d,
          userId = %d,
          sessionStart = %s,
          sessionEnd = %s
        }
        """.formatted(id, userId, sessionStart, sessionEnd);
    }

    @Override
    public int compareTo(Logs other) {
        return id.compareTo(other.id);
    }
}