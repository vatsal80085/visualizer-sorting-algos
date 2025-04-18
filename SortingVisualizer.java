import javax.swing.*;
import java.awt.*;

public class SortingVisualizer extends JFrame {
    private SortPanel sortPanel;
    private JSlider speedSlider;
    private JButton pauseButton;
    private volatile boolean isPaused = false;

    public SortingVisualizer() {
        setTitle("Sorting Visualizer");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        sortPanel = new SortPanel(this);
        add(sortPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.BLACK);
        controlPanel.setLayout(new FlowLayout());

        JButton resetBtn = new JButton("Reset");
        JButton bubbleBtn = new JButton("Bubble Sort");
        JButton selectionBtn = new JButton("Selection Sort");
        JButton insertionBtn = new JButton("Insertion Sort");
        JButton mergeBtn = new JButton("Merge Sort");
        JButton quickBtn = new JButton("Quick Sort");
        pauseButton = new JButton("Pause");

        styleButton(resetBtn);
        styleButton(bubbleBtn);
        styleButton(selectionBtn);
        styleButton(insertionBtn);
        styleButton(mergeBtn);
        styleButton(quickBtn);
        styleButton(pauseButton);

        speedSlider = new JSlider(1, 100, 30);
        speedSlider.setBackground(Color.BLACK);
        speedSlider.setForeground(Color.GREEN);
        speedSlider.setMajorTickSpacing(25);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);

        controlPanel.add(resetBtn);
        controlPanel.add(bubbleBtn);
        controlPanel.add(selectionBtn);
        controlPanel.add(insertionBtn);
        controlPanel.add(mergeBtn);
        controlPanel.add(quickBtn);
        controlPanel.add(pauseButton);
        controlPanel.add(new JLabel("Speed:", JLabel.RIGHT));
        controlPanel.add(speedSlider);

        add(controlPanel, BorderLayout.SOUTH);

        // Listeners
        resetBtn.addActionListener(e -> sortPanel.resetArray());
        bubbleBtn.addActionListener(e -> startSort(() -> sortPanel.bubbleSort()));
        selectionBtn.addActionListener(e -> startSort(() -> sortPanel.selectionSort()));
        insertionBtn.addActionListener(e -> startSort(() -> sortPanel.insertionSort()));
        mergeBtn.addActionListener(e -> startSort(() -> sortPanel.mergeSort()));
        quickBtn.addActionListener(e -> startSort(() -> sortPanel.quickSort()));

        pauseButton.addActionListener(e -> togglePause());
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.GREEN);
        button.setFocusPainted(false);
    }

    private void startSort(Runnable sortMethod) {
        new Thread(() -> {
            sortPanel.resetArray();
            sortMethod.run();
        }).start();
    }

    private void togglePause() {
        isPaused = !isPaused;
        pauseButton.setText(isPaused ? "Resume" : "Pause");
    }

    public int getSpeed() {
        return speedSlider.getValue();
    }

    public boolean isPaused() {
        return isPaused;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SortingVisualizer().setVisible(true));
    }
}
