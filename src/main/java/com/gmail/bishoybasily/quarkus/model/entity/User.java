package com.gmail.bishoybasily.quarkus.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.bishoybasily.quarkus.model.IdGenerator;
import com.gmail.bishoybasily.quarkus.model.StringSetConverter;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author bishoybasily
 * @since 2020-04-06
 */
@Data
@Accessors(chain = true)
@UserDefinition
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = IdGenerator.NAME)
    @GenericGenerator(name = IdGenerator.NAME, strategy = IdGenerator.CLASS)
    private String id;
    private String name;
    @Username
    @Column(unique = true)
    private String username;
    @Password
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Roles
    @Convert(converter = StringSetConverter.class)
    private Set<String> roles = new HashSet<>();

    public User clearId() {
        this.id = null;
        return this;
    }

}
