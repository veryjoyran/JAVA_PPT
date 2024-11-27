import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.util.Timer;

public class ImageSlideshowPanel extends JPanel {
    private File[] imageFiles;
    private int currentIndex = 0;
    private Image currentImage;

    private Timer timer;

    public ImageSlideshowPanel(String directoryPath, int delayInSeconds, JFrame frame) {
        // 获取目录中的所有图片文件
        File dir = new File(directoryPath);
        if (dir.isDirectory()) {
            imageFiles = dir.listFiles((dir1, name) -> {
                return name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpeg");
            });

            // 按照文件名排序
            Arrays.sort(imageFiles, Comparator.comparing(File::getName));


            // 创建一个定时器，按照设定的时间间隔切换图片
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // 切换到下一张图片
                    nextImage();

                    // 如果已播放完所有图片，停止定时器并延迟关闭窗口
                    if (currentIndex == imageFiles.length) {
                        // 延迟 2 秒后关闭窗口，确保最后一张图片有足够的时间显示
                        timer.cancel(); // 停止定时器
                        SwingUtilities.invokeLater(() -> {
                            try {
                                Thread.sleep(2000); // 等待 2 秒
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            closeWindow(frame); // 关闭窗口
                        });
                    }
                }
            }, 0, delayInSeconds * 1000); // delayInSeconds 毫秒切换一次图片
        }
    }

    // 加载指定索引的图片
    private void loadImage(int index) {
        try {
            currentImage = ImageIO.read(imageFiles[index]);
            repaint();  // 重绘面板以显示当前图片
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 切换到下一张图片
    private void nextImage() {
        loadImage(currentIndex);
        currentIndex = currentIndex + 1;

    }

    // 关闭窗口
    private void closeWindow(JFrame frame) {
        frame.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentImage != null) {
            int x = (getWidth() - currentImage.getWidth(this)) / 2;
            int y = (getHeight() - currentImage.getHeight(this)) / 2;
            g.drawImage(currentImage, x, y, this);
        }
    }
}
