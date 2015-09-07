package com.eportal.struts.action;

import java.io.UnsupportedEncodingException;
import java.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import com.eportal.ORM.*;
import com.eportal.service.*;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/** ������������� */
@SuppressWarnings("serial")
public class OrdersAction extends ActionSupport implements ModelDriven<Orders>{
	/** ͨ������ע��OrderService��CartService��MemberService���ʵ�� */
	OrderService service;
	CartService cartService;
	MemberService memberService;
	
	/** �����������������г��õĲ���ֵ */
	private String actionMsg;	//Action�䴫�ݵ���Ϣ����
	private List<Orders> ordersList;//�����б�
	private List<Cartselectedmer> selList;//ѡ����¼�б�
	
	//����ģ������
	private Orders model=new Orders();//���ڷ�װ��������ģ��
	public Orders getModel() {
		return model;
	}
		
	/** ��������������� */
	public String browseOrders(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		ordersList = service.browseOrders();
		return SUCCESS;
	}
	
	/** ���������ǰ��Ա���ж��������� */
	public String loadAllOrders(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		Member member = (Member)ServletActionContext.getRequest().getSession().getAttribute("member");
		if (member!=null){//��ǰ��Ա���ж�������
			ordersList = service.loadAllOrders(member);
		}else{//��Ա��δ��¼���¼ʧЧ
			addActionMessage(getText("orders_add_notlogin"));
		}
		return SUCCESS;
	}	
	
	/** ���������������� */
	public String addOrders(){
		Member member = (Member)ServletActionContext.getRequest().getSession().getAttribute("member");
		Orders order = null;
		Cart cart = null;
		if (member!=null){
			order = service.loadOrders(member);
			if (order==null){//������޶�����Ϊ���û�����һ������
				//װ�ظû�Ա��δ���ʹ��ﳵ
				cart = cartService.loadCart(member);
				if (cart==null){//���ﳵ�����ڣ��������ɶ���
					addActionMessage(getText("orders_add_nocart"));
				}else{
					if(cart.getMoney()>0){//���ﳵ���д�������Ʒ�������ɶ���
						order = new Orders();
						order.setCart(cart);//�����뵱ǰ���ﳵ����
						order.setMember(member);//�����뵱ǰ��Ա����
						order.setOrderDate(new Date());
						order.setOrderStatus(0);//δ����״̬
						order.setOrderNo(String.valueOf(System.currentTimeMillis()));//������
						if (service.saveOrUpdateOrders(order)){//���������ɹ�
							try {
								//���ٸ���Դ�����е��������Ե�Ŀ�������
								BeanUtils.copyProperties(model, order);
								//ѡ����¼�嵥
								selList = cartService.browseCartselectedmer(cart);
							} catch (Exception e) {
								e.printStackTrace();
							}							
						}else{//��������ʧ��
							addActionMessage(getText("orders_add_fail"));
						}						
					}else{//���ﳵ������Ʒ���������ɶ���
						addActionMessage(getText("orders_add_nocart"));
					}
				}				
			}else{//�Ѿ�����һ��δ���ʶ���
				try {
					//���ٸ���Դ�����е��������Ե�Ŀ�������
					BeanUtils.copyProperties(model, order);
					//ѡ����¼�嵥
					selList = cartService.browseCartselectedmer(order.getCart());
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
		}else{//��Ա��δ��¼,�޷��������߹���
			addActionMessage(getText("orders_add_notlogin"));
		}
		return SUCCESS;
	}
	
	/** ����鿴�������� */
	public String viewOrders(){
		if (model.getId()!=null){
			Orders order = service.loadOrders(model.getId());
			if(order==null){
				addActionMessage(getText("orders_view_fail"));
				return INPUT;
			}else{
				try {
					//���ٸ���Դ�����е��������Ե�Ŀ�������
					BeanUtils.copyProperties(model, order);
					//ѡ����¼�嵥
					selList = cartService.browseCartselectedmer(order.getCart());
					return SUCCESS;
				} catch (Exception e) {
					e.printStackTrace();
					return INPUT;
				}				
			}
		}else{
			addActionMessage(getText("orders_view_fail"));
			return INPUT;
		}		
	}
	
	/** ����ɾ��ָ���������� */
	public String delOrders(){
		if (model.getId()!=null){
			if (service.delOrders(model.getId())){
				actionMsg = getText("orders_delete_succ");
			}else{
				actionMsg = getText("orders_delete_fail");
			}			
		}else{
			actionMsg = getText("orders_delete_fail");
		}
		return INPUT;	
	}
	
	/** �����޸�ָ������״̬������ */
	public String updateOrdersStatus(){
		if (model.getId()!=null){
			Orders order = service.loadOrders(model.getId());
			if(order==null){
				actionMsg = getText("orders_update_fail");
			}else{
				order.setOrderStatus(model.getOrderStatus());
				if(service.saveOrUpdateOrders(order)){
					actionMsg = getText("orders_update_succ");
					//����ᵥ����Ϊ�ö�����ע���Ա������Ӧ�Ļ���
					if (model.getOrderStatus().intValue()==4){
						Member member = order.getMember();
						int integral = member.getIntegral().intValue()+(int)(order.getCart().getMoney()/10);
						member.setIntegral(integral);
						memberService.saveOrUpdateMember(member);						
						//����ǻ�Ա�ύ�Ľᵥ������ͬ������session�еĻ�Ա����
						if (ServletActionContext.getRequest().getSession().getAttribute("member")!=null){
							member = (Member)ServletActionContext.getRequest().getSession().getAttribute("member");
							member.setIntegral(integral);
							ServletActionContext.getRequest().getSession().setAttribute("member",member);
						}
					}
				}else{
					actionMsg = getText("orders_update_fail");
				}
			}		
		}else{
			actionMsg = getText("orders_update_fail");
		}
		return INPUT;	
	}
	
	/** �����ύָ������������ */
	public String submitOrders(){
		if (model.getId()!=null){
			Orders order = service.loadOrders(model.getId());
			if(order==null){
				actionMsg = getText("orders_submit_fail");
			}else{
				//�жϻ�Ա����ϵ�����Ƿ�����
				Member member = order.getMember();
				if (member.getMemberName()==null || member.getPhone()==null || member.getAddress()==null || member.getZip()==null){
					actionMsg = getText("orders_submit_warning");
				}else{
					Cart cart = order.getCart();
					cart.setCartStatus(1);//���ﳵ״̬���ó�1����ʾ�Ѿ�����
					if(cartService.saveOrUpdateCart(cart)){
						order.setOrderStatus(1);//����״̬���ó�1����ʾ�ɹ��ύ��δ����					
						if(service.saveOrUpdateOrders(order)){
							addActionMessage(getText("orders_submit_succ"));
							try {
								//���ٸ���Դ�����е��������Ե�Ŀ�������
								BeanUtils.copyProperties(model, order);
							} catch (Exception e) {
								e.printStackTrace();
							}						
							return SUCCESS;
						}else{
							actionMsg = getText("orders_submit_fail");
						}						
					}else{
						actionMsg = getText("orders_submit_fail");
					}
				}
			}		
		}else{
			actionMsg = getText("orders_submit_fail");
		}
		return INPUT;	
	}	

	public OrderService getService() {
		return service;
	}

	public void setService(OrderService service) {
		this.service = service;
	}

	public CartService getCartService() {
		return cartService;
	}

	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}

	public String getActionMsg() {
		return actionMsg;
	}

	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}

	public List<Orders> getOrdersList() {
		return ordersList;
	}

	public void setOrdersList(List<Orders> ordersList) {
		this.ordersList = ordersList;
	}

	public List<Cartselectedmer> getSelList() {
		return selList;
	}

	public void setSelList(List<Cartselectedmer> selList) {
		this.selList = selList;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}	
}