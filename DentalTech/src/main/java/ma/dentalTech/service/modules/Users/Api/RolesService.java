package ma.dentalTech.service.modules.Users.Api;

import ma.dentalTech.entities.Users.Roles;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public interface RolesService {

    // CRUD Operations
    List<Roles> getAllRoles() throws ServiceException;
    Optional<Roles> getRoleById(Long id) throws ServiceException;
    Roles createRole(Roles role) throws ServiceException, ValidationException;
    Roles updateRole(Roles role) throws ServiceException, ValidationException;
    void deleteRole(Long id) throws ServiceException;

    // Recherche
    Optional<Optional<Roles>> getRoleByLibelle(String libelle) throws ServiceException;
    Object searchRoles(String keyword) throws ServiceException;

    // Comptage
    long getTotalRoles() throws ServiceException;
}
