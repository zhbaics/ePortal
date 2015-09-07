package com.eportal.struts.action;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import com.eportal.ORM.Memberlevel;
import com.eportal.service.MemberLevelService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/** ��Ա����������� */
@SuppressWarnings("serial")
public class MemberlevelAction extends ActionSupport implements ModelDriven<Memberlevel>{
	/** ͨ������ע��MemberLevelService���ʵ�� */
	MemberLevelService service;
	
	/** ��Ա����������������г��õĲ���ֵ */
	private String actionMsg;	//Action�䴫�ݵ���Ϣ����
	private List<Memberlevel> memberLevelList;	//��Ա�����б�
	
	//����ģ������
	private Memberlevel model=new Memberlevel();//���ڷ�װ��Ա��������ģ��
	public Memberlevel getModel() {
		return model;
	}
		
	/** ���������Ա�������� */
	public String browseMemberlevel(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		memberLevelList = service.browseMemberlevel();//����ҵ���߼����ȡ�û�Ա�����б�
		return SUCCESS;
	}
	
	/** ����������Ա�������� */
	public String addMemberlevel(){
		if (service.saveOrUpdateMemberlevel(model)){//����ҵ���߼�������������Ļ�Ա����
			addActionMessage(getText("level_add_succ"));
		}else{
			addActionMessage(getText("level_add_fail"));
		}
		return SUCCESS;
	}
	
	/** ����ɾ����Ա�������� */
	public String delMemberlevel(){
		if (model.getId()!=null){
			if (service.delMemberlevel(model.getId())){//����ҵ���߼����ɾ��ָ���Ļ�Ա����
				actionMsg = getText("level_del_succ");
			}else{
				actionMsg = getText("level_del_fail");
			}			
		}else{
			actionMsg = getText("level_del_fail");
		}
		return "toBrowseMemberlevel";
	}
	
	/** ����鿴��Ա�������� */
	public String viewMemberlevel(){
		if (model.getId()!=null){
			//����ҵ���߼����װ��ָ���Ļ�Ա����	
			Memberlevel tempMemberlevel = service.loadMemberlevel(model.getId());
			if (tempMemberlevel!=null){
				try {
					//���ٸ���Դ�����е��������Ե�Ŀ�������
					BeanUtils.copyProperties(model, tempMemberlevel);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return SUCCESS;
			}else{
				actionMsg = getText("level_view_fail");
				return "toBrowseMemberlevel";
			}	
		}else{
			actionMsg = getText("level_view_fail");
			return "toBrowseMemberlevel";
		}		
	}
	
	/** ����װ�ػ�Ա�������� */
	public String editMemberlevel(){
		if (model.getId()!=null){
			//����ҵ���߼����װ��ָ���Ļ�Ա����
			Memberlevel tempMemberlevel = service.loadMemberlevel(model.getId());
			if (tempMemberlevel!=null){
				try {
					//���ٸ���Դ�����е��������Ե�Ŀ�������
					BeanUtils.copyProperties(model, tempMemberlevel);
				} catch (Exception e) {
					e.printStackTrace();
				}				
				return SUCCESS;
			}else{
				actionMsg = getText("level_edit_fail");
				return "toBrowseMemberlevel";
			}	
		}else{
			actionMsg = getText("level_edit_fail");
			return "toBrowseMemberlevel";
		}		
	}
	
	/** ������»�Ա�������� */
	public String updateMemberlevel(){		
		if (service.saveOrUpdateMemberlevel(model)){//����ҵ���߼��������ָ���Ļ�Ա����
			addActionMessage(getText("level_edit_succ"));
		}else{
			addActionMessage(getText("level_edit_fail"));
		}
		return INPUT;		
	}	

	public String getActionMsg() {
		return actionMsg;
	}

	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}

	public MemberLevelService getService() {
		return service;
	}

	public void setService(MemberLevelService service) {
		this.service = service;
	}

	public List<Memberlevel> getMemberLevelList() {
		return memberLevelList;
	}

	public void setMemberLevelList(List<Memberlevel> memberLevelList) {
		this.memberLevelList = memberLevelList;
	}	
}
