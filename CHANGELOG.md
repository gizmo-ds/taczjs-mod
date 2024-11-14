# Changelog

## Unreleased

## v1.2.2+mc1.20.1 - 2024-11-14

### Added

- TaCZStartupEvents: Added a `getStdJson` method to AbstractLoadEvent, enabling the retrieval of JavaScript-compatible
  standard JSON. The `getJson` method remains available for non-standard JSON formats.

### Changed

- Build Process: Updated to use a compressed icon for output, reducing file size and optimizing resource usage.

## v1.2.1+mc1.20.1 - 2024-11-11

### Added

- Added `TaCZJSUtils.openRefitScreen` and `TaCZJSUtils.mainHandHoldGun` functions.

### Fixed

- Prevented a crash when attempting to retrieve an icon for a non-existent
  item. [#2](https://github.com/gizmo-ds/taczjs-mod/issues/2)
