package com.gmail.bishoybasily.quarkus.model.dao;

import com.gmail.bishoybasily.quarkus.model.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.TransactionManager;

@RequiredArgsConstructor
@ApplicationScoped
public class UsersDao implements JpaDao<User, String, Object> {

    @Getter
    private final EntityManager entityManager;
    @Getter
    private final TransactionManager transactionManager;

}
