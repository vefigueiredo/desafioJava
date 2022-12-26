package com.desafioJava.core.interfaces;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import java.io.IOException;


public interface NotaFiscalService {

    void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException;

}
