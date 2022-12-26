package com.desafioJava.core.interfaces;

import com.desafioJava.core.models.Logout;

public interface LogoutDao {

    Logout logoutToken(String token);

    boolean admin(Logout logout);


    Logout getToken(String token);
}
