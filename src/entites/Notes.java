package entites;

public class Notes {
	public Images imgName;
	public Person personLogin;
	public Float notes;
	
	public Notes(Images imgName, Person personLogin, Float notes) {
		super();
		this.imgName = imgName;
		this.personLogin = personLogin;
		this.notes = notes;
	}
	public Images getImgName() {
		return imgName;
	}
	public void setImgName(Images img) {
		this.imgName = img;
	}
	public Person getPersonLogin() {
		return personLogin;
	}
	public void setPersonLogin(Person personLogin) {
		this.personLogin = personLogin;
	}
	public Float getNotes() {
		return notes;
	}
	public void setNotes(Float notes) {
		this.notes = notes;
	}

}
