package com.gmail.bishoybasily.quarkus;

import com.gmail.bishoybasily.quarkus.model.entity.User;
import com.gmail.bishoybasily.quarkus.model.service.UsersService;
import com.google.common.collect.ImmutableSet;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.reactivex.Observable;
import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@RequiredArgsConstructor
@ApplicationScoped
public class Listener {

    private final UsersService usersService;

    public void onStart(@Observes StartupEvent ev) {

        Observable.fromArray(
                new User()
                        .setUsername("bishoy")
                        .setName("bishoy basily")
                        .setPassword("bishoy")
                        .setRoles(ImmutableSet.of("user", "admin")),
                new User()
                        .setUsername("susana")
                        .setName("susana armia")
                        .setPassword("susana")
                        .setRoles(ImmutableSet.of("user"))
        ).flatMapSingle(usersService::add).subscribe();

    }

    public void onStop(@Observes ShutdownEvent ev) {

    }

}
