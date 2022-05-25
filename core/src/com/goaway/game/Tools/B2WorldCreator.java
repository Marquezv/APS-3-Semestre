package com.goaway.game.Tools;

import com.badlogic.gdx.maps.MapObject; // Classe que define as propriedades dos objetos
import com.badlogic.gdx.maps.objects.RectangleMapObject; // Define que os objetos serao rectangulos
import com.badlogic.gdx.maps.tiled.TiledMap;// Classe que carrega o mapa criado no TiledMap
import com.badlogic.gdx.math.Rectangle;// Classe utilizada para definir retangulos
import com.badlogic.gdx.physics.box2d.Body; // Classe que inicia o corpo 
import com.badlogic.gdx.physics.box2d.BodyDef;// Classe que define o corpo
import com.badlogic.gdx.physics.box2d.FixtureDef;// Classe que define dados de contato 
import com.badlogic.gdx.physics.box2d.PolygonShape;// Classe utilizada para definir o corpo como poligono
import com.badlogic.gdx.physics.box2d.World;// Classe que gerencia as ações e efeitos no mundo do jogo
import com.badlogic.gdx.utils.Array;// Classe utilitaria da biblioteca
import com.goaway.game.GoAway;// Classe principal do jogo
import com.goaway.game.Screens.PlayScreen;// Classe da tela
import com.goaway.game.Sprites.Brick; // Classe dos blocos
import com.goaway.game.Sprites.Coin;// Classe das moedas
import com.goaway.game.Sprites.Death;// Classe da morte
import com.goaway.game.Sprites.Objects;// classe dos objetos
import com.goaway.game.Sprites.Rat;// classe dos inimigos

public class B2WorldCreator {
	private Array<Rat> rats;
	// Define frame como um array de textura

	public B2WorldCreator(PlayScreen screen) {
		World world = screen.getWorld();
		TiledMap map = screen.getMap();
		
		BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        
        //create coin bodies/fixtures
        for(MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Death(screen, rect);
            
        }
        
        //create ground bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ GoAway.PPM, (rect.getY() + rect.getHeight() / 2)/ GoAway.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / GoAway.PPM, rect.getHeight() / 2 / GoAway.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        
//        //create brick bodies/fixtures
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Brick(screen, rect);
  
        }

        //create coin bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(screen, rect);
            
        }
        
        //create object bodies/fixtures
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Objects(screen, rect);
            
        }
        
        // create rats
        rats = new Array<Rat>();
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            rats.add(new Rat(screen, rect.getX() / GoAway.PPM, rect.getY() / GoAway.PPM));
            
        }
        

	}
	 public Array<Rat> getRats() {
 		return rats;
 	}

 	public void setRats(Array<Rat> rats) {
 		this.rats = rats;
 	}
}
