package com.shablobank.app.service;

import com.shablobank.app.controller.exception.EntityException;
import com.shablobank.app.models.Role;

import java.util.List;

public interface IRoleService {
    public Role saveRole(Role role);

    List<Role> fetchRoleList();

    Role fetchRole(Long idARole) throws EntityException;

    void deleteRole(Long idRole) throws EntityException;

    Role updateRole(Long idRole, Role role) throws EntityException;
}
