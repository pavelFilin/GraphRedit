package ru.feeleen.application.BLL;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Drawer {
    private int weight;
    private int height;
    private BufferedImage currentlyImage;
    Graphics2D graphics2D;

    public Image getCurrentlyImage(){
        WritableImage image = SwingFXUtils.toFXImage(currentlyImage, null);
        return image;
    }

    public Drawer(int weigth, int height) {
        this.weight = weigth;
        this.height = height;
        this.currentlyImage = new BufferedImage(weigth, height, BufferedImage.TYPE_INT_ARGB);
        graphics2D = currentlyImage.createGraphics();
        graphics2D.setPaint(Color.white);
    }

    public Image drawPencial(int x, int y , Color c) {
        if (x > 0 && y > 0 && x < weight && y < height) {
            currentlyImage.setRGB(x, y, c.getRGB());
        }
        WritableImage image = SwingFXUtils.toFXImage(currentlyImage, null);
        return image;
    }

    public Image drawLine(Integer x0, Integer y0, Integer x1, Integer y1, Color c) {
        bresenhamLine(x0, y0, x1, y1);
        WritableImage image = SwingFXUtils.toFXImage(currentlyImage, null);
        return image;
    }

    public Image drawCircle(Integer x0, Integer y0, Integer radius, Color c) {
        bresenhamCircle(x0, y0, radius);
        WritableImage image = SwingFXUtils.toFXImage(currentlyImage, null);
        return image;
    }

    void bresenhamLine(Integer x0, Integer y0, Integer x1, Integer y1) {
        boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
        if (steep) {
            swap(x0, y0);
            swap(x1, y1);
        }

        if (x0 > x1) {
            swap(x0, x1);
            swap(y0, y1);
        }
        int dx = x1 - x0;
        int dy = Math.abs(y1 - y0);
        int error = dx / 2;
        int ystep = (y0 < y1) ? 1 : -1;
        int y = y0;
        for (int x = x0; x <= x1; x++) {
            drawPoint(steep ? y : x, steep ? x : y, Color.BLACK);
            error -= dy;
            if (error < 0) {
                y += ystep;
                error += dx;
            }
        }
    }

    private void swap(int x, int y) {
        int t = x;
        x = y;
        y = t;
    }

    private void drawPoint(int x, int y, Color c) {
        currentlyImage.setRGB(x, y, c.getRGB());
    }

    private void bresenhamCircle(int x0, int y0, int radius) {
        int x = radius;
        int y = 0;
        int radiusError = 1 - x;
        while (x >= y) {
            drawPoint(x + x0, y + y0 , Color.cyan);
            drawPoint(y + x0, x + y0 , Color.cyan);
            drawPoint(-x + x0, y + y0 , Color.cyan);
            drawPoint(-y + x0, x + y0, Color.cyan);
            drawPoint(-x + x0, -y + y0, Color.cyan);
            drawPoint(-y + x0, -x + y0, Color.cyan);
            drawPoint(x + x0, -y + y0, Color.cyan);
            drawPoint(y + x0, -x + y0, Color.cyan);
            y++;
            if (radiusError < 0) {
                radiusError += 2 * y + 1;
            } else {
                x--;
                radiusError += 2 * (y - x + 1);
            }
        }
    }
}

