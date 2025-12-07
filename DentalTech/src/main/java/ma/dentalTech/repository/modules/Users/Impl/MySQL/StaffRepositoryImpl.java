package ma.dentalTech.repository.modules.Users.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Users.Staff;
import ma.dentalTech.repository.modules.Users.Api.StaffRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StaffRepositoryImpl implements StaffRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<Staff> findAll() {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM Staff ORDER BY dateRecrutement DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Staff", e);
        }
        return list;
    }

    @Override
    public Staff findById(Long id) {
        String sql = "SELECT * FROM Staff WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Staff", e);
        }
        return null;
    }

    @Override
    public void create(Staff st) {
        String sql = """
            INSERT INTO Staff (salaire, prime, dateRecrutement, soldeConge, user_id)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDouble(1, st.getSalaire());
            ps.setDouble(2, st.getPrime() != null ? st.getPrime() : 0.0);
            ps.setDate(3, st.getDateRecrutement() != null ? Date.valueOf(st.getDateRecrutement()) : Date.valueOf(LocalDate.now()));
            ps.setInt(4, st.getSoldeConge() != null ? st.getSoldeConge() : 0);
            ps.setLong(5, st.getUserId());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    st.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Staff", e);
        }
    }

    @Override
    public void update(Staff st) {
        String sql = """
            UPDATE Staff 
            SET salaire = ?, prime = ?, dateRecrutement = ?, soldeConge = ?
            WHERE id = ?
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDouble(1, st.getSalaire());
            ps.setDouble(2, st.getPrime());
            ps.setDate(3, Date.valueOf(st.getDateRecrutement()));
            ps.setInt(4, st.getSoldeConge());
            ps.setLong(5, st.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Staff", e);
        }
    }

    @Override
    public void delete(Staff st) {
        deleteById(st.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Staff WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById Staff", e);
        }
    }

    @Override
    public Optional<Staff> findByUserId(Long userId) {
        String sql = "SELECT * FROM Staff WHERE user_id = ?";

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
    public List<Staff> findByDateRecrutement(LocalDate date) {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM Staff WHERE dateRecrutement = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByDateRecrutement", e);
        }
        return list;
    }

    @Override
    public List<Staff> findByDateRange(LocalDate debut, LocalDate fin) {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM Staff WHERE dateRecrutement BETWEEN ? AND ? ORDER BY dateRecrutement DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(debut));
            ps.setDate(2, Date.valueOf(fin));
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
    public Double calculateTotalSalaires() {
        String sql = "SELECT SUM(salaire + prime) FROM Staff";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur calculateTotalSalaires", e);
        }
        return 0.0;
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Staff";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur count Staff", e);
        }
        return 0;
    }

    private Staff mapRow(ResultSet rs) throws SQLException {
        Staff st = new Staff();
        st.setId(rs.getLong("id"));
        st.setSalaire(rs.getDouble("salaire"));
        st.setPrime(rs.getDouble("prime"));

        Date dr = rs.getDate("dateRecrutement");
        if (dr != null) st.setDateRecrutement(dr.toLocalDate());

        st.setSoldeConge(rs.getInt("soldeConge"));
        st.setUserId(rs.getLong("user_id"));

        return st;
    }
}