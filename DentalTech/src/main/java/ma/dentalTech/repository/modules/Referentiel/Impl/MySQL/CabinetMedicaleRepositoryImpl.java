package ma.dentalTech.repository.modules.Referentiel.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Referentiel.CabinetMedicale;
import ma.dentalTech.repository.modules.Referentiel.Api.CabinetMedicaleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CabinetMedicaleRepositoryImpl implements CabinetMedicaleRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<CabinetMedicale> findAll() {
        List<CabinetMedicale> list = new ArrayList<>();
        String sql = "SELECT * FROM CabinetMedicale";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll CabinetMedicale", e);
        }
        return list;
    }

    @Override
    public CabinetMedicale findById(Long id) {
        String sql = "SELECT * FROM CabinetMedicale WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById CabinetMedicale", e);
        }
        return null;
    }

    @Override
    public void create(CabinetMedicale cm) {
        String sql = """
            INSERT INTO CabinetMedicale (nom, email, logo, adresse, tel, tel2, siteWeb, instagram, facebook, description)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, cm.getNom());
            ps.setString(2, cm.getEmail());
            ps.setString(3, cm.getLogo());
            ps.setString(4, cm.getAdresse());
            ps.setString(5, cm.getTel());
            ps.setString(6, cm.getTel2());
            ps.setString(7, cm.getSiteWeb());
            ps.setString(8, cm.getInstagram());
            ps.setString(9, cm.getFacebook());
            ps.setString(10, cm.getDescription());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    cm.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create CabinetMedicale", e);
        }
    }

    @Override
    public void update(CabinetMedicale cm) {
        String sql = """
            UPDATE CabinetMedicale 
            SET nom = ?, email = ?, logo = ?, adresse = ?, tel = ?, tel2 = ?, 
                siteWeb = ?, instagram = ?, facebook = ?, description = ?
            WHERE id = ?
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, cm.getNom());
            ps.setString(2, cm.getEmail());
            ps.setString(3, cm.getLogo());
            ps.setString(4, cm.getAdresse());
            ps.setString(5, cm.getTel());
            ps.setString(6, cm.getTel2());
            ps.setString(7, cm.getSiteWeb());
            ps.setString(8, cm.getInstagram());
            ps.setString(9, cm.getFacebook());
            ps.setString(10, cm.getDescription());
            ps.setLong(11, cm.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update CabinetMedicale", e);
        }
    }

    @Override
    public void delete(CabinetMedicale cm) {
        deleteById(cm.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM CabinetMedicale WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById CabinetMedicale", e);
        }
    }

    @Override
    public Optional<CabinetMedicale> findByNom(String nom) {
        String sql = "SELECT * FROM CabinetMedicale WHERE nom = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, nom);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByNom", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<CabinetMedicale> findByEmail(String email) {
        String sql = "SELECT * FROM CabinetMedicale WHERE email = ?";

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
    public CabinetMedicale getInfo() {
        String sql = "SELECT * FROM CabinetMedicale LIMIT 1";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur getInfo", e);
        }
        return null;
    }

    private CabinetMedicale mapRow(ResultSet rs) throws SQLException {
        CabinetMedicale cm = new CabinetMedicale();
        cm.setId(rs.getLong("id"));
        cm.setNom(rs.getString("nom"));
        cm.setEmail(rs.getString("email"));
        cm.setLogo(rs.getString("logo"));
        cm.setAdresse(rs.getString("adresse"));
        cm.setTel(rs.getString("tel"));
        cm.setTel2(rs.getString("tel2"));
        cm.setSiteWeb(rs.getString("siteWeb"));
        cm.setInstagram(rs.getString("instagram"));
        cm.setFacebook(rs.getString("facebook"));
        cm.setDescription(rs.getString("description"));
        return cm;
    }
}