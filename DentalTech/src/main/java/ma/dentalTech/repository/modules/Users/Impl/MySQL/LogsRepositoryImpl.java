package ma.dentalTech.repository.modules.Users.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Users.Logs;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.Users.Api.LogsRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogsRepositoryImpl implements LogsRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<Logs> findAll() {
        List<Logs> list = new ArrayList<>();
        String sql = "SELECT * FROM Logs ORDER BY sessionStart DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Logs", e);
        }
        return list;
    }

    @Override
    public Logs findById(Long id) {
        String sql = "SELECT * FROM Logs WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Logs", e);
        }
        return null;
    }

    @Override
    public long create(User user) {
        String sql = """
            INSERT INTO Logs (user_id, sessionStart, sessionEnd)
            VALUES (?, ?, ?)
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, log.getUserId());
            ps.setTimestamp(2, log.getSessionStart() != null ? Timestamp.valueOf(log.getSessionStart()) : Timestamp.valueOf(LocalDateTime.now()));

            if (log.getSessionEnd() != null) {
                ps.setTimestamp(3, Timestamp.valueOf(log.getSessionEnd()));
            } else {
                ps.setNull(3, Types.TIMESTAMP);
            }

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    log.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Logs", e);
        }
        return 0;
    }

    @Override
    public void update(Logs log) {
        String sql = "UPDATE Logs SET sessionEnd = ? WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            if (log.getSessionEnd() != null) {
                ps.setTimestamp(1, Timestamp.valueOf(log.getSessionEnd()));
            } else {
                ps.setNull(1, Types.TIMESTAMP);
            }
            ps.setLong(2, log.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Logs", e);
        }
    }

    @Override
    public void delete(Logs log) {
        deleteById(log.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Logs WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById Logs", e);
        }
    }

    @Override
    public List<Logs> findByUserId(Long userId) {
        List<Logs> list = new ArrayList<>();
        String sql = "SELECT * FROM Logs WHERE user_id = ? ORDER BY sessionStart DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByUserId", e);
        }
        return list;
    }

    @Override
    public List<Logs> findByDateRange(LocalDateTime debut, LocalDateTime fin) {
        List<Logs> list = new ArrayList<>();
        String sql = "SELECT * FROM Logs WHERE sessionStart BETWEEN ? AND ? ORDER BY sessionStart DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(debut));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByDateRange", e);
        }
        return list;
    }

    @Override
    public List<Logs> findActiveSessions() {
        List<Logs> list = new ArrayList<>();
        String sql = "SELECT * FROM Logs WHERE sessionEnd IS NULL ORDER BY sessionStart DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findActiveSessions", e);
        }
        return list;
    }

    @Override
    public void closeSession(Long logId, LocalDateTime sessionEnd) {
        String sql = "UPDATE Logs SET sessionEnd = ? WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(sessionEnd));
            ps.setLong(2, logId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur closeSession", e);
        }
    }

    @Override
    public long countByUserId(Long userId) {
        String sql = "SELECT COUNT(*) FROM Logs WHERE user_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur countByUserId", e);
        }
        return 0;
    }

    private Logs mapRow(ResultSet rs) throws SQLException {
        Logs log = new Logs();
        log.setId(rs.getLong("id"));
        log.setUserId(rs.getLong("user_id"));

        Timestamp ss = rs.getTimestamp("sessionStart");
        if (ss != null) log.setSessionStart(ss.toLocalDateTime());

        Timestamp se = rs.getTimestamp("sessionEnd");
        if (se != null) log.setSessionEnd(se.toLocalDateTime());

        return log;
    }
}
