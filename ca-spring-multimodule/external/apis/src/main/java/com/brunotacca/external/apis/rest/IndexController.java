package com.brunotacca.external.apis.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.brunotacca.external.apis.rest.customer.CustomerRestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/")
public class IndexController {

  @GetMapping
  public IndexRepresentationModel index() {
      return new IndexRepresentationModel(
          linkTo(CustomerRestController.class)
            .withRel(this.getRequestMappingFromController(CustomerRestController.class)));
  }

  private String getRequestMappingFromController(Class<?> controller) {
    return controller.getAnnotation(RequestMapping.class).value()[0].substring(1);
  }

}
