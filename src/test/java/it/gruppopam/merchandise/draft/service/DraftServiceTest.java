package it.gruppopam.merchandise.draft.service;

import it.gruppopam.merchandise.draft.repository.DraftRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DraftServiceTest {

    @InjectMocks
    private DraftService draftService;

    @Mock
    private DraftRepository draftRepository;

    @Test
    public void testIfFetchDraftTableNamesIsCalled() throws Exception {

        when(draftRepository.fetchDraftTableNames()).thenReturn(asList());

        Map<String, List<String>> map = draftService.getPopulatedListWithTableAndColumnNames();

        verify(draftRepository).fetchDraftTableNames();


    }

    @Test
    public void shouldGetTheColumnNamesMappedWithTableNames() throws Exception {

        List<String> discountColumns = asList("id", "supplier", "discount", "date");
        List<String> costColumns = asList("id", "supplier", "cost", "date");
        List<String> priceColumns = asList("id", "price", "date");
        List<String> noColumns = asList();

        when(draftRepository.fetchDraftTableNames()).thenReturn(asList("article_discounts", "costs", "prices", "none"));
        when(draftRepository.fetchColumnNamesForTable("article_discounts")).thenReturn(discountColumns);
        when(draftRepository.fetchColumnNamesForTable("costs")).thenReturn(costColumns);
        when(draftRepository.fetchColumnNamesForTable("prices")).thenReturn(priceColumns);
        when(draftRepository.fetchColumnNamesForTable("none")).thenReturn(noColumns);

        Map<String, List<String>> tableAndColumnNames = draftService.getPopulatedListWithTableAndColumnNames();


        assertEquals(tableAndColumnNames.get("article_discounts"), discountColumns);
        assertEquals(tableAndColumnNames.get("costs"), costColumns);
        assertEquals(tableAndColumnNames.get("prices"), priceColumns);
        assertEquals(tableAndColumnNames.get("none"), noColumns);

    }

    @Test
    public void shouldGetTablesHavingArticleIdColumn() throws Exception {

        List<String> discountColumns = asList("article_id", "supplier", "discount", "date");
        List<String> noColumns = asList("name","price");
        List<String> draftTableNames = asList("article_discounts", "none");

        when(draftRepository.fetchDraftTableNames()).thenReturn(draftTableNames);
        when(draftRepository.fetchColumnNamesForTable("article_discounts")).thenReturn(discountColumns);
        when(draftRepository.fetchColumnNamesForTable("none")).thenReturn(noColumns);

        Map<String, List<String>> allTableNames =  draftService.getPopulatedListWithTableAndColumnNames();
        List<String> tableNames = draftService.getOnlyTableNamesHavingArticleIdColumn();

        assertEquals(tableNames,asList("article_discounts"));
    }


    @Test
    public void testIfEmptyColumnListBuildsString() throws Exception {

        List<String> emptyColumnList = asList();

        String actual = draftService.buildColumnsAsString(emptyColumnList);

        String expected = "";

        assertEquals(expected, actual);

    }

    @Test
    public void testIfFetchForDraftArticlesIsCalled() throws Exception {

        List<String> articleColumns  =asList("id", "name");

        when(draftRepository.fetchColumnNamesForTable("articles")).thenReturn(articleColumns);

        when(draftRepository.fetchTableValuesForArticleId(8399612l,"id, name","articles")).thenReturn(asList());

        Map<String, Map<String, String>> actual = draftService.getDraftValuesForArticlesTable(8399612l);

        HashMap<String, String> columns = new HashMap<>();

        HashMap<String, Map<String, String>> results = new HashMap<>();

        results.put("articles", columns);

        assertEquals(results,actual);

    }
}
