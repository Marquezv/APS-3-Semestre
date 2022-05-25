package com.goaway.game.Sprites;

import com.badlogic.gdx.math.Rectangle;// Classe utilizada para definir retangulos
import com.goaway.game.GoAway;// Classe Principal
import com.goaway.game.Scenes.Hud;// Classe de Hud
import com.goaway.game.Screens.PlayScreen; // Classe da tela Principal

public class Brick extends InteractiveTileObject{
	public Brick(PlayScreen screen, Rectangle bounds) {
		// Define que precisa de uma tela e uma forma pra ser utilizada
		super(screen, bounds);
		fixture.setUserData(this);// Define que isso sera representado como uma fixture
		setCategoryFilter(GoAway.BRICK_BIT);// Define o bit
		
	}

	@Override
	public void onHit() {
		setCategoryFilter(GoAway.DESTROYED_BIT);// Destroi o bloco
		getCell().setTile(tile);// Tira o bloco do mapa
		Hud.addScore(100);// Adiciona o score
	}
}
