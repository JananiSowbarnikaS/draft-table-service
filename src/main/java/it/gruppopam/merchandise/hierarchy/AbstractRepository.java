package it.gruppopam.merchandise.hierarchy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public abstract class AbstractRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

}
