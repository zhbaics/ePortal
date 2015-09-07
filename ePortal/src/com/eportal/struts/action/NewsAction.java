package com.eportal.struts.action;

import java.io.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.eportal.ORM.*;
import com.eportal.service.*;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import com.eportal.util.*;

/** ���Ź�������� */
@SuppressWarnings("serial")
public class NewsAction extends ActionSupport implements ModelDriven<News>{
	/** ͨ������ע��NewsServiceImpl��ColumnsServiceImpl���ʵ�� */
	NewsService service;
	ColumnsService columnsService;
	
	/** ���Ź������������г��õĲ���ֵ */
	private String actionMsg;	//Action�䴫�ݵ���Ϣ����
	private List<News> newsList;//�����б�
	private String columnId;	//������ĿID
	private List<DoubleSelectNode> doubleSelectNodes;	//����������Ŀ�б�
	private String column1;	//��ǰѡ�еĵ�һ��������Ŀ
	private String column2;	//��ǰѡ�еĵڶ���������Ŀ
	
	/** �ϴ�ͼƬ�ļ������� */
	private File pic;	//�ϴ����ļ�
	private String picContentType;	//�ϴ��ļ�������
	private String picFileName;		//�ϴ��ļ����ļ���
	
	/** ��ǰ�����,��JSON��������첽���ظ��ͻ��� */
	private String jsonClicks = "0";
	
	//����ģ������
	private News model=new News();//���ڷ�װ��������ģ��
	public News getModel() {
		return model;
	}
	
	/** ��������������� */
	@SuppressWarnings("unchecked")
	public String browseNews(){
		if(actionMsg!=null){
			try {
				actionMsg = new String(actionMsg.getBytes("ISO8859-1"),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addActionMessage(actionMsg);
		}
		newsList = service.browseNews();//����ҵ���߼����ȡ�������б�
		return SUCCESS;
	}
	
	/** ���������������� */
	public String addNews(){
		try {
			//�Բ��ֲ���������֤����Ĭ��ֵ
			if (model.getClicks()==null){
				model.setClicks(0);
			}
			if (model.getPriority()==null){
				model.setPriority(0);
			}
			if (model.getFrom()==null||model.getFrom().trim().length()<1){
				model.setFrom("ԭ��");
			}
			if (model.getAuthor()==null||model.getAuthor().trim().length()<1){
				model.setAuthor("����");
			}
			model.setCdate(new Date());
			model.setEditor(((Admin)ServletActionContext.getRequest().getSession().getAttribute("admin")).getLoginName().trim());
			model.setHtmlPath("/html/news/"+Tools.getRndFilename()+".html");
			model.setStatus(0);

			//���������ݽ���Escape����
			model.setContent(Tools.escape(model.getContent().trim()));
			//����ͼƬ���ŵ��ϴ��ļ�
			if (model.getIsPicNews().intValue()==1){
				String tempfilename = Tools.getRndFilename()+Tools.getFileExtName(getPicFileName());
				String filename = ServletActionContext.getRequest().getRealPath("/upload").replaceAll("\\\\", "/")+"/"+tempfilename;
				System.out.println("filename="+filename);
				FileOutputStream fos = new FileOutputStream(filename);
				FileInputStream fis = new FileInputStream(getPic());
				byte[] buf = new byte[1024];
				int len = 0;
				while((len=fis.read(buf))>0){
					fos.write(buf,0,len);
				}
				if (fis!=null)fis.close();
				if (fos!=null)fos.close();
				model.setPicture("/upload/"+tempfilename);
			}
			
			//������Ӧ��������Ŀ
			if (column2!=null){
				model.setNewscolumns(columnsService.loadColumns(Integer.valueOf(column2)));
			}else if(column1!=null){
				model.setNewscolumns(columnsService.loadColumns(Integer.valueOf(column1)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		if (service.saveOrUpdateNews(model)){//����ҵ���߼������������������
			addActionMessage(getText("news_add_succ"));
		}else{
			addActionMessage(getText("news_add_fail"));
		}
		//����������Ŀ��������
		createDoubleSelect();		
		return SUCCESS;
	}
	
	/** ����ɾ���������� */
	public String delNews(){
		if (model.getId()!=null){
			if (service.delNews(model.getId())){//����ҵ���߼����ɾ��ָ��������
				actionMsg = getText("news_del_succ");
			}else{
				actionMsg = getText("news_del_fail");
			}			
		}else{
			actionMsg = getText("news_del_fail");
		}
		return "toBrowseNews";
	}
	
	/** ����鿴�������� */
	public String viewNews(){
		if (model.getId()!=null){
			//����ҵ���߼����װ��ָ��������
			News tempNews = service.loadNews(model.getId());
			if (tempNews!=null){
				try {
					//���ٸ���Դ�����е��������Ե�Ŀ�������
					BeanUtils.copyProperties(model, tempNews);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Newscolumns tmpColumn=tempNews.getNewscolumns();
				//ȡ��������Ŀ��ǰֵ
				if (tmpColumn.getNewscolumns()!=null){
					column2 = tmpColumn.getId().toString();
					column1 = tmpColumn.getNewscolumns().getId().toString();
				}else{
					column1 = tmpColumn.getId().toString();
				}
				//����������Ŀ��������
				createDoubleSelect();
				return SUCCESS;
			}else{
				actionMsg = getText("news_view_fail");
				return "toBrowseNews";
			}	
		}else{
			actionMsg = getText("news_view_fail");
			return "toBrowseNews";
		}		
	}
	
	/** ����װ���������� */
	public String editNews(){
		if (model.getId()!=null){
			//����ҵ���߼����װ��ָ��������
			News tempNews = service.loadNews(model.getId());
			if (tempNews!=null){
				try {
					//���ٸ���Դ�����е��������Ե�Ŀ�������
					BeanUtils.copyProperties(model, tempNews);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Newscolumns tmpColumn=tempNews.getNewscolumns();
				//ȡ��������Ŀ��ǰֵ
				if (tmpColumn.getNewscolumns()!=null){
					column2 = tmpColumn.getId().toString();
					column1 = tmpColumn.getNewscolumns().getId().toString();
				}else{
					column1 = tmpColumn.getId().toString();
				}
				//����������Ŀ��������
				createDoubleSelect();
				return SUCCESS;
			}else{
				actionMsg = getText("news_view_fail");
				return "toBrowseNews";
			}	
		}else{
			actionMsg = getText("news_view_fail");
			return "toBrowseNews";
		}			
	}
	
	/** ��������������� */
	public String updateNews(){
		News tempNews = service.loadNews(model.getId());//����ҵ���߼����װ��ָ��������
		try {
			//�Բ��ֲ���������֤����Ĭ��ֵ
			if (model.getClicks()==null){
				model.setClicks(0);
			}
			if (model.getPriority()==null){
				model.setPriority(0);
			}
			if (model.getFrom()==null||model.getFrom().trim().length()<1){
				model.setFrom("ԭ��");
			}
			if (model.getAuthor()==null||model.getAuthor().trim().length()<1){
				model.setAuthor("����");
			}
			model.setCdate(new Date());
			
			//�������¼����ֶε�ԭʼֵ
			model.setEditor(tempNews.getEditor());
			model.setHtmlPath(tempNews.getHtmlPath());
			model.setStatus(tempNews.getStatus());
			model.setPicture(tempNews.getPicture());

			//���������ݽ���Escape����
			model.setContent(Tools.escape(model.getContent().trim()));
			//����ͼƬ���ŵ��ϴ��ļ�
			if (model.getIsPicNews().intValue()==1 && getPicFileName()!=null && getPicFileName().trim().length()>0){
				String tempfilename = Tools.getRndFilename()+Tools.getFileExtName(getPicFileName());
				String filename = ServletActionContext.getRequest().getRealPath("/upload").replaceAll("\\\\", "/")+"/"+tempfilename;
				FileOutputStream fos = new FileOutputStream(filename);
				FileInputStream fis = new FileInputStream(getPic());
				byte[] buf = new byte[1024];
				int len = 0;
				while((len=fis.read(buf))>0){
					fos.write(buf,0,len);
				}
				if (fis!=null)fis.close();
				if (fos!=null)fos.close();
				model.setPicture("/upload/"+tempfilename);
			}
			
			//������Ӧ��������Ŀ
			if (column2!=null){
				model.setNewscolumns(columnsService.loadColumns(Integer.valueOf(column2)));
			}else if(column1!=null){
				model.setNewscolumns(columnsService.loadColumns(Integer.valueOf(column1)));
			}
			
			//��������Ϊ"δ����"
			model.setStatus(0);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		if (service.saveOrUpdateNews(model)){//����ҵ���߼��������ָ��������
			addActionMessage(getText("news_edit_succ"));
		}else{
			addActionMessage(getText("news_edit_fail"));
		}
		//����������Ŀ��������
		createDoubleSelect();		
		return INPUT;		
	}
	
	/** ����������Ŀ���������б����� */
	public String preAddNews(){
		//����������Ŀ��������
		createDoubleSelect();
		return SUCCESS;		
	}
	
	/** Ϊָ�����ŵĵ������һ */
	public String incNewsClicks(){
		if (model.getId()!=null){
			News tempNews = service.loadNews(model.getId());//����ҵ���߼����װ��ָ��������
			if (tempNews!=null){
				tempNews.setClicks(tempNews.getClicks().intValue()+1);
				//��һ��ĵ������jsonClicks���Դ��ؿͻ���
				jsonClicks=tempNews.getClicks().toString();	
				service.saveOrUpdateNews(tempNews);//����ҵ���߼��������ָ��������		
			}		
		}
		return SUCCESS;		
	}
	
	/** ����ָ������ */
	public String publisNews(){
		if (model.getId()!=null){
			News tempNews = service.loadNews(model.getId());
			if (tempNews!=null){
				HttpServletRequest request = ServletActionContext.getRequest();
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();				
				String url=basePath+"/admin/viewNews.action?id="+tempNews.getId();
				//������̬ҳ��������ʵ��
				HtmlGenerator hg = new HtmlGenerator(basePath);
				//�����ɾ�̬ҳ��
				if (hg.createHtmlPage(url, request.getRealPath(tempNews.getHtmlPath()))){
					actionMsg = getText("news_publish_succ");
					//�������ű�ǳ�"�ѷ���"
					tempNews.setStatus(1);					
				}else{
					actionMsg = getText("news_publish_fail");
					//�������ű�ǳ�"δ����"
					tempNews.setStatus(0);
				}
				service.saveOrUpdateNews(tempNews);	//����ҵ���߼��������ָ��������					
			}		
		}
		return "toBrowseNews";		
	}	
	
	/** ����������Ŀ�������� */
	private void createDoubleSelect(){
		//����һ�����������б�����ݼ���
		doubleSelectNodes=new ArrayList<DoubleSelectNode>();
		DoubleSelectNode node = null;
		DoubleSelectNode tempnode = null;
		List<DoubleSelectNode> nodes = null;
		//����������Ŀҵ���߼����װ��һ��������Ŀ�б�
		List<Newscolumns> columnsList = columnsService.listColumns();
		List<Newscolumns> childColumnsList = null;
		Newscolumns column = null;
		Newscolumns child_column = null;
		Iterator<Newscolumns> it = columnsList.iterator();
		Iterator<Newscolumns> it1 = null;
		while(it.hasNext()){
			column = it.next();
			node = new DoubleSelectNode();
			node.setName(column.getColumnName().trim());
			node.setValue(column.getId().toString());
			//����������Ŀҵ���߼����װ��ĳһ��������Ŀ������Ŀ�б�
			childColumnsList = columnsService.listChildColumns(column);
			it1 = childColumnsList.iterator();
			nodes = new ArrayList<DoubleSelectNode>();
			while(it1.hasNext()){
				child_column = it1.next();
				tempnode = new DoubleSelectNode();
				tempnode.setName(child_column.getColumnName().trim());
				tempnode.setValue(child_column.getId().toString());
				nodes.add(tempnode);
			}
			node.setSubNodes(nodes);
			doubleSelectNodes.add(node);
		}		
	}
	
	/** �ֶ������������ŵı���֤ */
	public void validateAddNews(){
		//�������
		if (model.getTitle()==null||model.getTitle().trim().length()<1){
			addFieldError("title",getText("news_validation_title"));
		}
		//�ؼ��ʱ���
		if (model.getKeyWords()==null||model.getKeyWords().trim().length()<1){
			addFieldError("keyWords",getText("news_validation_keyWords"));
		}		
		//ժҪ����
		if (model.getAbstract_()==null||model.getAbstract_().trim().length()<1){
			addFieldError("abstract_",getText("news_validation_abstract"));
		}
		//���ݱ���
		if (model.getContent()==null||model.getContent().trim().length()<1){
			addFieldError("content",getText("news_validation_content"));
		}
		//ͼƬ������Ч�Լ��
		if (model.getIsPicNews().intValue()==1){
			if (model.getId()==null){//�������ű���Ҫ����ͼƬ�ļ��ش����
				if (getPicFileName()==null||getPicFileName().trim().length()<1){
					addFieldError("pic",getText("news_validation_pic"));
				}else{
					if (!Tools.isEnableUploadType(1, getPicFileName())){
						String[] args = new String[1];
						args[0] = "����ͼƬ";				
						addFieldError("picture",getText("upload_picture_invalid",args));
					}					
				}			
			}
		}
		//����֤ʧ��
		if (hasFieldErrors()){
			//����������Ŀ��������
			createDoubleSelect();
		}
	}
	
	/** �ֶ������޸����ŵı���֤ */
	public void validateUpdateNews(){
		//�����������ŵĵı���֤��������֤�޸����ŵı�
		validateAddNews();
	}	

	public NewsService getService() {
		return service;
	}

	public void setService(NewsService service) {
		this.service = service;
	}

	public ColumnsService getColumnsService() {
		return columnsService;
	}

	public void setColumnsService(ColumnsService columnsService) {
		this.columnsService = columnsService;
	}

	public String getActionMsg() {
		return actionMsg;
	}

	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	public List<DoubleSelectNode> getDoubleSelectNodes() {
		return doubleSelectNodes;
	}

	public void setDoubleSelectNodes(List<DoubleSelectNode> doubleSelectNodes) {
		this.doubleSelectNodes = doubleSelectNodes;
	}

	public File getPic() {
		return pic;
	}

	public void setPic(File pic) {
		this.pic = pic;
	}

	public String getPicContentType() {
		return picContentType;
	}

	public void setPicContentType(String picContentType) {
		this.picContentType = picContentType;
	}

	public String getPicFileName() {
		return picFileName;
	}

	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getColumn2() {
		return column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	public String getJsonClicks() {
		return jsonClicks;
	}

	public void setJsonClicks(String jsonClicks) {
		this.jsonClicks = jsonClicks;
	}
}