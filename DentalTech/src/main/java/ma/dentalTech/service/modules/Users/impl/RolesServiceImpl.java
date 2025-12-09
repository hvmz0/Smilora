package ma.dentalTech.service.modules.Users.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.common.validation.Validators;
import ma.dentalTech.entities.Users.Roles;
import ma.dentalTech.repository.modules.Users.Api.RolesRepository;
import ma.dentalTech.service.modules.Users.Api.RolesService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class RolesServiceImpl implements RolesService {

    private final RolesRepository rolesRepository;

    @Override
    public List<Roles> getAllRoles() throws ServiceException {
        try {
            return rolesRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des rôles", e);
        }
    }

    @Override
    public Optional<Roles> getRoleById(Long id) throws ServiceException {
        try {
            Roles role = rolesRepository.findById(id);
            return Optional.ofNullable(role);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du rôle avec l'ID: " + id, e);
        }
    }

    @Override
    public Roles createRole(Roles role) throws ServiceException, ValidationException {
        try {
            validateRole(role);
            rolesRepository.create(user);
            return role;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du rôle", e);
        }
    }

    @Override
    public Roles updateRole(Roles role) throws ServiceException, ValidationException {
        try {
            if (role.getId() == null) {
                throw new ValidationException("L'ID du rôle est requis pour la mise à jour");
            }
            validateRole(role);
            rolesRepository.update(role);
            return role;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du rôle", e);
        }
    }

    @Override
    public void deleteRole(Long id) throws ServiceException {
        try {
            Roles role = rolesRepository.findById(id);
            if (role == null) {
                throw new ServiceException("Rôle non trouvé avec l'ID: " + id);
            }
            rolesRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du rôle", e);
        }
    }

    @Override
    public Optional<Optional<Roles>> getRoleByLibelle(String libelle) throws ServiceException {
        try {
            Optional<Roles> role = rolesRepository.findByLibelle(libelle);
            return Optional.ofNullable(role);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche du rôle par libellé", e);
        }
    }

    @Override
    public Object searchRoles(String keyword) throws ServiceException {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return getAllRoles();
            }
            return rolesRepository.findByLibelle(keyword);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de rôles", e);
        }
    }

    @Override
    public long getTotalRoles() throws ServiceException {
        try {
            return rolesRepository.create(user);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des rôles", e);
        }
    }

    private void validateRole(Roles role) throws ValidationException {
        Validators.notBlank(role.getLibelle(), "Le libellé du rôle");
    }
}
