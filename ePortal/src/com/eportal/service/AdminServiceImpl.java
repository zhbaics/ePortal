package com.eportal.service;

import java.util.List;
import com.eportal.DAO.BaseDAO;
import com.eportal.ORM.Admin;

/** ϵͳ�û�����ҵ���߼��ӿ�ʵ�� */
public class AdminServiceImpl implements AdminService {
	/** ͨ������ע��DAO���ʵ�� */
	BaseDAO dao;

	/** ϵͳ����Ա��¼ */
	public Admin adminLogin(String loginName, String loginPwd){
		String hql = "from Admin as a where a.loginName='"+loginName+"' and a.loginPwd='"+loginPwd+"'";
		return (Admin)dao.loadObject(hql);
	}

	/** �������޸Ĺ���Ա */	
	public boolean saveOrUpdateAdmin(Admin admin){
		boolean status = false;
		try{
			dao.saveOrUpdate(admin);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** �������Ա */
	@SuppressWarnings("unchecked")
	public List<Admin> browseAdmin(){
		return dao.listAll("Admin");
	}

	/** ɾ��ָ���Ĺ���Ա */
	public boolean delAdmin(Integer id){
		boolean status = false;
		try{
			dao.delById(Admin.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** װ��ָ���Ĺ���Ա */
	public Admin loadAdmin(Integer id){
		return (Admin)dao.loadById(Admin.class, id);
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
