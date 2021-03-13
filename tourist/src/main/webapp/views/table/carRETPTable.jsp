<%--
  Created by IntelliJ IDEA.
  User: snake
  Date: 2021/03/04
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta charset="UTF-8">
<script src="${pageContext.request.contextPath}/dist/js/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/dist/js/vintage.js"></script>
<p style="text-align: center;font-size: 18px;font-weight: 700;margin-bottom: 10px;">
    ${scenic_name}景区-
    <c:if test="${type eq '1'}">
        最近7天入园车辆数
    </c:if>
    <c:if test="${type eq '2'}">
        最近15天入园车辆数
    </c:if>
    <c:if test="${type eq '3'}">
        最近30天入园车辆数
    </c:if>
</p>
<div id="main1" style="height: 100%;width: 100%;"></div>
<script type="text/javascript">

    layui.use(['jquery', 'layer', 'element'], function (args) {
        var $ = layui.jquery;
        var layer = layui.layer;

        var myChart = echarts.init(document.getElementById('main1'));
        var option = {
            title: {
                text: ''
            },
            tooltip: {},
            toolbox: {
                right: '0',
                bottom: '0',
                show: true,
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            },
            legend: {
                data:['']
            },
            xAxis: {
                axisLabel:{interval:0},
                data: []
            },
            yAxis: {
                type: 'value',
                scale: true,
                name: '最大承载量',
                max: ${max_car},// y轴最大值
                min: 0,  // y轴最小值
                boundaryGap: [0.2, 0.2]
                },
            series: [{
                name: '入园车辆数',
                type: 'line',
                data: []
            }]

        };

        var count = [];// 车辆数
        var dayDate = [];// 日期
        myChart.showLoading();	//数据加载完之前先显示一段简单的loading动画

        $.ajax({	//使用JQuery内置的Ajax方法
            type: "post",		//post请求方式
            async: true,		//异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url: "${pageContext.request.contextPath}/Data/getCarREPTInfor.do",	//请求发送到ShowInfoIndexServlet处
            data: {scenic_id: "${scenic_id}", type: "${type}"},		//请求内包含一个key为name，value为A0001的参数；服务器接收到客户端请求时通过request.getParameter方法获取该参数值
            dataType: "json",		//返回数据形式为json
            success: function (result) {
                //请求成功时执行该函数内容，result即为服务器返回的json对象
                if (result != null && result.length > 0) {
                    for (var i = 0; i < result.length; i++) {
                        count.push(result[i].hf);
                        dayDate.push(result[i].day);
                    }
                    myChart.hideLoading();	//隐藏加载动画
                    option.xAxis.data = dayDate;// 设置x轴数据1
                    option.series[0].data = count;// 设置y轴数据1
                    myChart.setOption(option);
                }
                else {
                    //返回的数据为空时显示提示信息
                    layer.msg('图表请求数据为空，可能服务器暂未录入观测数据，您可以稍后再试！');
                    myChart.hideLoading();
                }
            }
        })
    });
</script>