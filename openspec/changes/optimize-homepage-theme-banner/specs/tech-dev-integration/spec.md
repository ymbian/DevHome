## ADDED Requirements

### Requirement: Theme banner style is consistent across core modules
The system SHALL apply the same theme banner visual style across 开发, 技改, and 工作计划 pages to ensure cross-module brand consistency.

#### Scenario: Cross-page consistency check
- **WHEN** the user navigates among the three core pages
- **THEN** the theme banner SHALL keep the same alignment, typography hierarchy, gradient card style, and spacing rhythm

### Requirement: Theme banner changes are maintained in shared style
The system SHALL implement theme banner style in shared stylesheet definitions so one update propagates to all core pages.

#### Scenario: Shared style propagation
- **WHEN** the `.site-theme` style is changed in the shared CSS
- **THEN** all pages that use `.site-theme` SHALL reflect the same updated visual behavior without per-page overrides
