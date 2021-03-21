<%--
  Created by IntelliJ IDEA.
  User: snake
  Date: 2021/03/04
  Time: 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/js/plugins/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css" media="all">
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/js/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/table.css"/>
<link href="${pageContext.request.contextPath}/assets/css/jquery.searchableSelect.css" rel="stylesheet" type="text/css">

<div class="admin-main">
    <div class="layui-tab layui-tab-brief" >
        <ul class="layui-tab-title">
            <li class="layui-this" onclick="TestHLC(this)">实时客流量</li>
            <li onclick="TestRETP(this)">入园人数</li>
        </ul>

        <div class="layui-tab-content">

            <%-- 选项卡1--%>
            <div class="layui-tab-item layui-show">
                <blockquote class="layui-elem-quote" style="padding-bottom: 1px;margin-bottom: 15px;">
                    <div class="layui-form-pane">
                        <div class="layui-form-item">

                            <div class="layui-input-inline">
                                <select id="list" lay-filter="list" style="z-index: 100;">
                                    <c:forEach items="${list}" var="item">
                                        <option id="${item.code}" value="${item.code}"
                                                data-id="${item.code}">${item.scenicname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <span>&nbsp;&nbsp;</span>
                            <div class="layui-input-inline">
                                <input class="layui-input" placeholder="日期" id="LAY_demorange_s" style="height: 35px;margin-left: 20px;">
                            </div>
                            <div class="layui-form-item" style="position:relative;display: inline-block;line-height: 2.7;margin-left: 20px;margin-bottom: 0px;margin-top: 3px;">
                                <a href="javascript:;" class="layui-btn layui-btn-small" id="search">
                                    <i class="layui-icon">&#xe615;</i> 搜索景区
                                </a>
                            </div>
                        </div>
                    </div>
                </blockquote>

                <div style="width:1627px;height:580px;" id="tab1"></div>
            </div>


           <%-- 选项卡2--%>
            <div class="layui-tab-item">
                <blockquote class="layui-elem-quote" style="padding-bottom: 1px;margin-bottom: 15px;">
                    <div class="layui-form-pane">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <select id="list1" style="z-index: 100;">
                                    <c:forEach items="${list}" var="item">
                                        <option id="${item.code}" value="${item.code}"
                                                data-id="${item.code}">${item.scenicname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <span>&nbsp;&nbsp;</span>
                            <select id="list2" style="z-index: 100;">
                                <option value="1" selected>最近7天入园人数</option>
                                <option value="2">最近15天入园人数</option>
<%--                                <option value="3">最近30天入园人数</option>--%>
                            </select>
                            <div class="layui-form-item" style="position:relative;display: inline-block;line-height: 2.7;margin-left: 20px;margin-bottom: 0px;margin-top: 3px;">
                                <a href="javascript:;" class="layui-btn layui-btn-small" id="search1">
                                    <i class="layui-icon">&#xe615;</i> 搜索景区
                                </a>
                            </div>
                        </div>
                    </div>
                </blockquote>

                <div style="width:1627px;height:580px;" id="tab2"></div>
            </div>

        </div>
    </div>

</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.searchableSelect.js"></script>

<script>
    layui.config({
        base: "../assets/js/modules/"
    });
    layui.use(['paging', 'form', 'element'], function () {
        var $ = layui.jquery

        // 初始化
        TestHLC();
    });
</script>
<script>
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        var a1;
        $('#LAY_demorange_s').val('2017-08-05');

        var start = {
            min: '2010-01-01',
            format: 'YYYY-MM-DD' //日期格式
            , max: laydate.now()
            , istoday: true
            , choose: function (datas) {
                a1 = datas;
            }
        };

        document.getElementById('LAY_demorange_s').onclick = function () {
            start.elem = this;
            laydate(start);
        }

        // 搜索
        $('#search').on('click', function () {
            TestHLC();
        });

        // 搜索1
        $('#search1').on('click', function () {
            TestRETP();
        });


        // 定时器，实现实时刷新功能，10s一次
        setInterval(function () {
            TestHLC();
        }, (10000));// 设置定时器时间  单位毫秒

    });
</script>


<%--<script>--%>
<%--    function TestDI(e) {--%>
<%--        layui.use('layer', function () {--%>
<%--            var $ = layui.jquery,--%>
<%--                layer = layui.layer;--%>

<%--            var enter_time = e.getAttribute('data-id');--%>
<%--            var scenic_id = e.getAttribute('data-name');--%>
<%--            var scenic_name = e.getAttribute('data-title');--%>

<%--            layer.open({--%>
<%--                title: '天气舒适指数',--%>
<%--                maxmin: true,--%>
<%--                type: 2,--%>
<%--                content: '${pageContext.request.contextPath}/Data/getDITable.do?enter_time=' + enter_time + "&&scenic_id=" + scenic_id + "&&scenic_name=" + scenic_name,--%>
<%--                area: ['100%', '100%'],--%>
<%--                resize: 'true',--%>
<%--                moveOut: 'true',--%>
<%--                success: function (layero, index) {--%>
<%--                    $('.admin-table-page').hide();--%>
<%--                },--%>
<%--                cancel: function (index, layero) {--%>
<%--                    $('.admin-table-page').show();--%>
<%--                }--%>
<%--            });--%>

<%--        })--%>
<%--    }--%>
<%--</script>--%>




<script>
    <%-- 实时客流承载量 --%>
    function TestHLC() {
        layui.use('layer', function () {
            var $ = layui.jquery;
            var enter_time = $('#LAY_demorange_s').val();
            var scenic_id = $('#list').val();
            var scenic_name = $('#list').find("option:selected").text();
            $('#tab1').load('${pageContext.request.contextPath}/Data/getHLCTable.do?enter_time=' + enter_time + "&&scenic_id=" + scenic_id + "&&scenic_name=" + scenic_name);
        })
    }

    <%-- 入园人数 --%>
    function TestRETP() {
        layui.use('layer', function () {
            var $ = layui.jquery;
            var scenic_id = $('#list1').val();
            var scenic_name = $('#list1').find("option:selected").text();
            var type = $('#list2').val();
            $('#tab2').load('${pageContext.request.contextPath}/Data/getRETPTable.do?type=' + type + "&&scenic_id=" + scenic_id + "&&scenic_name=" + scenic_name);
        })
    }
</script>



<%--<script>--%>
<%--    function TestVLC(e) {--%>
<%--        layui.use('layer', function () {--%>
<%--            var $ = layui.jquery,--%>
<%--                layer = layui.layer;--%>

<%--            var enter_time = e.getAttribute('data-id');--%>
<%--            var scenic_id = e.getAttribute('data-name');--%>

<%--            layer.open({--%>
<%--                title: '客流承载量',--%>
<%--                maxmin: true,--%>
<%--                type: 2,--%>
<%--                content: '${pageContext.request.contextPath}/DataTwo/getVLCTable.do?enter_time=' + enter_time + "&&scenic_id=" + scenic_id,--%>
<%--                area: ['100%', '100%'],--%>
<%--                resize: 'true',--%>
<%--                moveOut: 'true',--%>
<%--                success: function (layero, index) {--%>
<%--                    // console.log(layero, index);--%>
<%--                    $('.admin-table-page').hide();--%>
<%--                },--%>
<%--                cancel: function (index, layero) {--%>
<%--                    $('.admin-table-page').show();--%>
<%--                }--%>
<%--            });--%>
<%--        })--%>
<%--    }--%>
<%--</script>--%>
<%--<script>--%>
<%--    function TestCD(e) {--%>
<%--        layui.use('layer', function () {--%>
<%--            var $ = layui.jquery,--%>
<%--                layer = layui.layer;--%>

<%--            var enter_time = e.getAttribute('data-id');--%>
<%--            var scenic_id = e.getAttribute('data-name');--%>

<%--            layer.open({--%>
<%--                title: '总体舒适度',--%>
<%--                maxmin: true,--%>
<%--                type: 2,--%>
<%--                content: '${pageContext.request.contextPath}/DataTwo/getCDTable.do?enter_time=' + enter_time + "&&scenic_id=" + scenic_id,--%>
<%--                area: ['100%', '100%'],--%>
<%--                resize: 'true',--%>
<%--                moveOut: 'true',--%>
<%--                success: function (layero, index) {--%>
<%--                    // console.log(layero, index);--%>
<%--                    $('.admin-table-page').hide();--%>
<%--                },--%>
<%--                cancel: function (index, layero) {--%>
<%--                    $('.admin-table-page').show();--%>
<%--                }--%>
<%--            });--%>

<%--        })--%>

<%--    }--%>
<%--</script>--%>

<script>
    $(function () {
        $('select').searchableSelect();
    });
</script>


