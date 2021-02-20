package com.ssk.pattern.composite;

import java.awt.*;

/**
 * @author ssk
 * @date 2021/1/17
 * 通用形状接口
 */
public interface Shape {

    int getX();
    int getY();
    int getWidth();
    int getHeight();
    void move(int x, int y);
    boolean isInsideBounds(int x, int y);
    void select();
    void unSelect();
    boolean isSelected();
    void paint(Graphics graphics);

}
