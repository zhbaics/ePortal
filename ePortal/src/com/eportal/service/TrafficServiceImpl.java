package com.eportal.service;

import java.util.List;
import com.eportal.DAO.BaseDAO;

/** ����ͳ��ҵ���߼��ӿ�ʵ�� */
public class TrafficServiceImpl implements TrafficService {
	/** ͨ������ע��DAO���ʵ�� */
	BaseDAO dao;
	
	/** ������ʼ�¼ */
	public List browseTraffic(String hql) {
		return dao.query(hql);
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
