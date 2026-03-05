## ADDED Requirements

### Requirement: Global tech-premium visual tokens

The site SHALL expose a consistent set of CSS custom properties (variables) for tech-premium styling: primary/secondary/accent colors, background tiers, border radius, shadows, and optional motion duration. All shared components SHALL use these tokens so that a single change updates the whole look.

#### Scenario: Tokens applied in common.css

- **WHEN** a page loads and links to `common.css`
- **THEN** CSS variables for color, radius, shadow, and transition are defined in `:root` and used by sidebar, main, buttons, cards, and form controls

### Requirement: Sidebar and main area tech-premium look

The app sidebar and main content area SHALL use the global tokens to present a clear hierarchy: sidebar with distinct background and border, main area with readable contrast and spacing. Hover and active states SHALL use smooth transitions.

#### Scenario: Sidebar and main use tokens and transitions

- **WHEN** user views any page with sidebar and main
- **THEN** sidebar and main use the same color/spacing/shadow tokens and link hover/active states transition smoothly

### Requirement: Buttons and form controls tech-premium look

Primary and secondary (or outline) buttons and form controls (inputs, textareas, selects) SHALL use the global tokens and SHALL have clear focus and hover states with transitions. Buttons SHALL retain existing semantics (e.g. primary vs outline) while matching the tech-premium style.

#### Scenario: Buttons and inputs match style and have feedback

- **WHEN** user focuses or hovers a button or form field
- **THEN** visible feedback (color/border/shadow) is applied using tokens and transitions

### Requirement: Cards and list items tech-premium look

Cards and list items (`.card`, `.item`) SHALL use the global tokens for background, border, radius, and shadow so they appear elevated and consistent with the tech-premium style.

#### Scenario: Cards and items use tokens

- **WHEN** user views a page that renders `.card` or `.item` elements
- **THEN** those elements use the same tokens for background, border, radius, and shadow

### Requirement: Theme banner and typography consistency

The site theme banner (e.g. `.site-theme`) and headings SHALL use the global tokens and SHALL support a tech-premium look (e.g. gradient, typography scale). Typography SHALL remain readable and consistent across pages.

#### Scenario: Banner and headings use tokens

- **WHEN** user views a page that includes the theme banner or headings
- **THEN** banner and headings use the defined color/typography tokens and appear consistent with the rest of the UI

### Requirement: No breaking changes to structure or class names

Changes SHALL only add or extend CSS; existing class names and HTML structure SHALL remain valid. Existing behavior (links, forms, buttons) SHALL not be broken.

#### Scenario: Existing pages still work

- **WHEN** all tech-premium styles are applied
- **THEN** every existing page that uses `common.css` still renders correctly and all existing class names remain in use without removal or rename
