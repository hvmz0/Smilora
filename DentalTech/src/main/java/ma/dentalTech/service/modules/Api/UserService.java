package ma.dentalTech.service.modules.Api;

import ma.dentalTech.entities.Users.User;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.common.exceptions.AuthException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // CRUD Operations
    List<User> getAllUsers() throws ServiceException;
    Optional<User> getUserById(Long id) throws ServiceException;
    User createUser(User user) throws ServiceException, ValidationException;
    User updateUser(User user) throws ServiceException, ValidationException;
    void deleteUser(Long id) throws ServiceException;

    // Authentification
    User authenticate(String login, String password) throws AuthException;

    // Recherche
    Optional<User> getUserByEmail(String email) throws ServiceException;
    Optional<User> getUserByLogin(String login) throws ServiceException;
    Optional<User> getUserByCin(String cin) throws ServiceException;
    List<User> getUsersByRole(Long roleId) throws ServiceException;
    List<User> searchUsers(String keyword) throws ServiceException;

    // Vérification
    boolean emailExists(String email) throws ServiceException;
    boolean loginExists(String login) throws ServiceException;

    // Gestion
    void updateLastLogin(Long userId) throws ServiceException;

    // Comptage
    long getTotalUsers() throws ServiceException;
}
