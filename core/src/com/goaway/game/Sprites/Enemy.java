package com.goaway.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;// Classe utilizada para definir vetores(x,y)
import com.badlogic.gdx.physics.box2d.Body;// Classe que define os corpos no jogo
import com.badlogic.gdx.physics.box2d.World;// Classe que define o mundo do jogo
import com.goaway.game.Screens.PlayScreen;// Classe da tela Principal

public abstract class Enemy extends Sprite{
	protected World world;
	protected PlayScreen screen;
	public Body b2body;
	public Vector2 velocity;
	
	public Enemy(PlayScreen screen, float x, float y) {
		//Precisa de uma tela e de seu posicionamento
		this.world = screen.getWorld();
		this.screen = screen;
		setPosition(x, y);
		defineEnemy();
		velocity = new Vector2(2, 0);
	}
	
	protected abstract void defineEnemy();//Metodo abstrato para definir os inimigos
	public abstract void update(float dt);//Metodo abstrato para atualizar os inimigos
	public abstract void hitOnShape();//Metodo abstrato para sentir quando sofrer toque
	
	public void reverseVelocity(boolean x, boolean y) {
		// metodo que reverte a velocidade(pra frente e pra tras)
		if(x) 
			velocity.x = -velocity.x;
		
		if(y) 
			velocity.y = -velocity.y;
		
	}
		
}
