/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import org.hibernate.Session;

/**
 *
 * @author Andriy
 */
public class TagDAO extends DAO {
    
    public TagDAO(Session session) {
        super(session);
    }
    
}
