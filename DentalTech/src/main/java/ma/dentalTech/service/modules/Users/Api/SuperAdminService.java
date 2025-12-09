package ma.dentalTech.service.modules.Users.Api;

import ma.dentalTech.entities.Users.SuperAdmin;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public interface SuperAdminService {

    List<SuperAdmin> getAllSuperAdmins() throws ServiceException;
    Optional<SuperAdmin> getSuperAdminById(Long id) throws ServiceException;
    SuperAdmin createSuperAdmin(SuperAdmin superAdmin) throws ServiceException, ValidationException;
    SuperAdmin updateSuperAdmin(SuperAdmin superAdmin) throws ServiceException, ValidationException;
    void deleteSuperAdmin(Long id) throws ServiceException;

    Optional<SuperAdmin> getSuperAdminByUserId(Long userId) throws ServiceException;
}
