package com.deepak.mlm.service;

import com.deepak.mlm.dto.request.LoginDTO;
import com.deepak.mlm.dto.request.ReferralDTO;
import com.deepak.mlm.dto.request.UserDTO;

/**
 * This is our User Service interface. where I have declared 4 methods.
 */
public interface UserService {
    UserDTO registerUser(UserDTO userDTO, boolean isAdmin);

    String login(LoginDTO loginDTO);

    boolean referUser(ReferralDTO referralDTO);

    int getCommission(String email);
}
