package ma.dentalTech.repository.modules.Finance.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Finance.Revenues;
import ma.dentalTech.repository.modules.Finance.Api.RevenuesRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RevenuesRepositoryImpl implements RevenuesRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<Revenues> findAll() {
        List<Revenues> list = new ArrayList<>();
        String sql = "SELECT * FROM Revenues ORDER BY date DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Revenues", e);
        }
        return list;
    }

    @Override
    public Revenues findById(Long id) {
        String sql = "SELECT * FROM Revenues WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Revenues", e);
        }
        return null;
    }

    @Override
    public void create(Revenues r) {
        String sql = """
            INSERT INTO Revenues (titre, description, montant, date, statistique_id)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, r.getTitre());
            ps.setString(2, r.getDescription());
            ps.setDouble(3, r.getMontant());
            ps.setTimestamp(4, r.getDate() != null ? Timestamp.valueOf(r.getDate()) : Timestamp.valueOf(LocalDateTime.now()));

            if (r.getStatistiqueId() != null) {
                ps.setLong(5, r.getStatistiqueId());
            } else {
                ps.setNull(5, Types.BIGINT);
            }

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    r.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Revenues", e);
        }
    }

    @Override
    public void update(Revenues r) {
        String sql = """
            UPDATE Revenues 
            SET titre = ?, description = ?, montant = ?, date = ?
            WHERE id = ?
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, r.getTitre());
            ps.setString(2, r.getDescription());
            ps.setDouble(3, r.getMontant());
            ps.setTimestamp(4, Timestamp.valueOf(r.getDate()));
            ps.setLong(5, r.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Revenues", e);
        }
    }

    @Override
    public void delete(Revenues r) {
        deleteById(r.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Revenues WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById Revenues", e);
        }
    }

    @Override
    public List<Revenues> findByDate(LocalDate date) {
        List<Revenues> list = new ArrayList<>();
        String sql = "SELECT * FROM Revenues WHERE DATE(date) = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByDate", e);
        }
        return list;
    }

    @Override
    public List<Revenues> findByDateRange(LocalDateTime debut, LocalDateTime fin) {
        List<Revenues> list = new ArrayList<>();
        String sql = "SELECT * FROM Revenues WHERE date BETWEEN ? AND ? ORDER BY date DESC";

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
    public List<Revenues> findByStatistiqueId(Long statistiqueId) {
        List<Revenues> list = new ArrayList<>();
        String sql = "SELECT * FROM Revenues WHERE statistique_id = ? ORDER BY date DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, statistiqueId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByStatistiqueId", e);
        }
        return list;
    }

    @Override
    public List<Revenues> searchByTitre(String keyword) {
        List<Revenues> list = new ArrayList<>();
        String sql = "SELECT * FROM Revenues WHERE titre LIKE ? ORDER BY date DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur searchByTitre", e);
        }
        return list;
    }

    @Override
    public Double calculateTotal(LocalDateTime debut, LocalDateTime fin) {
        String sql = "SELECT SUM(montant) FROM Revenues WHERE date BETWEEN ? AND ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(debut));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur calculateTotal", e);
        }
        return 0.0;
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Revenues";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur count Revenues", e);
        }
        return 0;
    }

    private Revenues mapRow(ResultSet rs) throws SQLException {
        Revenues r = new Revenues();
        r.setId(rs.getLong("id"));
        r.setTitre(rs.getString("titre"));
        r.setDescription(rs.getString("description"));
        r.setMontant(rs.getDouble("montant"));

        Timestamp d = rs.getTimestamp("date");
        if (d != null) r.setDate(d.toLocalDateTime());

        long statId = rs.getLong("statistique_id");
        if (!rs.wasNull()) r.setStatistiqueId(statId);

        return r;
    }
}