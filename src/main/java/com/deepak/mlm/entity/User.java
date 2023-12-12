package com.deepak.mlm.entity;

import com.deepak.mlm.enums.Level;
import com.deepak.mlm.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is our user entity with multiple fields
 * @author Sudo-Deepak
 */
@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String referralCode;
    @OneToMany(targetEntity = ReferralHistory.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<ReferralHistory> referralHistories = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Level level;
}
