package com.eportal.service;

import java.util.List;
import com.eportal.ORM.Memberlevel;

/** ��Ա�������ҵ���߼��ӿ� */
public interface MemberLevelService {
	/** �����Ա���� */
	public List<Memberlevel> browseMemberlevel();	
	/** װ��ָ���Ļ�Ա���� */	
	public Memberlevel loadMemberlevel(Integer id);	
	/** ɾ��ָ���Ļ�Ա���� */	
	public boolean delMemberlevel(Integer id);	
	/** �������޸Ļ�Ա���� */
	public boolean saveOrUpdateMemberlevel(Memberlevel memberlevel);
	/** ȡ�ó�ʼ��Ա���� */
	public Memberlevel getInitMemberlevel();		
} 
