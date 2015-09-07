package com.eportal.tld;

import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.eportal.ORM.*;
import com.eportal.DAO.*;

/** ��Ʒ����б��Զ����ǩ�� */
public class CategoryListTag extends SimpleTagSupport{
	int listtype = 1;	//1-������Ʒ����б�,2-������Ʒ����б�,Ĭ��ֵΪ1	
	BaseDAO dao = null; 	//���ݿ�DAO�ӿ�
	String hql= null;
	List<Category> cateList1,cateList2;
	Iterator<Category> it1,it2;
	Category cate1,cate2;
	String jsp = "newmer.jsp";
	
	/** ��ǩ�崦�� */
    @SuppressWarnings("unchecked")
	public void doTag() throws JspException, IOException{
    	if (listtype==2){//������Ʒ����б�
    		jsp = "prommer.jsp";
    	}
    	String contextPath = ((HttpServletRequest)((PageContext)getJspContext()).getRequest()).getContextPath();
    	//ʹ��WebApplicationContextUtils�������ȡSpring IOC�����е�daoʵ��
    	dao = (BaseDAOImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(((PageContext)getJspContext()).getServletContext()).getBean("dao");    	
    	hql = "from Category as a where a.category is null order by a.id asc";
    	//װ�ص�һ����Ʒ���
    	cateList1 = dao.query(hql);
    	StringBuffer sb=new StringBuffer();
    	//����������Ʒ����б�
    	it1 = cateList1.iterator();
    	while(it1.hasNext()){
    		cate1 = it1.next();
    		sb.append("<div class='cate_name'><a target='_self' href='"+contextPath+"/webs/"+jsp+"?cateid="+cate1.getId()+"'>"+cate1.getCateName().trim()+"</a></div>\n");
    		sb.append("<div class='cate_list'>\n");
    		sb.append("<ul>\n");
    		//װ�صڶ�����Ʒ���
    		cateList2 = dao.query("from Category as a where a.category.id="+cate1.getId());
    		it2 = cateList2.iterator();
    		while(it2.hasNext()){
    			cate2 = it2.next();
    			sb.append("<li><a target='_self' href='"+contextPath+"/webs/"+jsp+"?cateid="+cate2.getId()+"'>"+cate2.getCateName().trim()+"</a></li>\n");
    		}
    		sb.append("</ul>\n");
    		sb.append("</div>\n");    		
    	}
    	//�����������ҳ����
    	getJspContext().getOut().println(sb);     	
    }

	public int getListtype() {
		return listtype;
	}

	public void setListtype(int listtype) {
		this.listtype = listtype;
	}
}
