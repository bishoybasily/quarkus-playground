package com.gmail.bishoybasily.quarkus.resource;

import com.gmail.bishoybasily.quarkus.model.entity.User;
import com.gmail.bishoybasily.quarkus.model.service.UsersService;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author bishoybasily
 * @since 2020-04-06
 */
@RequiredArgsConstructor
@Path("/api/users")
public class ResourceUsers {

    //    private final SecurityIdentity securityIdentity;
    private final UsersService usersService;

//    @GET
//    @Path("/me")
//    @Produces(MediaType.APPLICATION_JSON)
//    @NoCache
//    public Single<User> me() {
////        return Single.fromCallable(() -> {
////            //        String name = securityIdentity.getPrincipal().getName();
////            String name = "bishoybasily - reactive call";
////            return new User().setUsername(name);
////        });
//
//        return usersService.persist(new User().setName("base dao"));
//
//    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @NoCache
    public Single<User> one(@PathParam("id") String id) {
        return usersService.one(id);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @NoCache
    public Observable<User> all() {
        return usersService.all();
    }

}
