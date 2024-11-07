// 这是一个移除"机动军械师"中 EMX 配件的例子
// "机动军械师"的下载地址: https://www.curseforge.com/minecraft/mc-mods/timeless-and-classics-zero

TaCZJSEvents.attachmentIndexLoad((event) => {
    switch (event.getId().toString()) {
        case "create_armorer:scope_pipe": // 流体管道瞄准镜
        case "create_armorer:muzzle_pipe": // 流体管道枪口
        case "create_armorer:grip_pipe": // 流体管道握把
            event.removeAttachment();
            break;
        default:
            break;
    }
});
