package entite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import contenu.Assets;

public class Base extends Entite{
	
	private int vie;
	private TextureRegion image;
	private int x;
	private int y;

	public TextureRegion getImage() {
		return image;
	}

	public void setImage(TextureRegion image) {
		this.image = image;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Base(int vie, int x, int y) {
		this.vie = vie;
		this.x=x;
		this.y=y;
		this.image=Assets.rouge;
	}

	public int getVie() {
		return vie;
	}

	public void setVie(int vie) {
		this.vie = vie;
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

	public TextureRegion getTexture3() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}
