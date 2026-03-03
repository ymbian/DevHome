## ADDED Requirements

### Requirement: Tech improvement tasks are stored per type in MySQL

The system SHALL store tech improvement tasks in MySQL with one table per task type (e.g. vulnerability fix, component upgrade, database compliance, database performance). Each table SHALL contain at least: a primary key, application name (应用名), rectification issue description (整改问题描述), and rectification deadline (整改截止时间). The system SHALL support querying the list of tasks by type so that the API can return tasks from the corresponding table.

#### Scenario: Query list by type

- **WHEN** the client requests the task list for a given type (e.g. vulnerability fix)
- **THEN** the system SHALL query the MySQL table corresponding to that type and SHALL return records that include application name, issue description, and deadline

#### Scenario: Each type has its own table

- **WHEN** the system persists or reads tech improvement tasks
- **THEN** each supported type (vulnerability fix, component upgrade, database compliance, database performance, other) SHALL map to exactly one MySQL table with consistent column semantics (application name, issue description, deadline)

### Requirement: List API returns MySQL-backed task fields

The system SHALL provide a list API (e.g. GET /api/tech-improvement/tasks) that accepts a type filter and returns tasks from the corresponding MySQL table. Each item in the response SHALL include at least: id, application name (应用名), rectification issue description (整改问题描述), rectification deadline (整改截止时间), and optionally the task type.

#### Scenario: Response shape for catalog display

- **WHEN** the list API returns task data
- **THEN** each task object SHALL include fields suitable for display as: application name, issue description, and deadline (e.g. applicationName, issueDescription, deadline)

#### Scenario: Empty type returns empty or all-types behavior

- **WHEN** the client requests the list without a type or with "all"
- **THEN** the system SHALL either return an empty list or aggregate from all type tables, as defined by product behavior; the API contract SHALL be documented
