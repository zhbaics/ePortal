package com.eportal.service;

import java.util.List;
import com.eportal.DAO.BaseDAO;
import com.eportal.ORM.Member;

/** ע���Ա����ҵ���߼��ӿ�ʵ�� */
public class MemberServiceImpl implements MemberService {
	/** ͨ������ע��DAO���ʵ�� */
	BaseDAO dao;

	/** ע���Ա��¼ */
	public Member memberLogin(String loginName, String loginPwd){
		String hql = "from Member as a where a.loginName='"+loginName+"' and a.loginPwd='"+loginPwd+"'";
		return (Member)dao.loadObject(hql);
	}

	/** �������޸�ע���Ա */	
	public boolean saveOrUpdateMember(Member member){
		boolean status = false;
		try{
			dao.saveOrUpdate(member);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** ���ע���Ա */
	@SuppressWarnings("unchecked")
	public List<Member> browseMember(){
		return dao.listAll("Member");
	}

	/** ɾ��ָ����ע���Ա */
	public boolean delMember(Integer id){
		boolean status = false;
		try{
			dao.delById(Member.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** װ��ָ����ע���Ա */
	public Member loadMember(Integer id){
		return (Member)dao.loadById(Member.class, id);
	}
	
	/** ���ָ����¼�˺��Ƿ���� */
	public boolean isEnable(String loginname) {
		if (dao.countQuery("select count(*) from Member as a where a.loginName='"+loginname+"'")>0){
			return false;
		}else{
			return true;
		}
	}	

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
