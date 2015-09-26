package cases;

import com.badlogic.gdx.graphics.Texture;

public class CaseSol extends Case{
	
	private Texture image;

	public Texture getImage() {
		return image;
	}

	public void setImage(Texture image) {
		this.image = image;
	}

	public CaseSol(int xIndice, int yIndice) {
		super(xIndice, yIndice);
	}

}
