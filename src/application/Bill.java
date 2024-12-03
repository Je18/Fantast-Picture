package application;

public class Bill {
	private float bill;
	private int tips;
	private int nbPerson;

	public float getBill() {
		return bill;
	}

	public void setBill(float bill) {
		this.bill = bill;
	}

	public int getTips() {
		return tips;
	}

	public void setTips(int tips) {
		this.tips = tips;
	}

	public int getNbPerson() {
		return nbPerson;
	}

	public void setNbPerson(int nbPerson) {
		this.nbPerson = nbPerson;
	}

	public Bill(float bill, int tips, int nbPerson) {
		this.bill = bill;
		this.tips = tips;
		this.nbPerson = nbPerson;
	}

	public float calculPerPerson() throws IllegalArgumentException {
		if (this.bill <= 0) {
            throw new IllegalArgumentException("La valeur du bill doit être supérieure à 0");
        }
        if (this.tips <= 0) {
            throw new IllegalArgumentException("La valeur du tips doit être supérieure à 0");
        }
        if (this.nbPerson <= 0) {
            throw new IllegalArgumentException("Le nombre de personnes doit être supérieur à 0");
        }
        
        return (this.bill / this.tips / this.nbPerson);
	}

	public float calculTotalPerPerson(float tipPerPerson) throws NumberFormatException {

		return (this.bill / this.nbPerson) + tipPerPerson;

	}

}
