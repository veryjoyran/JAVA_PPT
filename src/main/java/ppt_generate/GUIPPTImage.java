package ppt_generate;

import cc.pptshow.ppt.element.impl.PPTImg;
import cc.pptshow.ppt.domain.PPTImgCss;

public class GUIPPTImage {
    private String path;
    private double left;
    private double top;
    private double width;
    private double height;
    private String name;

    public GUIPPTImage(String path) {
        this.path = path;
        this.left = 0;
        this.top = 0;
        this.width = 33.867;
        this.height = 19.05;
        this.name = "ppt";
    }

    public PPTImg PPTImg_SetCss() {
        PPTImg pptImg = PPTImg.build(this.path);

        PPTImgCss css = new PPTImgCss();
        css.setLeft(this.left);
        css.setTop(this.top);
        css.setWidth(this.width);
        css.setHeight(this.height);

        pptImg.setCss(css);

        return pptImg;
    }

    public void setLeft(double x1) {
        this.left = x1;
    }

    public void setTop(double top) {
        this.top = top;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
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

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public String getName() {
        return this.name;
    }
}
