package ma.dentalTech.service.modules.impl;

import lombok.RequiredArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.common.exceptions.AuthException;
import ma.dentalTech.common.validation.Validators;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.Users.Api.UserRepository;
import ma.dentalTech.service.modules.Api.UserService;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    @Override
    public List<User> getAllUsers() throws ServiceException {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des utilisateurs", e);
        }
    }

    @Override
    public Optional<User> getUserById(Long id) throws ServiceException {
        try {
            User user = userRepository.findById(id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de l'utilisateur avec l'ID: " + id, e);
        }
    }

    @Override
    public User createUser(User user) throws ServiceException, ValidationException {
        try {
            validateUser(user);

            // Vérifier l'unicité du login et email
            if (userRepository.existsByLogin(user.getLogin())) {
                throw new ValidationException("Ce login est déjà utilisé");
            }
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new ValidationException("Cet email est déjà utilisé");
            }

            // Hasher le mot de passe
            user.setMotDePass(hashPassword(user.getMotDePass()));

            userRepository.create(user);
            return user;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de l'utilisateur", e);
        }
    }

    @Override
    public User updateUser(User user) throws ServiceException, ValidationException {
        try {
            if (user.getId() == null) {
                throw new ValidationException("L'ID de l'utilisateur est requis pour la mise à jour");
            }

            validateUser(user);
            userRepository.update(user);
            return user;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de l'utilisateur", e);
        }
    }

    @Override
    public void deleteUser(Long id) throws ServiceException {
        try {
            User user = userRepository.findById(id);
            if (user == null) {
                throw new ServiceException("Utilisateur non trouvé avec l'ID: " + id);
            }
            userRepository.deleteById(id);  // Gardez cette ligne telle quelle
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de l'utilisateur", e);
        }
    }

    @Override
    public User authenticate(String login, String password) throws AuthException {
        try {
            Optional<User> userOpt = userRepository.findByLogin(login);
            if (userOpt.isEmpty()) {
                throw new AuthException("Identifiants invalides");  // Supprimé le ", e"
            }

            User user = userOpt.get();
            if (!verifyPassword(password, user.getMotDePass())) {
                throw new AuthException("Identifiants invalides");  // Supprimé le ", e"
            }

            // Mettre à jour le dernier login
            userRepository.updateLastLogin(user.getId());
            return user;

        } catch (AuthException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthException("Erreur lors de l'authentification");
        }
    }
    @Override
    public Optional<User> getUserByEmail(String email) throws ServiceException {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de l'utilisateur par email", e);
        }
    }

    @Override
    public Optional<User> getUserByLogin(String login) throws ServiceException {
        try {
            return userRepository.findByLogin(login);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de l'utilisateur par login", e);
        }
    }

    @Override
    public Optional<User> getUserByCin(String cin) throws ServiceException {
        try {
            return userRepository.findByCin(cin);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de l'utilisateur par CIN", e);
        }
    }

    @Override
    public List<User> getUsersByRole(Long roleId) throws ServiceException {
        try {
            return userRepository.findByRole(roleId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des utilisateurs par rôle", e);
        }
    }

    @Override
    public List<User> searchUsers(String keyword) throws ServiceException {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return getAllUsers();
            }
            return userRepository.searchByNom(keyword);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche d'utilisateurs", e);
        }
    }

    @Override
    public boolean emailExists(String email) throws ServiceException {
        try {
            return userRepository.existsByEmail(email);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la vérification de l'email", e);
        }
    }

    @Override
    public boolean loginExists(String login) throws ServiceException {
        try {
            return userRepository.existsByLogin(login);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la vérification du login", e);
        }
    }

    @Override
    public void updateLastLogin(Long userId) throws ServiceException {
        try {
            userRepository.updateLastLogin(userId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du dernier login", e);
        }
    }

    @Override
    public long getTotalUsers() throws ServiceException {
        try {
            return userRepository.count();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des utilisateurs", e);
        }
    }

    private void validateUser(User user) throws ValidationException {
        Validators.notBlank(user.getLogin(), "Le login");
        Validators.notBlank(user.getMotDePass(), "Le mot de passe");
        Validators.email(user.getEmail());
    }

    // ============ Méthodes de cryptage (internes au service) ============

    private String hashPassword(String password) {
        try {
            byte[] salt = generateSalt();
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Erreur lors du hashage du mot de passe", e);
        }
    }

    private boolean verifyPassword(String password, String storedHash) {
        try {
            String[] parts = storedHash.split(":");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] hash = Base64.getDecoder().decode(parts[1]);

            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] testHash = factory.generateSecret(spec).getEncoded();

            return slowEquals(hash, testHash);
        } catch (Exception e) {
            return false;
        }
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }
}