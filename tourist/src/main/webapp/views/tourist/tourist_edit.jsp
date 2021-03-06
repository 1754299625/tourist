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
    <title>修改游客信息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/js/plugins/layui/css/layui.css" media="all"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/js/plugins/font-awesome/css/font-awesome.min.css">
    <link href="${pageContext.request.contextPath}/assets/css/jquery.searchableSelect.css" rel="stylesheet"
          type="text/css">

</head>

<body>
<div style="margin: 15px;">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>修改游客信息</legend>
    </fieldset>

    <form class="layui-form" id="form1" action="">

<%--        <div class="layui-form-item">--%>
<%--            <label class="layui-form-label">景区名称</label>--%>
<%--            <div class="layui-input-inline">--%>
<%--                <input type="text" name="scenicname" value="${sc.scenicname}" lay-verify="title" autocomplete="off"--%>
<%--                       placeholder="请输入景区名称"--%>
<%--                       class="layui-input">--%>
<%--            </div>--%>
<%--        </div>--%>



        <div class="layui-form-item">
            <label class="layui-form-label">年龄</label>
            <div class="layui-input-block">
                <input type="text" style="width: 190px;"
                       id="age" lay-verify="required" placeholder="请输入年龄" autocomplete="off" value="${tourist.age}"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">性别</label>
            <div class="layui-input-block">
                <c:if test="${tourist.sex == 0}">
                    <input type="radio" name="sex" value="0" title="男" checked="">
                    <input type="radio" name="sex" value="1" title="女">
                </c:if>
                <c:if test="${tourist.sex == 1}">
                    <input type="radio" name="sex" value="0" title="男">
                    <input type="radio" name="sex" value="1" title="女" checked="">
                </c:if>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">所属地区</label>
            <div class="layui-input-block">
                <input type="text" style="width: 190px;"
                       id="regione" lay-verify="required" placeholder="请输入所属地区" autocomplete="off" value="${tourist.regione}"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">进入时间</label>
            <div class="layui-input-inline">
                <input class="layui-input" placeholder="进入时间" value="${enter_time}"
                       id="LAY_demorange_s">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">离开时间</label>
            <div class="layui-input-inline">
                <input class="layui-input" placeholder="截止时间" value="${leave_time}"
                       id="LAY_demorange_e">
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block" style="margin-left: 0px;">
                <label class="layui-form-label" style="left: 0">游客类型</label>
                <div class="layui-input-inline">
                    <select id="list" name="tourist_type">
                        <c:if test="${tourist.tourist_type == 1}">
                            <option id="1" value="1" data-id="1" selected="selected">散客</option>
                            <option id='2' value="2" data-id="2">团体</option>
                            <option id='3' value="4" data-id="3">电商</option>
                        </c:if>
                        <c:if test="${tourist.tourist_type == 2}">
                            <option id="1" value="1" data-id="1">散客</option>
                            <option id='2' value="2" data-id="2" selected="selected">团体</option>
                            <option id='3' value="4" data-id="3">电商</option>
                        </c:if>
                        <c:if test="${tourist.tourist_type == 3}">
                            <option id="1" value="1" data-id="1">散客</option>
                            <option id='2' value="2" data-id="2">团体</option>
                            <option id='3' value="3" data-id="3" selected="selected">电商</option>
                        </c:if>
                    </select>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" id="sub">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.searchableSelect.js"></script>
<script>
    layui.use(['form', 'layedit', 'laydate', 'layer'], function () {
        var form = layui.form(),
            layer = layui.layer,
            layedit = layui.layedit,
            laydate = layui.laydate;
        var $ = layui.jquery;
        //创建一个编辑器
        var editIndex = layedit.build('LAY_demo_editor');
        //自定义验证规则

        if ('${Msg}' != '') {
            layer.msg('${Msg}');
            //alert('${Msg}');
        }
    });
</script>


<script>
    layui.use('laydate', function () {
        //alert('${leave_time}');
        //alert('${enter_time}');
        var laydate = layui.laydate;
        <%--var b1 = '${leave_time}';--%>
        <%--var b2 = '${enter_time}';--%>
        var a1 = '${enter_time}', a2 = '${enter_time}';
        var start = {
            format: 'YYYY-MM-DD hh:mm:ss',    // 日期格式
            istime: false,                        // 是否开启时间选择
            isclear: true,                        // 是否显示清空
            istoday: true,                    // 是否显示今天
            issure: true,                        // 是否显示确认
            festival: true,                    // 是否显示节日
            min: '1900-01-01 00:00:00',            // 最小日期
            max: '2099-12-31 23:59:59',        // 最大日期
            start: '2014-6-15 23:00:00',            // 开始日期
            fixed: false,                        // 是否固定在可视区域
            zIndex: 99999999                // css z-index
            , choose: function (datas) {
                end.min = datas; //开始日选好后，重置结束日的最小日期
                end.start = datas //将结束日的初始值设定为开始日
                //alert(datas);
                a1 = datas;
            }
        };

        var end = {
            format: 'YYYY-MM-DD hh:mm:ss',    // 日期格式
            istime: false,                        // 是否开启时间选择
            isclear: true,                        // 是否显示清空
            istoday: true,                    // 是否显示今天
            issure: true,                        // 是否显示确认
            festival: true,                    // 是否显示节日
            min: '1900-01-01 00:00:00',            // 最小日期
            max: '2099-12-31 23:59:59',        // 最大日期
            start: '2014-6-15 23:00:00',            // 开始日期
            fixed: false,                        // 是否固定在可视区域
            zIndex: 99999999                // css z-index
            , choose: function (datas) {
                start.max = datas; //结束日选好后，重置开始日的最大日期
                a2 = datas;
                // alert(datas);
            }
        };

        document.getElementById('LAY_demorange_s').onclick = function () {
            start.elem = this;
            start.start = '2014-6-15 23:00:00';
            laydate(start);
        }

        document.getElementById('LAY_demorange_e').onclick = function () {
            end.elem = this;
            end.start = '2014-6-15 23:00:00';
            laydate(end);
        }

        $("#sub").on('click', function () {
            layui.use('layer', function () {
                var $ = layui.jquery,
                    layer = layui.layer;
                var tourist_type = document.getElementById("list").value;
                var tourist_code = '${tourist.tourist_code}';
                var sex = $('input[name="sex"]:checked').val();
                var age = document.getElementById("age").value;
                var regione = document.getElementById("regione").value;
                $.ajax({
                    type: "GET",
                    url: "${pageContext.request.contextPath}/people/updateByTouristCode.do",
                    data: {
                        tourist_code: tourist_code,
                        tourist_type: tourist_type,
                        sex: sex,
                        age: age,
                        regione: regione,
                        enter_time: a1,
                        leave_time: a2
                    },
                    dataType: "text",
                    success: function (data) {
                        layer.msg(data)
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭
                    },
                    fail: function (data){
                        layer.msg(data)
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭
                    }
                });

            });
        })
    });


</script>


</body>

</html>