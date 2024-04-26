package project;

public class NewWorker {

	int Worker_id;
	String Fullname;
	String address;
	String contact;
	String Expertise;
	
	
	
	public NewWorker(int worker_id, String fullname, String address, String contact, String expertise) {
		super();
		
		this.Worker_id = worker_id;
		this.Fullname = fullname;
		this.address = address;
		this.contact = contact;
		this.Expertise = expertise;
	}
	public NewWorker(String fullname2, String address2, String contact2, String expertise2) {
		
		this.Worker_id=0;
		this.Fullname=address2;
		this.address=address2;
		this.contact=contact2;
		this.Expertise=expertise2;
	}
	public int getWorker_id() {
		return Worker_id;
	}
	public void setWorker_id(int worker_id) {
		Worker_id = worker_id;
	}
	public String getFullname() {
		return Fullname;
	}
	public void setFullname(String fullname) {
		Fullname = fullname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getExpertise() {
		return Expertise;
	}
	public void setExpertise(String expertise) {
		Expertise = expertise;
	}
	@Override
	public String toString() {
		return "NewWorker [Worker_id=" + Worker_id + ", Fullname=" + Fullname + ", address=" + address + ", contact="
				+ contact + ", Expertise=" + Expertise + "]";
	}
	
	
	
	
	
	
}
