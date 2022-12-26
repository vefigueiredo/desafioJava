package com.desafioJava.core.interfaces;

import com.desafioJava.core.models.Login;

public interface LoginDao {

    Login loginToken(Object username, String token);

    Login getUsername(String username);

    Login getToken(String token);

    Login loginToken(String username, String token);
}
