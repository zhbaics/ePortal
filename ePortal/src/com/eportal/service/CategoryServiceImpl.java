package com.eportal.service;

import java.util.List;
import com.eportal.DAO.BaseDAO;
import com.eportal.ORM.Category;

/** ��Ʒ�������ҵ���߼��ӿ�ʵ�� */
public class CategoryServiceImpl implements CategoryService {
	/** ͨ������ע��DAO���ʵ�� */
	BaseDAO dao;

	/** �������޸���Ʒ���� */	
	public boolean saveOrUpdateCategory(Category category){
		boolean status = false;
		try{
			dao.saveOrUpdate(category);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** �����Ʒ���� */
	@SuppressWarnings("unchecked")
	public List<Category> browseCategory(){
		return dao.listAll("Category");
	}
	
	/** һ����Ʒ�����б� */
	@SuppressWarnings("unchecked")
	public List<Category> listCategory(){
		return dao.query("from Category as a where a.category is null");
	}
	
	/** �¼���Ʒ�����б� */
	@SuppressWarnings("unchecked")
	public List<Category> listChildCategory(Category category){
		if (category==null){
			return null;
		}else{
			return dao.query("from Category as a where a.category.id="+category.getId());
		}		
	}

	/** ɾ��ָ������Ʒ���� */
	public boolean delCategory(Integer id){
		boolean status = false;
		try{
			dao.delById(Category.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** װ��ָ������Ʒ���� */
	public Category loadCategory(Integer id){
		return (Category)dao.loadById(Category.class, id);
	}	

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
