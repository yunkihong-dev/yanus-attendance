package com.jpa.yanus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class SessionListener implements HttpSessionListener {



    private Logger log = LoggerFactory.getLogger(this.getClass());



    @Override

    public void sessionCreated(HttpSessionEvent se) {

        se.getSession().setMaxInactiveInterval(60*60*10); //세션만료60분

    }



    @Override

    public void sessionDestroyed(HttpSessionEvent se) {

    }

}