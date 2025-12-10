package ma.dentalTech.service.modules.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.entities.Enums.TypeEnum;
import ma.dentalTech.entities.Users.Notification;
import ma.dentalTech.repository.modules.Users.Api.NotificationRepository;
import ma.dentalTech.service.modules.Api.NotificationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> getAllNotifications() throws ServiceException {
        try {
            return notificationRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des notifications", e);
        }
    }

    @Override
    public Optional<Notification> getNotificationById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(notificationRepository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la notification", e);
        }
    }

    @Override
    public Notification createNotification(Notification notification) throws ServiceException, ValidationException {
        try {
            validateNotification(notification);
            notificationRepository.create(user);
            return notification;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de la notification", e);
        }
    }

    @Override
    public Notification updateNotification(Notification notification) throws ServiceException, ValidationException {
        try {
            if (notification.getId() == null) {
                throw new ValidationException("L'ID de la notification est requis");
            }
            validateNotification(notification);
            notificationRepository.update(notification);
            return notification;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de la notification", e);
        }
    }

    @Override
    public void deleteNotification(Long id) throws ServiceException {
        try {
            notificationRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de la notification", e);
        }
    }

    @Override
    public List<Notification> getNotificationsByPriorite(String priorite) throws ServiceException {
        try {
            return notificationRepository.findByPriorite(priorite);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des notifications par priorité", e);
        }
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) throws ServiceException {
        try {
            return notificationRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des notifications de l'utilisateur", e);
        }
    }

    @Override
    public List<Notification> getNotificationsByType(TypeEnum type) throws ServiceException {
        try {
            return notificationRepository.findByType(type);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des notifications par type", e);
        }
    }

    @Override
    public List<Notification> getNotificationsByDate(LocalDate date) throws ServiceException {
        try {
            return notificationRepository.findByDate(date);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des notifications par date", e);
        }
    }

    @Override
    public List<Notification> getUnreadNotifications(Long userId) throws ServiceException {
        try {
            return notificationRepository.findUnread(userId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des notifications non lues", e);
        }
    }

    @Override
    public void markAsRead(Long notificationId) throws ServiceException {
        try {
            notificationRepository.markAsRead(notificationId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du marquage de la notification comme lue", e);
        }
    }

    @Override
    public long countUnread(Long userId) throws ServiceException {
        try {
            return notificationRepository.countUnread(userId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des notifications non lues", e);
        }
    }

    private void validateNotification(Notification notification) throws ValidationException {
        if (notification.getUserId() == null) {
            throw new ValidationException("L'utilisateur est obligatoire");
        }
        if (notification.getMessage() == null || notification.getMessage().trim().isEmpty()) {
            throw new ValidationException("Le message est obligatoire");
        }
    }
}
