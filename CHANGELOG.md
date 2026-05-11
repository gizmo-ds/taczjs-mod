### v1.4.2+mc1.20.1 - 2026-05-12

- Add mixins for TaCZ 1.1.8+ [#22](https://github.com/gizmo-ds/taczjs-mod/issues/22)

### v1.4.1+mc1.20.1 - 2026-05-09

- Add mixins for [TaCZ: Refabricated](https://github.com/Sh1roCu/TACZ-Refabricated) 0.6+ [#21](https://github.com/gizmo-ds/taczjs-mod/issues/21)

### v1.4.0+mc1.20.1 - 2026-01-11

- Added [TaCZ: Refabricated](https://github.com/Sh1roCu/TACZ-Refabricated) support [#20](https://github.com/gizmo-ds/taczjs-mod/pull/20)

### v1.3.7+mc1.20.1 - 2025-12-02

- Added TaCZ 1.1.7 support

### v1.2.2+mc1.20.1 - 2024-11-14

Added

- TaCZStartupEvents: Added a `getStdJson` method to AbstractLoadEvent, enabling the retrieval of JavaScript-compatible
  standard JSON. The `getJson` method remains available for non-standard JSON formats.

Changed

- Build Process: Updated to use a compressed icon for output, reducing file size and optimizing resource usage.

### v1.2.1+mc1.20.1 - 2024-11-11

Added

- Added `TaCZJSUtils.openRefitScreen` and `TaCZJSUtils.mainHandHoldGun` functions.

Fixed

- Prevented a crash when attempting to retrieve an icon for a non-existent
  item. [#2](https://github.com/gizmo-ds/taczjs-mod/issues/2)