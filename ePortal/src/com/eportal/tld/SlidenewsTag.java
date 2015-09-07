package com.eportal.tld;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.eportal.ORM.*;
import com.eportal.DAO.*;
import com.eportal.util.*;

/** �õ�Ƭ�����б��Զ����ǩ�� */
public class SlidenewsTag extends SimpleTagSupport {
	BaseDAO dao = null; 	//���ݿ�DAO�ӿ�
	News obj= null;
	String hql= null;
	String newstype="4";	//��������:0-�������� 1-ͷ������ 2-�ȵ����� 3-�����Ƽ�  4-��������
	String section;	 		//������Ŀ���,�����Ŀ����м��ú���ָ�,��:I_001,I_002
	int number;    			//�������������
	int width;     			//�õ�����Ŀ��
	int height;    			//�õ�����ĸ߶�
	int titlelen=20;   		//��������
	String baseurl =""; 	//����URL
	String slideno ="1"; 	//�ڱ�ҳ�е�ǰ�õ��������
	
	/** ��ǩ�崦�� */
    public void doTag() throws JspException, IOException{
    	//ʹ��WebApplicationContextUtils�������ȡSpring IOC�����е�daoʵ��
    	dao = (BaseDAOImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(((PageContext)getJspContext()).getServletContext()).getBean("dao");    	
    	//�����ѯ�����б��HQL���
    	if (newstype.equals("4")){//��������
			hql=" from News as a where a.status=1 and a.newscolumns.columnCode in("+Tools.formatString(section)+") and a.isPicNews=1 order by a.priority desc,a.id desc";
		}else{
			hql=" from News as a where a.status=1 and a.newscolumns.columnCode in("+Tools.formatString(section)+") and a.newsType="+newstype+" and a.isPicNews=1 order by a.priority desc,a.id desc";
		}
		StringBuffer sb=new StringBuffer();
		List list=dao.query(hql,1,number);
		if(list==null||list.size()==0){
	    	//�����������ҳ����
	    	getJspContext().getOut().println(""); 			
			return;
		}
		Iterator it=list.iterator();
		sb.append("    <script language=javascript>\n");
		sb.append("	var focus_width"+slideno+"="+width+";     /*�õ�Ƭ����ͼƬ���*/\n");
		sb.append("	var focus_height"+slideno+"="+height+";    /*�õ�Ƭ����ͼƬ�߶�*/\n");
		sb.append("	var text_height"+slideno+"=20;    /*�õ�Ƭ�������ֱ���߶�*/\n");
		sb.append("	var swf_height"+slideno+" = focus_height"+slideno+"+text_height"+slideno+";\n");
		sb.append("	var pics"+slideno+" = '';\n");
		sb.append("	var links"+slideno+" = '';\n");
		sb.append("	var texts"+slideno+" = '';\n");
		sb.append("	function ati"+slideno+"(url, img, title)\n");
		sb.append("	{\n");
		sb.append("		if(pics"+slideno+" != '')\n");
		sb.append("		{\n");
		sb.append("			pics"+slideno+" = \"|\" + pics"+slideno+";\n");
		sb.append("			links"+slideno+" = \"|\" + links"+slideno+";\n");
		sb.append("			texts"+slideno+" = \"|\" + texts"+slideno+";\n");
		sb.append("		}");
		sb.append("		pics"+slideno+" = escape(img) + pics"+slideno+";\n");
		sb.append("		links"+slideno+" = escape(url) + links"+slideno+";\n");
		sb.append("		texts"+slideno+" = title + texts"+slideno+";\n");
		sb.append("	}\n");
		sb.append("    </script>\n");
		sb.append("    <script language=javascript>	\n");
		while(it.hasNext()){
			obj=(News)it.next();
			if (obj.getTitle().length()>titlelen){
				sb.append("      ati"+slideno+"('"+baseurl+obj.getHtmlPath()+"', '"+baseurl+"/"+obj.getPicture().trim()+"', '"+Tools.cutString(obj.getTitle(), titlelen*2)+"');\n");
			}else{
				sb.append("      ati"+slideno+"('"+baseurl+obj.getHtmlPath()+"', '"+baseurl+"/"+obj.getPicture().trim()+"', '"+obj.getTitle()+"');\n");
			}
		}		
		sb.append("	document.write('<embed src=\""+baseurl+"/js/pixviewer.swf\" wmode=\"opaque\" FlashVars=\"pics='+pics"+slideno+"+'&links='+links"+slideno+"+'&texts='+texts"+slideno+"+'&borderwidth='+focus_width"+slideno+"+'&borderheight='+focus_height"+slideno+"+'&textheight='+text_height"+slideno+"+'\" menu=\"false\" bgcolor=\"#DADADA\" quality=\"high\" width=\"'+ focus_width"+slideno+"+'\" height=\"'+ swf_height"+slideno+" +'\" allowScriptAccess=\"sameDomain\" type=\"application/x-shockwave-flash\"/>');	\n");
		sb.append("</script>\n");
    	//�����������ҳ����
    	getJspContext().getOut().println(sb);
	  }

	public String getBaseurl() {
		return baseurl;
	}
	public void setBaseurl(String baseurl) {
		this.baseurl = baseurl;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getNewstype() {
		return newstype;
	}
	public void setNewstype(String newstype) {
		this.newstype = newstype;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public int getTitlelen() {
		return titlelen;
	}
	public void setTitlelen(int titlelen) {
		this.titlelen = titlelen;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getSlideno() {
		return slideno;
	}
	public void setSlideno(String slideno) {
		this.slideno = slideno;
	}

}
