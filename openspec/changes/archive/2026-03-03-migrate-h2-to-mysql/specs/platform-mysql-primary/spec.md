## ADDED Requirements

### Requirement: Platform uses MySQL as primary datasource

The platform SHALL use MySQL as the primary datasource for all JPA-managed entities (e.g. pipeline instances and tech improvement tasks). The system SHALL NOT use H2 as the default datasource in production configuration.

#### Scenario: Application starts with MySQL configured

- **WHEN** `spring.datasource.url` points to a MySQL instance and the database schema has been created (e.g. via provided DDL)
- **THEN** the application starts successfully and all entity reads/writes go to MySQL

#### Scenario: Pipeline and tech task persistence on MySQL

- **WHEN** the user or system creates or updates a pipeline instance or a tech improvement task
- **THEN** the data is persisted in MySQL tables `pipeline_instance` and `tech_improvement_task` respectively

### Requirement: MySQL schema is provided for platform tables

The change SHALL provide DDL or migration scripts (e.g. Flyway/Liquibase or a single SQL file) to create `pipeline_instance` and `tech_improvement_task` tables on MySQL with column types compatible with existing JPA entities.

#### Scenario: New database setup

- **WHEN** an operator runs the provided schema script on an empty MySQL database and starts the application with that datasource
- **THEN** the application creates or validates the schema and operates without schema-related errors

### Requirement: H2 is not the default datasource

The default configuration SHALL NOT set `spring.datasource.url` to an H2 database. H2 MAY be available as an optional profile (e.g. for local development when MySQL is not available).

#### Scenario: Default configuration

- **WHEN** the application is started with default or production configuration (no H2 profile)
- **THEN** the primary datasource is MySQL (or explicitly documented as required); H2 is not used as the primary datasource
