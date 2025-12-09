package ma.dentalTech.repository.modules.Referentiel.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Referentiel.Actes;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.Referentiel.Api.ActesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActesRepositoryImpl implements ActesRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<Actes> findAll() {
        List<Actes> list = new ArrayList<>();
        String sql = "SELECT * FROM Actes ORDER BY libelle";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Actes", e);
        }
        return list;
    }

    @Override
    public Actes findById(Long id) {
        String sql = "SELECT * FROM Actes WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Actes", e);
        }
        return null;
    }

    @Override
    public long create(User user) {
        String sql = """
            INSERT INTO Actes (libelle, categorie, prixDeBase)
            VALUES (?, ?, ?)
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, a.getLibelle());
            ps.setString(2, a.getCategorie());
            ps.setDouble(3, a.getPrixDeBase());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    a.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Actes", e);
        }
        return 0;
    }

    @Override
    public void update(Actes a) {
        String sql = """
            UPDATE Actes 
            SET libelle = ?, categorie = ?, prixDeBase = ?
            WHERE id = ?
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, a.getLibelle());
            ps.setString(2, a.getCategorie());
            ps.setDouble(3, a.getPrixDeBase());
            ps.setLong(4, a.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Actes", e);
        }
    }

    @Override
    public void delete(Actes a) {
        deleteById(a.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Actes WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById Actes", e);
        }
    }

    @Override
    public List<Actes> findByCategorie(String categorie) {
        List<Actes> list = new ArrayList<>();
        String sql = "SELECT * FROM Actes WHERE categorie = ? ORDER BY libelle";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, categorie);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByCategorie", e);
        }
        return list;
    }

    @Override
    public List<Actes> searchByLibelle(String keyword) {
        List<Actes> list = new ArrayList<>();
        String sql = "SELECT * FROM Actes WHERE libelle LIKE ? ORDER BY libelle";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur searchByLibelle", e);
        }
        return list;
    }

    @Override
    public List<Actes> findByPriceRange(Double min, Double max) {
        List<Actes> list = new ArrayList<>();
        String sql = "SELECT * FROM Actes WHERE prixDeBase BETWEEN ? AND ? ORDER BY prixDeBase";

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
    public List<String> getAllCategories() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT categorie FROM Actes ORDER BY categorie";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("categorie"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur getAllCategories", e);
        }
        return list;
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Actes";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur count Actes", e);
        }
        return 0;
    }

    private Actes mapRow(ResultSet rs) throws SQLException {
        Actes a = new Actes();
        a.setId(rs.getLong("id"));
        a.setLibelle(rs.getString("libelle"));
        a.setCategorie(rs.getString("categorie"));
        a.setPrixDeBase(rs.getDouble("prixDeBase"));
        return a;
    }
}