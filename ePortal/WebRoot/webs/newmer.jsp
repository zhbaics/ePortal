<%@ page contentType="text/html; charset=gbk"%>
<%@include file="../common/web_head.jsp"%>
<%
	int pageNo=1;
	//��ҳ����
	if (request.getParameter("pageNo")!=null){
		pageNo = Integer.parseInt(request.getParameter("pageNo"));
	}	
	//��Ʒ���ർ��
	String cateid="";
	if (request.getParameter("cateid")!=null){
		cateid = request.getParameter("cateid");
	}
	//���칩��ҳ��ǩʹ�õ�URL
	String url=basepath+"/webs/newmer.jsp";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  xml:lang="zh-CN" lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>��Ʒ�ϼ�-<s:text name="website_name"/></title>
<base target="_blank" />
<meta name="Keywords" content="��������,�����̳�,���߹���,���Ϲ���" />
<meta name="Description" content="���������Ż�ePortal����ṩ�����̳����߹����Ʒչʾ������������Ѷ�����ȳ����ĵ���������!" />
<meta name="robots" content="index, follow" />
<meta name="googlebot" content="index, follow" />
<link href="<%=basepath%>/css/style.css" rel="stylesheet" type="text/css" />
<script language='javascript'>
	//ѡ����Ʒ
	function buy(merid){
		<s:if test="#session.member==null">
			alert('�Բ���,����δ��¼,���ȵ�¼����ѡ����Ʒ,лл����!');
		</s:if>
		<s:else>
			window.location='addToCart.action?merId='+merid;
		</s:else>			
	}    	
</script>
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
			�����ڵ�λ�ã�<a href="<%=basepath%>/webs/index.jsp">��ҳ</a> >> ��Ʒ�ϼ�
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
<!-- ������Ʒ�� -->	
		<div class="merlist">
			<div class="mer_head_left"></div>
			<div class="mer_head_text"><h2>��Ʒ�ϼ�</h2></div>
			<div class="mer_head_main"></div>
			<div class="mer_head_right"></div>
			<div class="merlist_body">
				<!-- ʹ���Զ�����Ʒ�б��ǩ -->
				<e:mermore baseurl="<%=basepath%>" pageNo="<%=pageNo%>" cateid="<%=cateid%>"/>
				<div class="page_ctrl">
					<!-- ʹ���Զ�����Ʒ�б��ҳ���Ʊ�ǩ -->
					<e:merpage url="<%=url%>" pageNo="<%=pageNo%>" cateid="<%=cateid%>"/>
				</div>
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
