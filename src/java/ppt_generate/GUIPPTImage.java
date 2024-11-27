package ppt_generate;

import cc.pptshow.ppt.element.impl.PPTImg;
import cc.pptshow.ppt.domain.PPTImgCss;

public class GUIPPTImage {
    private String path;
    private double x1;
    private double y1;
    private double width;
    private double height;

    public GUIPPTImage(String path) {
        this.path = path;
        this.x1 = 0;
        this.y1 = 0;
        this.width = 33.867;
        this.height = 19.05;
    }

    public PPTImg PPTImg_SetCss() {
        PPTImg pptImg = PPTImg.build(this.path);

        PPTImgCss css = new PPTImgCss();
        css.setLeft(this.x1);
        css.setTop(this.y1);
        css.setWidth(this.width);
        css.setHeight(this.height);

        pptImg.setCss(css);

        return pptImg;
    }
}
