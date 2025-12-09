package ma.dentalTech.repository.modules.Medecin.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Medecin.AgendaMedecin;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.Medecin.Api.AgendaMedecinRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AgendaMedecinRepositoryImpl implements AgendaMedecinRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<AgendaMedecin> findAll() {
        List<AgendaMedecin> list = new ArrayList<>();
        String sql = "SELECT * FROM AgendaMedecin ORDER BY dateCreation DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll AgendaMedecin", e);
        }
        return list;
    }

    @Override
    public AgendaMedecin findById(Long id) {
        String sql = "SELECT * FROM AgendaMedecin WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById AgendaMedecin", e);
        }
        return null;
    }

    @Override
    public long create(User user) {
        String sql = """
            INSERT INTO AgendaMedecin (medecin_id, dateCreation, joursDisponibles, horairesTravail, dateDerniereMaj)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, am.getMedecinId());
            ps.setTimestamp(2, am.getDateCreation() != null ? Timestamp.valueOf(am.getDateCreation()) : Timestamp.valueOf(LocalDateTime.now()));

            // Convertir List<String> en String séparé par des virgules
            String jours = am.getJoursDisponibles() != null ? String.join(",", am.getJoursDisponibles()) : "";
            ps.setString(3, jours);

            ps.setString(4, am.getHorairesTravail());
            ps.setTimestamp(5, am.getDateDerniereMaj() != null ? Timestamp.valueOf(am.getDateDerniereMaj()) : null);

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    am.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create AgendaMedecin", e);
        }
        return 0;
    }

    @Override
    public void update(AgendaMedecin am) {
        String sql = """
            UPDATE AgendaMedecin 
            SET joursDisponibles = ?, horairesTravail = ?, dateDerniereMaj = ?
            WHERE id = ?
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            String jours = am.getJoursDisponibles() != null ? String.join(",", am.getJoursDisponibles()) : "";
            ps.setString(1, jours);
            ps.setString(2, am.getHorairesTravail());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(4, am.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update AgendaMedecin", e);
        }
    }

    @Override
    public void delete(AgendaMedecin am) {
        deleteById(am.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM AgendaMedecin WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById AgendaMedecin", e);
        }
    }

    @Override
    public Optional<AgendaMedecin> findByMedecinId(Long medecinId) {
        String sql = "SELECT * FROM AgendaMedecin WHERE medecin_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, medecinId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByMedecinId", e);
        }
        return Optional.empty();
    }

    @Override
    public List<LocalDateTime> getDisponibilites(Long agendaId) {
        List<LocalDateTime> list = new ArrayList<>();
        String sql = "SELECT disponibilite FROM Disponibilites WHERE agenda_id = ? ORDER BY disponibilite";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, agendaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp ts = rs.getTimestamp("disponibilite");
                    if (ts != null) {
                        list.add(ts.toLocalDateTime());
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur getDisponibilites", e);
        }
        return list;
    }

    @Override
    public List<String> getJoursDisponibles(Long agendaId) {
        String sql = "SELECT joursDisponibles FROM AgendaMedecin WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, agendaId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String jours = rs.getString("joursDisponibles");
                    if (jours != null && !jours.isEmpty()) {
                        return Arrays.asList(jours.split(","));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur getJoursDisponibles", e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getConflits(Long agendaId) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT conflit FROM Conflits WHERE agenda_id = ? ORDER BY id";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, agendaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("conflit"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur getConflits", e);
        }
        return list;
    }

    @Override
    public void addDisponibilite(Long agendaId, LocalDateTime disponibilite) {
        String sql = "INSERT INTO Disponibilites (agenda_id, disponibilite) VALUES (?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, agendaId);
            ps.setTimestamp(2, Timestamp.valueOf(disponibilite));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur addDisponibilite", e);
        }
    }

    @Override
    public void removeDisponibilite(Long agendaId, LocalDateTime disponibilite) {
        String sql = "DELETE FROM Disponibilites WHERE agenda_id = ? AND disponibilite = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, agendaId);
            ps.setTimestamp(2, Timestamp.valueOf(disponibilite));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur removeDisponibilite", e);
        }
    }

    @Override
    public void addConflit(Long agendaId, String conflit) {
        String sql = "INSERT INTO Conflits (agenda_id, conflit) VALUES (?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, agendaId);
            ps.setString(2, conflit);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur addConflit", e);
        }
    }

    @Override
    public void clearConflits(Long agendaId) {
        String sql = "DELETE FROM Conflits WHERE agenda_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, agendaId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur clearConflits", e);
        }
    }

    private AgendaMedecin mapRow(ResultSet rs) throws SQLException {
        AgendaMedecin am = new AgendaMedecin();
        am.setId(rs.getLong("id"));
        am.setMedecinId(rs.getLong("medecin_id"));

        Timestamp dc = rs.getTimestamp("dateCreation");
        if (dc != null) am.setDateCreation(dc.toLocalDateTime());

        // Charger les disponibilités
        am.setDisponibilites(getDisponibilites(am.getId()));

        // Parser les jours disponibles
        String jours = rs.getString("joursDisponibles");
        if (jours != null && !jours.isEmpty()) {
            am.setJoursDisponibles(Arrays.asList(jours.split(",")));
        }

        am.setHorairesTravail(rs.getString("horairesTravail"));

        // Charger les conflits
        am.setConflits(getConflits(am.getId()));

        Timestamp dmaj = rs.getTimestamp("dateDerniereMaj");
        if (dmaj != null) am.setDateDerniereMaj(dmaj.toLocalDateTime());

        return am;
    }
}