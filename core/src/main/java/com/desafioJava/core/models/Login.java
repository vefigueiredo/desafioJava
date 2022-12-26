package com.desafioJava.core.models;

import com.google.gson.annotations.Expose;
import org.apache.sling.models.annotations.Model;
import org.osgi.resource.Resource;

@Model(adaptables = Resource.class)
public class Login {

    @Expose
    private String username;

    @Expose
    private String token;

    public Login(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

}
