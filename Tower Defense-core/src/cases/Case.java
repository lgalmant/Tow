package cases;

import contenu.Constantes;


public abstract class Case implements Constantes {

    private int xIndice;
    private int yIndice;
    
    public Case(int xIndice, int yIndice) {
          this.xIndice = xIndice;
          this.yIndice = yIndice;
    }

    // indice horizontal
    public void setIndiceX(int x) {
          this.xIndice = x;
    }

    // indice horizontal
    public int getIndiceX() {
          return this.xIndice;
    }

    // indice vertical
    public void setIndiceY(int y) {
          this.yIndice = y;
    }

    // indice vertical
    public int getIndiceY() {
          return this.yIndice;
    }

    // coordonnée horizontale en pixels
    public int getX() {
          return this.xIndice * CEP;
    }
    
    public int getXM(int p) {
        return this.xIndice * CEP + p;
  }

    // coordonnée verticale en pixels
    public int getY() {
          return this.yIndice * CEP;
    }
    
    public int getYM(int p) {
        return this.yIndice * CEP + p;
  }

    public int getLargeur() {
          return CEP;
    }

    public int getHauteur() {
          return CEP;
    }
    
  
}