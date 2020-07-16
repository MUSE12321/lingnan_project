package com.lingnan.summer.domain;

import java.util.ArrayList;
import java.util.List;

import com.lingnan.summer.util.StringUtil;


public class Device {
	
	private int did ;
	private String dname;
	private String tid; 
	private String location;  
	private String param;  
	private String status; 
	private int temp;
	
	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		this.temp = temp;
	}
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	private StringBuilder columnPlaceholder = new StringBuilder();//��ӱ������
	private StringBuilder paramPlaceholder = new StringBuilder();//��Ӧ���ٸ��ģ�
	
	public StringBuilder getColumnPlaceholder() {
		return columnPlaceholder;
	}
	public StringBuilder getParamPlaceholder() {
		return paramPlaceholder;
	}
	
	

	@Override
	public String toString() {
		return "device [dname=" + dname + ", tid=" + tid + ", location=" + location
				+ ", param=" + param + ", status=" + status + "]";
	}
	
	public void dualDynamicSQL() {
		// �Զ����	
		if(!StringUtil.isEmpty(dname)) {
			columnPlaceholder.append("dname").append(",");//һ�����Զ�Ӧһ�������м���,����
			paramPlaceholder.append("?,");
			params.add(dname);
		}
		
	
		if(!StringUtil.isEmpty(tid)) {
			columnPlaceholder.append("tid").append(",");
			paramPlaceholder.append("?,");
			params.add(tid);
		}
		
		if(location!=null) {
			columnPlaceholder.append("location").append(",");
			paramPlaceholder.append("?,");
			params.add(location);
		}
		
		if(!StringUtil.isEmpty(param)) {
			columnPlaceholder.append("param").append(",");
			paramPlaceholder.append("?,");
			params.add(param);
		}
		
		if(!StringUtil.isEmpty(status)) {
			columnPlaceholder.append("status").append(",");
			paramPlaceholder.append("?,");
			params.add(status);
		}
		 		
		//�����һ��,ȥ��
				int columnindex = columnPlaceholder.lastIndexOf(","); 
				//��ȡ���һ�����ţ����û�н����򷵻�-1����������-1ʱ��˵���Ѿ������ˡ�
				int paramindex = paramPlaceholder.lastIndexOf(",");
				if(columnindex != -1) {
					columnPlaceholder.deleteCharAt(columnindex);
				}
				if(paramindex != -1) {
					paramPlaceholder.deleteCharAt(paramindex);
				}

			}
	
	private List<Object> params= new ArrayList<Object>();
	public List<Object> getParams() {
		return params;
	}

	
	

}
