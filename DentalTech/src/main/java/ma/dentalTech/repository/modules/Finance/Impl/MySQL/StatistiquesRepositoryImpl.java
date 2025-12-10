package ma.dentalTech.repository.modules.Finance.Impl.MySQL;

import com.mysql.cj.jdbc.CallableStatement;
import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Finance.Statistiques;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.Finance.Api.StatistiquesRepository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StatistiquesRepositoryImpl implements StatistiquesRepository {
    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<Statistiques> findAll() {
        List<Statistiques> list = new ArrayList<>();
        String sql = "SELECT * FROM Statistiques ORDER BY date DESC";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Statistiques", e);
        }
        return list;
    }

    @Override
    public Statistiques findById(Long id) {
        String sql = "SELECT * FROM Statistiques WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Statistiques", e);
        }
        return null;
    }

    @Override
    public long create(User user) {
        String sql = "INSERT INTO Statistiques (totalCharges, totalRevenus, date) VALUES (?, ?, ?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            CallableStatement st = null;
            ps.setDouble(1, st.getBatchedArgs());
            ps.setDouble(2, st.getBoolean());
            ps.setTimestamp(3, Timestamp.valueOf(st.getDate()));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) st.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Statistiques", e);
        }
        return 0;
    }

    @Override
    public void update(Statistiques st) {
        String sql = "UPDATE Statistiques SET totalCharges = ?, totalRevenus = ? WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, st.getTotalCharges());
            ps.setDouble(2, st.getTotalRevenus());
            ps.setLong(3, st.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Statistiques", e);
        }
    }

    @Override
    public void delete(Statistiques st) {
        deleteById(st.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Statistiques WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById Statistiques", e);
        }
    }

    @Override
    public List<Statistiques> findByDateRange(LocalDateTime debut, LocalDateTime fin) {
        List<Statistiques> list = new ArrayList<>();
        String sql = "SELECT * FROM Statistiques WHERE date BETWEEN ? AND ? ORDER BY date DESC";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(debut));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByDateRange", e);
        }
        return list;
    }

    @Override
    public Statistiques findByDate(LocalDateTime date) {
        String sql = "SELECT * FROM Statistiques WHERE DATE(date) = DATE(?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByDate", e);
        }
        return null;
    }

    @Override
    public List<Statistiques> findByMonth(int year, int month) {
        List<Statistiques> list = new ArrayList<>();
        String sql = "SELECT * FROM Statistiques WHERE YEAR(date) = ? AND MONTH(date) = ? ORDER BY date DESC";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, year);
            ps.setInt(2, month);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByMonth", e);
        }
        return list;
    }

    @Override
    public Double calculateBeneficeByDateRange(LocalDateTime debut, LocalDateTime fin) {
        String sql = "SELECT SUM(totalRevenus - totalCharges) FROM Statistiques WHERE date BETWEEN ? AND ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(debut));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur calculateBeneficeByDateRange", e);
        }
        return 0.0;
    }

    @Override
    public List<Statistiques> findRecent(int limit) {
        List<Statistiques> list = new ArrayList<>();
        String sql = "SELECT * FROM Statistiques ORDER BY date DESC LIMIT ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findRecent", e);
        }
        return list;
    }

    private Statistiques mapRow(ResultSet rs) throws SQLException {
        Statistiques st = new Statistiques();
        st.setId(rs.getLong("id"));
        st.setTotalCharges(rs.getDouble("totalCharges"));
        st.setTotalRevenus(rs.getDouble("totalRevenus"));
        Timestamp d = rs.getTimestamp("date");
        if (d != null) st.setDate(d.toLocalDateTime());
        return st;
    }
}