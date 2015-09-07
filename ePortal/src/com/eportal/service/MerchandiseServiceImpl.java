package com.eportal.service;

import java.util.List;
import com.eportal.DAO.BaseDAO;
import com.eportal.ORM.Merchandise;

/** ��Ʒ����ҵ���߼��ӿ�ʵ�� */
public class MerchandiseServiceImpl implements MerchandiseService {
	/** ͨ������ע��DAO���ʵ�� */
	BaseDAO dao;

	/** �������޸���Ʒ */	
	public boolean saveOrUpdateMerchandise(Merchandise Merchandise){
		boolean status = false;
		try{
			dao.saveOrUpdate(Merchandise);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** �����Ʒ */
	@SuppressWarnings("unchecked")
	public List<Merchandise> browseMerchandise(){
		return dao.listAll("Merchandise");
	}
	
	/** ɾ��ָ������Ʒ */
	public boolean delMerchandise(Integer id){
		boolean status = false;
		try{
			dao.delById(Merchandise.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** װ��ָ������Ʒ */
	public Merchandise loadMerchandise(Integer id){
		return (Merchandise)dao.loadById(Merchandise.class, id);
	}	

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
