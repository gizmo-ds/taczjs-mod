# TaCZ JS Examples

TaCZ 使用 GSON 作为 JSON 解析器, GSON 似乎支持注释, JavaScript 的 JSON.parse 不支持注释, 为了兼容你可以使用下面的方法让
JavaScript 的 JSON.parse 支持带注释的 JSON.

```javascript
function removeComments(str) {
    str = str.replace(/\/\/.*$/gm, "") // 单行注释
    str = str.replace(/\/\*[\s\S]*?\*\//g, "") // 多行注释
    return str
}

const originalJSONParse = JSON.parse
JSON.parse = function (str, reviver) {
    return originalJSONParse(removeComments(str), reviver)
}
```
