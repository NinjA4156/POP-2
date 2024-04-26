package project;

public class NewAppoint {
    int aid;
    int rid;
    int uid;
    int sid;
    int wid;
    String Date;
    String Time;
    String Service;
    String Status;
	public NewAppoint(int aid, int rid, int uid, int sid, int wid, String date, String time, String service,
			String status) {
		super();
		this.aid = aid;
		this.rid = rid;
		this.uid = uid;
		this.sid = sid;
		this.wid = wid;
		Date = date;
		Time = time;
		Service = service;
		Status = status;
	}
	public NewAppoint(String apDate, String time2, int workerID, String status2) {
		this.Date=apDate;
		this.Time=time2;
		this.wid=workerID;
		this.Status=status2;
	}
	public NewAppoint(int apID, int staffID, int workerID, String date2, String time2, String service2) {
		this.aid=apID;
		this.sid=staffID;
		this.wid=workerID;
		this.Date=date2;
		this.Time=time2;
		this.Service=service2;
	}
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getWid() {
		return wid;
	}
	public void setWid(int wid) {
		this.wid = wid;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getService() {
		return Service;
	}
	public void setService(String service) {
		Service = service;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	@Override
	public String toString() {
		return "NewAppointment [aid=" + aid + ", rid=" + rid + ", uid=" + uid + ", sid=" + sid + ", wid=" + wid
				+ ", Date=" + Date + ", Time=" + Time + ", Service=" + Service + ", Status=" + Status + "]";
	}

    
}
