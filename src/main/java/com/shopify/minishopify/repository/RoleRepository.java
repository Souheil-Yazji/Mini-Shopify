package com.shopify.minishopify.repository;

import com.shopify.minishopify.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(Role.ERole role);
}
