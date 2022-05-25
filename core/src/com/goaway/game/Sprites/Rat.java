package com.goaway.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;// Animacão dos sprites
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;// Textura do sprite dentro do array de imagens
import com.badlogic.gdx.math.Vector2;// Classe que define os vetores 2d para formar areas de contato
import com.badlogic.gdx.physics.box2d.BodyDef;// Classe que define o corpo
import com.badlogic.gdx.physics.box2d.EdgeShape; // Classe que define formas de 4 pontos
import com.badlogic.gdx.physics.box2d.FixtureDef; // Classe que define dados de contato 
import com.badlogic.gdx.physics.box2d.PolygonShape; // Classe utilizada para definir o corpo como poligono
import com.badlogic.gdx.utils.Array; // Classe para utilizar arrays
import com.goaway.game.GoAway;// Classe principal do jogo
import com.goaway.game.Scenes.Hud; // Classe do Hud
import com.goaway.game.Screens.PlayScreen;// Classe controladora que forma a tela do jogo

public class Rat extends Enemy{

	private float stateTime;
	private Animation<TextureRegion> walkAnimation;
	private Array<TextureRegion> frames;
	private boolean setToDestroy = false;
	private boolean destroyed = false;
	private boolean runningRight;

	public Rat(PlayScreen screen, float x, float y) {
		super(screen, x, y);
		runningRight = true;

		Array<TextureRegion> frames = new Array<TextureRegion>();
		// Define frame como um array de textura
		for(int i = 0; i < 2; i++) 
			frames.add(new TextureRegion(screen.getAtlasEnemy().findRegion("Walk"), i * 64, 0, 32, 32));
		// Adiciona no frame onde estão as texturas e quais 
		// Indica qual a ação correspondente ao frame

		walkAnimation = new Animation(0.1f, frames);
		stateTime = 0;
		setBounds(getX(), getY(), 32 / GoAway.PPM, 32 / GoAway.PPM);// Seta os tamanhos do inimigo
		setToDestroy = false; // Se tiver sido elimininado
		destroyed = false; // Se foi destroido
	}
	
	public void update(float dt) {
		stateTime += dt;
		if(setToDestroy && !destroyed) {// Verifica se foi destroido
			world.destroyBody(b2body);// Tira do mundo
			destroyed = true; 
			setRegion(new TextureRegion(screen.getAtlasEnemy().findRegion("Hurt"), 26, 0, 32, 32));
			// Coloca o boloco de fundo
			Hud.addScore(100);// Adiciona no hud
			stateTime = 0;// Altera o contador
		}
		else if(!destroyed){ // Se estiver vivo
			b2body.setLinearVelocity(velocity);// Seta que tem velocidade linear
			setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
			// Seta a posição
			setRegion(walkAnimation.getKeyFrame(stateTime, true));
			// Seta a animação
			TextureRegion region;
			region = walkAnimation.getKeyFrame(stateTime, true);
			if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
				region.flip(true, false);
				runningRight = false;// Anda para esquerda
			}
			else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
				region.flip(true, false);
				runningRight = true;// Anda para direita
			}
			
		}
		
		
	}
	
	@Override
	protected void defineEnemy() {
		BodyDef bdef = new BodyDef();// Instancia a varivel da classe BodyDef
		bdef.position.set(getX() , getY());	// Define a posição inicial do corpo no mapa
		bdef.type = BodyDef.BodyType.DynamicBody;// Defiine o tipo de comportamento esperado para o corpo
		b2body = world.createBody(bdef);// Define que o corpo pode ser usado no mundo 

		
		FixtureDef fdef = new FixtureDef();// Define que o corpo é afetado pelas variveis do mundo
		PolygonShape shape = new PolygonShape();// Define que o corpo utilizara a classe PolygonShape para a definição da caixa de contato
		Vector2[] verticeShape = new Vector2[4];// Define que serão usados 4 vertices
		// Define a posição dos vetores no sprite para a caixa de contato
		verticeShape[0] = new Vector2(10, 0).scl(1 / GoAway.PPM);
		verticeShape[1] = new Vector2(-10, 0).scl(1 / GoAway.PPM);
		verticeShape[2] = new Vector2(10, -15).scl(1 / GoAway.PPM);
		verticeShape[3] = new Vector2(-10, -15).scl(1 / GoAway.PPM);
		// Seta que os vertices da caixa de contato
		shape.set(verticeShape);
		
		// Define qual bit representara o corpo no sistema
		fdef.filter.categoryBits = GoAway.ENEMY_BIT;
		// Define quais corpos o corpo tera contato no sistema
		fdef.filter.maskBits = GoAway.GROUND_BIT | 
				GoAway.BRICK_BIT |
				GoAway.OBJECT_BIT |
				GoAway.PLAYER_BIT;
		// Define o shape criado
		fdef.shape = shape;		
		b2body.createFixture(fdef).setUserData(this);
		// Define a fixture do corpo 
		 EdgeShape head = new EdgeShape();
	     head.set(new Vector2(-8 / GoAway.PPM, 4/ GoAway.PPM), new Vector2(8 / GoAway.PPM, 4/ GoAway.PPM));
	     fdef.filter.categoryBits = GoAway.ENEMY_HEAD_BIT;
	     fdef.shape = head;
	     fdef.restitution = 0.5f;
	     b2body.createFixture(fdef).setUserData(this);
	}
	
	public void draw(Batch batch) {
		if(!destroyed || stateTime < 1) {
			super.draw(batch);
		}
	}
	
	@Override
	public void hitOnShape() {
		setToDestroy = true;
	}
	
	
}
