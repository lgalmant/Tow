package contenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	
	public static TextureRegion nouvellePartie;
	
	public static Texture menu;
	public static Texture couleur;
	public static Texture fond;
	public static Texture nouvelle;
	public static Texture gameover;
	
	public static Texture communDos;
	public static Texture communDroite;
	public static Texture communGauche;
	public static Texture communFace;
	
	public static Texture grosDos;
	public static Texture grosDroite;
	public static Texture grosGauche;
	public static Texture grosFace;
	
	public static Texture rapideDos;
	public static Texture rapideDroite;
	public static Texture rapideGauche;
	public static Texture rapideFace;
	public static TextureRegion rapideFaceT;
	public static TextureRegion grosFaceT;
	public static TextureRegion communFaceT;
	
	public static Texture CGdroite;
	public static Texture CGgauche;
	public static Texture CGhaut;
	public static Texture CGbas;
	public static Texture CGhautGauche;
	public static Texture CGbasGauche;
	public static Texture CGhautDroite;
	public static Texture CGbasDroite;
	
	public static Texture GGdroite;
	public static Texture GGgauche;
	public static Texture GGhaut;
	public static Texture GGbas;
	public static Texture GGhautGauche;
	public static Texture GGbasGauche;
	public static Texture GGhautDroite;
	public static Texture GGbasDroite;
	
	public static TextureRegion Cdroite;
	public static TextureRegion Cgauche;
	public static TextureRegion Chaut;
	public static TextureRegion Cbas;
	public static TextureRegion ChautGauche;
	public static TextureRegion CbasGauche;
	public static TextureRegion ChautDroite;
	public static TextureRegion CbasDroite;
	
	public static TextureRegion Gdroite;
	public static TextureRegion Ggauche;
	public static TextureRegion Ghaut;
	public static TextureRegion Gbas;
	public static TextureRegion GhautGauche;
	public static TextureRegion GbasGauche;
	public static TextureRegion GhautDroite;
	public static TextureRegion GbasDroite;
	
	public static Texture pret;
	public static TextureRegion rouge;
	public static TextureRegion vert;
	public static TextureRegion jaune;
	public static TextureRegion bleu;
	public static TextureRegion gris;
	public static TextureRegion orange;
	public static TextureRegion bleuC;
	public static TextureRegion violet;
	public static TextureRegion coeur;
	public static TextureRegion commun;
	public static TextureRegion gros;
	public static Texture items;
	public static Texture monstres;
	public static TextureRegion coin;
	
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	
	public static Music musique;
	public static Sound fleche;
	public static Sound mort;
	
	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}
	
	public static void load(){
		
		menu=loadTexture("menu.png");
		monstres=loadTexture("monstres.png");
		nouvellePartie=new TextureRegion(menu, 100, 100, 300, 50);
		couleur=loadTexture("couleurs.png");
		rouge=new TextureRegion(couleur, 30, 30, 50, 50);
		nouvelle=loadTexture("fondnouvelle.jpg");
		gameover=loadTexture("gameover.jpg");
		fond=loadTexture("fond2.jpg");
		pret=loadTexture("fondpret.jpg");
		
		
		
		CGdroite=loadTexture("Cdroite.png");
		CGgauche=loadTexture("Cgauche.png");
		CGbas=loadTexture("Cbas.png");
		CGhaut=loadTexture("Chaut.png");
		CGbasDroite=loadTexture("CbasDroite.png");
		CGbasGauche=loadTexture("CbasGauche.png");
		CGhautDroite=loadTexture("ChautDroite.png");
		CGhautGauche=loadTexture("ChautGauche.png");
		
		Cdroite=new TextureRegion(CGdroite);
		Cgauche=new TextureRegion(CGgauche);
		Chaut=new TextureRegion(CGhaut);
		Cbas=new TextureRegion(CGbas);
		CbasDroite=new TextureRegion(CGbasDroite);
		ChautDroite=new TextureRegion(CGhautDroite);
		CbasGauche=new TextureRegion(CGbasGauche);
		ChautGauche=new TextureRegion(CGhautGauche);
		
		GGdroite=loadTexture("Gdroite.png");
		GGgauche=loadTexture("Ggauche.png");
		GGbas=loadTexture("Gbas.png");
		GGhaut=loadTexture("Ghaut.png");
		GGbasDroite=loadTexture("GbasDroite.png");
		GGbasGauche=loadTexture("GbasGauche.png");
		GGhautDroite=loadTexture("GhautDroite.png");
		GGhautGauche=loadTexture("GhautGauche.png");
		
		Gdroite=new TextureRegion(GGdroite);
		Ggauche=new TextureRegion(GGgauche);
		Ghaut=new TextureRegion(GGhaut);
		Gbas=new TextureRegion(GGbas);
		GbasDroite=new TextureRegion(GGbasDroite);
		GhautDroite=new TextureRegion(GGhautDroite);
		GbasGauche=new TextureRegion(GGbasGauche);
		GhautGauche=new TextureRegion(GGhautGauche);
		
		communDos=loadTexture("communDos.png");
		communDroite=loadTexture("communDroite.png");
		//communDroite=loadTexture("ccc.png");
		communGauche=loadTexture("communGauche.png");
		communFace=loadTexture("communFace.png");
		
		grosDos=loadTexture("GrosDos.png");
		grosDroite=loadTexture("GrosDroite.png");
		grosGauche=loadTexture("GrosGauche.png");
		grosFace=loadTexture("GrosFace.png");
		
		rapideDos=loadTexture("rapideDos.png");
		rapideDroite=loadTexture("rapideDroite.png");
		rapideGauche=loadTexture("rapideGauche.png");
		rapideFace=loadTexture("rapideFace.png");
		
		
		vert=new TextureRegion(couleur, 500, 35, 50, 50);
		jaune=new TextureRegion(couleur, 200, 35, 50, 50);
		gris=new TextureRegion(couleur, 400, 35, 50, 50);
		bleu=new TextureRegion(couleur, 500, 180, 50, 50);
		orange=new TextureRegion(couleur, 30, 180, 50, 50);
		bleuC=new TextureRegion(couleur, 400, 180, 50, 50);
		violet=new TextureRegion(couleur, 200, 180, 50, 50);
		coeur=new TextureRegion(couleur, 40, 280, 50, 50);
		items=loadTexture("items.png");
		coin=new TextureRegion(items, 128, 32, 32, 32);
		commun=new TextureRegion(monstres, 20, 15, 90, 90);
		gros=new TextureRegion(monstres, 135, 15, 90, 90);
		communFaceT=new TextureRegion(communFace);
		grosFaceT=new TextureRegion(grosFace);
		rapideFaceT=new TextureRegion(rapideFace);
		
		soundOff = new TextureRegion(items, 0, 0, 64, 64);
		soundOn = new TextureRegion(items, 64, 0, 64, 64);
		
		musique = Gdx.audio.newMusic(Gdx.files.internal("musique.mp3"));
		musique.setLooping(true);
		musique.setVolume(1f);
		if(Settings.autoriserSon)
			musique.play();
		
		mort = Gdx.audio.newSound(Gdx.files.internal("mort.mp3"));
		fleche = Gdx.audio.newSound(Gdx.files.internal("fleche.wav"));
		
	}
	
	public static void playSound (Sound sound) {
		if(Settings.autoriserSon)
			sound.play(1);
	}

}
