package com.eportal.tld;

import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/** FLV���߲������Զ����ǩ�� */
public class FlvPlayerTag extends SimpleTagSupport {

	String red5FullUrl;   //red5������Ӧ�õ���������·��
	String red5ServerIp;  //red5��������IP��ַ
	String width = "400"; //���������,��λ:����,Ĭ��400
	String height = "300";//�������߶�,��λ:����,Ĭ��300
	String flvFilename;	  //FLV�ļ���
	String picFilename;	  //����������ͼƬ�ļ�,Ĭ��Ϊred5������Ӧ���µ�cover.jpg	
	
	/** ��ǩ�崦�� */
    public void doTag() throws JspException, IOException{
    	//�淶����ֵ
    	if (!red5FullUrl.endsWith("/"))red5FullUrl=red5FullUrl+"/";
    	if (flvFilename.indexOf(".")!=-1)flvFilename = flvFilename.split("\\.")[0];
    	if (picFilename==null)picFilename=red5FullUrl+"cover.jpg";
    	else{
    		picFilename = ((HttpServletRequest)((PageContext)getJspContext()).getRequest()).getContextPath()+picFilename;
    	}    	
    	
    	//����FLV���߲�����
    	StringBuffer sb = new StringBuffer();
    	sb.append("<center><div id='flvcontainer'>��<a href='http://www.macromedia.com/go/getflashplayer'>����Flash������</a>���Ŵ�ӰƬ!</div></center>\n");
    	sb.append("<script type='text/javascript' src='"+red5FullUrl+"swfobject.js'></script>\n");
    	sb.append("<script type='text/javascript'>\n");
    	sb.append("	var so = new SWFObject('"+red5FullUrl+"mediaplayer.swf','mpl','"+width+"','"+height+"','7');\n");
    	sb.append("	so.addParam('allowfullscreen','true');\n");
    	sb.append("	so.addParam('allowscriptaccess','sameDomain');\n");
    	sb.append("	so.addVariable('width','"+width+"');\n");
    	sb.append("	so.addVariable('height','"+height+"');\n");
    	sb.append("	so.addVariable('file','rtmp://"+red5ServerIp+"/oflaDemo');\n");
    	sb.append("	so.addVariable('id','"+flvFilename+"');\n");
    	sb.append("	so.addVariable('type','rtmp');	\n");
    	sb.append("	so.addVariable('image','"+picFilename+"');\n");
    	sb.append("	so.write('flvcontainer');	\n");
    	sb.append("</script>\n");
    	//�����������ҳ����
    	getJspContext().getOut().println(sb);    	
    }

	public String getRed5FullUrl() {
		return red5FullUrl;
	}

	public void setRed5FullUrl(String red5FullUrl) {
		this.red5FullUrl = red5FullUrl;
	}

	public String getRed5ServerIp() {
		return red5ServerIp;
	}

	public void setRed5ServerIp(String red5ServerIp) {
		this.red5ServerIp = red5ServerIp;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getFlvFilename() {
		return flvFilename;
	}

	public void setFlvFilename(String flvFilename) {
		this.flvFilename = flvFilename;
	}

	public String getPicFilename() {
		return picFilename;
	}

	public void setPicFilename(String picFilename) {
		this.picFilename = picFilename;
	}
}
