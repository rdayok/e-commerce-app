package com.rdi.ecommerce.data.model;

import com.rdi.ecommerce.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import static com.rdi.ecommerce.enums.Role.USER;

@Entity
@Setter
@Getter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String profilePicture;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role = USER;
    private LocalDateTime dateRegistered;

    @PrePersist
    public void setDateRegistered() {
        dateRegistered = LocalDateTime.now();
    }
}
