package com.goaway.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap; // Classe que carrega o mapa criado no TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTile; // Classe que pega os tiles do mapa
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer; // Classe que pega as camadas do mapa
import com.badlogic.gdx.math.Rectangle;// Classe utilizada para definir retangulos
import com.badlogic.gdx.physics.box2d.Body;// Classe que define os corpos no jogo
import com.badlogic.gdx.physics.box2d.BodyDef; // Classe que define o corpo sofredor das fisicas
import com.badlogic.gdx.physics.box2d.Filter; // Classe que filtra
import com.badlogic.gdx.physics.box2d.Fixture;// Classe que define as fisicas
import com.badlogic.gdx.physics.box2d.FixtureDef; // Classe que define como as fisicas ocorrem
import com.badlogic.gdx.physics.box2d.PolygonShape; // Classe para definir polygonos
import com.badlogic.gdx.physics.box2d.World;// Classe que define o mundo do jogo
import com.goaway.game.GoAway;// Classe Principal
import com.goaway.game.Screens.PlayScreen;// Classe da tela Principal

public abstract class InteractiveTileObject {
	protected World world;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Rectangle bounds;
	protected Body body;
	
	protected Fixture fixture;
	
	public InteractiveTileObject(PlayScreen screen, Rectangle bounds) {
		// Define o mundo o mapa e as como sao as formas
		this.world = screen.getWorld();
		this.map = screen.getMap();
		this.bounds = bounds;
		
		BodyDef bdef = new BodyDef(); 
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.type = BodyDef.BodyType.StaticBody;// Os corpos sofreram como Static
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2)/ GoAway.PPM, (bounds.getY() + bounds.getHeight() / 2)/ GoAway.PPM);
        // Define a area de contato
        body = world.createBody(bdef);
        // Define como agiram os corpos no mundo
        shape.setAsBox(bounds.getWidth() / 2 / GoAway.PPM, bounds.getHeight() / 2 / GoAway.PPM);
        // Define o shape como box e seta as medidas
        fdef.shape = shape;
        // define o shape
        fixture = body.createFixture(fdef);
        // define que o corpo recebe as fixtures
	}
	
	public abstract void onHit();// Metodo abstrato de contato
	public void setCategoryFilter(short filterBit) { // Filtra os bits
		Filter filter = new Filter();
		filter.categoryBits = filterBit;
		fixture.setFilterData(filter);
	}
	
	public TiledMapTileLayer.Cell getCell(){ // Separa e monta os tiles do mapa
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(2);
		return layer.getCell((int)(body.getPosition().x * GoAway.PPM / 16), 
				(int)(body.getPosition().y * GoAway.PPM / 16));
	}
}
