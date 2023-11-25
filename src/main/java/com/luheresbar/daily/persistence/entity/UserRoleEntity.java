package com.luheresbar.daily.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_roles")
@IdClass(UserRolePK.class)
@Getter
@Setter
public class UserRoleEntity {

    @Id
    @Column(nullable = false)
    private String role;

    @Id
    @Column(name = "user_id", nullable = false, length = 25)
    private String userId;

    @Column(name = "granted_date", nullable = false)
    private LocalDateTime grantedDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private UserEntity user;

}
