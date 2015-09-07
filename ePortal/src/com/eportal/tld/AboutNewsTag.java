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

/** ��������б��Զ����ǩ�� */
public class AboutNewsTag extends SimpleTagSupport{
	SimpleDateFormat df = null;	
	BaseDAO dao = null; 	//���ݿ�DAO�ӿ�
	News obj= null;
	String hql= null;
	String currentId="0";	//��ǰ���ŵ�ID��
	int number;      		//���ŵ�����
	int pageNo = 1;			//ҳ��
	int titlelen;    		//���������
	String keywords = "";	//�ؼ���
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
		hql=" from News as a where (";
		String[] keys = keywords.split(",");
		for(int k=0;k<keys.length;k++){
			if (k==0){
				hql = hql + "(a.keyWords like '%"+keys[k]+"%') ";
			}else{
				hql = hql + "or (a.keyWords like '%"+keys[k]+"%') ";
			}
		}
		hql=hql+") and a.id<>"+currentId+" and a.status=1 order by a.priority desc,a.id desc";
		//System.out.println("about hql="+hql);
		StringBuffer sb=new StringBuffer();
		List list=dao.query(hql,pageNo,number);
		if(list==null||list.size()==0){
	    	//�����������ҳ����
	    	getJspContext().getOut().println("�����������!"); 			
			return;
		}
		Iterator it=list.iterator();
		sb.append("<ul>\n");
		while(it.hasNext()){
			if(split.equals("1")){//����ָ��				
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
			}else{
				splitchar="";
				if(prex.equals("0")){//�����ַ�ǰ׺		
					prexchar="&middot;";
				}				
			}
			
			obj=(News)it.next();
			if (df!=null){//�����б���Ҫ����
				dateStr = df.format(obj.getCdate());
				if(obj.getTitle().length()>titlelen){//�����ַ�����
					if(prex.endsWith("_.jpg")||prex.endsWith("_.gif")||prex.endsWith("_.png")){//˳��ͼƬ
						sb.append("<li><div class='newslist_title'><img src='"+prex.replaceAll("_.","_"+i+".")+"'/><a href='"+baseurl+obj.getHtmlPath()+"' title='"+obj.getTitle()+"'>"+Tools.cutString(obj.getTitle(), titlelen*2)+"</a></div><div class='newslist_date'>"+dateStr+"</div>"+splitchar+"</li>\n");
					}else if(prex.endsWith(".jpg")||prex.endsWith(".gif")||prex.endsWith(".png")){//�̶�ͼƬ
						sb.append("<li><div class='newslist_title'><img src='"+prex+"'/><a href='"+baseurl+obj.getHtmlPath()+"' title='"+obj.getTitle()+"'>"+Tools.cutString(obj.getTitle(), titlelen*2)+"</a></div><div class='newslist_date'>"+dateStr+"</div>"+splitchar+"</li>\n");
					}else{					
						sb.append("<li><div class='newslist_title'>"+prexchar+"<a href='"+baseurl+obj.getHtmlPath()+"' title='"+obj.getTitle()+"'>"+Tools.cutString(obj.getTitle(), titlelen*2)+"</a></div><div class='newslist_date'>"+dateStr+"</div>"+splitchar+"</li>\n");
					}
				}else{
					if(prex.endsWith("_.jpg")||prex.endsWith("_.gif")||prex.endsWith("_.png")){//˳��ͼƬ
						sb.append("<li><div class='newslist_title'><img src='"+prex.replaceAll("_.","_"+i+".")+"'/><a href='"+baseurl+obj.getHtmlPath()+"'>"+obj.getTitle()+"</a></div><div class='newslist_date'>"+dateStr+"</div>"+splitchar+"</li>\n");
					}else if(prex.endsWith(".jpg")||prex.endsWith(".gif")||prex.endsWith(".png")){//�̶�ͼƬ
						sb.append("<li><div class='newslist_title'><img src='"+prex+"'/><a href='"+baseurl+obj.getHtmlPath()+"'>"+obj.getTitle()+"</a></div><div class='newslist_date'>"+dateStr+"</div>"+splitchar+"</li>\n");
					}else{
						sb.append("<li><div class='newslist_title'>"+prexchar+"<a href='"+baseurl+obj.getHtmlPath()+"'>"+obj.getTitle()+"</a></div><div class='newslist_date'>"+dateStr+"</div>"+splitchar+"</li>\n");
					}
				}				
			}else{//�����б���Ҫ����
				if(obj.getTitle().length()>titlelen){//�����ַ�����
					if(prex.endsWith("_.jpg")||prex.endsWith("_.gif")||prex.endsWith("_.png")){//˳��ͼƬ
						sb.append("<li><img src='"+prex.replaceAll("_.","_"+i+".")+"'/><a href='"+baseurl+obj.getHtmlPath()+"' title='"+obj.getTitle()+"'>"+Tools.cutString(obj.getTitle(), titlelen*2)+"</a>"+splitchar+"</li>\n");
					}else if(prex.endsWith(".jpg")||prex.endsWith(".gif")||prex.endsWith(".png")){//�̶�ͼƬ
						sb.append("<li><img src='"+prex+"'/><a href='"+baseurl+obj.getHtmlPath()+"' title='"+obj.getTitle()+"'>"+Tools.cutString(obj.getTitle(), titlelen*2)+"</a>"+splitchar+"</li>\n");
					}else{					
						sb.append("<li>"+prexchar+"<a href='"+baseurl+obj.getHtmlPath()+"' title='"+obj.getTitle()+"'>"+Tools.cutString(obj.getTitle(), titlelen*2)+"</a>"+splitchar+"</li>\n");
					}
				}else{
					if(prex.endsWith("_.jpg")||prex.endsWith("_.gif")||prex.endsWith("_.png")){//˳��ͼƬ
						sb.append("<li><img src='"+prex.replaceAll("_.","_"+i+".")+"'/><a href='"+baseurl+obj.getHtmlPath()+"'>"+obj.getTitle()+"</a>"+splitchar+"</li>\n");
					}else if(prex.endsWith(".jpg")||prex.endsWith(".gif")||prex.endsWith(".png")){//�̶�ͼƬ
						sb.append("<li><img src='"+prex+"'/><a href='"+baseurl+obj.getHtmlPath()+"'>"+obj.getTitle()+"</a>"+splitchar+"</li>\n");
					}else{
						sb.append("<li>"+prexchar+"<a href='"+baseurl+obj.getHtmlPath()+"'>"+obj.getTitle()+"</a>"+splitchar+"</li>\n");
					}
				}				
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

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getCurrentId() {
		return currentId;
	}

	public void setCurrentId(String currentId) {
		this.currentId = currentId;
	}

}
