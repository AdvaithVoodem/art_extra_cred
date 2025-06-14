import java.awt.BorderLayout;
import javax.swing.*;

public class Main {
    public static void main(String[] args){
        JFrame frame = new JFrame("Perlin Noise Terrain Map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        JButton regenerateButton = new JButton("Regenerate");
        Panel terrainPanel = new Panel(200, 200);
        regenerateButton.addActionListener(e -> terrainPanel.regenerate());
        frame.setLayout(new BorderLayout());
        frame.add(terrainPanel, BorderLayout.CENTER);
        frame.add(regenerateButton, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}