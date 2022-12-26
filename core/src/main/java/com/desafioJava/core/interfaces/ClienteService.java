package com.desafioJava.core.interfaces;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import java.io.IOException;

public interface ClienteService {

    void doPost(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws IOException;

    void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws IOException;

    void doPut(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws IOException;

    void doDelete(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws IOException;

}
