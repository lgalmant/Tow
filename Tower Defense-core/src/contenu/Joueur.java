package contenu;

public class Joueur {
	
	private String nom;
	private float income;
	private int or;
	private int vie;
	
	public float getIncome() {
		return income;
	}
	public void setIncome(float income) {
		this.income = income;
	}
	public int getOr() {
		return or;
	}
	public void setOr(int or) {
		this.or = or;
	}
	public Joueur(String nom, float income, int or, int vie) {
		this.nom = nom;
		this.income = income;
		this.or = or;
		this.vie=vie;
	}
	public int getVie() {
		return vie;
	}
	public void setVie(int vie) {
		this.vie = vie;
	}
	private boolean perdu=false;
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public boolean isPerdu() {
		return perdu;
	}
	public void setPerdu(boolean perdu) {
		this.perdu = perdu;
	}
	
	

}
