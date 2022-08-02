# Clean Architecture + SpringBoot + MultiModule

SpringBoot project structured with Clean Architecture enforced by Maven MultiModule with whipped cream (Automated Tests) and a cherry added on top (Code Quality Analysis).

![](https://img.shields.io/badge/Status-WIP-orange)

### Proof of Concept:

Simple application (domain complexity) with hard architectural boundaries enforced by maven multimodule following Clean Architectural guidelines using Spring to make DI easy.

- Spring (easy DI setup (Spring IoC))
- SpringBoot (Quick Spring project setup)
- Maven MultiModule (Enforced Architectural Boundaries)

**Extras:** This project features automated tests to demonstrate a way to do so in a clean architecture style with enforced boundaries. Since this is not a real-world application, the test suit doesn't go further than integration and unit tests.

- JUnit and AssertJ
- Test Containers

**Extras 2:** This project also features automated code quality analysis by using:

- SonarQube
- GitHub Actions

**Extra 3:** Reactive java project, just because I wanted to try CA in a reactive style. I believe it might be a good fit to distinguish the flow of control of controllers and presenters.

- Spring Webflux
- Project Reactor

**Extra 4:** Domain-Driven Design. Just a little bit to model this small project domain, even tho DDD is not recommended for small projects. This project stays just at the tip of the DDD iceberg. Check out the [Project Domain]. 

### Documents

* [Project Domain](docs/PROJECT_DOMAIN.md)
* [Project Architecture](docs/PROJECT_ARCHITECTURE.md)
* [Setup and Configuration](docs/GET_STARTED.md)
* [Contributing](docs/CONTRIBUTING.md)
* [Getting help](docs/SUPPORT.md)
* [Be nice to everyone](docs/CODE_OF_CONDUCT.md)


[Project Domain]: docs/PROJECT_DOMAIN.md