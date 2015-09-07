package com.eportal.struts.interceptor;

import org.apache.struts2.ServletActionContext;
import com.eportal.ORM.Admin;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/** session���ڡ���¼��Ч�Լ�������Ȩ����֤������ */
public class LoginedCheckInterceptor extends AbstractInterceptor {

	/** �������󲢽��е�¼��Ч����֤ */
	public String intercept(ActionInvocation ai) throws Exception {
		//ȡ�������URL
		String url = ServletActionContext.getRequest().getRequestURL().toString();
		String prim = null;
		Admin admin = null;
		int index = 0;
		//��֤Session�Ƿ����
		if(!ServletActionContext.getRequest().isRequestedSessionIdValid()){
			//session����,ת��session������ʾҳ,������ת����¼ҳ��
			return "tologin";
		}else{
			//�Ե�¼��ע������ֱ�ӷ���,��������
			if (url.indexOf("admin_login.action")!=-1 || url.indexOf("admin_logout.action")!=-1){
				return ai.invoke();
			}else{
				admin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
				//��֤�Ƿ��Ѿ���¼
				if (admin==null){
					//��δ��¼,��ת����¼ҳ��
					return "tologin";
				}else{
					//����ģ����Ȩ��λӳ��,���ֿ�����ǰ̨����������������������ռ�"/admin"��ʾ����
					if (url.indexOf("/admin_")!=-1 || url.indexOf("/updateAdmin")!=-1){//ϵͳ�û�����
						index = 2; //Ȩ��λΪ2
					}else if (url.indexOf("/columns_")!=-1 || url.indexOf("/updateColumns")!=-1){//������Ŀ����
						index = 3; //Ȩ��λΪ3
					}else if (url.indexOf("/news_")!=-1 || url.indexOf("/preAddNews")!=-1 || url.indexOf("/updateNews")!=-1 || url.indexOf("/publisNews")!=-1){//���Ź���
						index = 4; //Ȩ��λΪ4
					}else if (url.indexOf("/rule_")!=-1 || url.indexOf("/preAddNewsrule")!=-1 || url.indexOf("/updateNewsrule")!=-1){ //���Ųɼ�
						index = 5; //Ȩ��λΪ5
					}else if (url.indexOf("/level_")!=-1 || url.indexOf("/updateMemberlevel")!=-1){//��Ա�������
						index = 6; //Ȩ��λΪ6
					}else if (url.indexOf("/member_")!=-1 || url.indexOf("/preAddMember")!=-1 || url.indexOf("/admin/updateMember")!=-1){//��Ա����
						index = 6; //Ȩ��λΪ6
					}else if (url.indexOf("/cate_")!=-1 || url.indexOf("/updateCategory")!=-1){//��Ʒ�������
						index = 7; //Ȩ��λΪ7
					}else if (url.indexOf("/mer_")!=-1 || url.indexOf("/preAddMerchandise")!=-1 || url.indexOf("/updateMerchandise")!=-1 || url.indexOf("/publisMerchandise")!=-1){//��Ʒ����
						index = 8; //Ȩ��λΪ8
					}else if (url.indexOf("/orders_")!=-1 || url.indexOf("/admin/updateOrdersStatus")!=-1){//��������
						index = 9; //Ȩ��λΪ9
					}else if (url.indexOf("/traffic_")!=-1){//����ͳ��
						index = 10; //Ȩ��λΪ10
					}
					//ȡ�õ�ǰ�û��Ĳ���Ȩ��
					prim = admin.getPrivileges().trim();
					//����Ȩ����֤
					if (index>0){
						if (prim.substring(0,1).equals("1") || prim.substring(index-1,index).equals("1")){
							//��֤ͨ��,����
							return ai.invoke();
						}else{
							//��֤ʧ��,ת��Ȩ����֤ʧ����ʾҳ
							return "noprim";
						}
					}else{
						//��������ҪȨ����֤������ֱ�ӷ���
						return ai.invoke();
					}					
				}				
			}			
		}
	}
}