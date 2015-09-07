package com.eportal.service;

import java.util.List;
import com.eportal.DAO.BaseDAO;
import com.eportal.ORM.Newsrule;

/** ���Ųɼ��������ҵ���߼��ӿ�ʵ�� */
public class CrawlServiceImpl implements CrawlService {
	/** ͨ������ע��DAO���ʵ�� */
	BaseDAO dao;

	/** �������޸����Ųɼ����� */	
	public boolean saveOrUpdateNewsrule(Newsrule rule){
		boolean status = false;
		try{
			dao.saveOrUpdate(rule);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** ������Ųɼ����� */
	@SuppressWarnings("unchecked")
	public List<Newsrule> browseNewsrule(){
		return dao.listAll("Newsrule");
	}

	/** ɾ��ָ�������Ųɼ����� */
	public boolean delNewsrule(Integer id){
		boolean status = false;
		try{
			dao.delById(Newsrule.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** װ��ָ�������Ųɼ����� */
	public Newsrule loadNewsrule(Integer id){
		return (Newsrule)dao.loadById(Newsrule.class, id);
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
