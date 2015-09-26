package monstre;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import contenu.Assets;
import contenu.Constantes;
import contenu.Terrain;
import entite.Spawn;

public class MonstreCommun extends Monstre implements Constantes{
	
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
	
	public MonstreCommun(Terrain terrain){
		super(terrain, Assets.communDos,Assets.communDroite,Assets.communGauche,Assets.communFace);
		this.spawn=terrain.spawn;
		
		
		this.nom="Commun";
		this.cout=100;
		this.or=50;
		this.income=3;
		this.vie=50;
		this.etat="";
		this.domages=10;
		this.rayon=1;
		this.vitesse=2;
		
		this.imageDos=Assets.communDos;
		this.imageDroite=Assets.communDroite;
		this.imageGauche=Assets.communGauche;
		this.imageFace=Assets.communFace;
		
		this.terrain=terrain;
		this.xIndex=this.spawn.getX();
		this.yIndex=this.spawn.getY();
		this.xPos=this.spawn.getX()*CEP;
		this.yPos=this.spawn.getY()*CEP;
		this.image=Assets.communFace;
		
		
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	

	public int getOr() {
		return or;
	}

	public void setOr(int or) {
		this.or = or;
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
		return this.image;
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

	public Texture getTexture3() {
		return this.image;
	}

	@Override
	public TextureRegion getTexture() {
		// TODO Auto-generated method stub
		return null;
	}

}
