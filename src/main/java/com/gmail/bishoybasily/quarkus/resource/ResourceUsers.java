package com.gmail.bishoybasily.quarkus.resource;

import com.gmail.bishoybasily.quarkus.model.User;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author bishoybasily
 * @since 2020-04-06
 */
@Path("/api/users")
public class ResourceUsers {

//    @Inject
//    private SecurityIdentity securityIdentity;

    @GET
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    @NoCache
    public User me() {
//        String name = securityIdentity.getPrincipal().getName();
        String name = "bishoybasily";
        return new User().setUsername(name);
    }

}
