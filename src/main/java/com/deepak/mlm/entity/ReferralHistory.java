package com.deepak.mlm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * ReferralHistory entity is responsible for storing referral history data.
 * @author Sudo-Deepak
 */
@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ReferralHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String referralEmail;
}
