<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>考勤记录</title>
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
  	<input id="holidayText" type="hidden" value="${date}"/>
  	<input id="pwdText" type="hidden" value="${pwd}"/>
  	<input id="userIdText" type="hidden" value="${userId}"/>
  	<input id="jiaBanDanCountText" type="hidden" value="${jiaBanDanCount}"/>
  	<input id="tiaoXiuDanCountText" type="hidden" value="${tiaoXiuDanCount}"/>
  	<input id="kaoqinStr" type="hidden" value=""/>
  	<div id="shuoMingDiv" style="display:none">
  	说明：<br/>
  		<table class="table table-bordered" style="width:40%">
  			<tr>
				<td class="success" style="width:20%">加班</td>
				<td class="warning" style="width:20%">忘打卡</td>
				<td class="danger" style="width:20%">调休</td>
				<td class="active" style="width:20%">节假日</td>
				<td>正常</td>
			</tr>
		</table>
		</div>
		<div class="btn-group" id="operateDiv" style="display:none">
		<button id="howToUse" class="btn btn-default">如何使用？</button>
		<c:if test="${showHistory == 'true'}">
			<button id="historyBtn" class="btn btn-info">历史记录</button>
		</c:if>
		<c:if test="${userId == '23007'}">
			<button id="tongjiBtn" class="btn btn-info">统计信息</button>
		</c:if>
		<button id="zhedieBtn" class="btn btn-default">隐藏正常项</button>
			<button id="zhengliBtn" class="btn btn-default">整理</button>
		</div>
		&nbsp;&nbsp;<span id="info"></span>
		<br/><br/>
		<div class="row" id="rowsDiv" style="display:none">
		<div class="col-md-12 mainDiv box">
		如果某天中有调休和加班，下表中最终的显示状态为“调休”，加班的时间会在整理之后获取<br/>
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
  					<td>${kaoqin.holidayName}
  						<c:if test="${kaoqin.inTenMin != 0}">
  							<span class="glyphicon glyphicon-exclamation-sign inTenMin" data-toggle="tooltip" data-placement="right" title="第${kaoqin.inTenMin}次10分钟内迟到，不记录"></span>
  						</c:if>
  					</td>
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
  	<c:if test="${showHistory == 'true'}">
  	<div id="historyModal" class="modal fade">
	  <div class="modal-dialog" style="width:90%">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title">考勤信息历史记录</h4>
	      </div>
	      <div class="modal-body">
	      		 <span>说明：下图信息只能大概反映加班、调休和忘打卡的趋势，不能做为统计加班总时长的依据。</span>
	        		<p id="userHistory" style="width:400px;height:400px"></p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	      </div>
	    </div><!-- /.modal-content -->
	 </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
  	</c:if>
	<div id="resultModal" class="modal fade">
	  <div class="modal-dialog" style="width:90%">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title">最终结果</h4>
	      </div>
	      <div class="modal-body">
	        <p id="resultContent">
	        		<table class="table table-bordered">
	        			<tr>
	        				<td id="totalResult" colspan="3"></td>
	        			</tr>
	        			<tr>
	        				<td style="width:33%">调休</td>
	        				<td style="width:33%">忘打卡</td>
	        				<td>加班</td>
	        			</tr>
	        			<tr>
	        				<td id="tiaoxiuResult"></td>
	        				<td id="wangdakaResult"></td>
	        				<td id="jiabanResult"></td>
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
	        <h4 class="modal-title" id="alertTitle"></h4>
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
	
	<c:if test="${userId == '23007'}">
		<form id="tongjiForm" action="/ECE9CEABECEA894FF272E6D96A59BDEF" method="POST" target="_blank">
			<input type="hidden" name="userId" value="${userId}"/>
		</form>
		<script type="text/javascript">
			$("#tongjiBtn").click(function(){
				$("#tongjiForm").submit();
			});
		</script>
	</c:if>
  	<script type="text/javascript">
  		//效果
		setTimeout("divFadeIn()",200);
		function divFadeIn(){
			$("#shuoMingDiv").fadeIn(600,function(){
	  			$("#operateDiv").fadeIn(600,function(){
	  				$("#rowsDiv").fadeIn(600);
	  			});
	  		});
		}
  		$("#howToUse").click(function(){
  			showHowToUse();
  		});
  		function showHowToUse(){
  			var stepContent = "<table style='width:100%'><tr><td><b>请按照下面的步骤顺序执行：</b></td></tr><tr><td><code>第一步</code> 点击下图中的\"整理\"按钮，进行数据整理</td></tr>";
  			stepContent += "<tr><td><img src='img/step1.png' style='width:75%'/></td></tr>";
  			stepContent += "<tr><td>整理之后的数据是按照时间的顺序进行排列的，可以和左侧的数据进行对比查看，判断数据是否正确。如果不正确，请手动进行整理。如果数据正确，可以执行第二步<hr/></td></tr>";
  			stepContent += "<tr><td><code>第二步</code> 点击\"查看最终结果按钮\"，生成最终结果。如下图</td></tr>";
  			stepContent += "<tr><td><img src='img/step2.png' style='width:75%'/></td></tr>";
  			stepContent += "<tr><td>最终数据是按照类型进行排列的，方便填写加班和调休单，如下图</td></tr>";
  			stepContent += "<tr><td><img src='img/step3.png' style='width:75%'/><hr/></td></tr>";
  			stepContent += "<tr><td><code>第三步</code> 可以直接下载或在线打印加班调休单。如下图</td></tr>";
  			stepContent += "<tr><td><img src='img/step4.png' style='width:75%'/></td></tr>";
  			stepContent += "<tr><td>该弹窗只在首次访问时自动弹出</td></tr>";
  			stepContent += "</table>";
  			alert("使用步骤说明",stepContent,true,"70%");
  		}
  		if("1" == "${baseInfo.isFirstUse}"){
  			showHowToUse();
  		}
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
  				data:"date="+date+"&holidays="+$("#holidayText").val()+"&userId="+$("#userIdText").val()+"&pwd="+$("#pwdText").val(),
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
  					if(data.indexOf("{") != -1){
  						var jsonarray= $.parseJSON(data);
  	  					var content = "";
  	  					$.each(jsonarray,function(i, n){
  	  						var kaoqinStr = "";
  	  						if(n.type == "jiaban"){
  	  							content += "<div class='jiaban'><div class='alert alert-success' inter='"+n.interval+"' orginClass='alert-success' role='alert' orgin='日期：<b>"+n.date+"</b> 打卡时间："+n.dakaStartTime+" -- "+n.dakaEndTime+"'>";
  	  							kaoqinStr += "j ";
  	  							$("#jiabanTotalHour").val($("#jiabanTotalHour").val() * 1 + n.interval * 1);
  	  						}else if(n.type == "tiaoxiu"){
  	  							if(n.dakaStartTime == ""){
  	  								content += "<div class='tiaoxiu'><div class='alert alert-danger' inter='"+n.interval+"' orginClass='alert-danger' role='alert' orgin='日期：<b>"+n.date+"</b> 无打卡时间'>";
  	  							}else{
  	  								content += "<div class='tiaoxiu'><div class='alert alert-danger' inter='"+n.interval+"' orginClass='alert-danger' role='alert' orgin='日期：<b>"+n.date+"</b> 打卡时间："+n.dakaStartTime+" -- "+n.dakaEndTime+"'>";
  	  							}
  	  							$("#tiaoxiuTotalHour").val($("#tiaoxiuTotalHour").val() * 1 + n.interval * 1);
  	  							kaoqinStr += "t ";
  	  						}else if(n.type == "wangdaka"){
  	  							content += "<div class='wangdaka'><div class='alert alert-warning' orginClass='alert-warning' role='alert' orgin='日期：<b>"+n.date+"</b> 打卡时间："+n.dakaStartTime+"'>";
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
  					}
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
  							data:"kaoqinStr="+$("#kaoqinStr").val()+"&userId="+$("#userIdText").val()+"&pwd="+$("#pwdText").val(),
  							type:"POST"
  						});
  						$("#resultTd .alert").dblclick(function(){
  							var origin = $(this).attr("orgin");
  							var html = $(this).html();
  							if(html.indexOf("打卡时间")==-1){
  								$(this).removeClass("alert-success").removeClass("alert-danger").removeClass("alert-warning").addClass("alert-default");
  							}else{
  								$(this).removeClass("alert-default").addClass($(this).attr("orginClass"));
  							}
  							$(this).html(origin);
	 						$(this).attr("orgin",html);
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
  			var wangdakaCount = 0;
  			$(".wangdaka").each(function(i,n){
  				wangdakaContent += $(n).html();
  				wangdakaCount ++;
  			});
  			if(0==jiabanCount){
  				jiabanContent = "<div class='alert alert-success' role='alert'>无加班记录</div>";
  			}else{
	  			jiabanContent += "<div id='totalJianBanContentDiv' class='alert alert-info' role='alert' totalTime='"+$("#jiabanTotalHour").val()+"'>加班总时长："+$("#jiabanTotalHour").val()+" h</div>";
  			}
  			if(0 == tiaoxiuCount){
  				tiaoxiuContent += "<div class='alert alert-danger' role='alert'>无调休记录</div>";
  			}else{
	  			tiaoxiuContent += "<div id='totalTiaoXiuContentDiv' class='alert alert-info' role='alert' totalTime='"+$("#tiaoxiuTotalHour").val()+"'>调休总时长："+$("#tiaoxiuTotalHour").val()+" h</div>";
  			}
  			$("#jiabanResult").html(jiabanContent);
  			$("#tiaoxiuResult").html(tiaoxiuContent);
  			if(wangdakaCount == ""){
  				$("#wangdakaResult").html("<div class='alert alert-warning' role='alert'>无忘打卡记录</div>");
  			}else{
  				$("#wangdakaResult").html(wangdakaContent);
  			}
  			$("#resultModal").modal("show");
  			//计算 totalResult
  			var jiaBanDanCount = $("#jiaBanDanCountText").val() * 1;
  			var tiaoXiuDanCount = $("#tiaoXiuDanCountText").val() * 1;
  			var jiabanZhangShu = Math.ceil(jiabanCount/jiaBanDanCount);
  			var tiaoxiuZhangShu = Math.ceil((tiaoxiuCount + wangdakaCount)/tiaoXiuDanCount);
  			var totalResult = "";
  			if(0!=jiabanCount){
  				totalResult = "加班一共有：<code>"+jiabanCount+"</code>条，大约需要打印<code>"+jiabanZhangShu+"</code>张加班单<br/>";
  			}
			if(wangdakaCount == 0 && 0 != tiaoxiuCount){
	  			totalResult += "调休一共有：<code>"+tiaoxiuCount+"</code>条，大约需要打印<code>"+tiaoxiuZhangShu+"</code>张调休单<br/>";
  			}
			if(wangdakaCount != 0 && 0 == tiaoxiuCount){
				totalResult += "忘打卡一共有：<code>"+wangdakaCount+"</code>条，大约需要打印<code>"+tiaoxiuZhangShu+"</code>张调休单<br/>";
			}
			if(wangdakaCount != 0 && 0 != tiaoxiuCount){
				totalResult += "调休和忘打卡一共有：<code>"+(tiaoxiuCount + wangdakaCount)+"</code>条，大约需要打印<code>"+tiaoxiuZhangShu+"</code>张调休单<br/>";
			}
  			totalResult += "<code><a target='_blank' href='/print.jsp'>点击打印加班单</a></code>&nbsp;&nbsp;&nbsp;&nbsp;<code><a target='_blank' href='/print.jsp?t=1'>点击打印调休单</a></code><br/>";
  			totalResult += "<code><a target='_self' href='/download?fileName=jiaban'>点击下载加班单</a></code>&nbsp;&nbsp;&nbsp;&nbsp;<code><a target='_blank' href='/download?fileName=tiaoxiu'>点击下载调休单</a></code>";
  			$("#totalResult").html(totalResult);
  			$("#resultContent .alert").dblclick(function(){
				if($(this).html().indexOf("总时长")==-1 && $(this).html().indexOf("记录")==-1){
					var origin = $(this).attr("orgin");
					var html = $(this).html();
					var type = "";
					var inter = 0;
					if(origin.indexOf("加班") != -1 || html.indexOf("加班") != -1){
						type = "加班"
					}
					if(origin.indexOf("调休") != -1 || html.indexOf("调休") != -1){
						type = "调休";
					}
					if(html.indexOf("忘记打卡")!=-1 || html.indexOf("时长")!=-1 ){
						$(this).removeClass("alert-success").removeClass("alert-danger").removeClass("alert-warning").addClass("alert-default");
						inter = 0 - 1*$(this).attr("inter");
					}else{
						$(this).removeClass("alert-default").addClass($(this).attr("orginClass"));
						inter = 1*$(this).attr("inter");
					}
					$(this).html(origin);
					$(this).attr("orgin",html);
					var user = "${baseInfo.userId == '23007'}";
					if(user == "true"){
						var currentTotal = 0;
						if(type == "加班"){
							currentTotal = $("#totalJianBanContentDiv").attr("totalTime")*1 + inter;
							$("#totalJianBanContentDiv").html("加班总时长："+currentTotal+" h");
							$("#totalJianBanContentDiv").attr("totalTime",currentTotal);
						}
						if(type == "调休"){
							currentTotal = $("#totalTiaoXiuContentDiv").attr("totalTime")*1 + inter;
							$("#totalTiaoXiuContentDiv").html("调休总时长："+currentTotal+" h");
							$("#totalTiaoXiuContentDiv").attr("totalTime",currentTotal);
						}
					}
				}
  			});
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
  				alert("提示信息","没有需要整理的加班或调休",true,"40%");
  				$("#zhengliBtn").html("整理");
  				$(".status").hide();
				$("#info").html("");
  			}else{
  				//效果
  	  			$(".mainDiv").animate({width:"58.3333%"},700,function(){
	  				zhengliByDate($(".zhengli").eq(0).find(".dateTd").html());
  	  			});
  			}
  		}
  		$("#zhengliBtn").click(function(){
  			if($("#zhengliBtn").html()=="整理"){
		  		zhengli();
  			}
  		});
  		
  		function alert(title,str,showClose,width){
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
  			$("#alertTitle").html(title);
  		}
  		$(".inTenMin").mouseover(function(){
  			$(this).tooltip('show');
  		});
  		
  		//统计
  		$.ajax({
  			url:"/tongjiUserAjax",
  			type:"POST",
  			data:"userId=${userId}"
  		});
  	</script>
  	<c:if test="${showHistory == 'true'}">
  	<script type="text/javascript">
		$("#historyBtn").click(function(){
			$.ajax({
				url:"/userHistoryAjax",
				type:"POST",
	  			data:"userId=${userId}",
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
					        text: '',
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
					$("#userHistory").css("width",$("#historyModal").css("width").replace("px","") * 0.9 - 30);
					$("#historyModal").modal('show');
					echarts.init(document.getElementById('userHistory')).setOption(option);
	  			}
			});
		});
  	</script>
  	</c:if>
  </body>
</html>