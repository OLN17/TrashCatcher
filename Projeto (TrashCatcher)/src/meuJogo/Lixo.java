package meuJogo;

import java.awt.*;
import javax.swing.ImageIcon;

public class Lixo {

    private int x;
    private int y;
    private int largura;
    private int altura;
    private int velocidade;
    private TipoLixo tipo;

    public Lixo(int x, int y, int velocidade, TipoLixo tipo) {
        this.x = x;
        this.y = y;
        this.velocidade = velocidade;
        this.tipo = tipo;
        largura = 30;
        altura = 30;
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

    public void desenhar(Graphics2D g2d) {
        ImageIcon imgLixo = null;

        // Carrega a imagem correspondente ao tipo de lixo
        switch (tipo) {
            case GARRAFA:
                imgLixo = new ImageIcon(getClass().getResource("garrafa.png"));
                break;
            case PAPEL:
                imgLixo = new ImageIcon(getClass().getResource("salgadis.png"));
                break;
            case LATA:
                imgLixo = new ImageIcon(getClass().getResource("lata.png"));
                break;
        }

        // Verifica se a imagem foi carregada corretamente
        if (imgLixo != null) {
            Image img = imgLixo.getImage();
            g2d.drawImage(img, x, y, largura, altura, null);
        }
    }

    public void queda() {
        y += velocidade;
        if (y >= JogoLixeira.ALTURA_TELA) {
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura);
    }
}


enum TipoLixo {
    GARRAFA,
    PAPEL,
    LATA
}