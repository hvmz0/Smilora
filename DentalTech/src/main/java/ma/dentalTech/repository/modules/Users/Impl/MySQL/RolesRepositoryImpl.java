package ma.dentalTech.repository.modules.Users.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Users.Roles;
import ma.dentalTech.entities.Enums.Role;
import ma.dentalTech.repository.modules.Users.Api.RolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RolesRepositoryImpl implements RolesRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<Roles> findAll() {
        List<Roles> list = new ArrayList<>();
        // On trie par ID ou par libelle selon ton choix
        String sql = "SELECT * FROM Roles ORDER BY id DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Roles", e);
        }
        return list;
    }

    @Override
    public Roles findById(Long id) {
        String sql = "SELECT * FROM Roles WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Roles", e);
        }
        return null;
    }

    @Override
    public void create(Roles r) {
        String sql = "INSERT INTO Roles (libelle) VALUES (?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Conversion Enum -> String pour la base de données
            ps.setString(1, r.getLibelle().name());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    r.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Roles", e);
        }
    }

    @Override
    public void update(Roles r) {
        String sql = "UPDATE Roles SET libelle = ? WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, r.getLibelle().name());
            ps.setLong(2, r.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Roles", e);
        }
    }

    @Override
    public void delete(Roles r) {
        if (r != null && r.getId() != null) {
            deleteById(r.getId());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Roles WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById Roles", e);
        }
    }

    @Override
    public Optional<Roles> findByLibelle(String libelle) {
        String sql = "SELECT * FROM Roles WHERE libelle = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, libelle);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByLibelle", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByLibelle(String libelle) {
        String sql = "SELECT 1 FROM Roles WHERE libelle = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, libelle);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur existsByLibelle", e);
        }
    }

    // Mapping SQL -> Java avec gestion de l'Enum
    private Roles mapRow(ResultSet rs) throws SQLException {
        Roles r = new Roles();
        r.setId(rs.getLong("id"));

        String libelleStr = rs.getString("libelle");
        if (libelleStr != null) {
            try {
                // Conversion String (Base de données) -> Enum (Java)
                r.setLibelle(Role.valueOf(libelleStr));
            } catch (IllegalArgumentException e) {
                System.err.println("Attention : Role inconnu en base de données -> " + libelleStr);
                // Tu peux définir une valeur par défaut ici si besoin
            }
        }

        return r;
    }
}