package com.deepak.mlm.mapper;

import com.deepak.mlm.dto.request.UserDTO;
import com.deepak.mlm.entity.User;
import org.mapstruct.Mapper;

import static com.deepak.mlm.constants.Constants.SPRING;

/**
 * This is our user mapper interface, which is responsible for convert dto to entity and entity to dto
 * @author Sudo-Deepak
 */
@Mapper(componentModel = SPRING)
public interface UserMapper extends EntityMapper<User, UserDTO> {
}
