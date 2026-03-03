## MODIFIED Requirements

### Requirement: Tech improvement tasks are listed and filterable by type

The system SHALL provide a catalog of tech improvement tasks. The task list SHALL be sourced from MySQL per-type tables. Each displayed task SHALL include at least: application name (应用名), rectification issue description (整改问题描述), and rectification deadline (整改截止时间). The system SHALL support filtering the list by type (e.g. vulnerability fix, component upgrade); when the user selects a type, the system SHALL query the corresponding MySQL table and display the result in the tech improvement module UI.

#### Scenario: User opens tech improvement module

- **WHEN** the user navigates to the tech improvement module
- **THEN** the system SHALL display a list of tech improvement tasks with application name, issue description, deadline, and key attributes (sourced from MySQL by type)

#### Scenario: User filters by type

- **WHEN** the user selects one or more task types (e.g. vulnerability fix, component upgrade)
- **THEN** the system SHALL show only tasks from the corresponding MySQL table(s) matching the selected types

#### Scenario: Task list shows required fields from MySQL

- **WHEN** the tech improvement task list is displayed
- **THEN** each row SHALL show at least application name (应用名), rectification issue description (整改问题描述), and rectification deadline (整改截止时间)

#### Scenario: Task has required attributes

- **WHEN** a tech improvement task is stored in a MySQL per-type table
- **THEN** the system SHALL store and display at least: application name, rectification issue description, rectification deadline (and type implied by table)

### Requirement: Supported task types include standard categories

The system SHALL support at least the following task types: vulnerability fix, component version upgrade, database specification compliance, database performance optimization. The system MAY support an "other" or extensible type for future categories. Each type SHALL map to one MySQL table for task storage and listing.

#### Scenario: Types are available for filtering and display

- **WHEN** the user views or filters tasks
- **THEN** the system SHALL expose the standard types (vulnerability fix, component upgrade, database compliance, database performance) and MAY expose additional configured types

#### Scenario: New task can be created with a type

- **WHEN** a tech improvement task is created (e.g. via API or admin UI)
- **THEN** the system SHALL accept one of the supported types and SHALL persist it in the corresponding MySQL table (or existing platform store, per implementation choice)
