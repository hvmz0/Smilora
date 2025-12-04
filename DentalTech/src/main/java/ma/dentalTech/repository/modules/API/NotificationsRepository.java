package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.Notifications.Notification;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour la gestion des notifications
 * Author : Hamza
 */
public interface NotificationsRepository extends CrudRepository<Notification, Long> {

    /**
     * Trouve les notifications d'un receveur
     */
    List<Notification> findByReceveurId(Long receveurId);

    /**
     * Trouve les notifications d'un envoyeur
     */
    List<Notification> findByEnvoyeurId(Long envoyeurId);

    /**
     * Trouve les notifications par type
     */
    List<Notification> findByType(String typeEnum);

    /**
     * Trouve les notifications par niveau
     */
    List<Notification> findByNiveau(String niveauEnum);

    /**
     * Trouve les notifications non lues pour un utilisateur
     */
    List<Notification> findUnreadByReceveur(Long receveurId);

    /**
     * Trouve les notifications dans une plage de dates
     */
    List<Notification> findByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Recherche dans les notifications par message
     */
    List<Notification> searchByMessage(String keyword);

    /**
     * Trouve les dernières notifications pour un utilisateur
     */
    List<Notification> findRecentByReceveur(Long receveurId, int limit);

    /**
     * Marque une notification comme lue
     */
    void markAsRead(Long notificationId);

    /**
     * Marque toutes les notifications d'un utilisateur comme lues
     */
    void markAllAsReadByReceveur(Long receveurId);

    /**
     * Compte les notifications non lues pour un utilisateur
     */
    long countUnreadByReceveur(Long receveurId);

    /**
     * Compte les notifications par type
     */
    long countByType(String typeEnum);

    /**
     * Supprime les anciennes notifications
     */
    void deleteOlderThan(LocalDateTime date);

    /**
     * Vérifie si une notification existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total de notifications
     */
    long count();

    /**
     * Récupère une page de notifications
     */
    List<Notification> findPage(int limit, int offset);
}