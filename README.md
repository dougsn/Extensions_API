<h1 align="center" style="font-weight: bold;">Extension 💻</h1>


<div align="center">
 <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white">
 <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white">
 <img src="https://img.shields.io/badge/MySQL-005CFE.svg?style=for-the-badge&logo=mysql&logoColor=white">
 <img src="https://img.shields.io/badge/react-000.svg?style=for-the-badge&logo=react&logoColor=blue">
</div>


<p align="center">
 <a href="#started">Primeiros passos</a> • 
  <a href="#routes">API Endpoints</a> •
 <a href="#colab">Colaboradores</a> •
</p>

<p align="center">
  <b>Este projeto foi criado para praticar a implementação de testes end-to-end com JUnit5, Restassured e Testcontainers.</b>
</p>

<h2 id="started">🚀  Primeiros passos</h2>

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
DEV_MYSQL_PASSWORD=senha;
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

Todos os endpoints se encontram no Swagger, assim que iniciar o backend acesse: 

- [Swagger](http://localhost:8080/swagger-ui/index.html#/)

<h2 id="colab">🤝 Colaboradores</h2>

Um agradecimento especial a todas as pessoas que contribuíram para este projeto.

<table>
  <tr>
    <td align="center">
      <a href="#">
        <img src="https://avatars.githubusercontent.com/u/80468956?s=96&v=4" width="100px;" alt="Douglas Nascimento Profile Picture"/><br>
        <sub>
          <b>Douglas Nascimento</b>
        </sub>
      </a>
    </td>
  </tr>
</table>
