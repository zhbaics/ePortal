package com.eportal.util;

import java.util.List;

/** ��s:doubleselect��ǩʹ�õļ����ڵ� */
public class DoubleSelectNode {
	String name;//����ʾ������
	String value;//�ڵ��ֵ
	List<DoubleSelectNode> subNodes;//�����ӽڵ㼯
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<DoubleSelectNode> getSubNodes() {
		return subNodes;
	}
	public void setSubNodes(List<DoubleSelectNode> subNodes) {
		this.subNodes = subNodes;
	}
}
