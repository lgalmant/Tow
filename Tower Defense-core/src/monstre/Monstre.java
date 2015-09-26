package monstre;

import tour.Tour;
import aStar.AStar;
import aStar.AreaMap;
import aStar.Node;
import aStar.Path;
import aStar.heuristics.ClosestHeuristic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import contenu.Assets;
import contenu.Constantes;
import contenu.Terrain;
import entite.Entite;
import entite.Mur;
import entite.Spawn;

//import ecran.EcranJeu.DeplacerMonstre;

/**
 * Classe représentant un monstre
 */
public abstract class Monstre extends Entite implements  Constantes{
	
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
	
	private Texture imageDos;
	private Texture imageDroite;
	private Texture imageGauche;
	private Texture imageFace;
	
	private int nbPas;
	private Path p;
	private int nbPasPro;
	private Path pPro;
	private boolean arrive;
	private boolean fou;
	private boolean enMouvement;
	private int etatDep=0;
	private Deplacer deplacer;
	private DeplacerGros deplacerGros;
	private boolean first;
	private boolean pro;
	private boolean mort;
	
	
	private static final int HAUT = 0;
	private static final int BAS = 1;
	private static final int DROITE = 2;
	private static final int GAUCHE = 3;
	
	
	private Thread t;
	private Thread t2;
	
	public Monstre(Terrain terrain){
		
		this.nbPas=0;
		this.terrain=terrain;
		this.spawn=this.terrain.spawn;
		this.xPos=this.spawn.getX()*CEP + CEP/6;
		this.yPos=this.spawn.getY()*CEP + CEP/6;
		this.xIndex=this.spawn.getX();
		this.yIndex=this.spawn.getY();
		this.arrive=false;
		this.fou=false;
		this.enMouvement=false;
		this.deplacer=new Deplacer(this);
		this.deplacerGros=new DeplacerGros(this);
		this.first=true;
		this.pro=false;
		this.mort=false;
		t = new Thread(this.deplacer);
		t2 = new Thread(this.deplacerGros);
		this.image=this.imageFace;
	    
		
	}
	
public Monstre(Terrain terrain, Texture i1,Texture i2,Texture i3,Texture i4){
		
		
		this.imageDos=i1;
		this.imageDroite=i2;
		this.imageGauche=i3;
		this.imageFace=i4;
		this.nbPas=0;
		this.terrain=terrain;
		this.spawn=this.terrain.spawn;
		this.xPos=this.spawn.getX()*CEP + CEP/6;
		this.yPos=this.spawn.getY()*CEP + CEP/6;
		this.xIndex=this.spawn.getX();
		this.yIndex=this.spawn.getY();
		this.arrive=false;
		this.fou=false;
		this.enMouvement=false;
		this.deplacer=new Deplacer(this);
		this.deplacerGros=new DeplacerGros(this);
		this.first=true;
		this.pro=false;
		this.mort=false;
		t = new Thread(this.deplacer);
		t2 = new Thread(this.deplacerGros);
		this.image=this.imageFace;
		this.p=this.calculerChemin(this.spawn.getX(), this.spawn.getY());
	    
		
	}
	
	public Monstre(String nom, int cout, float income, int vie, String etat,
			int domages, float rayon, float vitesse, Spawn spawn) {
		this.nom = nom;
		this.cout = cout;
		this.income = income;
		this.vie = vie;
		this.etat = etat;
		this.domages = domages;
		this.rayon = rayon;
		this.vitesse = vitesse;
		this.image=Assets.communFace;
		this.spawn=spawn;
		this.xPos=this.spawn.getX()*CEP + CEP/6;
		this.yPos=this.spawn.getY()*CEP + CEP/6;
		this.xIndex=this.spawn.getX();
		this.yIndex=this.spawn.getY();
		
	}
	/**
	 * Gère, case par case, le déplacement d'un monstre
	 */
	public void depD(){
		
		/*if(this.t!=null)
			this.t.interrupt();*/
		this.deplacer.setM(this);
		this.t=new Thread(this.deplacer);
		
		this.setEnMouvement(true);
		this.t.start();
		//if(!this.isFirst()){
		//this.t.run();
		//}
		this.first=false;
	}
	/**
	 * Gère le déplacement global d'un monstre
	 * @param terrain Le terrain du jeu afin de calculer le chemin que le monstre doit prendre
	 */
	public void grosDep(Terrain terrain){
		if(!this.isMort()){
			this.terrain=terrain;
			this.deplacerGros.setM(this);
			this.t2=new Thread(this.deplacerGros);
			
			this.t2.start();
		}
		
		
	}
	
	
	
	public Texture getImageDos() {
		return imageDos;
	}

	public void setImageDos(Texture imageDos) {
		this.imageDos = imageDos;
	}

	public Texture getImageDroite() {
		return imageDroite;
	}

	public void setImageDroite(Texture imageDroite) {
		this.imageDroite = imageDroite;
	}

	public Texture getImageGauche() {
		return imageGauche;
	}

	public void setImageGauche(Texture imageGauche) {
		this.imageGauche = imageGauche;
	}

	public Texture getImageFace() {
		return imageFace;
	}

	public void setImageFace(Texture imageFace) {
		this.imageFace = imageFace;
	}



	class Deplacer implements Runnable{
		private Monstre m;
		public Deplacer(Monstre m){
			this.m=m;
			
		}
		public void setM(Monstre m){
			this.m=m;
		}
	    public void run() {
	    	
	    	this.m.setArrive(this.m.getxIndex()==this.m.terrain.base.getX()-1&&this.m.getyIndex()==this.m.terrain.base.getY());
	    	if(!m.isArrive()){
	    		while((m.getxPos()!=(this.m.getP().waypoints.get(m.getNbPas()).getX()*CEP))||
						(m.getyPos()!=(this.m.getP().waypoints.get(m.getNbPas()).getY()*CEP))){
					//System.out.println("Position x : " + m.getxPos() + " y = " + m.getyPos());
					if(m.getxPos()<this.m.getP().waypoints.get(m.getNbPas()).getX()*CEP){
						m.setxPos((m.getxPos()+1));
						m.setImage2(m.getImageDroite());
					}
					
					if(m.getxPos()>this.m.getP().waypoints.get(m.getNbPas()).getX()*CEP){
						m.setxPos((m.getxPos()-1));
						m.setImage2(m.getImageGauche());
						
					}
						

					if(m.getyPos()<this.m.getP().waypoints.get(m.getNbPas()).getY()*CEP){
						m.setyPos((m.getyPos()+1));
						m.setImage2(m.getImageDos());
						
					}
						
					if(m.getyPos()>this.m.getP().waypoints.get(m.getNbPas()).getY()*CEP){
						m.setyPos((m.getyPos()-1));
						m.setImage2(m.getImageFace());
						
					}
						
					
					try {
				        this.m.getT().sleep((long) ((1/(m.getVitesse())*1000)/CEP));
				      } catch (InterruptedException e) {
				        e.printStackTrace();
				      }
					/*if(this.m.getImage()==this.m.getImageDos())
						System.out.println("Image dos");
					if(this.m.getImage()==this.m.getImageFace())
						System.out.println("Image face");
					if(this.m.getImage()==this.m.getImageDroite())
						System.out.println("Image droite");
					if(this.m.getImage()==this.m.getImageGauche())
						System.out.println("Image gauche");*/
					
				}
		    	
		    	if(this.m.isPro()){
		    		this.m.setP(this.m.getpPro());
			    	this.m.setNbPas(this.m.getNbPasPro());
		    	}
		    	this.m.setPro(false);
		    	
		    	
		    	
		    	
	    	}
	    	m.setEnMouvement(false);
	    	this.m.grosDep(this.m.terrain);
	    	this.m.getT().stop();
	    	
	    }        
	    
	  }  
	
	
	
	public boolean isPro() {
		return pro;
	}

	public void setPro(boolean pro) {
		this.pro = pro;
	}

	class DeplacerGros implements Runnable{
		private Monstre m;
		public DeplacerGros(Monstre m){
			this.m=m;
			
		}
		public void setM(Monstre m){
			this.m=m;
		}
	    public void run() {
	    	if(!this.m.isEnMouvement()){
				Path p3=this.m.getP();
				/*for(Node n : p3.waypoints){
					System.out.println(n.getX() + "     " + n.getY());
					
				}*/
				//System.out.println("DEPLACEMENT, x=" + m.getxIndex() + " y= " + m.getyIndex());
				if(p3==null){
					this.m.setFou(true);
					this.m.setP(calculerCheminFou(this.m.getxIndex(),this.m.getyIndex()));
			    	this.m.setNbPas(0);
			    	this.m.depD();
					return;
				}
				if(this.m.isFou()){
					if(p3!=null){
						this.m.setFou(false);
					}
				}
				this.m.setArrive(this.m.getxIndex()==this.m.terrain.base.getX()-1&&this.m.getyIndex()==this.m.terrain.base.getY());
				if(!this.m.isArrive()){
					this.m.setxIndex(p3.waypoints.get(this.m.getNbPas()).getX());
					this.m.setyIndex(p3.waypoints.get(this.m.getNbPas()).getY());
					this.m.setNbPas(this.m.getNbPas()+1);
					
					this.m.depD();
								
					
				}
				else{
					while(!this.m.isMort()){
						this.m.terrain.base.setVie(this.m.terrain.base.getVie()-this.m.getDomages());
						try {
							this.m.getT2().sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			}
	    	if(this.m.isPro()){
	    		this.m.setP(this.m.getpPro());
		    	this.m.setNbPas(this.m.getNbPasPro());
	    	}
	    	this.m.getT2().stop();
	    }        
	    
	  }  
	
	
	private Path calculerChemin(int startX, int startY){
		int[][] map;
		map=new int[NBCOL+1][NBLIG+1];
		for(int i=0;i<NBCOL;i++){
			for(int j=0;j<NBLIG;j++){
				if(this.terrain.entite[i][j].getEntite()==null)
					map[i][j]=0;
				else
					map[i][j]=1;
			}
		}
		
		for(int i=0;i<NBCOL;i++){
			map[i][NBLIG]=0;
		}
		for(int i=0;i<NBLIG;i++){
			map[NBCOL][i]=0;
		}
        
        AreaMap Amap2 = new AreaMap(NBCOL+1, NBLIG+1, map);
        ClosestHeuristic heuristic2 = new ClosestHeuristic();
        AStar pathFinder2 = new AStar(Amap2, heuristic2);
        int goalX = this.terrain.base.getX()-1;
        int goalY = this.terrain.base.getY();
        Path p2=new Path();
        p2=pathFinder2.calcShortestPath(startX, startY, goalX, goalY);
        return p2;
        
        
	}
	
	public Thread getT2() {
		return t2;
	}

	public void setT2(Thread t2) {
		this.t2 = t2;
	}

	public boolean isFirst() {
		return first;
	}
	
	

	public boolean isMort() {
		return mort;
	}

	public void setMort(boolean mort) {
		this.mort = mort;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public int getEtatDep() {
		return etatDep;
	}
	
	

	public int getNbPasPro() {
		return nbPasPro;
	}

	public void setNbPasPro(int nbPasPro) {
		this.nbPasPro = nbPasPro;
	}

	public Path getpPro() {
		return pPro;
	}

	public void setpPro(Path pPro) {
		this.pPro = pPro;
	}

	public void setEtatDep(int etatDep) {
		this.etatDep = etatDep;
	}

	public Thread getT() {
		return t;
	}

	public void setT(Thread t) {
		this.t = t;
	}
	
	private Path calculerCheminFou(int startX, int startY){
		int[][] map;
		map=new int[NBCOL][NBLIG+1];
		for(int i=0;i<NBCOL;i++){
			for(int j=0;j<NBLIG;j++){
				if(this.terrain.entite[i][j].getEntite() instanceof Mur)
					map[i][j]=1;
				else
					map[i][j]=0;
				//map[i][j]=0;
			}
		}
		
		for(int i=0;i<NBCOL;i++){
			map[i][NBLIG]=0;
		}
        
        AreaMap Amap2 = new AreaMap(NBCOL, NBLIG+1, map);
        ClosestHeuristic heuristic2 = new ClosestHeuristic();
        AStar pathFinder2 = new AStar(Amap2, heuristic2);
        int goalX = this.terrain.base.getX()-1;
        int goalY = this.terrain.base.getY();
        Path p2=new Path();
        p2=pathFinder2.calcShortestPath(startX, startY, goalX, goalY);
        return p2;
        
        
	}

	public boolean isEnMouvement() {
		return enMouvement;
	}

	public void setEnMouvement(boolean enMouvement) {
		this.enMouvement = enMouvement;
	}

	public boolean isFou() {
		return fou;
	}

	public void setFou(boolean fou) {
		this.fou = fou;
	}

	public boolean isArrive() {
		return arrive;
	}

	public void setArrive(boolean arrive) {
		this.arrive = arrive;
	}

	public void calculNextPos(){
		int xB = this.terrain.base.getX();
		int yB = this.terrain.base.getY();
		int x=this.xIndex;
		int y=this.yIndex;
		
	}
	
	
	

	public Path getP() {
		return p;
	}

	public void setP(Path p) {
		this.p = p;
	}

	public String getNom() {
		return nom;
	}
	
	

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getNbPas() {
		return nbPas;
	}

	public void setNbPas(int nbPas) {
		this.nbPas = nbPas;
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
	
	public abstract int getOr();

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

	/*public TextureRegion getTexture() {
		return this.image;
	}*/

	public Texture getImage() {
		return image;
	}

	public void setImage2(Texture image) {
		this.image = image;
	}

	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
		this.xIndex=(int) (xPos/CEP);
	}

	public float getyPos() {
		return yPos;
		
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
		this.yIndex=(int) (yPos/CEP);
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
		this.xPos=xIndex*CEP;
	}

	public int getyIndex() {
		return yIndex;
	}

	public void setyIndex(int yIndex) {
		this.yIndex = yIndex;
		this.yPos=yIndex*CEP;
	}
	
	public Texture getTexture3(){
		return this.image;
	}
	
	
	
	
	

}
