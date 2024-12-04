package entites;

public class Images {
	public String imgLink;
	public String name;

	public Images(String imgLink, String name) {
		super();
		this.imgLink = imgLink;
		this.name = name;
	}
	public String getImgLink() {
		return imgLink;
	}
	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
