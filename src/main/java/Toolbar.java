import java.awt.BorderLayout;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Font;

// 该类用于构建工具栏
public class Toolbar extends JPanel {
    // 单例模式
    private static Toolbar tb;
    static final long serialVersionUID = 12345;

    // 文本输入文本框
    private JTextField jtf1 = new JTextField("Input Content Here", 20);
    // “前景色”单选框
    JRadioButton fore;
    // 字体选择器
    JComboBox<String> fontChooser = new JComboBox<>();
    // 字号选择器
    JComboBox<Integer> sizeChooser = new JComboBox<>();

    private Toolbar() {
        // 创建三个面板分别用于三行
        JPanel firstRowPanel = new JPanel();
        JPanel secondRowPanel = new JPanel();
        JPanel thirdRowPanel = new JPanel();

        // 设置Toolbar的布局为BorderLayout
        this.setLayout(new BorderLayout());

        // 获取事件监听器实例
        EventListener el = EventListener.getInstance();

        // 关于PPT的按钮数组
        String[] pptArray = { "新建ppt", "保存ppt", "保存当前页", "打开ppt", "放映ppt", "生成.pptx文件"};

        // 第一行：添加PPT按钮并添加按钮点击时间监听
        for (String item : pptArray) {
            JButton tmp = new JButton(item); // 创建按钮
            tmp.addActionListener(el); // 给按钮添加事件监听
            firstRowPanel.add(tmp); // 将按钮添加到第一行面板
        }

        // 创建工具按钮数组
        String[] shapeArray = { "铅笔", "直线", "矩形", "圆", "文本", "橡皮擦", "帮助" };

        // 第二行：添加所有工具按钮并添加按钮点击事件监听
        for (String item : shapeArray) {
            JButton tmp = new JButton(item); // 创建按钮
            tmp.addActionListener(el); // 给按钮添加事件监听
            secondRowPanel.add(tmp); // 将按钮添加到第二行面板
        }

        // 第二行：添加颜色选择器
        secondRowPanel.add(new Colorlist()); // 添加颜色选择组件

        // 前景色与背景色单选按钮
        ButtonGroup bg = new ButtonGroup();
        fore = new JRadioButton("前景色", true); // 默认选中前景色
        JRadioButton back = new JRadioButton("背景色");
        bg.add(fore);
        bg.add(back);
        secondRowPanel.add(fore); // 添加前景色单选按钮
        secondRowPanel.add(back); // 添加背景色单选按钮

        // 第三行：添加其他工具（线条粗细、字体、字号、文本框）
        thirdRowPanel.add(new Linewidth()); // 添加线条粗细选择器
        setComboBox(Utils.getSystemFonts()); // 获取系统字体并设置到字体选择器
        thirdRowPanel.add(fontChooser); // 添加字体选择器
        for (int i = 9; i <= 72; i++) {
            sizeChooser.addItem(Integer.valueOf(i)); // 将字号选项（9到72）加入到字号选择器中
        }
        sizeChooser.setSelectedIndex(7); // 默认选中字号为 16
        thirdRowPanel.add(sizeChooser); // 添加字号选择器
        thirdRowPanel.add(jtf1); // 添加文本框

        // 最后将三个面板分别添加到Toolbar的布局中
        this.add(firstRowPanel, BorderLayout.NORTH); // 将第一行面板添加到工具栏的顶部
        this.add(secondRowPanel, BorderLayout.CENTER); // 将第二行面板添加到工具栏的中间
        this.add(thirdRowPanel, BorderLayout.SOUTH); // 将第三行面板添加到工具栏的底部
    }



    // 获取实例的静态方法
    public static Toolbar getInstance() {
        if (tb == null) {
            tb = new Toolbar();
        }
        return tb;
    }

    // 获取文本
    public String getTextString() {
        return this.jtf1.getText();
    }

    // 当前选中的是否是前景色
    public boolean isForebackgroundSelected() {
        return this.fore.isSelected();
    }

    public void setComboBox(List<String> list) {
        for (var item : list) {
            this.fontChooser.addItem(item);
        }
    }

    public Font getSelectedFont() {
        return Utils.map.get(this.fontChooser.getSelectedItem());
    }

    public int getSelectedSize() {
        return (Integer) this.sizeChooser.getSelectedItem();
    }
}
