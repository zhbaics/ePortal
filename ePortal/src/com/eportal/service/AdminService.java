package com.eportal.service;

import java.util.List;
import com.eportal.ORM.Admin;

/** ϵͳ�û�����ҵ���߼��ӿ� */
public interface AdminService {
	/** ϵͳ����Ա��¼ */
	public Admin adminLogin(String loginName,String loginPwd);	
	/** �������Ա */
	public List<Admin> browseAdmin();	
	/** װ��ָ���Ĺ���Ա */	
	public Admin loadAdmin(Integer id);	
	/** ɾ��ָ���Ĺ���Ա */	
	public boolean delAdmin(Integer id);	
	/** �������޸Ĺ���Ա */
	public boolean saveOrUpdateAdmin(Admin admin);	
}
