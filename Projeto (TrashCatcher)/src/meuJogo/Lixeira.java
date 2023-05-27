package meuJogo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;

public class Lixeira {

    private int x;
    private int y;
    private int largura;
    private int altura;
    private int velocidade;
    @SuppressWarnings("unused")
	private int vidas = 3;
    private Rectangle hitbox;
    private boolean esquerda;
    private boolean direita;

    public Lixeira() {
        x = 350;
        y = 500;
        largura = 100;
        altura = 20;
        velocidade = 20;
        esquerda = false;
        direita = false;
        hitbox = new Rectangle(350, 500, largura, altura);
    }

    public void reiniciarFase() {
        vidas = 3;
        JogoLixeira.pontuacao = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public void mover() {
        if (esquerda && x > 0) {
            x -= velocidade;
        }
        if (direita && x < JogoLixeira.LARGURA_TELA - largura) {
            x += velocidade;
        }
        atualizarHitbox();
    }

    private void atualizarHitbox() {
        hitbox.setBounds(350, 500, largura, altura);
    }

    public void keyPressed(KeyEvent e) {
        int codigo = e.getKeyCode();

        if (codigo == KeyEvent.VK_LEFT) {
            esquerda = true;
        }
        if (codigo == KeyEvent.VK_RIGHT) {
            direita = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int codigo = e.getKeyCode();

        if (codigo == KeyEvent.VK_LEFT) {
            esquerda = false;
        }
        if (codigo == KeyEvent.VK_RIGHT) {
            direita = false;
        }
    }

    public void desenhar(Graphics2D g2d) {
        ImageIcon imgLixeira = new ImageIcon(getClass().getResource("lixeira.png"));
        Image img = imgLixeira.getImage();
        int larguraImg = imgLixeira.getIconWidth();
        int alturaImg = imgLixeira.getIconHeight();
        g2d.drawImage(img, x, y, larguraImg, alturaImg, null);

    }

    public void colide(Lixo lixo) {
        if (hitbox.intersects(lixo.getBounds())) {
            JogoLixeira.pontuacao += 10;
        } else if (lixo.getY() >= JogoLixeira.ALTURA_TELA - lixo.getAltura()) {
            vidas--;
        }
    }

    public Rectangle getBounds() {
        return hitbox;
    }
}