<%@ page contentType="text/html; charset=gbk"%>
<%@include file="../common/admin_head.jsp"%>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title><s:text name="admin_title"/></title>
<link href="<%=basepath%>/css/admin.css" rel="stylesheet" type="text/css" />
<script language="javascript">
	var teipprim ="";
	//��ԭȨ���б�
	function recoverPrim(temp){
		if (teipprim=='000000000000000'){
			document.all.privileges.value = "";	
			return;	
		}else{
			for (var i=0;i<15;i++){
				if (temp.substring(i,i+1)=="1")document.getElementById("prim"+(i+1)).checked = true;
			}			
		}	
	}
</script>
</head>
<body>
<center>
	<br/><div class="titleText"><s:text name="admin_info"/></div><br/>
	<div class="formDiv">
		<table width="500" align="center" border="0" cellpadding="0" cellspacing="0">
		  <tr>
		    <td align="right"><s:text name="login_label_loginname"/>��</td>
		    <td><s:textfield name="loginName" size="22" disabled="true"/></td>    	    
		  </tr>
		  <tr>
		    <td align="right" valign="top"><s:text name="admin_prim"/>��</td>
		    <td valign="top"><table width="420" cellspacing="0" cellpadding="0">
		        <tr>
		          <td width="20"><s:checkbox id="prim1" name="prim1" disabled="true"/></td>
		          <td>1��ȫȨ</td>
		          <td width="20"><s:checkbox id="prim2" name="prim2" disabled="true"/></td>
		          <td>2��ϵͳ�û�����</td>
		          <td width="20"><s:checkbox id="prim3" name="prim3" disabled="true"/></td>
		          <td>3��������Ŀ����</td>		          
		        </tr>
		        <tr>
		          <td width="20"><s:checkbox id="prim4" name="prim4" disabled="true"/></td>
		          <td>4�����Ź���</td>
		          <td width="20"><s:checkbox id="prim5" name="prim5" disabled="true"/></td>
		          <td>5�����Ųɼ�</td>
		          <td width="20"><s:checkbox id="prim6" name="prim6" disabled="true"/></td>
		          <td>6����Ա����</td>		          
		        </tr>
		        <tr>
		          <td width="20"><s:checkbox id="prim7" name="prim7" disabled="true"/></td>
		          <td>7����Ʒ�������</td>
		          <td width="20"><s:checkbox id="prim8" name="prim8" disabled="true"/></td>
		          <td>8����Ʒ����</td>
		          <td width="20"><s:checkbox id="prim9" name="prim9" disabled="true"/></td>
		          <td>9����������</td>		          
		        </tr>
		        <tr>
		          <td width="20"><s:checkbox id="prim10" name="prim10" disabled="true"/></td>
		          <td>10������ͳ��</td>
		          <td width="20"><s:checkbox id="prim11" name="prim11" disabled="true"/></td>
		          <td>11������һ</td>
		          <td width="20"><s:checkbox id="prim12" name="prim12" disabled="true"/></td>
		          <td>12��������</td>		          
		        </tr>
		        <tr>
		          <td width="20"><s:checkbox id="prim13" name="prim13" disabled="true"/></td>
		          <td>13��������</td>
		          <td width="20"><s:checkbox id="prim14" name="prim14" disabled="true"/></td>
		          <td>14��������</td>
		          <td width="20"><s:checkbox id="prim15" name="prim15" disabled="true"/></td>
		          <td>15��������</td>		          
		        </tr> 		        		        		        		        		        
		      </table></td>
		  </tr>
		</table>
		<br>
	  <div align="center">
		<s:set name="label_return" value="%{getText('label_return')}"/>
	    <input type="button" name="btn_ret" value="${label_return}" onClick="window.location='admin_browseAdmin.action';">
	  </div>
	</div>
</center>
<s:if test="#request.privileges!=null && #request.privileges.length()>0">
	<script language="javascript">
		recoverPrim('${privileges}');
	</script>	
</s:if>
</body>
</html>
