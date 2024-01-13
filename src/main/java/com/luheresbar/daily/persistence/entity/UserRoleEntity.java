package com.luheresbar.daily.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Integer userId;

    @Column(name = "granted_date")
    private LocalDateTime grantedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    private UserEntity user;

}
