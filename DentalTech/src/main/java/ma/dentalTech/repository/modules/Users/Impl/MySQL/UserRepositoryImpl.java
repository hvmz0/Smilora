package ma.dentalTech.repository.modules.Users.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Enums.Sexe;
import ma.dentalTech.entities.Users.Roles;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.Users.Api.UserRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY nom";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Users", e);
        }
        return list;
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM Users WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById User", e);
        }
        return null;
    }

    @Override
    public void create(User u) {
        String sql = """
            INSERT INTO Users (nom, email, adresse, cin, tel, sexe, role_id, login, motDePass, dateNaissance)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getNom());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getAdresse());
            ps.setString(4, u.getCin());
            ps.setString(5, u.getTel());
            ps.setString(6, u.getSexe() != null ? u.getSexe().name() : null);
            ps.setLong(7, u.getRole() != null ? u.getRole().getId() : 0);
            ps.setString(8, u.getLogin());
            ps.setString(9, u.getMotDePass());
            ps.setDate(10, u.getDateNaissance() != null ? Date.valueOf(u.getDateNaissance()) : null);

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    u.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create User", e);
        }
    }

    @Override
    public void update(User u) {
        String sql = """
            UPDATE Users 
            SET nom = ?, email = ?, adresse = ?, cin = ?, tel = ?, sexe = ?, 
                login = ?, motDePass = ?, dateNaissance = ?
            WHERE id = ?
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, u.getNom());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getAdresse());
            ps.setString(4, u.getCin());
            ps.setString(5, u.getTel());
            ps.setString(6, u.getSexe() != null ? u.getSexe().name() : null);
            ps.setString(7, u.getLogin());
            ps.setString(8, u.getMotDePass());
            ps.setDate(9, u.getDateNaissance() != null ? Date.valueOf(u.getDateNaissance()) : null);
            ps.setLong(10, u.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update User", e);
        }
    }

    @Override
    public void delete(User u) {
        deleteById(u.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Users WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById User", e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByEmail", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String sql = "SELECT * FROM Users WHERE login = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByLogin", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByCin(String cin) {
        String sql = "SELECT * FROM Users WHERE cin = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, cin);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByCin", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findByRole(Long roleId) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE role_id = ? ORDER BY nom";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, roleId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByRole", e);
        }
        return list;
    }

    @Override
    public List<User> searchByNom(String keyword) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE nom LIKE ? ORDER BY nom";

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
    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM Users WHERE email = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur existsByEmail", e);
        }
    }

    @Override
    public boolean existsByLogin(String login) {
        String sql = "SELECT 1 FROM Users WHERE login = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur existsByLogin", e);
        }
    }

    @Override
    public void updateLastLogin(Long userId) {
        String sql = "UPDATE Users SET lastLoginDate = ? WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setLong(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur updateLastLogin", e);
        }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Users";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur count Users", e);
        }
        return 0;
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setNom(rs.getString("nom"));
        u.setEmail(rs.getString("email"));
        u.setAdresse(rs.getString("adresse"));
        u.setCin(rs.getString("cin"));
        u.setTel(rs.getString("tel"));

        String sexe = rs.getString("sexe");
        if (sexe != null) u.setSexe(Sexe.valueOf(sexe));

        // Role - simplification: créer un objet Role basique
        long roleId = rs.getLong("role_id");
        if (!rs.wasNull()) {
            Roles role = new Roles();
            role.setId(roleId);
            u.setRole(role);
        }

        u.setLogin(rs.getString("login"));
        u.setMotDePass(rs.getString("motDePass"));

        Date lld = rs.getDate("lastLoginDate");
        if (lld != null) u.setLastLoginDate(lld.toLocalDate());

        Date dn = rs.getDate("dateNaissance");
        if (dn != null) u.setDateNaissance(dn.toLocalDate());

        return u;
    }
}