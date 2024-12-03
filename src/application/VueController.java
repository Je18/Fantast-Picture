package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class VueController {

	@FXML
	private TextField billText;

	@FXML
	private TextField tipsText;

	@FXML
	private TextField nbText;

	@FXML
	private Button btnCalc;

	@FXML
	private Label errorLbl;

	@FXML
	private TextField tipResult;

	@FXML
	private TextField totalResult;

	@FXML
	private TextField dateTxt;

	public void onClickBtn(ActionEvent e) {
		try {
			errorLbl.setText("");
			verificationChamps(billText.getText(), tipsText.getText(), nbText.getText());
			formatDate();
		} catch (NumberFormatException num) {
			errorLbl.setText(num.getMessage());
		} catch (IllegalArgumentException ill) {
			errorLbl.setText(ill.getMessage());
		}
	}

	public void verificationChamps(String bill, String tips, String nbPers) throws NumberFormatException {
		if (bill.equals("")) {
			throw new NumberFormatException("Le champs Bill ne doit pas être vide");
		}
		if (tips.equals("")) {
			throw new NumberFormatException("Le champs Tips ne doit pas être vide");
		}
		if (nbPers.equals("")) {
			throw new NumberFormatException("Le champs Nb de personne ne doit pas être vide");
		}

		Bill calcul = new Bill(Integer.valueOf(bill), Integer.valueOf(tips), Integer.valueOf(nbPers));
		tipResult.setText(String.valueOf(calcul.calculPerPerson()));
		totalResult.setText(String.valueOf(calcul.calculTotalPerPerson(Float.valueOf(tipResult.getText()))));
	}

	public void isNumber(KeyEvent e) throws IllegalArgumentException {
		String regex = "[0-9]";
		errorLbl.setText("");
		try {
			if (!e.getCharacter().matches(regex)) {
				if (billText.getLength() > 0 || tipsText.getLength() > 0 || nbText.getLength() > 0) {
					billText.setText("");
					tipsText.setText("");
					nbText.setText("");
				}
				throw new IllegalArgumentException("Les champs doivent contenir uniquement des chiffres entre 0 et 9");
			}
		} catch (IllegalArgumentException err) {
			errorLbl.setText(err.getMessage());
		}
	}

	public void formatDate() throws IllegalArgumentException {
		String regex = "[0-3][0-9]/[0-1][0-9]/[0-9]{4}";
		errorLbl.setText("");
		try {
			if (!dateTxt.getText().matches(regex)) {
				dateTxt.setText("");
				throw new IllegalArgumentException("La date doit etre sous le format dd/mm/yyyy");
			} else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	            Date formattedDate = dateFormat.parse(dateTxt.getText());
	            
	            Calendar today = Calendar.getInstance();
	            today.set(Calendar.HOUR_OF_DAY, 0);
	            today.set(Calendar.MINUTE, 0);
	            today.set(Calendar.SECOND, 0);
	            today.set(Calendar.MILLISECOND, 0);

	            if (!formattedDate.equals(today.getTime())) {
	            	errorLbl.setText("");
	                throw new IllegalArgumentException("La date saisie doit être identique à celle d'aujourd'hui");
	            }
	            String formattedDateString = dateFormat.format(formattedDate);
	            
				writeInFile(formattedDateString);
			}
		} catch (IllegalArgumentException e) {
			errorLbl.setText(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void writeInFile(String date) {
        File file = new File("C:\\Users\\Eleve\\eclipse-workspace\\Tips\\src\\application\\saveInfo.txt");
        boolean dateExist = false;

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            List<String> lines = new ArrayList<>(Files.readAllLines(file.toPath()));

            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(date.toString())) {
                    lines.set(i, date + " ;" + billText.getText() + " ;" + tipsText.getText() + " ;" + nbText.getText());
                    dateExist = true;
                    break;
                }
            }

            if (!dateExist) {
            	lines.add(date + " ;" + billText.getText() + " ;" + tipsText.getText() + " ;" + nbText.getText());
            }

            Files.write(file.toPath(), lines);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
