package com.eportal.service;

import java.util.List;
import com.eportal.ORM.*;

/** ��������ҵ���߼��ӿ� */
public interface OrderService {

	/** ������� */
	public List<Orders> browseOrders();	
	/** װ��ָ���Ķ��� */	
	public Orders loadOrders(Integer id);
	/** װ��ָ����Ա��δ���ʶ��� */	
	public Orders loadOrders(Member member);	
	/** װ��ָ����Ա�����ж��� */	
	public List<Orders> loadAllOrders(Member member);	
	/** ɾ��ָ���Ķ��� */	
	public boolean delOrders(Integer id);
	/** �������޸Ķ��� */
	public boolean saveOrUpdateOrders(Orders order);
	
}
