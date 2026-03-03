## ADDED Requirements

### Requirement: Status bar is shown above history task list

The system SHALL render a status bar above the history task list in the development module, and SHALL keep the history task list below it.

#### Scenario: Page initial render
- **WHEN** the user opens the development module page
- **THEN** the status bar SHALL appear above history tasks, and history tasks SHALL still be listed below

#### Scenario: Select a history item
- **WHEN** the user clicks a history requirement item
- **THEN** the status bar SHALL update to reflect the selected item's stage status

### Requirement: Status bar displays four stages with spec mode ON/OFF

The system SHALL display stages in order: `需求 -> 设计 -> spec模式(开启/关闭) -> 编码`.

#### Scenario: Spec mode enabled
- **WHEN** selected history item has `specModeEnabled = true`
- **THEN** the status bar SHALL display `spec模式: 开启`

#### Scenario: Spec mode disabled
- **WHEN** selected history item has `specModeEnabled = false`
- **THEN** the status bar SHALL display `spec模式: 关闭`
