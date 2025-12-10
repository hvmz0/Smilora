package ma.dentalTech.service.modules.Api;

import ma.dentalTech.entities.Users.Notification;
import ma.dentalTech.entities.Enums.TypeEnum;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NotificationService {

    List<Notification> getAllNotifications() throws ServiceException;
    Optional<Notification> getNotificationById(Long id) throws ServiceException;
    Notification createNotification(Notification notification) throws ServiceException, ValidationException;
    Notification updateNotification(Notification notification) throws ServiceException, ValidationException;
    void deleteNotification(Long id) throws ServiceException;

    List<Notification> getNotificationsByPriorite(String priorite) throws ServiceException;
    List<Notification> getNotificationsByUserId(Long userId) throws ServiceException;
    List<Notification> getNotificationsByType(TypeEnum type) throws ServiceException;
    List<Notification> getNotificationsByDate(LocalDate date) throws ServiceException;
    List<Notification> getUnreadNotifications(Long userId) throws ServiceException;
    void markAsRead(Long notificationId) throws ServiceException;
    long countUnread(Long userId) throws ServiceException;
}
