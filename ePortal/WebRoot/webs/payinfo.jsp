<%@ page contentType="text/html; charset=gbk"%>
<%@include file="../common/web_head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  xml:lang="zh-CN" lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>�����ύ�ɹ�-<s:text name="website_name"/></title>
<base target="_blank" />
<meta name="Keywords" content="��������,�����̳�,���߹���,���Ϲ���" />
<meta name="Description" content="���������Ż�ePortal����ṩ�����̳����߹����Ʒչʾ������������Ѷ�����ȳ����ĵ���������!" />
<meta name="robots" content="index, follow" />
<meta name="googlebot" content="index, follow" />
<link href="<%=basepath%>/css/style.css" rel="stylesheet" type="text/css" />
</head>

<body>
<!--DIVҳ��������-->
<div class="container">
<%@include file="../common/menubar.jsp"%>
<!-- �������� -->	
	<div class="split"></div>
<!--λ�õ�����-->	
	<div class="nav">
		<div class="nav_left"></div>
		<div class="nav_main">
			�����ڵ�λ�ã�<a href="<%=basepath%>/webs/index.jsp">��ҳ</a> >> �����ύ�ɹ�</div>
		<div class="nav_now">
			<%@include file="../common/jsdate.jsp"%>	
		</div>
		<div class="nav_right"></div>
	</div>
<!-- �������� -->	
	<div class="split"></div>
<!-- ҳ��������� -->	
	<div class="main_left">
<!-- ��Ʒ������ -->	
		<div class="cate">
			<div class="cate_head_left"></div>
			<div class="cate_head_text"><h2>��Ʒ����</h2></div>
			<div class="cate_head_main"></div>
			<div class="cate_head_right"></div>
			<div class="cate_body">
				<!-- ʹ���Զ�����Ʒ����б��ǩ -->
				<e:category/> 	 				
			</div>
		</div>
<!-- �������� -->	
		<div class="split"></div>		
<!-- ���������� -->	
		<div class="prom">
			<div class="prom_head_left"></div>
			<div class="prom_head_text"><h2>��������</h2></div>
			<div class="prom_head_main"></div>
			<div class="prom_head_right"></div>
			<div class="prom_body">
				<!-- ʹ���Զ�����Ʒ�б��ǩ -->
				<e:merlist baseurl="<%=basepath%>" size="4" listtype="2"/> 			
			</div>
		</div>			
	</div>
<!-- ҳ�������Ҳ� -->	
	<div class="main_right">
<!-- �����ύ�ɹ���ʾ�� -->	
		<div class="cart">
			<div class="mer_head_left"></div>
			<div class="mer_head_text"><h2>�����ύ�ɹ�</h2></div>
			<div class="mer_head_main"></div>
			<div class="mer_head_right"></div>
			<div class="cart_body">
				<s:if test="#session.member==null">
					<center>
						<br/><img hspace="5" src="<%=basepath%>/images/sorry.gif" />
						<br/><span class="errorMessage"><s:text name="orders_add_notlogin"/></span>					
					</center>
				</s:if>
				<s:else>
					<br/>
					<table width="90%" align="center" cellspacing="0" cellpadding="0" border="0">
						<tr valign="center">
						  <td><img hspace="5" src="<%=basepath%>/images/Car_07.gif" /></td>
						  <td class="cart_step_default">1.�鿴���ﳵ��Ʒ</td>
						  <td><img height="39" src="<%=basepath%>/images/Car_15.gif" width="1" /></td>
						  <td align="middle"><img hspace="5" src="<%=basepath%>/images/Car_09.gif" /></td>
						  <td class="cart_step_default">2.ȷ�϶�����Ϣ</td>
						  <td><img height="39" src="<%=basepath%>/images/Car_15.gif" width="1" /></td>
						  <td align="middle"><img hspace="5" src="<%=basepath%>/images/Car_11.gif" /></td>
						  <td class="cart_step_current">3.�����ύ�ɹ�</td>
						</tr>
					</table><br/>				
					<table width="94%" border="0" cellpadding="0" cellspacing="0" bgcolor="#F7F3F7" align="center">
					  <tr height="10" bgcolor="#F7F3F7"><td colspan="4"></td></tr>
					  <tr height="30" bgcolor="#FFFFFF">
					  	<td width="120" rowspan="6" align="right"><img src="<%=basepath%>/images/Car_icon_10.gif"/></td>
						<td align="right" class="blackbold">������ţ�</td>
						<td><s:property value="orderNo"/></td>
						<td rowspan="6" bgcolor="#F7F3F7" width="240">&nbsp;����ʻ���Ϣ��<br/><br/>&nbsp;�����У��й��������й��ݷ��й�ҵ԰֧��<br/><br/>&nbsp;�ʺţ�1234 5678 3214 123456</td>							
					  </tr>
					  <tr height="30" bgcolor="#F7F3F7">
						<td align="right" class="blackbold">��&nbsp;��&nbsp;�ˣ�</td>
						<td><s:property value="member.memberName"/></td>							
					  </tr>
					  <tr height="30" bgcolor="#FFFFFF">
						<td align="right" class="blackbold">��ϵ�绰��</td>
						<td><s:property value="member.phone"/></td>							
					  </tr>
					  <tr height="30" bgcolor="#F7F3F7">
						<td align="right" class="blackbold">�ջ���ַ��</td>
						<td><s:property value="member.address"/></td>							
					  </tr>
					  <tr height="30" bgcolor="#FFFFFF">
						<td align="right" class="blackbold">�������룺</td>
						<td><s:property value="member.zip"/></td>							
					  </tr>
					  <tr height="30" bgcolor="#F7F3F7">
						<td align="right" class="blackbold">������</td>
						<td><s:property value="@com.eportal.util.Tools@formatCcurrency(#request.cart.money)"/></td>							
					  </tr>
					  <tr height="10" bgcolor="#F7F3F7"><td colspan="4"></td></tr>   					  
					</table>
				</s:else>				
			</div>
		</div>
<!-- ������Ʒ�� -->	
		<div class="cartmer">
			<div class="mer_head_left"></div>
			<div class="mer_head_text"><h2>�����ϼ�</h2></div>
			<div class="mer_head_main"></div>
			<div class="mer_head_right"></div>
			<div class="cartmer_body">
				<!-- ʹ���Զ�����Ʒ�б��ǩ -->
				<e:merlist baseurl="<%=basepath%>" size="10" listtype="1" picWidth="94"/>
			</div>
		</div>		
	</div>		
<!-- �������� -->	
	<div class="split"></div>	
<!-- ҳ���� -->			
	<%@include file="../common/foot.jsp"%>						
</div>
<!-- �����ʾ��Ϣ -->
<s:if test="hasActionMessages()">
	<!-- ʹ���Զ����������ҳ�Ի����ǩ -->
	<e:msgdialog basepath="<%=basepath%>" height="1020" top="140">
		<s:actionmessage/>
	</e:msgdialog>
</s:if>	
</body>
</html>
