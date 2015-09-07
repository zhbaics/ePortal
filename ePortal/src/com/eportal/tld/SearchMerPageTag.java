package com.eportal.tld;

import java.io.*;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.eportal.DAO.*;
import com.eportal.ORM.Category;
import com.eportal.util.*;

/** ��Ʒ������ҳ�����Զ����ǩ�� */
public class SearchMerPageTag extends SimpleTagSupport{
	BaseDAO dao = null; //���ݿ�DAO�ӿ�
	String hql= null;
	int listtype = 1;	//1-������Ʒ�б�,2-������Ʒ�б�,Ĭ��ֵΪ1
	String url = "";	//��ҳ���õ�URL,����Ϊ������
	int pageNo = 1;		//ҳ��
	int pageSize=6;     //ÿҳ��Ʒ����,Ĭ��ֵ6
	String keyword ="";	//�����ؼ���	
	int pageTotal=1;    //��ҳ��
	int prePageNo = 1;	//��һҳҳ��	
	int nextPageNo = 1;	//��һҳҳ��
	List<Category> catelist;
	Iterator<Category> cateit;
	Category cate;
	String ids=null;

	/** ��ǩ�崦�� */
    public void doTag() throws JspException, IOException{
    	//ʹ��WebApplicationContextUtils�������ȡSpring IOC�����е�daoʵ��
    	dao = (BaseDAOImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(((PageContext)getJspContext()).getServletContext()).getBean("dao");    	
    	if (listtype==1){//������Ʒ�б�
    		hql = "select count(*) from Merchandise as a where a.status=1 and (a.merName like '%"+keyword+"%' or a.merModel like '%"+keyword+"%' or a.merDesc like '%"+Tools.escape(keyword)+"%')";
    	}else{//������Ʒ�б�
    		hql = "select count(*) from Merchandise as a where a.status=1 and a.special=1 and (a.merName like '%"+keyword+"%' or a.merModel like '%"+keyword+"%' or a.merDesc like '%"+Tools.escape(keyword)+"%')";
    	}
		StringBuffer sb=new StringBuffer();
		//ͳ��������������Ʒ����
		int total=dao.countQuery(hql);
		//������ҳ��
		if (total>0){
			pageTotal = total / pageSize;
			if ((total%pageSize)>0)pageTotal++;
		}
		//������һҳ
		if (pageNo>1){
			prePageNo = pageNo-1;
		}else{
			prePageNo = 1;
		}
		//������һҳ
		if (pageNo<pageTotal){
			nextPageNo = pageNo+1;
		}else{
			nextPageNo = pageTotal;
		}
		//�����url
		if (url.indexOf("?")!=-1){
			url = url+"&";
		}else{
			url = url+"?";
		}
		sb.append("<div>��ǰ��["+pageNo+"/"+pageTotal+"]ҳ [<a target='_self' href='"+url+"pageNo=1'>��ҳ</a>] ");
		//��һҳʱ,"��һҳ"������Ч
		if (pageNo==1){
			sb.append("[��һҳ] ");
		}else{
			sb.append("[<a target='_self' href='"+url+"pageNo="+prePageNo+"'>��һҳ</a>] ");
		}
		//��ĩҳʱ,"��һҳ"������Ч
		if (pageNo==pageTotal){
			sb.append("[��һҳ] ");
		}else{
			sb.append("[<a target='_self' href='"+url+"pageNo="+nextPageNo+"'>��һҳ</a>] ");
		}
		sb.append("[<a target='_self' href='"+url+"pageNo="+pageTotal+"'>βҳ</a>]</div>");
    	//�����������ҳ����
    	getJspContext().getOut().println(sb); 
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getListtype() {
		return listtype;
	}

	public void setListtype(int listtype) {
		this.listtype = listtype;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
