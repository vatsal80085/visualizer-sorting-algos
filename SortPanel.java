import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SortPanel extends JPanel {
    private int[] array;
    private static final int NUM_BARS = 40;
    private static final Color DARK_GREEN = new Color(0, 100, 0);
    private static final Color LIGHT_GREEN = new Color(0, 255, 70);
    private static final Color HIGHLIGHT = new Color(144, 238, 144);
    private int comparingIndex = -1, swappingIndex = -1;

    private SortingVisualizer visualizer;

    public SortPanel(SortingVisualizer visualizer) {
        this.visualizer = visualizer;
        resetArray();
    }

    public void resetArray() {
        array = new int[NUM_BARS];
        Random rand = new Random();
        for (int i = 0; i < NUM_BARS; i++) {
            array[i] = rand.nextInt(400) + 50;
        }
        comparingIndex = -1;
        swappingIndex = -1;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);

        int width = getWidth();
        int height = getHeight();

        int barWidth = width / (NUM_BARS * 2);
        int gap = barWidth;

        for (int i = 0; i < array.length; i++) {
            int x = i * (barWidth + gap);
            int barHeight = array[i];
            int y = height - barHeight;

            Graphics2D g2d = (Graphics2D) g;
            if (i == comparingIndex)
                g2d.setColor(HIGHLIGHT);
            else if (i == swappingIndex)
                g2d.setColor(Color.RED);
            else
                g2d.setColor(DARK_GREEN);
            g2d.fillRect(x, y, barWidth, barHeight);

            g.setColor(LIGHT_GREEN);
            g.setFont(new Font("Consolas", Font.BOLD, 12));
            String value = String.valueOf(array[i]);
            int textWidth = g.getFontMetrics().stringWidth(value);
            g.drawString(value, x + (barWidth - textWidth) / 2, y - 5);
        }
    }

    private void sleepAndPause() {
        try {
            while (visualizer.isPaused())
                Thread.sleep(50);
            Thread.sleep(visualizer.getSpeed());
        } catch (InterruptedException ignored) {
        }
    }

    private void swap(int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
        beep();
    }

    private void beep() {
        Toolkit.getDefaultToolkit().beep();
    }

    public void bubbleSort() {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                comparingIndex = j;
                repaint();
                sleepAndPause();
                if (array[j] > array[j + 1]) {
                    swappingIndex = j + 1;
                    swap(j, j + 1);
                    repaint();
                    sleepAndPause();
                }
                swappingIndex = -1;
            }
        }
        clearHighlights();
    }

    public void selectionSort() {
        for (int i = 0; i < array.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < array.length; j++) {
                comparingIndex = j;
                repaint();
                sleepAndPause();
                if (array[j] < array[min])
                    min = j;
            }
            swappingIndex = min;
            swap(i, min);
            repaint();
            sleepAndPause();
        }
        clearHighlights();
    }

    public void insertionSort() {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                comparingIndex = j;
                array[j + 1] = array[j];
                repaint();
                sleepAndPause();
                j--;
            }
            array[j + 1] = key;
            swappingIndex = j + 1;
            repaint();
            sleepAndPause();
        }
        clearHighlights();
    }

    public void mergeSort() {
        mergeSortRecursive(0, array.length - 1);
        clearHighlights();
    }

    private void mergeSortRecursive(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortRecursive(left, mid);
            mergeSortRecursive(mid + 1, right);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            comparingIndex = i;
            swappingIndex = j;
            repaint();
            sleepAndPause();
            if (array[i] <= array[j])
                temp[k++] = array[i++];
            else
                temp[k++] = array[j++];
        }
        while (i <= mid)
            temp[k++] = array[i++];
        while (j <= right)
            temp[k++] = array[j++];
        for (int l = 0; l < temp.length; l++) {
            array[left + l] = temp[l];
            repaint();
            sleepAndPause();
        }
    }

    public void quickSort() {
        quickSortRecursive(0, array.length - 1);
        clearHighlights();
    }

    private void quickSortRecursive(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSortRecursive(low, pi - 1);
            quickSortRecursive(pi + 1, high);
        }
    }

    private int partition(int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            comparingIndex = j;
            repaint();
            sleepAndPause();
            if (array[j] <= pivot) {
                i++;
                swap(i, j);
                repaint();
                sleepAndPause();
            }
        }
        swap(i + 1, high);
        repaint();
        sleepAndPause();
        return i + 1;
    }

    private void clearHighlights() {
        comparingIndex = -1;
        swappingIndex = -1;
        repaint();
    }
}
