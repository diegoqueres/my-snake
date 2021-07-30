package net.diegoqueres.mysnake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.diegoqueres.mysnake.enums.Direcao;

import static net.diegoqueres.mysnake.Constants.IDX_CABECA_COBRA;
import static net.diegoqueres.mysnake.Constants.TEMPO_MOVER_PADRAO;

public class GameScreen implements Screen {

    private Game game;

    private Viewport viewport;
    private SpriteBatch batch;

    private Texture texCorpo;
    private Texture texFundo;
    private Texture texPonto;

    private boolean[][] corpo;
    private Array<Vector2> partes;
    private Direcao direcao;

    private float tempoParaMover;


    public GameScreen(Game game) {
        this.game = game;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(100, 100);
        viewport.apply();

        gerarTextura();
        init();
    }

    @Override
    public void render(float delta) {
        update(delta);

        limparTela();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        batch.draw(texFundo, 0, 0, 100, 100);
        for (Vector2 parte : partes)
            batch.draw(texCorpo, parte.x*5, parte.y*5, 5, 5);

        batch.end();
    }

    private void update(float delta) {
        tempoParaMover -= delta;
        if (tempoParaMover <= 0) {
            tempoParaMover = TEMPO_MOVER_PADRAO;
            Gdx.app.log("Log", "move");

            int x1, y1, x2, y2;

            x1 = (int) partes.get(IDX_CABECA_COBRA).x;
            y1 = (int) partes.get(IDX_CABECA_COBRA).y;
            corpo[x1][y1] = false;

            x2 = x1;
            y2 = y1;

            switch (direcao) {
                case CIMA:
                    y1++;
                    break;
                case DIREITA:
                    x1++;
                    break;
                case BAIXO:
                    y1--;
                    break;
                case ESQUERDA:
                    x1--;
                    break;
            }

            if (isMovimentacaoInvalida(x1, y1)) {
                // perdeu
                return;
            }

            partes.get(IDX_CABECA_COBRA).set(x1, y1);
            corpo[x1][y1] = true;

            for (int i = IDX_CABECA_COBRA + 1; i < partes.size; i++) {
                x1 = (int) partes.get(i).x;
                y1 = (int) partes.get(i).y;
                corpo[x1][y1] = false;

                partes.get(i).set(x2, y2);
                corpo[x2][y2] = true;

                x2 = x1;
                y2 = y1;
            }

        }

    }

    private boolean isMovimentacaoInvalida(int x, int y) {
        return (x < 0 || y < 0 || x > 19 || y > 19 || corpo[x][y]);
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

    private void init() {
        corpo = new boolean[20][20];

        partes = new Array<Vector2>();
        partes.add(new Vector2(6, 5));
        corpo[6][5] = true;
        partes.add(new Vector2(5, 5));
        corpo[5][5] = true;

        direcao = Direcao.DIREITA;

        tempoParaMover = TEMPO_MOVER_PADRAO;   //a cada 0,4 segundos, a snake se movimenta.
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
