package com.eportal.service;

import java.util.List;
import com.eportal.ORM.News;

/** ���Ź���ҵ���߼��ӿ� */
public interface NewsService {
	/** ������� */
	public List<News> browseNews();
	/** װ��ָ�������� */	
	public News loadNews(Integer id);	
	/** ɾ��ָ�������� */	
	public boolean delNews(Integer id);	
	/** �������޸����� */
	public boolean saveOrUpdateNews(News News);
}
