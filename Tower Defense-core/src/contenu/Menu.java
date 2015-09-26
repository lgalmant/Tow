package contenu;

import cases.CaseEntite;

public class Menu {
	
	public CaseEntite[][] cases;
	
	public Menu(int x, int y){
		this.cases=new CaseEntite[x][y];
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				this.cases[i][j]=new CaseEntite(i,j);
				
			}
		}
	}

}
