package com.eportal.tld;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.eportal.ORM.*;
import com.eportal.service.*;
import com.eportal.util.Tools;
import com.eportal.DAO.*;

/** ��Ƶ��Ʒ�б��Զ����ǩ�� */
public class VideoListTag extends SimpleTagSupport{
	int size = 10;		//��Ʒ�б�����,Ĭ��Ϊ10��
	int pageNo = 1; 	//ҳ��,Ĭ��Ϊ��һҳ
	int picWidth=120;	//��ƷͼƬ���,Ĭ��ֵ120
	String baseurl =""; //����URL
	BaseDAO dao = null; 	//���ݿ�DAO�ӿ�
	String hql= null;
	List<Merchandise> merlist;
	Iterator<Merchandise> it;
	Merchandise mer;
	String filename;
	Map map;
	String height_width;
	
	/** ��ǩ�崦�� */
    @SuppressWarnings("unchecked")
	public void doTag() throws JspException, IOException{
    	//ʹ��WebApplicationContextUtils�������ȡSpring IOC�����е�daoʵ��
    	dao = (BaseDAOImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(((PageContext)getJspContext()).getServletContext()).getBean("dao");
		hql = "from Merchandise as a where a.video is not null and a.status=1 order by a.id desc";
    	StringBuffer sb=new StringBuffer();
    	sb.append("<ul>\n");
    	merlist = dao.query(hql, pageNo, size);
    	it = merlist.iterator();
    	while(it.hasNext()){
    		mer = it.next();
    		sb.append("<li>\n");
    		sb.append("<div class='videolist_photo'>");
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
    			sb.append("<a href='"+baseurl+mer.getHtmlPath()+"' title='��Ʒ����:"+mer.getMerName()+"\n��Ʒ�ͺ�:"+mer.getMerModel()+"'><img align='absmiddle' src='"+baseurl+"/images/noimages.gif' width='"+picWidth+"'/></a>");
    		}    		
    		sb.append("</div>\n");
    		if (mer.getPicture()!=null && mer.getPicture().trim().length()>0){//����ƷͼƬ
    			sb.append("<img src='"+baseurl+"/images/play.gif' style='cursor:pointer' onclick=\"playFlv('"+baseurl+mer.getPicture()+"','"+mer.getVideo()+"')\"/>\n");
    		}else{//����ƷͼƬ
    			sb.append("<img src='"+baseurl+"/images/play.gif' style='cursor:pointer' onclick=\"playFlv('"+baseurl+"/images/noimages.gif','"+mer.getVideo()+"')\"/>\n");
    		}    		
    		sb.append("<a href='"+baseurl+mer.getHtmlPath()+"' title='��Ʒ����:"+mer.getMerName()+"\n��Ʒ�ͺ�:"+mer.getMerModel()+"'><img src='"+baseurl+"/images/icon_info.gif'/></a>\n");
    		sb.append("</li>\n");
    	}
    	if (merlist==null || merlist.size()<1){
    		sb.append("<br/>&nbsp;&nbsp;�Բ���,��ʱû��������������Ʒ��¼!\n");
    	}    	
    	sb.append("</ul>\n");
    	//�����������ҳ����
    	getJspContext().getOut().println(sb);     	
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
}
