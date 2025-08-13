package radarSubmarine;

// Importações necessárias para construção da interface gráfica e manipulação de eventos
import javax.swing.*;              // Componentes Swing para GUI (JFrame, JPanel, Timer, etc.)
import java.awt.*;                 // Ferramentas gráficas de baixo nível (Graphics, Color, etc.)
import java.awt.event.ActionEvent; // Representa eventos de ação (ex: clique de botão, timer)
import java.awt.event.ActionListener; // Interface para tratamento de eventos de ação

/**
 * Classe responsável por simular um radar de submarino com animação gráfica.
 * Estende JPanel para renderização personalizada e implementa ActionListener
 * para controle da animação via Timer.
 */
public class radar_Submarine_sonar extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    // Ângulo atual do feixe do radar (em radianos)
    private double anguloAtual = 0;

    // Timer responsável por atualizar a animação periodicamente
    private Timer timerDeAnimacao;

    /**
     * Construtor da classe. Inicializa o painel com fundo preto e inicia o Timer.
     */
    public radar_Submarine_sonar() {
        setBackground(Color.BLACK);                      // Define o fundo do painel como preto
        timerDeAnimacao = new Timer(50, this);           // Timer com intervalo de 50ms
        timerDeAnimacao.start();                         // Inicia a animação
    }

    /**
     * Método sobrescrito do JPanel. Responsável por desenhar o radar na tela.
     * @param g Objeto Graphics utilizado para renderização.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);                         // Limpa o painel antes de redesenhar
        Graphics2D g2d = (Graphics2D) g;                 // Conversão para Graphics2D para recursos avançados

        int largura = getWidth();
        int altura = getHeight();
        int raio = Math.min(largura, altura) / 2 - 20;   // Define o raio do radar com margem
        int centroX = largura / 2;
        int centroY = altura / 2;

        g2d.setColor(Color.GREEN);                       // Cor padrão do radar
        g2d.setStroke(new BasicStroke(2));               // Define espessura das linhas

        // Desenha os círculos concêntricos (anéis do radar)
        for (int i = 0; i < 4; i++) {
            int raioDoAnel = raio - (i * raio / 4);
            g2d.drawOval(centroX - raioDoAnel, centroY - raioDoAnel, 2 * raioDoAnel, 2 * raioDoAnel);
        }

        // Desenha linhas radiais a cada 45 graus
        for (int i = 0; i < 360; i += 45) {
            double rad = Math.toRadians(i);
            int x = centroX + (int) (raio * Math.cos(rad));
            int y = centroY + (int) (raio * Math.sin(rad));
            g2d.drawLine(centroX, centroY, x, y);
        }

        // Desenha o feixe rotativo (linha semi-transparente)
        g2d.setColor(new Color(0, 255, 0, 100));          // Verde com transparência
        int feixeX = centroX + (int) (raio * Math.cos(anguloAtual));
        int feixeY = centroY + (int) (raio * Math.sin(anguloAtual));
        g2d.drawLine(centroX, centroY, feixeX, feixeY);
    }

    /**
     * Método chamado automaticamente a cada tick do Timer.
     * Atualiza o ângulo do feixe e solicita a repintura do painel.
     * @param e Evento de ação disparado pelo Timer.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        anguloAtual += Math.toRadians(2);                // Incrementa o ângulo em 2 graus
        if (anguloAtual >= 2 * Math.PI) {                // Reinicia após completar 360°
            anguloAtual -= 2 * Math.PI;
        }
        repaint();                                       // Solicita a atualização da tela
    }

    /**
     * Método principal. Inicializa a aplicação e exibe a janela com o radar.
     * Utiliza SwingUtilities.invokeLater para garantir execução na thread de GUI.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame janela = new JFrame("Radar do Submarino");
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            janela.setExtendedState(JFrame.MAXIMIZED_BOTH); // Tela cheia
            janela.setUndecorated(true);                    // Remove bordas da janela

            janela.add(new radar_Submarine_sonar()); // 
            janela.setVisible(true);
        });
    }
}
