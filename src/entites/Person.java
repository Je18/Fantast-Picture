package entites;

public class Person {
	public String name;
	public String login;
	
	public Person(String name, String login) {
		super();
		this.name = name;
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
