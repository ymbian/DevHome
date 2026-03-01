## ADDED Requirements

### Requirement: Platform can trigger or drive Cursor-based coding after specs are ready

The system SHALL provide a way to trigger or drive coding in Cursor after openspec artifacts (e.g. tasks.md and related specs) are ready. This MAY be implemented by generating a task list and context for Cursor Agent, or by calling a Cursor API if one is available and configured.

#### Scenario: Emit task list and context for Cursor

- **WHEN** the pipeline has completed openspec integration and tasks are ready
- **THEN** the system SHALL make available the tasks and relevant context (e.g. tasks.md, design.md, proposal) so that the user or Cursor Agent can execute coding steps (e.g. via /opsx:apply or equivalent)

#### Scenario: Optional one-shot trigger via API

- **WHEN** a Cursor API (e.g. Composer/Agent) is configured and the pipeline reaches the coding step
- **THEN** the system MAY call that API to start or queue a coding job with the current change name and artifact paths; if no API is configured, the system SHALL rely on task list and documentation only

#### Scenario: Clear indication when coding step is ready

- **WHEN** openspec integration has produced apply-ready artifacts
- **THEN** the system SHALL show the user that the coding step is ready (e.g. "Run /opsx:apply" or "Open Cursor and apply tasks") and SHALL expose or link to the change name and paths needed for implementation

### Requirement: Cursor integration does not depend on undocumented APIs

The system SHALL NOT require the use of undocumented or private Cursor APIs. If the only way to trigger coding is via user action (e.g. running a command in Cursor or pasting a task list), that SHALL be the default and documented behavior.

#### Scenario: No Cursor API configured

- **WHEN** no Cursor API is configured
- **THEN** the system SHALL still mark the pipeline as ready for coding and SHALL provide instructions or links so the user can complete coding in Cursor manually or via documented Cursor features

#### Scenario: Cursor API configured

- **WHEN** a Cursor API endpoint and credentials are configured
- **THEN** the system MAY use them only in ways consistent with Cursor’s public or documented API; any failure SHALL fall back to the task-list / manual flow
