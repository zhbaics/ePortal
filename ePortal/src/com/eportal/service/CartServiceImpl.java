package com.eportal.service;

import java.util.List;
import com.eportal.DAO.BaseDAO;
import com.eportal.ORM.Cart;
import com.eportal.ORM.Cartselectedmer;
import com.eportal.ORM.Member;
import com.eportal.ORM.Merchandise;

/** ���ﳵ����ҵ���߼��ӿ�ʵ�� */
public class CartServiceImpl implements CartService {
	/** ͨ������ע��DAO���ʵ�� */
	BaseDAO dao;
	
	/** ������ﳵ */
	@SuppressWarnings("unchecked")
	public List<Cart> browseCart() {
		return dao.listAll("Cart");
	}

	/** ���ָ�����ﳵ��ѡ����¼ */
	@SuppressWarnings("unchecked")
	public List<Cartselectedmer> browseCartselectedmer(Cart cart) {
		String hql = "from Cartselectedmer as a where a.cart.id="+cart.getId()+" order by a.id desc";
		return dao.query(hql);
	}

	/** ���ָ���Ĺ��ﳵ */
	public boolean clearCart(Integer id) {
		boolean status = false;
		try{
			dao.update("delete from Cartselectedmer as a where a.cart.id="+id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** ɾ��ָ���Ĺ��ﳵ */
	public boolean delCart(Integer id) {
		boolean status = false;
		try{
			dao.delById(Cart.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** ɾ��ָ����ѡ����¼ */
	public boolean delCartselectedmer(Integer id) {
		boolean status = false;
		try{
			dao.delById(Cartselectedmer.class, id);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** װ��ָ���Ĺ��ﳵ */
	public Cart loadCart(Integer id) {
		return (Cart)dao.loadById(Cart.class, id);
	}

	/** װ��ָ����Ա�Ĺ��ﳵ */	
	public Cart loadCart(Member member) {		
		return (Cart)dao.loadObject("from Cart as a where a.member.id="+member.getId()+" and a.cartStatus=0");
	}

	/** װ��ָ����ѡ����¼ */
	public Cartselectedmer loadCartselectedmer(Integer id) {
		return (Cartselectedmer)dao.loadById(Cartselectedmer.class, id);
	}
	
	/** װ������ָ�����ﳵ��ѡ����ָ����Ʒ��ѡ����¼ */	
	public Cartselectedmer loadCartselectedmer(Cart cart, Merchandise mer) {
		return (Cartselectedmer)dao.loadObject("from Cartselectedmer as a where a.merchandise.id="+mer.getId()+" and a.cart.id="+cart.getId());
	}	

	/** �������޸Ĺ��ﳵ */
	public boolean saveOrUpdateCart(Cart cart) {
		boolean status = false;
		try{
			dao.saveOrUpdate(cart);
			status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return status;
	}

	/** �������޸�ѡ����¼ */
	public boolean saveOrUpdateCartselectedmer(Cartselectedmer cartselectedmer) {
		boolean status = false;
		try{
			dao.saveOrUpdate(cartselectedmer);
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
