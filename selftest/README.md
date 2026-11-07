# TaCZ JS Self-Test

KubeJS scripts that check whether TaCZ JS events work. All output is prefixed with `[TaCZJS Selftest]`.

Copy the three folders into `<gameDir>/kubejs/`, restart the client and enter a world. Use a gun to aim, shoot, melee
and reload, then run `/taczjs_selftest`.

Results are in `logs/kubejs/*.log`: `OK` means the event fired, `FAILED` means it is broken.

One thing to know: the scripts use `var` everywhere because KubeJS' Rhino rejects `const`/`let` inside functions that
run more than once.