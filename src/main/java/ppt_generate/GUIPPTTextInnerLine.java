package ppt_generate;

import cc.pptshow.ppt.element.impl.PPTInnerText;
import cc.pptshow.ppt.domain.PPTInnerTextCss;
import cc.pptshow.ppt.element.impl.PPTInnerLine;
import cc.pptshow.ppt.domain.PPTInnerLineCss;

//该类为文本框，需要绑定文本(GUIPPTInnerText)的内容
public class GUIPPTTextInnerLine {
    private double lineHeigth;  //行高，默认为1，单倍行距
    private String name;
    private PPTInnerText pptInnerText;

    public GUIPPTTextInnerLine() {
        this.lineHeigth = 1;
        this.name = "text";
    }
    public GUIPPTTextInnerLine(PPTInnerText pptInnerText) {
        this.lineHeigth = 1;
        this.name = "text";
        this.pptInnerText = pptInnerText;
    }

    public PPTInnerLine PPTInnerLine_SetCss() {
        PPTInnerLine pptInnerLine = new PPTInnerLine();

        PPTInnerLineCss css = new PPTInnerLineCss();
        css.setLineHeight(this.lineHeigth);

        pptInnerLine.setCss(css);
        pptInnerLine.add(this.pptInnerText);

        return pptInnerLine;
    }

    public void setLineHeigth(double lineHeigth) {
        this.lineHeigth = lineHeigth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLineHeigth() {
        return this.lineHeigth;
    }




}
