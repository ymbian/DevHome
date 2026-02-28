## ADDED Requirements

### Requirement: Design document is generated from requirement text

The system SHALL produce a design document (e.g. Markdown) from the user-submitted requirement description, using configurable template and structure.

#### Scenario: Generate design on pipeline step

- **WHEN** the pipeline runs the design-generation step with a persisted requirement text
- **THEN** the system SHALL generate a design document and store it (e.g. file or record) linked to the same pipeline instance

#### Scenario: Design output format is configurable

- **WHEN** an administrator or configuration specifies a design template or section layout
- **THEN** the system SHALL use that template or layout when generating the design document

#### Scenario: User can view and edit generated design

- **WHEN** the user requests to view the generated design
- **THEN** the system SHALL present the design content (e.g. in an editor or preview) and SHALL allow the user to edit and save changes before proceeding to openspec or coding steps

### Requirement: Design generation may use LLM

The system MAY use an LLM (local or remote API) to turn the requirement description into structured design content. If no LLM is configured, the system MAY output a template with placeholders for manual completion.

#### Scenario: LLM configured

- **WHEN** an LLM endpoint or provider is configured
- **THEN** the system SHALL use it to generate design content from the requirement text within the configured template

#### Scenario: No LLM configured

- **WHEN** no LLM is configured
- **THEN** the system SHALL still produce a design artifact (e.g. template with placeholders) so the pipeline can continue and the user can fill it manually
