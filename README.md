## Descrição

A **Card Game API** é um projeto RESTful que simula um jogo no estilo "card game" baseado em avaliações de filmes. O
jogador deve selecionar qual filme possui melhor avaliação no IMDB, considerando tanto a nota média quanto o número de
votos. O sistema é baseado em Spring Boot e utiliza autenticação JWT para gerenciar sessões de usuários.

---

## Funcionalidades

### **Autenticação e Gerenciamento de Usuários**

- **Registrar usuário**: Criação de novos usuários.
- **Login**: Geração de token JWT para autenticação.

### **Jogo**

- **Iniciar jogo**: Criação de uma nova partida para um usuário.
- **Obter próximo par de filmes**: Geração de pares de filmes únicos para o jogador escolher.
- **Enviar resposta**: Avaliação da escolha do jogador.
- **Encerrar jogo**: Finalização de uma partida ativa.
- **Obter ranking**: Exibição do ranking de usuários com as melhores pontuações.

---

## Endpoints da API

### **Autenticação**

1. **Registrar usuário**

#### **Descrição**

Registra um novo usuário com ```username``` e ```password```

```bash
POST /auth/register
```

#### **Body**

```json
{
  "username": "string",
  "password": "string"
}
```

#### Response

**HTTP 201 Created**
```"User successfully registered"```
---

2. **Login**

#### **Descrição**

Realiza o login de um usuário com ```username``` e ```password```

```bash
POST /auth/login
```

#### **Body**

```json
{
  "username": "string",
  "password": "string"
}
```

#### Response

**HTTP 200 OK**

```json
{
  "token": "string"
}
```

---

### **Jogo**

1. **Iniciar jogo**

#### **Descrição**

Inicia um novo jogo

```bash
GET /game/start?userId={userId}
```

#### Response

**HTTP 200 OK**

```json
{
  "roundId": 1,
  "movie1Id": 101,
  "movie1Title": "Movie A",
  "movie2Id": 102,
  "movie2Title": "Movie B",
  "answered": false,
  "correctMovieId": 101
}
```

2. **Obter próximo par de filmes**

#### **Descrição**

Obtém a próxima rodada do jogo

```bash
GET /game/next-pair?gameId={gameId}
```

#### Response

**HTTP 200 OK**

```json
{
  "roundId": 2,
  "movie1Id": 201,
  "movie1Title": "Movie C",
  "movie2Id": 202,
  "movie2Title": "Movie D",
  "answered": false,
  "correctMovieId": 202
}
```

3. **Enviar resposta**

#### **Descrição**

Obtém a próxima rodada do jogo

```bash
GET /game/answer?roundId={roundId}&selectedMovieId={selectedMovieId} 
```

#### Response

**HTTP 200 OK**

```"Correct!"``` or ```"Wrong!"```

4. **Encerrar jogo**

#### **Descrição**

Encerra o jogo

```bash
GET /game/end?gameId={gameId}
```

#### Response

**HTTP 200 OK**

```"Game ended!"```

5. **Obter ranking**

#### **Descrição**

Obtém ranking

```bash
GET /game/ranking
```

#### Response

**HTTP 200 OK**

```json
[
  {
    "username": "string",
    "score": "double"
  },
  ...
]
```

---

## Pré-requisitos para Rodar o Projeto

1. **Configurar a API Key do OMDB**

- Insira sua chave pessoal da API do OMDB no arquivo `application.yml`:
- ```yaml
  omdb: 
    api-key: <INSERT_PERSONAL_KEY>
  ```

- A chave pode ser obtida no site [OMDB API](http://www.omdbapi.com/).

2. **Java**

- Certifique-se de que o Java 17 ou superior esteja instalado.

3. **Maven**

- Garanta que o Maven está instalado e configurado.

---

## Passos para Executar

1. Clone o repositório:

```bash
git clone <repository-url>
cd cardgame
```

2. Compile o projeto

```bash
mvn clean install
```

3. Execute o projeto

```bash
mvn spring-boot:run
```

4. Acesse a aplicação em:

```http://localhost:8080```

---

## **Tecnologias Utilizadas**

- **Spring Boot: Framework principal para desenvolvimento do backend.**
- **Spring Security: Para autenticação e autorização.**
- **JWT (JSON Web Token): Gerenciamento de sessões de usuário.**
- **H2 Database: Banco de dados em memória para persistência.**
- **OMDB API: Integração para buscar detalhes dos filmes.**

---

## Estrutura do Banco de Dados

1. Tabela ```'user'```

- Campos: ```id```, ```username```, ```password```, ```score```

2. Tabela ```game```

- Campos: ```id```, ```user_id```, ```status```, ```attempts_left```

3. Tabela ```round```

- Campos: ```id```, ```game_id```, ```movie1_id```, ```movie2_id```, ```answered```

4. Tabela ```movie```

- Campos: ```id```, ```title```, ```rating```, ```votes```

---

## Observações

- **Tokens JWT**:
    - Após login, o token deve ser enviado no cabeçalho de cada requisição:
  ```makefile
  Authorization: Bearer <token>
  ```

- **Segurança**:
    - Apenas endpoints de autenticação (/auth/**) são públicos. Todos os demais requerem autenticação JWT.

- **População inicial**:
    - Os filmes são carregados automaticamente ao iniciar a aplicação, utilizando a API OMDB.