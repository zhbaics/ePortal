package com.eportal.service;

import java.util.List;
import com.eportal.ORM.Category;

/** ��Ʒ�������ҵ���߼��ӿ� */
public interface CategoryService {
	/** �����Ʒ���� */
	public List<Category> browseCategory();
	/** һ����Ʒ�����б� */
	public List<Category> listCategory();	
	/** �¼���Ʒ�����б� */
	public List<Category> listChildCategory(Category category);		
	/** װ��ָ������Ʒ���� */	
	public Category loadCategory(Integer id);	
	/** ɾ��ָ������Ʒ���� */	
	public boolean delCategory(Integer id);	
	/** �������޸���Ʒ���� */
	public boolean saveOrUpdateCategory(Category category);
}
