package com.eportal.struts.action;

import java.util.*;
import java.net.*;
import org.apache.struts2.ServletActionContext;
import com.eportal.service.*;
import com.opensymphony.xwork2.ActionSupport;

/** ����ͳ�ƿ����� */
@SuppressWarnings("serial")
public class TrafficAction extends ActionSupport{
	/** ͨ������ע��TrafficService���ʵ�� */
	TrafficService service;

	private List result;//��ѯ����б�
	private String hql =null;//��ѯHQL���
	
	/** ����IPͳ������ */
	public String browseIP(){
		hql = ServletActionContext.getRequest().getParameter("hql");
		if(hql==null){
			hql="select a.ip,max(a.area),max(a.visitDate),count(a.ip) from Traffic as a group by a.ip order by a.id desc";
		}else{			
			try {
				hql=URLDecoder.decode(hql,"UTF-8");
			} catch (Exception e) {}
		}
		List tempresult = service.browseTraffic(hql);
		//�Բ�ѯ����������·�װ
		Iterator it = tempresult.iterator();
		Object[] obj = null;
		Map row = null;
		result = new ArrayList();
		while(it.hasNext()){
			obj = (Object[])it.next();
			row = new HashMap();
			row.put("ip", obj[0]);
			row.put("area", obj[1]);			
			row.put("visitDate", obj[2]);
			row.put("times", obj[3]);
			result.add(row);
		}
		return SUCCESS;
	}
	
	/** ����PVͳ������ */
	public String browsePV(){
		hql = ServletActionContext.getRequest().getParameter("hql");
		if(hql==null){
			hql="from Traffic as a order by a.id desc";
		}else{			
			try {
				hql=URLDecoder.decode(hql,"UTF-8");
			} catch (Exception e) {}
		}
		result = service.browseTraffic(hql);
		return SUCCESS;
	}	

	public TrafficService getService() {
		return service;
	}

	public void setService(TrafficService service) {
		this.service = service;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}	
}