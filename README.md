# TMDB API

API REST de catálogo de filmes construída com Spring Boot. Os filmes seguem o modelo de dados do [TMDB](https://www.themoviedb.org/) (título, sinopse, data de lançamento, popularidade, nota média e número de votos), e usuários autenticados podem montar sua lista de favoritos.

## Funcionalidades

- CRUD de filmes com validações de negócio (título obrigatório, filme duplicado, filme inexistente)
- Consultas: filme com a maior nota e filmes com popularidade acima de um valor
- Lista de filmes favoritos por usuário
- Autenticação com JWT e senhas com BCrypt (Spring Security, sessão stateless)
- Tratamento centralizado de erros com respostas padronizadas
- Documentação interativa com Swagger / OpenAPI

## Stack

- Java 17
- Spring Boot 3.4 (Web, Data JPA, Security, Validation, Actuator)
- MySQL
- Auth0 java-jwt
- springdoc-openapi
- Lombok
- Maven

## Endpoints

Todas as rotas exigem o cabeçalho `Authorization: Bearer <token>`, exceto `POST /login` e a documentação.

### Autenticação

| Método | Rota | Descrição |
| --- | --- | --- |
| POST | `/login` | Autentica o usuário e devolve o token JWT |

### Filmes

| Método | Rota | Descrição |
| --- | --- | --- |
| GET | `/api` | Lista todos os filmes |
| GET | `/api/{id}` | Busca um filme pelo id |
| POST | `/api` | Cadastra um filme |
| PUT | `/api` | Edita um filme |
| DELETE | `/api/{id}` | Remove um filme |
| GET | `/api/maiorNota` | Filme com a maior nota |
| GET | `/api/popularidadeMaiorQue/{n}` | Filmes com popularidade maior que `n` |

### Favoritos

| Método | Rota | Descrição |
| --- | --- | --- |
| GET | `/favoritar` | Lista as listas de favoritos |
| GET | `/favoritar/{id}` | Busca uma lista de favoritos pelo id |
| POST | `/favoritar` | Cria uma lista de favoritos |
| PUT | `/favoritar` | Adiciona um filme a uma lista existente |

## Como executar

Pré-requisitos: Java 17 e um MySQL acessível.

1. Clone o repositório e entre na pasta do projeto:

   ```bash
   git clone https://github.com/MateusKen/TMDB_API.git
   cd TMDB_API/api
   ```

2. Crie o banco `api_spring` no MySQL e ajuste usuário e senha em `src/main/resources/application.properties`. Variáveis de ambiente opcionais:

   | Variável | Padrão | Uso |
   | --- | --- | --- |
   | `MYSQL_HOST` | `localhost` | Host do MySQL |
   | `JWT_SECRET` | valor de desenvolvimento | Segredo usado para assinar os tokens |

   No perfil `prod`, a conexão vem de `DATASOURCE_URL`, `DATASOURCE_USERNAME` e `DATASOURCE_PASSWORD`.

3. Suba a aplicação:

   ```bash
   ./mvnw spring-boot:run
   ```

4. Acesse a documentação em `http://localhost:8080/swagger-ui.html`.

## Estrutura

```
api/src/main/java/br/com/projeto/api
├── controle/     # controladores REST (login, filmes, favoritos)
├── servico/      # regras de negócio
├── modelo/       # entidades, DTOs e repositórios JPA
├── validacao/    # validações de negócio plugáveis por tipo
└── infra/        # segurança (JWT), tratamento de erros e OpenAPI
```

## Decisões de projeto

- **Validações como componentes.** Cada regra é uma classe que implementa `ValidacaoFilme<T>`; o serviço recebe a lista injetada pelo Spring e aplica as que se encaixam no tipo. Adicionar uma regra nova não exige mexer no serviço.
- **Segurança stateless.** Um filtro valida o JWT a cada requisição, sem sessão no servidor.

## Autor

Mateus Kenzo Iochimoto · [LinkedIn](https://www.linkedin.com/in/mateus-kenzo-iochimoto)
