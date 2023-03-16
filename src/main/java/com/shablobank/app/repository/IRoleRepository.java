package com.shablobank.app.repository;


import com.shablobank.app.models.ERole;
import com.shablobank.app.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
