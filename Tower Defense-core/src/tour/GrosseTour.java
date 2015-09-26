package tour;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import contenu.Assets;

public class GrosseTour extends Tour{
	
	private String nom;
	private int cout;
	private float rayon;
	private int degats;
	private int vie;
	private float debit;
	private TextureRegion image;
	
	public GrosseTour(int x, int y){
		super("Grosse", 30, (float) 2.5,40,50,(float)0.3,x,y,Assets.Gbas,Assets.Ghaut, Assets.Gbas
				, Assets.Gdroite, Assets.Ggauche, Assets.GhautGauche, Assets.GhautDroite
				, Assets.GbasGauche, Assets.GbasDroite);
		this.nom = "Grosse";
		this.cout = 30;
		this.rayon = (float) 3;
		this.degats = 40;
		this.vie = 50;
		this.debit = (float)0.3;
		this.image=Assets.Gbas;
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
