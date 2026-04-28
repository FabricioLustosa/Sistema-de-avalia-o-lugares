# Mudanças realizadas no projeto

## Objetivo
Refatorar a aplicação para que ela compile, suba e seja testável sem depender de um PostgreSQL externo configurado manualmente.

## O que foi corrigido

### 1. Dependências do Maven
No `pom.xml`:
- removi starters inválidos como `spring-boot-starter-webmvc`, `spring-boot-starter-webmvc-test`, `spring-boot-starter-data-jpa-test` e `spring-boot-h2console`;
- adicionei os starters corretos:
  - `spring-boot-starter-web`
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-test`
- mantive `H2` e `PostgreSQL` para permitir desenvolvimento local e evolução futura.

### 2. Configuração de banco
No `application.properties` principal:
- troquei a configuração fixa de PostgreSQL por H2 em memória;
- mantive o console H2 habilitado;
- ajustei a URL e o driver para evitar falha de inicialização quando não houver banco externo.

### 3. Entidades JPA
Em `Place` e `Review`:
- substituí `@Data` por `@Getter`, `@Setter` e `@NoArgsConstructor`;
- evitei problemas de `toString`, `equals` e `hashCode` em relacionamentos bidirecionais;
- adicionei `orphanRemoval = true` no relacionamento de `Place`;
- criei métodos auxiliares `addReview` e `removeReview` para manter os dois lados do relacionamento sincronizados.

### 4. DTO de resposta
Em `PlaceResponseDTO`:
- removi código inválido que tentava usar `placeService` dentro da DTO;
- passei a converter os reviews para `ReviewResponseDTO`;
- deixei a resposta preparada para retornar o lugar com suas avaliações.

### 5. Camada de serviço
Em `PlaceService`:
- troquei injeção por campos por injeção via construtor;
- validei melhor as operações de exclusão;
- garanti que reviews sejam associados corretamente ao lugar;
- removi a remoção manual inconsistente e passei a usar a associação da entidade.

### 6. Controller REST
Em `PlaceController`:
- troquei retorno de entidades por DTOs;
- padronizei os status HTTP:
  - `201 Created` ao criar recursos;
  - `204 No Content` ao excluir;
- o endpoint de `top-rated` agora retorna DTOs também.

### 7. Testes
- mantive o teste de contexto;
- adicionei um teste de integração em `PlaceServiceIntegrationTest` cobrindo:
  - criação de lugar;
  - adição de review;
  - cálculo da média.
- criei `src/test/resources/application.properties` para isolar os testes com H2 e `ddl-auto=create-drop`.

## Resultado
A aplicação fica mais estável, mais simples de rodar em qualquer máquina e mais segura para evoluir, sem depender de uma instância local de PostgreSQL para subir ou executar os testes.

## Como executar
```powershell
./mvnw test
```

Se quiser iniciar a aplicação:
```powershell
./mvnw spring-boot:run
```

