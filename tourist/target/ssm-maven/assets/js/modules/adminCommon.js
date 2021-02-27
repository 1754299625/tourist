layui.define(["layer", "element"], function (e) {
    var n = layui.jquery, t = layui.layer, a = layui.element();
    e("adminCommon", {
        homePageTpl: function () {
            n("#main").find(".layui-tab-item").eq(0).load("./dist/views/home.html?v=" + (new Date).getTime())
        }, reloadTpl: function (e, t, l, o) {
            n("#tpl").load(e, function (e) {
                a.tabAdd(t, {title: l, content: e, id: o}), a.tabChange(t, o)
            })
        }, reloadHistory: function (e) {
            history.replaceState(null, "", location.pathname + location.search), e && e()
        }, initUrlHash: function () {
            var e = location.hash, t = n('[data-hash="' + e.replace("#", "") + '"]');
            if (e && t.length > 0) {
                var a = t.data("url") + "?v=" + (new Date).getTime(), l = t.find("span").text(), o = t.data("id");
                adminCommon.reloadTpl(a, "tabMain", l, o)
            }
        }, initNavForTabUrlHash: function (e, l) {
            var o = e.data("url") + "?v=" + (new Date).getTime(), i = e.data("hash"), c = l.find("span").text(),
                u = l.attr("data-id"), m = n("#tabBody").children('li[lay-id="' + u + '"]').length;
            t.msg(c), m || adminCommon.reloadTpl(o, "tabMain", c, u), a.tabChange("tabMain", u), adminCommon.reloadHistory(function () {
                location.hash = i
            })
        }, setHashByLayId: function () {
            var e = n(".layui-tab-title").find(".layui-this").attr("lay-id"),
                t = n(".layui-nav-item").find('dd[data-id="' + e + '"]').data("hash");
            t ? location.hash = t : adminCommon.reloadHistory()
        }, toggleFullScreen: function () {
            document.fullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement ? document.cancelFullScreen ? document.cancelFullScreen() : document.mozCancelFullScreen ? document.mozCancelFullScreen() : document.webkitCancelFullScreen && document.webkitCancelFullScreen() : document.documentElement.requestFullscreen ? document.documentElement.requestFullscreen() : document.documentElement.mozRequestFullScreen ? document.documentElement.mozRequestFullScreen() : document.documentElement.webkitRequestFullscreen && document.documentElement.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT)
        }
    })
});