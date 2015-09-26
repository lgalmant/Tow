package entite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import contenu.Assets;

public class Spawn extends Entite{
	
	private TextureRegion image;
	private int x;
	private int y;
	
	public Spawn(int x, int y){
		this.image=Assets.jaune;
		this.x=x;
		this.y=y;
	}

	public TextureRegion getTexture() {
		return this.image;
	}

	public TextureRegion getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
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
