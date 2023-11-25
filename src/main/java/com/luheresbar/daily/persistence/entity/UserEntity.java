package com.luheresbar.daily.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "user_id", nullable = false, length = 25)
    private String userId;

    @Column(nullable = false, length = 25)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(length = 50)
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<AccountEntity> accounts;

    @OneToMany(mappedBy = "user")
    private List<CategoryEntity> categories;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserRoleEntity> roles;

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", accounts=" + accounts +
                ", categories=" + categories +
                ", roles=" + roles +
                '}';
    }
}
