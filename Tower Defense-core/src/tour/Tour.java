package tour;

import monstre.Monstre;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

import contenu.Constantes;
import entite.Entite;

public abstract class Tour extends Entite implements Constantes{
	
	private String nom;
	private int cout;
	private float rayon;
	private int degats;
	private int vie;
	private float debit;
	public TextureRegion image;
	private Circle cercle;
	private int x, y, xPos, yPos;
	private boolean attaque;
	private Thread t;
	
	public TextureRegion haut;
	public TextureRegion bas;
	public TextureRegion droite;
	public TextureRegion gauche;
	public TextureRegion hautGauche;
	public TextureRegion hautDroite;
	public TextureRegion basGauche;
	public TextureRegion basDroite;
	public Tir tir;
	
	public boolean ilTir=false;
	
	
	public Tour(String nom, int cout, float rayon, int degats, int vie,
			float debit, int x, int y, TextureRegion image, TextureRegion haut, TextureRegion bas
			, TextureRegion droite, TextureRegion gauche, TextureRegion hautGauche, TextureRegion hautDroite
			, TextureRegion basGauche, TextureRegion basDroite) {
		this.nom = nom;
		this.cout = cout;
		this.rayon = rayon;
		this.degats = degats;
		this.vie = vie;
		this.debit = debit;
		this.image=image;
		this.x=x;
		this.y=y;
		this.xPos=x*CEP;
		
		this.yPos=y*CEP;
		this.cercle=new Circle(xPos+CEP/2, yPos+CEP/2, this.rayon*CEP+CEP/2+1);
		this.attaque=false;
		
		this.droite=droite;
		this.gauche=gauche;
		this.haut=haut;
		this.bas=bas;
		this.basDroite=basDroite;
		this.basGauche=basGauche;
		this.tir=new Tir(this);
		this.hautDroite=hautDroite;
		this.hautGauche=hautGauche;
		this.t= new Thread(this.tir);
	}
	
	public Tour(int x, int y){
		this.x=x;
		this.y=y;
		this.xPos=x*CEP;
		this.yPos=y*CEP;
		this.cercle=new Circle(xPos+CEP/2, yPos+CEP/2, this.rayon);
		this.attaque=false;
	}
	
	public void tir(Monstre m){
		this.tir=new Tir(this);
		this.tir.setM(m);
		this.t=new Thread(this.tir);
		
		this.t.start();
	}
	
	public class Tir implements Runnable{
		private Monstre m;
		private Tour tour;
		private int x,y;
		//private Rectangle rec;
		public Tir(Tour tour){
			this.tour=tour;

			this.x=tour.getxPos()+CEP/2;
			this.y=tour.getyPos()+CEP/2;
			
		}
		
		public int getX(){
			return this.x;
		}
		public int getY(){
			return this.y;
		}

		public void setM(Monstre m){
			
			this.m=m;
			//this.rec=new Rectangle(m.getxPos()-10, m.getyPos()-10, CEP+20,CEP+20);
		}
		
		
	    public void run() {
	    	
	    	tour.ilTir=true;
	    	  while(this.x!=m.getxPos()+CEP/2&&this.y!=m.getyPos()+CEP/2){
	    	  //while(this.rec.contains(m.getxPos(), m.getyPos())){
	    		/*System.out.println("La tour était positionnée à x= " + tour.getxPos() + " y= " + tour.getyPos() +
		    			" et le tir est à x= " + this.x + " y= " + this.y);*/
	    		
	    		if(m.getxPos()+CEP/2>this.x){
	    			this.x++;
				}
	    		if(m.getyPos()+CEP/2>this.y){
	    			this.y++;
				}
	    		if(m.getxPos()+CEP/2<this.x){
	    			this.x--;
				}
	    		if(m.getyPos()+CEP/2<this.y){
	    			this.y--;
				}
	    		
	    		/*if(m.getxPos()>this.x&&m.getyPos()>this.y){
	    			this.x++;
	    			this.y++;
					
				}
				else if(m.getxPos()<this.x&&m.getyPos()>this.y){
					this.x--;
	    			this.y++;
				}
				else if(m.getxPos()>this.x&&m.getyPos()<this.y){
					this.x++;
	    			this.y--;
				}
				else if(m.getxPos()<this.x&&m.getyPos()<this.y){
					this.x--;
	    			this.y--;
				}*/
	    		
	    		
	    		try {
					t.sleep(8);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	tour.ilTir=false;
	    	t.stop();
	    }               
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
	
	

	public Thread getT() {
		return t;
	}

	public void setT(Thread t) {
		this.t = t;
	}

	public Tir getTir() {
		return tir;
	}

	public void setTir(Tir tir) {
		this.tir = tir;
	}

	public TextureRegion getImage() {
		return image;
	}

	public void setImage(TextureRegion image) {
		this.image = image;
	}

	public boolean isAttaque() {
		return attaque;
	}

	public void setAttaque(boolean attaque) {
		this.attaque = attaque;
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

	public TextureRegion getTexture() {
		return this.image;
	}

	public Circle getCercle() {
		return cercle;
	}

	public void setCercle(Circle cercle) {
		this.cercle = cercle;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		this.xPos=x*CEP;
		this.cercle.x=this.xPos+CEP/2;
		this.cercle.y=this.yPos+CEP/2;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		this.yPos=y*CEP;
		this.cercle.x=this.xPos+CEP/2;
		this.cercle.y=this.yPos+CEP/2;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
		this.x=xPos/CEP;
		this.cercle.x=this.xPos+CEP/2;
		this.cercle.y=this.yPos+CEP/2;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
		this.y=yPos/CEP;
		this.cercle.x=this.xPos+CEP/2;
		this.cercle.y=this.yPos+CEP/2;
	}
	
	

}
