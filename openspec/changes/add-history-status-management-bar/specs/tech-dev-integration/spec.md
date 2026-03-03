## MODIFIED Requirements

### Requirement: Development module exposes APIs callable by tech improvement module

The system SHALL provide programmatic entry points that the tech improvement module can use to create a pipeline instance from a requirement description and to trigger design generation and openspec run for a given instance. These MAY be the same REST endpoints already used by the web UI (e.g. POST /api/requirements, POST /api/instances/{id}/design/generate, POST /api/instances/{id}/openspec/run).  
In addition, history-related endpoints SHALL return normalized stage status fields (`requirement`, `design`, `specModeEnabled`, `coding`) so callers can render a consistent process bar.

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

#### Scenario: History API returns status fields for process bar

- **WHEN** the caller queries requirement history or requirement detail for rendering task progress
- **THEN** the response SHALL include normalized stage status (`requirement`, `design`, `specModeEnabled`, `coding`) for each returned instance
