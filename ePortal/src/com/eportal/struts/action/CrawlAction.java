package com.eportal.struts.action;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Pattern;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.apache.struts2.ServletActionContext;
import com.eportal.DAO.BaseDAO;
import com.eportal.DAO.BaseDAOImpl;
import com.eportal.ORM.*;
import com.eportal.service.*;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.eportal.util.*;

/** ���Ųɼ������������ */
@SuppressWarnings("serial")
public class CrawlAction extends ActionSupport implements ModelDriven<Newsrule>{
	/** ͨ������ע��CrawlService��ColumnsService���ʵ�� */
	CrawlService service;
	ColumnsService columnsService;
	
	/** ���Ųɼ�����������������г��õĲ���ֵ */
	private String actionMsg;	//Action�䴫�ݵ���Ϣ����
	private List<Newsrule> ruleList;//�ɼ������б�
	private List<Newscolumns> newscolumnsList;//������Ŀ�б�	
	private String newscolumnsid=null;//����������ĿID
	
	//����ģ������
	private Newsrule model=new Newsrule();//���ڷ�װ�ɼ���������ģ��
	public Newsrule getModel() {
		return model;
	}
	
	/** ����������Ųɼ��������� */
	public String browseNewsrule(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		ruleList = service.browseNewsrule();//����ҵ���߼����ȡ�òɼ������б�
		return SUCCESS;
	}
	
	/** �����������Ųɼ��������� */
	public String addNewsrule(){
		Newscolumns newscolumns;
		if(newscolumnsid!=null){
			newscolumns = columnsService.loadColumns(Integer.parseInt(newscolumnsid));
			if(newscolumns!=null){
				//�ɼ�������������Ŀ���й���
				model.setNewscolumns(newscolumns);
			}
		}
		if (service.saveOrUpdateNewsrule(model)){//����ҵ���߼�������������Ĳɼ�����
			addActionMessage(getText("rule_add_succ"));
		}else{
			addActionMessage(getText("rule_add_fail"));
		}
		//Ϊ��̨�ɼ����������������Ŀ�����б�����
		newscolumnsList = columnsService.browseColumns();		
		return SUCCESS;
	}
	
	/** ����ɾ�����Ųɼ��������� */
	public String delNewsrule(){
		if (model.getId()!=null){
			if (service.delNewsrule(model.getId())){//����ҵ���߼����ɾ��ָ���Ĳɼ�����
				actionMsg = getText("rule_del_succ");
			}else{
				actionMsg = getText("rule_del_fail");
			}			
		}else{
			actionMsg = getText("rule_del_fail");
		}
		return "toBrowseNewsrule";
	}
	
	/** ����鿴���Ųɼ��������� */
	public String viewNewsrule(){
		if (model.getId()!=null){
			//����ҵ���߼����װ��ָ���Ĳɼ�����			
			Newsrule rule = service.loadNewsrule(model.getId());
			if (rule!=null){
				try {
					//���ٸ���Դ�����е��������Ե�Ŀ�������
					BeanUtils.copyProperties(model, rule);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return SUCCESS;
			}else{
				actionMsg = getText("rule_view_fail");
				return "toBrowseNewsrule";
			}	
		}else{
			actionMsg = getText("rule_view_fail");
			return "toBrowseNewsrule";
		}		
	}
	
	/** ����װ�����Ųɼ��������� */
	public String editNewsrule(){
		//Ϊ��̨�ɼ����������������Ŀ�����б�����
		newscolumnsList = columnsService.browseColumns();
		if (model.getId()!=null){
			//����ҵ���߼����װ��ָ���Ĳɼ�����			
			Newsrule rule = service.loadNewsrule(model.getId());
			if (rule!=null){
				try {
					//���ٸ���Դ�����е��������Ե�Ŀ�������
					BeanUtils.copyProperties(model, rule);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//ȡ��������ĿID
				newscolumnsid=rule.getNewscolumns().getId().toString();
				return SUCCESS;
			}else{
				actionMsg = getText("rule_view_fail");
				return "toBrowseNewsrule";
			}	
		}else{
			actionMsg = getText("rule_view_fail");
			return "toBrowseNewsrule";
		}		
	}
	
	/** ����������Ųɼ��������� */
	public String updateNewsrule(){		
		Newscolumns newscolumns;
		if(newscolumnsid!=null){
			//����ҵ���߼����װ��ָ����������Ŀ
			newscolumns = columnsService.loadColumns(Integer.parseInt(newscolumnsid));
			if(newscolumns!=null){
				//�ɼ�������������Ŀ���й���
				model.setNewscolumns(newscolumns);
			}
		}
		if (service.saveOrUpdateNewsrule(model)){//����ҵ���߼��������ָ���Ĳɼ�����
			addActionMessage(getText("rule_edit_succ"));
		}else{
			addActionMessage(getText("rule_edit_fail"));
		}
		//Ϊ��̨�ɼ����������������Ŀ�����б�����
		newscolumnsList = columnsService.browseColumns();		
		return INPUT;		
	}
	
	/** ����ɼ����������б����� */
	public String preAddNewsrule(){
		//Ϊ��̨�ɼ����������������Ŀ�����б�����
		newscolumnsList = columnsService.browseColumns();
		return SUCCESS;		
	}
	
	/** �������Ųɼ����� */
	public String crawlNewsrule(){
		//ʹ��WebApplicationContextUtils�������ȡSpring IOC�����е�daoʵ��
		BaseDAO dao = (BaseDAOImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext()).getBean("dao");
		//ʹ��ServletActionContext�������ȡSession�е�admin����ֵ
		Admin admin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		if (model.getId()!=null){
			//����ҵ���߼����װ��ָ���Ĳɼ�����	
			Newsrule rule = service.loadNewsrule(model.getId());
			//�������Ųɼ��߳�
			CrawlNewsThread ct = new CrawlNewsThread(rule,dao,admin);
			Thread th = new Thread(ct);
			th.start();
			//ʹ��ServletActionContext��������Session��д��ct����
			ServletActionContext.getRequest().getSession().setAttribute("ct", ct);
		}
		return "status";		
	}	
	
	/** �ֶ������������Ųɼ�����ı���֤ */
	public void validateAddNewsrule(){
		//�ɼ��������Ʊ���
		if (model.getRuleName()==null||model.getRuleName().trim().length()<1){
			addFieldError("rulename",getText("rule_validation_rulename"));
		}		
		//��ڵ�ַ����,����Ч��ʹ��������ʽ������֤
		if (model.getUrl()==null || model.getUrl().trim().length()<1 || !Pattern.matches("http(s)?://(.)*", model.getUrl().trim())){
			addFieldError("url",getText("rule_validation_url"));
		}
		//�����б���ʼ����������
		if (model.getListBegin()==null||model.getListBegin().trim().length()<1){
			addFieldError("listbegin",getText("rule_validation_listbegin"));
		}
		//�����б���ֹ����������
		if (model.getListEnd()==null||model.getListEnd().trim().length()<1){
			addFieldError("listend",getText("rule_validation_listend"));
		}
		//���ű�����ʼ����������
		if (model.getTitleBegin()==null||model.getTitleBegin().trim().length()<1){
			addFieldError("titlebegin",getText("rule_validation_titlebegin"));
		}
		//���ű�����ֹ����������
		if (model.getTitleEnd()==null||model.getTitleEnd().trim().length()<1){
			addFieldError("titleend",getText("rule_validation_titleend"));
		}
		//����������ʼ����������
		if (model.getContentBegin()==null||model.getContentBegin().trim().length()<1){
			addFieldError("contentbegin",getText("rule_validation_contentbegin"));
		}
		//����������ֹ����������
		if (model.getContentEnd()==null||model.getContentEnd().trim().length()<1){
			addFieldError("contentend",getText("rule_validation_contentend"));
		}		
		//����֤ʧ��
		if (hasFieldErrors()){
			//Ϊ��̨�ɼ����������������Ŀ�����б�����
			newscolumnsList = columnsService.browseColumns();
		}		
	}
	
	/** �ֶ������޸����Ųɼ�����ı���֤ */
	public void validateUpdateNewsrule(){
		validateAddNewsrule();
	}

	public CrawlService getService() {
		return service;
	}

	public void setService(CrawlService service) {
		this.service = service;
	}

	public ColumnsService getColumnsService() {
		return columnsService;
	}

	public void setColumnsService(ColumnsService columnsService) {
		this.columnsService = columnsService;
	}

	public String getActionMsg() {
		return actionMsg;
	}

	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}

	public List<Newsrule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<Newsrule> ruleList) {
		this.ruleList = ruleList;
	}

	public List<Newscolumns> getNewscolumnsList() {
		return newscolumnsList;
	}

	public void setNewscolumnsList(List<Newscolumns> newscolumnsList) {
		this.newscolumnsList = newscolumnsList;
	}

	public String getNewscolumnsid() {
		return newscolumnsid;
	}

	public void setNewscolumnsid(String newscolumnsid) {
		this.newscolumnsid = newscolumnsid;
	}
}