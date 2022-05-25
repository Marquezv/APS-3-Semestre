package com.goaway.game.Sprites;

import com.badlogic.gdx.math.Rectangle;// Classe utilizada para definir retangulos
import com.goaway.game.GoAway;// Classe Principal
import com.goaway.game.Screens.PlayScreen; // Classe da tela Principal

public class End extends InteractiveTileObject{
	public End(PlayScreen screen, Rectangle bounds) {
		// Define que precisa de uma tela e uma forma pra ser utilizada
		super(screen, bounds);
		fixture.setUserData(this);// Define que isso sera representado como uma fixture
		setCategoryFilter(GoAway.END);// Define o bit
		
	}

	@Override
	public void onHit() {
		System.out.println("End");
		
	}
}
