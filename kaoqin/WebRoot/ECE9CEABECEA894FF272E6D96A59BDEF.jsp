<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>统计信息</title>
    <script>
	var _hmt = _hmt || [];
	(function() {
	  var hm = document.createElement("script");
	  hm.src = "//hm.baidu.com/hm.js?935700f3226db9751e7e9abdb213a325";
	  var s = document.getElementsByTagName("script")[0]; 
	  s.parentNode.insertBefore(hm, s);
	})();
	</script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge"/>
    <meta name="renderer" content="webkit"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta content="telephone=no,email=no" name="format-detection">
    <meta name="msapplication-tap-highlight" content="no"/>
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <meta name="full-screen" content="yes">
    <meta name="x5-fullscreen" content="true">
    <meta name="browsermode" content="application">
    <meta name="x5-page-mode" content="app">
	<link rel="stylesheet" href="/css/bootstrap.min.css">
	<link rel="stylesheet" href="/css/bootstrap-theme.min.css">
	<script src="/js/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script src="/js/echarts.min.js"></script>
	<style type="text/css">
		.box {
			border: 1px solid #d9d9d9;
			border-radius: 4px;
			box-shadow: 0 2px 4px #d9d9d9;
			margin: 5px 0px 5px 10px;
			padding: 15px;
			padding-top: 30px;
			text-align: left;
		}
	</style>
  </head>
  	
  <body>
  	<div class="container" style="width:88%;margin-top:30px">
  		<div id="userHisMap" style="width:100%;height:400px"></div>
  		<hr/>
  		用户历史访问统计信息：<select id="userVisitHisSelect" class="form-control" style="width:20%;display:inline">
  			<option value="none">请选择日期</option>
  			<c:forEach items="${hisDates}" var="d">
  				<option value="${d}">${d}</option>
  			</c:forEach>
  		</select><br/>
  		<table id="userVisitHisTable" class="table table-bordered" style="width:100%;display:none">
  			<tr>
  				<td>用户Id</td>
  				<td>姓名</td>
  				<td>访问次数</td>
  				<td>最后访问时间</td>
  			</tr>
  		</table>
  		<hr/>
  		文件下载和打印次数
  		<table class="table table-bordered" style="width:100%">
  			<tr>
  				<td style="width:25%">调休文件下载次数</td>
  				<td style="width:25%">${downLoad.downLoadTiaoxiu}</td>
  				<td style="width:25%">加班文件下载次数</td>
  				<td style="width:25%">${downLoad.downLoadJiaban}</td>
  			</tr>
  			<tr>
  				<td>调休文件打印次数</td>
  				<td>${downLoad.printTiaoxiu}</td>
  				<td>加班文件打印次数</td>
  				<td>${downLoad.printJiaban}</td>
  			</tr>
  		</table>
  		<hr/>
  		用户访问统计&nbsp;&nbsp;<button id="userVisitBtn" class="btn btn-default">获取</button>
  		<table id="userVisitTable" class="table table-bordered" style="width:100%;display:none">
  			<tr>
  				<td style="width:50px">用户Id</td>
  				<td style="width:50px">姓名</td>
  				<td style="width:150px">访问时间</td>
  				<td>访问ip</td>
  				<td>客户端信息</td>
  			</tr>
  		</table>
  		<hr/>
  		<div id="userHisDiv" style="display:none">
  		用户历史数据查询：
  		<select id="userHistorySelect" class="form-control" style="width:20%;display:inline">
  			<option value="none" selected="selected">请选择用户</option>
  			<c:forEach items="${idToNameMap}" var="idToName">
  				<option value="${idToName.key}">${idToName.value}</option>
  			</c:forEach>
  		</select>
  		<br/>
  		<div id="userHistory" style="width:100%;height:400px"></div>
  		<hr/>
  		</div>
  	</div>
  	<script type="text/javascript">
  		var userCount = ${userCount};
  		var visitCount = ${visitCount};
  		var dates = ${dates};
		var option = {
		    title: {
		        text: '用户数和总访问数',
		        subtext: ''
		    },
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['用户数','总访问数']
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    toolbox: {
		        feature: {
		            saveAsImage: {}
		        }
		    },
		    xAxis: {
		        type: 'category',
		        boundaryGap: false,
		        data: dates
		    },
		    yAxis: {
		        type: 'value'
		    },
		    series: [
		        {
		            name:'用户数',
		            type:'line',
		            data:userCount,
		            itemStyle : {  
	                    normal : {  
	                    		color:'#4cae4c',
	                        lineStyle:{  
	                            color:'#5cb85c'  
	                        }  
	                    }  
	                }
		        },
		        {
		            name:'总访问数',
		            type:'line',
		            data:visitCount,
		            itemStyle : {  
	                    normal : {  
	                    		color:'#eea236',
	                        lineStyle:{  
	                            color:'#f0ad4e'  
	                        }  
	                    }  
	                }
		        }
		    ]
		};
		echarts.init(document.getElementById('userHisMap')).setOption(option);
		$("#userHistorySelect").change(function(){
			var val = $(this).val();
			var username = $("option[value='"+val+"']").html();
			if(val != "none"){
				$.ajax({
					url:"/ECE9CEABECEA894FF272E6D96A59BDEF/history",
					data:"userId="+val,
					type:"POST",
					success:function(data){
						if(data.indexOf("|")==-1){
							alert(data);
							return false;
						}
						var datas = data.split("|");
						var dates = datas[3].split(",");
						var jiabanHours = datas[0].split(",");
						var tiaoxiuHours = datas[1].split(",");
						var wangdakaCounts = datas[2].split(",");
						var option = {
						    title: {
						        text: username+'考勤历史信息',
						        subtext: ''
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:['加班小时数','调休小时数','忘打卡次数']
						    },
						    grid: {
						        left: '3%',
						        right: '4%',
						        bottom: '3%',
						        containLabel: true
						    },
						    toolbox: {
						        feature: {
						            saveAsImage: {}
						        }
						    },
						    xAxis: {
						        type: 'category',
						        boundaryGap: false,
						        data: dates
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [
						        {
						            name:'加班小时数',
						            type:'line',
						            data:jiabanHours,
						            itemStyle : {  
				                        normal : {  
				                        		color:'#4cae4c',
				                            lineStyle:{  
				                                color:'#5cb85c'  
				                            }  
				                        }  
				                    }
						        },
						        {
						            name:'调休小时数',
						            type:'line',
						            data:tiaoxiuHours,
						            itemStyle : {  
				                        normal : {  
				                        		color:'#d43f3a',
				                            lineStyle:{  
				                                color:'#d9534f'  
				                            }  
				                        }  
				                    }
						        },
						        {
						            name:'忘打卡次数',
						            type:'line',
						            data:wangdakaCounts,
						            itemStyle : {  
				                        normal : {  
				                        		color:'#eea236',
				                            lineStyle:{  
				                                color:'#f0ad4e'  
				                            }  
				                        }  
				                    }
						        }
						    ]
						};
						echarts.init(document.getElementById('userHistory')).setOption(option);
					}
				});
			}
		});
		Date.prototype.Format = function (formatStr) { //author: meizz 
			var str = formatStr;
            var Week = ['日', '一', '二', '三', '四', '五', '六'];
            str = str.replace(/yyyy|YYYY/, this.getFullYear());
            str = str.replace(/yy|YY/, (this.getYear() % 100) > 9 ? (this.getYear() % 100).toString() : '0' + (this.getYear() % 100));
            str = str.replace(/MM/, this.getMonth() > 8 ? (this.getMonth() + 1).toString() : '0' + (this.getMonth() + 1));
            str = str.replace(/M/g, this.getMonth());
            str = str.replace(/w|W/g, Week[this.getDay()]);
            str = str.replace(/dd|DD/, this.getDate() > 9 ? this.getDate().toString() : '0' + this.getDate());
            str = str.replace(/d|D/g, this.getDate());
            str = str.replace(/hh|HH/, this.getHours() > 9 ? this.getHours().toString() : '0' + this.getHours());
            str = str.replace(/h|H/g, this.getHours());
            str = str.replace(/mm/, this.getMinutes() > 9 ? this.getMinutes().toString() : '0' + this.getMinutes());
            str = str.replace(/m/g, this.getMinutes());
            str = str.replace(/ss|SS/, this.getSeconds() > 9 ? this.getSeconds().toString() : '0' + this.getSeconds());
            str = str.replace(/s|S/g, this.getSeconds());
            return str;
		}
		$("#userVisitHisSelect").change(function(){
			var val = $(this).val();
			if("none" == val){
				$("#userVisitHisTable").hide();
				return false;
			}
			$.ajax({
				url:"/ECE9CEABECEA894FF272E6D96A59BDEF/getVisitTableAjax",
				type:"POST",
				data:"date="+val,
				success:function(data){
					if(data.indexOf("{")==-1){
						alert(data);
						return false;
					}
					$(".add").remove();
					$("#userVisitHisTable").show();
					var jsonarray= $.parseJSON(data);
					var content = "";
					$.each(jsonarray,function(i,n){
						var clazz = "";
						if(n.count > 1 && n.count < 10){
							clazz = "warning";
						}
						if(n.count >= 10){
							clazz = "danger";
						}
						content = "<tr class='add "+clazz+"'><td>"+n.userId+"</td><td>"+n.userName+"</td><td>"+n.count+"</td><td>"+new Date(n.lastUpdateTime).Format("yyyy-MM-dd HH:mm:ss")+"</td></tr>";
						$("#userVisitHisTable").append(content);
					});
				}
			});
		});
		var c = 0;
		$(document).click(function(){
			c ++;
			if(c > 20){
				$("#userHisDiv").show();
			}
		});
		$("#userVisitBtn").click(function(){
			$.ajax({
				url:"/ECE9CEABECEA894FF272E6D96A59BDEF/getUserVisitAjax",
				type:"POST",
				success:function(data){
					$(".his").remove();
					$("#userVisitTable").show();
					var jsonarray= $.parseJSON(data);
					var content = "";
					$.each(jsonarray,function(i,n){
						content = "<tr class='his'><td class='userVisitId'>"+n.userId+"</td><td>"+n.userName+"</td><td>"+new Date(n.loginTime).Format("yyyy-MM-dd HH:mm:ss")+"</td><td>"+n.ip+"</td><td>"+n.userAgent+"</td></tr>";
						$("#userVisitTable").append(content);
					});
					$(".his").click(function(){
						$("#userVisitTable").find(".info").each(function(i,n){
							$(n).removeClass("info");
						});
						var ui = $(this).find(".userVisitId").html();
						$(".userVisitId:contains('"+ui+"')").each(function(i,n){
							$(n).parent().addClass("info");
						});
					});
					$(".his").dblclick(function(){
						$("#userVisitTable").find(".info").each(function(i,n){
							$(n).removeClass("info");
						});
					});
				}
			});
		});
  	</script>
  </body>
</html>