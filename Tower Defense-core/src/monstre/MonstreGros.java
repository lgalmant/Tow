package monstre;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import contenu.Assets;
import contenu.Terrain;
import entite.Spawn;

public class MonstreGros extends Monstre{
	
	private String nom;
	private int cout;
	private float income;
	private int vie;
	private String etat;
	private int domages;
	private float rayon;
	private float vitesse;
	private Texture image;
	private float xPos;
	private float yPos;
	private Spawn spawn;
	private int xIndex;
	private int yIndex;
	private Terrain terrain;
	private int or;
	
	private Texture imageDos;
	private Texture imageDroite;
	private Texture imageGauche;
	private Texture imageFace;
	
	public MonstreGros(Terrain terrain){
		super(terrain,Assets.grosDos,Assets.grosDroite,Assets.grosGauche,Assets.grosFace);
		this.nom="Gros";
		this.cout=200;
		this.income=5;
		this.vie=500;
		this.etat="";
		this.or=70;
		this.domages=50;
		this.rayon=(float) 0.5;
		this.vitesse=(float) 0.7;
		this.spawn=terrain.spawn;
		this.terrain=terrain;
		this.xIndex=this.spawn.getX();
		this.yIndex=this.spawn.getY();
		this.xPos=this.spawn.getX()*CEP;
		
		this.imageDos=Assets.grosDos;
		this.imageDroite=Assets.grosDroite;
		this.imageGauche=Assets.grosGauche;
		this.imageFace=Assets.grosFace;
		this.yPos=this.spawn.getY()*CEP;
		this.image=Assets.grosFace;
		
		
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

	public float getIncome() {
		return income;
	}
	
	

	public int getOr() {
		return or;
	}

	public void setOr(int or) {
		this.or = or;
	}

	public void setIncome(float income) {
		this.income = income;
	}

	public int getVie() {
		return vie;
	}

	public void setVie(int vie) {
		this.vie = vie;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public int getDomages() {
		return domages;
	}

	public void setDomages(int domages) {
		this.domages = domages;
	}

	public float getRayon() {
		return rayon;
	}

	public void setRayon(float rayon) {
		this.rayon = rayon;
	}

	public float getVitesse() {
		return vitesse;
	}

	public void setVitesse(float vitesse) {
		this.vitesse = vitesse;
	}

	public Texture getTexture2() {
		return image;
	}

	public void setTexture2(Texture image) {
		this.image = image;
	}

	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

	public Spawn getSpawn() {
		return spawn;
	}

	public void setSpawn(Spawn spawn) {
		this.spawn = spawn;
	}

	public int getxIndex() {
		return xIndex;
	}

	public void setxIndex(int xIndex) {
		this.xIndex = xIndex;
	}

	public int getyIndex() {
		return yIndex;
	}

	public void setyIndex(int yIndex) {
		this.yIndex = yIndex;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}

	@Override
	public TextureRegion getTexture() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
