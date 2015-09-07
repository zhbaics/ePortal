package com.eportal.tld;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import java.text.SimpleDateFormat;
import com.eportal.ORM.*;
import com.eportal.DAO.*;
import com.eportal.util.*;

/** �ı������б��Զ����ǩ�� */
public class TextnewsTag extends SimpleTagSupport{
	SimpleDateFormat df = null;
	BaseDAO dao = null;		//���ݿ�DAO�ӿ�
	News obj= null;
	String hql= null;
	String newstype="4";	//��������:0-�������� 1-ͷ������ 2-�ȵ����� 3-�����Ƽ� 4-��������
	String section;	 		//������Ŀ���,�����Ŀ����м��ú���ָ�,��:I_001,I_002
	int number;      		//���ŵ�����
	int pageNo = 1;			//ҳ��
	int titlelen;    		//���������
	String split="0";  		//�Ƿ���Ҫ�ָ�����Ĭ��Ϊ�޷ָ���    
	String prex="";   		//ǰ��ͼƬ��Ĭ��Ϊ�ޣ�'0'Ϊ'��',����Ϊ������
	String prexchar=""; 	//ǰ׺�ַ�
	String splitchar=""; 	//�ָ��ַ�
	String baseurl =""; 	//����URL
	String dateFormat="";   //���ڸ�ʽ��Ĭ��Ϊ�ޣ�y:�� M:�� d:�� k:ʱ m:�� s:�� �磺yyyy-MM-dd kk:mm:ss ��Ҫ�ⲿ�ṩ.newslist_title��.newslist_date����CSS��ʽ��������ʾЧ��
	String dateStr="";		//��ʽ����������ַ���
	
	/** ��ǩ�崦�� */
    public void doTag() throws JspException, IOException{
    	//ʹ��WebApplicationContextUtils�������ȡSpring IOC�����е�daoʵ��
    	dao = (BaseDAOImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(((PageContext)getJspContext()).getServletContext()).getBean("dao");
		if (dateFormat.length()>0){//�����б���Ҫ����
			try{
				df = new SimpleDateFormat(dateFormat);
			}catch(Exception ex){//��������ڸ�ʽ����ȷʱ,ʹ��Ĭ�ϵ�yyyy-MM-dd���ڸ�ʽ
				df = new SimpleDateFormat("yyyy-MM-dd");
			}
		}
		int i=1;
		//�����ѯ�����б��HQL���
		if (newstype.equals("4")){//��������
			hql=" from News as a where a.status=1 and a.newscolumns.columnCode in("+Tools.formatString(section)+") order by a.priority desc,a.id desc";			
		}else{//ָ������
			hql=" from News as a where a.status=1 and a.newscolumns.columnCode in("+Tools.formatString(section)+") and a.newsType="+newstype+" order by a.priority desc,a.id desc";			
		}
		StringBuffer sb=new StringBuffer();
		List list=dao.query(hql,pageNo,number);//ֱ�ӵ���dao��ѯ�����б�
		if(list==null||list.size()==0){
	    	//��ѯ�����Ϊ��,������ִ���ҳ����
	    	getJspContext().getOut().println(""); 			
			return;
		}
		//�������
		String redStart = "";
		String redEnd = "";
		String tempTile="";
		String divDate1="",divDate2="";		
		Iterator it=list.iterator();
		sb.append("<ul>\n");
		while(it.hasNext()){
			if(split.equals("1")){//��������ָ��				
				if(prex.equals("0")){//�����ַ�ǰ׺
					if (i%2==1){					
						prexchar="&middot;";
					}else{
						prexchar="";
					}			
				}
				if (i%2==1){
					splitchar="&nbsp;|&nbsp;";
				}else{
					splitchar="";
				}
			}else{//������ָ��
				splitchar="";
				if(prex.equals("0")){//�����ַ�ǰ׺		
					prexchar="&middot;";
				}				
			}			
			obj=(News)it.next();
			//�����Ƿ���Ҫ���
			if (obj.getRed()==1){
				redStart ="<span style='color:#ff0000;'>";
				redEnd = "</span>";
			}else{
				redStart = "";
				redEnd = "";				
			}
			if(obj.getTitle().length()>titlelen){//�����ַ����ȳ���
				tempTile=Tools.cutString(obj.getTitle(), titlelen*2);
			}else{//�����ַ����Ȳ�����
				tempTile=obj.getTitle();
			}
			if (df!=null){//�����б���Ҫ����
				dateStr = df.format(obj.getCdate());
				divDate1="<div class='newslist_title'>";
				divDate2="</div><div class='newslist_date'>"+dateStr+"</div>";
			}else{//�����б���Ҫ����
				divDate1="";
				divDate2="";
			}
			if(prex.endsWith("_.jpg")||prex.endsWith("_.gif")||prex.endsWith("_.png")){//����ǰʹ��˳��ͼƬ��Ϊǰ׺
				sb.append("<li>"+divDate1+"<img src='"+prex.replaceAll("_.","_"+i+".")+"'/><a href='"+baseurl+obj.getHtmlPath()+"' title='"+obj.getTitle()+"'>"+redStart+tempTile+redEnd+"</a>"+divDate2+splitchar+"</li>\n");
			}else if(prex.endsWith(".jpg")||prex.endsWith(".gif")||prex.endsWith(".png")){//����ǰʹ�ù̶�ͼƬ��Ϊǰ׺
				sb.append("<li>"+divDate1+"<img src='"+prex+"'/><a href='"+baseurl+obj.getHtmlPath()+"' title='"+obj.getTitle()+"'>"+redStart+tempTile+redEnd+"</a>"+divDate2+splitchar+"</li>\n");
			}else{//����ǰʹ���ı��ַ���Ϊǰ׺			
				sb.append("<li>"+divDate1+prexchar+"<a href='"+baseurl+obj.getHtmlPath()+"' title='"+obj.getTitle()+"'>"+redStart+tempTile+redEnd+"</a>"+divDate2+splitchar+"</li>\n");
			}
			i++;
		}
		sb.append("</ul>\n");
    	//�����������ҳ����
    	getJspContext().getOut().println(sb); 
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getPrex() {
		return prex;
	}

	public void setPrex(String prex) {
		this.prex = prex;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSplit() {
		return split;
	}

	public void setSplit(String split) {
		this.split = split;
	}

	public int getTitlelen() {
		return titlelen;
	}

	public void setTitlelen(int titlelen) {
		this.titlelen = titlelen;
	}

	public String getNewstype() {
		return newstype;
	}

	public void setNewstype(String newstype) {
		this.newstype = newstype;
	}

	public String getBaseurl() {
		return baseurl;
	}

	public void setBaseurl(String baseurl) {
		this.baseurl = baseurl;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

}
