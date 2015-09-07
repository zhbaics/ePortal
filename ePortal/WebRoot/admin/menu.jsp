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
 	
	//响应菜单单击事件
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
	
	//处理菜单导航
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
		//添加临时参数,标识这是一次全新的请求
		url = "<%=basepath%>/admin/"+url+"?tmp="+tmp;
		if (menuid=='exit'){
			window.parent.location=url;
		}else{
			window.parent.mainFrame.location=url;
		}		
	}
	
	//响应菜单展开事件
	function treeNodeExpanded(arg) {
	    alert('id['+arg.source.widgetId+'], name['+ arg.source.title+ '] expanded');
	}
	
	//响应菜单收缩事件
	function treeNodeCollapsed(arg) {
	    alert('id['+arg.source.widgetId+'], name['+ arg.source.title+ '] collapsed');
	}
	
	//注册菜单事件处理
	dojo.addOnLoad(function(){
	    root = dojo.widget.byId('adminctrl');
	    dojo.event.topic.subscribe(root.eventNames.titleClick, treeNodeSelected);
	});
	
	//展开所有菜单项
	function expandAll(){
       for(var i=0; i<root.children.length; i++) {
          var child = root.children[i];
          dojo.lang.forEach(child.getDescendants(),function(node) {node.expand(); });
       }
	}
</script>
</head>
<body style="padding:10px;">
<s:tree label="<b>ePortal后台管理</b>" id="adminctrl" theme="ajax" showRootGrid="true" showGrid="true">
    <s:treenode theme="ajax" label="<img src='../images/icon_newscolumn.gif'/>新闻管理" id="news_column">
        <s:treenode theme="ajax" label="<img src='../images/icon_column.gif'/>新闻栏目管理" id="column"/>
        <s:treenode theme="ajax" label="<img src='../images/icon_news.gif'/>新闻管理" id="news"/>
    </s:treenode>
    <s:treenode theme="ajax" label="<img src='../images/icon_crawl.gif'/>新闻采集" id="crawl"/>
    <s:treenode theme="ajax" label="<img src='../images/icon_member.gif'/>会员管理" id="memeber_level">
    	<s:treenode theme="ajax" label="<img src='../images/icon_level.gif'/>会员级别管理" id="memeberLevel"/>
    	<s:treenode theme="ajax" label="<img src='../images/icon_member.gif'/>会员管理" id="memeber"/>    
    </s:treenode>
    <s:treenode theme="ajax" label="<img src='../images/icon_catemer.gif'/>商品管理" id="cate_mer">
        <s:treenode theme="ajax" label="<img src='../images/icon_cate.gif'/>商品分类" id="cate"/>
        <s:treenode theme="ajax" label="<img src='../images/icon_mer.gif'/>商品管理" id="mer"/>
    </s:treenode>
    <s:treenode theme="ajax" label="<img src='../images/icon_order.gif'/>订单管理" id="order"/>
    <s:treenode theme="ajax" label="<img src='../images/icon_trafic.gif'/>流量统计" id="ip_pv">
        <s:treenode theme="ajax" label="<img src='../images/icon_ip.gif'/>IP统计" id="ip"/>
        <s:treenode theme="ajax" label="<img src='../images/icon_pv.gif'/>PV统计" id="pv"/>
    </s:treenode>    
    <s:treenode theme="ajax" label="<img src='../images/icon_admin.gif'/>系统用户管理" id="admin"/>
    <s:treenode theme="ajax" label="<img src='../images/icon_exit.gif'/>安全退出" id="exit"/>    
</s:tree>
<br/>
</body>
<script type="text/javascript">
	//展开所有菜单项
	window.setTimeout("expandAll();",2000);
</script>
</html>