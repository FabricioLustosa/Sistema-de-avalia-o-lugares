# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Spring Boot 4.0.5 / Java 21 app for reviewing places (restaurants, parks, etc.). Package root:
`com.fabriciolustosa.sistema_de_avaliacao_de_lugares`.

## Commands

Windows: use `mvnw.cmd`. Bash tool: use `./mvnw`.

- Run app: `./mvnw spring-boot:run` (requires a local PostgreSQL instance — see Database below)
- Run tests: `./mvnw test`
- Run a single test: `./mvnw test -Dtest=ClassName#methodName`
- Build jar: `./mvnw clean package`
- Docker build: `docker build -t sistema-de-avaliacao-de-lugares .` (multi-stage, builds the jar with `mvnw` inside the image, skips tests)

There is currently only one test (`SistemaDeAvaliacaoDeLugaresApplicationTests`, a context-load smoke test) — no service/controller/repository test coverage exists yet.

## Database

Two profiles:
- Default (`application.properties`): PostgreSQL at `jdbc:postgresql://localhost:5432/places_db`, hardcoded local dev credentials, `ddl-auto=update`.
- `prod` (`application-prod.properties`): reads `SPRING_DATASOURCE_URL` / `_USER` / `_PASSWORD` from env vars; also enables the H2 console.

There's also an H2 in-memory dependency available for tests/local experimentation, but it's not wired as the active datasource in either properties file.

## Architecture

Standard layered Spring MVC app, but with **two parallel interfaces over the same service layer**:

- `controller/PlaceController` — JSON REST API under `/places` (returns DTOs, used e.g. by Postman/API clients).
- `controller/PlaceViewController` — server-rendered Thymeleaf UI under `/view` (returns view names, works directly with entities and `@ModelAttribute` bindings).
- `controller/AuthController` — registration (`/register`) and login page routing; login itself is handled by Spring Security's `formLogin`.

Both controllers delegate all business logic to `service/PlaceService` — controllers do not talk to repositories directly.

**Data flow:** `Controller → Service → Repository → Entity`. The REST controller converts entities to DTOs via static mapper classes (`mapper/PlaceMapper`, `mapper/ReviewMapper`) before returning; the view controller passes entities straight into Thymeleaf templates.

**Entities & JSON recursion:** `Place` has a `@OneToMany` list of `Review` (cascade ALL, orphanRemoval, eager fetch); `Review` has a `@ManyToOne` back to `Place`. This bidirectional link is annotated `@JsonManagedReference` (Place side) / `@JsonBackReference` (Review side) to stop infinite recursion when Jackson serializes an entity directly — but the REST endpoints route through `PlaceResponseDTO`/`ReviewResponseDTO` instead, which is the mapping described in `MELHORIAS_EXPLICADAS.md`.

**Authorization pattern:** ownership checks live in `PlaceService`, not in controllers or a filter. Pattern used throughout: pull the `User` off `Authentication.getPrincipal()` (`User implements UserDetails`), then compare `place.getOwner().getId()` / `review.getOwner().getId()` against it, throwing `ForbiddenException` on mismatch and `ResourceNotFoundException` when the entity doesn't exist. Both are translated to HTTP responses by `controller/GlobalExceptionHandler` (`@RestControllerAdvice`), which also handles `MethodArgumentNotValidException` for `@Valid` DTO validation failures.

**Security (`SecurityConfig`):** only `/` and `/register` are `permitAll()`; everything else requires authentication. Form login redirects to `/view` on success. `CustomUserDetailsService` loads `User` by username via `UserRepository`; passwords are BCrypt-hashed (`AuthController` encodes on registration).

**Partial updates:** PATCH endpoints (`partialUpdatePlace`, `partialUpdateReview`) use small `*UpdateRequestDTO` classes and apply only non-null fields to the entity — this is the pattern to follow for any new partial-update endpoint.

Comments in the service/controller/DTO code are intentionally written in Portuguese for didactic purposes (see `MELHORIAS_EXPLICADAS.md` / `README.md`) — match that style if editing nearby code rather than switching to English.
