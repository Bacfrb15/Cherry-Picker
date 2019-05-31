/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex001;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author franz
 */
public class Character {
    private int x;
    private int y;
    private int width;
    private int height;
    private int moveSpeed;

    public Character() {
        x = 100;
        y = 100;
        width = 100;
        height = 100;
        moveSpeed = 10;
    }
    
    public void drawCharacter(Graphics2D g2d,int x,int y,int height, int width)
    {
        g2d.setColor(Color.red);
        g2d.fillOval(x, y, width, height);
        g2d.fillOval(x, y, width, height);
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
    
    
    
    
}
