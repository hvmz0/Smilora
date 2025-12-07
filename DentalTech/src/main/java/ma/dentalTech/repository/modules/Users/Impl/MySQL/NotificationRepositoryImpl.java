package ma.dentalTech.repository.modules.Users.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Users.Notification;
import ma.dentalTech.entities.Enums.TypeEnum;
import ma.dentalTech.repository.modules.Users.Api.NotificationRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationRepositoryImpl implements NotificationRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<Notification> findAll() {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM Notification ORDER BY date DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Notification", e);
        }
        return list;
    }

    @Override
    public Notification findById(Long id) {
        String sql = "SELECT * FROM Notification WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Notification", e);
        }
        return null;
    }

    @Override
    public void create(Notification n) {
        String sql = "INSERT INTO Notification (titre, message, date, time, type, priorite, lu, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, n.getTitre());
            ps.setString(2, n.getMessage());
            ps.setDate(3, Date.valueOf(n.getDate()));

            if (n.getTime() != null) ps.setTime(4, Time.valueOf(n.getTime()));
            else ps.setNull(4, Types.TIME);

            if (n.getType() != null) ps.setString(5, n.getType().name());
            else ps.setNull(5, Types.VARCHAR);

            ps.setString(6, n.getPriorite());
            ps.setBoolean(7, n.isLu());
            ps.setLong(8, n.getUserId());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) n.setId(keys.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Notification", e);
        }
    }

    @Override
    public void update(Notification n) {
        String sql = "UPDATE Notification SET titre=?, message=?, date=?, time=?, type=?, priorite=?, lu=?, user_id=? WHERE id=?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, n.getTitre());
            ps.setString(2, n.getMessage());
            ps.setDate(3, Date.valueOf(n.getDate()));

            if (n.getTime() != null) ps.setTime(4, Time.valueOf(n.getTime()));
            else ps.setNull(4, Types.TIME);

            if (n.getType() != null) ps.setString(5, n.getType().name());
            else ps.setNull(5, Types.VARCHAR);

            ps.setString(6, n.getPriorite());
            ps.setBoolean(7, n.isLu());
            ps.setLong(8, n.getUserId());
            ps.setLong(9, n.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Notification", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Notification WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur delete Notification", e);
        }
    }

    @Override
    public List<Notification> findByPriorite(String priorite) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM Notification WHERE priorite = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, priorite);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByPriorite", e);
        }
        return list;
    }

    @Override
    public List<Notification> findByUserId(Long userId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM Notification WHERE user_id = ? ORDER BY date DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByUserId", e);
        }
        return list;
    }

    private Notification mapRow(ResultSet rs) throws SQLException {
        Notification n = new Notification();

        n.setId(rs.getLong("id"));
        n.setTitre(rs.getString("titre"));
        n.setMessage(rs.getString("message"));

        Date d = rs.getDate("date");
        if (d != null) n.setDate(d.toLocalDate());

        Time t = rs.getTime("time");
        if (t != null) n.setTime(t.toLocalTime());

        String typeStr = rs.getString("type");
        if (typeStr != null) {
            try {
                n.setType(TypeEnum.valueOf(typeStr));
            } catch (IllegalArgumentException ignored) {}
        }

        n.setPriorite(rs.getString("priorite"));
        n.setLu(rs.getBoolean("lu"));
        n.setUserId(rs.getLong("user_id"));

        return n;
    }
}
