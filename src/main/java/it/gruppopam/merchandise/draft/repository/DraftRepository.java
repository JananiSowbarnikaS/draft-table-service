package it.gruppopam.merchandise.draft.repository;


import it.gruppopam.merchandise.hierarchy.AbstractRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

import static java.util.Arrays.asList;

@Repository
public class DraftRepository extends AbstractRepository {


    public List<Object[]> fetchTableValuesForArticleId(Long articleId, String columnList, String tableName) {

        List<Object[]> resultList;

        if(columnList!=""){
            resultList = entityManager.createNativeQuery("SELECT " + columnList + " FROM draft_config." + tableName +" " +
                    "WHERE article_id = " + articleId).getResultList();
        }else{
            resultList = asList();
        }
        return resultList;
    }

    public List<String> fetchDraftTableNames() {

        List<String> draftTableNames = new ArrayList<String>();

        draftTableNames = entityManager.createNativeQuery("SELECT table_name FROM information_schema.tables " +
                                                          "WHERE table_schema = 'draft_config'").getResultList();

        return draftTableNames;

    }

    public List<String> fetchColumnNamesForTable(String draftTableName){

        List<String> columnNames = new ArrayList<String>();

        columnNames = entityManager.createNativeQuery("SELECT column_name FROM information_schema.columns " +
                                                      "WHERE table_schema = 'draft_config' AND table_name = '" + draftTableName + "'").getResultList();

        return columnNames;
    }

}
