package ecran;

import java.awt.Graphics;



























import tour.GrosseTour;
import tour.Tour;
import tour.TourCommune;
import monstre.Monstre;
import monstre.MonstreCommun;
import monstre.MonstreGros;
import monstre.MonstreRapide;
import aStar.AStar;
import aStar.AreaMap;
import aStar.Node;
import aStar.Path;
import aStar.heuristics.AStarHeuristic;
import aStar.heuristics.ClosestHeuristic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.sun.javafx.geom.Rectangle;

import contenu.Assets;
import contenu.Constantes;
import contenu.Jeu;
import contenu.Joueur;
import contenu.Menu;
import contenu.Settings;
import contenu.Terrain;
import entite.Base;
import entite.Mur;
import entite.Spawn;

/**
 * C'est la classe principale du jeu, c'est elle qui gèrera le moteur graphique et le moteur physique
 */
public class EcranJeu extends ScreenAdapter implements Constantes{
	
	static final int GAME_READY = 0; // Etat lorsque l'on crée l'écran jeu
	static final int GAME_RUNNING = 1; // lorsque le jeu tourne
	static final int GAME_PAUSED = 2; // Lors d'une pause (pas encore utilisé)
	static final int GAME_LEVEL_END = 3; // Lorsque le jeu se termine
	static final int GAME_OVER = 4; // Le jeu est terminé
	
	static final int NO = 0;
	static final int COURRANTE = 1;	// pour ajouter des tours
	static final int GROSSE = 2;
	static final int c = 3;
	static final int d = 4;
	
	private Jeu jeu;
	private int state; // Etat du jeu
	private Terrain terrain;
	private Menu menu;
	private Menu menu2;
	private Joueur joueur;
	private int cptM;
	private int seuil=1000;
	private Path pT;
	private int cptD;
	private int seuilD=10;
	private Rectangle rectangles[][];
	private Rectangle rectangles2[][];
	private Tour tourCourrante;
	private int TtourCourrante;
	private Rectangle recP;
	private TextureRegion sound;
	private boolean att=false;
	private Rectangle recT;
	private Timer t;
	private int pile[];
	private static final int nbMonstreP=6;
	private Menu menu3;
	private boolean remove=false;
	private int mr;
	private Thread thread;
	private Thread tSpawn;
	private Thread tTour;
	
	int pos=80;
	BitmapFont font2[] = new BitmapFont[this.nbMonstreP];
	String s3=new String("");
	ShapeRenderer g=new ShapeRenderer();
	//ShapeRenderer g2=new ShapeRenderer();
	
	String vie;
	String orS;
	
	BitmapFont font = new BitmapFont();
	
    Path p;
    private int nbM=0;
    private boolean spawning;

	public EcranJeu(Jeu jeu) {
		this.jeu=jeu;
		this.state=GAME_READY;
		this.terrain=new Terrain(NBCOL,NBLIG);
		this.menu=new Menu(NBCOLM,NBLIGM);
		this.menu2=new Menu(NBCOLM,NBLIGM);
		this.menu3=new Menu(nbMonstreP,1);
		this.TtourCourrante=0;
		this.rectangles = new Rectangle[NBCOLM][NBLIGM];
		this.rectangles2 = new Rectangle[NBCOLM][NBLIGM];
		this.recT=new Rectangle(0,0,L,H);
		//this.recS=new Rectangle(0,0,CEP,CEP);
		this.recP=new Rectangle(0,H-NBLIG*CEP,NBCOL*CEP,NBLIG*CEP);
		this.tourCourrante=null;
		this.pile=new int[this.nbMonstreP+1];
		this.spawning=false;
		
		if(Settings.autoriserSon)
			this.sound=Assets.soundOn;
		else
			this.sound=Assets.soundOff;
		
		this.t= new Timer();
		
		font.setColor(Color.BLACK);
		font.setScale((float) 1.5);
		
		
		for(int i=0;i<this.nbMonstreP;i++){
			this.font2[i]=new BitmapFont();
		}
		
		
        
        //pathFinder.printPath();
        
        
        
        
        
		
		
		genererNiveau();
		//calculerChemin();
	}
	
	
	
	/**
	 * CalculerChemin permet de calculer le chemin du monstre
	 * 
	 * @param startX Position x du monstre
	 * @param startY Position y du monstre
	 * @return Le nouveau chemin du monstre
	 */
	
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
	
	/**
	 * Permet de déplacer un monstre
	 */
	
	public void deplacerMonstres(){
			if(!this.terrain.monstres.isEmpty()){
				for(Monstre m : this.terrain.monstres){
					m.grosDep(this.terrain);
				}
			
		}
		
	}
	
	/**
	 * Gère l'attaque des tours en fonction des monstres
	 */
	
	public void attaqueTour(){
		
			if(!this.terrain.tours.isEmpty()&&!this.terrain.monstres.isEmpty()){
				for(Tour t : this.terrain.tours){
					//for(Monstre m : this.terrain.monstres){
					for(int i=this.terrain.monstres.size()-1;i>=0;i--){	// On parcourt la liste à l'envers pour des soucis de concurrence entre les Threads
						Monstre m=this.terrain.monstres.get(i);
						//System.out.println("x= " + t.getCercle().x + " y= " + t.getCercle().y + "rayon= " +t.getCercle().radius);
						//System.out.println("mx= " + m.getxPos() + " my= " + m.getyPos());
						if(!t.isAttaque()&&!m.isMort()){
							if(t.getCercle().contains(m.getxPos(),m.getyPos())){
								t.setAttaque(true);
								this.att=true;
								if(m.getxIndex()<t.getX()&&m.getyIndex()<t.getY()){
									t.image=t.basGauche;
									t.setImage(t.basGauche);
									
								}
								else if(m.getxIndex()>t.getX()&&m.getyIndex()<t.getY()){
									t.image=t.basDroite;
									t.setImage(t.basDroite);
								}
								else if(m.getxIndex()<t.getX()&&m.getyIndex()>t.getY()){
									t.image=t.hautGauche;
									t.setImage(t.hautGauche);
								}
								else if(m.getxIndex()>t.getX()&&m.getyIndex()>t.getY()){
									t.image=t.hautDroite;
									t.setImage(t.hautDroite);
								}
								else if(m.getxIndex()<t.getX()){
									t.image=t.gauche;
									t.setImage(t.gauche);
								}
								else if(m.getxIndex()>t.getX()){
									t.image=t.droite;
									t.setImage(t.droite);
								}
								else if(m.getyIndex()>t.getY()){
									t.image=t.haut;
									t.setImage(t.haut);
								}
								else if(m.getyIndex()<t.getY()){
									t.image=t.bas;
									t.setImage(t.bas);
								}
								Assets.playSound(Assets.fleche);
								t.tir(m);
								m.setVie(m.getVie()-t.getDegats());
								
							}
							if(m.getVie()<=0){
								Assets.playSound(Assets.mort);
								this.remove=true;
								
								mr=this.terrain.monstres.indexOf(m);
								this.joueur.setIncome(this.joueur.getIncome()-m.getIncome());
								this.joueur.setOr(this.joueur.getOr()+m.getOr());
								this.terrain.monstres.remove(mr);
								mr=0;
								m.setMort(true);
							}
							
						}
						
						
					}
					
					t.setAttaque(false);
				}
				if(remove){
					
					this.remove=false;
				}
				
			}
			this.att=false;
		
		
	}
	
	/**
	 * Fait apparaître une vague de monstres
	 */
	
	public void spawnVague(){
		this.spawning=true;
		this.terrain.monstresArr.addAll(this.terrain.monstresEnn);
		this.terrain.monstresEnn.clear();
			
			// ON ARRETE
			for(Monstre m : this.terrain.monstresArr){
				m.setP(calculerChemin(m.getxIndex(),m.getyIndex()));
				
				
				try {
					this.tSpawn.sleep((long) (1000/m.getVitesse()));// Ici on attend que le monstre ait une case d'écart avec le spawn
																	// pour que l'autre spawn
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				m.grosDep(this.terrain);
				this.terrain.monstres.add(m);
				if(m instanceof MonstreCommun){
					this.pile[0]--;
				}
				else if(m instanceof MonstreGros){
					this.pile[1]--;
				}
				else if(m instanceof MonstreRapide){
					this.pile[2]--;
				}
			}
			
			//this.terrain.monstres.addAll(this.terrain.monstresArr);
			this.terrain.monstresArr.clear();
			
			/*for(int i=0;i<this.nbMonstreP;i++){
				this.pile[i]=0;
			}*/
			//deplacerMonstres();
			this.spawning=false;
		
	}
	
	/*public void dessinerChemin(){
		if(this.p==null){
		}
		else{
			for(Node n : p.waypoints){
	        	
	        	ShapeRenderer g4=new ShapeRenderer();
	    		
	    		g4.begin(ShapeType.Filled);
	    		
	    		g4.setColor(Color.RED);
	    		g4.rect(this.terrain.entite[n.getX()][n.getY()].getX(), this.terrain.entite[n.getX()][n.getY()].getY(), CEP, CEP);

	    		
	    		g4.end();
	        }
		}
		
	}*/
	
	/**
	 * Génère le niveau, se place dans le constructeur de l'EcranJeu
	 */
	
	private void genererNiveau() {
		creerMap2();
		
		
		//this.terrain.monstres.add(new Monstre("commun", 20, (float)10.0, 50, "", 10, 2, (float)2.0, this.terrain.spawn));
		for(int i = 0; i < NBCOLM ; i++){
			for(int j = 0;j<NBLIGM;j++){
				this.rectangles[i][j]=new Rectangle(this.menu.cases[i][j].getXM(900),H-(this.menu.cases[i][j].getYM(60))-CEP,CEP,CEP);
				this.rectangles2[i][j]=new Rectangle(this.menu2.cases[i][j].getXM(900),H-(this.menu2.cases[i][j].getYM(320))-CEP,CEP,CEP);
			}
		}
		
		for(int i=0;i<this.nbMonstreP;i++){
			this.pile[i]=0;
		}
		
		
		Timer.schedule(new Task(){
		    @Override
		    public void run() {
		        income();
		    }
		}, 0,1);
		
		/*Timer.schedule(new Task(){
		    @Override
		    public void run() {
		        spawnVague();
		    }
		}, 5,5);*/
		
		this.tSpawn = new Thread(new Vague());
		this.tSpawn.start();
		
		this.tTour = new Thread(new AttTour());
		this.tTour.start();
		 //thread = new Thread(new DeplacerMonstre());
	     //thread.start();
		
		
			
			
			/*Timer.schedule(new Task(){
				@Override
			    public void run() {
			        System.out.println("1s");
			    }
			}, 0,1);*/
			
			
			/*Timer.schedule(new Task(){
				@Override
			    public void run() {
					
			        attaqueTour();
			    }
			}, 0,(float) 0.5);*/
		}
	
	/**
	 * Permet de créer la première map
	 */
	public void creerMap1(){
		this.terrain.base=new Base(500, 18, 5);
		this.terrain.entite[18][5].setEntite(this.terrain.base);
		this.terrain.spawn=new Spawn(0,5);
		this.terrain.entite[0][5].setEntite(this.terrain.spawn);
		this.joueur=new Joueur(Settings.nom, (float)1, 10000, this.terrain.base.getVie());
		this.menu.cases[0][0].setEntite(new TourCommune(0,0));
		this.menu.cases[0][1].setEntite(new GrosseTour(0,0));
		
		this.menu2.cases[0][0].setEntite(new MonstreCommun(this.terrain));
		this.menu2.cases[1][0].setEntite(new MonstreGros(this.terrain));
		this.menu2.cases[2][0].setEntite(new MonstreRapide(this.terrain));
		
		
		creerMur(1,1);
		creerMur(1,2);
		creerMur(2,2);
		creerMur(5,3);
		creerMur(5,3);
		creerMur(6,5);
		//creerMur(7,0);
		//creerMur(7,1);
		creerMur(7,2);
		creerMur(7,3);
		creerMur(7,4);
		creerMur(7,5);
		creerMur(7,6);
		//creerMur(7,7);
		//creerMur(7,8);
		//creerMur(8,7);
		//creerMur(8,7);
		creerMur(10,0);
		creerMur(10,1);
		creerMur(10,2);
		creerMur(10,3);
		creerMur(10,4);
		creerMur(10,5);
		creerMur(10,6);
		//creerMur(10,7);
		creerMur(15,2);
		//creerMur(16,8);
		//creerMur(18,9);
		creerMur(18,4);
		creerMur(18,7);
	}
	/**
	 * Permet de créer la deuxième map
	 */
	public void creerMap2(){
		this.terrain.base=new Base(1000, 18, 1);
		this.terrain.entite[18][1].setEntite(this.terrain.base);
		this.terrain.spawn=new Spawn(0,5);
		this.terrain.entite[0][5].setEntite(this.terrain.spawn);
		this.joueur=new Joueur(Settings.nom, (float)1, 10000, this.terrain.base.getVie());
		this.menu.cases[0][0].setEntite(new TourCommune(0,0));
		this.menu.cases[0][1].setEntite(new GrosseTour(0,0));
		
		this.menu2.cases[0][0].setEntite(new MonstreCommun(this.terrain));
		this.menu2.cases[1][0].setEntite(new MonstreGros(this.terrain));
		this.menu2.cases[2][0].setEntite(new MonstreRapide(this.terrain));
		
		
		creerMur(3,0);
		creerMur(3,1);
		creerMur(3,2);
		creerMur(3,3);
		creerMur(3,4);
		creerMur(3,5);
		creerMur(3,6);
		creerMur(3,7);
		creerMur(3,8);
		
		creerMur(2,2);
		creerMur(4,3);
		creerMur(5,3);
		creerMur(6,5);
		//creerMur(7,0);
		//creerMur(7,1);
		creerMur(7,2);
		//creerMur(7,3);
		creerMur(7,4);
		creerMur(7,5);
		creerMur(7,6);
		creerMur(7,7);
		creerMur(7,8);
		creerMur(7,9);
		//creerMur(7,7);
		//creerMur(7,8);
		//creerMur(8,7);
		//creerMur(8,7);
		creerMur(10,0);
		creerMur(10,1);
		creerMur(10,2);
		//creerMur(10,3);
		creerMur(10,4);
		creerMur(10,5);
		creerMur(10,6);
		//creerMur(10,7);
		creerMur(15,2);
		//creerMur(16,8);
		//creerMur(18,9);
		creerMur(18,4);
		creerMur(18,6);
	}
	
	/**
	 * Classe permettant le déplacement des monstres dans un nouveau Thread
	 * @see EcranJeu#deplacerMonstres()
	 */
	
	class DeplacerMonstre implements Runnable{
	    public void run() {
	    	/*Timer.schedule(new Task(){
				@Override
			    public void run() {
					
			        deplacerMonstres();
			        System.out.println("1s dep");
			    }
			}, 0,1);  */ 
	    	
	    	while(true){
	    		deplacerMonstres();
	    		try {
					thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    }               
	  }   
	
	/**
	 * Classe permettant le tir des tours dans un nouveau Thread
	 * @see EcranJeu#attaqueTour()
	 */
	
	class AttTour implements Runnable{
	    public void run() {
	    	/*Timer.schedule(new Task(){
				@Override
			    public void run() {
					
			        deplacerMonstres();
			        System.out.println("1s dep");
			    }
			}, 0,1);  */ 
	    	
	    	while(true){
	    		attaqueTour();
	    		try {
					tTour.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    }               
	  }
	
	
	
	class Vague implements Runnable{
	    public void run() {
	    	/*Timer.schedule(new Task(){
				@Override
			    public void run() {
					
			        deplacerMonstres();
			        System.out.println("1s dep");
			    }
			}, 0,1);  */ 
	    	try {
				tSpawn.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	while(true){
	    		spawnVague();
	    		try {
	    			tSpawn.sleep(6000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    }               
	  }   
	
		    
		
	
	
	
	public void creerMur(int x, int y){
		this.terrain.entite[x][y].setEntite(new Mur());
	}
	
	public void income(){
		this.joueur.setOr((int) (this.joueur.getOr()+this.joueur.getIncome()));
		
		
		//System.out.println("ddd");
	}
	
	
	/**
	 * Méthode principale du moteur physique, permet de lancer les différentes méthodes d'upgrade en fonction de l'état du jeu
	 * @param deltaTime fps
	 * @see EcranJeu#state
	 */

	public void update (float deltaTime) {
		if (deltaTime > 0.1f) deltaTime = 0.1f;
		switch (state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_LEVEL_END:
			updateLevelEnd();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}
	
	private void updateReady(){
		if (Gdx.input.justTouched()) {
			if(recS.contains(Gdx.input.getX(), Gdx.input.getY())){
				if(this.sound==Assets.soundOn){
					this.sound=Assets.soundOff;
					Assets.musique.stop();
					Settings.autoriserSon=false;
				}
					
				else{
					this.sound=Assets.soundOn;
					Assets.musique.play();
					Settings.autoriserSon=true;
				}

			}
			else
				state = GAME_RUNNING;
		}
	}
	
	private void updateRunning(float deltaTime){
		this.joueur.setVie(this.terrain.base.getVie());
		
		if(this.joueur.isPerdu()){
			this.state=GAME_LEVEL_END;
		}
		else{
			if(this.terrain.base.getVie()<=0)
				this.joueur.setPerdu(true);
			
		}
		
		
		
		
		
		if(Gdx.input.justTouched()){
			int or=this.joueur.getOr();
			int X=Gdx.input.getX();
			int Y=H-Gdx.input.getY();
			if(this.recP.contains(Gdx.input.getX(), Gdx.input.getY())){
				if(this.TtourCourrante!=0){
					
					for(int i=0;i<NBCOL;i++){
						for(int j=0;j<NBLIG;j++){
							if(this.terrain.entite[i][j].getEntite()==null&&X>this.terrain.ground[i][j].getX()&&X<(this.terrain.ground[i][j].getX()+CEP)&&Y>this.terrain.ground[i][j].getY()&&Y<(this.terrain.ground[i][j].getY()+CEP)){
								this.TtourCourrante=0;
								this.terrain.entite[i][j].setEntite(this.tourCourrante);
								this.tourCourrante.setX(this.terrain.ground[i][j].getIndiceX());
								this.tourCourrante.setY(this.terrain.ground[i][j].getIndiceY());
								this.terrain.tours.add((Tour) this.tourCourrante);
								break;
								
							}
						}
					}
					
				}
				else{
					for(int i=0;i<NBCOL;i++){
						for(int j=0;j<NBLIG;j++){
							if(this.terrain.entite[i][j].getEntite()!=null&&X>this.terrain.ground[i][j].getX()&&X<(this.terrain.ground[i][j].getX()+CEP)&&Y>this.terrain.ground[i][j].getY()&&Y<(this.terrain.ground[i][j].getY()+CEP)){
								if(this.terrain.entite[i][j].getEntite() instanceof Tour || this.terrain.entite[i][j].getEntite() instanceof Mur){
									this.terrain.tours.remove(this.terrain.entite[i][j].getEntite());
									this.terrain.entite[i][j].setEntite(null);
									
									break;
								}
								
								
							}
						}
					}
				}
				for(Monstre m : this.terrain.monstres){
					m.setpPro(calculerChemin(m.getxIndex(),m.getyIndex()));
					m.setNbPasPro(0);
					m.setPro(true);
					//m.grosDep(terrain);
				}
					
			}
			
			else{
				if(recS.contains(Gdx.input.getX(), Gdx.input.getY())){
					if(this.sound==Assets.soundOn){
						this.sound=Assets.soundOff;
						Assets.musique.stop();
						Settings.autoriserSon=false;
					}
						
					else{
						this.sound=Assets.soundOn;
						Assets.musique.play();
						Settings.autoriserSon=true;
					}

				}
				if(this.rectangles[0][0].contains(Gdx.input.getX(), Gdx.input.getY())){
					if(or>=this.menu.cases[0][0].getEntite().getCout()){
						this.joueur.setOr(or-this.menu.cases[0][0].getEntite().getCout());
						this.tourCourrante=new TourCommune(this.menu.cases[0][0].getIndiceX(),this.menu.cases[0][0].getIndiceY());
						this.TtourCourrante=COURRANTE;
								
					}
					return;
				}
				if(this.rectangles[0][1].contains(Gdx.input.getX(), Gdx.input.getY())){
					if(or>=this.menu.cases[0][1].getEntite().getCout()){
						this.joueur.setOr(or-this.menu.cases[0][1].getEntite().getCout());
						this.tourCourrante=new GrosseTour(this.menu.cases[0][1].getIndiceX(),this.menu.cases[0][1].getIndiceY());
						this.TtourCourrante=GROSSE;
								
					}
					
					return;
				}
				if(this.rectangles[0][2].contains(Gdx.input.getX(), Gdx.input.getY())){
					
					return;
				}
				if(this.rectangles[0][3].contains(Gdx.input.getX(), Gdx.input.getY())){
					
					return;
				}
				if(this.rectangles[0][4].contains(Gdx.input.getX(), Gdx.input.getY())){
					
					return;
				}
				if(this.rectangles[1][0].contains(Gdx.input.getX(), Gdx.input.getY())){
					
					return;
				}
				if(this.rectangles[1][1].contains(Gdx.input.getX(), Gdx.input.getY())){
					
					return;
				}
				
				
				
				
				if(!this.spawning){
					if(this.rectangles2[0][0].contains(Gdx.input.getX(), Gdx.input.getY())){
						if(or>=this.menu2.cases[0][0].getEntite().getCout()){
							
							this.joueur.setOr(or-this.menu2.cases[0][0].getEntite().getCout());
							this.joueur.setIncome(this.joueur.getIncome()+this.menu2.cases[0][0].getEntite().getIncome());
							this.terrain.monstresEnn.add((Monstre) new MonstreCommun(this.terrain));
							this.pile[0]++;
									
						}
						return;
					}
					if(this.rectangles2[1][0].contains(Gdx.input.getX(), Gdx.input.getY())){
						if(or>=this.menu2.cases[1][0].getEntite().getCout()){
							
							this.joueur.setOr(or-this.menu2.cases[1][0].getEntite().getCout());
							this.joueur.setIncome(this.joueur.getIncome()+this.menu2.cases[1][0].getEntite().getIncome());
							this.terrain.monstresEnn.add((Monstre) new MonstreGros(this.terrain));
							this.pile[1]++;
						}
						
						return;
					}
					if(this.rectangles2[2][0].contains(Gdx.input.getX(), Gdx.input.getY())){
						if(or>=this.menu2.cases[2][0].getEntite().getCout()){
							
							this.joueur.setOr(or-this.menu2.cases[2][0].getEntite().getCout());
							this.joueur.setIncome(this.joueur.getIncome()+this.menu2.cases[2][0].getEntite().getIncome());
							this.terrain.monstresEnn.add((Monstre) new MonstreRapide(this.terrain));
							this.pile[2]++;
						}
						
						return;
					}
				}
				
			}
			
			
			
			
		}
		
		
		
		/*if(this.cptM==0){
			this.terrain.monstres.add(new Monstre("commun", 20, (float)10.0, 50, "", 10, 2, (float)2.0, this.terrain.spawn));
			if(this.cptD==0){
				for(Monstre m : this.terrain.monstres){
					m.setxPos(m.getxPos()+160);
				}
				
			}
			else{
				cptD++;
				if(cptD>=this.seuilD)
					cptD=0;
			}
		}
		else{
			cptM++;
			if(cptM>=this.seuil)
				cptM=0;
		}*/
		/*if(this.cptD==0){
			for(Monstre m : this.terrain.monstres){
				m.setxPos(m.getxPos()+5);
				
			}
			System.out.println("+1p");
			cptD++;
			
		}
		else{
			cptD++;
			
			if(cptD>=this.seuilD)
				cptD=0;
		}*/
		
		
		
		
		
	}

	private void updatePaused(){
		
	}

	private void updateLevelEnd(){
		if(!this.terrain.monstres.isEmpty()){
			for(Monstre m : this.terrain.monstres){
				m.setMort(true);
			}
		}
		
		this.terrain.monstres.clear();
		this.terrain.monstresArr.clear();
		
		
		if(Gdx.input.justTouched()){
			if(this.recT.contains(Gdx.input.getX(), Gdx.input.getY())){
				if(recS.contains(Gdx.input.getX(), Gdx.input.getY())){
					if(this.sound==Assets.soundOn){
						this.sound=Assets.soundOff;
						Assets.musique.stop();
						Settings.autoriserSon=false;
					}
						
					else{
						this.sound=Assets.soundOn;
						Assets.musique.play();
						Settings.autoriserSon=true;
					}

				}
				else{
					this.state=GAME_OVER;
				}
				return;
				
			}
			
		}
	}

	private void updateGameOver(){
		//this.jeu.supprScreen();
		Ecran ecran=new Ecran(this.jeu);
		this.jeu.setScreen(ecran);
		this.dispose();
	}
	
	/**
	 * Méthode principale du moteur graphique du jeu. Tout comme update, elle lance les différentes méthodes de dessin 
	 * en fonction de l'état du jeu.
	 */
	
	public void draw(){
		GL20 gl = Gdx.gl;
		gl.glClearColor(0, 0, 0, 1); 
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL20.GL_BLEND);
		gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		
		switch (state) {
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_LEVEL_END:
			presentLevelEnd();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		}
	
		
		gl.glDisable(GL20.GL_BLEND);
		
	}
	
	private void presentRunning() {
		
		//dessinerChemin();
		//TEST 0
		
		jeu.batch.begin();
		jeu.batch.draw(Assets.fond, 0, 0, L, H);
		jeu.batch.end();
		
		g.begin(ShapeType.Line);
		
		g.setColor(Color.GRAY);
		
		
		for(int i=0;i<NBCOL;i++){
			for(int j=0;j<NBLIG;j++){
				g.rect(this.terrain.ground[i][j].getX(), this.terrain.ground[i][j].getY(), this.terrain.ground[i][j].getLargeur(), this.terrain.ground[i][j].getHauteur());
			}
		}
		
		
		for(int i=0;i<NBCOLM;i++){
			for(int j=0;j<NBLIGM;j++){
				g.rect(this.menu.cases[i][j].getXM(900), this.menu.cases[i][j].getYM(60), CEP, CEP);
				g.rect(this.menu2.cases[i][j].getXM(900), this.menu2.cases[i][j].getYM(320), CEP, CEP);
			}
		}
		

		//TEST 1
		
		this.menu3.cases[0][0].setImage(Assets.communFaceT);
		this.menu3.cases[1][0].setImage(Assets.grosFaceT);
		this.menu3.cases[2][0].setImage(Assets.rapideFaceT);
		
		for(int i=0;i<this.nbMonstreP-1;i++){
			for(int j=0;j<1;j++){
				g.rect(this.menu3.cases[i][j].getXM(50), this.menu3.cases[i][j].getYM(470), CEP, CEP);
				this.jeu.batch.begin();
				this.jeu.batch.enableBlending();
				if(this.menu3.cases[i][j].getImage()!=null){
					this.jeu.batch.draw(this.menu3.cases[i][j].getImage(), menu3.cases[i][j].getXM(50), menu3.cases[i][j].getYM(470), CEP, CEP);
				}
				
				
				
				
				font2[i].setColor(Color.PURPLE);
				font2[i].setScale((float) 1);
				s3="";
				s3+=this.pile[i];
				font2[i].draw(this.jeu.batch, s3, pos, menu3.cases[i][j].getYM(485));
				pos+=CEP;
				this.jeu.batch.end();
			}
			
		}
		pos=80;
		
		
		
		g.setAutoShapeType(true);
		
		g.rect(L/2-80, H-H/7-CEP, CEP, CEP);
		g.set(ShapeType.Filled);
		g.setColor(Color.WHITE);
		for(Tour t : this.terrain.tours){
			if(t.ilTir)
				g.circle(t.getTir().getX(), t.getTir().getY(), 1);
		}
		g.setColor(Color.GRAY);
		g.set(ShapeType.Line);
		
		
		g.end();
		
		
		
		//TEST 2
		
		for(int i=0;i<NBCOL;i++){
			for(int j=0;j<NBLIG;j++){
				if(this.terrain.entite[i][j].getEntite()!=null){
					
					
					
					this.jeu.batch.begin();
					this.jeu.batch.enableBlending();
					this.jeu.batch.draw(this.terrain.entite[i][j].getEntite().getTexture(), this.terrain.entite[i][j].getX(), this.terrain.entite[i][j].getY(), CEP, CEP);
					
					this.jeu.batch.end();
					
				}
				
				if(this.terrain.entite[i][j].getImage()!=null){
					this.jeu.batch.begin();
					this.jeu.batch.enableBlending();
					this.jeu.batch.draw(this.terrain.entite[i][j].getImage(), this.terrain.entite[i][j].getX(), this.terrain.entite[i][j].getY(), CEP, CEP);
					
					this.jeu.batch.end();
				}
					
					
			}
		}
		
		this.jeu.batch.begin();
		this.jeu.batch.enableBlending();
		this.jeu.batch.draw(this.sound,0,H-CEP,CEP,CEP);
		
		this.jeu.batch.end();
		//TESTTTTT
		
		
		
		for(int i=0;i<NBCOLM;i++){
			for(int j=0;j<NBLIGM;j++){
				
				if(this.menu.cases[i][j].getEntite() instanceof Tour){
					this.jeu.batch.enableBlending();
					this.jeu.batch.begin();
					this.jeu.batch.draw(this.menu.cases[i][j].getEntite().getTexture(), this.menu.cases[i][j].getXM(900), this.menu.cases[i][j].getYM(60), CEP, CEP);
					this.jeu.batch.end();
				}
				
				if(this.menu2.cases[i][j].getEntite() instanceof Monstre){
					
					this.jeu.batch.begin();
					this.jeu.batch.enableBlending();
					//System.out.println(this.menu2.cases[i][j].getEntite().getIncome());
					this.jeu.batch.draw(this.menu2.cases[i][j].getEntite().getTexture2(), this.menu2.cases[i][j].getXM(900), this.menu2.cases[i][j].getYM(320), CEP, CEP);
					this.jeu.batch.end();
				}
				
			}
		}
		
		if(!this.att){
			for(Monstre m : this.terrain.monstres){
				this.jeu.batch.enableBlending();
				this.jeu.batch.begin();
				//System.out.println("On dessine à x= " + (int)m.getxPos()+CEP/5 + " y= " +(int)m.getyPos()+CEP/5);
				//System.out.println("On dessine le monstre à x= " +m.getxIndex()+" y= " +m.getyIndex());
				this.jeu.batch.draw(m.getImage(), (int)m.getxPos()+CEP/5, (int)m.getyPos()+CEP/5, TM, TM);
				font.setScale((float) 0.7);
				font.draw(this.jeu.batch, String.valueOf(m.getVie()), m.getxPos()+CEP,m.getyPos()+CEP);
				this.jeu.batch.end();
			}
		}
		
		font.setScale((float) 1.5);
		this.jeu.batch.enableBlending();
		this.jeu.batch.begin();
		font.setColor(Color.YELLOW);
		
		font.draw(this.jeu.batch, "Tour séléctionnée", L/4, H-H/7);
		font.draw(this.jeu.batch, "Bon jeu, " + Settings.nom, L/4, H-H/10);
		
		switch(this.TtourCourrante){
		case COURRANTE : 
			this.jeu.batch.draw(this.tourCourrante.getTexture(), L/2-80, H-H/7-CEP, CEP, CEP);
			break;
		case GROSSE : 
			this.jeu.batch.draw(this.tourCourrante.getTexture(), L/2-80, H-H/7-CEP, CEP, CEP);
			break;
		case NO :
			break;
		}
		
		this.jeu.batch.draw(Assets.coeur, 3*L/4, (float) (H-H/9),32,32);
		vie=String.valueOf(this.joueur.getVie());
		orS=String.valueOf(this.joueur.getOr());
		font.draw(this.jeu.batch, vie, 3*L/4+48, H-H/9+25);
		
		this.jeu.batch.draw(Assets.coin, 3*L/4, H-H/7);
		
		font.draw(this.jeu.batch, orS, 3*L/4+48, H-H/7+25);
		this.jeu.batch.end();
		
		
		
		
	}

	private void presentGameOver() {
		
		
	}

	private void presentLevelEnd() {
		jeu.batch.begin();
		jeu.batch.draw(Assets.gameover, 0, 0, L, H);
		this.jeu.batch.draw(this.sound,0,H-CEP,CEP,CEP);
		jeu.batch.end();
		
	}

	private void presentPaused() {
		// TODO Auto-generated method stub
		
	}

	private void presentReady() {
		
		jeu.batch.begin();
		jeu.batch.draw(Assets.pret, 0, 0, L, H);
		this.jeu.batch.draw(this.sound,0,H-CEP,CEP,CEP);
		jeu.batch.end();
		
		
	}

	/**
	 * Méthode lancée en boucle, elle lance update et draw
	 * @see EcranJeu#update(float)
	 * @see EcranJeu#draw()
	 */
	@Override
	public void render (float delta) {
		update(delta);
		draw();
	}
	

	/**
	 * Est appellée lors d'un redimensionnement de la fenêtre pour déplacer les composants afin de s'adapter à la nouvelle taille
	 * @param width Largeur de l'écran
	 * @param height Hauteur de l'écran
	 */
	public void resize(int width, int height){
		for(int i=0;i<NBCOLM;i++){
			for(int j=0;i<NBLIGM;i++){
				this.rectangles[i][i]=new Rectangle(this.menu.cases[i][j].getXM(900),height-(this.menu.cases[i][j].getYM(60))-CEP,CEP,CEP);
				this.rectangles2[i][i]=new Rectangle(this.menu2.cases[i][j].getXM(900),height-(this.menu2.cases[i][j].getYM(320))-CEP,CEP,CEP);
			}
		}
		this.recT=new Rectangle(0,0,width,height);
		this.recP=new Rectangle(0,height-NBLIG*CEP,NBCOL*CEP,NBLIG*CEP);
	}
	
	

}
