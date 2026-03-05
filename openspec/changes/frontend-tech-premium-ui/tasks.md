## 1. Visual tokens in common.css

- [x] 1.1 Add or extend CSS custom properties in `:root` for tech-premium palette (primary, secondary, accent, background tiers, border radius, shadows, transition duration)
- [x] 1.2 Ensure existing variables (e.g. `--color-primary`, `--color-bg`) are kept and optionally extended for the new look

## 2. Sidebar and main area

- [x] 2.1 Update `.app-sidebar` and `.app-main` to use the new tokens (background, border, shadow, spacing) with smooth hover/active transitions
- [x] 2.2 Verify sidebar nav links and active state use tokens and transitions

## 3. Buttons and form controls

- [x] 3.1 Update `.btn`, `.btn-secondary`, `.btn-outline` (if present) to use tokens and add consistent focus/hover transitions
- [x] 3.2 Update `input`, `select`, `textarea` and their focus styles to use tokens and light motion

## 4. Cards and list items

- [x] 4.1 Update `.card` and `.item` to use tokens for background, border, radius, and shadow so they match the tech-premium style

## 5. Theme banner and typography

- [x] 5.1 Update `.site-theme` (if used) and heading styles to use tokens and a consistent tech-premium typography/color
- [x] 5.2 Ensure body and list typography remain readable and use the same token set

## 6. Page-level consistency and verification

- [x] 6.1 Apply or align design.html (and any other page-specific inline styles) to use common tokens; reduce inline overrides where possible
- [x] 6.2 Manually verify index, design, tech, work-plan pages render correctly with no broken layout or removed class names
