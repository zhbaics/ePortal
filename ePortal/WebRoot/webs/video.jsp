<%@ page contentType="text/html; charset=gbk"%>
<%@include file="../common/web_head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  xml:lang="zh-CN" lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>��Ƶ����-<s:text name="website_name"/></title>
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
			�����ڵ�λ�ã�<a href="<%=basepath%>/webs/index.jsp">��ҳ</a> >> ��Ƶ����
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
<!-- ��Ƶ������ -->	
		<div class="video">
			<div id="flvcontainer"><a href="http://www.macromedia.com/go/getflashplayer">������Flash������</a>���Ÿ���Ƶ!</div>
			<script type="text/javascript" src="http://localhost:8080/red5/swfobject.js"></script>
			<script type="text/javascript">
				var so = new SWFObject('http://localhost:8080/red5/mediaplayer.swf','mpl','440','310','7');
				so.addParam('allowfullscreen','true');
				so.addParam('allowscriptaccess','sameDomain');
				so.addVariable('width','440');
				so.addVariable('height','310');
				so.addVariable('file','rtmp://localhost/oflaDemo');
				so.addVariable('id','test');
				so.addVariable('type','rtmp');	
				so.addVariable("image","http://localhost:8080/red5/cover.jpg");
				so.write("flvcontainer");
				
				//����ָ����flv�ļ�
				function playFlv(pic,flv){
					so.addVariable('id',flv);
					so.addVariable("image",pic);
					so.addVariable('autostart','true');
					so.write("flvcontainer");					
				}					
			</script>		
		</div>
<!-- �����̳ǿ�Ѷ�� -->	
		<div class="news">
			<div class="news_head_left"></div>
			<div class="news_head_text"><h2>�̳ǿ�Ѷ</h2></div>
			<div class="news_head_main"></div>
			<div class="news_head_right"></div>
			<div class="videonews_body">
				<!-- ʹ���Զ����ı������б��ǩ -->
				<e:textnews section="SCKX" newstype="0" number="10" prex="0" titlelen="16" baseurl="<%=basepath%>"/>				
			</div>
		</div>
<!-- �������� -->	
		<div class="split"></div>
<!-- ��Ƶ��Ʒ�� -->	
		<div class="videolist">
			<div class="mer_head_left"></div>
			<div class="mer_head_text"><h2>��Ƶר��</h2></div>
			<div class="mer_head_main"></div>
			<div class="mer_head_right"></div>
			<div class="videolist_body">
				<!-- ʹ���Զ�����Ƶ��Ʒ�б��ǩ -->
				<e:videolist baseurl="<%=basepath%>"/>								
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
