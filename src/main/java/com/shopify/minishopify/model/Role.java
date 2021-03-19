package com.shopify.minishopify.model;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    public enum ERole {
        ROLE_ANON,  // Can Browse + purchase
        ROLE_USER,  // Can create/modify/own shops, create/modify/own products + {Role_Anon}
        ROLE_ADMIN  // all access
    }

    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ERole role;

    public Role() {}

    public Role(ERole role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

}
