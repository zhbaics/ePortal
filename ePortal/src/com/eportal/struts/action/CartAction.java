package com.eportal.struts.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import com.eportal.ORM.*;
import com.eportal.service.*;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/** ���ﳵ��������� */
@SuppressWarnings("serial")
public class CartAction extends ActionSupport implements ModelDriven<Cart>{
	/** ͨ������ע��CartService��MerchandiseService���ʵ�� */
	CartService service;
	MerchandiseService merService;
	
	/** ���ﳵ�������������г��õĲ���ֵ */
	private String actionMsg;	//Action�䴫�ݵ���Ϣ����
	private List<Cart> cartList;//���ﳵ�б�
	private List<Cartselectedmer> selList;//ѡ����¼�б�
	private Integer merId;		//ѡ������ƷID
	private Integer number;		//ѡ������
	private Integer selId;		//ѡ����¼ID
	
	//����ģ������
	private Cart model=new Cart();//���ڷ�װ���ﳵ����ģ��
	public Cart getModel() {
		return model;
	}
		
	/** ����������ﳵ���� */
	public String browseCart(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		cartList = service.browseCart();//����ҵ���߼����ȡ�ù��ﳵ�б�
		return SUCCESS;
	}
	
	/** ���������� */
	public String addToCart(){
		Member member = (Member)ServletActionContext.getRequest().getSession().getAttribute("member");
		Cart cart = null;
		Merchandise mer = null;
		Cartselectedmer sel = null;
		double price = 0;
		if (member!=null){
			//װ�ص�ǰע���Ա�Ĺ��ﳵ
			cart = service.loadCart(member);
			if (cart==null){//������޹��ﳵ��Ϊ���û�����һ�����ﳵ
				cart = new Cart();
				//���ﳵ�ĳ�ʼ״̬Ϊ0,��ʾ��δ����
				cart.setCartStatus(0);
				//���ﳵĬ���ܽ��Ϊ0
				cart.setMoney(Double.valueOf(0));
				//���ﳵ�뵱ǰע���Ա���й���
				cart.setMember(member);
				//�־û����ﳵ
				if (!service.saveOrUpdateCart(cart)){
					actionMsg = getText("cart_add_fail");
				}				
			}
			//װ�ر�ѡ������Ʒ
			mer = merService.loadMerchandise(merId);
			if (mer!=null){//����Ʒ��Ч
				//����Ƿ��Ѵ�����ͬ��ѡ����¼
				sel = service.loadCartselectedmer(cart, mer);
				if (sel==null){//��������ͬ��ѡ����¼
					//����һ��ѡ����¼
					sel = new Cartselectedmer();
					sel.setCart(cart);
					sel.setMerchandise(mer);
					sel.setNumber(1);//Ĭ��ѡ������Ϊ1
					//��Ʒ����
					if (mer.getSpecial().intValue()==1){//�ؼ���Ʒ,����۸�Ϊ�ؼ�
						price = mer.getSprice();
					}else{//���ؼ���Ʒ,����۸�Ϊ�Żݺ���г���
						price = mer.getPrice()*(100-member.getMemberlevel().getFavourable())/100;
					}
					sel.setPrice(price);
					sel.setMoney(price);
					//���¼��㹺�ﳵ�ܽ��
					cart.setMoney(cart.getMoney().doubleValue()+price);
				}else{//�Ѵ�����ͬ��ѡ����¼
					sel.setNumber(sel.getNumber()+1);//ѡ��������1
					sel.setMoney(sel.getMoney()+sel.getPrice());//����һ������
					//���¼��㹺�ﳵ�ܽ��
					cart.setMoney(cart.getMoney().doubleValue()+sel.getPrice());
				}
				//�־û����ﳵ����ѡ����¼
				if (service.saveOrUpdateCart(cart)){
					if (service.saveOrUpdateCartselectedmer(sel)){
						actionMsg = getText("cart_add_succ");
					}else{
						actionMsg = getText("cart_add_fail");				
					}					
				}else{
					actionMsg = getText("cart_add_fail");
				}
					
			}else{//ָ����Ʒ������
				actionMsg = getText("cart_add_fail");
			}		
		}else{//��Ա��δ��¼,�޷��������߹���
			actionMsg = getText("cart_add_fail");
		}
		return "toViewCart";
	}
	
	/** ����鿴���ﳵ���� */
	public String viewCart(){
		//���ղ���������Action���ݵ���Ϣ
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}		
		if (model.getId()!=null){//��̨����Ա�鿴���ﳵ����
			Cart tempCart = service.loadCart(model.getId());
			if (tempCart!=null){
				try {
					//���ٸ���Դ�����е��������Ե�Ŀ�������
					BeanUtils.copyProperties(model, tempCart);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//װ�ظù��ﳵ��Ӧ����Ʒѡ����¼��
				selList = service.browseCartselectedmer(tempCart);
				return SUCCESS;
			}else{
				actionMsg = getText("cart_view_fail");
				return "toBrowseCart";
			}	
		}else{//ע���Ա�鿴�Լ��Ĺ��ﳵ����
			Member member = (Member)ServletActionContext.getRequest().getSession().getAttribute("member");
			if (member!=null){
				//װ�ص�ǰע���Ա�Ĺ��ﳵ
				Cart cart = service.loadCart(member);
				if (cart==null){//������޹��ﳵ��Ϊ���û�����һ�����ﳵ
					cart = new Cart();
					//���ﳵ�ĳ�ʼ״̬Ϊ0,��ʾ��δ����
					cart.setCartStatus(0);
					//���ﳵĬ���ܽ��Ϊ0
					cart.setMoney(Double.valueOf(0));
					//���ﳵ�뵱ǰע���Ա���й���
					cart.setMember(member);
					//�־û����ﳵ
					if (!service.saveOrUpdateCart(cart)){
						addActionMessage(getText("cart_create_fail"));
					}				
				}else{//װ�ص�ǰ���ﳵ����Ʒѡ����¼
					selList = service.browseCartselectedmer(cart);					
				}
				try {
					//���ٸ���Դ�����е��������Ե�Ŀ�������
					BeanUtils.copyProperties(model, cart);
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}else{
				addActionMessage(getText("cart_view_fail"));
			}
			return SUCCESS;
		}		
	}
	
	/** ����ɾ��ָ��ѡ����¼���� */
	public String delCartselectedmer(){
		Cartselectedmer sel = null;
		Cart cart = null;
		double money = 0;
		if (selId!=null){
			//װ��ָ����ѡ����¼
			sel = service.loadCartselectedmer(selId);
			if (sel!=null){
				money = sel.getMoney();
				cart = sel.getCart();
				//���¹��ﳵ���ܽ��
				cart.setMoney(cart.getMoney().doubleValue()-money);
				if (service.saveOrUpdateCart(cart)){
					//ɾ��ָ����ѡ����¼
					if (service.delCartselectedmer(selId)){
						actionMsg = getText("cart_delselect_succ");
					}else{
						actionMsg = getText("cart_delselect_fail");
					}					
				}else{
					actionMsg = getText("cart_delselect_fail");
				}				
			}else{
				actionMsg = getText("cart_delselect_fail");
			}
			
		}else{
			actionMsg = getText("cart_delselect_fail");
		}
		return INPUT;		
	}
	
	/** �����޸�ѡ���������� */
	public String updateSelectedNumber(){
		Cartselectedmer sel = null;
		Cart cart = null;
		double money = 0;
		if (selId!=null && number!=null){
			//װ��ָ����ѡ����¼
			sel = service.loadCartselectedmer(selId);
			if (sel!=null){
				money = sel.getPrice()*number;
				//���¹��ﳵ���ܽ��
				cart = sel.getCart();
				cart.setMoney(cart.getMoney().doubleValue()-sel.getMoney()+money);				
				//����ѡ�����������ϼ�
				sel.setNumber(number);
				sel.setMoney(money);
				if (service.saveOrUpdateCart(cart)){
					//����ָ����ѡ����¼
					if (service.saveOrUpdateCartselectedmer(sel)){
						actionMsg = getText("cart_updatenumber_succ");
					}else{
						actionMsg = getText("cart_updatenumber_fail");
					}					
				}else{
					actionMsg = getText("cart_updatenumber_fail");
				}				
			}else{
				actionMsg = getText("cart_updatenumber_fail");
			}			
		}else{
			actionMsg = getText("cart_updatenumber_fail");
		}
		return INPUT;		
	}
	
	/** ������չ��ﳵ���� */
	public String clearCart(){
		if (model.getId()!=null){
			//װ��ָ���Ĺ��ﳵ
			Cart cart = service.loadCart(model.getId());
			if (cart!=null){		
				//���ﳵ�ܽ������
				cart.setMoney(Double.valueOf(0));
				if (service.saveOrUpdateCart(cart)){
					//��յ�ǰ���ﳵ������ѡ����¼
					if (service.clearCart(model.getId())){
						actionMsg = getText("cart_clear_succ");
					}else{
						actionMsg = getText("cart_clear_fail");
					}									
				}else{
					actionMsg = getText("cart_clear_fail");	
				}				
			}else{
				actionMsg = getText("cart_clear_fail");
			}				
		}else{
			actionMsg = getText("cart_clear_fail");
		}
		return "toViewCart";
	}

	public CartService getService() {
		return service;
	}
	public void setService(CartService service) {
		this.service = service;
	}
	public String getActionMsg() {
		return actionMsg;
	}

	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}

	public List<Cart> getCartList() {
		return cartList;
	}

	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}

	public Integer getMerId() {
		return merId;
	}

	public void setMerId(Integer merId) {
		this.merId = merId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getSelId() {
		return selId;
	}

	public void setSelId(Integer selId) {
		this.selId = selId;
	}

	public List<Cartselectedmer> getSelList() {
		return selList;
	}

	public void setSelList(List<Cartselectedmer> selList) {
		this.selList = selList;
	}

	public MerchandiseService getMerService() {
		return merService;
	}

	public void setMerService(MerchandiseService merService) {
		this.merService = merService;
	}
}