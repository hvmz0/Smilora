package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.Certificat.Certificat;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des certificats médicaux
 * Author : Houssam
 */
public interface CertificatRepository extends CrudRepository<Certificat, Long> {

    /**
     * Trouve un certificat par consultation
     */
    Optional<Certificat> findByConsultationId(Long consultationId);

    /**
     * Trouve les certificats par date
     */
    List<Certificat> findByDate(LocalDate date);

    /**
     * Trouve les certificats dans une plage de dates
     */
    List<Certificat> findByDateRange(LocalDate dateDebut, LocalDate dateFin);

    /**
     * Trouve les certificats par durée minimale
     */
    List<Certificat> findByDureeMin(Long dureeMin);

    /**
     * Compte les certificats émis pour une consultation
     */
    long countByConsultation(Long consultationId);

    /**
     * Récupère les derniers certificats créés
     */
    List<Certificat> findRecent(int limit);

    /**
     * Vérifie si un certificat existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total de certificats
     */
    long count();

    /**
     * Récupère une page de certificats
     */
    List<Certificat> findPage(int limit, int offset);
}