package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.service.Avaliador;

public class AvaliadorTest {
	
	private Avaliador leiloeiro;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;

	@Before
	public void criaAvaliador() {
		this.leiloeiro = new Avaliador();
		this.joao = new Usuario("Jo�o");
		this.jose = new Usuario("Jos�");
		this.maria = new Usuario("Maria");
	}

	@Test(expected=RuntimeException.class)
	public void naoDeveAvaliarLeiloesSemNenhumLance() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation").constroi();
	
		leiloeiro.avalia(leilao);
	}
	
	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		Leilao leilao = new CriadorDeLeilao()
	            .para("Playstation 3 Novo")
	            .lance(joao, 250.0)
	            .lance(jose, 300.0)
	            .lance(maria, 400.0)
	            .constroi();

		leiloeiro.avalia(leilao);

		assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
		assertThat(leiloeiro.getMenorLance(), equalTo(250.0));
	}
	
	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		Leilao leilao = new CriadorDeLeilao()
		        .para("Playstation 3 Novo")
		        .lance(joao, 1000.0)
		        .constroi();
		
		leiloeiro.avalia(leilao);

		assertThat(leiloeiro.getMaiorLance(), 
				equalTo(leiloeiro.getMenorLance()));
	}
	
	@Test
	public void deveEncontrarOsTresMaioresLances() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 novo")
				.lance(joao, 100.0)
				.lance(maria, 200.0)
				.lance(joao, 300.0)
				.lance(maria, 400.0)
				.constroi();

		leiloeiro.avalia(leilao);
		
		List<Lance> maiores = leiloeiro.getTresMaiores();
		assertEquals(3, maiores.size());
		assertThat(maiores, hasItems(
					new Lance(maria, 400.0),
					new Lance(joao, 300.0),
					new Lance(maria, 200.0)
		));
	}
}