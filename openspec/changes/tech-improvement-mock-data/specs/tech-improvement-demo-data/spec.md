## ADDED Requirements

### Requirement: Demo data for tech improvement task list

The system SHALL provide sample (mock) tech improvement tasks when the task list would otherwise be empty, so that the tech improvement page can display example rows for demonstration and development. Preloading SHALL occur only when using the built-in storage (e.g. H2) and when no tech improvement tasks exist yet.

#### Scenario: First run with H2 and empty table

- **WHEN** the application starts and the tech improvement task table (e.g. H2) is empty
- **THEN** the system SHALL insert a small set of sample tasks covering the supported types (e.g. vulnerability fix, component upgrade, database compliance, database performance, other) so that the list API returns non-empty data

#### Scenario: List page shows sample data

- **WHEN** the user opens the tech improvement page after the above preload
- **THEN** the task list SHALL display the sample tasks with at least type, title/application name, and description/issue description, so that filtering by type and list layout can be verified

#### Scenario: MySQL in use

- **WHEN** the tech improvement list is configured to use MySQL (e.g. per-type tables) for data
- **THEN** the system SHALL NOT insert H2 mock data; the list SHALL rely on MySQL data only
