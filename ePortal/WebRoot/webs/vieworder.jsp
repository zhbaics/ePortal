<%@ page contentType="text/html; charset=gbk"%>
<%@include file="../common/web_head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  xml:lang="zh-CN" lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>�鿴��������-<s:text name="website_name"/></title>
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
			�����ڵ�λ�ã�<a href="<%=basepath%>/webs/index.jsp">��ҳ</a> >> �鿴��������</div>
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
<!-- ���������� -->	
		<div class="cart">
			<div class="mer_head_left"></div>
			<div class="mer_head_text"><h2>�鿴��������</h2></div>
			<div class="mer_head_main"></div>
			<div class="mer_head_right"></div>
			<div class="addorder_body">
				<s:if test="#session.member==null">
					<center>
						<br/><img hspace="5" src="<%=basepath%>/images/sorry.gif" />
						<br/><span class="errorMessage"><s:text name="orders_add_notlogin"/></span>					
					</center>
				</s:if>
				<s:else>
					<br/>
					<table width="94%" border="0" cellpadding="0" cellspacing="0" bgcolor="#F7F3F7" align="center">
					  <tr height="50" >
						<td colspan="2" class="titleText" align="center">����������Ϣ</td>		
					  </tr>
					  <tr height="30" bgcolor="#FFFFFF">
						<td width="146" align="right" class="blackbold">������ţ�</td>
						<td><s:property value="orderNo"/></td>							
					  </tr>
					  <tr height="30" bgcolor="#F7F3F7">
						<td width="146" align="right" class="blackbold">��&nbsp;��&nbsp;�ˣ�</td>
						<td><s:property value="member.memberName"/></td>							
					  </tr>
					  <tr height="30" bgcolor="#FFFFFF">
						<td width="146" align="right" class="blackbold">��ϵ�绰��</td>
						<td><s:property value="member.phone"/></td>							
					  </tr>
					  <tr height="30" bgcolor="#F7F3F7">
						<td width="146" align="right" class="blackbold">�ջ���ַ��</td>
						<td><s:property value="member.address"/></td>							
					  </tr>
					  <tr height="30" bgcolor="#FFFFFF">
						<td width="146" align="right" class="blackbold">�������룺</td>
						<td><s:property value="member.zip"/></td>							
					  </tr>
					  <tr height="30" bgcolor="#F7F3F7">
						<td width="146" align="right" class="blackbold">�µ����ڣ�</td>
						<td><s:property value="@com.eportal.util.Tools@formatDate(#request.orderDate)"/></td>							
					  </tr>
					  <tr height="30" bgcolor="#FFFFFF">
						<td width="146" align="right" class="blackbold">����״̬��</td>
						<td>
							<s:if test="orderStatus==1">���µ�,δ����</s:if>
							<s:elseif test="orderStatus==2">������,δ����</s:elseif>
							<s:elseif test="orderStatus==3">�Ѵ���,δ�ᵥ</s:elseif>
							<s:elseif test="orderStatus==4">�ѽᵥ</s:elseif>							
						</td>							
					  </tr>					  					  
					  <tr height="50">
						<td colspan="2" class="titleText" align="center">������Ʒ�嵥</td>		
					  </tr>					    					  
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					  <tr>
						<td></td>
					  </tr>
					  <tr>
						<td align="center">
							<table cellspacing="1" cellpadding="0" width="94%" border="0" bgcolor="#F7F3F7">
							  <tr height="30" class="blackbold" align="center">
								<td>��Ʒ����</td>
								<td>�г���</td>
								<td>��Ա��</td>
								<td>����</td>
								<td>���</td>
							  </tr>
							  <s:iterator value="selList" id="row" status="st">
							  	<s:if test="#st.odd"><!-- ������,����Ϊ��ɫ -->
							  		<tr class="text" align="center" bgcolor="#FFFFFF" height="30">
							  	</s:if>
							  	<s:else><!-- ż����,����Ϊǳ��ɫ -->
							  		<tr class="text" align="center" bgcolor="#F7F3F7" height="30">
							  	</s:else>
									<td>
										&nbsp;<a href="<%=basepath%>${row.merchandise.htmlPath}" target="_blank"> 
										  <span class="blueText">${row.merchandise.merName}</span>
										</a>							</td>
									<td><s:property value="@com.eportal.util.Tools@formatCcurrency(#row.merchandise.price)"/></td>
									<td><s:property value="@com.eportal.util.Tools@formatCcurrency(#row.price)"/></td>
									<td><s:property value="#row.number"/></td>
									<td><s:property value="@com.eportal.util.Tools@formatCcurrency(#row.money)"/></td>
								  </tr>						  	
							  </s:iterator>							  						  						  
							  <tr align="center" height="30">
								<td colspan="5" class="blackbold"><img hspace="5" src="<%=basepath%>/images/me03.gif" align="absmiddle" /> 
									�����ܽ��:<s:property value="@com.eportal.util.Tools@formatCcurrency(#request.cart.money)"/>(���������ͷ���)							</td>
							  </tr>		
							</table>
						</td>
					  </tr>
					  <tr align="center">
						<td><br/>
							<img style="cursor:pointer" onClick="window.location='loadAllOrders.action';" src="<%=basepath%>/images/return.gif"  border="0"/></td>
					  </tr>
					</table>
				</s:else>				
			</div>
		</div>
	</div>
<!-- �������� -->	
	<div class="split"></div>	
<!-- ҳ���� -->			
	<%@include file="../common/foot.jsp"%>						
</div>
</body>
</html>
