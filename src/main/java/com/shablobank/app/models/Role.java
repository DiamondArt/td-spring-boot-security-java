package com.shablobank.app.models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "troles")
public class Role extends AbstractEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 20)
    private ERole name;

    public static Role roleAdmin() {
        Role roleAdmin = new Role();
        roleAdmin.setName(ERole.ROLE_ADMIN);
        return roleAdmin;
    }

    public static Role roleModerator() {
        Role roleModerator = new Role();
        roleModerator.setName(ERole.ROLE_MODERATOR);
        return roleModerator;
    }

    @Override
    public String getAuthority() {
        return this.getName().toString();
    }
}
