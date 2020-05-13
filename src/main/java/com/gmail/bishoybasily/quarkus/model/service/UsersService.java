package com.gmail.bishoybasily.quarkus.model.service;

import com.gmail.bishoybasily.quarkus.model.dao.UsersDao;
import com.gmail.bishoybasily.quarkus.model.entity.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;

@RequiredArgsConstructor
@ApplicationScoped
public class UsersService {

    private final UsersDao usersDao;

    public Single<User> one(String id) {
        return usersDao.find(User.class, id);
    }

    public Single<User> add(User user) {
        return Single.just(user)
                .map(it -> it.setPassword(BcryptUtil.bcryptHash(it.getPassword())))
                .flatMap(usersDao::persist);
    }

    public Observable<User> all() {
        return usersDao.find(User.class);
    }

}
