package com.gmail.bishoybasily.quarkus.resource;

import io.quarkus.security.identity.SecurityIdentity;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author bishoybasily
 * @since 2020-04-06
 */
@RequiredArgsConstructor
@Path("/api/secured")
public class ResourceSecured {

    private final SecurityIdentity securityIdentity;

    @GET
    @Path("/a")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    @NoCache
    public Single<String> a() {
        return Single.just("a is allowed for admin only like you: " + securityIdentity.getPrincipal().getName());
    }

    @GET
    @Path("/b")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    @NoCache
    public Single<String> b() {
        return Single.just("b is allowed for users only like you: " + securityIdentity.getPrincipal().getName());
    }

}
