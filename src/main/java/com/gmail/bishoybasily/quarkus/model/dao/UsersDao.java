package com.gmail.bishoybasily.quarkus.model.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.TransactionManager;

@RequiredArgsConstructor
@ApplicationScoped
public class UsersDao implements JpaDao {

    @Getter
    private final EntityManager entityManager;
    @Getter
    private final TransactionManager transactionManager;

}
