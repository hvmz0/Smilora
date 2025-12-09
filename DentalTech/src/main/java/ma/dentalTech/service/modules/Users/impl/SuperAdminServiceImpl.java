package ma.dentalTech.service.modules.Users.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.entities.Users.SuperAdmin;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.Users.Api.SuperAdminRepository;
import ma.dentalTech.service.modules.Users.Api.SuperAdminService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private final SuperAdminRepository superAdminRepository;

    @Override
    public List<SuperAdmin> getAllSuperAdmins() throws ServiceException {
        try {
            return superAdminRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des super administrateurs", e);
        }
    }

    @Override
    public Optional<SuperAdmin> getSuperAdminById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(superAdminRepository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du super administrateur", e);
        }
    }

    @Override
    public SuperAdmin createSuperAdmin(SuperAdmin superAdmin) throws ServiceException, ValidationException {
        try {
            validateSuperAdmin(superAdmin);
            User user = null;
            long l = superAdminRepository.create(user);
            return superAdmin;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du super administrateur", e);
        }
    }

    @Override
    public SuperAdmin updateSuperAdmin(SuperAdmin superAdmin) throws ServiceException, ValidationException {
        try {
            if (superAdmin.getId() == null) {
                throw new ValidationException("L'ID du super administrateur est requis");
            }
            validateSuperAdmin(superAdmin);
            superAdminRepository.update(superAdmin);
            return superAdmin;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du super administrateur", e);
        }
    }

    @Override
    public void deleteSuperAdmin(Long id) throws ServiceException {
        try {
            superAdminRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du super administrateur", e);
        }
    }

    @Override
    public Optional<SuperAdmin> getSuperAdminByUserId(Long userId) throws ServiceException {
        try {
            return superAdminRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche du super administrateur par utilisateur", e);
        }
    }

    private void validateSuperAdmin(SuperAdmin superAdmin) throws ValidationException {
        if (superAdmin.getUserId() == null) {
            throw new ValidationException("L'utilisateur est obligatoire");
        }
    }
}
