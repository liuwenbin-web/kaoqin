<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>考勤记录</title>
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
  	<input id="holidayText" type="hidden" value="${date}"/>
  	<input id="userIdText" type="hidden" value="${userId}"/>
  	<input id="jiaBanDanCountText" type="hidden" value="${jiaBanDanCount}"/>
  	<input id="tiaoXiuDanCountText" type="hidden" value="${tiaoXiuDanCount}"/>
  	<input id="kaoqinStr" type="hidden" value=""/>
  	说明：<br/>
  		<table class="table table-bordered" style="width:30%">
  			<tr>
				<td class="active">节假日</td>
				<td class="success">加班</td>
				<td class="warning">忘打卡</td>
				<td class="danger">调休</td>
				<td>正常</td>
			</tr>
		</table>
		<div class="btn-group">
			<button id="zhedieBtn" class="btn btn-default">隐藏正常项</button>
			<button id="zhengliBtn" class="btn btn-default">整理</button>
		</div>
		&nbsp;&nbsp;<span id="info"></span>
		<hr/>
		<div class="row">
		<div class="col-md-12 mainDiv box">
  		<table class="table table-bordered" style="width:100%">
  			<tr>
				<td>日期类型</td>
				<td>日期</td>
				<td>类型</td>
				<td>开始时间</td>
				<td>结束时间</td>
				<td class="status" style="display:none">状态</td>
			</tr>
  			<c:forEach items="${kaoqinList}" var="kaoqin" varStatus="e">
  				<c:if test="${kaoqin.isHoliday}">
  					<tr class="active normal">
  				</c:if>
  				<c:if test="${!kaoqin.isHoliday}">
  					<c:if test="${'加班'==kaoqin.color}">
  						<tr class="success zhengli">
  					</c:if>
  					<c:if test="${'正常'==kaoqin.color}">
  						<tr style="color:#c7c7c7" class="normal">
  					</c:if>
  					<c:if test="${'调休'==kaoqin.color}">
  						<tr class="danger zhengli">
  					</c:if>
  					<c:if test="${'忘打卡'==kaoqin.color}">
  						<tr class="warning zhengli">
  					</c:if>
  				</c:if>
  					<td>${kaoqin.holidayName}</td>
  					<td class="dateTd">${kaoqin.date}</td>
  					<td>
	  					<c:if test="${kaoqin.color == '加班' || kaoqin.color == '调休' || kaoqin.color == '忘打卡'}">
	  						<kbd>${kaoqin.color}</kbd>
	  					</c:if>
  					</td>
  					<td>${kaoqin.startTime}</td>
  					<td>${kaoqin.endTime}</td>
  					<td class="status" style="display:none"></td>
  				</tr>
  			</c:forEach>
  		</table>
  		</div>
  		<div class="col-md-4 box resultDiv" style="display:none">
  			<table style="width:100%">
  				<tr>
  					<td class="result" style="display:none">整理结果</td>
  				</tr>
  				<tr>
  					<td style="color:black;width:380px;display:none" class="result" id="resultTd" rowspan="${fn:length(kaoqinList)}"></td>
  				</tr>
  			</table>
  		</div>
  		</div>
  	</div>
  	
	<div id="resultModal" class="modal fade">
	  <div class="modal-dialog" style="width:82%">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title">最终结果</h4>
	      </div>
	      <div class="modal-body">
	        <p id="resultContent">
	        		<table class="table table-bordered">
	        			<tr>
	        				<td>调休</td>
	        				<td>忘打卡</td>
	        				<td>加班</td>
	        			</tr>
	        			<tr>
	        				<td id="tiaoxiuResult"></td>
	        				<td id="wangdakaResult"></td>
	        				<td id="jiabanResult"></td>
	        			</tr>
	        			<tr>
	        				<td id="totalResult" colspan="3"></td>
	        			</tr>
	        		</table>
	        </p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	      </div>
	    </div><!-- /.modal-content -->
	 </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<input style="display:none" type="text" id="jiabanTotalHour" value="0"/>
	<input style="display:none" type="text" id="tiaoxiuTotalHour" value="0"/>
  	
  	<div id="alertModal" class="modal fade">
	  <div class="modal-dialog" style="width:40%">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title">提示信息</h4>
	      </div>
	      <div class="modal-body">
	        <p id="alertContent">
	        		
	        </p>
	      </div>
	      <div class="modal-footer">
	        <button id="closeBtn" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	      </div>
	    </div><!-- /.modal-content -->
	 </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
  	<script type="text/javascript">
  		$("#zhedieBtn").click(function(){
  			if($(this).html()=="隐藏正常项"){
  				$(".normal").hide(300);
  				if($("#resultTd").html() != ""){
  					$(".normal").eq(0).show();
  				}
  				$(this).html("显示正常项");
  			}else{
  				$(".normal").show(300);
  				$(this).html("隐藏正常项");
  			}
  		});
  		function zhengliByDate(date){
  			$.ajax({
  				url:"/zhengli",
  				data:"date="+date+"&holidays="+$("#holidayText").val()+"&userId="+$("#userIdText").val(),
  				type:"POST",
  				beforeSend:function(){
  					$(".zhengli").each(function(i,e){
  						if($(e).find(".dateTd").html() == date){
  							$(e).find(".status").html("正在整理中");
  							$("#info").html("<code>正在整理"+date+"的数据</code>");
  						}
  					});
  				},
  				success:function(data){
  					//处理成功之后的逻辑
  					var jsonarray= $.parseJSON(data);
  					var content = "";
  					$.each(jsonarray,function(i, n){
  						var kaoqinStr = "";
  						if(n.type == "jiaban"){
  							content += "<div class='jiaban'><div class='alert alert-success' role='alert'>";
  							kaoqinStr += "j ";
  							$("#jiabanTotalHour").val($("#jiabanTotalHour").val() * 1 + n.interval * 1);
  						}else if(n.type == "tiaoxiu"){
  							content += "<div class='tiaoxiu'><div class='alert alert-danger' role='alert'>";
  							$("#tiaoxiuTotalHour").val($("#tiaoxiuTotalHour").val() * 1 + n.interval * 1);
  							kaoqinStr += "t ";
  						}else if(n.type == "wangdaka"){
  							content += "<div class='wangdaka'><div class='alert alert-warning' role='alert'>";
  							kaoqinStr += "w ";
  						}
  						kaoqinStr += n.date + " " + n.startTime + " " + n.endTime + " " + n.interval + "|";
  						$("#kaoqinStr").val($("#kaoqinStr").val() + kaoqinStr);
  					  	content += "日期：<b>" + n.date+"</b>&nbsp;&nbsp;";
  					  	if(n.type == "jiaban"){
  					  		content += "加班";	
  					  	}else if(n.type == "tiaoxiu"){
  					  		content += "调休";
  					  	}else if(n.type == "wangdaka"){
  					  		content += "忘记打卡";
  					  	}
  					  	if(n.type == "wangdaka"){
	  					  	content += "&nbsp;打卡时间：<b>"+n.startTime+"</b></div></div>";
  					  	}else{
  					  		content += "&nbsp;<b>" + n.startTime + "</b> -- <b>" + n.endTime+"</b>&nbsp;&nbsp;时长：<b>"+n.interval+" h</b></div></div>";
  					  	}
  					});
  					$(".mainDiv").addClass("col-md-7");
  					$(".mainDiv").removeClass("col-md-12");
  					$(".result").show();
  					$(".resultDiv").show();
  					$("#resultTd").html($("#resultTd").html()+content);
  					//获取下一个
  					var showNext = false;
  					var continueEach = true;
  					$(".zhengli").each(function(i,e){
  						if(continueEach){
  							if(showNext){
  								zhengliByDate($(e).find(".dateTd").html());
  	  							continueEach = false;
  	  							showNext = false;
  	  						}
  	  						if($(e).find(".dateTd").html() == date){
  	  							$(e).find(".status").html("");
  	  							showNext = true;
  	  						}
  						}
  					});
  					//全部整理完成
  					if(showNext){
  						$(".status").hide();
  						$("#info").html("");
  						$("#resultTd").html($("#resultTd").html()+"<button class='btn btn-default' style='width:100%' onclick='showFinalResult()'>查看最终结果</button>");
  						$("#zhengliBtn").html("整理");
  						//将最终的结果提交给后端，并进行保存
  						$.ajax({
  							url:"saveData",
  							data:"kaoqinStr="+$("#kaoqinStr").val()+"&userId="+$("#userIdText").val(),
  							type:"POST"
  						});
  					}
  				}
  			});
  		}
  		function showFinalResult(){
  			var jiabanContent = "";
  			var jiabanCount = 0;
  			$(".jiaban").each(function(i,n){
  				jiabanContent += $(n).html();
  				jiabanCount ++;
  			});
  			
  			var tiaoxiuContent = "";
  			var tiaoxiuCount = 0;
  			$(".tiaoxiu").each(function(i,n){
  				tiaoxiuContent += $(n).html();
  				tiaoxiuCount ++;
  			});
  			var wangdakaContent = "";
  			$(".wangdaka").each(function(i,n){
  				wangdakaContent += $(n).html();
  				tiaoxiuCount ++;
  			});
  			jiabanContent += "<div class='alert alert-info' role='alert'>加班总时长："+$("#jiabanTotalHour").val()+" h</div>";
  			tiaoxiuContent += "<div class='alert alert-info' role='alert'>调休总时长："+$("#tiaoxiuTotalHour").val()+" h</div>";
  			$("#jiabanResult").html(jiabanContent);
  			$("#tiaoxiuResult").html(tiaoxiuContent);
  			$("#wangdakaResult").html(wangdakaContent);
  			$("#resultModal").modal("show");
  			//计算 totalResult
  			var jiaBanDanCount = $("#jiaBanDanCountText").val() * 1;
  			var tiaoXiuDanCount = $("#tiaoXiuDanCountText").val() * 1;
  			var jiabanZhangShu = Math.ceil(jiabanCount/jiaBanDanCount);
  			var tiaoxiuZhangShu = Math.ceil(tiaoxiuCount/tiaoXiuDanCount);
  			var totalResult = "加班一共有：<code>"+jiabanCount+"</code>条，大约需要打印<code>"+jiabanZhangShu+"</code>张加班单<br/>";
  			totalResult += "调休和忘打卡一共有：<code>"+tiaoxiuCount+"</code>条，大约需要打印<code>"+tiaoxiuZhangShu+"</code>张调休单<br/>";
  			totalResult += "<code><a target='_blank' href='/print.jsp'>点击打印加班单</a></code>&nbsp;&nbsp;&nbsp;&nbsp;<code><a target='_blank' href='/print.jsp?t=1'>点击打印调休单</a></code><br/>";
  			totalResult += "<code><a target='_self' href='/download?fileName=jiaban'>点击下载加班单</a></code>&nbsp;&nbsp;&nbsp;&nbsp;<code><a target='_blank' href='/download?fileName=tiaoxiu'>点击下载调休单</a></code>";
  			$("#totalResult").html(totalResult);
  		}
  		function zhengli(){
  			$("#kaoqinStr").val("");
  			$("#zhengliBtn").html("整理中");
  			$(".status").show();
  			$("#jiabanTotalHour").val("0");
  			$("#tiaoxiuTotalHour").val("0");
  			$("#resultTd").html("");
  			$(".result").hide();
  			//获取第一个
  			if($(".zhengli").eq(0).html() == undefined){
  				alert("没有需要整理的加班或调休");
  				$("#zhengliBtn").html("整理");
  				$(".status").hide();
				$("#info").html("");
  			}else{
  				zhengliByDate($(".zhengli").eq(0).find(".dateTd").html());
  			}
  		}
  		$("#zhengliBtn").click(function(){
  			if($("#zhengliBtn").html()=="整理"){
		  		zhengli();
  			}
  		});
  		
  		function alert(str,showClose,width){
  			//backdrop
  			$("#alertModal .modal-dialog").css("width",width);
  			if(showClose){
  				$("#showClose").show();
  			}else{
  				$("#showClose").hide();
  			}
  			$('#alertModal').modal({
  				backdrop: 'static'
			});
  			$("#alertModal").modal("show");
  			$("#alertContent").html(str);
  		}
  	</script>
  </body>
</html>