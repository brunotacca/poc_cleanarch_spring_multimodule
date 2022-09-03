package com.brunotacca.external.apis.rest.customer;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.brunotacca.external.apis.ApisApplication;
import com.brunotacca.external.apis.CustomDisplayNameGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
@AutoConfigureRestDocs
@ContextConfiguration(classes = ApisApplication.class)
@WebMvcTest(CustomerRestController.class)
class CustomerRestControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private final String validId = "55951aeb-4fc8-4ba4-b78a-020138b13d22";

  private Map<String, Object> validNewCustomerInput = new HashMap<>();
  private Map<String, Object> invalidNewCustomerInput = new HashMap<>();

  private Map<String, Object> validIdInput = new HashMap<>();
  private Map<String, Object> invalidIdInput = new HashMap<>();
  

  @BeforeEach
  void beforeEach() {
    this.validNewCustomerInput.put("name", "Foo Bar");
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

      mockMvc.perform(post("/customer").contentType(MediaTypes.HAL_JSON)
        .content(objectMapper.writeValueAsString(validNewCustomerInput)))
        .andExpect(status().isCreated())
        .andDo(
          document(
            "customer-create", 
            preprocessRequest(prettyPrint()), 
            preprocessResponse(prettyPrint()), 
            requestFields(
              fieldWithPath("name").description("The name of the customer").optional(), 
              fieldWithPath("email").description("The email of the customer"), 
              fieldWithPath("street").description("The street of the customer"), 
              fieldWithPath("number").description("The number of the customer"), 
              fieldWithPath("city").description("The city of the customer"), 
              fieldWithPath("zip").description("The zip of the customer")
            )
          )
        );

    }

    @Test
    void shouldNotCreateCustomerAndReturn400() throws Exception {

      mockMvc.perform(post("/customer").contentType(MediaTypes.HAL_JSON)
        .content(objectMapper.writeValueAsString(invalidNewCustomerInput)))
        .andExpect(status().isBadRequest());

    }
  }

  @Nested
  class GetById {
    // TODO
  }
  
  @Nested
  class FindCustomerByName {
    // TODO
  }
  
  @Nested
  class Update {
    // TODO
  }
  
  @Nested
  class Activate {
    // TODO
  }
  
  @Nested
  class Deactivate {
    // TODO
  }
  


}
