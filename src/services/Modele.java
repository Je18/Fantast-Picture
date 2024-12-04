package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import entites.Images;
import entites.Notes;
import entites.Person;

public class Modele {

	Images imgParis = new Images("paris.jpg", "Paris");
	Images imgLosAngeles = new Images("los_angeles.jpg", "Los Angeles");
	Images imgMontevideo = new Images("ville_montevideo.jpg", "Montevideo");

	Person pers1 = new Person("Jean", "jean");
	Person pers2 = new Person("Michel", "mich");

	List<Notes> notes = new ArrayList<>();
	List<Notes> getNotes = new ArrayList<>();

	public List<Images> getListImage() {
		List<Images> imgList = new ArrayList<>();
		imgList.add(imgParis);
		imgList.add(imgLosAngeles);
		imgList.add(imgMontevideo);
		return imgList;
	}

	public List<String> getImageNames() {
		List<String> names = new ArrayList<>();
		for (Images img : getListImage()) {
			names.add(img.getName());
		}
		return names;
	}

	public List<String> getImageLink() {
		List<String> link = new ArrayList<>();
		for (Images img : getListImage()) {
			link.add(img.getImgLink());
		}
		return link;
	}

	public List<Person> getListPerson() {
		List<Person> personList = new ArrayList<>();
		personList.add(pers1);
		personList.add(pers2);
		return personList;
	}

	public List<String> getPersonName() {
		List<String> names = new ArrayList<>();
		for (Person person : getListPerson()) {
			names.add(person.getName());
		}
		return names;
	}

	public List<String> getPersonLogin() {
		List<String> logins = new ArrayList<>();
		for (Person person : getListPerson()) {
			logins.add(person.getLogin());
		}
		return logins;
	}

	public List<Notes> createNote(Images imgName, Person personName, float note) {
		if (!notes.contains(imgName.getName()) && !notes.contains(personName.getName())) {
			notes.add(new Notes(imgName, personName, note));
			writeInFile(imgName, personName, note);
		}
		return notes;
	}

	public void writeInFile(Images imgName, Person personName, float note) {
		File file = new File("C:\\Users\\Eleve\\eclipse-workspace\\Fantast’Picture\\src\\services\\notes.txt");
		boolean isExist = false;

		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			List<String> lines = new ArrayList<>(Files.readAllLines(file.toPath()));
			for (int i = 0; i < lines.size(); i++) {
				if (lines.get(i).contains(imgName.getName()) && lines.get(i).contains(personName.getName())) {
					lines.set(i, imgName.getName() + " ;" + personName.getName() + " ;" + note);
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				lines.add(imgName.getName() + " ;" + personName.getName() + " ;" + note);
			}

			Files.write(file.toPath(), lines);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Notes> readFromFile() {
	    List<Notes> notesList = new ArrayList<>();
	    File file = new File("C:\\Users\\Eleve\\eclipse-workspace\\Fantast’Picture\\src\\services\\notes.txt");

	    try {
	        if (file.exists()) {
	            List<String> lines = Files.readAllLines(file.toPath());
	            for (String line : lines) {
	                String[] data = line.split(" ;");
	                if (data.length == 3) {
	                    String imgName = data[0].trim();
	                    String personName = data[1].trim();
	                    float note = Float.parseFloat(data[2].trim());
	                    
	                    Images image = null;
	                    Person person = null;
	                    for (Images img : getListImage()) {
	                        if (img.getName().equals(imgName)) {
	                            image = img;
	                            break;
	                        }
	                    }
	                    for (Person p : getListPerson()) {
	                        if (p.getName().equals(personName)) {
	                            person = p;
	                            break;
	                        }
	                    }

	                    if (image != null && person != null) {
	                        notesList.add(new Notes(image, person, note));
	                    }
	                }
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return notesList;
	}
	
	public Images getImageByName(String name) {
	    for (Images img : getListImage()) {
	        if (img.getName().equals(name)) {
	            return img;
	        }
	    }
	    return null;
	}

	public Person getPersonByName(String name) {
	    for (Person person : getListPerson()) {
	        if (person.getName().equals(name)) {
	            return person;
	        }
	    }
	    return null;
	}

	public Person getIdentity(String name, String password) {
	    for (Person person : getListPerson()) {
	        if (person.getName().equals(name) && person.getLogin().equals(password)) {
	            return person;
	        }
	    }
	    return null;
	}

	public Notes getNoteForImgPerson(Images image, Person person) {
	    for (Notes note : readFromFile()) {
	        if (note.getImgName().equals(image) && note.getPersonLogin().equals(person)) {
	            return note;
	        }
	    }
	    return null;
	}


}