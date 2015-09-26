package contenu;

import java.util.ArrayList;

import cases.CaseEntite;
import cases.CaseSol;
import entite.Base;
import entite.Spawn;
import tour.Tour;
import monstre.Monstre;

public class Terrain implements Constantes{
	
	public CaseSol ground[][];
	public CaseEntite entite[][];
	public Base base;
	public Spawn spawn;
	public ArrayList<Monstre> monstres;
	public ArrayList<Monstre> monstresEnn; 
	public ArrayList<Monstre> monstresArr; 
	public ArrayList<Tour> tours; 
	
	public Terrain(int x, int y){
		this.monstres=new ArrayList<Monstre>();
		this.monstresEnn=new ArrayList<Monstre>();
		this.monstresArr=new ArrayList<Monstre>();
		this.tours=new ArrayList<Tour>();
		this.ground=new CaseSol[x][y];
		for(int i=0;i<NBCOL;i++){
			for(int j=0;j<NBLIG;j++){
				this.ground[i][j]=new CaseSol(i,j);
			}
		}
		this.entite=new CaseEntite[x][y];
		for(int i=0;i<NBCOL;i++){
			for(int j=0;j<NBLIG;j++){
				this.entite[i][j]=new CaseEntite(i,j);
			}
		}
	}

}
