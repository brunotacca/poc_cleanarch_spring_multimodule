package com.brunotacca.external.apis.rest;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

public class IndexRepresentationModel extends RepresentationModel<IndexRepresentationModel> {
  public IndexRepresentationModel(Link initialLink) {
      super(initialLink);
  }
}