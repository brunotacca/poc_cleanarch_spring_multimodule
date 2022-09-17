package com.brunotacca.external.apis.rest;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.brunotacca.external.apis.CustomDisplayNameGenerator;

@SpringBootTest
@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
@AutoConfigureTestDatabase(replace = Replace.ANY)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class IndexControllerIT {

  @Autowired
  private MockMvc mockMvc;

    @Test
    void indexExample() throws Exception {
        this.mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(
              document(
                "index", 
                preprocessRequest(prettyPrint()), 
                preprocessResponse(prettyPrint()), 
                links(
                  halLinks(),
                  linkWithRel("customers").description("The Customers resource"),
                  linkWithRel("customers:search").description("The Search for Customers")
                ), 
                responseFields(subsectionWithPath("_links").description("Links to resources")),
                responseHeaders(headerWithName("Content-Type").description("The Content-Type of the payload, e.g. `application/hal+json`"))
              )
            );
    }
}
