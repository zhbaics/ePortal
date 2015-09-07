package com.eportal.service;

import java.util.List;
import com.eportal.DAO.BaseDAO;
import com.eportal.ORM.Memberlevel;

/** ��Ա�������ҵ���߼��ӿ�ʵ�� */
public class MemberLevelServiceImpl implements MemberLevelService {
	/** ͨ������ע��DAO���ʵ�� */
	BaseDAO dao;

	/** �������޸Ļ�Ա���� */	
	public boolean saveOrUpdateMemberlevel(Memberlevel memberlevel){
		boolean status = false;
		try{
			dao.saveOrUpdate(memberlevel);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** �����Ա���� */
	@SuppressWarnings("unchecked")
	public List<Memberlevel> browseMemberlevel(){
		return dao.listAll("Memberlevel");
	}

	/** ɾ��ָ���Ļ�Ա���� */
	public boolean delMemberlevel(Integer id){
		boolean status = false;
		try{
			dao.delById(Memberlevel.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** װ��ָ���Ļ�Ա���� */
	public Memberlevel loadMemberlevel(Integer id){
		return (Memberlevel)dao.loadById(Memberlevel.class, id);
	}
	
	/** ȡ�ó�ʼ��Ա���� */
	@SuppressWarnings("unchecked")
	public Memberlevel getInitMemberlevel() {
		List<Memberlevel> list = dao.query("from Memberlevel as a order by a.favourable asc", 1, 1);
		if (list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}		
	}	

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
