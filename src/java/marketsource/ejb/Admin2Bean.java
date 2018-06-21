/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import marketsource.entity.Company;
import java.util.logging.*;
import marketsource.entity.Article;


/**
 * A singleton bean to handle application scoped functions
 * @author Titanium
 */

@Singleton
@Startup
public class Admin2Bean {

    @PersistenceContext
    private EntityManager em;
    
    private static final Logger logger = Logger.getLogger("marketsource");

    @PostConstruct
    public void LogStart() {
        
        

        logger.setLevel(Level.ALL);

        //System.out.println("Bean2 initialized...");
        logger.log(Level.FINE, "Bean2 initialized...");
        em.getProperties();
        Company c = new Company();
        c.setSymbol("TESTC");
        //c.setCompanyId(Short.MIN_VALUE);
        em.persist(c);
        
        
    }

}
