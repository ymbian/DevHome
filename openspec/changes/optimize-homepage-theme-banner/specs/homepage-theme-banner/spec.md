## ADDED Requirements

### Requirement: Theme banner is center-aligned and visually prominent
The system SHALL render the homepage theme banner text as center-aligned and with a clear primary-title hierarchy so users can immediately identify the site theme.

#### Scenario: User opens any core page
- **WHEN** the user opens 开发, 技改, or 工作计划 page
- **THEN** the top theme banner SHALL be displayed centered with larger typography than page section titles

### Requirement: Theme banner uses brand card style
The system SHALL style the theme banner with a blue-purple gradient background, rounded corners, and card shadow to create a homepage-like thematic impression.

#### Scenario: Theme banner style is applied
- **WHEN** the page renders the `.site-theme` element
- **THEN** the banner SHALL use blue-purple gradient background, visible shadow, and rounded-corner card styling

### Requirement: Typography and spacing reinforce attraction
The system SHALL increase text weight, font size, slight letter spacing, and padding for the theme banner to improve readability and visual attraction.

#### Scenario: Theme banner text hierarchy
- **WHEN** the theme banner is displayed
- **THEN** the text SHALL appear in bold, enlarged form with slight letter spacing and generous inner spacing
