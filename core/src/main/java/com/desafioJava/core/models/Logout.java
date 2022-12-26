package com.desafioJava.core.models;

import com.google.gson.annotations.Expose;
import org.apache.sling.models.annotations.Model;
import org.osgi.resource.Resource;

@Model(adaptables = Resource.class)
public class Logout {
    @Expose
    private String token;

    public Logout(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
