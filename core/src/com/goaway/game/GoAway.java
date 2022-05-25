package com.goaway.game;// Instancia o pacote

import com.badlogic.gdx.Game; // Classe que geri o jogo
import com.badlogic.gdx.graphics.g2d.SpriteBatch; // Classe utilizada para desenhar usando indices
import com.goaway.game.Screens.PlayScreen;// Classe controladora que forma a tela do jogo

public class GoAway extends Game {
	public static final int V_WIDTH = 720; // Varivel que define a Largura da tela do jog
	public static final int V_HEIGHT = 360; // Varivel que define a Altura da tela do jog
	public static final float PPM = 100; // Valor utlizado para padronizar as medidas
	
	public static final short NOTHING_BIT = 0; // Valor utlizado para tirar a definição
	public static final short GROUND_BIT = 1; // Valor utlizado para definir o solo
	public static final short PLAYER_BIT = 2; // Valor utlizado para definir o player
	public static final short BRICK_BIT = 4; // Valor utlizado para definir as estruturas de colisao dos inimigos
	public static final short COIN_BIT = 8; // Valor utlizado para definir os lixos/moedas
	public static final short DESTROYED_BIT = 16; // Valor utlizado para definir que o bloco foi destroido
	public static final short OBJECT_BIT = 32; // Valor utlizado para definir os objetos
	public static final short ENEMY_BIT = 64; // Valor utlizado para definir os corpos dos inimigos
	public static final short ENEMY_HEAD_BIT = 128; // Valor utlizado para definir a cabeça dos inimigos
	public static final short DEATH = 256; // Valor utlizado para definir a zona de morte do player
	public static final short END = 512; // Valor utlizado para definir a zona de final de jogo
	
	public SpriteBatch batch; // Seta a classe utilizada para a renderização dos sprites

	@Override
	public void create () {
		batch = new SpriteBatch();  // Instancia a classe utilizada para a renderização dos sprites 
		setScreen(new PlayScreen(this)); // Seta a tela inicia do jogo
	}

	@Override
	public void render () { // Metodo que realiza a renderização
		super.render();
	}
	
	
}
