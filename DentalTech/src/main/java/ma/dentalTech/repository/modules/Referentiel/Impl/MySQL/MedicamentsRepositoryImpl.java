package ma.dentalTech.repository.modules.Referentiel.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Referentiel.Medicaments;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.Referentiel.Api.MedicamentsRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentsRepositoryImpl implements MedicamentsRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<Medicaments> findAll() {
        List<Medicaments> list = new ArrayList<>();
        String sql = "SELECT * FROM Medicaments ORDER BY nom";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Medicaments", e);
        }
        return list;
    }

    @Override
    public Medicaments findById(Long id) {
        String sql = "SELECT * FROM Medicaments WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Medicaments", e);
        }
        return null;
    }

    @Override
    public long create(User user) {
        String sql = """
            INSERT INTO Medicaments (nom, prix, description)
            VALUES (?, ?, ?)
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, m.getNom());
            ps.setDouble(2, m.getPrix());
            ps.setString(3, m.getDescription());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    m.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Medicaments", e);
        }
        return 0;
    }

    @Override
    public void update(Medicaments m) {
        String sql = """
            UPDATE Medicaments 
            SET nom = ?, prix = ?, description = ?
            WHERE id = ?
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, m.getNom());
            ps.setDouble(2, m.getPrix());
            ps.setString(3, m.getDescription());
            ps.setLong(4, m.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Medicaments", e);
        }
    }

    @Override
    public void delete(Medicaments m) {
        deleteById(m.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Medicaments WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById Medicaments", e);
        }
    }

    @Override
    public List<Medicaments> searchByNom(String keyword) {
        List<Medicaments> list = new ArrayList<>();
        String sql = "SELECT * FROM Medicaments WHERE nom LIKE ? ORDER BY nom";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur searchByNom", e);
        }
        return list;
    }

    @Override
    public List<Medicaments> findByPriceRange(Double min, Double max) {
        List<Medicaments> list = new ArrayList<>();
        String sql = "SELECT * FROM Medicaments WHERE prix BETWEEN ? AND ? ORDER BY prix";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDouble(1, min);
            ps.setDouble(2, max);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByPriceRange", e);
        }
        return list;
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Medicaments";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur count Medicaments", e);
        }
        return 0;
    }

    private Medicaments mapRow(ResultSet rs) throws SQLException {
        Medicaments m = new Medicaments();
        m.setId(rs.getLong("id"));
        m.setNom(rs.getString("nom"));
        m.setPrix(rs.getDouble("prix"));
        m.setDescription(rs.getString("description"));
        return m;
    }
}