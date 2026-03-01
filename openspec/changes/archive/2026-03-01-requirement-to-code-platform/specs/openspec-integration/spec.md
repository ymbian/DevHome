## ADDED Requirements

### Requirement: Platform can create or update an openspec change

The system SHALL call the openspec CLI to create a new change or operate on an existing change (e.g. `openspec new change <name>`), using the change name derived from the requirement or pipeline instance.

#### Scenario: Create new change when none exists

- **WHEN** the pipeline runs the openspec-integration step and no change exists for the current pipeline instance
- **THEN** the system SHALL invoke `openspec new change <name>` (or equivalent) so that artifacts can be generated under that change

#### Scenario: Reuse or update existing change

- **WHEN** the pipeline runs and an change already exists for the current instance (e.g. same id or name)
- **THEN** the system SHALL use that change for subsequent openspec commands (e.g. `openspec status --change <name>`)

### Requirement: Platform can drive openspec artifact generation

The system SHALL invoke openspec in a way that produces or updates proposal, design, specs, and tasks artifacts as defined by the openspec schema (e.g. spec-driven). This MAY be done by writing files into the change directory and/or by calling openspec commands that generate or validate artifacts.

#### Scenario: Proposal and design files are present for the change

- **WHEN** the pipeline has generated or updated proposal and design content (from requirement and design-generation step)
- **THEN** the system SHALL ensure the corresponding files (e.g. proposal.md, design.md) exist under the change directory so that `openspec status` reflects them as done

#### Scenario: Specs and tasks are generated or updated

- **WHEN** the pipeline proceeds to generate specs and tasks
- **THEN** the system SHALL create or update specs under the change (e.g. specs/<capability>/spec.md) and tasks.md so that the change becomes apply-ready according to openspec status

#### Scenario: openspec CLI availability is checked

- **WHEN** the user or pipeline attempts to run openspec integration
- **THEN** the system SHALL verify that the openspec CLI is available (e.g. `openspec --version` or which) and SHALL surface a clear error if it is not

### Requirement: openspec working directory is configurable

The system SHALL allow configuration of the working directory or repository root where openspec is run (e.g. DevHome repo path), so that changes are created under the correct openspec/changes/<name>/ path.

#### Scenario: Configure openspec root

- **WHEN** the platform is configured with a project path
- **THEN** all openspec CLI invocations SHALL be executed with that path as the current working directory (or equivalent) so that changes and artifacts are stored in the expected location
