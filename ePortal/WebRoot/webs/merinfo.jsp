<%@ page contentType="text/html; charset=gbk"%>
<%@page import="com.eportal.ORM.Merchandise"%>
<%@page import="com.eportal.util.Tools"%>
<%@page import="com.eportal.service.*"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="java.util.*"%>
<%@include file="../common/web_head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  xml:lang="zh-CN" lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title><s:property value="merName"/>-<s:text name="website_name"/></title>
<base target="_blank" />
<meta name="Keywords" content='<s:property value="merName"/>' />
<meta name="Description" content='<s:property value="merName"/>,<s:property value="merModel"/>' />
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
			�����ڵ�λ�ã�<a href="<%=basepath%>/webs/index.jsp">��ҳ</a> >> ��Ʒ����
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
		<div class="merinfo">
			<div class="baseinfo">
				<s:if test="picture!=null">
					<%
						//����ƷͼƬ�Ŀ����߶Ƚ��з�Χ����,�����������ҳ��
						String filename=request.getRealPath(((Merchandise)request.getAttribute("model")).getPicture()).replaceAll("\\\\","/");
						Map map = Tools.getPicWidthHeight(filename); 
						String height_width ="height='240'";
						if (map!=null){
							int width = Integer.valueOf(map.get("width").toString());
							int height = Integer.valueOf(map.get("height").toString());
							if (width>=height){//����ͼƬ,���ƿ�ȼ���
								height_width ="width='240'";
							}else{//����ͼƬ,���Ƹ߶ȼ���
								height_width ="height='240'";
							}
						}						
					%>
					<img src="<%=basepath%>/<s:property value="picture"/>" <%=height_width%> style="float:left;margin:10px;"/>									
				</s:if>
				<s:else>
					<img src="<%=basepath%>/images/noimages.gif"/>" height="240" style="float:left;margin:10px;"/>
				</s:else>							
				<span class="merfield">��Ʒ����:</span> <s:property value="merName"/><br/>
				<span class="merfield">��Ʒ�ͺ�:</span> <s:property value="merModel"/><br/>
				<span class="merfield">�г��۸�:</span> <s:property value="@com.eportal.util.Tools@formatCcurrency(#request.price)"/>Ԫ<br/>
				<span class="merfield">��Ա�۸�:</span> 
				<s:if test="special==1">
					<s:property value="@com.eportal.util.Tools@formatCcurrency(#request.sprice)"/>Ԫ<br/>
				</s:if>	
				<s:else>
					<%
		        		//�����Ա��,��Ա��Ϊ����Żݻ�Ա��
		        		double tmpprice = 0;
		        		Merchandise mer = (Merchandise)request.getAttribute("model");
		        		MemberLevelService memberLevelService = (MemberLevelServiceImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean("memberLevelService");
						tmpprice = mer.getPrice()*(100-memberLevelService.getInitMemberlevel().getFavourable().intValue())/100;
					%>
					<%=Tools.formatCcurrency(tmpprice)%>Ԫ<br/>
				</s:else>	
				<span class="merfield">��������:</span> <s:property value="%{#special==1?'��':'��'}"/><br/>
				<span class="merfield">��������:</span> <s:property value="manufacturer"/><br/>
				<span class="merfield">��������:</span> <s:property value="leaveFactoryDate"/><br/>	
				<span class="merfield">��Ʒ��Ƶ:</span> 
				<s:if test="video!=null">
					<img src="<%=basepath%>/images/play.gif" style="vertical-align:middle;cursor:pointer;" onclick="playflv()"/><br/>
				</s:if>
				<s:else>��<br/></s:else>
				<div class="merfield1">��Ҫ����: <img src="<%=basepath%>/images/icon_buy.gif" style="vertical-align:middle;"/></div>											
		    </div>
			<div class="otherinfo">
				<span class="merfield">��Ʒ����:</span><br/>
				<s:property escape="false" value="@com.eportal.util.Tools@unescape(#request.merDesc)"/>		
			</div>		
		</div>
	</div>		
<!-- �������� -->	
	<div class="split"></div>	
<%@include file="../common/foot.jsp"%>
</div>
<!-- ���ô����ֵ�FLV���߲����� -->
<span id="flvspan" style="display:none">
	<e:msgdialog basepath="<%=basepath%>" title="������Ƶ����" height="1020" top="140" boxwidth="460" tmpid="flv">
		<!-- FLV���߲����� -->
		<e:flvplayer red5FullUrl="http://localhost:8080/red5" red5ServerIp="localhost" flvFilename="${video}" picFilename="${picture}"/>
	</e:msgdialog>
</span>
<script language='javascript'>
	//����flv��Ƶ
	function playflv(){
		document.getElementById('flvspan').style.display='inline';
		document.getElementById('maskflv').style.display='inline';
		document.getElementById('msgpromptflv').style.display='inline';
	}	
	//�رմ����ֵ���ҳ�Ի���
	closemaskflv();
</script>
</body>
</html>
