package it.gruppopam.merchandise.shared.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;


@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
public abstract class AbstractRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ApplicationContext applicationContext;
    @PersistenceContext
    protected EntityManager entityManager;
    @Autowired
    private DataSource dataSource;

    public void clear() {
        entityManager.clear();
    }

    protected void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }


}
