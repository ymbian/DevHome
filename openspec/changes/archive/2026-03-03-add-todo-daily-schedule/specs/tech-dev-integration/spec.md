## ADDED Requirements

### Requirement: Global navigation includes todo module entry
The system SHALL expose a "待办事项" navigation entry in the same sidebar navigation used by development and tech improvement pages.

#### Scenario: Sidebar shows todo entry
- **WHEN** the user opens any page that renders the shared sidebar navigation
- **THEN** the sidebar SHALL include links for 开发, 技改, and 待办事项

#### Scenario: User navigates to todo module
- **WHEN** the user clicks the 待办事项 entry in the sidebar
- **THEN** the system SHALL navigate to the todo page

### Requirement: Navigation highlight supports todo route
The system SHALL apply active navigation highlighting for the todo page using the same behavior style as existing modules.

#### Scenario: Todo page is active
- **WHEN** the current path is the todo page route
- **THEN** the 待办事项 navigation item SHALL be highlighted as active and other module entries SHALL not be highlighted
