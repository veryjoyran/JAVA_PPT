package ppt_generate;

import cc.pptshow.ppt.element.impl.PPTInnerText;
import cc.pptshow.ppt.domain.PPTInnerTextCss;

public class GUIPPTInnerText {
    private int fontsize;   //字体，默认为12
    private int spacing;   //间距，默认为0
    private String fontFamilt; //字体，默认宋体
    private String color;  //颜色，默认黑色
    private boolean italic;  //是否斜体,默认不倾斜
    private boolean bold;  //是否加粗，默认不加粗
    private String text;  //文本内容
    private String name;  //文本框名称

    public GUIPPTInnerText(String text) {
        this.text = text;
        this.fontsize = 12;
        this.spacing = 0;
        this.fontFamilt = "宋体";
        this.color = "000000";
        this.italic = false;
        this.bold = false;
        this.name = "text";
    }

    public PPTInnerText PPTInnerText_SetCss() {
        PPTInnerText pptInnerText = new PPTInnerText();

        PPTInnerTextCss css = new PPTInnerTextCss();
        css.setFontSize(this.fontsize);
        css.setSpacing(this.spacing);
        css.setFontFamily(this.fontFamilt);
        css.setColor(this.color);
        css.setItalic(this.italic);
        css.setBold(this.bold);

        pptInnerText.setCss(css);
        pptInnerText.setText(this.text);

        return pptInnerText;
    }

    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public void setFontFamilt(String fontFamilt) {
        this.fontFamilt = fontFamilt;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFontsize() {
        return this.fontsize;
    }

    public int getSpacing() {
        return this.spacing;
    }

    public String getFontFamilt() {
        return this.fontFamilt;
    }

    public String getColor() {
        return this.color;
    }

    public boolean getItalic() {
        return this.italic;
    }

    public boolean getBold() {
        return this.bold;
    }

    public String getText() {
        return this.text;
    }

    public String getName() {
        return this.name;
    }



}
