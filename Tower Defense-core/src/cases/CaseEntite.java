package cases;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import entite.Entite;

public class CaseEntite extends Case{
	
	private Entite entite;
	private TextureRegion image;

	public CaseEntite(int xIndice, int yIndice) {
		super(xIndice, yIndice);
	}

	public Entite getEntite() {
		return entite;
	}

	public void setEntite(Entite entite) {
		this.entite = entite;
	}

	public TextureRegion getImage() {
		return image;
	}

	public void setImage(TextureRegion image) {
		this.image = image;
	}
	

	
}
