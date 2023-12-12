package com.deepak.mlm.dto.request;

import com.deepak.mlm.enums.Level;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is the UserDTO, It will use when we register a user.
 * @author Sudo-Deepak
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Level level;
}
