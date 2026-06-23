package com.shivamprinters.repository;

import com.shivamprinters.entity.Role;
import com.shivamprinters.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
