<%@ page contentType="text/html; charset=gbk"%>
<%@include file="../common/admin_head.jsp"%>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title><s:text name="admin_title"/></title>
<link href="<%=basepath%>/css/admin.css" rel="stylesheet" type="text/css" />
<script language="JavaScript">
<!--
	//��������ѡ���
	function popup(test){
	  var xx=event.clientX+140;
	  var yy=event.clientY+140;
	  test.value=window.showModalDialog("<%=basepath%>/js/selectDate.htm","","dialogWidth:350px;dialogHeight:180px;dialogLeft:"+xx+"px;dialogTop:"+yy+"px;status:0;");
	}
	
	//��Ӧ"��ѯ��ť"�����¼�
	function query(){
		var hqlstr="select a.ip,max(a.area),max(a.visitDate),count(a.ip) from Traffic as a";
		var where = false;
		if (document.getElementById("keywords").value!=''){
			hqlstr = hqlstr + " where a."+document.getElementById("keyfield").value+" like '%"+document.getElementById("keywords").value+"%'";
			where = true;
		}
		if (document.getElementById("begin").value!=''){
			if (!where){
				hqlstr = hqlstr + " where a.visitDate>='"+document.getElementById("begin").value+" 00:00:00.0'";
				where = true;
			}else{
				hqlstr = hqlstr + " and a.visitDate>='"+document.getElementById("begin").value+" 00:00:00.0'";
			}
		}
		if (document.getElementById("end").value!=''){
			if (!where){
				hqlstr = hqlstr + " where a.visitDate<='"+document.getElementById("end").value+" 23:59:59.999'";
			}else{
				hqlstr = hqlstr + " and a.visitDate<='"+document.getElementById("end").value+" 23:59:59.999'";
			}
		}		
		hqlstr=hqlstr+" group by a.ip order by a.id desc";
		hqlstr = encodeURI(encodeURI(hqlstr));
		window.parent.ipMainFrame.location="traffic_browseIP.action?hql="+hqlstr;
	}
-->
</script>
</head>
<body style="padding-top:10px;">
<center>
	<div class="titleText"><s:text name="traffic_ip"/></div>
	<table width="94%" border="0" cellpadding="0" cellspacing="0">
	  <tr>
   	    <td width="100">��ѡ���ѯ����:</td>
	    <td width="100">
	    	<s:select name="keyfield" list="#{'ip':'IP��ַ','sourceUrl':'��ԴURL','targetUrl':'�ܷ�URL','area':'������'}" listKey="key" listValue="value"/>
	    </td>
	    <td width="50">ʱ���:</td>
	    <td width="100"><input id="begin" type="text" onClick="popup(this)" size="12" readonly="true"></td>
	    <td width="20">��:</td>
	    <td width="100"><input id="end" type="text" onClick="popup(this)" size="12" readonly="true"></td>
	    <td  width="50">�ؼ���:</td>
	    <td><input id="keywords" type="text" size="30"></td>
	    <td><input type="button" value="��ѯ" onclick="query()"/></td>
	  </tr>
	</table>
</center>
</body>
</html>