
<%--
  Created by IntelliJ IDEA.
  User: 123
  Date: 2021/03/04
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/js/plugins/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css" media="all">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/js/plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/table.css"/>
    <link href="${pageContext.request.contextPath}/assets/css/jquery.searchableSelect.css" rel="stylesheet"
          type="text/css">
    <script src="${pageContext.request.contextPath}/assets/js/plugins/layui/lay/modules/laydate.js"></script>
</head>
<body>

<div class="admin-main">
    <blockquote class="layui-elem-quote">

        <div class="layui-input-block"
             style="display: inline-block; margin-left: 30px; min-height: inherit; vertical-align: bottom;">
            <input type="text" name="keyword" id="keyword" required lay-verify="keyword" class="layui-input"
                   autocomplete="off" placeholder="请输入搜索关键词" style="height: 40px; line-height: 30px;" value="">
        </div>
        <a href="javascript:;" class="layui-btn layui-btn-small" id="search" style="margin-left: 10px;">
            <i class="layui-icon">&#xe615;</i> 搜索
        </a>
<%--        <a href="javascript:;" class="layui-btn layui-btn-small" id="searchAll">--%>
<%--            <i class="layui-icon">&#xe615;</i> 查看全部--%>
<%--        </a>--%>
        <a href="javascript:;" class="layui-btn layui-btn-small" id="exportAll">
        <i class="layui-icon">&#xe61d;</i> 导出数据
        </a>
        <span>&nbsp;&nbsp;</span>
        <input type="file" name="file" lay-type="file" class="layui-upload-file" lay-title="导入文件" id="import" style="margin-left: 10px;">

    </blockquote>
    <fieldset class="layui-elem-field">
        <legend>${scenicname}-${time} &nbsp;&nbsp;总车辆：${number}辆</legend>
        <input type="hidden" name="enter_time" value="${time}"/>
        <input type="hidden" name="science_id" value="${science_id}"/>
        <div class="layui-field-box layui-form">
            <table class="layui-table admin-table">
                <thead>
                <tr>
                    <th>日期</th>
                    <th>车牌号</th>
                    <th>类型</th>
                    <th>停车位编号</th>
                    <th>进入时间</th>
                    <th>离开时间</th>
                </tr>
                </thead>
                <tbody id="content">

                </tbody>
            </table>
        </div>
    </fieldset>
    <div class="admin-table-page">
        <div id="paged" class="page">
        </div>
    </div>
</div>
<!--模板-->
<script type="text/html" id="tpl">
    {{# layui.each(d.list, function(index, item){ }}
    <tr>
        <td>{{ item.day }}</td>
        <td>{{ item.license_car }}</td>
        <td>{{# if(item.car_type == 1){ }}
            临停车
            {{# } }}
            {{# if(item.car_type == 2){ }}
            月租车
            {{# } }}
            {{# if(item.car_type == 3){ }}
            员工车
            {{# } }}
            {{# if(item.car_type == 4){ }}
            免费车
            {{# } }}
        </td>
        <td>{{item.park_id}}</td>
        <td>{{ item.enter_time }}</td>
        <td>{{ item.leave_time }}</td>
    </tr>
    {{# }); }}
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.searchableSelect.js"></script>
<script>
    layui.config({
        base: "../assets/js/modules/"
    });
    layui.use(['paging', 'form'], function () {
        var $ = layui.jquery,
            paging = layui.paging(),
            layerTips = parent.layer === undefined ? layui.layer : parent.layer, //获取父窗口的layer对象
            layer = layui.layer, //获取当前窗口的layer对象
            form = layui.form();
        var science_id = $(" input[ name='science_id' ] ").val();
        var day = $(" input[ name='enter_time' ] ").val();

        paging.init({
            openWait: true,
            url: '${pageContext.request.contextPath}/car/getCarPage.do', //地址
            //url:'../assets/tsconfig.json',
            elem: '#content', //内容容器
            params: { //发送到服务端的参数
                science_id: science_id,
                day: day
            },
            type: 'GET',
            tempElem: '#tpl', //模块容器
            pageConfig: { //分页参数配置
                elem: '#paged', //分页容器
                pageSize: 10 //分页大小
            },
            success: function () { //渲染成功的回调
                // alert('渲染成功');
            },
            fail: function (msg) { //获取数据失败的回调
                //alert('获取数据失败')
            },
            complate: function () { //完成的回调
                //alert('处理完成');
                //重新渲染复选框
                form.render('checkbox');
                form.on('checkbox(allselector)', function (data) {
                    var elem = data.elem;

                    $('#content').children('tr').each(function () {
                        var $that = $(this);
                        //全选或反选
                        $that.children('td').eq(0).children('input[type=checkbox]')[0].checked = elem.checked;
                        form.render('checkbox');
                    });
                });

            }
        });
        $('#search').on('click', function () {
            layui.use(['paging', 'form'], function () {
                var $ = layui.jquery,
                    paging = layui.paging(),
                    layerTips = parent.layer === undefined ? layui.layer : parent.layer, //获取父窗口的layer对象
                    layer = layui.layer, //获取当前窗口的layer对象
                    form = layui.form();

                var keyword = $('#keyword').val();
                var code = $(" input[ name='science_id' ] ").val();
                var time = $(" input[ name='enter_time' ] ").val();
                paging.init({
                    openWait: true,
                    url: '${pageContext.request.contextPath}/car/findCar.do', //地址
                    elem: '#content', //内容容器
                    params: { //发送到服务端的参数
                        keyword: keyword,
                        day: time,
                        code: code
                    },
                    type: 'GET',
                    tempElem: '#tpl', //模块容器
                    pageConfig: { //分页参数配置
                        elem: '#paged', //分页容器
                        pageSize: 10 //分页大小
                    },
                    success: function () { //渲染成功的回调
                        // alert('渲染成功');
                    },
                    fail: function (msg) { //获取数据失败的回调
                        //alert('获取数据失败')
                    },
                    complate: function () { //完成的回调
                        //alert('处理完成');
                        //重新渲染复选框
                        form.render('checkbox');
                        form.on('checkbox(allselector)', function (data) {
                            var elem = data.elem;

                            $('#content').children('tr').each(function () {
                                var $that = $(this);
                                //全选或反选
                                $that.children('td').eq(0).children('input[type=checkbox]')[0].checked = elem.checked;
                                form.render('checkbox');
                            });
                        });

                    }
                });
            });
        });

        $('#exportAll').on('click', function () {
            location.href = "${pageContext.request.contextPath}/FileTwo/exportCarExcelAll.do";
        })

    });
</script>
<script>
    function info(e) {
        layui.use('layer', function () {
            var $ = layui.jquery,
                layer = layui.layer;
            var code = e.getAttribute("data-id");
            var time = e.getAttribute("data-time");
            var name = e.getAttribute("data-name");
            layer.open({
                title: '修改',
                maxmin: true,
                type: 2,
                content: "${pageContext.request.contextPath}/car/getCarInfoByCodeAndTime.do?code=" + code + "&&enter_time=" + time + "&&name=" + name,
                area: ['50%', '50%'],
                resize: 'true',
                moveOut: 'true',
                success: function (layero, index) {
                    console.log(layero, index);
                    $('.admin-table-page').hide();
                },
                cancel: function (index, layero) {
                    $('.admin-table-page').show();
                    $('#search').click();
                }
            });

        })
    }
</script>
<script>
    layui.use('upload', function () {
        layer = layui.layer;
        var science_id = $(" input[ name='science_id' ] ").val();
        console.log(science_id);
        layui.upload({
            url: '${pageContext.request.contextPath}/FileTwo/importfileCar.do?science_id='+science_id
            , elem: '#import' //指定原始元素，默认直接查找class="layui-upload-file"
            , method: 'post' //上传接口的http类型
            , success: function (res) {
                $('#search').click();
                layer.msg(res);
            }
        });
    });
</script>
</body>
</html>