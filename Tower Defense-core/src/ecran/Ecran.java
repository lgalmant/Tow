package ecran;

import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

import contenu.Assets;
import contenu.Constantes;
import contenu.Jeu;
import contenu.Settings;

public class Ecran extends ScreenAdapter implements Constantes{
	
	private Jeu jeu;
	private Rectangle recNouv;
	private TextureRegion sound;
	private Preferences prefs = Gdx.app.getPreferences("My Preferences");
	private String texteInitial;
    private String titreBoiteDialogue;
    private String message;
    private boolean nomEntre;
	
	
	public Ecran(Jeu jeu){
		 this.jeu=jeu;
		 nomEntre=false;
		 texteInitial = "";
		 recNouv=new Rectangle(L/3+40, H/4+20,375,315);
			if(Settings.autoriserSon)
				this.sound=Assets.soundOn;
			else
				this.sound=Assets.soundOff;
		 if(prefs.contains("dernierNom"))
			 texteInitial=prefs.getString("dernierNom");
         titreBoiteDialogue = "Quel est votre nom ?";

         Gdx.input.getTextInput(new TextInputListener() {
                    @Override
                    public void input(String texteSaisi) {
                    	Settings.nom=texteSaisi;
                    	prefs.flush();
                    	if(Settings.nom.equals("")||Settings.nom.equals(" ")||Settings.nom.equals("  ")||Settings.nom.equals("   ")
                    			||Settings.nom.equals("    ")
                    			||Settings.nom.equals("     ")||Settings.nom.equals("      ")||Settings.nom.equals("       ")||
                    			Settings.nom.equals("        ")){
                    		Settings.nom="Inconnu";
                    		prefs.flush();
                    	}
                    	else{
                    		prefs.putString("dernierNom", texteSaisi);
                        	prefs.flush();
                    	}
                    	nomEntre=true;
                    }
                    @Override
                    public void canceled() {
                    	Settings.nom="inconnu";
                    	nomEntre=true;
                    	prefs.flush();
                    }
         }, titreBoiteDialogue, texteInitial, message);
		
		
		
		
		
		//String nom;
		//System.out.println("Entrez votre nom :  ");
		//Scanner scanner=new Scanner(System.in);
		//nom=scanner.nextLine();
		//scanner.close();
		//System.out.println("Bonjour "+nom);
		//System.out.println("Bonjour "+prefs.getString("sonptitnom"));
		
		//prefs.putString("sonptitnom", nom);
		//prefs.flush();
	}
	
	public void update(){
		if(Gdx.input.justTouched()){
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
				return;

			}
			if(this.recNouv.contains(Gdx.input.getX(), Gdx.input.getY())&&nomEntre){
				this.jeu.setScreen(new EcranJeu(this.jeu));
				
				return;
			}
			
		}
		
	}
	
	public void draw(){
		GL20 gl = Gdx.gl;
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.jeu.batch.enableBlending();
		jeu.batch.begin();
		
		jeu.batch.draw(Assets.nouvelle, 0, 0, L, H);
		jeu.batch.draw(this.sound,0,H-CEP,CEP,CEP);
		/*if(afficher)
        {
            font.draw(jeu.batch, message, 10, 200);
        }*/
		jeu.batch.end();

		/*ShapeRenderer g=new ShapeRenderer();
		g.begin(ShapeType.Filled);
		
		g.setColor(Color.GRAY);
		g.rect(L/3+40, H/4+20,375,315);
		g.end();*/
	}
	
	@Override
	public void render(float Delta) {
		this.draw();
		this.update();
		
	}
	
	

	

}
