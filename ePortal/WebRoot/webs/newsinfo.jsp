<%@ page contentType="text/html; charset=gbk"%>
<%@include file="../common/web_head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  xml:lang="zh-CN" lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title><s:property value="title"/>-<s:text name="website_name"/></title>
<base target="_blank" />
<meta name="Keywords" content='<s:property value="keyWords"/>' />
<meta name="Description" content='<s:property value="abstract_"/>' />
<meta name="robots" content="index, follow" />
<meta name="googlebot" content="index, follow" />
<link href="<%=basepath%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basepath%>/js/json.js"></script>
<script type="text/javascript" src="<%=basepath%>/js/prototype-1.4.0.js"></script>
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
			�����ڵ�λ�ã�<a href="<%=basepath%>/webs/index.jsp">��ҳ</a> >> <a href="<%=basepath%>/webs/news.jsp">�̳ǿ�Ѷ</a> >> ��������
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
		<div class="newsinfo">
			<div class="newsinfo_title">
				<h1><s:property value="title"/></h1>
				���ߣ�<s:property value="author"/>&nbsp;&nbsp;
				��Դ��<s:property value="from"/>&nbsp;&nbsp;
				���ڣ�<s:property value="@com.eportal.util.Tools@formatDate(#request.cdate)"/>&nbsp;&nbsp;
				�������<span id="clickcount"></span><br/>
			</div>
			<div id="newsinfo_body">	
				<s:if test="picture!=null">
					<center><img src='<%=basepath%>/<s:property value="picture"/>'/></center><br/>		
				</s:if>
				<s:set name="pages" value="@com.eportal.util.Tools@splitContent(#request.content)"/>
				<s:iterator value="pages" id="page" status="st">
					<s:set name="spanid" value="%{'page'+#st.count}"/>
					<s:if test="#st.count==1">
						<span id='${spanid}'>
							<s:property escape="false" value="page"/>
						</span>							
					</s:if>
					<s:else>
						<span id='${spanid}' style="display:none">
							<s:property escape="false" value="page"/>
						</span>						
					</s:else>
				</s:iterator>
			</div>
			<s:if test="#pages.length>1">
				<!-- �������ݷ�ҳ���� -->	
				<div class="newsinfo_page">[<span class="newsinfo_page_ctl" onclick="firstPage()">��ҳ</span>]&nbsp;&nbsp;[<span class="newsinfo_page_ctl" onclick="prevPage()">��һҳ</span>]&nbsp;&nbsp;[<span class="newsinfo_page_ctl" onclick="nextPage()">��һҳ</span>]&nbsp;&nbsp;[<span class="newsinfo_page_ctl" onclick="endPage()">ĩҳ</span>]</div>
				<script language="javascript">
					//��ҳ��
					var pageCount = <s:property value="#pages.length"/>;
					//��ǰҳ��
					var currentPageNo=1;
					//��ʾָ��ҳ������
					function goPage(pageno){
						for(var i=1;i<=pageCount;i++){
							if (i==pageno){
								document.getElementById("page"+i).style.display="inline";
							}else{
								document.getElementById("page"+i).style.display="none";
							}
						}
					}
					//��ҳ
					function firstPage(){
						currentPageNo=1;
						goPage(currentPageNo);
					}
					//ĩҳ
					function endPage(){
						currentPageNo=pageCount;
						goPage(currentPageNo);
					}
					//��һҳ
					function prevPage(){
						if (currentPageNo>1)currentPageNo=currentPageNo-1;
						goPage(currentPageNo);
					}
					//��һҳ
					function nextPage(){
						if (currentPageNo<pageCount)currentPageNo=currentPageNo+1;
						goPage(currentPageNo);
					}			
				</script>									
			</s:if>
			<div class="about_news">
				<div class="about_news_tile"><h2>�������</h2></div>
				<e:aboutnews keywords="${keyWords}" currentId="${id}" number="6" prex="0" titlelen="16" baseurl="<%=basepath%>"/>
			</div>
		</div>
	</div>		
<!-- �������� -->	
	<div class="split"></div>	
<%@include file="../common/foot.jsp"%>
</div>
<!-- ʹ��JS���Prototype���JSON���ʵ���첽�������һ -->
<script language="JavaScript">
	//�����첽����action�ĺ���
	function incClicks(){
		//���ʵ������һ��incNewsClicks.action
		var url = '../ajax/incNewsClicks.action';
		//��������������˴�����tmp������Ϊ���÷�����֪������һ���µ�����
		var params = 'id=${id}&tmp='+(new Date()).getTime();
		//����Ajax.Request�������ڷ����첽����
		var myAjax = new Ajax.Request(
		url,
		{
			//����ʽ��get
			method:'get',
			//�������
			parameters:params,
			//ָ���ص�����
			onComplete: processResponse,
			//�Ƿ��첽��������
			asynchronous:true
		});
	}
	//�����첽��Ӧ����Ĵ�����
    function processResponse(request){    	
    	var obj = JSON.parse(request.responseText);
    	//����һ��ĵ��������clickcount����ʾ����
		$("clickcount").innerHTML = obj.jsonClicks;
	}
	//����incClicks()����ʵ�ֵ������һ
	incClicks();	
</script>
</body>	
</html>
