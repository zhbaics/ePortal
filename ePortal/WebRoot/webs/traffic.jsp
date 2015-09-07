<%@ page contentType="text/html; charset=gbk"%>
<%@ page import="com.eportal.DAO.*" %>
<%@ page import="com.eportal.ORM.Traffic" %>
<%@ page import="java.sql.*" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%
	//ʹ��WebApplicationContextUtils�������ȡSpring IOC�����е�daoʵ��
	BaseDAO dao = (BaseDAOImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext()).getBean("dao");
	String ip="";//IP��ַ
	double ipvalue = 0;
	String fromUrl="";//��·URL
	String toUrl="";//�ܷ�URL
	String address="";//IP������	
	if(request.getRemoteAddr()!=null){
		ip=request.getRemoteAddr();//ȡ��IP��ַ
		//����IP��ַ����Ӧ����ֵ
		String[] tmp = ip.split("\\.");
		ipvalue = Double.parseDouble(tmp[0])*256*256*256+Double.parseDouble(tmp[1])*256*256+Double.parseDouble(tmp[2])*256+Double.parseDouble(tmp[3]);
	}
	if(request.getParameter("fromUrl")!=null && request.getParameter("fromUrl").length()>0){
		fromUrl=request.getParameter("fromUrl");//ȡ����·URL
	}else{
		fromUrl="ֱ�ӽ���";
	}
	if(request.getParameter("toUrl")!=null && request.getParameter("toUrl").length()>0){
		toUrl=request.getParameter("toUrl");//ȡ���ܷ�URL
	}
	if(ip.startsWith("192")||ip.startsWith("127")){
		address="����";
	}else{
		//ͨ��JDBC����MySQL�洢����pro_iptoaddressȡ��IP������
		Connection conn = dao.getConnection();
		CallableStatement stmt = conn.prepareCall("{call pro_iptoaddress(?,?)}");
		stmt.setDouble(1,ipvalue);
		stmt.registerOutParameter(2, Types.VARCHAR);
	    stmt.execute();
	    address= stmt.getString(2);
	    if(address==null||address.length()<1)address="δ֪����";	
	}
	//������ʼ�¼
	Traffic traffic = new Traffic();
	traffic.setIp(ip);
	traffic.setSourceUrl(fromUrl);
	traffic.setTargetUrl(toUrl);
	traffic.setArea(address);
	traffic.setVisitDate(new java.util.Date());
	dao.saveOrUpdate(traffic);	
%>