package com.deepak.mlm.service.impl;

import com.deepak.mlm.dto.request.LoginDTO;
import com.deepak.mlm.dto.request.ReferralDTO;
import com.deepak.mlm.dto.request.UserDTO;
import com.deepak.mlm.entity.ReferralHistory;
import com.deepak.mlm.entity.User;
import com.deepak.mlm.mapper.UserMapper;
import com.deepak.mlm.repository.UserRepository;
import com.deepak.mlm.security.jwt.JwtTokenProvider;
import com.deepak.mlm.service.UserService;
import com.deepak.mlm.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.deepak.mlm.enums.Role.ADMIN;
import static com.deepak.mlm.enums.Role.USER;

/**
 * This is our User Service Impl class, which has the following method.
 * @author Sudo-Deepak
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String getCurrentLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO registerUser(UserDTO userDTO, boolean isAdmin) {
        User user = userMapper.toEntity(userDTO);
        user.setReferralCode(CommonUtil.generateReferralCode());
        user.setRole(isAdmin ? ADMIN : USER);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateToken(authentication);
    }

    @Override
    public boolean referUser(ReferralDTO referralDTO) {
        boolean isSuccess = false;
        String currentLoggedInUser = getCurrentLoggedInUser();
        if (!StringUtils.isEmpty(currentLoggedInUser)) {
            User user = userRepository.findByEmail(currentLoggedInUser).orElse(null);
            if (null != user) {
                user.getReferralHistories().add(new ReferralHistory(null, referralDTO.getEmail()));
                userRepository.save(user);
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    @Override
    public int getCommission(String email) {
        int result = 0;
        User user = userRepository.findByEmail(email).orElse(null);
        if (null != user) {
            result = CommonUtil.calculateAndGetCommission(user.getLevel());
        }
        return result;
    }
}
