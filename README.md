# Clean Architecture + SpringBoot + MultiModule

SpringBoot project structured with Clean Architecture enforced by Maven MultiModule with whipped cream (Automated Tests) and a cherry added on top (Code Quality Analysis).

![](https://img.shields.io/badge/Status-WIP-orange) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

#### Sonar

<center>

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=brunotacca_poc_cleanarch_spring_multimodule)](https://sonarcloud.io/summary/new_code?id=brunotacca_poc_cleanarch_spring_multimodule) \
[![Sonar Analysis](https://github.com/brunotacca/poc_cleanarch_spring_multimodule/actions/workflows/sonar.yml/badge.svg)](https://github.com/brunotacca/poc_cleanarch_spring_multimodule/actions/workflows/sonar.yml)

</center> 

[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=brunotacca_poc_cleanarch_spring_multimodule&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=brunotacca_poc_cleanarch_spring_multimodule) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=brunotacca_poc_cleanarch_spring_multimodule&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=brunotacca_poc_cleanarch_spring_multimodule) [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=brunotacca_poc_cleanarch_spring_multimodule&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=brunotacca_poc_cleanarch_spring_multimodule) \
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=brunotacca_poc_cleanarch_spring_multimodule&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=brunotacca_poc_cleanarch_spring_multimodule) [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=brunotacca_poc_cleanarch_spring_multimodule&metric=bugs)](https://sonarcloud.io/summary/new_code?id=brunotacca_poc_cleanarch_spring_multimodule) [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=brunotacca_poc_cleanarch_spring_multimodule&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=brunotacca_poc_cleanarch_spring_multimodule) [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=brunotacca_poc_cleanarch_spring_multimodule&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=brunotacca_poc_cleanarch_spring_multimodule) \
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=brunotacca_poc_cleanarch_spring_multimodule&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=brunotacca_poc_cleanarch_spring_multimodule) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=brunotacca_poc_cleanarch_spring_multimodule&metric=coverage)](https://sonarcloud.io/summary/new_code?id=brunotacca_poc_cleanarch_spring_multimodule) [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=brunotacca_poc_cleanarch_spring_multimodule&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=brunotacca_poc_cleanarch_spring_multimodule)

### Proof of Concept:

Simple application (domain complexity) with hard architectural boundaries enforced by maven multimodule following Clean Architectural guidelines using Spring to make DI easy. In this CA, Spring framework is decoupled and required only in the external layers (api and datasources).

- [x] Spring (easy DI setup (Spring IoC))
- [x] SpringBoot (Quick Spring project setup)
- [x] Maven MultiModule (Enforced Architectural Boundaries)
- [x] Clean Architecture

**Extras 1:** This project features automated tests to demonstrate a way to do so in a clean architecture style with enforced boundaries. Since this is not a real-world application, the test suit doesn't go further than integration and unit tests.

- [x] JUnit, Mockito and AssertJ
- [x] Flyway
- [x] H2 and Postgres
- [x] Test Containers

**Extras 2:** This project also features automated code quality analysis by using:

- [x] SonarQube
- [x] GitHub Actions

**Extras 3:** RESTful API with Docs.

- [x] RESTful API (HATEOAS)
- [x] Documentation (Spring RestDocs + AsciiDoc + Pandoc & GitHub Actions)
  - [x] [HTML](ca-spring-multimodule/external/apis/docs/html/api-guide.html)
  - [x] [Docbook](ca-spring-multimodule/external/apis/docs/docbook/api-guide.xml)
  - [x] [Markdown](ca-spring-multimodule/external/apis/docs/md/api-guide.md)

**Extras 4:** Domain-Driven Design. Just a little bit to model this small project domain, even tho DDD is not recommended for small projects. This project stays just at the tip of the DDD iceberg. Check out the [Project Domain]. 

- [x] Domain PoC Planning

### Documents

* [x] [Project Domain]
* [x] [Project Architecture]
* [ ] [Setup and Configuration]
* [ ] [Contributing]
* [x] [Getting help]
* [x] [Be nice to everyone]


### Project Progress

- [x] Customer
  - [x] Domain
    - [x] Entities
    - [x] UseCases
  - [x] Adapters
    - [x] Controllers
  - [x] External
    - [x] Datasources
    - [x] Apis
- [ ] Product
- [ ] Order

[Project Domain]: docs/PROJECT_DOMAIN.md
[Project Architecture]: docs/PROJECT_ARCHITECTURE.md
[Setup and Configuration]: docs/GET_STARTED.md
[Contributing]: docs/CONTRIBUTING.md
[Getting help]: docs/SUPPORT.md
[Be nice to everyone]: docs/CODE_OF_CONDUCT.md

