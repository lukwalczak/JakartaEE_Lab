# JakartaEE_Lab — Narzędzia i aplikacje Jakarta EE

Uni project for the "Narzędzia i aplikacje Jakarta EE" course.

This repository contains a small Jakarta EE application with a REST API. The project is intended as a learning lab: the app demonstrates typical Jakarta EE concepts (JAX-RS, JPA, CDI, security integration, Open Liberty configuration). The latest branch contains a simple authentication flow and some secured endpoints.

## What is inside
- Monolithic Jakarta EE application (Java backend + web resources).
- REST API endpoints (JAX-RS).
- JPA persistence configured with H2 database (in-memory by default).
- Open Liberty server configuration for local development.
- A simple authentication example (on the latest branch) securing selected endpoints and views.
- Example HTTP requests for manual testing.

## Build

If your default `java` is not from JDK 17, run the build from the project root (here named `jakartaee-lab`) with:

```bash
JAVA_HOME=<path_to_jdk_home> mvn package
```

Or simply (when JAVA_HOME already points to JDK 17):

```bash
mvn package
```

Note: the project is tested with JDK 17 and Maven.

## Configuration

Open Liberty server is configured to use an in-memory H2 database by default. To change it to a file-based H2 database, open `src/main/liberty/server.xml` (or `src/main/liberty` in your project layout) and comment the in-memory datasource URL:

```xml
<properties URL="jdbc:h2:mem:jakartaee-lab-db"/>
```

and uncomment (or replace) with a file-based URL:

```xml
<properties URL="jdbc:h2:/tmp/jakartaee-lab-db;AUTO_SERVER=TRUE"/>
```

Remember to change `/tmp/jakartaee-lab-db` to a directory accessible on your system.

Persistence schema generation is controlled in `src/main/resources/META-INF/persistence.xml` (or the project `persistence.xml`). By default the application creates missing tables but does not drop them. This behavior is controlled by:

```xml
<property name="jakarta.persistence.schema-generation.database.action" value="create"/>
```

Change `value` to `none` or other supported actions if needed.

## Running

To run the application with Open Liberty in dev mode (recommended for development), from the project root:

```bash
mvn -P liberty liberty:dev
```

If your default `java` is not from JDK 17, run:

```bash
JAVA_HOME=<path_to_jdk_home> mvn -P liberty liberty:dev
```

After the server starts, the REST API and web endpoints will be available at the configured base URI (check `server.xml` for port/context).

## Stuff worth paying attention to

In the project you may find files and directories similar to the following (locations may vary slightly depending on module layout):

- `src/main/java/` - application source code (REST resources, services, entities).
- `src/main/resources/META-INF/persistence.xml` - JPA configuration and schema-generation options.
- `src/main/liberty/server.xml` - Open Liberty server configuration (features, security, datasource).
- `src/main/webapp/WEB-INF/web.xml` - web application descriptors and security constraints (if present).
- `src/main/webapp/WEB-INF/template/` - example templates / navigation (if using JSF).
- `src/main/resources/requests.http` or `.http` files - example HTTP requests for testing secured endpoints.
- `README.md` - this file.

## License

Project is licensed under the [MIT](LICENSE) license.


## Author

Łukasz Walczak
