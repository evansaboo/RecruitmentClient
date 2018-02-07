/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Emil
 */
public class CookieHandler {
        private Cookie cookie;
    
    public void setCookie(String name, String value, int expiry) {

    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    cookie = null;

    Cookie[] userCookies = request.getCookies();
    if (userCookies != null && userCookies.length > 0 ) {
        for (int i = 0; i < userCookies.length; i++) {
            if (userCookies[i].getName().equals(name)) {
                cookie = userCookies[i];
                break;
            }
        }
    }

    if (cookie != null) {
        cookie.setValue(value);
    } else {
        cookie = new Cookie(name, value);
        cookie.setPath(request.getContextPath());
    }

    cookie.setMaxAge(expiry);

    HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    response.addCookie(cookie);
  }

  public Cookie getCookie(String value) {

    FacesContext facesContext = FacesContext.getCurrentInstance();

    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    cookie = null;

    Cookie[] userCookies = request.getCookies();
    if (userCookies != null && userCookies.length > 0 ) {
        for (int i = 0; i < userCookies.length; i++) {
            if (userCookies[i].getName().equals(value)) {
                cookie = userCookies[i];
                return cookie;
            }
        }
    }
    return null;
  }
}
