package com.goaway.game.Sprites;

import com.badlogic.gdx.math.Rectangle;// Classe utilizada para definir retangulos
import com.goaway.game.GoAway;// Classe Principal
import com.goaway.game.Screens.PlayScreen; // Classe da tela Principal

public class Objects extends InteractiveTileObject{
	public Objects(PlayScreen screen, Rectangle bounds) {
		// Define que precisa de uma tela e uma forma pra ser utilizada
		super(screen, bounds);
		fixture.setUserData(this);// Define que isso sera representado como uma fixture
		setCategoryFilter(GoAway.OBJECT_BIT);// Define o bit
		
	}

	@Override
	public void onHit() {
		// TODO Auto-generated method stub
		
	}

}
