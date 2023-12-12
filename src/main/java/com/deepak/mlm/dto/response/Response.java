package com.deepak.mlm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is our generic response class.
 * @author Sudo-Deepak
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private boolean status;
    private String description;
    private Object data;
}
