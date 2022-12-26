package com.desafioJava.core.interfaces;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

public interface LoginService {

    void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException;


}
