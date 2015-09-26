package entite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import contenu.Assets;

public class Mur extends Entite{
	
	private TextureRegion image;
	
	public Mur(){
		this.image=Assets.gris;
	}

	public TextureRegion getTexture() {
		return this.image;
	}

	@Override
	public int getCout() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRayon() {
		// TODO Auto-generated method stub
		return 0;
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
