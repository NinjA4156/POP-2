package project;

public class NewService {
	int serviceid;
	int uid;
	String Name;
	String Description;
	String Duration;
	int Cost;
	public NewService(int serviceid, int uid, String name, String description, String duration, int cost) {
		super();
		this.serviceid = serviceid;
		this.uid=uid;
		Name = name;
		Description = description;
		Duration = duration;
		Cost = cost;
	}
	 
//	public NewService(int uid2, String name2, String description2, String duration2, int cost2) {
//		this.serviceid=0;
//		this.uid=uid2;
//		this.Name=name2;
//		this.Description=description2;
//		this.Duration=duration2;
//		this.Cost=cost2;
//	}

	public NewService(int serviceid, String name2, String description2, String duration2, int cost2) {
		this.serviceid=serviceid;
		this.Name=name2;
		this.Description=description2;
		this.Duration=description2;
		this.Cost=cost2;
	}

	public NewService(String name2, String description2, String duration2, int cost2) {
		this.Name=name2;
		this.Description=description2;
		this.Duration=duration2;
		this.Cost=cost2;
	}

	public int getServiceid() {
		return serviceid;
	}

	public void setServiceid(int serviceid) {
		this.serviceid = serviceid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getDuration() {
		return Duration;
	}

	public void setDuration(String duration) {
		Duration = duration;
	}

	public int getCost() {
		return Cost;
	}

	public void setCost(int cost) {
		Cost = cost;
	}

	@Override
	public String toString() {
		return "NewService [serviceid=" + serviceid + ", uid=" + uid + ", Name=" + Name + ", Description=" + Description
				+ ", Duration=" + Duration + ", Cost=" + Cost + "]";
	}
	
	
}
