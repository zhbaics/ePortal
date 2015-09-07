package com.eportal.service;

import java.util.List;
import com.eportal.DAO.BaseDAO;
import com.eportal.ORM.Newscolumns;

/** ������Ŀ����ҵ���߼��ӿ�ʵ�� */
public class ColumnsServiceImpl implements ColumnsService {
	/** ͨ������ע��DAO���ʵ�� */
	BaseDAO dao;

	/** �������޸�������Ŀ */	
	public boolean saveOrUpdateColumns(Newscolumns columns){
		boolean status = false;
		try{
			dao.saveOrUpdate(columns);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** ���������Ŀ */
	@SuppressWarnings("unchecked")
	public List<Newscolumns> browseColumns(){
		return dao.listAll("Newscolumns");
	}
	
	/** һ��������Ŀ�б� */
	@SuppressWarnings("unchecked")
	public List<Newscolumns> listColumns(){
		return dao.query("from Newscolumns as a where a.newscolumns is null");
	}
	
	/** �¼�������Ŀ�б� */
	@SuppressWarnings("unchecked")
	public List<Newscolumns> listChildColumns(Newscolumns columns){
		if (columns==null){
			return null;
		}else{
			return dao.query("from Newscolumns as a where a.newscolumns.id="+columns.getId());
		}		
	}

	/** ɾ��ָ����������Ŀ */
	public boolean delColumns(Integer id){
		boolean status = false;
		try{
			dao.delById(Newscolumns.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** װ��ָ����������Ŀ */
	public Newscolumns loadColumns(Integer id){
		return (Newscolumns)dao.loadById(Newscolumns.class, id);
	}	

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
