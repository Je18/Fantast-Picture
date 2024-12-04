package application;

import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.Modele;
import entites.Images;
import entites.Notes;
import entites.Person;

public class Controller {

    @FXML
    private ListView<String> listView;

    @FXML
    private ImageView imgViewer;

    @FXML
    private PasswordField pwdInput;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField noteInput;

    @FXML
    private Label errorLbl;

    @FXML
    private Button noteBtn;
    
    @FXML
    private Button loginBtn;

    private Modele model;

    private String imgSelected = "";

    private void init() {
        this.model = new Modele();
    }

    @FXML
    void initialize() {
        init();
        verifyIdentity();
    }

    private void isChange() {
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            imgSelected = newValue;
            updateImage();
            updateNote();
        });
    }

    private void updateImage() {
        int indexSeleted = listView.getSelectionModel().getSelectedIndex();
        if (indexSeleted >= 0 && indexSeleted < model.getImageLink().size()) {
            getImage(model.getImageLink().get(indexSeleted));
        }
    }

    private void updateNote() {
        try {
            Images selectedImage = model.getImageName(imgSelected);
            Person selectedPerson = model.getPersonName(nameInput.getText().trim());

            if (selectedImage != null && selectedPerson != null) {
                Notes note = model.getNoteForImgPerson(selectedImage, selectedPerson);
                if (note != null) {
                    noteInput.clear();
                    noteInput.setText(Float.toString(note.getNotes()));
                } else {
                    noteInput.clear();
                }
            } else {
                noteInput.clear();
            }
        } catch (Exception e) {
            errorLbl.setText("Impossible de charger la note.");
            e.printStackTrace();
        }
    }

    public void verifyIdentity() {
        try {
            Person authenPerson = model.getIdentity(nameInput.getText(), pwdInput.getText());
            if (authenPerson != null) {
                errorLbl.setText("Bienvenue " + authenPerson.getName() + " !");
                noteBtn.setDisable(false);
                noteInput.setDisable(false);

                if (listView.getItems().isEmpty()) {
                    listView.getItems().addAll(model.getImageNames());
                    if (!model.getImageLink().isEmpty()) {
                        getImage(model.getImageLink().get(0));
                    }
                }
                isChange();
            } else {
            	resetBtnInput("Vous devez �tre connect� pour noter.");
            }
        } catch (NullPointerException e) {
        	resetBtnInput("Donn�es utilisateur manquantes.");
            e.printStackTrace();
        }
    }

    private void resetBtnInput(String errorMessage) {
        listView.getItems().clear();
        imgViewer.setImage(null);
        noteBtn.setDisable(true);
        noteInput.setDisable(true);
        errorLbl.setText(errorMessage);
    }

    public void getImage(String link) {
        InputStream inputS = getClass().getResourceAsStream(link);
        if (inputS != null) {
            Image img = new Image(inputS);
            imgViewer.setImage(img);
        }
    }

    public void addNotes() {
        if (!noteInput.getText().isEmpty() && !imgSelected.isEmpty()) {
            try {
                float noteValue = Float.parseFloat(noteInput.getText());
                if (noteValue < 0 || noteValue > 20) {
                    errorLbl.setText("La note doit �tre entre 0 et 20");
                    return;
                }

                Images image = model.getImageName(imgSelected);
                Person person = model.getPersonName(nameInput.getText());

                if (image != null && person != null) {
                    model.createNote(image, person, noteValue);
                    errorLbl.setText("Note ajout�e pour " + imgSelected);
                } else {
                    errorLbl.setText("Ajout de la note impossible");
                }
            } catch (NumberFormatException e) {
                errorLbl.setText("La note doit etre entre 0 et 20");
            }
        } else {
            errorLbl.setText("Une image doit �tre s�lectionn�e");
        }
    }
}
