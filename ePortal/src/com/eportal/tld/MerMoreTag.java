package com.eportal.tld;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.eportal.ORM.*;
import com.eportal.service.MemberLevelService;
import com.eportal.service.MemberLevelServiceImpl;
import com.eportal.util.Tools;
import com.eportal.DAO.*;

/** ��Ʒ�б��Զ����ǩ�� */
public class MerMoreTag extends SimpleTagSupport{
	int listtype = 1;	//1-������Ʒ�б�,2-������Ʒ�б�,Ĭ��ֵΪ1
	int size = 6;		//��Ʒ�б�����,Ĭ��Ϊ6��
	int pageNo = 1; 	//ҳ��,Ĭ��Ϊ��һҳ
	int picWidth=76;	//��ƷͼƬ���,Ĭ��ֵ76
	String baseurl =""; //����URL
	String cateid ="";	//��Ʒ����ID
	BaseDAO dao = null; 	//���ݿ�DAO�ӿ�
	MemberLevelService memberLevelService; //��Ա����ҵ���߼��ӿ�		
	String hql= null;
	List<Merchandise> merlist;
	Iterator<Merchandise> it;
	Merchandise mer;
	List<Category> catelist;
	Iterator<Category> cateit;
	Category cate;	
	String filename;
	Map map;
	String height_width;
	String merDesc;
	String ids=null;
	
	/** ��ǩ�崦�� */
    @SuppressWarnings("unchecked")
	public void doTag() throws JspException, IOException{
    	//ʹ��WebApplicationContextUtils�������ȡSpring IOC�����е�dao��memberLevelServiceʵ��
    	dao = (BaseDAOImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(((PageContext)getJspContext()).getServletContext()).getBean("dao");    	
    	memberLevelService = (MemberLevelServiceImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(((PageContext)getJspContext()).getServletContext()).getBean("memberLevelService");    	
		//���ָ������Ʒ��������ҳ����༰�������ID��,�γ�ID��
    	if (cateid.trim().length()>0){//ָ����Ʒ����
			hql = "from Category as a where a.id="+cateid+" or a.category.id="+cateid;
			catelist = dao.query(hql);
			cateit = catelist.iterator();
			while (cateit.hasNext()){
				cate = cateit.next();
				if (ids==null){
					ids = cate.getId().toString();
				}else{
					ids = ids+","+cate.getId().toString();
				}
			}
		}   	
    	if (listtype==1){//������Ʒ�б�
    		if (ids!=null){//ָ����Ʒ����
    			hql = "from Merchandise as a where a.status=1 and a.category.id in("+ids+") order by a.id desc";
    		}else{//������Ʒ����
    			hql = "from Merchandise as a where a.status=1 order by a.id desc";
    		}    		
    	}else{//������Ʒ�б�
    		if (ids!=null){//ָ����Ʒ����
    			hql = "from Merchandise as a where a.status=1 and a.special=1 and a.category.id in("+ids+") order by (a.price-a.sprice)*100/a.price desc";
    		}else{//������Ʒ����
    			hql = "from Merchandise as a where a.status=1 and a.special=1 order by (a.price-a.sprice)*100/a.price desc";
    		}    		
    	}
    	StringBuffer sb=new StringBuffer();
    	sb.append("<ul>\n");
    	merlist = dao.query(hql, pageNo, size);
    	it = merlist.iterator();
    	while(it.hasNext()){
    		mer = it.next();
    		sb.append("<li>\n");
    		sb.append("<div class='merlist_photo'>");
    		if (mer.getPicture()!=null && mer.getPicture().trim().length()>0){//����ƷͼƬ
        		//������ƷͼƬ�Ŀ����߶�,��ȷ���ȱ���ʾ
    			filename=((PageContext)getJspContext()).getRequest().getRealPath(mer.getPicture().replaceAll("\\\\","/"));
    			map = Tools.getPicWidthHeight(filename); 
    			height_width ="height='"+picWidth+"'";
    			if (map!=null){
    				int width = Integer.valueOf(map.get("width").toString());
    				int height = Integer.valueOf(map.get("height").toString());
    				if (width>=height){//����ͼƬ,���ƿ�ȼ���
    					height_width ="width='"+picWidth+"'";
    				}else{//����ͼƬ,���Ƹ߶ȼ���
    					height_width ="height='"+picWidth+"'";
    				}
    			}    			
    			sb.append("<a href='"+baseurl+mer.getHtmlPath()+"' title='��Ʒ����:"+mer.getMerName()+"\n��Ʒ�ͺ�:"+mer.getMerModel()+"'><img src='"+baseurl+mer.getPicture().trim()+"' "+height_width+"/></a>");
    		}else{//����ƷͼƬ
    			sb.append("<a href='"+baseurl+mer.getHtmlPath()+"' title='��Ʒ����:"+mer.getMerName()+"\n��Ʒ�ͺ�:"+mer.getMerModel()+"'><img src='"+baseurl+"/images/noimages.gif' width='"+picWidth+"'/></a>");
    		}
    		sb.append("</div>\n");
    		if (mer.getMerDesc()!=null){
        		//����Ʒ���������ݽ���unescape����
    			merDesc = Tools.unescape(mer.getMerDesc());
    			//�޳���Ʒ���������е�HTML��ǩ,ֻ�����ı�����
    			merDesc=merDesc.replaceAll("</?[^>]+>","");
    			merDesc=merDesc.replaceAll("&nbsp;","").replaceAll("\n", "").replaceAll("\r", "").trim(); 
    			//�Գ���150�����ֵ����ݽ��нضϴ���
    			if (merDesc.getBytes().length>300){
    				merDesc = Tools.cutString(merDesc,300)+"...";
    			}
    		}else{
    			merDesc="";
    		}
    		sb.append("<div class='merlist_desc'>"+merDesc+"</div>\n"); 
    		sb.append("<div class='merlist_price'>\n");
    		sb.append("<a href='"+baseurl+mer.getHtmlPath()+"'><img src='"+baseurl+"/images/icon_info.gif'/></a><br/>\n");
    		sb.append("<img src='"+baseurl+"/images/icon_buy.gif' style='cursor:pointer' onclick='buy("+mer.getId()+")'/><br/>\n");    		
        	if (listtype==1){//������Ʒ�б�,������Żݼ�,�Żݼۼ�Ϊ��Ա��,�����Ա��Ϊ����Żݻ�Ա��
        		sb.append("�г��ۣ�"+Tools.formatCcurrency(mer.getPrice())+"<br/>\n");
        		double tmpprice = 0;
        		if (mer.getSpecial().intValue()==1){//�д�����
        			tmpprice = mer.getSprice().doubleValue();
        		}else{
        			tmpprice = mer.getPrice()*(100-memberLevelService.getInitMemberlevel().getFavourable().intValue())/100;
        		}        		
        		sb.append("<span class='redtext'>��Ա�ۣ�"+Tools.formatCcurrency(tmpprice)+"</span>\n");
        	}else{//������Ʒ�б�
        		sb.append("ԭ�ۣ�"+Tools.formatCcurrency(mer.getPrice())+"<br/>\n");        		
        		sb.append("<span class='redtext'>�ؼۣ�"+Tools.formatCcurrency(mer.getSprice())+"</span>\n");
        	}
        	sb.append("</div>\n");
        	sb.append("</li>\n");
    	}
    	if (merlist==null || merlist.size()<1){
    		sb.append("<br/>&nbsp;&nbsp;�Բ���,��ʱû��������������Ʒ��¼!\n");
    	}
    	sb.append("</ul>\n");
    	//�����������ҳ����
    	getJspContext().getOut().println(sb);     	
    }

	public int getListtype() {
		return listtype;
	}

	public void setListtype(int listtype) {
		this.listtype = listtype;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPicWidth() {
		return picWidth;
	}

	public void setPicWidth(int picWidth) {
		this.picWidth = picWidth;
	}

	public String getBaseurl() {
		return baseurl;
	}

	public void setBaseurl(String baseurl) {
		this.baseurl = baseurl;
	}

	public String getCateid() {
		return cateid;
	}

	public void setCateid(String cateid) {
		this.cateid = cateid;
	}
}
