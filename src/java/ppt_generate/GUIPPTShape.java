package ppt_generate;

import cc.pptshow.ppt.element.impl.PPTShape;
import cc.pptshow.ppt.domain.PPTShapeCss;
import cc.pptshow.ppt.domain.shape.Shape;
import cc.pptshow.ppt.domain.shape.Rect;
import cc.pptshow.ppt.domain.shape.Ellipse;
import cc.pptshow.ppt.domain.shape.Parallelogram;
import cc.pptshow.ppt.domain.shape.RoundRect;


public class GUIPPTShape {
    private String type;
    private double left;
    private double top;
    private double width;
    private double height;
    private String name;

    public GUIPPTShape(String type,double left, double top, double width, double height) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.type = type;
        this.name = type;
    }

    public PPTShape PPTShape_SetCss(double fillnet) {
        PPTShape pptShape = new PPTShape();

        PPTShapeCss css = new PPTShapeCss();
        Shape shape;
        if (this.type.equals("rectangle")) {
             shape = new Rect();
            css.setShape(shape);
        } else if (this.type.equals("ellipse")) {
            shape = new Ellipse();
            css.setShape(shape);
        }
        else if (this.type.equals("parallelogram")) {
            shape = new Parallelogram(fillnet);
            css.setShape(shape);
        }
        else if (this.type.equals("roundRect")) {
            shape = new RoundRect();
            css.setShape(shape);
        }

        css.setLeft(this.left);
        css.setTop(this.top);
        css.setWidth(this.width);
        css.setHeight(this.height);

        pptShape.setCss(css);

        return pptShape;
    }

    public void setLeft(double left) {
        this.left = left;
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

    public void setType(String type) {
        this.type = type;
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

    public String getType() {
        return this.type;
    }



}
