<%@ page contentType="text/html; charset=gbk"%>
<%@ page import="com.opensymphony.xwork2.util.*" %>
<%@include file="../common/web_head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  xml:lang="zh-CN" lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>�޸�ע������-<s:text name="website_name"/></title>
<base target="_blank" />
<meta name="Keywords" content="��������,�����̳�,���߹���,���Ϲ���" />
<meta name="Description" content="���������Ż�ePortal����ṩ�����̳����߹����Ʒչʾ������������Ѷ�����ȳ����ĵ���������!" />
<meta name="robots" content="index, follow" />
<meta name="googlebot" content="index, follow" />
<link href="<%=basepath%>/css/style.css" rel="stylesheet" type="text/css" />
</head>

<body>
<s:if test="model==null">
<%
	//�ֶ���session�е�memberʵ������ValueStack��,����ҳ���Struts2��ǩֱ�Ӷ�ȡ
	ValueStack vs = (ValueStack)request.getAttribute("struts.valueStack");
	vs.push(session.getAttribute("member"));
%>
</s:if>
<!-- DIVҳ�������� -->
<div class="container">
<%@include file="../common/menubar.jsp"%>
<!-- �������� -->	
	<div class="split"></div>
<!--λ�õ�����-->	
	<div class="nav">
		<div class="nav_left"></div>
		<div class="nav_main">
			�����ڵ�λ�ã�<a href="<%=basepath%>/webs/index.jsp">��ҳ</a> >> �޸�ע������</div>
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
<!-- �޸�ע�������� -->	
		<div class="cart">
			<div class="mer_head_left"></div>
			<div class="mer_head_text"><h2>�޸�ע������</h2></div>
			<div class="mer_head_main"></div>
			<div class="mer_head_right"></div>
			<div class="cart_body">
				<br/>
				<s:form action="/webs/updateMember.action" id="regform" target="_self">				
					<table width="95%" align="center" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
					  <tr bgcolor="#F7F3F7" height="29">
						<td align="right" width="220">��¼�˺ţ�</td>
						<td><s:textfield name="loginName" size="40"/>&nbsp;<span class='redtext'>*</span></td>
					  </tr>
					  <tr bgcolor="#F7F3F7" height="29">
						<td align="right">��¼���룺</td>
						<td><s:password name="loginPwd" size="40"/>&nbsp;<span class='redtext'>������޸�����,����ɲ���</span></td>
					  </tr>
					  <tr bgcolor="#F7F3F7" height="29">
						<td align="right">�˶����룺</td>
						<td><s:password name="againPwd" size="40"/>&nbsp;<span class='redtext'>������޸�����,����ɲ���</span></td>
					  </tr>
					  <tr bgcolor="#F7F3F7" height="29">
						<td align="right">��ʵ������</td>
						<td><s:textfield name="memberName" size="40"/></td>
					  </tr>
					  <tr bgcolor="#F7F3F7" height="29">
						<td align="right">��ϵ�绰��</td>
						<td><s:textfield name="phone" size="40"/></td>
					  </tr>
					  <tr bgcolor="#F7F3F7" height="29">
						<td align="right">��ϵ��ַ��</td>
						<td><s:textfield name="address" size="40"/></td>
					  </tr>
					  <tr bgcolor="#F7F3F7" height="29">
						<td align="right">�������룺</td>
						<td><s:textfield name="zip" size="40"/></td>
					  </tr>
					  <tr bgcolor="#F7F3F7" height="29">
						<td align="right">�������䣺</td>
						<td><s:textfield name="email" size="40"/></td>
					  </tr>
					  <tr>
						<td height="40" colspan="2" align="center">
							<s:submit type="image" src="../images/submit.gif"/>&nbsp;&nbsp;
							<input type="image" src="../images/reset.gif"  onclick="regform.reset(); return false;"/>
						</td>
					  </tr>
					</table>
					<s:hidden name="id"/>
				</s:form>				
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
<!-- �������ʾ��Ϣ -->
<s:if test="hasFieldErrors()">
	<!-- ʹ���Զ����������ҳ�Ի����ǩ -->
	<e:msgdialog basepath="<%=basepath%>" height="1020" top="140">
		<s:fielderror/>
	</e:msgdialog>
</s:if>
<s:if test="hasActionMessages()">
	<!-- ʹ���Զ����������ҳ�Ի����ǩ -->
	<e:msgdialog basepath="<%=basepath%>" height="1020" top="140">
		<s:actionmessage/>
	</e:msgdialog>
</s:if>	
</body>
</html>
