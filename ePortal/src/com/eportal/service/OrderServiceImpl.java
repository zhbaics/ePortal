package com.eportal.service;

import java.util.List;
import com.eportal.DAO.BaseDAO;
import com.eportal.ORM.*;

/** ��������ҵ���߼��ӿ�ʵ�� */
public class OrderServiceImpl implements OrderService {
	/** ͨ������ע��DAO���ʵ�� */
	BaseDAO dao;
	
	/** ������� */
	@SuppressWarnings("unchecked")
	public List<Orders> browseOrders() {
		return dao.query("from Orders as a where a.orderStatus>0 order by a.id desc");
	}

	/** ɾ��ָ���Ķ��� */
	public boolean delOrders(Integer id) {
		boolean status = false;
		try{
			dao.delById(Orders.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** װ��ָ���Ķ��� */
	public Orders loadOrders(Integer id) {
		return (Orders)dao.loadById(Orders.class, id);
	}

	/** װ��ָ����Ա�����ж��� */	
	@SuppressWarnings("unchecked")
	public List<Orders> loadAllOrders(Member member) {		
		return dao.query("from Orders as a where a.orderStatus>0 and a.member.id="+member.getId());
	}
	
	/** װ��ָ����Ա��δ���ʶ��� */		
	public Orders loadOrders(Member member) {
		return (Orders)dao.loadObject("from Orders as a where a.member.id="+member.getId()+" and a.orderStatus=0");
	}	

	/** �������޸Ķ��� */
	public boolean saveOrUpdateOrders(Orders order) {
		boolean status = false;
		try{
			dao.saveOrUpdate(order);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
