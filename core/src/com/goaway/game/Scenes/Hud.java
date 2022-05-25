package com.goaway.game.Scenes;

import com.badlogic.gdx.graphics.Color;// Classe de cores no padrao rgb
import com.badlogic.gdx.graphics.OrthographicCamera; // Classe que define a camera como visao orthografica
import com.badlogic.gdx.graphics.g2d.BitmapFont; // Classe que renderiza as fontes
import com.badlogic.gdx.graphics.g2d.SpriteBatch;// Classe utilizada para desenhar usando indices
import com.badlogic.gdx.scenes.scene2d.Stage; // Classe que manipula a viewport e implementa input events
import com.badlogic.gdx.scenes.scene2d.ui.Label; // Classe que renderiza as labels
import com.badlogic.gdx.scenes.scene2d.ui.Table; // Classe que formata as labels em tabela
import com.badlogic.gdx.utils.Disposable; // Classe/Interface para recursos de utilidade 
import com.badlogic.gdx.utils.viewport.FitViewport; // Classe utilizada para manter a porporção no mundo
import com.badlogic.gdx.utils.viewport.Viewport; // Classe que gerencia a camera e determina como as coordenadas do mundo
import com.goaway.game.GoAway; // Classe Principal

public class Hud implements Disposable{ // implenta as utilidades
	// Instanciações
	public Stage stage; 
	private Viewport viewport;
	
	private Integer worldTimer;
	private float timeCount;
	private static Integer score;
	
	private Label countdownLabel;
	private static Label scoreLabel;
	private Label timeLabel;
	private Label levelLabel;
	private Label worldLabel;
	private Label playerLabel;
	
	
	public Hud(SpriteBatch sb) {
		worldTimer = 0; //Valor inicial do Timer
		timeCount = 0; //Valor inicial do Timer real
		score = 0; // Valor da pontuação
		
		viewport = new FitViewport(GoAway.V_WIDTH, GoAway.V_HEIGHT, new OrthographicCamera());// Configura a camera do jogo
		stage = new Stage(viewport, sb); // Recebe a viewport e define como padrao
		
		Table table = new Table(); 
		table.top(); // Localização da tabela
		table.setFillParent(true); // Aumenta de acordo com a tela
		
		countdownLabel = new Label(String.format("%06d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		// Label do Timer
		scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		// Label do Score
		timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		// Label do Text Timer
		playerLabel =new Label("Luara", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		// Label do NomE
		
		// Posiciona as labels na table
		table.add(playerLabel).expandX().padTop(10);
		table.add(worldLabel).expandX().padTop(10);
		table.add(timeLabel).expandX().padTop(10);
		table.row();
		table.add(scoreLabel).expandX();
		table.add(levelLabel).expandX();
		table.add(countdownLabel).expandX();
	
		stage.addActor(table);// Adiciona a table na tela

	}
	
	public void update(float dt) {// Timer - adiciona valor a cada update/1s
		timeCount += dt; 
		if(timeCount >= 1) {
			worldTimer++;
			countdownLabel.setText(String.format("%03d", worldTimer));
			timeCount = 0;
		}
		
	}
	
	public static void addScore(int value) { // Adciona os pontos no Hud
		score += value;
		scoreLabel.setText(String.format("%06d", score));
	}
	
	@Override
	public void dispose() { // Metodo para fechar a classe
		stage.dispose();
	}
	
}
