<%--
  Created by IntelliJ IDEA.
  User: snake
  Date: 2017/7/27
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>修改景区信息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/js/plugins/layui/css/layui.css" media="all"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/js/plugins/font-awesome/css/font-awesome.min.css">

    <style type="text/css">
        input.layui-input.layui-unselect {width:190px}
        div.layui-unselect.layui-form-select.layui-form-selected{width: 190px;margin-left: 110px;}
        i.layui-edge{left: 1000px;}
    </style>
</head>

<body>
<div style="margin: 15px;">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>修改景区信息</legend>
    </fieldset>

    <form class="layui-form" id="form1" action="${pageContext.request.contextPath}/scenic/editScenicMenu.do">
        <input type="hidden" name="code" value="${sc.code}"> 　
        <input type="hidden" name="status" value="关闭" id="status1"> 　
        <div class="layui-form-item">
            <label class="layui-form-label">景区名称</label>
            <div class="layui-input-inline">
                <input type="text" name="scenicname" value="${sc.scenicname}" lay-verify="title" autocomplete="off"
                       placeholder="请输入景区名称"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">景区地址</label>
            <div class="layui-input-inline">
                <input type="text" name="address" value="${sc.address}" lay-verify="address" placeholder="请输入景区地址"
                       autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">联系电话</label>
            <div class="layui-input-inline">
                <input type="tel" name="telephone" value="${sc.telephone}" lay-verify="phone" placeholder="请输入联系电话"
                       autocomplete="off"
                       class="layui-input">
            </div>
        </div>

<%--        <div class="layui-form-item">--%>
<%--            <label class="layui-form-label">舒适度阈值</label>--%>
<%--            <div class="layui-input-inline">--%>
<%--                <input type="text" name="max_di" value="${sc.max_di}" lay-verify="di" placeholder="请输入最大阈值"--%>
<%--                       autocomplete="off"--%>
<%--                       class="layui-input">--%>
<%--            </div>--%>
<%--        </div>--%>

        <div class="layui-form-item">
            <label class="layui-form-label">景区类型</label>
            <select id="list" name="scenictype">
                <c:forEach items="${list}" var="item">
                    <option id="${item.id}" value="${item.id}" data-id="${item.id}">${item.scenictype_name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">开放状态</label>
            <div class="layui-input-inline">
                <input type="checkbox" lay-skin="switch" lay-filter="switchTest" lay-text="开放|关闭"
                        id="check1">
<%--                <c:if test="${sc.status == '开放'}">--%>
<%--                    <input type="checkbox" name="status" lay-skin="switch" lay-filter="switchTest" lay-text="开放|关闭"--%>
<%--                           value="开放11" id="check1">--%>
<%--                </c:if>--%>
<%--                <c:if test="${sc.status=='关闭'}">--%>
<%--                    <input type="checkbox" checked name="status" lay-skin="switch" lay-filter="switchTest" value="关闭11"--%>
<%--                           lay-text="开放|关闭" title="开关" id="check1">--%>
<%--                </c:if>--%>

            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">客流承载量</label>
            <div class="layui-input-inline">
                <input type="text" name="max_people" value="${sc.max_people}" lay-verify="number" placeholder="请输入最大承载量"
                       autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">车流承载量</label>
            <div class="layui-input-inline">
                <input type="text" name="max_car" value="${sc.max_car}" lay-verify="number" placeholder="请输入最大车流量"
                       autocomplete="off"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">团队票价</label>
            <div class="layui-input-inline">
                <input type="text" name="teamTickets" value="${sc.teamTickets}" lay-verify="required" placeholder="请输入团队票价"
                       autocomplete="off"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">散客票价</label>
            <div class="layui-input-inline">
                <input type="text" name="individualTickets" value="${sc.individualTickets}" lay-verify="required" placeholder="请输入散客票价"
                       autocomplete="off"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">电商票价</label>
            <div class="layui-input-inline">
                <input type="text" name="internetTickets" value="${sc.internetTickets}" lay-verify="required" placeholder="请输入电商票价"
                       autocomplete="off"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">停车费单价</label>
            <div class="layui-input-inline">
                <input type="text" name="parkingRate" value="${sc.parkingRate}" lay-verify="required" placeholder="请输入停车费单价"
                       autocomplete="off"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/layui/layui.js"></script>
<script>

    layui.use(['form', 'layedit', 'laydate', 'layer'], function () {
        var form = layui.form(),
            layer = layui.layer,
            layedit = layui.layedit,
            laydate = layui.laydate;
        var a;

        var $ = layui.jquery;
        //创建一个编辑器
        var editIndex = layedit.build('LAY_demo_editor');
        //自定义验证规则
        form.verify({
            title: function (value) {
                if (value.length < 1) {
                    return '景区名字不得为空!';
                }
            }, address: function (value) {
                if (value.length < 1) {
                    return '景区地址不得为空!';
                }
            }

        });

        //监听提交
        form.on('submit(demo1)', function (data) {
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭
        });

        form.on('switch(switchTest)', function (data) {
            // console.log(data.elem); //得到checkbox原始DOM对象
            // console.log(data.elem.checked); //开关是否开启，true或者false
            // console.log(data.value); //开关value值，也可以通过data.elem.value得到
            // console.log(data.othis); //得到美化后的DOM对象
            //alert(data.value);
            //alert(data.elem.checked);
            if(data.elem.checked){
                $("#check1").val('开放');
            }else{
                $("#check1").val('关闭');
            }
            // console.log($("#check1").val());
            //alert($("#check1").val());
            $('#status1').val($('#check1').val());

        });

        if ('${Msg}' != '') {
            layer.msg('${Msg}');
        }


    });
</script>
</body>

</html>