//noinspection ES6ConvertVarToLetConst,JSUnresolvedReference,DuplicatedCode,JSCheckFunctionSignatures

// TaCZ JS self-test — server scripts
// Covers every TaCZServerEvents event (data loading + shooter events) and provides the
// /taczjs_selftest command. Output is prefixed with "[TaCZJS Selftest]".
//
// var is used everywhere: KubeJS' Rhino rejects repeated const/let declarations in named
// functions ("redeclaration of var").

var SELFTEST_TAG = "[TaCZJS Selftest][server]"
var SELFTEST_LOG_LIMIT = 3
var SELFTEST_VERIFY_SET_JSON = true

var selftestStats = {}
var selftestOrder = []
var selftestGunIds = []
var selftestAmmoIds = []
var selftestAttachmentIds = []

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
        return String(event.getId())
    } catch (e) {
        return "<unknown>"
    }
}

// Print to the log and, when run by a player, to chat
function selftestOutput(source, msg) {
    selftestInfo(msg)
    if (!source) return
    try {
        if (source.player) source.player.sendSystemMessage(Text.of(msg))
    } catch (e) {
        // ignored: no chat context, the log already has the result
    }
}

function selftestCheckLoad(name, event, extra) {
    var s = selftestStat(name)
    s.count++
    try {
        var id = event.getId()
        selftestAssert(id != null, "getId() returned null")
        var json = event.getJson()
        selftestAssert(json != null, "getJson() returned null")
        var parsed = JSON.parse(json)
        selftestAssert(parsed != null, "JSON.parse(getJson()) returned null")
        selftestAssert(event.getStdJson() != null, "getStdJson() returned null")
        if (extra) extra(event, parsed)
        s.ok++
        if (s.sample == null) s.sample = String(id)
        if (s.logged < SELFTEST_LOG_LIMIT) {
            s.logged++
            selftestInfo("OK " + name + " " + String(id))
        }
        if (SELFTEST_VERIFY_SET_JSON && s.count === 1) {
            event.setJson(json)
            selftestInfo("OK " + name + " setJson() round-trip")
        }
    } catch (e) {
        s.failed++
        selftestError(name + " FAILED at " + selftestId(event) + ": " + e)
    }
}

function selftestCheckShooter(name, event) {
    var s = selftestStat(name)
    s.count++
    try {
        selftestAssert(event.getEntity() != null, "getEntity() returned null")
        selftestAssert(event.getShooter() != null, "getShooter() returned null")
        var gunId = event.getGunId()
        selftestAssert(gunId != null, "getGunId() returned null")
        selftestAssert(event.getGunItem() != null, "getGunItem() returned null")
        s.ok++
        if (s.logged < SELFTEST_LOG_LIMIT) {
            s.logged++
            selftestInfo("OK " + name + " gun=" + String(gunId))
        }
    } catch (e) {
        s.failed++
        selftestError(name + " FAILED: " + e)
    }
}

function selftestSummary(source) {
    var fired = 0
    var failed = 0
    var i
    var s
    for (i = 0; i < selftestOrder.length; i++) {
        s = selftestStats[selftestOrder[i]]
        if (s.count > 0) fired++
        failed += s.failed
    }
    selftestOutput(source, "==== summary: " + fired + "/" + selftestOrder.length + " event types fired, " + failed + " failure(s) ====")
    for (i = 0; i < selftestOrder.length; i++) {
        s = selftestStats[selftestOrder[i]]
        selftestOutput(source, "  " + selftestOrder[i] + ": count=" + s.count + " ok=" + s.ok + " failed=" + s.failed)
    }
    if (failed > 0) selftestError("selftest finished with failures")
}

// ---- data loading events ----

TaCZServerEvents.gunIndexLoad(event => {
    selftestCheckLoad("gunIndexLoad", event, ev => {
        selftestAssert(ev.getPOJO() != null, "getPOJO() returned null")
        selftestAssert(typeof ev.removeGun === "function", "removeGun() is missing")
    })
    if (selftestGunIds.length < 8) selftestGunIds.push(String(event.getId()))
})

TaCZServerEvents.ammoIndexLoad(event => {
    selftestCheckLoad("ammoIndexLoad", event, ev => {
        selftestAssert(ev.getPOJO() != null, "getPOJO() returned null")
        selftestAssert(typeof ev.removeAmmo === "function", "removeAmmo() is missing")
    })
    if (selftestAmmoIds.length < 8) selftestAmmoIds.push(String(event.getId()))
})

TaCZServerEvents.attachmentIndexLoad(event => {
    selftestCheckLoad("attachmentIndexLoad", event, ev => {
        selftestAssert(ev.getPOJO() != null, "getPOJO() returned null")
        selftestAssert(typeof ev.removeAttachment === "function", "removeAttachment() is missing")
    })
    if (selftestAttachmentIds.length < 8) selftestAttachmentIds.push(String(event.getId()))
})

TaCZServerEvents.gunDataLoad(event => {
    selftestCheckLoad("gunDataLoad", event, ev => {
        selftestAssert(ev.getGunData() != null, "getGunData() returned null")
        selftestAssert(typeof ev.removeGunData === "function", "removeGunData() is missing")
    })
})

TaCZServerEvents.attachmentDataLoad(event => {
    selftestCheckLoad("attachmentDataLoad", event, ev => {
        selftestAssert(ev.getAttachmentData() != null, "getAttachmentData() returned null")
        selftestAssert(typeof ev.removeAttachmentData === "function", "removeAttachmentData() is missing")
    })
})

TaCZServerEvents.attachmentTagsLoad(event => {
    selftestCheckLoad("attachmentTagsLoad", event, ev => {
        var tags = ev.getAttachmentTags()
        selftestAssert(tags != null, "getAttachmentTags() returned null")
        selftestAssert(typeof tags.length === "number", "getAttachmentTags() did not return an array")
        selftestAssert(typeof ev.removeAttachmentTags === "function", "removeAttachmentTags() is missing")
    })
})

// ---- shooter events (only fire when a gun is actually used) ----

TaCZServerEvents.entityShoot(event => selftestCheckShooter("entityShoot", event))
TaCZServerEvents.entityAim(event => selftestCheckShooter("entityAim", event))
TaCZServerEvents.entityMelee(event => selftestCheckShooter("entityMelee", event))
TaCZServerEvents.entityReload(event => selftestCheckShooter("entityReload", event))

// ---- summary and command ----

ServerEvents.loaded(() => {
    // Fabric reloads the server scripts when joining a world, which clears the counters.
    // Do not print a green-looking 0/0 summary in that case.
    if (selftestOrder.length > 0) {
        selftestSummary(null)
    } else {
        selftestInfo("server scripts were reloaded after data loading, counters are cleared; see latest.log, or run /reload and then /taczjs_selftest")
    }
})

ServerEvents.commandRegistry(event => {
    var Commands = event.commands
    event.register(
        Commands.literal("taczjs_selftest")
            .executes(ctx => {
                selftestSummary(ctx.source)
                selftestCheckUtils(ctx.source)
                return 1
            })
    )
})

function selftestExpect(source, name, fn, detail) {
    try {
        var result = fn()
        selftestOutput(source, "  " + name + (detail ? "(" + detail + ")" : "") + " -> " + result)
    } catch (e) {
        selftestError(name + " FAILED: " + e)
        selftestOutput(source, "  " + name + " -> FAILED: " + e)
    }
}

function selftestCheckUtils(source) {
    var gunId = selftestGunIds.length > 0 ? selftestGunIds[0] : "tacz:p90"
    var ammoId = selftestAmmoIds.length > 0 ? selftestAmmoIds[0] : "tacz:762x54"
    var attachmentId = selftestAttachmentIds.length > 0 ? selftestAttachmentIds[0] : "tacz:sniper_extended_mag_3"

    selftestOutput(source, "---- TaCZJSUtils ----")
    selftestExpect(source, "getGunIndex", () => TaCZJSUtils.getGunIndex(new ResourceLocation(gunId)) != null, gunId)
    selftestExpect(source, "getAmmoIndex", () => TaCZJSUtils.getAmmoIndex(new ResourceLocation(ammoId)) != null, ammoId)
    selftestExpect(source, "getAttachmentIndex", () => TaCZJSUtils.getAttachmentIndex(new ResourceLocation(attachmentId)) != null, attachmentId)
    if (typeof TaCZJSUtils.getRecipe !== "function") {
        selftestOutput(source, "  getRecipe -> SKIP (declared in types/TaCZJSUtils.d.ts but not implemented)")
    } else {
        selftestExpect(source, "getRecipe", () => TaCZJSUtils.getRecipe(new ResourceLocation("tacz:gun/p90")) != null, "tacz:gun/p90")
    }
    if (source && source.player) {
        selftestExpect(source, "mainHandHoldGun", () => TaCZJSUtils.mainHandHoldGun(source.player), null)
    }
}