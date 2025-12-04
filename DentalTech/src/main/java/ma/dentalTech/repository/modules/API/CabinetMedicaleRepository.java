package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.CabinetMedicale.CabinetMedicale;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion du cabinet médical
 * Author : Houssam
 */
public interface CabinetMedicaleRepository extends CrudRepository<CabinetMedicale, Long> {

    /**
     * Trouve un cabinet par son email
     */
    Optional<CabinetMedicale> findByEmail(String email);

    /**
     * Trouve un cabinet par son nom
     */
    Optional<CabinetMedicale> findByNom(String nom);

    /**
     * Trouve tous les cabinets actifs
     */
    List<CabinetMedicale> findByActif(boolean isActif);

    /**
     * Trouve tous les cabinets avec présence web
     */
    List<CabinetMedicale> findByIsWeb(boolean isWeb);

    /**
     * Met à jour le statut actif d'un cabinet
     */
    void updateActifStatus(Long id, boolean isActif);

    /**
     * Met à jour le statut web d'un cabinet
     */
    void updateWebStatus(Long id, boolean isWeb);

    /**
     * Recherche des cabinets par mot-clé (nom ou adresse)
     */
    List<CabinetMedicale> searchByKeyword(String keyword);

    /**
     * Vérifie si un cabinet existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total de cabinets
     */
    long count();

    /**
     * Récupère une page de cabinets
     */
    List<CabinetMedicale> findPage(int limit, int offset);
}