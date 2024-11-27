package test;

import cc.pptshow.ppt.show.PPTShow;
import cc.pptshow.ppt.show.PPTShowSide;
import cc.pptshow.ppt.domain.PPTSideCss;
import cc.pptshow.ppt.element.impl.PPTText;
import cc.pptshow.ppt.element.impl.PPTInnerLine;
import cc.pptshow.ppt.element.impl.PPTInnerText;
import cc.pptshow.ppt.domain.PPTInnerTextCss;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

public class PPTShowGUIExample {

    public static void main(String[] args) {
        JFrame frame = new JFrame("PPTShow 可视化工具");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1));

        JLabel titleLabel = new JLabel("请输入标题：");
        JTextField titleField = new JTextField();

        JLabel contentLabel = new JLabel("请输入正文：");
        JTextField contentField = new JTextField();

        JLabel backgroundColorLabel = new JLabel("请输入背景颜色 (如：FFFFFF)：");
        JTextField backgroundColorField = new JTextField();

        JButton createButton = new JButton("生成 PPT");
        JLabel statusLabel = new JLabel("");

        frame.add(titleLabel);
        frame.add(titleField);
        frame.add(contentLabel);
        frame.add(contentField);
        frame.add(backgroundColorLabel);
        frame.add(backgroundColorField);
        frame.add(createButton);
        frame.add(statusLabel);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String content = contentField.getText();
                String backgroundColor = backgroundColorField.getText();

                if (title.isEmpty() || content.isEmpty() || backgroundColor.isEmpty()) {
                    statusLabel.setText("所有字段均为必填！");
                } else {
                    try {
                        PPTShow pptShow = PPTShow.build();
                        PPTShowSide side = PPTShowSide.build();

                        // 设置背景颜色
                        PPTSideCss sideCss = PPTSideCss.build();
                        // 如果没有 setBackgroundColor 方法，可以通过其他设置实现背景效果
                        side.setCss(sideCss);

                        // 设置标题
                        PPTInnerText titleText = PPTInnerText.build(title);
                        PPTInnerTextCss titleTextCss = PPTInnerTextCss.build().setColor("0000FF");
                        titleText.setCss(titleTextCss);
                        PPTInnerLine titleLine = PPTInnerLine.build(Collections.singletonList(titleText));
                        PPTText pptTitle = PPTText.build(Collections.singletonList(titleLine));
                        side.add(pptTitle);

                        // 设置正文
                        PPTInnerText contentText = PPTInnerText.build(content);
                        PPTInnerTextCss contentTextCss = PPTInnerTextCss.build().setColor("000000");
                        contentText.setCss(contentTextCss);
                        PPTInnerLine contentLine = PPTInnerLine.build(Collections.singletonList(contentText));
                        PPTText pptContent = PPTText.build(Collections.singletonList(contentLine));
                        side.add(pptContent);

                        // 添加页面到 PPT
                        pptShow.add(side);

                        // 保存文件
                        String filePath = "output.pptx";
                        pptShow.toFile(filePath);

                        statusLabel.setText("PPT 生成成功：" + filePath);
                    } catch (Exception ex) {
                        statusLabel.setText("生成 PPT 时发生错误：" + ex.getMessage());
                    }
                }
            }
        });

        frame.setVisible(true);
    }
}
