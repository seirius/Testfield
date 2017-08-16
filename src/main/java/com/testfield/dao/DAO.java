package com.testfield.dao;

import org.hibernate.Session;

/**
 *
 * @author Andriy
 */
public class DAO {
    
    protected Session session;
    
    public DAO(Session session) {
        this.session = session;
    }
    
}
