package ma.dentalTech.repository.modules.Finance.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Finance.Charges;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.Finance.Api.ChargesRepository;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChargesRepositoryImpl implements ChargesRepository {
    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<Charges> findAll() {
        List<Charges> list = new ArrayList<>();
        String sql = "SELECT * FROM Charges ORDER BY date DESC";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Charges", e);
        }
        return list;
    }

    @Override
    public Charges findById(Long id) {
        String sql = "SELECT * FROM Charges WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Charges", e);
        }
        return null;
    }

    @Override
    public long create(User user) {
        String sql = "INSERT INTO Charges (titre, description, montant, date, statistique_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, ch.getTitre());
            ps.setString(2, ch.getDescription());
            ps.setDouble(3, ch.getMontant());
            ps.setTimestamp(4, ch.getDate() != null ? Timestamp.valueOf(ch.getDate()) : Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(5, ch.getStatistiqueId());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) ch.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Charges", e);
        }
        return 0;
    }

    @Override
    public void update(Charges ch) {
        String sql = "UPDATE Charges SET titre = ?, description = ?, montant = ?, date = ? WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, ch.getTitre());
            ps.setString(2, ch.getDescription());
            ps.setDouble(3, ch.getMontant());
            ps.setTimestamp(4, Timestamp.valueOf(ch.getDate()));
            ps.setLong(5, ch.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Charges", e);
        }
    }

    @Override
    public void delete(Charges ch) {
        deleteById(ch.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Charges WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById Charges", e);
        }
    }

    @Override
    public List<Charges> findByDate(LocalDate date) {
        List<Charges> list = new ArrayList<>();
        String sql = "SELECT * FROM Charges WHERE DATE(date) = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByDate", e);
        }
        return list;
    }

    @Override
    public List<Charges> findByDateRange(LocalDateTime debut, LocalDateTime fin) {
        List<Charges> list = new ArrayList<>();
        String sql = "SELECT * FROM Charges WHERE date BETWEEN ? AND ? ORDER BY date DESC";
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
    public List<Charges> findByStatistiqueId(Long statistiqueId) {
        List<Charges> list = new ArrayList<>();
        String sql = "SELECT * FROM Charges WHERE statistique_id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, statistiqueId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByStatistiqueId", e);
        }
        return list;
    }

    @Override
    public List<Charges> searchByTitre(String keyword) {
        List<Charges> list = new ArrayList<>();
        String sql = "SELECT * FROM Charges WHERE titre LIKE ? ORDER BY date DESC";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur searchByTitre", e);
        }
        return list;
    }

    @Override
    public Double calculateTotal(LocalDateTime debut, LocalDateTime fin) {
        String sql = "SELECT SUM(montant) FROM Charges WHERE date BETWEEN ? AND ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(debut));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur calculateTotal", e);
        }
        return 0.0;
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Charges";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur count Charges", e);
        }
        return 0;
    }

    private Charges mapRow(ResultSet rs) throws SQLException {
        Charges ch = new Charges();
        ch.setId(rs.getLong("id"));
        ch.setTitre(rs.getString("titre"));
        ch.setDescription(rs.getString("description"));
        ch.setMontant(rs.getDouble("montant"));
        Timestamp d = rs.getTimestamp("date");
        if (d != null) ch.setDate(d.toLocalDateTime());
        ch.setStatistiqueId(rs.getLong("statistique_id"));
        return ch;
    }
}