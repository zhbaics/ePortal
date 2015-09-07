package com.eportal.service;

import java.util.List;

import com.eportal.ORM.*;

/** ���ﳵ����ҵ���߼��ӿ� */
public interface CartService {

	/** ������ﳵ */
	public List<Cart> browseCart();	
	/** װ��ָ���Ĺ��ﳵ */	
	public Cart loadCart(Integer id);
	/** װ��ָ����Ա�Ĺ��ﳵ */	
	public Cart loadCart(Member member);	
	/** ɾ��ָ���Ĺ��ﳵ */	
	public boolean delCart(Integer id);
	/** ���ָ���Ĺ��ﳵ */	
	public boolean clearCart(Integer id);		
	/** �������޸Ĺ��ﳵ */
	public boolean saveOrUpdateCart(Cart cart);
	
	/** ���ָ�����ﳵ��ѡ����¼ */
	public List<Cartselectedmer> browseCartselectedmer(Cart cart);	
	/** װ��ָ����ѡ����¼ */	
	public Cartselectedmer loadCartselectedmer(Integer id);
	/** װ������ָ�����ﳵ��ѡ����ָ����Ʒ��ѡ����¼ */	
	public Cartselectedmer loadCartselectedmer(Cart cart,Merchandise mer);	
	/** ɾ��ָ����ѡ����¼ */	
	public boolean delCartselectedmer(Integer id);	
	/** �������޸�ѡ����¼ */
	public boolean saveOrUpdateCartselectedmer(Cartselectedmer cartselectedmer);	
	
}
