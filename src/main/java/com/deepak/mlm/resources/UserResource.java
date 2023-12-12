package com.deepak.mlm.resources;

import com.deepak.mlm.dto.request.LoginDTO;
import com.deepak.mlm.dto.request.ReferralDTO;
import com.deepak.mlm.dto.request.UserDTO;
import com.deepak.mlm.dto.response.Response;
import com.deepak.mlm.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.deepak.mlm.constants.Constants.BASE_URL;
import static com.deepak.mlm.constants.Constants.URLConstants.*;
import static org.springframework.http.HttpStatus.*;

/**
 * This is our resource class, where we have implemented our endpoints
 * @author Sudo-Deepak
 */
@RestController
@RequestMapping(BASE_URL)
public class UserResource {
    @Autowired
    private UserService userService;

    /**
     * This endpoint is responsible for registering user.
     * @param userDTO
     * @param isAdmin
     * @return User data with description.
     */
    @PostMapping(REGISTER_USER)
    public ResponseEntity<Response> registerUser(@RequestBody UserDTO userDTO, @RequestParam(defaultValue = "false") boolean isAdmin) {
        HttpStatus status = INTERNAL_SERVER_ERROR;
        Response response = new Response();
        response.setDescription("Something went wrong!");
        UserDTO savedUser = userService.registerUser(userDTO, isAdmin);
        if (null != savedUser && null != savedUser.getId()) {
            response.setStatus(true);
            response.setDescription("User registered successfully!");
            response.setData(savedUser);
            status = CREATED;
        }
        return new ResponseEntity<>(response, status);
    }

    /**
     * This endpoint is used for login.
     * @param loginDTO
     * @return Response with jwt token.
     */
    @PostMapping(LOGIN)
    public ResponseEntity<Response> login(@RequestBody LoginDTO loginDTO) {
        HttpStatus status = BAD_REQUEST;
        Response response = new Response();
        response.setDescription("Credentials are incorrect!");
        String token = userService.login(loginDTO);
        if (!StringUtils.isEmpty(token)) {
            response.setStatus(true);
            response.setDescription("User logged in successfully!");
            response.setData(token);
            status = OK;
        }
        return new ResponseEntity<>(response, status);
    }

    /**
     * This endpoint is used for refer another user.
     * @param referralDTO
     * @return Response with description.
     */
    @PatchMapping(REFER)
    public ResponseEntity<Response> referUser(@RequestBody ReferralDTO referralDTO) {
        HttpStatus status = INTERNAL_SERVER_ERROR;
        Response response = new Response();
        response.setDescription("Something went wrong!");
        boolean isReferred = userService.referUser(referralDTO);
        if (isReferred) {
            response.setStatus(true);
            response.setDescription("User referred successfully!");
            status = OK;
        }
        return new ResponseEntity<>(response, status);
    }

    /**
     * This endpoint is used for get commission.
     * @param email
     * @return Commission percentage.
     */
    @GetMapping(GET_COMMISSION)
    public ResponseEntity<Response> getCommission(@RequestParam String email) {
        Response response = new Response();
        int commission = userService.getCommission(email);
        response.setStatus(true);
        response.setDescription("Commission fetched successfully!");
        response.setData(commission + " %");
        return new ResponseEntity<>(response, OK);
    }
}
