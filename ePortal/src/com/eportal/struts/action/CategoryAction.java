package com.eportal.struts.action;

import java.io.UnsupportedEncodingException;
import java.util.List;
import com.eportal.ORM.Category;
import com.eportal.service.CategoryService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/** ��Ʒ������������ */
@SuppressWarnings("serial")
public class CategoryAction extends ActionSupport implements ModelDriven<Category>{
	/** ͨ������ע��CategoryService���ʵ�� */
	CategoryService service;
	
	/** ��Ʒ����������������г��õĲ���ֵ */
	private String actionMsg;	//Action�䴫�ݵ���Ϣ����
	private List<Category> categoryList;	//��Ʒ�����б�
	private String parentid;	//�ϼ���Ŀ��ID
	
	//����ģ������
	private Category model=new Category();//���ڷ�װ��Ʒ��������ģ��
	public Category getModel() {
		return model;
	}
	
	/** ���������Ʒ�������� */
	public String browseCategory(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		categoryList = service.browseCategory();//����ҵ���߼����ȡ����Ʒ����б�
		return SUCCESS;
	}
	
	/** ����������Ʒ�������� */
	public String addCategory(){
		Category tempCategory = new Category();
		tempCategory.setCateName(model.getCateName());
		if(parentid!=null && Integer.parseInt(parentid)>0){
			tempCategory.setCategory(service.loadCategory(Integer.valueOf(parentid)));
		}
		if (service.saveOrUpdateCategory(tempCategory)){//����ҵ���߼����������������Ʒ���
			addActionMessage(getText("category_add_succ"));
		}else{
			addActionMessage(getText("category_add_fail"));
		}
		return SUCCESS;
	}
	
	/** ����ɾ����Ʒ�������� */
	public String delCategory(){
		if (model.getId()!=null){
			if (service.delCategory(model.getId())){//����ҵ���߼����ɾ��ָ������Ʒ���
				actionMsg = getText("category_del_succ");
			}else{
				actionMsg = getText("category_del_fail");
			}			
		}else{
			actionMsg = getText("category_del_fail");
		}
		return "toBrowseCategory";
	}
	
	/** ����鿴��Ʒ�������� */
	public String viewCategory(){
		if (model.getId()!=null){
			//����ҵ���߼����װ��ָ������Ʒ���
			Category tempCategory = service.loadCategory(model.getId());
			if (tempCategory!=null){
				model.setCateName(tempCategory.getCateName().trim());
				if (tempCategory.getCategory()!=null){
					parentid = tempCategory.getCategory().getId().toString();
				}else{
					parentid = "-1";
				}				
				return SUCCESS;
			}else{
				actionMsg = getText("category_view_fail");
				return "toBrowseCategory";
			}	
		}else{
			actionMsg = getText("category_view_fail");
			return "toBrowseCategory";
		}		
	}
	
	/** ����װ����Ʒ�������� */
	public String editCategory(){
		if (model.getId()!=null){
			//����ҵ���߼����װ��ָ������Ʒ���
			Category tempCategory = service.loadCategory(model.getId());
			if (tempCategory!=null){
				model.setCateName(tempCategory.getCateName().trim());
				if (tempCategory.getCategory()!=null){
					parentid = tempCategory.getCategory().getId().toString();
				}else{
					parentid = "-1";
				}
				return SUCCESS;
			}else{
				actionMsg = getText("category_edit_fail");
				return "toBrowseCategory";
			}	
		}else{
			actionMsg = getText("category_edit_fail");
			return "toBrowseCategory";
		}		
	}
	
	/** ���������Ʒ�������� */
	public String updateCategory(){
		//����ҵ���߼����װ��ָ������Ʒ���
		Category tempCategory = service.loadCategory(model.getId());
		tempCategory.setCateName(model.getCateName());
		if(parentid!=null && Integer.parseInt(parentid)>0){
			tempCategory.setCategory(service.loadCategory(Integer.valueOf(parentid)));
		}else{
			tempCategory.setCategory(null);
		}
		if (service.saveOrUpdateCategory(tempCategory)){//����ҵ���߼��������ָ������Ʒ���
			addActionMessage(getText("category_edit_succ"));
		}else{
			addActionMessage(getText("category_edit_fail"));
		}
		return INPUT;		
	}
	
	/** ����һ����Ʒ���������б����� */
	public String listCategory(){
		categoryList = service.listCategory();//����ҵ���߼����ȡ��һ����Ʒ����б�
		Category firstnode = new Category();
		firstnode.setId(-1);
		firstnode.setCateName("���ϼ����");
		categoryList.add(0,firstnode);
		return SUCCESS;		
	}	

	public String getActionMsg() {
		return actionMsg;
	}
	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}
	public CategoryService getService() {
		return service;
	}
	public void setService(CategoryService service) {
		this.service = service;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
}
