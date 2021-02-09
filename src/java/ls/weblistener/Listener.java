/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ls.weblistener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class Listener implements HttpSessionListener{

    private static int count;
    
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("Session Created: " + event.getSession().getId());
        count++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.println("Session Destroyed: " + event.getSession().getId());
        count--;
    }
    
    public static int getCount(){
        return count;
    }
}
