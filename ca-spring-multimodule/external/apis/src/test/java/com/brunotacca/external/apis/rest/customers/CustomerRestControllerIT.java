package com.brunotacca.external.apis.rest.customers;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;

import com.brunotacca.external.apis.CustomDisplayNameGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
@AutoConfigureRestDocs
@WebMvcTest(CustomersRestController.class)
class CustomerRestControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private final String validId = "55951aeb-4fc8-4ba4-b78a-020138b13d22";
  private final String validName = "Foo Bar";

  private Map<String, Object> validNewCustomerInput = new HashMap<>();
  private Map<String, Object> invalidNewCustomerInput = new HashMap<>();

  private Map<String, Object> validIdInput = new HashMap<>();
  private Map<String, Object> invalidIdInput = new HashMap<>();
  

  @BeforeEach
  void beforeEach() {
    this.validNewCustomerInput.put("name", validName);
    this.validNewCustomerInput.put("email", "foo@bar.com");
    this.validNewCustomerInput.put("street", "Foo Street");
    this.validNewCustomerInput.put("number", "123B");
    this.validNewCustomerInput.put("city", "Bay Bar");
    this.validNewCustomerInput.put("zip", "000000-000");

    this.invalidNewCustomerInput.put("name", "");

    this.validIdInput.put("id", validId);
    this.invalidIdInput.put("id", "");
  }
 
  @Nested
  class Create {

    @Test
    void shouldCreateCustomerAndReturn201() throws Exception {

      mockMvc.perform(post("/customers").contentType(MediaTypes.HAL_JSON).content(objectMapper.writeValueAsString(validNewCustomerInput)))
        .andExpect(status().isCreated())
        .andDo(
          document(
            "customers-create", 
            preprocessRequest(prettyPrint()), 
            preprocessResponse(prettyPrint()), 
            links(
              halLinks(),
              linkWithRel("edit").description("Edit customer data")
            ), 
            requestFields(
              fieldWithPath("name").description("The name of the customer"), 
              fieldWithPath("email").description("The email of the customer"), 
              fieldWithPath("street").description("The street of the customer"), 
              fieldWithPath("number").description("The number of the customer"), 
              fieldWithPath("city").description("The city of the customer"), 
              fieldWithPath("zip").description("The zip of the customer")
            ),
            responseHeaders(headerWithName("Location").description("The location of the created resource"))
          )
        );

    }

    @Test
    void shouldNotCreateCustomerAndReturn400() throws Exception {

      mockMvc.perform(post("/customers").contentType(MediaTypes.HAL_JSON)
        .content(objectMapper.writeValueAsString(invalidNewCustomerInput)))
        .andExpect(status().isBadRequest());

    }

  }

  @Nested
  class GetById {

    @Test
    void shouldGetCustomerAndReturn200() throws Exception {

      mockMvc.perform(get("/customers/{id}", validId).accept(MediaTypes.HAL_FORMS_JSON_VALUE))
        .andExpect(status().isOk())
        .andDo(
          document(
            "customers-getbyid", 
            preprocessRequest(prettyPrint()), 
            preprocessResponse(prettyPrint()), 
            pathParameters(parameterWithName("id").description("Customer Id")),
            responseBody(),
            links(
              halLinks(),
              linkWithRel("self").description("Resource Self Link")
            )
          )
        );

    }

    @Test
    void shouldNotGetCustomerAndReturn400() throws Exception {

      mockMvc.perform(get("/customers/"+"  ").accept(MediaTypes.HAL_FORMS_JSON_VALUE))
        .andExpect(status().isBadRequest());

    }

  }
  
  @Nested
  class FindCustomerByName {

    @Test
    void shouldFindCustomersAndReturn200() throws Exception {

      mockMvc.perform(get("/customers?name={customerName}", validName).accept(MediaTypes.HAL_FORMS_JSON_VALUE))
        .andExpect(status().isOk())
        .andDo(
          document(
            "customers-findbyname", 
            preprocessRequest(prettyPrint()), 
            preprocessResponse(prettyPrint()), 
            requestParameters(parameterWithName("name").description("Customer name to search")),
            responseFields(
              subsectionWithPath("_embedded.existingCustomerModelList").description("The customer list"),
              subsectionWithPath("_links").description("The search made")
            )
          )
        );

    }

    @Test
    void shouldNotFindCustomersAndReturn400() throws Exception {

      mockMvc.perform(get("/customers?name="+"  ").accept(MediaTypes.HAL_FORMS_JSON_VALUE))
        .andExpect(status().isBadRequest());

    }

  }
  
  @Nested
  class Update {

    @Test
    void shouldUpdateCustomerAndReturn200() throws Exception {

      mockMvc.perform(put("/customers/{id}", validId).contentType(MediaTypes.HAL_JSON).content(objectMapper.writeValueAsString(validNewCustomerInput)))
        .andExpect(status().isOk())
        .andDo(
          document(
            "customers-update", 
            preprocessRequest(prettyPrint()), 
            preprocessResponse(prettyPrint()), 
            pathParameters(parameterWithName("id").description("Customer Id")),
            requestFields(
              fieldWithPath("name").description("The name of the customer"), 
              fieldWithPath("email").description("The email of the customer"), 
              fieldWithPath("street").description("The street of the customer"), 
              fieldWithPath("number").description("The number of the customer"), 
              fieldWithPath("city").description("The city of the customer"), 
              fieldWithPath("zip").description("The zip of the customer")
            ),
            links(
              halLinks(),
              linkWithRel("self").description("Resource Self Link")
            )
          )
        );

    }

    @Test
    void shouldNotUpdateCustomerAndReturn400() throws Exception {

      mockMvc.perform(
                put("/customers/{id}", "   ")
                .contentType(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(validNewCustomerInput))
              )
              .andExpect(status().isBadRequest());

      mockMvc.perform(
        put("/customers/{id}", validId)
        .contentType(MediaTypes.HAL_JSON)
        .content(objectMapper.writeValueAsString(invalidNewCustomerInput))
      )
      .andExpect(status().isBadRequest());

    }

  }
  
  @Nested
  class Activate {
    @Test
    void shouldActivateCustomerAndReturn200() throws Exception {

      mockMvc.perform(patch("/customers/{id}/activate", validId))
        .andExpect(status().isAccepted())
        .andDo(print())
        .andDo(
          document("customers-activate", 
            preprocessRequest(prettyPrint()), 
            preprocessResponse(prettyPrint()), 
            pathParameters(parameterWithName("id").description("Customer Id")),
            links(
              halLinks(),
              linkWithRel("self").description("Resource Self Link"),
              linkWithRel("deactivate").description("Link to deactivate the customer")
            )
          )
        );

    }

    @Test
    void shouldNotActivateCustomerAndReturn400() throws Exception {
      mockMvc.perform(patch("/customers/{id}/activate", "  "))
        .andExpect(status().isBadRequest());
    }
  }
  
  @Nested
  class Deactivate {
    @Test
    void shouldDeactivateCustomerAndReturn200() throws Exception {
      mockMvc.perform(patch("/customers/{id}/deactivate", validId))
        .andExpect(status().isAccepted())
        .andDo(print())
        .andDo(
          document("customers-deactivate", 
            preprocessRequest(prettyPrint()), 
            preprocessResponse(prettyPrint()), 
            pathParameters(parameterWithName("id").description("Customer Id")),
            links(
              halLinks(),
              linkWithRel("self").description("Resource Self Link"),
              linkWithRel("activate").description("Link to activate the customer")
            )
          )
        );
    }

    @Test
    void shouldNotDeactivateCustomerAndReturn400() throws Exception {
      mockMvc.perform(patch("/customers/{id}/deactivate", "  "))
        .andExpect(status().isBadRequest());
    }
  }
  


}
