package project;

public class NewRequest {
	int reqid;
	int uid;
	String location;
	String service;
	String description;
	String status;
	public NewRequest(int uid, String location, String service, String description, String status) {
		super();
		this.reqid = 0;
		this.uid = uid;
		this.location = location;
		this.service = service;
		this.description = description;
		this.status = status;
	}
	public NewRequest(int rid, int uid, String location, String service, String description, String status) {
		this.reqid=rid;
		this.uid=uid;
		this.location=location;
		this.service=service;
		this.description=description;
		this.status=status;
	}
	public NewRequest(int reqid2, int userid, String service2) {
		this.reqid=reqid2;
		this.uid=userid;
		this.service=service2;
	}
	public int getReqid() {
		return reqid;
	}
	public void setReqid(int reqid) {
		this.reqid = reqid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "NewRequest [reqid=" + reqid + ", uid=" + uid + ", location=" + location + ", service=" + service
				+ ", description=" + description + ", status=" + status + "]";
	}

		
	


	
	
	
}
