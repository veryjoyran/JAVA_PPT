import java.awt.event.*;
import javax.swing.event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.io.File;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import cc.pptshow.ppt.show.PPTShow;
import cc.pptshow.ppt.show.PPTShowSide;
import cc.pptshow.ppt.element.impl.PPTImg;
import ppt_generate.GUIPPTImage;

// 该类实现了鼠标事件、键盘事件和按钮点击事件的监听
public class EventListener extends MouseInputAdapter implements ActionListener, KeyListener, ChangeListener {
    // 使用单例模式
    private static EventListener i;
    // 点击点和落点的坐标
    private int x1, x2, y1, y2;
    // 当前选中的颜色
    private Color selectedColor;
    // 当前背景色
    private Color backgroundColor;
    // 当前使用的操作
    private String operation;
    // 当前路径
    private String currentPath;
    // 当前线条粗细
    private int width;
    // 画笔
    private Graphics pen;
    // 所有画过的图
    private final Deque<Shape> history = new LinkedList<>();
    // 保存实时按键的栈
    private final Deque<Integer> stack = new LinkedList<>();
    // 保存按鼠标时的历史状态（用于笔和橡皮擦的撤销）
    private final Deque<Shape> previous = new LinkedList<>();


    private EventListener() {
        // 默认画笔为黑色，背景色为白色，选中操作为铅笔
        selectedColor = Color.BLACK;
        backgroundColor = Color.WHITE;
        operation = "铅笔";
        currentPath = System.getProperty("user.dir") + "\\src\\main\\java\\ppt";
        width = 1;
    }

    // 获取实例的静态方法
    public static EventListener getInstance() {
        if (i == null) {
            i = new EventListener();
        }
        return i;
    }

    // 清除所有状态并重新绘制
    public void clear(boolean clearFile) {
        history.clear();
        previous.clear();
        if (clearFile) {
            // 清除之前打开的文件
            Drawboard.getInstance().clearFile();
        }
        Drawboard.getInstance().repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton instance = (JButton) e.getSource();
        // 点击的是颜色（因为颜色按钮没有文字）
        if ("".equals(e.getActionCommand())) {
            if (Toolbar.getInstance().isForebackgroundSelected()) {
                // 设置前景色
                selectedColor = instance.getBackground();
            } else {
                // 设置背景色
                backgroundColor = instance.getBackground();
                // 刷新画板
                this.setBackgroundColor();
            }
        } else {
            // 选择帮助操作时输出帮助信息并return
            if (instance.getText().equals("帮助")) {
                showHelpMessage();
                Drawboard.getInstance().requestFocus();
                return;
            }
            // ppt操作
            if (instance.getText().equals("新建ppt")) {
                File currentDir = new File(currentPath);
                String parentPath = currentDir.getParent();
                String fileName = currentDir.getName();
                if(fileName.equals("ppt")){
                    String result = showInputMessage(currentDir);
                    if(result != null)
                        currentPath = result;
                }
                else{
                    JOptionPane.showMessageDialog(null, "有已经打开的PPT，请先保存");
                }
                Drawboard.getInstance().requestFocus();
                return;
            }
            if (instance.getText().equals("保存ppt")){
                File currentDir = new File(currentPath);
                String parentPath = currentDir.getParent();
                String fileName = currentDir.getName();
                if(fileName.equals("ppt"))
                    JOptionPane.showMessageDialog(null, "没有已经打开的PPT");
                else{
                    JOptionPane.showMessageDialog(null, "PPT" + fileName + "已保存");
                    currentPath = parentPath;
                }
                Drawboard.getInstance().requestFocus();
                return;
            }
            if (instance.getText().equals("保存当前页")){
                saveSinglePPT();
                Drawboard.getInstance().requestFocus();
                return;
            }
            if (instance.getText().equals("打开ppt")){
                openPPT();
                Drawboard.getInstance().requestFocus();
                return;
            }
            if (instance.getText().equals("放映ppt")){
                File currentDir = new File(currentPath);
                String parentPath = currentDir.getParent();
                String fileName = currentDir.getName();
                if(fileName.equals("ppt"))
                    JOptionPane.showMessageDialog(null, "没有已经打开的PPT");
                else{
                    String directoryPath = currentPath; // 替换为实际的图片目录路径

                    // 设置每隔 3 秒切换图片
                    int delayInSeconds = 3;

                    // 创建并显示窗口
                    JFrame frame = new JFrame("Image Slideshow");
                    ImageSlideshowPanel slideshowPanel = new ImageSlideshowPanel(directoryPath, delayInSeconds, frame);

                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(800, 600); // 设置窗口大小
                    frame.add(slideshowPanel); // 添加面板到窗口
                    frame.setVisible(true);
                }
                Drawboard.getInstance().requestFocus();
                return;

            }
            if (instance.getText().equals("生成.pptx文件")){
                File currentDir = new File(currentPath);
                String parentPath = currentDir.getParent();
                String fileName = currentDir.getName();
                if(fileName.equals("ppt"))
                    JOptionPane.showMessageDialog(null, "没有已经打开的PPT");
                else{

                    // 新建一个PPT对象
                    PPTShow pptShow = PPTShow.build();

                    // 获取目录下的所有图片文件
                    File dir = new File(currentPath);
                    File[] imageFiles = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpeg"));

                    // 按文件名排序（如果需要）
                    if (imageFiles != null) {
                        Arrays.sort(imageFiles, (file1, file2) -> file1.getName().compareTo(file2.getName()));

                        // 遍历每个图片文件
                        for (File imageFile : imageFiles) {
                            // 新建一页PPT
                            PPTShowSide side = PPTShowSide.build();

                            // 创建GUIPPTImage对象并设置图片
                            GUIPPTImage guiPPTImage = new GUIPPTImage(imageFile.getAbsolutePath());
                            PPTImg pptImg = guiPPTImage.PPTImg_SetCss();

                            // 将图片添加到PPT页面
                            side.add(pptImg);

                            //添加背景音乐
                            side.setBackgroundMusic("src/main/java/background_music.mp3");

                            //添加自动换页
                            side.setAutoPagerTime(3000);


                            // 在PPT里面添加PPT页面
                            pptShow.add(side);
                        }
                        // 输出最终PPT文件
                        pptShow.toFile(fileName);

                        JOptionPane.showMessageDialog(null, "已经在根目录生成了" + fileName);
                    } else {
                        JOptionPane.showMessageDialog(null, "指定路径没有找到有效的文件");
                    }




                    currentPath = parentPath;
                }
                Drawboard.getInstance().requestFocus();
                return;
            }

            // 否则将操作赋值给参数
            operation = instance.getText();
        }
        // 将焦点还给绘图区域（没有焦点没有办法响应键盘事件）
        Drawboard.getInstance().requestFocus();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // 保存该个时刻的最新状态
        previous.push(history.peekLast());
        // 按下鼠标时调用的函数
        x1 = e.getX();
        y1 = e.getY();
        x2 = e.getX();
        y2 = e.getY();
        // 原地画点，为了和mouseDragged协作实现动态拖拽的效果
        if (this.operation.equals("橡皮擦")) {
            addEraser();
        } else if (this.operation.equals("文本")) {
            addText();
        } else {
            addShape();
        }
        Drawboard.getInstance().requestFocus();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        switch (this.operation) {
            case "铅笔":
                addShape();
                break;
            case "橡皮擦":
                addEraser();
                break;
            case "文本":
                revert(true);
                addText();
                break;
            default:
                revert(true);
                addShape();
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        switch (this.operation) {
            case "铅笔":
                addShape();
                x1 = x2;
                y1 = y2;
                break;
            case "橡皮擦":
                addEraser();
                x1 = x2;
                y1 = y2;
                break;
            case "文本":
                revert(true);
                addText();
                break;
            default:
                revert(true);
                addShape();
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // 有大于一个按键并且上一个按键为Ctrl
        if (stack.size() >= 1 && stack.peek() == 17) {
            switch (e.getKeyCode()) {
                case 90: // Ctrl+Z -> 撤销
                    revert(false);
                    break;
                case 83: // Ctrl+S -> 保存图片
                    Drawboard.getInstance().savePanelAsImage();
                    break;
                case 79: // Ctrl+O -> 打开图片
                    Drawboard.getInstance().loadImageToPanel();
                    break;
                case 81: // Ctrl+Q -> 清空历史
                    this.clear(true);
                    break;
                case 72: // Ctrl+H -> 弹出帮助信息
                    showHelpMessage();
                    break;
                default:
                    // 处理未定义的按键
                    break;
            }
        }
        // 将按键码压栈
        stack.push(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // 松开按键则弹栈
        stack.pop();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider jslider = (JSlider) e.getSource();
        this.width = jslider.getValue();
        // 将焦点还给绘图区域（没有焦点没有办法响应键盘事件）
        Drawboard.getInstance().requestFocus();
    }

    // 撤销有两种类型，锁定撤销和非锁定撤销
    // 锁定撤销时，起点不会被删除，在动态拖拽操作中使用
    // 非锁定撤销时，起点和边同时被删除，在手动调用的撤销操作中使用
    public void revert(boolean fixed) {
        Shape toCompare = fixed ? previous.peek() : previous.poll();
        Shape tmp;
        while ((tmp = history.peekLast()) != null) {
            if (!tmp.equals(toCompare)) {
                history.pollLast();
            } else {
                break;
            }
        }
        Drawboard.getInstance().repaint();
    }

    public Color getSelectedColor() {
        return this.selectedColor;
    }

    public String getOperation() {
        return this.operation;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public int getWidth() {
        return this.width;
    }

    public void setPen(Graphics pen) {
        this.pen = pen;
    }

    public Deque<Shape> getHistory() {
        return this.history;
    }

    private void addShape() {
        // 添加新图
        Shape tmp = new MultiShape(x1, y1, x2, y2);
        // 加入历史
        history.add(tmp);
        // 用pen将tmp画在图上
        tmp.draw(pen);
    }

    // 设置背景色
    public void setBackgroundColor() {
        Drawboard instance = Drawboard.getInstance();
        instance.setBackground(backgroundColor);
        for (var item : history) {
            if (item instanceof Eraser) {
                // 刷新历史橡皮到当前背景颜色
                item.refresh();
            }
        }
        // 重新绘制
        instance.repaint();
    }

    private void addEraser() {
        // 添加新图
        Shape tmp = new Eraser(x1, y1);
        // 加入历史
        history.add(tmp);
        // 用pen将tmp画在图上
        tmp.draw(pen);
    }

    private void addText() {
        // 添加新文本
        Shape tmp = new TextShape(x2, y2);
        // 加入历史
        history.add(tmp);
        // 用pen将tmp画在图上
        tmp.draw(pen);
    }

    private void showHelpMessage() {
        JOptionPane.showInternalMessageDialog(null, Utils.getHelpMessage(), "帮助", JOptionPane.INFORMATION_MESSAGE);
    }

    private String showInputMessage(File formerfolder) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("请输入ppt名称：");
        JTextField textField = new JTextField(20);

        // 将标签和文本框添加到面板中
        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);

        // 弹出输入对话框，获取用户输入
        int option = JOptionPane.showConfirmDialog(null, panel, "新建PPT", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // 如果点击了确定按钮
        if (option == JOptionPane.OK_OPTION) {
            String folderName = textField.getText().trim();

            if (folderName != null && !folderName.trim().isEmpty()) {
                // 创建文件夹
                // 指定要创建的文件夹路径（可以指定为当前路径，或其他路径）
                // 使用mkdir()方法创建文件夹
                File folder = new File(formerfolder, folderName);
                if (folder.mkdir()) {
                    JOptionPane.showMessageDialog(null, "PPT创建成功！路径: " + folder.getAbsolutePath());
                    return String.valueOf(folder.getAbsolutePath());
                } else {
                    JOptionPane.showMessageDialog(null, "PPT创建失败或PPT已存在。");
                    return null;
                }

            } else {
                JOptionPane.showMessageDialog(null, "PPT名称不能为空！");
            }
        }
        return null;
    }

    private void saveSinglePPT(){
        File currentDir = new File(currentPath);
        String fileName = currentDir.getName();
        if(fileName.equals("ppt"))
            JOptionPane.showMessageDialog(null, "没有已经打开的PPT");
        else{
            File folder = new File(currentPath);

            // 获取PPT中页数数据
            File[] files = folder.listFiles((dir, name) -> new File(dir, name).isFile());

            if (files.length != 0) {
                // 创建一个数组存储去除图片后缀
                String[] fileNamesWithoutExtension = new String[files.length];

                // 遍历去除后缀并存储
                for (int i = 0; i < files.length; i++) {
                    // 获取页数
                    String pptNumber = files[i].getName();
                    int dotIndex = pptNumber.lastIndexOf(".");
                    if (dotIndex != -1) {
                        fileNamesWithoutExtension[i] = pptNumber.substring(0, dotIndex);
                    } else {
                        fileNamesWithoutExtension[i] = pptNumber; // 如果没有扩展名，直接存储文件名
                    }
                }
                // 按照字典顺序排序
                Arrays.sort(fileNamesWithoutExtension);

                String number = fileNamesWithoutExtension[files.length -1];
                try {
                    // 将字符串转换为 int，并保存下一页
                    int num = Integer.parseInt(number) + 1;
                    Drawboard.getInstance().savePanelAsPPT(currentPath, String.valueOf(num));
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "未知错误");
                }
            }
            else {
                Drawboard.getInstance().savePanelAsPPT(currentPath, "1");
            }

        }
    }

    private void openPPT(){
        File currentDir = new File(currentPath);
        String parentPath = currentDir.getParent();
        String fileName = currentDir.getName();
        if(fileName.equals("ppt")){
            JFileChooser folderChooser = new JFileChooser();
            folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            // 显示打开对话框
            int result = folderChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                // 获取用户选择的PPT
                File selectedFolder = folderChooser.getSelectedFile();
                JOptionPane.showMessageDialog(null, "用户选择的PPT是: " + selectedFolder.getAbsolutePath());
                currentPath = selectedFolder.getAbsolutePath();
            } else {
                JOptionPane.showMessageDialog(null, "用户取消了操作");
            }

        }
        else{
            JOptionPane.showMessageDialog(null, "有已经打开的PPT，请先保存");
        }
    }

}
