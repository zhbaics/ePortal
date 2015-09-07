package com.eportal.struts.action;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.struts2.ServletActionContext;
import com.eportal.ORM.Admin;
import com.eportal.service.AdminService;
import com.eportal.util.MD5;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/** ϵͳ����Ա��������� */
@SuppressWarnings("serial")
public class AdminAction extends ActionSupport implements ModelDriven<Admin>{
	/** ͨ������ע��AdminService���ʵ�� */
	AdminService service;
	
	/** ϵͳ�û��������������г��õĲ���ֵ */
	private String rand; 		//�����֤��
	private String actionMsg;	//Action�䴫�ݵ���Ϣ����
	private List<Admin> adminList;		//ϵͳ�û��б�
	
	//����ģ������
	private Admin model=new Admin();//���ڷ�װϵͳ�û�����ģ��
	public Admin getModel() {
		return model;
	}
	
	/** �����¼���� */
	public String login(){
		if(!rand.equalsIgnoreCase((String)ServletActionContext.getRequest().getSession().getAttribute("rand"))){
			addActionError(getText("login_rand_error"));
			return "login";
		}else{
			Admin tempAdmin = service.adminLogin(model.getLoginName(), MD5.MD5Encode(model.getLoginPwd()));
			if(tempAdmin!=null){
				ServletActionContext.getRequest().getSession().setAttribute("admin",tempAdmin);
				return "index";
			}else{
				addActionError(getText("login_fail"));
				return "login";				
			}
		}
	}
	
	/** ����ע������ */
	public String logout(){		
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}
	
	/** �������ϵͳ�û����� */
	public String browseAdmin(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		adminList = service.browseAdmin();
		return SUCCESS;
	}
	
	/** ��������ϵͳ�û����� */
	public String addAdmin(){
		Admin tempAdmin = new Admin();
		tempAdmin.setLoginName(model.getLoginName());
		tempAdmin.setLoginPwd(MD5.MD5Encode(model.getLoginPwd()));
		tempAdmin.setPrivileges(model.getPrivileges());
		if (service.saveOrUpdateAdmin(tempAdmin)){
			addActionMessage(getText("admin_add_succ"));
		}else{
			addActionMessage(getText("admin_add_fail"));
		}
		return SUCCESS;
	}
	
	/** ����ɾ��ϵͳ�û����� */
	public String delAdmin(){
		if (model.getId()!=null){
			if (service.delAdmin(model.getId())){
				actionMsg = getText("admin_del_succ");
			}else{
				actionMsg = getText("admin_del_fail");
			}			
		}else{
			actionMsg = getText("admin_del_fail");
		}
		return "toBrowseAdmin";
	}
	
	/** ����鿴ϵͳ�û����� */
	public String viewAdmin(){
		if (model.getId()!=null){
			Admin tempAdmin = service.loadAdmin(model.getId());
			if (tempAdmin!=null){
				model.setLoginName(tempAdmin.getLoginName().trim());
				model.setPrivileges(tempAdmin.getPrivileges().trim());
				return SUCCESS;
			}else{
				actionMsg = getText("admin_view_fail");
				return "toBrowseAdmin";
			}	
		}else{
			actionMsg = getText("admin_view_fail");
			return "toBrowseAdmin";
		}		
	}
	
	/** ����װ��ϵͳ�û����� */
	public String editAdmin(){
		if (model.getId()!=null){
			Admin tempAdmin = service.loadAdmin(model.getId());
			if (tempAdmin!=null){
				model.setLoginName(tempAdmin.getLoginName().trim());
				model.setPrivileges(tempAdmin.getPrivileges().trim());				
				return SUCCESS;
			}else{
				actionMsg = getText("admin_edit_fail");
				return "toBrowseAdmin";
			}	
		}else{
			actionMsg = getText("admin_edit_fail");
			return "toBrowseAdmin";
		}		
	}
	
	/** �������ϵͳ�û����� */
	public String updateAdmin(){		
		Admin tempAdmin = service.loadAdmin(model.getId());
		tempAdmin.setLoginName(model.getLoginName());
		if (model.getLoginPwd()!=null&&model.getLoginPwd().length()>0){
			tempAdmin.setLoginPwd(MD5.MD5Encode(model.getLoginPwd()));
		}
		tempAdmin.setPrivileges(model.getPrivileges());
		if (service.saveOrUpdateAdmin(tempAdmin)){
			addActionMessage(getText("admin_edit_succ"));
		}else{
			addActionMessage(getText("admin_edit_fail"));
		}
		return INPUT;		
	}	
	
	public String getRand() {
		return rand;
	}
	public void setRand(String rand) {
		this.rand = rand;
	}
	public AdminService getService() {
		return service;
	}
	public void setService(AdminService service) {
		this.service = service;
	}
	public String getActionMsg() {
		return actionMsg;
	}

	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}
	
	public List<Admin> getAdminList() {
		return adminList;
	}
	
	public void setAdminList(List<Admin> adminList) {
		this.adminList = adminList;
	}	
}
