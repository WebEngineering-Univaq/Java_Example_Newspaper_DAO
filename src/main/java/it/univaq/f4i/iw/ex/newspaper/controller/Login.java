/*
 * Login.java
 *
 * Questo esempio mostra come utilizzare le sessioni per autenticare un utente
 * 
 * This example shows how to use sessions to authenticate the user
 *
 */
package it.univaq.f4i.iw.ex.newspaper.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityHelpers;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Ingegneria del Web
 * @version
 */
public class Login extends NewspaperBaseController {

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute("referrer", request.getParameter("referrer"));
        result.activate("login.ftl.html", request, response);
    }

    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("u");
        String password = request.getParameter("p");
        //... VALIDAZIONE IDENTITA'...
        //... IDENTITY CHECKS ...

        if (!username.isEmpty() && !password.isEmpty()) {
            //se la validazione ha successo
            //if the identity validation succeeds
            //carichiamo lo userid dal database utenti
            //load userid from user database
            int userid = 1;
            SecurityHelpers.createSession(request, username, userid);
            //se è stato trasmesso un URL di origine, torniamo a quell'indirizzo
            //if an origin URL has been transmitted, return to it
            if (request.getParameter("referrer") != null) {
                response.sendRedirect(request.getParameter("referrer"));
            } else {
                response.sendRedirect("issues");
            }
        } else {
            handleError("Login failed", request, response);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            if (request.getParameter("login") != null) {
                action_login(request, response);
            } else {
                String https_redirect_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https-redirect", https_redirect_url);
                action_default(request, response);
            }
        } catch (IOException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }   
}
