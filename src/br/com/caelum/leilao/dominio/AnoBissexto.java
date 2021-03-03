package br.com.caelum.leilao.dominio;

public class AnoBissexto {
	private Integer ano;
	
	public Integer getAnoBissexto() {
		return ano;
	}
	
	public boolean ehBissexto(int ano) {
		if(((ano % 4) == 0) && ((ano % 100) != 0)) return true; 
	    else if ((ano % 400) == 0) return true; 
	    else return false;  	
	}
}