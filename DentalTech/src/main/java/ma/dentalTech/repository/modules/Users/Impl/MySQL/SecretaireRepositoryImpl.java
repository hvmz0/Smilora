package ma.dentalTech.repository.modules.Users.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Users.Secretaire;
import ma.dentalTech.repository.modules.Users.Api.SecretaireRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SecretaireRepositoryImpl implements SecretaireRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<Secretaire> findAll() {
        List<Secretaire> list = new ArrayList<>();
        String sql = "SELECT * FROM Secretaires ORDER BY id";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Secretaires", e);
        }
        return list;
    }

    @Override
    public Secretaire findById(Long id) {
        String sql = "SELECT * FROM Secretaires WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Secretaire", e);
        }
        return null;
    }

    @Override
    public void create(Secretaire s) {
        String sql = """
            INSERT INTO Secretaires (numCNSS, commission, agendaMed, user_id)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, s.getNumCNSS());
            ps.setDouble(2, s.getCommission());
            ps.setString(3, s.getAgendaMed());
            ps.setLong(4, s.getUserId());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    s.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Secretaire", e);
        }
    }

    @Override
    public void update(Secretaire s) {
        String sql = """
            UPDATE Secretaires 
            SET numCNSS = ?, commission = ?, agendaMed = ?
            WHERE id = ?
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, s.getNumCNSS());
            ps.setDouble(2, s.getCommission());
            ps.setString(3, s.getAgendaMed());
            ps.setLong(4, s.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Secretaire", e);
        }
    }

    @Override
    public void delete(Secretaire s) {
        deleteById(s.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Secretaires WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById Secretaire", e);
        }
    }

    @Override
    public Optional<Secretaire> findByUserId(Long userId) {
        String sql = "SELECT * FROM Secretaires WHERE user_id = ?";

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

    @Override
    public Optional<Secretaire> findByNumCNSS(String numCNSS) {
        String sql = "SELECT * FROM Secretaires WHERE numCNSS = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, numCNSS);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByNumCNSS", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Secretaire> findByAgendaMed(String agendaMed) {
        List<Secretaire> list = new ArrayList<>();
        String sql = "SELECT * FROM Secretaires WHERE agendaMed = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, agendaMed);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByAgendaMed", e);
        }
        return list;
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Secretaires";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur count Secretaires", e);
        }
        return 0;
    }

    private Secretaire mapRow(ResultSet rs) throws SQLException {
        Secretaire s = new Secretaire();
        s.setId(rs.getLong("id"));
        s.setNumCNSS(rs.getString("numCNSS"));
        s.setCommission(rs.getDouble("commission"));
        s.setAgendaMed(rs.getString("agendaMed"));
        s.setUserId(rs.getLong("user_id"));
        return s;
    }
}