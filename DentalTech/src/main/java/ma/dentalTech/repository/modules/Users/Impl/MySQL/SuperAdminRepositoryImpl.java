package ma.dentalTech.repository.modules.Users.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Users.SuperAdmin;
import ma.dentalTech.repository.modules.Users.Api.SuperAdminRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SuperAdminRepositoryImpl implements SuperAdminRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<SuperAdmin> findAll() {
        List<SuperAdmin> list = new ArrayList<>();
        String sql = "SELECT * FROM SuperAdmin";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll SuperAdmin", e);
        }
        return list;
    }

    @Override
    public SuperAdmin findById(Long id) {
        String sql = "SELECT * FROM SuperAdmin WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById SuperAdmin", e);
        }
        return null;
    }

    @Override
    public void create(SuperAdmin sa) {
        String sql = "INSERT INTO SuperAdmin (user_id) VALUES (?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, sa.getUserId());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    sa.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create SuperAdmin", e);
        }
    }

    @Override
    public void update(SuperAdmin sa) {
        // SuperAdmin n'a pas de champs à mettre à jour à part user_id qui ne change pas
        String sql = "UPDATE SuperAdmin SET user_id = ? WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, sa.getUserId());
            ps.setLong(2, sa.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update SuperAdmin", e);
        }
    }

    @Override
    public void delete(SuperAdmin sa) {
        deleteById(sa.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM SuperAdmin WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById SuperAdmin", e);
        }
    }

    @Override
    public Optional<SuperAdmin> findByUserId(Long userId) {
        String sql = "SELECT * FROM SuperAdmin WHERE user_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByUserId", e);
        }
        return Optional.empty();
    }

    private SuperAdmin mapRow(ResultSet rs) throws SQLException {
        SuperAdmin sa = new SuperAdmin();
        sa.setId(rs.getLong("id"));
        sa.setUserId(rs.getLong("user_id"));
        return sa;
    }
}
