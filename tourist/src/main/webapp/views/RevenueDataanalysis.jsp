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

            </div>
            <div style="width: 1638px;height: 645px;">
                <div style="width: 35%;height: 100%;display: inline-block;float: left;">
                    <div style="width: 100%;height: 25%;font-size: 17px;font-weight: 700;margin-top: 20px; margin-left: 25px;">
                        <p id="nowTime" style="font-size: 20px;"></p>

                        <div style="margin-top: 25px;">
                            <p style="display: inline-block;">当前售票数：</p>
                            <p style="display: inline-block;font-size: 21px;color: red;" id="touristCount"></p>&nbsp;&nbsp;人/次
                        </div>
                        <div style="margin-top: 15px;">
                            <p style="display: inline-block;">当前车流量：</p>
                            <p style="display: inline-block;font-size: 21px;color: red;" id="carCount"></p>&nbsp;&nbsp;辆/次
                        </div>
                        <div style="margin-top: 15px;">
                            <p style="display: inline-block;">今日销售额：</p>
                            <p style="display: inline-block;font-size: 21px;color: red;" id="total"></p>&nbsp;&nbsp;元
                        </div>
                    </div>
                    <!--  饼状图 -->
                    <div style="width: 100%;height: 75%;" id="revePie"></div>
                </div>
                <!-- 折线图 -->
                <div style="width: 65%;height: 100%;display: inline-block;">
                    <p style="font-size: 18px;font-weight: 700;text-align: center;">近7天销售额统计</p>
                    <div style="width: 100%;height: 98%;" id="ReveLine"></div>
                </div>
            </div>

        </div>
    </div>

</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.searchableSelect.js"></script>
<script src="${pageContext.request.contextPath}/dist/js/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/dist/js/vintage.js"></script>

<script>
    layui.config({
        base: "../assets/js/modules/"
    });
    layui.use(['paging', 'form', 'element'], function () {
        var $ = layui.jquery

        // 初始化
        getREVE();


    });
</script>
<script>
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        var a1;
        $('#LAY_demorange_s').val('2017-07-23');

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
            getREVE();
        });

        // // 定时器，实现实时刷新功能，10s一次
        // setInterval(function () {
        //     TestHLC();
        // }, (10000));// 设置定时器时间  单位毫秒

    });
</script>

<script>
    $(function () {
        $('select').searchableSelect();
    });
</script>

<script>
    <%-- 今日收入 --%>
    function getREVE() {
        var enter_day = $('#LAY_demorange_s').val();// 检索日期
        var scenic_id = $('#list').val();// 景区ID

        // 收入来源饼状图
        var myChart = echarts.init(document.getElementById('revePie'));
        var option = {
            series : [
                {
                    name: '收入来源',
                    type: 'pie',
                    radius: '55%',
                    data:[],
                    roseType: 'angle',
                    itemStyle: {
                        normal: {
                            // 显示阴影
                            shadowBlur: 200,
                            shadowColor: 'rgba(0, 0, 0, 0.5)',
                            // 显示百分比
                            label:{
                                show: true,
                                formatter: '{b} : {c} ({d}%)'
                            },
                            labelLine :{show:true}
                        }
                    }
                }
            ]
        };

        // 近7天收入折线图
        var myChart1 = echarts.init(document.getElementById('ReveLine'));
        var option1 = {
            tooltip: {},
            legend: {
                data:['']
            },
            xAxis: {
                axisLabel:{interval:0},
                data: []
            },
            yAxis: [{
                position:'right',
                type: 'value',
                scale: true,
                name: '销售额',
                max: 10000,// y轴最大值
                min: 0,  // y轴最小值
                boundaryGap: [0.2, 0.2]
            }],
            series: [{
                name: '销售额',
                type: 'line',
                data: []
            }]

        };


        // 获取数据
        $.ajax({
            type: "post",		//post请求方式
            async: true,		//异步请求
            url: "${pageContext.request.contextPath}/Data/getRevenue.do",
            data: {enter_day: enter_day, scenic_id: scenic_id},
            dataType: "json",		//返回数据形式为json
            success: function (result) {
                // 今日数据赋值
                $('#touristCount').text(result.touristCount);
                $('#carCount').text(result.carCount);
                $('#total').text(result.total);

                $('#nowTime').text($('#LAY_demorange_s').val() + '  '+$('#list').find("option:selected").text() + '景区  收入数据分析');


                // 饼状图赋值
                option.series[0].data[0]  = {value:result.typeList[0], name:'团队票'};// 团队销售额
                option.series[0].data[1]  = {value:result.typeList[1], name:'散客票'};// 散客销售额
                option.series[0].data[2]  = {value:result.typeList[2], name:'电商票'};// 电商销售额
                option.series[0].data[3]  = {value:result.typeList[3], name:'停车费'};// 停车费销售额
                myChart.setOption(option);

                // 折线图赋值
                option1.xAxis.data = result.dayList;// 日期
                option1.series[0].data = result.totalList;// 销售额
                myChart1.setOption(option1);
            },
            error: function () {
                //返回的数据为空时显示提示信息
                layer.msg('图表请求数据为空，可能服务器暂未录入观测数据，您可以稍后再试！');
                myChart.hideLoading();
            }
        });
    }
</script>



