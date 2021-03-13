<%--
  Created by IntelliJ IDEA.
  User: snake
  Date: 2021/03/04
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta charset="UTF-8">
<script src="${pageContext.request.contextPath}/dist/js/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/dist/js/vintage.js"></script>
<p style="text-align: center;font-size: 18px;font-weight: 700;margin-bottom: 10px;">${enter_day} ${scenic_name} 实时客流量分析</p>
<div id="main" style="height: 100%;width: 100%;"></div>
<script type="text/javascript">

    layui.use(['jquery', 'layer', 'element'], function (args) {
        var $ = layui.jquery;
        var layer = layui.layer;

        var myChart = echarts.init(document.getElementById('main'));
        var option = {
            title: {
                text: ''
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#c5ccc7'
                    }
                }
            },
            legend: {
                data: ['客流承载量', '景区最大客流承载量', '实时客流量']
            },
            toolbox: {
                right: '0',
                bottom: '0',
                show: true,
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            }, visualMap: {
                top: 90,
                right: 0,
                pieces: [{
                    gt: 0,
                    lte: 80,
                    color: '#42992b'
                }, {
                    gt: 90,
                    lte: 100,
                    color: '#995b00'
                }, {
                    gt: 100,
                    color: '#e10000'
                }],
                outOfRange: {
                    color: '#e10000'
                }
            },
            dataZoom: {
                show: false,
                start: 0,
                end: 100
            },
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: true,
                    data: []
                },
                {
                    type: 'category',
                    boundaryGap: true,
                    data: []
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    scale: true,
                    name: '客流承载量',
                    max: 1,
                    min: 0,
                    boundaryGap: [0.2, 0.2]
                },
                {
                    type: 'value',
                    scale: true,
                    name: '实时客流量',
                    max: ${max_people},// y轴最大值
                    min: 0,  // y轴最小值
                    boundaryGap: [0.2, 0.2]
                }
            ],
            series: [
                {
                    name: '实时客流量',
                    type: 'bar',
                    xAxisIndex: 1,
                    yAxisIndex: 1,
                    data: []
                },
                {
                    name: '客流承载量',
                    type: 'line',
                    data: []
                }
            ]
        };

        var HLC = [];// 客流承载量   当前客流量/总客流量
        var HF = [];// 当前时间点客流量
        var count = [];// 时间坐标

        myChart.showLoading();	//数据加载完之前先显示一段简单的loading动画
            $.ajax({	//使用JQuery内置的Ajax方法
                type: "post",		//post请求方式
                async: true,		//异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "${pageContext.request.contextPath}/Data/getHLCInfor.do",	//请求发送到ShowInfoIndexServlet处
                data: {scenic_id: "${scenic_id}", enter_time: "${enter_day}"},		//请求内包含一个key为name，value为A0001的参数；服务器接收到客户端请求时通过request.getParameter方法获取该参数值
                dataType: "json",		//返回数据形式为json
                success: function (result) {
                    console.log(result);
                    //请求成功时执行该函数内容，result即为服务器返回的json对象
                    if (result != null && result.length > 0) {
                        for (var i = 0; i < result.length; i++) {
                            if (result[i].hlc > 1) {
                                layer.msg("警告！超过景区最大客流承载量！")
                            }
                            HLC.push(result[i].hlc);
                            HF.push(result[i].hf);
                            count.push(result[i].time_hour);
                        }
                        myChart.hideLoading();	//隐藏加载动画
                        option.xAxis[0].data = count;// 设置x轴数据1
                        option.xAxis[1].data = count;// 设置x轴数据2
                        option.series[0].data = HF;// 设置y轴数据1
                        option.series[1].data = HLC;// 设置y轴数据2
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