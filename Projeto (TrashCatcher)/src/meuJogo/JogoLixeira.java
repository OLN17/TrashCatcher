package meuJogo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class JogoLixeira extends JPanel implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int LARGURA_TELA = 800;
    static final int ALTURA_TELA = 600;
    private static final int DELAY = 10;

    private Lixeira lixeira;
    private List<Lixo> lixos;
    private int vidas;
    static int pontuacao;
    private boolean gameOver;
    private Image planoDeFundo;


    public JogoLixeira() {
        setFocusable(true);
        setDoubleBuffered(true);
        addKeyListener(new TecladoAdapter());

        lixeira = new Lixeira();
        lixos = new ArrayList<>();
        vidas = 3;
        pontuacao = 0;
        gameOver = false;
         
        planoDeFundo = new ImageIcon(getClass().getResource("Background.png")).getImage();


        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            atualizar();
            verificarColisao();
        }
        repaint();
    }

    private void atualizar() {
        lixeira.mover();
        gerarLixo();

        for (int i = 0; i < lixos.size(); i++) {
            Lixo lixo = lixos.get(i);
            lixo.queda();

            if (lixo.getY() > ALTURA_TELA) {
                lixos.remove(lixo);
                i--;
                vidas--;

                if (vidas == 0) {
                    gameOver = true;
                }
            }
        }
    }

    private void gerarLixo() {
        if (Math.random() < 0.02) {
            int x = (int) (Math.random() * (LARGURA_TELA - 30));
            int velocidade = (int) (Math.random() * 3) + 1;
            TipoLixo tipo = obterTipoAleatorio();  // Adicione esta linha para obter um tipo de lixo aleatório
            Lixo lixo = new Lixo(x, 0, velocidade, tipo);  // Adicione o argumento do tipo de lixo
            lixos.add(lixo);
        }
    }

    private TipoLixo obterTipoAleatorio() {
        Random random = new Random();
        int index = random.nextInt(TipoLixo.values().length);
        return TipoLixo.values()[index];
    }

    private void verificarColisao() {
        Rectangle retanguloLixeira = new Rectangle(lixeira.getX(), lixeira.getY(), lixeira.getLargura(), lixeira.getAltura());

        for (int i = 0; i < lixos.size(); i++) {
            Lixo lixo = lixos.get(i);
            Rectangle retanguloLixo = lixo.getBounds();

            if (retanguloLixeira.intersects(retanguloLixo)) {
                lixos.remove(lixo);
                i--;
                pontuacao += 10;
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
    	g.drawImage(planoDeFundo, 0, 0, getWidth(), getHeight(), this);

        Graphics2D g2d = (Graphics2D) g;

        lixeira.desenhar(g2d);

        for (Lixo lixo : lixos) {
            lixo.desenhar(g2d);
        }

        if (gameOver) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            g2d.drawString("Game Over", LARGURA_TELA / 2 - 80, ALTURA_TELA / 2 - 15);

            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("Pontuação: " + pontuacao, LARGURA_TELA / 2 - 75, ALTURA_TELA / 2 + 15);

            g2d.drawString("Pressione R para reiniciar a fase", LARGURA_TELA / 2 - 130, ALTURA_TELA / 2 + 50);
            g2d.drawString("Pressione ESC para fechar o jogo", LARGURA_TELA / 2 - 130, ALTURA_TELA / 2 + 75);
        } else {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("Pontuação: " + pontuacao, 30, 50);

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("Vidas: " + vidas, 30, 100);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    private class TecladoAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (gameOver) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    reiniciarFase();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    encerrarJogo();
                }
            } else {
                lixeira.keyPressed(e);
            }
            
            
        }

        public void keyReleased(KeyEvent e) {
            lixeira.keyReleased(e);
        }
    }

    private void reiniciarFase() {
        lixeira = new Lixeira();
        lixos.clear();
        vidas = 3;
        pontuacao = 0;
        gameOver = false;
    }

    private void encerrarJogo() {
        System.exit(0);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Trash Catcher");
        JogoLixeira jogo = new JogoLixeira();
        frame.add(jogo);
        frame.setSize(LARGURA_TELA, ALTURA_TELA);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        
    }
}