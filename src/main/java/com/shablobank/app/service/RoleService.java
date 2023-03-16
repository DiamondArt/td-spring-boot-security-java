package com.shablobank.app.service;

import com.shablobank.app.controller.exception.EntityException;
import com.shablobank.app.models.Role;
import com.shablobank.app.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RoleService implements  IRoleService{
    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> fetchRoleList() {
        return roleRepository.findAll();
    }

    @Override
    public Role fetchRole(Long idRole) throws EntityException {
        Optional<Role> role = roleRepository.findById(idRole);
        if (!role.isPresent()) {
            throw new EntityException("Analyse not found");
        }
        return role.get();
    }

    @Override
    public void deleteRole(Long idRole) throws EntityException {
        Optional<Role> role = roleRepository.findById(idRole);
        if (!role.isPresent()) {
            throw new EntityException("Analyse not available ");
        }
        roleRepository.deleteById(idRole);
    }

    @Override
    public Role updateRole(Long idRole, Role role) throws EntityException {
        Optional<Role> findRole = roleRepository.findById(idRole);
        if (!findRole.isPresent()) {
            throw new EntityException("No value present");
        }

        Role roleDb = roleRepository.findById(idRole).get();
        if(Objects.nonNull(role.getName())){
            roleDb.setName(role.getName());
        }
        return roleRepository.save(roleDb);
    }
}
