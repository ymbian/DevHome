## ADDED Requirements

### Requirement: Type filter is presented as horizontal tabs

The system SHALL present the tech improvement type filter as horizontal tabs above the task list. Tabs SHALL include "全部" (all) and each supported task type (e.g. 漏洞修复, 组件版本升级, 数据库规范整改, 数据库性能优化, 其他). The currently selected tab SHALL be visually highlighted. Clicking a tab SHALL filter the task list to that type without requiring a dropdown to be opened.

#### Scenario: User sees all types as tabs

- **WHEN** the user opens the tech improvement module
- **THEN** the system SHALL display the type filter as horizontal tabs so that "全部" and all type labels are visible at once above the list

#### Scenario: User switches filter by clicking a tab

- **WHEN** the user clicks a type tab (e.g. 漏洞修复)
- **THEN** the system SHALL filter the task list to that type and SHALL highlight the selected tab as active

#### Scenario: Active tab reflects current filter

- **WHEN** the task list is filtered by a specific type (or showing all)
- **THEN** the corresponding tab SHALL be visually indicated as the current selection
