//noinspection ES6ConvertVarToLetConst,JSUnresolvedReference,DuplicatedCode

// TaCZ JS self-test — startup scripts
// Covers every TaCZStartupEvents event. Output is prefixed with "[TaCZJS Selftest]".
//
// The first sample of each data event calls setJson() with its original JSON to verify the
// write path. The content is identical, so game data is not changed.
//
// var is used everywhere: KubeJS' Rhino rejects repeated const/let declarations in named
// functions ("redeclaration of var").

var SELFTEST_TAG = "[TaCZJS Selftest][startup]"
var SELFTEST_LOG_LIMIT = 3
var SELFTEST_VERIFY_SET_JSON = true

var selftestStats = {}
var selftestOrder = []

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

function selftestSummary(stage) {
    var fired = 0
    var failed = 0
    var i
    var s
    for (i = 0; i < selftestOrder.length; i++) {
        s = selftestStats[selftestOrder[i]]
        if (s.count > 0) fired++
        failed += s.failed
    }
    selftestInfo("==== " + stage + " summary: " + fired + "/" + selftestOrder.length + " event types fired so far, " + failed + " failure(s) ====")
    for (i = 0; i < selftestOrder.length; i++) {
        s = selftestStats[selftestOrder[i]]
        selftestInfo("  " + selftestOrder[i] + ": count=" + s.count + " ok=" + s.ok + " failed=" + s.failed)
    }
    if (failed > 0) selftestError("selftest finished with failures")
    if (stage === "recipe") selftestInfo("data events fire after recipes; see the ServerEvents.loaded summary in server.log")
}

TaCZStartupEvents.gunIndexLoad(event => {
    selftestCheckLoad("gunIndexLoad", event, ev => {
        selftestAssert(ev.getPOJO() != null, "getPOJO() returned null")
        selftestAssert(typeof ev.removeGun === "function", "removeGun() is missing")
    })
})

TaCZStartupEvents.ammoIndexLoad(event => {
    selftestCheckLoad("ammoIndexLoad", event, ev => {
        selftestAssert(ev.getPOJO() != null, "getPOJO() returned null")
        selftestAssert(typeof ev.removeAmmo === "function", "removeAmmo() is missing")
    })
})

TaCZStartupEvents.attachmentIndexLoad(event => {
    selftestCheckLoad("attachmentIndexLoad", event, ev => {
        selftestAssert(ev.getPOJO() != null, "getPOJO() returned null")
        selftestAssert(typeof ev.removeAttachment === "function", "removeAttachment() is missing")
    })
})

TaCZStartupEvents.gunDataLoad(event => {
    selftestCheckLoad("gunDataLoad", event, ev => {
        selftestAssert(ev.getGunData() != null, "getGunData() returned null")
        selftestAssert(typeof ev.removeGunData === "function", "removeGunData() is missing")
    })
})

TaCZStartupEvents.attachmentDataLoad(event => {
    selftestCheckLoad("attachmentDataLoad", event, ev => {
        selftestAssert(ev.getAttachmentData() != null, "getAttachmentData() returned null")
        selftestAssert(typeof ev.removeAttachmentData === "function", "removeAttachmentData() is missing")
    })
})

TaCZStartupEvents.attachmentTagsLoad(event => {
    selftestCheckLoad("attachmentTagsLoad", event, ev => {
        var tags = ev.getAttachmentTags()
        selftestAssert(tags != null, "getAttachmentTags() returned null")
        selftestAssert(typeof tags.length === "number", "getAttachmentTags() did not return an array")
        selftestAssert(typeof ev.removeAttachmentTags === "function", "removeAttachmentTags() is missing")
    })
})

TaCZStartupEvents.recipeLoadBegin(event => {
    var s = selftestStat("recipeLoadBegin")
    s.count++
    try {
        selftestAssert(typeof event.putRecipe === "function", "putRecipe() is missing")
        selftestAssert(typeof event.addRecipe === "function", "addRecipe() is missing")
        selftestAssert(typeof event.removeAllRecipes === "function", "removeAllRecipes() is missing")
        s.ok++
        selftestInfo("OK recipeLoadBegin")
    } catch (e) {
        s.failed++
        selftestError("recipeLoadBegin FAILED: " + e)
    }
})

TaCZStartupEvents.recipeLoad(event => {
    selftestCheckLoad("recipeLoad", event, ev => {
        selftestAssert(typeof ev.removeRecipe === "function", "removeRecipe() is missing")
    })
})

TaCZStartupEvents.recipeLoadEnd(event => {
    var s = selftestStat("recipeLoadEnd")
    s.count++
    try {
        selftestAssert(typeof event.putRecipe === "function", "putRecipe() is missing")
        selftestAssert(typeof event.removeAllRecipes === "function", "removeAllRecipes() is missing")
        s.ok++
        selftestInfo("OK recipeLoadEnd")
    } catch (e) {
        s.failed++
        selftestError("recipeLoadEnd FAILED: " + e)
    }
    selftestSummary("recipe")
})