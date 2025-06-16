import java.awt.*;
import javax.swing.*;

public final class Panel extends JPanel{
    private final int cols, rows;
    private final float[][] terrain;
    //private int currRow = 0;
    private Timer timer;
    private int diagonal = 0;
    
    public Panel(int cols, int rows){
        this.cols = cols;
        this.rows = rows;
        terrain = new float[cols][rows];

        generateTerrain();
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        float alpha = 0.7f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        super.paintComponent(g);
        int cellW = getWidth() / cols;
        int cellH = getHeight() / rows;

        // for(int i = 0; i < cols; i++){
        //     for (int j = 0; j <= currRow && j < rows; j++){
        //         g.setColor(getColor(terrain[i][j]));
        //         g.fillOval(i * cellW, j * cellH, cellW, cellH);
        //     }
        // }

        for (int d = 0; d <= diagonal; d++) {
            for (int i = cols - 1; i >= 0; i--) {
                int j = d - i;
                if (j >= 0 && j < rows) {
                    g.setColor(getColor(terrain[i][j]));
                    g.fillOval(i * cellW, j * cellH, cellW, cellH);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 800); 
    }

    private Color getColor(float x){
        if(x < 0.1) return Color.RED;
        else if(x < 0.6) return Color.ORANGE;
        else if(x < 0.8) return new Color(139, 69, 19);
        else return Color.YELLOW;
    }

    public void generateTerrain(){
        Noise gen = new Noise();
        float scale = 0.1f;
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                terrain[i][j] = gen.perlin(i*scale, j*scale, 4, 0.5f);
            }
        }

        //currRow = 0;
        diagonal = 0;

        if(timer != null && timer.isRunning()){
            timer.stop();
        }

        // timer = new Timer(10, e -> {
        //     currRow++;
        //     repaint();
        //     if(currRow >= rows) ((Timer) e.getSource()).stop();
        // });

        timer = new Timer(10, e -> {
            diagonal++;
            repaint();
            if (diagonal > cols + rows - 2) {
                ((Timer) e.getSource()).stop();
            }
        });

        timer.start();
    }

    public void regenerate() {
        generateTerrain();
        repaint();
    }
}
