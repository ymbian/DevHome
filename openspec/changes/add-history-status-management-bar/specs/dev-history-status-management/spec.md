## ADDED Requirements

### Requirement: History requirement items expose normalized stage status

The system SHALL provide a normalized stage status view for each history requirement item, including requirement stage, design stage, spec mode flag, and coding stage.

#### Scenario: Query history list with status view
- **WHEN** the user opens the development module home page and requests requirement history
- **THEN** each history item SHALL include status fields for `requirement`, `design`, `specModeEnabled`, and `coding`

#### Scenario: Old records without explicit status fields
- **WHEN** a historical record has no dedicated spec-mode or coding fields
- **THEN** the system SHALL derive stage status from existing data (e.g., status/design content/change path) and return a non-empty default status view

### Requirement: Spec mode state is explicitly represented

The system SHALL return spec mode as an explicit ON/OFF state for each history item, rather than requiring frontend inference from unrelated fields.

#### Scenario: Design generated with spec mode enabled
- **WHEN** the user triggers design generation with spec mode enabled for an instance
- **THEN** that instance's history status SHALL show `specModeEnabled = true`

#### Scenario: Design generated without spec mode
- **WHEN** the user triggers design generation with spec mode disabled for an instance
- **THEN** that instance's history status SHALL show `specModeEnabled = false`
