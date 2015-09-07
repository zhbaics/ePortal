package com.eportal.service;

import java.util.List;
import com.eportal.ORM.Member;

/** ע���Ա����ҵ���߼��ӿ� */
public interface MemberService {
	/** ע���Ա��¼ */
	public Member memberLogin(String loginName,String loginPwd);	
	/** ���ע���Ա */
	public List<Member> browseMember();	
	/** װ��ָ����ע���Ա */	
	public Member loadMember(Integer id);	
	/** ɾ��ָ����ע���Ա */	
	public boolean delMember(Integer id);	
	/** �������޸�ע���Ա */
	public boolean saveOrUpdateMember(Member member);
	/** ���ָ����¼�˺��Ƿ���� */
	public boolean isEnable(String loginname);	
}
