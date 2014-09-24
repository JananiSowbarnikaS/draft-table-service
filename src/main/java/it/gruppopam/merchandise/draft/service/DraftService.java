package it.gruppopam.merchandise.draft.service;


import it.gruppopam.merchandise.draft.dto.ResultDto;
import it.gruppopam.merchandise.draft.repository.DraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DraftService {

    @Autowired
    private DraftRepository draftRepository;

    //Map<String, List<ResultDto>> resultDtoMap = new HashMap<>();
    private List<String> tablesWithArticleId = new ArrayList<>();
    private Map<String, List<String>> mapOfTableAndColumn = new HashMap<>();
    private Map<String, Map<String, String>> finalMapWithValues = new HashMap<>();
    private Map<String, String> intermediateResults = new HashMap<>();

    public Map<String, List<String>> getPopulatedListWithTableAndColumnNames() {

        String tableName;

        List<String> draftTableName = draftRepository.fetchDraftTableNames();

        Iterator tableIter = draftTableName.iterator();

        while (tableIter.hasNext()) {

            tableName = (String) tableIter.next();
            mapOfTableAndColumn.put(tableName, draftRepository.fetchColumnNamesForTable(tableName));

        }

        return mapOfTableAndColumn;
    }

    public List<String> getOnlyTableNamesHavingArticleIdColumn() {

        for (Map.Entry<String, List<String>> eachTable : mapOfTableAndColumn.entrySet()) {

            if (eachTable.getValue().contains("article_id")) {
                tablesWithArticleId.add(eachTable.getKey());
            }
        }

        return tablesWithArticleId;
    }

    public Map<String, Map<String, String>> getAllDraftTableValuesForGivenArticleId(Long articleId) {

        mapOfTableAndColumn = getPopulatedListWithTableAndColumnNames();

        tablesWithArticleId = getOnlyTableNamesHavingArticleIdColumn();

        String columnList;

        List<Object[]> objects;

        for (Map.Entry<String, List<String>> eachEntry : mapOfTableAndColumn.entrySet()) {
            if (tablesWithArticleId.contains(eachEntry.getKey())) {

                columnList = buildColumnsAsString(eachEntry.getValue());

                objects = draftRepository.fetchTableValuesForArticleId(articleId, columnList, eachEntry.getKey());

                mapValuesToColumns(objects, eachEntry.getValue());

                finalMapWithValues.put(eachEntry.getKey(), intermediateResults);
            }
        }

        getDraftValuesForArticlesTable(articleId);

        System.out.println(finalMapWithValues);

        return finalMapWithValues;

    }

    public String buildColumnsAsString(List<String> columnList) {

        StringBuilder rString = new StringBuilder();
        String sep = ", ";

        if (!columnList.isEmpty()) {

            for (String each : columnList) {

                rString.append(each).append(sep);
            }

            return rString.replace(rString.length() - 2, rString.length(), "").toString();
        } else {
            return "";
        }
    }

    public void mapValuesToColumns(List<Object[]> resultList, List<String> columnList) {

        for (Object[] fields : resultList) {

            for (int i = 0; i < fields.length; i++) {

                if (fields[i] != null) {
                    //new ResultDto(columnList.get(i), fields[i].toString());
                    intermediateResults.put(columnList.get(i), fields[i].toString());
                } else {
                    intermediateResults.put(columnList.get(i), "");
                }
            }
        }

    }

    public Map<String, Map<String, String>> getDraftValuesForArticlesTable(Long articleId) {
        String tableName = "articles";

        List<String> articleColumns = draftRepository.fetchColumnNamesForTable(tableName);

        String columnList = buildColumnsAsString(articleColumns);

        List<Object[]> objects = draftRepository.fetchTableValuesForArticleId(articleId, columnList, tableName);

        mapValuesToColumns(objects, articleColumns);

        finalMapWithValues.put(tableName, intermediateResults);

        return finalMapWithValues;

    }

}
