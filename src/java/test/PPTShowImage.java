package test;

import cc.pptshow.ppt.show.PPTShow;
import cc.pptshow.ppt.show.PPTShowSide;
import cc.pptshow.ppt.domain.PPTSideCss;
import cc.pptshow.ppt.element.impl.PPTText;
import cc.pptshow.ppt.element.impl.PPTInnerLine;
import cc.pptshow.ppt.element.impl.PPTInnerText;
import cc.pptshow.ppt.domain.PPTInnerTextCss;
import cc.pptshow.ppt.element.impl.PPTImg;
import cc.pptshow.ppt.domain.PPTImgCss;
import ppt_generate.GUIPPTImage;

public class PPTShowImage {

    public static void main(String[] args) {
        //新建一个PPT对象
        PPTShow pptShow = PPTShow.build();
        //新建一页PPT
        PPTShowSide side = PPTShowSide.build();
//        //创建一个图片对象
//        PPTImg pptImg = PPTImg.build("src/main/java/test/test.jpg");
//
//        //设置图片的样式
//        PPTImgCss css = new PPTImgCss();
//        css.setLeft(0);
//        css.setTop(0);
//        css.setWidth(20);
//        css.setHeight(20);

//        pptImg.setCss(css);
        GUIPPTImage guiPPTImage = new GUIPPTImage("src/main/java/test/test.jpg");
        PPTImg pptImg = guiPPTImage.PPTImg_SetCss();

        //输出到文件
        side.add(pptImg);
        //在PPT里面添加PPT页面
        pptShow.add(side);

        //输出到文件
        pptShow.toFile("img");
    }
}