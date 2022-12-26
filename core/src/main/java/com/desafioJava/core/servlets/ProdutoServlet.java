
package com.desafioJava.core.servlets;

import com.desafioJava.core.interfaces.ProdutoService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.apache.sling.api.servlets.ServletResolverConstants.*;

@Component(service = {Servlet.class}, property = {
        SLING_SERVLET_PATHS + "=" + "/bin/desafioJava/produtos",
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_DELETE,
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_PUT,
        SLING_SERVLET_EXTENSIONS + "=" + "json"
})

public class ProdutoServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    private ProdutoService produtoService;

    @Activate
    public ProdutoServlet(@Reference ProdutoService produtoService) {
        this.produtoService=produtoService;
    }

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        produtoService.doGet(req,resp);
    }

    @Override
    protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        produtoService.doPost(req,resp);
    }

    @Override
    protected void doPut(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        produtoService.doPut(req,resp);
    }

    @Override
    protected void doDelete(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        produtoService.doDelete(req,resp);
    }

}

