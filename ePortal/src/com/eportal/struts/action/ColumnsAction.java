package com.eportal.struts.action;

import java.io.UnsupportedEncodingException;
import java.util.List;
import com.eportal.ORM.Newscolumns;
import com.eportal.service.ColumnsService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/** ������Ŀ��������� */
@SuppressWarnings("serial")
public class ColumnsAction extends ActionSupport implements ModelDriven<Newscolumns>{
	/** ͨ������ע��ColumnsService���ʵ�� */
	ColumnsService service;
	
	/** ������Ŀ�������������г��õĲ���ֵ */
	private String actionMsg;	//Action�䴫�ݵ���Ϣ����
	private List<Newscolumns> columnsList;	//������Ŀ�б�
	private String parentid;	//�ϼ���Ŀ��ID
	
	//����ģ������
	private Newscolumns model=new Newscolumns();//���ڷ�װ������Ŀ���Ե�ģ��ʵ��
	public Newscolumns getModel() {
		return model;
	}
	
	/** �������������Ŀ���� */
	public String browseColumns(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		columnsList = service.browseColumns();//����ҵ���߼����ȡ��������Ŀ�б�
		return SUCCESS;
	}
	
	/** ��������������Ŀ���� */
	public String addColumns(){
		Newscolumns tempColumns = new Newscolumns();
		tempColumns.setColumnName(model.getColumnName());
		tempColumns.setColumnCode(model.getColumnCode());
		if(parentid!=null && Integer.parseInt(parentid)>0){
			tempColumns.setNewscolumns(service.loadColumns(Integer.valueOf(parentid)));
		}
		if (service.saveOrUpdateColumns(tempColumns)){//����ҵ���߼��������������������Ŀ
			addActionMessage(getText("columns_add_succ"));
		}else{
			addActionMessage(getText("columns_add_fail"));
		}
		return SUCCESS;
	}
	
	/** ����ɾ��������Ŀ���� */
	public String delColumns(){
		if (model.getId()!=null){
			if (service.delColumns(model.getId())){//����ҵ���߼����ɾ��ָ����������Ŀ
				actionMsg = getText("columns_del_succ");
			}else{
				actionMsg = getText("columns_del_fail");
			}			
		}else{
			actionMsg = getText("columns_del_fail");
		}
		return "toBrowseColumns";
	}
	
	/** ����鿴������Ŀ���� */
	public String viewColumns(){
		if (model.getId()!=null){
			//����ҵ���߼����װ��ָ����������Ŀ
			Newscolumns tempColumns = service.loadColumns(model.getId());
			if (tempColumns!=null){
				model.setColumnName(tempColumns.getColumnName().trim());
				model.setColumnCode(tempColumns.getColumnCode().trim());
				if (tempColumns.getNewscolumns()!=null){
					parentid = tempColumns.getNewscolumns().getId().toString();
				}else{
					parentid = "-1";
				}				
				return SUCCESS;
			}else{
				actionMsg = getText("columns_view_fail");
				return "toBrowseColumns";
			}	
		}else{
			actionMsg = getText("columns_view_fail");
			return "toBrowseColumns";
		}		
	}
	
	/** ����װ��������Ŀ���� */
	public String editColumns(){
		if (model.getId()!=null){
			//����ҵ���߼����װ��ָ����������Ŀ
			Newscolumns tempColumns = service.loadColumns(model.getId());
			if (tempColumns!=null){
				model.setColumnName(tempColumns.getColumnName().trim());
				model.setColumnCode(tempColumns.getColumnCode().trim());
				if (tempColumns.getNewscolumns()!=null){
					parentid = tempColumns.getNewscolumns().getId().toString();
				}else{
					parentid = "-1";
				}
				return SUCCESS;
			}else{
				actionMsg = getText("columns_edit_fail");
				return "toBrowseColumns";
			}	
		}else{
			actionMsg = getText("columns_edit_fail");
			return "toBrowseColumns";
		}		
	}
	
	/** �������������Ŀ���� */
	public String updateColumns(){		
		Newscolumns tempColumns = service.loadColumns(model.getId());
		tempColumns.setColumnName(model.getColumnName());
		tempColumns.setColumnCode(model.getColumnCode());
		if(parentid!=null && Integer.parseInt(parentid)>0){
			tempColumns.setNewscolumns(service.loadColumns(Integer.valueOf(parentid)));
		}else{
			tempColumns.setNewscolumns(null);
		}
		if (service.saveOrUpdateColumns(tempColumns)){//����ҵ���߼��������ָ����������Ŀ
			addActionMessage(getText("columns_edit_succ"));
		}else{
			addActionMessage(getText("columns_edit_fail"));
		}
		return INPUT;		
	}
	
	/** ����һ��������Ŀ�����б����� */
	public String listColumns(){
		columnsList = service.listColumns();//����ҵ���߼����ȡ��һ��������Ŀ�б�
		Newscolumns firstnode = new Newscolumns();
		firstnode.setId(-1);
		firstnode.setColumnName("���ϼ���Ŀ");
		columnsList.add(0,firstnode);
		return SUCCESS;	
	}	

	public String getActionMsg() {
		return actionMsg;
	}
	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}
	public ColumnsService getService() {
		return service;
	}
	public void setService(ColumnsService service) {
		this.service = service;
	}

	public List<Newscolumns> getColumnsList() {
		return columnsList;
	}
	public void setColumnsList(List<Newscolumns> columnsList) {
		this.columnsList = columnsList;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
}
