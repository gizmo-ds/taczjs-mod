//noinspection ES6ConvertVarToLetConst,JSUnresolvedReference,DuplicatedCode

// TaCZ JS self-test — client scripts
// Covers every TaCZClientEvents event. Output is prefixed with "[TaCZJS Selftest]".
//
// Hold a gun in game, then aim / shoot / melee / reload to fire the events. A summary is
// printed every 60 seconds when something new happened, and when leaving the world.
//
// var is used everywhere: KubeJS' Rhino rejects repeated const/let declarations in named
// functions ("redeclaration of var").

var SELFTEST_TAG = "[TaCZJS Selftest][client]"
var SELFTEST_LOG_LIMIT = 3

var selftestStats = {}
var selftestOrder = []
var selftestDirty = false
var selftestTicks = 0

function selftestStat(name) {
    if (!selftestStats[name]) {
        selftestStats[name] = {count: 0, ok: 0, failed: 0, logged: 0, sample: null}
        selftestOrder.push(name)
    }
    return selftestStats[name]
}

function selftestInfo(msg) {
    console.info(SELFTEST_TAG + " " + msg)
}

function selftestError(msg) {
    console.error(SELFTEST_TAG + " " + msg)
}

function selftestAssert(condition, message) {
    if (!condition) throw new Error(message)
}

function selftestId(event) {
    try {
        return String(event.getGunId())
    } catch (e) {
        return "<unknown>"
    }
}

function selftestCheckGunEvent(name, event, runtime) {
    var s = selftestStat(name)
    s.count++
    try {
        var gunId = event.getGunId()
        selftestAssert(gunId != null, "getGunId() returned null")
        selftestAssert(event.getGunIndex() != null, "getGunIndex() returned null")
        var vanillaInteract = event.isVanillaInteract()
        selftestAssert(typeof vanillaInteract === "boolean", "isVanillaInteract() did not return a boolean")
        if (runtime) {
            selftestAssert(event.getGunOperator() != null, "getGunOperator() returned null")
            selftestAssert(typeof event.canInteractEntity() === "boolean", "canInteractEntity() did not return a boolean")
            event.getBlockHitResult()
            event.getEntityHitResult()
        }
        s.ok++
        selftestDirty = true
        if (s.sample == null) s.sample = String(gunId)
        if (s.logged < SELFTEST_LOG_LIMIT) {
            s.logged++
            selftestInfo("OK " + name + " gun=" + String(gunId) + " vanillaInteract=" + vanillaInteract)
        }
    } catch (e) {
        s.failed++
        selftestDirty = true
        selftestError(name + " FAILED at " + selftestId(event) + ": " + e)
    }
}

function selftestSummary() {
    var fired = 0
    var failed = 0
    var i
    var s
    for (i = 0; i < selftestOrder.length; i++) {
        s = selftestStats[selftestOrder[i]]
        if (s.count > 0) fired++
        failed += s.failed
    }
    selftestInfo("==== summary: " + fired + "/" + selftestOrder.length + " event types fired, " + failed + " failure(s) ====")
    for (i = 0; i < selftestOrder.length; i++) {
        s = selftestStats[selftestOrder[i]]
        selftestInfo("  " + selftestOrder[i] + ": count=" + s.count + " ok=" + s.ok + " failed=" + s.failed)
    }
    if (failed > 0) selftestError("selftest finished with failures")
}

TaCZClientEvents.gunIndexLoad(event => {
    selftestCheckGunEvent("gunIndexLoad", event, false)
})

TaCZClientEvents.playerAim(event => {
    selftestCheckGunEvent("playerAim", event, true)
})

TaCZClientEvents.playerShoot(event => {
    selftestCheckGunEvent("playerShoot", event, true)
})

TaCZClientEvents.playerMelee(event => {
    selftestCheckGunEvent("playerMelee", event, true)
})

TaCZClientEvents.playerReload(event => {
    selftestCheckGunEvent("playerReload", event, true)
})

ClientEvents.loggedIn(() => {
    selftestInfo("ready - aim / shoot / melee / reload with a gun to trigger client events")
})

// Print the running result every 60 seconds when something new happened
ClientEvents.tick(() => {
    selftestTicks++
    if (selftestTicks % 1200 === 0 && selftestDirty) {
        selftestDirty = false
        selftestSummary()
    }
})

ClientEvents.loggedOut(() => {
    if (selftestOrder.length > 0) selftestSummary()
})