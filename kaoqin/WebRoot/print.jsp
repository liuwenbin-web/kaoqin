<%@page import="com.mapbar.kaoqin.util.DownLoadSave"%>
<%@page import="com.mapbar.kaoqin.bean.DownLoadBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>打印</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="/js/jquery.min.js"></script>
	<script src="/js/jPrintArea.js"></script>
	<%
		String type = request.getParameter("t");
		if(null == type || "".equals(type)){
			type = "jiaban";
			DownLoadBean downLoadBean = DownLoadSave.fileToObject();
			if(downLoadBean == null){
				downLoadBean = new DownLoadBean();
				downLoadBean.setDownLoadJiaban(0);
				downLoadBean.setDownLoadTiaoxiu(0);
				downLoadBean.setPrintJiaban(0);
				downLoadBean.setPrintTiaoxiu(0);
			}
			downLoadBean.setPrintJiaban(downLoadBean.getPrintJiaban() + 1);
			DownLoadSave.objectToFile(downLoadBean);
		}else{
			type = "tiaoxiu";
			DownLoadBean downLoadBean = DownLoadSave.fileToObject();
			if(downLoadBean == null){
				downLoadBean = new DownLoadBean();
				downLoadBean.setDownLoadJiaban(0);
				downLoadBean.setDownLoadTiaoxiu(0);
				downLoadBean.setPrintJiaban(0);
				downLoadBean.setPrintTiaoxiu(0);
			}
			downLoadBean.setPrintTiaoxiu(downLoadBean.getPrintTiaoxiu() + 1);
			DownLoadSave.objectToFile(downLoadBean);
		}
	%>	
  </head>
  
  <body>
    <div id="printDiv">
    		<img src="img/<%=type %>.jpg" style="width:99%;overflow:hidden"/>
    </div>
  </body>
  <script type="text/javascript">
  	window.onload=function(){
	  	$("#printDiv").printArea(); 
  	}
  </script>
</html>
