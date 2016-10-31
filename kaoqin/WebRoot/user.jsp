<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%
	String userAgent = request.getHeader("User-Agent").toLowerCase();
	if(userAgent.indexOf("msie")!=-1){
		//IE浏览器
		%>
		<html>
			<head>
				<title>浏览器兼容性问题</title>
				<script>
				var _hmt = _hmt || [];
				(function() {
				  var hm = document.createElement("script");
				  hm.src = "//hm.baidu.com/hm.js?935700f3226db9751e7e9abdb213a325";
				  var s = document.getElementsByTagName("script")[0]; 
				  s.parentNode.insertBefore(hm, s);
				})();
				</script>
			</head>
			<body>
				<table style="width:100%" border="0">
					<tr>
						<td align="center">
							<b>目前该系统只支持非IE内核的浏览器，如：chrome，firefox和safari浏览器。
							<br/>国内双核浏览器（如：360安全浏览器，猎豹浏览器等）请切换至“极速模式”访问
							</b>
						</td>
						<tr>
							<td align="center" style="padding-top:50px">
								<img src="img/bsie.jpg"/>
							</td>
						</tr>
					</tr>
				</table>
			</body>
		</html>
		<%
		return;
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>考勤记录用户提交</title>
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
	<link rel="stylesheet" href="/css/zabuto_calendar.min.css">
	<script src="/js/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script src="/js/fileUpload.js"></script>
	<script src="/js/zabuto_calendar.min.js"></script>
	<script src="js/dmuploader.min.js"></script>
	<script src="js/jquery.md5.js"></script>
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
		.dateSelect {
			background-color:#ddedfb;
		}
	</style>
  </head>
  	
  <body>
	<div id="dragUploadDiv" class="dragContent" style="position:absolute;z-index:999;top:70;left:0;height:100%;width:100%;border:0px solid #000"></div>
  	<div class="container" style="width:70%;margin-top:30px">
  		<div>
  			<table>
  				<tr>
  					<td style="overflow:hidden"><button class="btn btn-default" id="uploadFileNameBtn">点击上传考勤文件</button><input id="kqFile" name="kqFile" type="file" style="display:none"></td>
  					<td><button style="display:none;margin-left:10px" id="uploadKaoQinFileBtn" class="btn btn-primary">立即上传</button><button style="display:none;" id="cancelUploadFileBtn" class="btn btn-danger">取消</button><code style="display:none;margin-left:10px" id="uploadMsg"></code></td>
  				</tr>
  				<tr class="dragContent">
  					<td style="padding-top:8px;padding-bottom:8px">
  						或将考勤文件拖拽到下面的空白处进行上传
  					</td>
  				</tr>
  			</table>
  			<input type="hidden" id="uploadFileName" value="刘文斌-考勤1.xls"/>
  			<div id="userContent">
  			</div>
  			<div id="dateContent" style="display:none">
  				<hr/>
  				请选择<code id="dateStr"></code>的规定休息日（包括各种法定假日及周末）:
  				<div id="kqCalendar" style="width:400px"></div>
  				说明：蓝色标注的日期是休息日（包含法定假日及周末）<br>可根据实际情况，点击日期取消或者新增。<kbd id="careful" data-toggle="tooltip" data-placement="right" title="日期设置会直接影响考勤结果">谨慎操作</kbd><br>
  				<code><a href="javascript:;" onclick="showRiliShuoMing()">不知如何操作？点我</a></code>
  			</div>
  			<hr/>
  			<div id="upDiv" style="display:none">
  				<div id="upInfo">
  				</div>
  			</div>
  			<div id="submitDiv" style="display:none">
  				确认信息无误后&nbsp;<button id="submitBtn" class="btn btn-primary" disabled>提交信息，整理考勤内容</button>&nbsp;&nbsp;<code id="upMsg" style="display:none"></code>
  			</div>
  			<form id="infoForm" action="/kaoqin" method="post">
  				<input type="hidden" name="userId" id="userIdSubmitInput"/>
  				<input type="hidden" name="date" id="dateSubmitInput"/>
  				<input type="hidden" name="pwd" id="pwdSubmitInput"/>
  			</form>
  		</div>
  	</div>
  	
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
	<div id="uploadResult" style="display:none"></div>
  	<script type="text/javascript">
  		var sysAlertContent = "<b>系统说明：</b><br/><br/><span style='color:red'>1. 通过系统整理出来的数据不能保证完全正确，请认真核对整理出来的信息。<br/>&nbsp;&nbsp;&nbsp;&nbsp;如因该系统造成考勤提交的问题，该系统概不负责</span><br/>";
  		sysAlertContent += "2. 请确保上传的考勤文件真实有效。<br/>";
  		sysAlertContent += "3. 该系统只适用于工作时间为：9:00 -- 18:00 的考勤记录。<br/>";
  		sysAlertContent += "4. 如有任何系统相关的意见、建议或问题，请发送邮件至 <code>liuwb@mapbar.com</code><br/>";
  		sysAlertContent += "5. 该弹窗只在首次访问时自动弹出<hr/>";
  		sysAlertContent += "<b>安全说明：</b><br/><br/>1.上传的考勤文件不会在服务器上保留，解析完数据后立即删除。<br/>";
  		sysAlertContent += "2. 初次使用需要设置密码。密码设置成功后不能修改，请牢记<br/>";
  		sysAlertContent += "3. 密码在网络传输和服务器存储中使用了非对称性加密。不会被破解<br/>";
  		sysAlertContent += "4. 用户信息以加密的形式进行存储。<br/>";
  		var upinter = null;
  		var secondDown = 3;
  		$("#uploadFileNameBtn").click(function(){
  			if("点击上传考勤文件" == $("#uploadFileNameBtn").html()){
	  			$("#kqFile").click();
  			}
  		});
  		$(window).resize(function(){
  			$("#dragUploadDiv").css("height",$(document).height() - 70);
  		});
  		$("#dragUploadDiv").css("height",$(document).height() - 140);
  		function resetBtns(){
  			$("#uploadFileNameBtn").html("点击上传考勤文件");
  			$("#kqFile").val("");
  			$("#uploadKaoQinFileBtn").hide();
  			$("#uploadFileNameBtn").removeClass("active");
			$("#cancelUploadFileBtn").hide();
			if(null != upinter){
				clearInterval(upinter);
				upinter = null;
			}
			secondDown = 3;
  		}
  		$("#careful").mouseover(function(){
  			$("#careful").tooltip('show');
  		});
  		//uploadMsg
  		function uploadInterval(){
  			secondDown --;
  			if(secondDown == 0){
  				clearInterval(upinter);
  				upinter = null;
  				secondDown = 3;
  				$("#uploadKaoQinFileBtn").hide();
  				$("#cancelUploadFileBtn").hide();
  				$("#uploadMsg").html("");
  				$("#uploadMsg").hide();
  				uploadFile();
  			}
  			$("#uploadMsg").html(secondDown + "秒后自动提交");
  		}
  		$("#kqFile").change(function(){
  			$("#uploadFileNameBtn").html("文件名："+$(this).val());
  			if("点击上传考勤文件" != $("#uploadFileNameBtn").html()){
  				$(".dragContent").hide();
  				$("#uploadFileNameBtn").addClass("active");
  				$("#uploadKaoQinFileBtn").show();
  				$("#cancelUploadFileBtn").show();
  				$("#uploadMsg").removeClass("alert-success").removeClass("alert-danger");
  				$("#uploadMsg").html(secondDown + "秒后自动提交");
  				$("#uploadMsg").show();
  				secondDown = 3;
  				upinter = setInterval("uploadInterval()",1000);
  			}
  		});
  		$("#cancelUploadFileBtn").click(function(){
  			resetBtns();
  			$("#uploadMsg").html("");
			$("#uploadMsg").hide();
			$(".dragContent").show();
  		});
  		$("#uploadKaoQinFileBtn").click(function(){
  			$("#uploadKaoQinFileBtn").hide();
			$("#cancelUploadFileBtn").hide();
			$("#uploadMsg").removeClass("alert-success").removeClass("alert-danger");
			$("#uploadMsg").html("上传中...");
			if(null != upinter){
				clearInterval(upinter);
				upinter = null;
			}
			secondDown = 3;
  			uploadFile();
  		});
  		function uploadFile(){
  			if($("#kqFile").val() == ""){
  				$("#uploadMsg").html("请先选择考勤文件，请刷新页面重试");
    	 			$("#uploadMsg").removeClass("alert-success").removeClass("alert-danger");
    	 			$("#uploadMsg").addClass("alert-danger");
    	 			$("#uploadMsg").slideDown(300);
    	 			return false;
  			}
  			$.ajaxFileUpload({
  		         url:'/uploadFileAjax',
  		         fileElementId:'kqFile',
  		       	 dataType : 'text',
  		         success: function (data, status){
  		        	 	$("#uploadResult").html(data);
  		        	 	data = $("#uploadResult").text();
  		        	 	if(data.indexOf("上传成功")!=-1){
  		        	 		$("#uploadMsg").html("上传成功");
  		        	 		$("#uploadMsg").removeClass("alert-success").removeClass("alert-danger");
  		        	 		$("#uploadMsg").addClass("alert-success");
  		        	 		$("#uploadMsg").slideDown(300);
  		        	 		var fileName = data.split(":")[1];
  		        	 		$("#uploadFileName").val(fileName);
  		        	 		baseAnalyse();
  		        	 	}else{
  		        	 		$("#uploadMsg").html(data+"，请刷新页面重试");
  		        	 		$("#uploadMsg").removeClass("alert-success").removeClass("alert-danger");
  		        	 		$("#uploadMsg").addClass("alert-danger");
  		        	 		$("#uploadMsg").slideDown(300);
  		        	 	}
  		         },
  		         error: function (data, status){
  		        		$("#uploadMsg").html("未知错误，上传失败，请刷新页面重试");
	        	 		$("#uploadMsg").removeClass("alert-success").removeClass("alert-danger");
	        	 		$("#uploadMsg").addClass("alert-danger");
	        	 		$("#uploadMsg").slideDown(300);
	        	 		resetBtns();
  		         }
  		     });
  		}
  		function showRiliShuoMing(){
  			alert("<table style='width:100%' border='0'><tr><td align='center'>可以去百度搜索日历，将百度日历上的标明不工作的日期（节假日，周六周天）对应到该系统的日历上，以2016年9月份的数据为例。见下图</td></tr><tr><td align='center'><hr/></td></tr><tr><td align='center'><img src='/img/rilishuoming.jpg' style='width:75%'/></td></tr></table>",true,"80%");
  		}
  		//baseAnalyse();
  		function baseAnalyse(){
  			$.ajax({
  				url:"/getBaseInfo",
  				type:"POST",
  				data:"fileName="+$("#uploadFileName").val(),
  				success:function(data){
  					if(""==data){
  						$("#uploadMsg").html("未知错误，解析用户数据失败");
		        	 		$("#uploadMsg").removeClass("alert-success").removeClass("alert-danger");
		        	 		$("#uploadMsg").addClass("alert-danger");
		        	 		$("#uploadMsg").slideDown(300);
  						return false;
  					}
  					$("#userIdSubmitInput").val(data.userId);
  					if(1 == data.isFirstUse){
  						alert(sysAlertContent,true,"40%");
  					}
  					var uc = "<hr/>"+data.userName;
  					uc += "，您好。您上传的考勤为<code>";
  					uc += data.date;
  					uc += "</code>的考勤信息，以下是考勤规则的相关说明：<br/><br/>";
  					uc += "您的工作时间为：<code>"+data.startWorkTimeStr+" -- "+data.endWorkTimeStr+"</code><br/>";
  					uc += "午休时间为：<code>"+data.halfBeginTimeStr+" -- "+data.halfEndTimeStr+"</code><br/>";
  					uc += "工作日加班超过<code>19:30</code>方认定为加班，加班时间自<code>"+data.overWorkLineTimeStr+"</code>开始计算<br/>";
  					$("#userContent").hide();
  					$("#userContent").html(uc);
  					$("#userContent").slideDown(800,function(){
  						$("#dateContent").slideDown(800,function(){
  							$("#upDiv").slideDown(800,function(){
  								$("#submitDiv").slideDown(800);
  							});
  						});
  					});
  					$("#dateStr").html(data.date);
  					var year = data.date.split("年")[0];
  					var month = data.date.split("年")[1].replace("月","");
  					$("#kqCalendar").zabuto_calendar({
  						cell_border: true,
  						year: year,
  				      	month: month,
  				      	show_previous: false,
  				      	show_next: false,
  			  		    language: "cn",
	  			  		action: function () {
	  			  			var child = $(this).children();
	  		                if(child.attr("class").indexOf("dateSelect") != -1){
	  		                		child.removeClass("dateSelect");
	  		                }else{
	  		                		child.addClass("dateSelect");
	  		                }
	  		            }
  			  	    });
  					//获取法定假日
  					$.ajax({
  						url:"getHoliday",
  						data:"year="+year+"&month="+month,
  						type:"POST",
  						success:function(d){
  							$(".day").each(function(i,n){
  								if(d.indexOf("/"+$(n).html()+"/")!=-1){
  									$(n).addClass("dateSelect");
  								}
  							});
  						}
  					});
  					
  					//记录用户访问次数
  					$.ajax({
  						url:"setUserVisitAjax",
  						data:"userId="+data.userId+"&userName="+data.userName,
  						type:"POST"
  					});
  					//获取用户密码情况。
  					$.ajax({
  						url:"/getUp",
  						data:"userId="+data.userId,
  						type:"POST",
  						success:function(d){
  							if(d == "first"){
  								$("#upInfo").html("您首次使用该系统，为了数据安全，请设置一个安全密码<br/><code>长度在6-16位。该密码一旦设置，不可修改。</code><br/>安全密码：<input value='' type='password' id='upInput' class='form-control' style='width:300px;display:inline'/><hr/>");
  								$("#pwdSubmitInput").val("");
  							}else if(d == "exist"){
  								if(window.localStorage){
  									var lsPwd = localStorage[data.userId];
  									if(lsPwd == null || "" == lsPwd || "null" == lsPwd){
  										$("#upInfo").html("请输入您的安全密码：<br/>安全密码：<input value='' type='password' id='upInput' class='form-control' style='width:300px;display:inline'/><hr/>");
  										$("#pwdSubmitInput").val("");
  									}else{
  										$("#pwdSubmitInput").val(lsPwd);
  										$("#submitBtn").removeAttr("disabled");
  									}
  								}else{
  									$("#upInfo").html("请输入您的安全密码：<br/>安全密码：<input value='' type='password' id='upInput' class='form-control' style='width:300px;display:inline'/><hr/>");
  									$("#pwdSubmitInput").val("");
  								}
  							}else{
  								//判断有无缓存
  								if(window.localStorage){
  									//localStorage[data.userId] = null;
  									var lsPwd = localStorage[data.userId];
  									if(lsPwd == null || "" == lsPwd || "null" == lsPwd){
  										$("#upInfo").html("请输入您的安全密码：<br/>安全密码：<input value='' type='password' id='upInput' class='form-control' style='width:300px;display:inline'/><hr/>");
  										$("#pwdSubmitInput").val("");
  									}else{
  										$("#pwdSubmitInput").val(lsPwd);
  										$("#submitBtn").removeAttr("disabled");
  									}
  								}else{
  									$("#upInfo").html("请输入您的安全密码：<br/>安全密码：<input value='' type='password' id='upInput' class='form-control' style='width:300px;display:inline'/><hr/>");
  									$("#pwdSubmitInput").val("");
  								}
  							}
  							$("#upInput").val("");
  							$("#upInput").keyup(function(){
  								if($("#upInput").val() !== ""){
  									$("#submitBtn").removeAttr("disabled");
  								}else{
  									$("#submitBtn").attr("disabled","disabled");
  								}
  							});
  						}
  					});
  					//预先加载图片
  					$("body").append("<img src='img/step1.png' style='display:none'/>");
  					$("body").append("<img src='img/step2.png' style='display:none'/>");
  					$("body").append("<img src='img/step3.png' style='display:none'/>");
  					$("body").append("<img src='img/step4.png' style='display:none'/>");
  					$("body").append("<img src='img/rilishuoming.jpg' style='display:none'/>");
  				}
  			});
  		}
  		$("#submitBtn").click(function(){
  			//获取节假日时间
  			var dataStr = "/";
  			$(".dateSelect").each(function(i,n){
  				dataStr += $(n).html() + "/";
  			});
  			if(dataStr == "/"){
  				alert("请在日历上标出休息日",true,"40%");
  				return false;
  			}
  			//验证密码
  			var upSubmit = true;
  			if($("#pwdSubmitInput").val() == ""){
	  			var pwd = $("#upInput").val();
	  			if(pwd.length < 6){
	  				alert("密码太短，密码长度请在6-16位");
	  				return false;
	  			}
	  			if(pwd.length > 16){
	  				alert("密码太长，密码长度请在6-16位");
	  				return false;
	  			}
	  			//pwd的密码加密
	  			pwd = $.md5(pwd);
	  			var userId = $("#userIdSubmitInput").val();
	  			$.ajax({
	  				url:"setUp",
	  				type:"POST",
	  				async: false,
	  				data:"userId="+userId+"&pwd="+pwd,
	  				beforeSend:function(){
	  					$("#upMsg").html("正在验证中，请稍候");
	  					$("#upMsg").show();
	  				},
	  				success:function(data){
	  					if("success"!=data){
	  						upSubmit = false;
	  						$("#upMsg").html(data);
	  						return false;
	  					}else{
	  						//成功后保存
	  						if(window.localStorage){
								localStorage[userId]=pwd;
							}
	  						$("#pwdSubmitInput").val(pwd);
	  					}
	  				}
	  			});
  			}
  			$("#dateSubmitInput").val(dataStr);
  			if(upSubmit){
  	  			$("#infoForm").submit();
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
  	<script type="text/javascript">
  		var dragFileName = null;
		$('#dragUploadDiv').dmUploader({
			url : '/uploadKaoQinAjax',
			dataType : 'json',
			onInit : function() {
			},
			onBeforeUpload : function(id) {
			},
			onNewFile : function(id, file) {
				dragFileName = file.name;
				$("#uploadFileNameBtn").html("文件名："+dragFileName);
	  			$("#uploadFileNameBtn").addClass("active");
	  			$("#uploadMsg").removeClass("alert-success").removeClass("alert-danger");
      	 		$("#uploadMsg").slideDown(300);
				$("#uploadMsg").html("上传中...");
			},
			onComplete : function(data) {
				$(".dragContent").hide();
				$("#uploadMsg").html("上传成功");
      	 		$("#uploadMsg").removeClass("alert-success").removeClass("alert-danger");
      	 		$("#uploadMsg").addClass("alert-success");
      	 		$("#uploadMsg").slideDown(300);
      	 		$.ajax({
      	 			url:"getDragRealNameAjax",
      	 			data:"fileName="+dragFileName,
      	 			type:"POST",
      	 			success:function(data){
      	 				$("#uploadFileName").val(data);
		      	 		baseAnalyse();
      	 			}
      	 		});
			},
			onUploadProgress : function(id, percent) {
			},
			onUploadSuccess : function(id, data) {
			},
			onUploadError : function(id, message) {
			},
			onFileTypeError : function(file) {
			},
			onFileSizeError : function(file) {
			},
			onFallbackMode : function(message) {
			}
		});
		var sysAlertCount = 0;
		$(document).click(function(){
			sysAlertCount ++;
			if(40 == sysAlertCount){
				alert(sysAlertContent,true,"40%");
				sysAlertCount = 0;
			}
		});
	</script>
  </body>
</html>