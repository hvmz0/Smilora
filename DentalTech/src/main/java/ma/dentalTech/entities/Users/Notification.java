package ma.dentalTech.entities.Users;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;
import ma.dentalTech.entities.Enums.TypeEnum;
import ma.dentalTech.entities.Enums.PrioriteEnum;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Notification implements Comparable<Notification> {
    private Long id;
    private String titre;
    private String message;
    private LocalDate date;
    private LocalTime time;
    private TypeEnum type;
    private PrioriteEnum priorite;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;
        Notification that = (Notification) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        Notification {
          id = %d,
          titre = '%s',
          message = '%s',
          date = %s,
          time = %s,
          type = %s,
          priorite = %s
        }
        """.formatted(id,titre, message, date, time, type, priorite);
    }

    @Override
    public int compareTo(Notification other) {
        return id.compareTo(other.id);
    }
}