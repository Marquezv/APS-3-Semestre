package com.goaway.game.Screens;

import com.badlogic.gdx.Game; // Classe que geri o jogo
import com.badlogic.gdx.Gdx; // Classe utilizada pra fazer referencias a aplicao e inputs
import com.badlogic.gdx.Screen; // Interface utilizada para definir a classe como uma tela
import com.badlogic.gdx.graphics.Color;// Classe de cores no padrao rgb
import com.badlogic.gdx.graphics.GL20;// Interface que envolve os metodos principais do OpenGL ES 2.0
import com.badlogic.gdx.graphics.OrthographicCamera; // Classe que define a camera como orthografica
import com.badlogic.gdx.graphics.g2d.BitmapFont;// Classe que renderiza as fontes
import com.badlogic.gdx.scenes.scene2d.Stage;// Classe que manipula a viewport e implementa input events
import com.badlogic.gdx.scenes.scene2d.ui.Label; // Classe que renderiza as labels
import com.badlogic.gdx.scenes.scene2d.ui.Table; // Classe que formata as labels em tabela
import com.badlogic.gdx.utils.viewport.FitViewport; // Classe utilizada para manter a porporção no mundo
import com.badlogic.gdx.utils.viewport.Viewport; // Classe que gerencia a camera e determina como as coordenadas do mundo
import com.goaway.game.GoAway;// Classe Principal

public class GameOverScreen implements Screen{
	// Instanciações
	private Viewport viewport;
    private Stage stage;

    private Game game;

    public GameOverScreen(Game game){
        this.game = game;
        viewport = new FitViewport(GoAway.V_WIDTH, GoAway.V_HEIGHT, new OrthographicCamera());// Configura a camera do jogo
        stage = new Stage(viewport, ((GoAway) game).batch);  // Recebe a viewport e define como um metodo

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE); // Define a cor e estilo da font

        Table table = new Table();
        table.center();// Localização da tabela
        table.setFillParent(true);// Aumenta de acordo com a tela

        Label gameOverLabel = new Label("GAME OVER", font); // Define o game over na tela
        table.add(gameOverLabel).expandX(); // Centraliza
        table.row();// Adiciona a linha/margem

        stage.addActor(table);// Adiciona a table na tela
    }
	@Override
	public void show() { // Metodo executado quando a tela inicia
		// TODO Auto-generated method stub
		
	}

	@Override
	 public void render(float delta) { // Metodo executado para renderização
	    Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    stage.draw();
	}

	@Override 
	public void resize(int width, int height) { // Metodo utilizado para dar forma a tela
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() { // Metodo executado quando pausada a tela
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {// Metodo executado quando des-pausada a tela
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {// Metodo executado quando a tela é ocultada
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {// Metodo executado quando a tela não precisa mais ser usada
		// TODO Auto-generated method stub
		stage.dispose();
	}
	
}
