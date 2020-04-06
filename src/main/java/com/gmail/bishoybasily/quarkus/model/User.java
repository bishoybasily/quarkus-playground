package com.gmail.bishoybasily.quarkus.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author bishoybasily
 * @since 2020-04-06
 */
@Data
@Accessors(chain = true)
public class User {

    private String username;

}
