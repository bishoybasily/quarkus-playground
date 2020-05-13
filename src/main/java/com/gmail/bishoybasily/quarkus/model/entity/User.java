package com.gmail.bishoybasily.quarkus.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.bishoybasily.quarkus.IdGenerator;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author bishoybasily
 * @since 2020-04-06
 */
@Data
@Accessors(chain = true)
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = IdGenerator.NAME)
    @GenericGenerator(name = IdGenerator.NAME, strategy = IdGenerator.CLASS)
    private String id;
    private String name;
    @Column(unique = true)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
