package com.luheresbar.daily.persistence.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "user_id", nullable = false, length = 25)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false, length = 25)
    private String username;

    @Column(nullable = false, length = 100)
    @JsonIgnore
    private String password;

    @Column(length = 50)
    private String email;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<AccountEntity> accounts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<CategoryEntity> categories;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserRoleEntity> roles;

}
