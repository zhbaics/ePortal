package com.eportal.struts.action;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;
import com.eportal.DAO.BaseDAO;
import com.eportal.ORM.*;
import com.eportal.util.Tools;

/** ���Ųɼ��߳� */
public class CrawlNewsThread implements Runnable {

	private Newsrule rule;//���Ųɼ�����
	private BaseDAO dao;//���ݿ�������
	private Admin admin;//ϵͳ����Ա
	private StringBuffer statusMessage=new StringBuffer();//������Ϣ
	private int suc=0,fail=0;//�ɼ��ɹ���ʧ�ܵ���������
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

	/** ���췽�� */
	public CrawlNewsThread(Newsrule rule,BaseDAO dao,Admin admin){
		this.rule =  rule;
		this.dao = dao;
		this.admin = admin;		
	}

	public void run() {
		//��ʽ���ɼ�����ĸ�������
		rule.setListBegin(formatHtml(rule.getListBegin()));
		rule.setListEnd(formatHtml(rule.getListEnd()));
		rule.setTitleBegin(formatHtml(rule.getTitleBegin()));
		rule.setTitleEnd(formatHtml(rule.getTitleEnd()));
		rule.setAuthorBegin(formatHtml(rule.getAuthorBegin()));
		rule.setAuthorEnd(formatHtml(rule.getAuthorEnd()));
		rule.setContentBegin(formatHtml(rule.getContentBegin()));
		rule.setContentEnd(formatHtml(rule.getContentEnd()));
		rule.setFromBegin(formatHtml(rule.getFromBegin()));
		rule.setFromEnd(formatHtml(rule.getFromEnd()));
		rule.setMidBegin(formatHtml(rule.getMidBegin()));
		rule.setMidEnd(formatHtml(rule.getMidEnd()));
		
		statusMessage.append("��ʼ��"+rule.getRuleName()+"�������Ųɼ�!..................."+df.format(new Date())+"\n");
		String body = formatHtml(getBody(formatBodyTag(getPage(rule.getUrl(),rule.getEncode()))));
		if (body!=null && body.length()>0){
			statusMessage.append("1.ץȡ�����б�ҳ("+rule.getUrl()+")�ɹ�!\n");
			String newslistcode = getNewsListCode(body,rule.getListBegin(),rule.getListEnd());
			if (newslistcode!=null && newslistcode.length()>0){
				statusMessage.append("2.��ȡ�����б�HTMLԴ��ɹ�!\n");
				List newslist = getNewsList(newslistcode);
				if (newslist!=null){
					statusMessage.append("3.�ɹ���ȡ�����б���"+newslist.size()+"��!\n");
					newslist = getAbsUrl(newslist,rule.getUrl());
					statusMessage.append("4.��ʼ�ɼ��������б���ľ�������...................\n");
					getAllNews(newslist,rule);
					statusMessage.append("5.���ɼ�"+(suc+fail)+"������,�ɹ�"+suc+"��,ʧ��"+fail+"��!\n");
				}else{
					statusMessage.append("3.��ȡ�����б���ʧ��!\n");
				}
			}else{
				statusMessage.append("2.��ȡ�����б�HTMLԴ��ʧ��!\n");
			}
		}else{
			statusMessage.append("2.ץȡ�����б�ҳ("+rule.getUrl()+")ʧ��!\n");
		}		
		statusMessage.append("��ɡ�"+rule.getRuleName()+"�������Ųɼ�!..................."+df.format(new Date())+"\n");
	}
	
	/** �ɼ�ָ���б��е������������� */
	private void getAllNews(List newslist,Newsrule rule){
		String url =null;
		String title = null;
		int i=0;
		if(newslist==null)return;
		List result = new ArrayList();
		News news = null;
		Map newsitem = null;
		Iterator it = newslist.iterator();
		while(it.hasNext()){
			newsitem = (Map)it.next();
			url = newsitem.get("href").toString();
			title = newsitem.get("title").toString();
			try {
				news = getNews(url,rule);
				if (news==null){
					statusMessage.append("\t�ɼ���"+(i+1)+"�����š�"+title+"��ʧ��!\n");
				}else{
					statusMessage.append("\t�ɼ���"+(i+1)+"�����š�"+title+"���ɹ�!\n");
				}
				//����һ������Ϣһ��
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
	}
	
	/** �ɼ�ָ��URL���������� */
	private News getNews(String url,Newsrule rule){
		News news = new News();		
		String body = formatHtml(getBody(formatBodyTag(getPage(url,rule.getEncode()))));
		int p1,p2;
		if (body==null)return null;
		//��ȡ���ű���
		p1 = body.indexOf(rule.getTitleBegin());
		if(p1!=-1){
			String title = null;
			p1=p1+rule.getTitleBegin().length();
			p2 = body.indexOf(rule.getTitleEnd(),p1);
			if (p2!=-1){
				title = getText(body.substring(p1, p2));
				//ȥ�������е���������
				p1 = title.indexOf(">");
				if (p1!=-1){
					p2 = title.indexOf("<");
					if (p2!=-1){
						title = title.substring(p1+1, p2);
					}else{
						title = title.substring(p1+1);
					}
				}
				news.setTitle(title.trim());
				//����ժҪ��ؼ���ֱ��ȡ���������
				news.setAbstract_(title.trim());
				news.setKeyWords(title.trim());
			}
		}
		//��ȡ��������
		if (rule.getAuthorBegin()!=null && rule.getAuthorBegin().trim().length()>0 && rule.getAuthorEnd()!=null && rule.getAuthorEnd().trim().length()>0){
			p1 = body.indexOf(rule.getAuthorBegin());
			if(p1!=-1){
				String author = null;
				p1=p1+rule.getAuthorBegin().length();
				p2 = body.indexOf(rule.getAuthorEnd(),p1);
				if (p2!=-1){
					author = getText(body.substring(p1, p2));
					//ȥ�������е���������
					p1 = author.indexOf(">");
					if (p1!=-1){
						p2 = author.indexOf("<");
						if (p2!=-1){
							author = author.substring(p1+1, p2);
						}else{
							author = author.substring(p1+1);
						}
					}
					news.setAuthor(author.trim());				
				}
			}			
		}
		//��ȡ������Դ
		if (rule.getFromBegin()!=null && rule.getFromBegin().trim().length()>0 && rule.getFromEnd()!=null && rule.getFromEnd().trim().length()>0){
			p1 = body.indexOf(rule.getFromBegin());
			if(p1!=-1){
				String from = null;
				p1=p1+rule.getFromBegin().length();
				p2 = body.indexOf(rule.getFromEnd(),p1);
				if (p2!=-1){
					from = getText(body.substring(p1, p2));
					//ȥ����Դ�е���������
					p1 = from.indexOf(">");
					if (p1!=-1){
						p2 = from.indexOf("<");
						if (p2!=-1){
							from = from.substring(p1+1, p2);
						}else{
							from = from.substring(p1+1);
						}
					}
					news.setFrom(from.trim());				
				}
			}			
		}
		//��ȡ��������
		String tempcontent=null;
		if (rule.getMidBegin()!=null && rule.getMidBegin().trim().length()>0 && rule.getMidEnd()!=null && rule.getMidEnd().trim().length()>0){//�л��л�����,��Ҫȥ��	
			//��ȡ��һ����������
			p1 = body.indexOf(rule.getContentBegin());
			if(p1!=-1){
				p1=p1+rule.getContentBegin().length();
				p2 = body.indexOf(rule.getMidBegin(),p1);
				if (p2!=-1){
					tempcontent = body.substring(p1, p2);					
				}
			}
			//��ȡ�ڶ�����������
			p1 = body.indexOf(rule.getMidEnd());
			if(p1!=-1){
				p1=p1+rule.getMidEnd().length();
				p2 = body.indexOf(rule.getContentEnd(),p1);
				if (p2!=-1){
					tempcontent = tempcontent+body.substring(p1, p2);					
				}
			}			
		}else{//�޻��л�����
			p1 = body.indexOf(rule.getContentBegin());
			if(p1!=-1){
				p1=p1+rule.getContentBegin().length();
				p2 = body.indexOf(rule.getContentEnd(),p1);
				if (p2!=-1){
					tempcontent = body.substring(p1, p2);
				}
			}			
		}
		if (tempcontent!=null){
			//����ԭʼͼƬ����
			tempcontent = tempcontent.replaceAll("<img", "iiiiiiiiii");
			tempcontent = tempcontent.replaceAll("<IMG", "iiiiiiiiii");
			//����ԭʼ���и�ʽ
			tempcontent = tempcontent.replaceAll("<br/>", "############");
			tempcontent = tempcontent.replaceAll("<br />", "############");
			tempcontent = tempcontent.replaceAll("<br>", "############");
			tempcontent = tempcontent.replaceAll("</p>", "############");
			tempcontent = tempcontent.replaceAll("</P>", "############");
			//ȥ�����е�HTML��ǩ,�������ı�����
			tempcontent = getText(tempcontent);
			//���������·�װ�ɶ���
			tempcontent = tempcontent.replaceAll("iiiiiiiiii", "<img");
			tempcontent = tempcontent.replaceAll("############", "<br/>");			
			while (tempcontent.indexOf("<br/><br/>")!=-1){
				tempcontent = tempcontent.replaceAll("<br/><br/>", "<br/>");
			}
			tempcontent = tempcontent.replaceAll("<br/>", "</p><p>");
			tempcontent = "<p>"+tempcontent+"</p>";
			tempcontent = tempcontent.replaceAll("<p><p>", "<p>");
			tempcontent = tempcontent.replaceAll("</p></p>", "</p>");
			news.setContent(Tools.escape(tempcontent));
		}
		//�жϲɼ������Ƿ�����
		if (news.getTitle()==null || news.getTitle().trim().length()<1 || news.getContent()==null || news.getContent().trim().length()<1){
			fail++;
			return null;			
		}else{
			//��ʼ��newsʵ������������
			news.setClicks(0);
			news.setPriority(0);
			news.setCdate(new Date());
			news.setHtmlPath("/html/news/"+Tools.getRndFilename()+".html");
			news.setStatus(0);
			news.setRed(0);
			news.setNewsType(0);
			if (news.getAuthor()==null||news.getAuthor().trim().length()<1){
				news.setAuthor("����");
			}
			if (news.getFrom()==null||news.getFrom().trim().length()<1){
				news.setFrom("ԭ��");
			}
			news.setEditor(admin.getLoginName());
			news.setIsPicNews(0);			
			//��������Ŀ���й���
			news.setNewscolumns(rule.getNewscolumns());
			//��ѯ��ǰ�����Ƿ��Ѿ�����
			List temp = dao.query("from News as a where a.newscolumns.id="+rule.getNewscolumns().getId()+" and a.title='"+news.getTitle()+"'");
			if (temp==null||temp.size()<1){
				//���浱ǰ�ɼ�������
				dao.saveOrUpdate(news);
				suc++;
				return news;
			}else{
				fail++;
				return null;
			}
		}		
	}
	
	/** ʹ��HtmlParser���ȥ�������е�HTML��ǩ,�õ����ı����� */
	private String getText(String content){
		String result = content;
		try{
			Parser parser = Parser.createParser(content, "GBK");
			TextExtractingVisitor visitor = new TextExtractingVisitor();
            parser.visitAllNodesWith(visitor);
            result = visitor.getExtractedText();
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return result;
	}
	
	/** �õ������б���ľ���URL��ַ */
	private List getAbsUrl(List newslist,String baseurl){
		if(newslist==null||baseurl==null)return null;
		List result = newslist;
		String temurl = null;
		for (int i=0;i<result.size();i++){
			temurl = ((Map)result.get(i)).get("href").toString();
			if (temurl.indexOf("http://")==-1 && temurl.indexOf("https://")==-1){
				temurl = baseurl+"/"+temurl;
				while(temurl.indexOf("//")!=-1){
					temurl = temurl.replaceAll("//", "/");
				}
				temurl = temurl.replaceAll("http:/", "http://");
				temurl = temurl.replaceAll("https:/", "https://");
				((Map)result.get(i)).put("href", temurl);
			}
		}
		return result;
	}
	
	/** ʹ��HtmlParser�����ȡ�����б��� */
	private List getNewsList(String newslistcode){
		List list = null;
		//����HtmlParserʵ�����ڽ���ָ��html����
		Parser parser = Parser.createParser(newslistcode, "GBK");
		//����TagNameFilterʵ��������ȡa��ǩ
        NodeFilter filter = new TagNameFilter ("A");
        try {
            NodeIterator it = parser.extractAllNodesThatMatch(filter).elements();
            TagNode node = null;
            Map map = null;
            while(it.hasMoreNodes()){
            	if (list==null)list = new ArrayList();
            	node = (TagNode)it.nextNode();
            	map = new HashMap();
            	map.put("href", node.getAttribute("href"));
            	map.put("title", node.toPlainTextString());
            	list.add(map);
            }
		} catch (ParserException e) {
			e.printStackTrace();
		}          
		return list;
	}
	
	/** ��ȡ�����б����� */
	private String getNewsListCode(String body,String listBegin,String listEng){
		String listcode = null;
		int p1,p2;
		if (body!=null && listBegin!=null && listEng!=null){
			p1 = body.indexOf(listBegin);
			if (p1!=-1){
				p1 = p1 + listBegin.length();
				p2 = body.indexOf(listEng,p1);
				if (p2!=-1){
					listcode = body.substring(p1, p2);
				}
			}
		}
		return listcode;
	}	
	
	/** ʹ��HttpClient�����ȡָ��URL��ҳ��HTMLԴ�� */
	private String getPage(String url,String encode){
		//����HttpClientʵ��
		HttpClient httpClient=new HttpClient();
		//���ñ������
		if (encode!=null){
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,encode);
		}else{
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gbk");
		}		
		//����Cookies
		httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);

		//����GetMethodʵ������ָ��URL
		GetMethod getMethod = new GetMethod(url);
		try{
			//����ָ��URL��ȡ�÷���״̬��
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode==200){//���سɹ�״̬��200
				//��ȡҳ��HTMLԴ��
				StringBuffer sb = new StringBuffer();
				InputStream in = getMethod.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String line;
				while((line=br.readLine())!=null){
					sb.append(line);
				}
				if(br!=null)br.close();
				return sb.toString();
			}else{
				return null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	/** ��ʽ��Body��ǩ */
	private String formatBodyTag(String htmlcode){
		String result = htmlcode;
		if (htmlcode!=null){
			//ͳһ��body��ǩת����Сд,�˴�ֻ���Ǽ���ϰ�ߵ�д��
			while (result.indexOf("<BODY")!=-1){
				result = result.replaceAll("<BODY", "<body");
			}
			while (result.indexOf("<Body")!=-1){
				result = result.replaceAll("<Body", "<body");
			}
			while (result.indexOf("</BODY")!=-1){
				result = result.replaceAll("</BODY", "</body");
			}
			while (result.indexOf("</Body")!=-1){
				result = result.replaceAll("</Body", "</body");
			}			
		}
		return result;
	}
	
	/** ʹ��������ʽ��ȡbody������ */
	private String getBody(String htmlcode){
		if(htmlcode==null)return null;
		Pattern p = Pattern.compile("<body(.*)>(.*)</body>",Pattern.MULTILINE|Pattern.DOTALL);
		Matcher m = p.matcher(htmlcode);
		if (m.find()){
			return m.group();
		}else{
			return null;
		}
	}	
	
	/** ��ʽ��ָ����HTMLԴ�� */
	private String formatHtml(String htmlcode){
		String result = htmlcode;
		if (htmlcode!=null && htmlcode.trim().length()>0){
			//ȥ���س���
			while (result.indexOf("\r")!=-1){
				result = result.replaceAll("\r", "");
			}
			//ȥ�����з�
			while (result.indexOf("\n")!=-1){
				result = result.replaceAll("\n", "");
			}			
			//ȥ���Ʊ��
			while (result.indexOf("\t")!=-1){
				result = result.replaceAll("\t", "");
			}			
			//ȥ������ո�
			while (result.indexOf("  ")!=-1){
				result = result.replaceAll("  ", " ");
			}
			//ȥ��ȫ�ǿո�
			while (result.indexOf("��")!=-1){
				result = result.replaceAll("��", "");
			}
			return result;
		}else{
			return null;
		}		
	}

	/** ��ȡ������Ϣ */
	public StringBuffer getStatusMessage() {
		return statusMessage;
	}	

}
