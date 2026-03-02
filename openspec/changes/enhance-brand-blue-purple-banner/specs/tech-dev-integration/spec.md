## ADDED Requirements

### Requirement: Brand banner style is consistent across core pages
The system SHALL apply the same brand blue-purple theme banner style across 开发, 技改, and 工作计划 pages.

#### Scenario: Consistent cross-page banner style
- **WHEN** users navigate between the three core pages
- **THEN** the banner SHALL keep the same centered layout, typography hierarchy, and brand blue-purple card treatment

### Requirement: Shared CSS controls banner visual behavior
The system SHALL maintain banner style through shared stylesheet definitions so visual updates propagate to all core pages without duplicate overrides.

#### Scenario: Shared style propagation
- **WHEN** `.site-theme` style is updated in shared CSS
- **THEN** all core pages using `.site-theme` SHALL reflect the same updated visual effect
