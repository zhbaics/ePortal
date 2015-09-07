<%@ page contentType="text/html; charset=gbk"%>
<%@include file="../common/web_head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  xml:lang="zh-CN" lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>��������-<s:text name="website_name"/></title>
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
			�����ڵ�λ�ã�<a href="<%=basepath%>/webs/index.jsp">��ҳ</a> >> ��������
		</div>
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
			<div class="mer_head_text"><h2>��������</h2></div>
			<div class="mer_head_main"></div>
			<div class="mer_head_right"></div>
			<div class="order_body">
				<s:if test="#session.member==null">
					<center>
						<br/><img hspace="5" src="<%=basepath%>/images/sorry.gif" />
						<br/><span class="errorMessage"><s:text name="orders_add_notlogin"/></span>					
					</center>
				</s:if>
				<s:else>
					<br/>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					  <tr>
						<td></td>
					  </tr>
					  <tr>
						<td align="center">
							<table cellspacing="1" cellpadding="0" width="94%" border="0" bgcolor="#F7F3F7">
							  <tr height="26" class="blackbold" align="center">
								<td>�������</td>
								<td>�������</td>
								<td>�µ�����</td>
								<td>����״̬</td>
								<td>�鿴</td>
								<td>�ᵥ</td>
								<td>����</td>
							  </tr>							  
							  <s:iterator value="ordersList" id="row" status="st">
							  	<s:if test="#st.odd"><!-- ������,����Ϊ��ɫ -->
							  		<tr class="text" align="center" bgcolor="#FFFFFF">
							  	</s:if>
							  	<s:else><!-- ż����,����Ϊǳ��ɫ -->
							  		<tr class="text" align="center" bgcolor="#F7F3F7">
							  	</s:else>
									<td>&nbsp;${row.orderNo}</td>
									<td><s:property value="@com.eportal.util.Tools@formatCcurrency(#row.cart.money)"/></td>
									<td><s:property value="@com.eportal.util.Tools@formatDate(#row.orderDate)"/></td>
									<td>
										<s:if test="#row.orderStatus==1">���µ�,δ����</s:if>
										<s:elseif test="#row.orderStatus==2">������,δ����</s:elseif>
										<s:elseif test="#row.orderStatus==3">�Ѵ���,δ�ᵥ</s:elseif>
										<s:elseif test="#row.orderStatus==4">�ѽᵥ</s:elseif>										
									</td>
									<td><img style="cursor:pointer" onClick="window.location='viewOrders.action?id=${id}';" src="<%=basepath%>/images/view.gif" border="0"/></td>
									<td>
										<s:if test="#row.orderStatus==3">
											<img style="cursor:pointer" onClick="window.location='updateOrdersStatus.action?id=${id}&orderStatus=4';" src="<%=basepath%>/images/edit.gif" border="0"/>
										</s:if>
										<s:else><img src="<%=basepath%>/images/edit1.gif" border="0"/></s:else>
									</td>									
									<td>
										<s:if test="#row.orderStatus==1">
											<img style="cursor:pointer" onClick="window.location='delOrders.action?id=${id}';" src="<%=basepath%>/images/delete_01.gif" border="0"/>
										</s:if>
										<s:else><img src="<%=basepath%>/images/delete_02.gif" border="0"/></s:else>
									</td>
								  </tr>
							  </s:iterator>
							  <s:if test="ordersList==null || ordersList.size<1">
								  <tr height="26" class="blackbold" align="center" bgcolor="#FFFFFF">
									<td colspan="7" class="redtext" align="left">&nbsp;�Բ���,��Ŀǰû�ж�������!</td>
								  </tr>							  	
							  </s:if>							  	
							</table>
						</td>
					  </tr>
					</table>					
				</s:else>
			</div>
		</div>
<!-- ������Ʒ�� -->	
		<div class="mer">
			<div class="mer_head_left"></div>
			<div class="mer_head_text"><h2>�����ϼ�</h2></div>
			<div class="mer_head_main"></div>
			<div class="mer_head_right"></div>
			<div class="mer_body">
				<!-- ʹ���Զ�����Ʒ�б��ǩ -->
				<e:merlist baseurl="<%=basepath%>" size="10" listtype="1" picWidth="120"/> 
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
