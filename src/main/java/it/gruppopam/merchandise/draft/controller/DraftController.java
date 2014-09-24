package it.gruppopam.merchandise.draft.controller;

import it.gruppopam.merchandise.draft.repository.DraftRepository;
import it.gruppopam.merchandise.draft.service.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.sort;

@Path("/drafts")
@Controller
@Validated
@Produces(MediaType.APPLICATION_JSON)
public class DraftController {

    @Autowired
    private DraftService draftService;

    @GET
    public void getDraftArticlesInfo(Long articleId) {

        draftService.getAllDraftTableValuesForGivenArticleId(articleId);

    }

    @Path("/check")
    @GET
    public Map<String, String> getCheckResult(){
        Map<String, String > result= new HashMap<>();
        result.put("Name", "Janani");
        return result;
    }
}
