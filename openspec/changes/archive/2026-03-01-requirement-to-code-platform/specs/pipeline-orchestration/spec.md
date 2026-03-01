## ADDED Requirements

### Requirement: Pipeline runs in a defined sequence

The system SHALL run the pipeline in a defined order: requirement input → design generation → openspec integration (create/update change, produce proposal/design/specs/tasks) → cursor integration (trigger or prepare coding). Steps MAY be skipped or re-run only when explicitly requested or configured.

#### Scenario: Full pipeline run

- **WHEN** the user submits a requirement and starts the pipeline
- **THEN** the system SHALL execute, in order: persist requirement → generate design → create/update openspec change and artifacts → prepare or trigger Cursor coding

#### Scenario: Step failure does not run later steps by default

- **WHEN** a pipeline step fails (e.g. openspec CLI error or design generation error)
- **THEN** the system SHALL stop or pause the pipeline and SHALL not run subsequent steps until the user retries or fixes the failure

#### Scenario: User can re-run from a step

- **WHEN** the user chooses to re-run from a specific step (e.g. regenerate design or re-run openspec)
- **THEN** the system SHALL re-execute that step and, if configured, subsequent steps, using the current pipeline instance data

### Requirement: Pipeline state is visible and persisted

The system SHALL persist pipeline state (e.g. requirement id, current step, success/failure, artifact paths) and SHALL expose it so the user can see progress and resume or retry.

#### Scenario: Show current pipeline status

- **WHEN** the user views the pipeline for a given requirement or instance
- **THEN** the system SHALL show at least: which step is current or last completed, whether the pipeline succeeded or failed, and where outputs (design, change path, tasks) are located

#### Scenario: State survives restart

- **WHEN** the platform process restarts
- **THEN** persisted pipeline state SHALL remain available so the user can see past runs and, if supported, resume from the last successful step

### Requirement: One pipeline instance per requirement submission

The system SHALL treat each submitted requirement as one pipeline instance. Each instance SHALL have its own state, change name (or id), and artifact set. Multiple submissions SHALL create multiple instances unless the product explicitly supports "update existing" for the same requirement.

#### Scenario: New submission creates new instance

- **WHEN** the user submits a new requirement description
- **THEN** the system SHALL create a new pipeline instance and SHALL not overwrite another instance’s state or openspec change unless the user explicitly chooses to update an existing one

#### Scenario: Instance is identifiable

- **WHEN** the user lists or selects pipeline runs
- **THEN** each instance SHALL be identifiable (e.g. by id, timestamp, or requirement excerpt) and SHALL be associated with at most one openspec change name
