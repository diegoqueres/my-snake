package net.diegoqueres.mysnake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

    private Game game;

    private Viewport viewport;
    private SpriteBatch batch;

    private Texture texCorpo;
    private Texture texFundo;
    private Texture texPonto;

    public GameScreen(Game game) {
        this.game = game;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(100, 100);
        viewport.apply();

        gerarTextura();
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(viewport.getCamera().combined);
        limparTela();

        batch.begin();
        batch.draw(texFundo, 0, 0, 100, 100);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private void limparTela() {
        Gdx.gl.glClearColor(.29f, .894f, .373f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void gerarTextura() {
        Pixmap pixmap = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        pixmap.setColor(1f, 1f, 1f, 1f);
        pixmap.fillRectangle(0, 0, 64, 64);
        texCorpo = new Texture(pixmap);
        pixmap.dispose();

        Pixmap pixmap2 = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        pixmap2.setColor(.29f, .784f, .373f, .5f);
        pixmap2.fillRectangle(0, 0, 64, 64);
        texFundo = new Texture(pixmap2);
        pixmap2.dispose();

        Pixmap pixmap3 = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        pixmap.setColor(1f, 1f, 1f, 1f);
        pixmap3.fillCircle(32, 32, 32);
        texPonto = new Texture(pixmap3);
        pixmap3.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
