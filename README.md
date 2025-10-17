# 🎬 TMDB API - Sistema de Gerenciamento de Filmes

API REST desenvolvida com Spring Boot para gerenciamento de filmes, permitindo cadastro, consulta, edição e remoção de filmes, além de funcionalidades de favoritos e autenticação de usuários.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Funcionalidades](#funcionalidades)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Como Executar](#como-executar)
- [Documentação da API](#documentação-da-api)
- [Endpoints](#endpoints)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Autor](#autor)

## 🎯 Sobre o Projeto

Este projeto é uma API RESTful que permite gerenciar informações sobre filmes, incluindo título, sinopse, data de lançamento, popularidade e avaliações. O sistema também oferece funcionalidades para que usuários possam favoritar filmes e adicionar notas e comentários pessoais.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.4.2**
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Spring Boot DevTools
  - Spring Boot Actuator
- **MySQL 8.0**
- **Lombok** - Redução de código boilerplate
- **JWT (JSON Web Token)** - Autenticação
- **SpringDoc OpenAPI 3** - Documentação automática da API
- **Maven** - Gerenciamento de dependências
- **Dotenv Java** - Gerenciamento de variáveis de ambiente

## ✨ Funcionalidades

### Gerenciamento de Filmes
- ✅ Cadastrar novos filmes
- ✅ Listar todos os filmes
- ✅ Buscar filme por ID
- ✅ Editar informações de filmes
- ✅ Remover filmes
- ✅ Consultar filme com maior nota
- ✅ Filtrar filmes por popularidade

### Sistema de Favoritos
- ✅ Favoritar filmes
- ✅ Adicionar notas e comentários aos favoritos
- ✅ Listar todos os filmes favoritos
- ✅ Buscar favorito por ID

### Segurança
- ✅ Autenticação de usuários com JWT
- ✅ Proteção de endpoints com Spring Security
- ✅ Validação de dados de entrada

## 📦 Pré-requisitos

Antes de começar, você precisará ter instalado em sua máquina:

- [Java JDK 17+](https://www.oracle.com/java/technologies/downloads/)
- [Maven 3.6+](https://maven.apache.org/download.cgi)
- [MySQL 8.0+](https://dev.mysql.com/downloads/mysql/)
- [Git](https://git-scm.com/)

## 🔧 Instalação

1. Clone o repositório:
```bash
git clone https://github.com/MateusKen/TMDB_API.git
cd TMDB_API
```

2. Navegue até o diretório da API:
```bash
cd api
```

3. Instale as dependências:
```bash
./mvnw clean install
```
ou no Windows:
```bash
mvnw.cmd clean install
```

## ⚙️ Configuração

### Banco de Dados

1. Crie um banco de dados MySQL:
```sql
CREATE DATABASE api_spring;
```

2. Configure as credenciais do banco de dados no arquivo `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/api_spring
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

> **⚠️ Atenção de Segurança:** Em ambientes de produção, NUNCA utilize credenciais hardcoded. Use variáveis de ambiente, serviços de gerenciamento de segredos (como AWS Secrets Manager, Azure Key Vault) ou arquivos de configuração criptografados.

### Variáveis de Ambiente

Configure as seguintes variáveis de ambiente ou crie um arquivo `.env`:

```properties
MYSQL_HOST=localhost
JWT_SECRET=sua_chave_secreta_jwt
```

**Importante:** Por segurança, é recomendado usar variáveis de ambiente em produção. A senha do banco de dados e o secret JWT não devem ser commitados no repositório.

## 🏃 Como Executar

### Modo Desenvolvimento

Execute o projeto usando Maven:
```bash
./mvnw spring-boot:run
```

ou no Windows:
```bash
mvnw.cmd spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### Executar os Testes

```bash
./mvnw test
```

## 📖 Documentação da API

A documentação interativa da API está disponível através do Swagger UI após iniciar a aplicação:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI Docs:** http://localhost:8080/api-docs

## 🔗 Endpoints

### Autenticação

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/login` | Autenticação de usuário |

### Filmes

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/` | Mensagem de boas-vindas | ✅ |
| POST | `/api` | Cadastrar novo filme | ✅ |
| GET | `/api` | Listar todos os filmes | ✅ |
| GET | `/api/{id}` | Buscar filme por ID | ✅ |
| PUT | `/api` | Editar filme | ✅ |
| DELETE | `/api/{id}` | Remover filme | ✅ |
| GET | `/api/maiorNota` | Buscar filme com maior nota | ✅ |
| GET | `/api/popularidadeMaiorQue/{n}` | Filtrar filmes por popularidade | ✅ |
| GET | `/status` | Verificar status da API | ✅ |

### Favoritos

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| POST | `/favoritar` | Favoritar um filme | ✅ |
| PUT | `/favoritar` | Adicionar nota/comentário ao favorito | ✅ |
| GET | `/favoritar` | Listar todos os favoritos | ✅ |
| GET | `/favoritar/{id}` | Buscar favorito por ID | ✅ |

### Exemplo de Requisição - Cadastrar Filme

```json
POST /api
Content-Type: application/json
Authorization: Bearer {seu_token_jwt}

{
  "title": "Clube da Luta",
  "overview": "É um filme em que se luta e não se fala sobre clube da luta",
  "release_date": "10-29-1999",
  "popularity": 14.4,
  "vote_average": 6.89,
  "vote_count": 1231350
}
```

### Exemplo de Resposta

```json
HTTP/1.1 201 Created
Content-Type: text/plain

"Filme cadastrado com sucesso!"
```

## 📁 Estrutura do Projeto

```
api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/projeto/api/
│   │   │       ├── controle/          # Controllers REST
│   │   │       ├── infra/             # Configurações e exceções
│   │   │       ├── modelo/            # Entidades e DTOs
│   │   │       │   ├── filme/         # Modelo de Filme
│   │   │       │   ├── usuario/       # Modelo de Usuário
│   │   │       │   ├── interacoes/    # Favoritos e Watchlist
│   │   │       │   └── watchlist/     # Watchlist
│   │   │       ├── servico/           # Lógica de negócio
│   │   │       └── validacao/         # Validações customizadas
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-test.properties
│   │       └── application-prod.properties
│   └── test/                          # Testes unitários
├── pom.xml                            # Dependências Maven
└── mvnw                               # Maven Wrapper
```

## 🛡️ Segurança

- Todos os endpoints (exceto `/login`) requerem autenticação via JWT
- As senhas são armazenadas de forma segura
- Validações de entrada em todos os endpoints
- Tratamento de exceções centralizado

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.

1. Faça um Fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto é de código aberto e está disponível para uso educacional e pessoal.

## 👤 Autor

**Mateus Ken**

- GitHub: [@MateusKen](https://github.com/MateusKen)

---

⭐ Se este projeto foi útil para você, considere dar uma estrela!

