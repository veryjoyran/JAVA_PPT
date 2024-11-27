package ppt_generate;

import cc.pptshow.ppt.element.impl.PPTLine;
import cc.pptshow.ppt.domain.PPTLineCss;

public class GUIPPTLine {
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private String name;

    public GUIPPTLine(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.name = "line";
    }

    public PPTLine PPTLine_SetCss() {
        PPTLine pptLine =new PPTLine();

        PPTLineCss css = new PPTLineCss();
        css.setLeft(this.x1);
        css.setTop(this.y1);
        css.setWidth(this.x2);
        css.setHeight(this.y2);

        pptLine.setCss(css);

        return pptLine;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX1() {
        return this.x1;
    }

    public double getY1() {
        return this.y1;
    }

    public double getX2() {
        return this.x2;
    }

    public double getY2() {
        return this.y2;
    }

    public String getName() {
        return this.name;
    }

}
