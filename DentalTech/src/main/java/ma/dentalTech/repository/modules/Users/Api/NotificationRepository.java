package ma.dentalTech.repository.modules.Users.Api;

import ma.dentalTech.entities.Users.Notification;
import ma.dentalTech.entities.Enums.TypeEnum;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {


    List<Notification> findByPriorite(String priorite);


    List<Notification> findByUserId(Long userId);


    List<Notification> findByType(TypeEnum type);
    List<Notification> findByDate(LocalDate date);


    List<Notification> findUnread(Long userId);
    void markAsRead(Long notificationId);
    long countUnread(Long userId);
}