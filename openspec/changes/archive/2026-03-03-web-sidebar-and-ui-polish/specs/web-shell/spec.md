## ADDED Requirements

### Requirement: Global shell with left sidebar navigation

The system SHALL provide a global web shell consisting of a fixed left sidebar and a main content area. The sidebar SHALL list at least two navigation entries: "开发" (development) and "技改" (tech improvement), linking to the requirement page and the tech improvement page respectively.

#### Scenario: User sees sidebar on every main page

- **WHEN** the user opens the home page, tech improvement page, or design page
- **THEN** the same left sidebar SHALL be visible with "开发" and "技改" links

#### Scenario: Current page is highlighted in sidebar

- **WHEN** the user is on the requirement page, tech page, or design page
- **THEN** the corresponding sidebar entry SHALL be visually highlighted (e.g. active state) so the current module is clear

#### Scenario: Main content area shows existing pages

- **WHEN** the user clicks a sidebar link or navigates directly via URL
- **THEN** the main content area SHALL display the existing page content (requirement form, tech task list, or design editor) without removing or replacing the sidebar

### Requirement: URLs and deep links remain valid

The system SHALL preserve existing URLs (e.g. `/`, `/tech.html`, `/design.html?id=...`) so that bookmarks and external links continue to work. The shell SHALL be added around existing content without changing route semantics.

#### Scenario: Direct access to design page

- **WHEN** the user opens `/design.html?id=xxx` directly
- **THEN** the page SHALL render with the sidebar and the design content for the given instance, and the sidebar SHALL still reflect the current context (e.g. design or development)

#### Scenario: Navigation from sidebar

- **WHEN** the user clicks "开发" or "技改" in the sidebar
- **THEN** the browser SHALL navigate to `/` or `/tech.html` respectively, and the main content SHALL update accordingly
