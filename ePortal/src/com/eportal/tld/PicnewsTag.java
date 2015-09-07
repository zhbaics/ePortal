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

/** ͼƬ�����б��Զ����ǩ�� */
public class PicnewsTag extends SimpleTagSupport{
	BaseDAO dao = null;		//���ݿ�DAO�ӿ�
	News obj= null;
	String hql= null;
	String newstype="4";	//��������:0-�������� 1-ͷ������ 2-�ȵ����� 3-�����Ƽ�  4-��������
	String section;	 		//������Ŀ���,�����Ŀ����м��ú���ָ�,��:I_001,I_002
	int width;				//ͼƬ���
	int height;				//ͼƬ�߶�
	int number=1;			//�б�����
	String hastitle="0";   	//�Ƿ���Ҫ���� 0:����Ҫ���⡡������Ҫ����
	int titlelen;          	//��������
	String baseurl =""; 	//����URL	
	
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
		sb.append("<ul>\n");
		while(it.hasNext()){
			obj=(News)it.next();
			sb.append("<li>");
			if(hastitle.equals("1")){//��Ҫ����
				sb.append("<center><a href='"+baseurl+obj.getHtmlPath()+"'>\n");
				sb.append("<img width='"+width+"' alt='"+obj.getTitle()+"' height='"+height+"' src='"+baseurl+"/"+obj.getPicture().trim()+"'>\n");
				sb.append("</a><br/>\n");
				if (obj.getTitle().length()>titlelen){//�����ַ�����
					sb.append("<a href='"+baseurl+obj.getHtmlPath()+"' title='"+obj.getTitle()+"'>"+Tools.cutString(obj.getTitle(), titlelen*2)+"</a></center>\n");
				}else{
					sb.append("<a href='"+baseurl+obj.getHtmlPath()+"'>"+obj.getTitle()+"</a></center>\n");
				}
			}else{//����Ҫ����
				sb.append("<a href='"+obj.getHtmlPath()+"'>\n");
				sb.append("<img width='"+width+"' alt='"+obj.getTitle()+"' height='"+height+"' src='"+baseurl+"/"+obj.getPicture().trim()+"'>\n");
				sb.append("</a>\n");
			}
			sb.append("</li>\n");
		}
		sb.append("</ul>\n");
    	//�����������ҳ����
    	getJspContext().getOut().println(sb);
	}

	public String getHastitle() {
		return hastitle;
	}
	public void setHastitle(String hastitle) {
		this.hastitle = hastitle;
	}
	public String getNewstype() {
		return newstype;
	}
	public void setNewstype(String newstype) {
		this.newstype = newstype;
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
	public String getBaseurl() {
		return baseurl;
	}
	public void setBaseurl(String baseurl) {
		this.baseurl = baseurl;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}	
}
