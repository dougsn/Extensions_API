<!-- [JAVASCRIPT__BADGE]: https://img.shields.io/badge/Javascript-000?style=for-the-badge&logo=javascript
[TYPESCRIPT__BADGE]: https://img.shields.io/badge/typescript-D4FAFF?style=for-the-badge&logo=typescript
[EXPRESS__BADGE]: https://img.shields.io/badge/express-005CFE?style=for-the-badge&logo=express
[VUE__BADGE]: https://img.shields.io/badge/VueJS-fff?style=for-the-badge&logo=vue
[NEST__BADGE]: https://img.shields.io/badge/nest-7026b9?style=for-the-badge&logo=nest
[GRAPHQL__BADGE]: https://img.shields.io/badge/GraphQL-e10098?style=for-the-badge&logo=graphql -->
[JAVA_BADGE]:https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRING_BADGE]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[MYSQL_BADGE]:https://img.shields.io/badge/MySQL-005CFE.svg?style=for-the-badge&logo=mysql&logoColor=white
[REACT_BADGE]:https://img.shields.io/badge/react-000.svg?style=for-the-badge&logo=react&logoColor=blue


<h1 align="center" style="font-weight: bold;">Extension 💻</h1>

![spring][SPRING_BADGE]
![java][JAVA_BADGE]
![mysql][MYSQL_BADGE]
![react][REACT_BADGE]

<p align="center">
 <a href="#started">Primeiros passos</a> • 
  <a href="#routes">API Endpoints</a> •
 <a href="#colab">Colaboradores</a> •
</p>

<p align="center">
  <b>Este projeto foi criado para praticar a implementação de tests end-to-end com JUnit5, Restassured e Testcontainers.</b>
</p>

<h2 id="started">🚀  Primeiros passos</h2>

Here you describe how to run your project locally

<h3>Pré-requisitos</h3>

Para conseguir executar o projeto localmente é necessário ter instalado:

- [Java](https://www.oracle.com/br/java/technologies/downloads/#java17)
- [MySQL](https://dev.mysql.com/downloads/installer/)
- [NodeJS](https://nodejs.org/en/download/current)
- [Yarn](https://classic.yarnpkg.com/lang/en/docs/install/#windows-stable)

E também é necessário ter um Schema chamado: `ramal`

<h3>Clonagem</h3>

Como clonar meu projeto

```bash
git clone https://github.com/dougsn/Extensions_API.git
```

<h3>Variáveis de Ambiente</h2>

Crie as variáveis de ambiente no seu computador, e as referencie no `application.yml` , por exemplo:

```yaml
APP_PROFILE=dev;
CORS_URL=http://localhost:8080,http://localhost:5173;
DEV_MYSQL_HOST=localhost;
DEV_MYSQL_PASSWORD=salvador1;
DEV_MYSQL_PORT=3306;
DEV_MYSQL_USER=root;
JWT_SECRET=321D4B6150645367566B597033733676397924423F3328482B4D625985546222;
```

E inclua no `application.yml` e `application-dev.yml`, por exemplo:

```yaml
application.yml

cors.originPatterns:${CORS_URL}
spring.profiles.active:${APP_PROFILE}
spring.jwt.secret:${JWT_SECRET}
```

E no `application-dev.yml`

```yaml
application-dev.yml

spring.datasource.url:jdbc:mysql://${DEV_MYSQL_HOST}:${DEV_MYSQL_PORT}/ramal
spring.datasource.username:${DEV_MYSQL_USER}
spring.datasource.password:${DEV_MYSQL_PASSWORD}
```

<h3>Iniciando</h3>

Como iniciar o projeto

```bash
Backend

cd Extension_API/backend
mvn spring-boot:run

Frontend
cd Extension_API/frontend
mvn yarn dev
``````


<h2 id="routes">📍 API Endpoints</h2>

Here you can list the main routes of your API, and what are their expected request bodies.
​
| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>GET /authenticate</kbd>     | retrieves user info see [response details](#get-auth-detail)
| <kbd>POST /authenticate</kbd>     | authenticate user into the api see [request details](#post-auth-detail)

<h3 id="get-auth-detail">GET /authenticate</h3>

**RESPONSE**
```json
{
  "name": "Fernanda Kipper",
  "age": 20,
  "email": "her-email@gmail.com"
}
```

<h3 id="post-auth-detail">POST /authenticate</h3>

**REQUEST**
```json
{
  "username": "fernandakipper",
  "password": "4444444"
}
```

**RESPONSE**
```json
{
  "token": "OwoMRHsaQwyAgVoc3OXmL1JhMVUYXGGBbCTK0GBgiYitwQwjf0gVoBmkbuyy0pSi"
}
```

<h2 id="colab">🤝 Colaboradores</h2>

Um agradecimento especial a todas as pessoas que contribuíram para este projeto.

<table>
  <tr>
    <td align="center">
      <a href="#">
        <img src="https://avatars.githubusercontent.com/u/80468956?s=96&v=4" width="100px;" alt="Douglas Nascimento Profile Picture"/><br>
        <sub>
          <b>Fernanda Kipper</b>
        </sub>
      </a>
    </td>
  </tr>
</table>
