package ppt_generate;


//该类为文本框，需要绑定文本(GUIPPTTextInnerLine)的内容
import cc.pptshow.ppt.element.impl.PPTInnerText;
import cc.pptshow.ppt.domain.PPTInnerTextCss;
import cc.pptshow.ppt.element.impl.PPTInnerLine;
import cc.pptshow.ppt.domain.PPTInnerLineCss;
import cc.pptshow.ppt.element.impl.PPTText;
import cc.pptshow.ppt.domain.PPTTextCss;


public class GUIPPTText {
    private double left;
    private double top;
    private double height;
    private double width;
    private double lineHeigth;  //行高
    private String name;
    private PPTInnerLine pptInnerLine;

    public GUIPPTText(double left, double top, double height, double width) {
        this.left = left;
        this.top = top;
        this.height = height;
        this.width = width;
        this.lineHeigth = 1;
        this.name = "text";
    }

    public PPTText PPTText_SetCss(PPTInnerLine pptInnerLine) {
        PPTText pptText = new PPTText(this.pptInnerLine);

        PPTTextCss css = new PPTTextCss();
        css.setLeft(this.left);
        css.setTop(this.top);
        css.setHeight(this.height);
        css.setWidth(this.width);

        pptText.setCss(css);

        return pptText;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public void setTop(double top) {
        this.top = top;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setLineHeigth(double lineHeigth) {
        this.lineHeigth = lineHeigth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLeft() {
        return this.left;
    }

    public double getTop() {
        return this.top;
    }

    public double getHeight() {
        return this.height;
    }

    public double getWidth() {
        return this.width;
    }

    public double getLineHeigth() {
        return this.lineHeigth;
    }

    public String getName() {
        return this.name;
    }

    public PPTInnerLine getPPTInnerLine() {
        return this.pptInnerLine;
    }

    public void setPPTInnerLine(PPTInnerLine pptInnerLine) {
        this.pptInnerLine = pptInnerLine;
    }

    public void addPPTInnerText(PPTInnerText pptInnerText) {
        this.pptInnerLine.add(pptInnerText);
    }

}
