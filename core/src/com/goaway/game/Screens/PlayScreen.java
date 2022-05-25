package com.goaway.game.Screens;

import com.badlogic.gdx.Gdx;// Classe utilizada pra fazer referencias a aplicao e inputs
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;// Interface utilizada para definir a classe como uma tela
import com.badlogic.gdx.graphics.GL20;// Interface que envolve os metodos principais do OpenGL ES 2.0
import com.badlogic.gdx.graphics.OrthographicCamera;// Classe que define a camera como orthografica
import com.badlogic.gdx.graphics.g2d.TextureAtlas; // Classe que carrega imagens de criadas pelo TexturePack
import com.badlogic.gdx.maps.tiled.TiledMap; // Classe que carrega o mapa criado no TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader;// Classe que uni o mapa com as texturas
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer; // Classe que renderiza o mapa
import com.badlogic.gdx.math.Vector2; // Classe utilizada para definir vetores(x,y)
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;// Classe utilizada para visualizar a box2d dos componentes do mapa
import com.badlogic.gdx.physics.box2d.World; // Classe que define o mundo do jogo
import com.badlogic.gdx.utils.viewport.FitViewport;// Classe utilizada para manter a porporção no mundo
import com.badlogic.gdx.utils.viewport.Viewport;// Classe que gerencia a camera e determina como as coordenadas do mundo
import com.goaway.game.GoAway;// Classe Principal
import com.goaway.game.Scenes.Hud; // Classe de Hud
import com.goaway.game.Sprites.Enemy; // Classe dos inimigos
import com.goaway.game.Sprites.Player; // Classe do Player
import com.goaway.game.Sprites.Player.State; // Enum dos estados do player
import com.goaway.game.Tools.B2WorldCreator; // Classe que cria/organiza o mundo
import com.goaway.game.Tools.WorldContactListener; // Classe que reage quando a contato utilizada em todas as ocasiões de contato


public class PlayScreen implements Screen{
	// Instanciações
    private GoAway game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;
    private Player player;
    private TextureAtlas atlas;
    private TextureAtlas atlasEnemy;	

    public PlayScreen(GoAway game){
    	atlas = new TextureAtlas("player.txt"); // classe que recebe e interpreta o txt do player
    	atlasEnemy = new TextureAtlas("rat.txt");// classe que recebe e interpreta o txt do rato
        this.game = game;// Seta o jogo
        gamecam = new OrthographicCamera(); // Define a camera
        gamePort = new FitViewport(GoAway.V_WIDTH / GoAway.PPM, GoAway.V_HEIGHT / GoAway.PPM, gamecam); // Seta o campo de visao do player
        hud = new Hud(game.batch); // Seta o Hud
        maploader = new TmxMapLoader(); // Seta que renderizara o mapa
        map = maploader.load("map.tmx"); // Recebe o txt do mapa
        renderer = new OrthogonalTiledMapRenderer(map, 1 / GoAway.PPM); // Renderiza o mapa de forma orthografica na proporção pre definida
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);// Define a posição inicial da camera 
        world = new World(new Vector2(0 , -10 ), true);// Define a gravidade
//        b2dr = new Box2DDebugRenderer(); // Habilita o debuger
        
        creator = new B2WorldCreator(this); // Gera o mundo
        
        //Player
        player = new Player(this); // Define o player
        
        world.setContactListener(new WorldContactListener()); // Inicia a escuta de colisoes
        
    }
 	
    public TextureAtlas getAtlas() { // Pega a textura
    	return atlas;
    }
    
    public TextureAtlas getAtlasEnemy() {// Pega a textura do inimigo
    	return atlasEnemy;
    }
    
    @Override
    public void show() {// Metedo executado quando esta se torna a tela atual


    }

    public void handleInput(float dt){// Metodo que define a velocidade e ações do player
       if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.currentState != State.JUMPING) {
    	   player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);
       }
       if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
    	   player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
       }
       if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
    	   player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
       }
     

    }

    public void update(float dt){// Metodo que realiza operações a cada 1s
        //handle user input first
        handleInput(dt);//Executa o metodo de ação do jogador
        
        world.step(1/60f, 6, 2);// Define o tempo, fps e velocidades do jogo
        
        player.update(dt);//Atualiza o player
        for(Enemy enemy : creator.getRats())// Atualiza os inimigos
        	enemy.update(dt);
        hud.update(dt);// Atualiza o Hud
        
        if(player.currentState != Player.State.DEAD) // Centraliza a camera no player ja morto
        	gamecam.position.x = player.b2body.getPosition().x;
        
        gamecam.position.x = player.b2body.getPosition().x;// Centraliza a camera no player eixo x
        gamecam.position.y = player.b2body.getPosition().y;// Centraliza a camera no playe eixo y

        
        gamecam.update();// Atualiza a camera acompanhando o movimento do player
        
        renderer.setView(gamecam);// Renderiza apenas o que a camera ve
      
    }

    @Override
    public void render(float delta) {
    	//separa a lógica de atualização da renderização
        update(delta);

        //Limpa a tela do jogo com Preto
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();//renderiza o mapa de jogo

     
//        b2dr.render(world, gamecam.combined);//renderiza o Box2DDebugLines

        game.batch.setProjectionMatrix(gamecam.combined);// Define a matriz de projeção a ser usada por este Lote.
        game.batch.begin();//Defina uma matriz de planejamento para ser usada por este Lote.
        player.draw(game.batch);// Desenha a matriz
        for(Enemy enemy : creator.getRats())//Renderiza os inimigos
        	enemy.draw(game.batch);
        game.batch.end();// Finaliza o render
        
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);//Define a matriz de projeção da camera junto com o hud
        hud.stage.draw();// Desenha a matriz
        if(gameOver()) {// Quando gameOver troca a tela
        	game.setScreen(new GameOverScreen(game));
        }
      
        
    }
    
    public boolean gameOver() { //Verifica se o player morreu
    	if(player.currentState == Player.State.DEAD && player.getStateTimer() > 1) {
    		return true;
    	}
    	return false;
    }
    
    @Override
    public void resize(int width, int height) {// Metodo utilizado para dar forma a tela
        gamePort.update(width,height);//atualizou a janela de visualização do jogo
    }
    public TiledMap getMap() { // Pega o mapa
    	return map;
    }
    public World getWorld() {  // Pega o mundo
    	return world;
    }
    
    
    @Override
    public void pause() {// Metodo executado quando pausada a tela

    }

    @Override
    public void resume() {// Metodo executado quando des-pausada a tela

    }

    @Override
    public void hide() {// Metodo executado quando a tela é ocultada

    }

    @Override
    public void dispose() {// Metodo executado quando a tela não precisa mais ser usada
    	map.dispose();
    	renderer.dispose();
    	world.dispose();
    	b2dr.dispose();
    	hud.dispose();
    }
}