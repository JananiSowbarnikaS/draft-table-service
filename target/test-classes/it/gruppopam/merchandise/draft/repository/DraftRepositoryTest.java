package it.gruppopam.merchandise.draft.repository;

import it.gruppopam.merchandise.draft.controller.DraftController;
import it.gruppopam.merchandise.draft.service.DraftService;
import it.gruppopam.merchandise.shared.repository.AbstractRepositoryTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.Query;
import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
public class DraftRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private DraftRepository draftRepository;

    @Before
    @Rollback(false)
    public void setUp(){

        //Delete query to delete if already present in table

        Query deleteQueryA = entityManager.createNativeQuery("DELETE FROM draft_config.article_discounts WHERE article_id = "+ 8399612);
        Query deleteQueryB = entityManager.createNativeQuery("DELETE FROM draft_config.store_group_assortment WHERE article_id = "+ 8399612);

        int result= deleteQueryA.executeUpdate();

        System.out.println("Rows deleted: " + result);

        result = deleteQueryB.executeUpdate();

        System.out.println("Rows deleted: " + result);

        //Insert query to insert data into draft tables
        Query insertQueryA = entityManager.createNativeQuery("INSERT INTO draft_config.article_discounts(article_id, line_id, discount_class_type, store_group_id, start_date, status, end_date, published_status, id, created_by, updated_by, draft_action)" +
                " VALUES(?, ?, ?, ?, CAST(? AS DATE), ?, CAST(? AS DATE), ?, ?, ?, ?, ?)");

        //Setting the parameters with values
        insertQueryA.setParameter(1, 8399612);
        insertQueryA.setParameter(2, 832980002);
        insertQueryA.setParameter(3, "CA");
        insertQueryA.setParameter(4, 6000);
        insertQueryA.setParameter(5, "2014-08-29");
        insertQueryA.setParameter(6, true);
        insertQueryA.setParameter(7, "9999-12-31");
        insertQueryA.setParameter(8, "UNPUBLISHED");
        insertQueryA.setParameter(9, 2843);
        insertQueryA.setParameter(10, "FONTE");
        insertQueryA.setParameter(11, "FONTE");
        insertQueryA.setParameter(12, "NEW");

        result = insertQueryA.executeUpdate();

        System.out.println("Rows inserted: " + result);

        Query insertQueryB = entityManager.createNativeQuery("INSERT INTO draft_config.store_group_assortment(article_id, store_group_id, start_date, created_by, updated_by, published_status, end_date, status, draft_action) " +
                                                             "VALUES(?, ?, CAST(? AS DATE), ?, ?, ?, CAST(? AS DATE), ?, ?)");

        insertQueryB.setParameter(1, 8399612);
        insertQueryB.setParameter(2, 12345);
        insertQueryB.setParameter(3, "2014-08-29");
        insertQueryB.setParameter(4, "FONTE");
        insertQueryB.setParameter(5, "FONTE");
        insertQueryB.setParameter(6, "UNPUBLISHED");
        insertQueryB.setParameter(7, "9999-08-23");
        insertQueryB.setParameter(8, true);
        insertQueryB.setParameter(9, "");

        result = insertQueryB.executeUpdate();

        System.out.println("Rows inserted: " + result);

    }


    @Test
    public void testIfAllDraftTableNamesAreRetrieved() throws Exception {

        List<String> draftTableNames = draftRepository.fetchDraftTableNames();

        assertEquals(draftTableNames.size(), 22);

    }

    @Test
    public void testIfColumnNamesAreFetchedForAnyDraftTable() throws Exception {

        List<String> actualColumnNames = draftRepository.fetchColumnNamesForTable("article_discounts");

        List<String> expectedColumnNames = asList("article_id", "line_id", "discount_class_type", "store_group_id", "start_date", "status", "end_date", "published_status", "id", "notes", "created_by", "created_at", "updated_by", "updated_at", "draft_action");

        assertEquals(actualColumnNames, expectedColumnNames);

    }

    @Test
    public void testIfTableValuesAreFetchedForATable() throws Exception {

        Long articleId = 8399612l;

        String columnList = "article_id, line_id, discount_class_type, store_group_id, start_date, status, end_date, published_status, id, notes, created_by, created_at, updated_by, updated_at, draft_action";

        String tableName = "article_discounts";

        List<Object[]> actualList = draftRepository.fetchTableValuesForArticleId(articleId, columnList, tableName);

        assertEquals(actualList.size(),1);

    }

}
