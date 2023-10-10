# CodeChallenge

A brief overview of this project. First of all I really enjoyed doing this challenge as it helped me to try out some new things.

- The client side is built with [React](https://react.dev/) and the server side with [Spring Boot](https://spring.io/projects/spring-boot). I used the [JHipster](https://www.jhipster.tech/) generator to scaffold the project.

- The project is build with [Gradle](https://gradle.org/) and [Node](https://nodejs.org/en), and the Gradle binary is included with the project (`/gradle/wrapper/gradle-wrapper.jar`) so that you don't have to install anything. As for node please install a version of Node which is `>=16.17.0`

- The project is also configured to use [Docker](https://www.docker.com/) and that I find is the easiest way to start it.

## Method 1

- Clone the Repo, and navigate to the root directory of the project.
- Run `./gradlew -x webapp` to build and run the project backend.
- On a separate terminal run `npm install` to install the frontend dependencies.
- Run `npm start` to start the frontend.
- Go to [localhost:9000](http://localhost:9000/) to see the application running.
- Note that both the backend and frontend must be running for the application to work so keep both terminals (one for `./gradlew -x webapp` and one for `npm start`) running.

## Method 2

- Clone the Repo, and navigate to the root directory of the project.
- Run `npm run java:docker` to build the docker image.
- Run ` docker-compose -f src/main/docker/app.yml up -d` to start the docker image.
- Go to [localhost:8080](http://localhost:8080/) to see the application running.

## Notes

- In Method 1 the application is run using a small file based [H2 database](http://h2database.com/html/main.html).

- In Method 2 the application is run using a [PostgreSQL](https://www.postgresql.org/) database.

- Dummy data is generated on startup for both methods; using [Liquibase](https://www.liquibase.org/). The data is generated from the `src/main/resources/config/liquibase/fake-data` directory.

- If you are running an arm64 processor os like MacOS with M1 processor family instead of `npm run java:docker` use `npm run java:docker:arm64` to build the docker image.

## Project Structure

Node is required for generation and recommended for development. `package.json` is always generated for a better development experience with prettier, commit hooks, scripts and so on.

`/src/main/java` contains the back-end. The API endpoints can be found at, `src/main/java/come/sudharaka/codechallenge/web/rest`.

`/src/main/webapp` contains the front-end. The React components can be found at, `src/main/webapp/app/entities`.

- `/src/main/docker` - Docker configurations for the application and services that the application depends on.

- After you start the application the Swagger UI will be available at, [localhost:9000/swagger-ui/](http://localhost:9000/admin/docs) or [localhost:8080/swagger-ui/](http://localhost:8080/admin/docs). The API endpoints can be tested from there as well.

## Client tests

Some unit tests are run by [Jest](https://jestjs.io/). They can be run with:

```
npm test
```
