package com.gmail.bishoybasily.quarkus.resource;

import com.gmail.bishoybasily.quarkus.model.User;
import io.quarkus.security.identity.SecurityIdentity;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.inject.Inject;
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

    @Inject
    private SecurityIdentity securityIdentity;

    @GET
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    @NoCache
    public User me() {
        return new User().setUsername(securityIdentity.getPrincipal().getName());
    }

}
