package contenu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ecran.Ecran;

public class Jeu extends Game{
	private Ecran ecran;
	public SpriteBatch batch;
	
	
	
	@Override
	public void create () {
		Assets.load();
		this.ecran = new Ecran(this);
		
		this.batch=new SpriteBatch();
		
		this.setScreen(ecran);
		
		
		

	}
	
	public void supprScreen(){
		this.setScreen(null);
		//this.getScreen().dispose();
	}

	@Override
	public void render () {
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		super.render();
		
		//batch.begin();
		
		//batch.end();
	}
}
