## ADDED Requirements

### Requirement: Development module exposes APIs callable by tech improvement module

The system SHALL provide programmatic entry points that the tech improvement module can use to create a pipeline instance from a requirement description and to trigger design generation and openspec run for a given instance. These MAY be the same REST endpoints already used by the web UI (e.g. POST /api/requirements, POST /api/instances/{id}/design/generate, POST /api/instances/{id}/openspec/run).

#### Scenario: Create pipeline from requirement text

- **WHEN** the tech improvement module (or backend service) sends a request with a requirement description (e.g. POST /api/requirements with body { "text": "..." })
- **THEN** the development module SHALL create a new pipeline instance and SHALL return the instance id (e.g. in the response body) so that the caller can use it for subsequent steps

#### Scenario: Trigger design generation for an instance

- **WHEN** the caller sends a request to generate design for a given instance id (e.g. POST /api/instances/{id}/design/generate)
- **THEN** the development module SHALL run the design generation for that instance and SHALL return or persist the generated design content

#### Scenario: Trigger openspec run for an instance

- **WHEN** the caller sends a request to run openspec for a given instance id (e.g. POST /api/instances/{id}/openspec/run)
- **THEN** the development module SHALL run the openspec integration (create/update change, write artifacts) for that instance and SHALL return the change path or status

#### Scenario: Existing web flow unchanged

- **WHEN** a user uses the existing development module UI (submit requirement, generate design, run openspec)
- **THEN** the behavior SHALL remain unchanged; the same APIs may be used by both the UI and the tech improvement dispatch flow

### Requirement: Tech improvement dispatch orchestrates development steps

The tech improvement module SHALL orchestrate the sequence: create pipeline with requirement text → generate design → run openspec, using the development module's APIs or services, so that a single "handle" action results in a coding-ready pipeline instance.

#### Scenario: Full flow in one dispatch action

- **WHEN** the user dispatches one or more tech tasks
- **THEN** the system SHALL call the development module to create instance(s), then for each instance SHALL call design generate and openspec run in order, and SHALL surface the final state (e.g. link to design page or coding-ready message) to the user

#### Scenario: User can open the created instance in development module

- **WHEN** dispatch has completed successfully
- **THEN** the user SHALL be able to open the created pipeline instance in the development module (e.g. via link to /design.html?id={instanceId}) to view design, run openspec again if needed, or see coding-ready guidance
