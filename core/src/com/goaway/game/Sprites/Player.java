package com.goaway.game.Sprites;






//Importa pacotes da biblioteca
import com.badlogic.gdx.graphics.g2d.Animation;// Animacão dos sprites
import com.badlogic.gdx.graphics.g2d.Sprite; // Definicão dos sprites e propriedades
import com.badlogic.gdx.graphics.g2d.TextureRegion; // Textura do sprite dentro do array de imagens
import com.badlogic.gdx.math.Vector2; // Classe que define os vetores 2d para formar areas de contato
import com.badlogic.gdx.physics.box2d.Body; // Classe que inicia o corpo 
import com.badlogic.gdx.physics.box2d.BodyDef; // Classe que define o corpo
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef; // Classe que define dados de contato 
import com.badlogic.gdx.physics.box2d.PolygonShape; // Classe utilizada para definir o corpo como poligono
import com.badlogic.gdx.physics.box2d.World; // Classe que gerencia as ações e efeitos no mundo do jogo
import com.badlogic.gdx.utils.Array;// Classe utilitaria da biblioteca
import com.goaway.game.GoAway; // Classe principal do jogo
import com.goaway.game.Screens.PlayScreen; // Classe controladora que forma a tela do jogo

public class Player extends Sprite {
	public enum State {
		FALLING, JUMPING, STANDING, RUNNING, DEAD			// Define os enuns de ações possiveis do personagem
	};

	public static State currentState;// Define o estado atual do personagem
	public State previousState;// Define o estado previo do personagem
	public World world;
	public Body b2body;
	private TextureRegion playerStand;
	private TextureRegion playerDead;
	private Animation<TextureRegion> playerRun;
	private Animation<TextureRegion> playerJump;
	private float stateTimer;// Define a variavel de tempo de estado
	private boolean runningRight;// Verifica a direção do corpo
	public static boolean playerIsDead = false;// Verifica se o player esta morto

	public boolean isPlayerIsDead() {
		return playerIsDead;
	}

	public void setPlayerIsDead(boolean playerIsDead) {
		this.playerIsDead = playerIsDead;
	}

	public Player(PlayScreen screen) {
		// Metodo Construtor ( Define :
		// ° sprite do corpo
		super(screen.getAtlas().findRegion("otter_idle"));
		// ° define o gerenciamento do jogo
		this.world = screen.getWorld();
		// ° define os estados do corpo
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		runningRight = true;

		Array<TextureRegion> frames = new Array<TextureRegion>();
		// Define frame como um array de textura
		for (int i = 7; i < 10; i++) {
			frames.add(new TextureRegion(getTexture(), i * 200, 0, 200, 200));
			// Adiciona no frame onde estão as texturas e quais 
		}
		// Indica qual a ação correspondente ao frame
		playerRun = new Animation<TextureRegion>(0.1f, frames);
		// Limpa o array 
		frames.clear();

		for (int i = 2; i < 6; i++) {
			frames.add(new TextureRegion(getTexture(), i * 200, 0, 200, 200));
			// Adiciona no frame onde estão as texturas e quais 
		}
		// Indica qual a ação correspondente ao frame
		playerJump = new Animation<TextureRegion>(0.1f, frames);

		playerStand = new TextureRegion(getTexture(), 0, 0, 200, 200);

		playerDead = new TextureRegion(getTexture(), 0, 0, 200, 200);

		definePlayer();
		setBounds(0, 0, 60 / GoAway.PPM, 60 / GoAway.PPM);// Seta os tamanhos do player
		
		setRegion(playerStand);// Seta a região padrão

	}

	public void update(float dt) {
		// Metodo update( Verifica a posição atual e qual o frame/ação atual do corpo)
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));
	}

	public TextureRegion getFrame(float dt) {
		// Metodo getFrame( Define o estado e mudança do corpo)
		currentState = getState();// recebe o estado atual

		TextureRegion region;// Inicia uma regiao de textura
		switch (currentState) {
		// altera o region de acordo com o estado do corpo
		case DEAD:
			region = playerDead;
			hit();
			break;
		case JUMPING:
			region = playerJump.getKeyFrame(stateTimer, true);
			break;
		case RUNNING:
			region = playerRun.getKeyFrame(stateTimer, true);
			break;
		case FALLING:
		case STANDING:

		default:
			region = playerStand;
			break;
		}
		// Altera o lado do sprite de acordo com a velocidade do corpo
		if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
			// Negativa
			region.flip(true, false);
			runningRight = false;
		} else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
			// Positiva
			region.flip(true, false);
			runningRight = true;
		}
		// Verifica o estadoAtual e o estadoPrevio sao iguais
		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		// se true : stateTimer = stateTimer + dt
		// false: stateTimer = 0
		previousState = currentState;
		// estadoPrevio recebe o estado atual
		return region;
		// retorna a ação/frame atual
	}

	public State getState() {
		// Metodo getState(Verifica o estado atual do corpo(velociade e eixos)
		// e retorna em forma de enuns
		if (playerIsDead) {
			return State.DEAD;
		} else if (b2body.getLinearVelocity().y > 0
				|| (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
			return State.JUMPING;
		} else if (b2body.getLinearVelocity().y < 0) {
			return State.FALLING;
		} else if (b2body.getLinearVelocity().x != 0) {
			return State.RUNNING;
		} else {
			return State.STANDING;
		}
	}

	public void definePlayer() {
		// Metodo definePlayer(Define :
		// Instancia a varivel da classe BodyDef
		BodyDef bdef = new BodyDef();
		// Define a posição inicial do corpo no mapa
		bdef.position.set(300 / GoAway.PPM, 300 / GoAway.PPM);
		// Defiine o tipo de comportamento esperado para o corpo
		bdef.type = BodyDef.BodyType.DynamicBody;
		// Define que o corpo pode ser usado no mundo 
		b2body = world.createBody(bdef);

		FixtureDef fdef = new FixtureDef();
		// Define que o corpo é afetado pelas variveis do mundo
		PolygonShape shape = new PolygonShape();
		// Define que o corpo utilizara a classe PolygonShape para a definição da caixa de contato
		Vector2[] verticeShape = new Vector2[4];
		// Define que serão usados 4 vertices
		// Define a posição dos vetores no sprite para a caixa de contato
		verticeShape[0] = new Vector2(8, 2).scl(1 / GoAway.PPM);
		verticeShape[1] = new Vector2(-8, 2).scl(1 / GoAway.PPM);
		verticeShape[2] = new Vector2(8, -18).scl(1 / GoAway.PPM);
		verticeShape[3] = new Vector2(-8, -18).scl(1 / GoAway.PPM);
		// Seta que os vertices da caixa de contato
		shape.set(verticeShape);
		// Define qual bit representara o corpo no sistema
		fdef.filter.categoryBits = GoAway.PLAYER_BIT;
		// Define quais corpos o corpo tera contato no sistema
		fdef.filter.maskBits = GoAway.GROUND_BIT | GoAway.COIN_BIT | GoAway.BRICK_BIT | GoAway.ENEMY_BIT
				| GoAway.ENEMY_HEAD_BIT | GoAway.DEATH | GoAway.END;
		// Define o shape criado
		fdef.shape = shape;
		// Define que a fixture do corpo respresentada como "body"
		b2body.createFixture(fdef).setUserData("body");

	}

	public boolean isDead() {
		return playerIsDead;
	}

	public float getStateTimer() {
		return stateTimer;
	}

	public void hit() { // Define o que aconte quando o player morre
		playerIsDead = true;
		Filter filter = new Filter();
		filter.maskBits = GoAway.NOTHING_BIT;
		for (Fixture fixture : b2body.getFixtureList())
			fixture.setFilterData(filter);
	}

}
