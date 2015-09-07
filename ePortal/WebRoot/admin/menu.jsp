<%@ page contentType="text/html; charset=gbk"%>
<%@include file="../common/admin_head.jsp"%>
<html>
<head>
<title><s:text name="admin_title"/></title>
<s:head theme="ajax" debug="true"/>
<link href="<%=basepath%>/css/admin.css" rel="stylesheet" type="text/css" />
 <script language="JavaScript" type="text/javascript">
 	var url = "#";
 	var root;
 	
	//��Ӧ�˵������¼�
	function treeNodeSelected(arg) {
		var node = dojo.widget.byId(arg.source.widgetId);
		if(node.isFolder){
			if(dojo.widget.byId(arg.source.widgetId).isExpanded){
				dojo.widget.byId(arg.source.widgetId).collapse();
			}else{
				dojo.widget.byId(arg.source.widgetId).expand();
			}		
		}else{
			processSelected(arg.source.widgetId);
		}
	}
	
	//����˵�����
	function processSelected(menuid){
		var tmp = (new Date()).getTime();
		if (menuid=='column'){
			url = "columns_browseColumns.action";
		}else if (menuid=='news'){
			url = "news_browseNews.action";
		}else if (menuid=='crawl'){
			url = "rule_browseNewsrule.action";
		}else if (menuid=='memeberLevel'){
			url = "level_browseMemberlevel.action";
		}else if (menuid=='memeber'){
			url = "member_browseMember.action";
		}else if (menuid=='cate'){
			url = "cate_browseCategory.action";
		}else if (menuid=='mer'){
			url = "mer_browseMerchandise.action";
		}else if (menuid=='order'){
			url = "orders_browseOrders.action";		
		}else if (menuid=='ip'){
			url = "browseIP_index.jsp";		
		}else if (menuid=='pv'){
			url = "browsePV_index.jsp";	
		}else if (menuid=='admin'){
			url = "admin_browseAdmin.action";	
		}else if (menuid=='exit'){
			url = "admin_logout.action";	
		}
		//�����ʱ����,��ʶ����һ��ȫ�µ�����
		url = "<%=basepath%>/admin/"+url+"?tmp="+tmp;
		if (menuid=='exit'){
			window.parent.location=url;
		}else{
			window.parent.mainFrame.location=url;
		}		
	}
	
	//��Ӧ�˵�չ���¼�
	function treeNodeExpanded(arg) {
	    alert('id['+arg.source.widgetId+'], name['+ arg.source.title+ '] expanded');
	}
	
	//��Ӧ�˵������¼�
	function treeNodeCollapsed(arg) {
	    alert('id['+arg.source.widgetId+'], name['+ arg.source.title+ '] collapsed');
	}
	
	//ע��˵��¼�����
	dojo.addOnLoad(function(){
	    root = dojo.widget.byId('adminctrl');
	    dojo.event.topic.subscribe(root.eventNames.titleClick, treeNodeSelected);
	});
	
	//չ�����в˵���
	function expandAll(){
       for(var i=0; i<root.children.length; i++) {
          var child = root.children[i];
          dojo.lang.forEach(child.getDescendants(),function(node) {node.expand(); });
       }
	}
</script>
</head>
<body style="padding:10px;">
<s:tree label="<b>ePortal��̨����</b>" id="adminctrl" theme="ajax" showRootGrid="true" showGrid="true">
    <s:treenode theme="ajax" label="<img src='../images/icon_newscolumn.gif'/>���Ź���" id="news_column">
        <s:treenode theme="ajax" label="<img src='../images/icon_column.gif'/>������Ŀ����" id="column"/>
        <s:treenode theme="ajax" label="<img src='../images/icon_news.gif'/>���Ź���" id="news"/>
    </s:treenode>
    <s:treenode theme="ajax" label="<img src='../images/icon_crawl.gif'/>���Ųɼ�" id="crawl"/>
    <s:treenode theme="ajax" label="<img src='../images/icon_member.gif'/>��Ա����" id="memeber_level">
    	<s:treenode theme="ajax" label="<img src='../images/icon_level.gif'/>��Ա�������" id="memeberLevel"/>
    	<s:treenode theme="ajax" label="<img src='../images/icon_member.gif'/>��Ա����" id="memeber"/>    
    </s:treenode>
    <s:treenode theme="ajax" label="<img src='../images/icon_catemer.gif'/>��Ʒ����" id="cate_mer">
        <s:treenode theme="ajax" label="<img src='../images/icon_cate.gif'/>��Ʒ����" id="cate"/>
        <s:treenode theme="ajax" label="<img src='../images/icon_mer.gif'/>��Ʒ����" id="mer"/>
    </s:treenode>
    <s:treenode theme="ajax" label="<img src='../images/icon_order.gif'/>��������" id="order"/>
    <s:treenode theme="ajax" label="<img src='../images/icon_trafic.gif'/>����ͳ��" id="ip_pv">
        <s:treenode theme="ajax" label="<img src='../images/icon_ip.gif'/>IPͳ��" id="ip"/>
        <s:treenode theme="ajax" label="<img src='../images/icon_pv.gif'/>PVͳ��" id="pv"/>
    </s:treenode>    
    <s:treenode theme="ajax" label="<img src='../images/icon_admin.gif'/>ϵͳ�û�����" id="admin"/>
    <s:treenode theme="ajax" label="<img src='../images/icon_exit.gif'/>��ȫ�˳�" id="exit"/>    
</s:tree>
<br/>
</body>
<script type="text/javascript">
	//չ�����в˵���
	window.setTimeout("expandAll();",2000);
</script>
</html>