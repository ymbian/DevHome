## ADDED Requirements

### Requirement: User can select one or more tech improvement tasks to handle

The system SHALL allow the user to select one or more tech improvement tasks from the catalog and trigger "handle" or "dispatch". The system SHALL then convert the selected task(s) into a requirement description and SHALL call the development module to create a pipeline instance and run the design and openspec steps so that the tech improvement flows through to coding-ready state.

#### Scenario: User selects a single task and dispatches

- **WHEN** the user selects one tech improvement task and chooses to handle it
- **THEN** the system SHALL create a pipeline instance in the development module with a requirement description derived from the task (e.g. title + description + type), SHALL trigger design generation and openspec run for that instance, and SHALL expose or link to the resulting pipeline instance (e.g. design page or coding-ready guidance)

#### Scenario: User selects multiple tasks and dispatches

- **WHEN** the user selects multiple tech improvement tasks and chooses to handle them
- **THEN** the system SHALL create one pipeline instance per selected task (or one combined instance, per product decision), SHALL run the development module flow for each instance, and SHALL report success or failure and links to the created instance(s)

#### Scenario: Dispatch failure is reported

- **WHEN** any step of the dispatch (create pipeline, design generate, openspec run) fails
- **THEN** the system SHALL record or display the failure and SHALL allow the user to retry or inspect the state

### Requirement: Requirement description is derived from tech task

The system SHALL build the requirement text passed to the development module from the tech improvement task's title, description, and type so that the resulting design and openspec artifacts are meaningful for the tech improvement.

#### Scenario: Requirement text includes task context

- **WHEN** a tech improvement task is dispatched
- **THEN** the requirement text sent to the development module SHALL include at least the task title and description and SHALL indicate the task type (e.g. "技改-漏洞修复: ..." or "[Component upgrade] ...")

#### Scenario: Pipeline instance is linked to task

- **WHEN** dispatch succeeds
- **THEN** the system SHALL associate the created pipeline instance (e.g. by storing the instance id on the task or in a separate relation) so that the user can navigate from the tech task to the development instance
