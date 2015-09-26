package tour;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import contenu.Assets;

public class TourCommune extends Tour{

	private String nom;
	private int cout;
	private float rayon;
	private int degats;
	private int vie;
	private float debit;
	private TextureRegion image;
	
	public TourCommune(int x, int y){
		super("Commune", 10, (float) 3.5,15,20,(float)0.5,x,y,Assets.Cbas,Assets.Chaut, Assets.Cbas
				, Assets.Cdroite, Assets.Cgauche, Assets.ChautGauche, Assets.ChautDroite
				, Assets.CbasGauche, Assets.CbasDroite);
		this.nom = "Commune";
		this.cout = 10;
		this.rayon = (float) 5;
		this.degats = 10;
		this.vie = 20;
		this.debit = (float)0.5;
		this.image=Assets.Cbas;
	}
	
	public TextureRegion getTexture() {
		return this.image;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getCout() {
		return cout;
	}

	public void setCout(int cout) {
		this.cout = cout;
	}

	public float getRayon() {
		return rayon;
	}

	public void setRayon(float rayon) {
		this.rayon = rayon;
	}

	public int getDegats() {
		return degats;
	}

	public void setDegats(int degats) {
		this.degats = degats;
	}

	public int getVie() {
		return vie;
	}

	public void setVie(int vie) {
		this.vie = vie;
	}

	public float getDebit() {
		return debit;
	}

	public void setDebit(float debit) {
		this.debit = debit;
	}

	public TextureRegion getImage() {
		return image;
	}

	public void setImage(TextureRegion image) {
		this.image = image;
	}

	@Override
	public float getIncome() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Texture getTexture2() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
