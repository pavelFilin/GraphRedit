package ru.feeleen.application.BLL;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;


public class Drawer {
    private int width;
    private int height;
    private BufferedImage currentlyImage;
    Graphics2D graphics2D;
    Color currentlyColor;

    public Image getCurrentlyImage() {
        WritableImage image = SwingFXUtils.toFXImage(currentlyImage, null);
        return image;
    }

    public Drawer(int width, int height) {
        this.width = width;
        this.height = height;
        this.currentlyImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics2D = currentlyImage.createGraphics();
        graphics2D.setPaint(Color.white);
    }

    public Image drawPencial(int x, int y, Color c) {
        if (x > 0 && y > 0 && x < width && y < height) {
            currentlyImage.setRGB(x, y, c.getRGB());
        }
        WritableImage image = SwingFXUtils.toFXImage(currentlyImage, null);
        return image;
    }

    public Image drawLine(Integer x0, Integer y0, Integer x1, Integer y1, Color c) {
        currentlyColor = c;
        bresenhamLine(x0, height - y0, x1, height - y1);
        WritableImage image = SwingFXUtils.toFXImage(currentlyImage, null);
        return image;
    }

    public Image drawCircle(Integer x0, Integer y0, Integer radius, Color c) {
        currentlyColor = c;
        bresenhamCircle(x0, height - y0, radius);
        WritableImage image = SwingFXUtils.toFXImage(currentlyImage, null);
        return image;
    }

//    void bresenhamLine(Integer x0, Integer y0, Integer x1, Integer y1) {
//        boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
//        if (steep) {
//            swap(x0, y0);
//            swap(x1, y1);
//        }
//
//        if (x0 > x1) {
//            swap(x0, x1);
//            swap(y0, y1);
//        }
//        int dx = x1 - x0;
//        int dy = Math.abs(y1 - y0);
//        int error = dx / 2;
//        int ystep = (y0 < y1) ? 1 : -1;
//        int y = y0;
//        for (int x = x0; x <= x1; x++) {
//            drawPoint(steep ? y : x, steep ? x : y, currentlyColor);
//            error -= dy;
//            if (error < 0) {
//                y += ystep;
//                error += dx;
//            }
//        }
//    }

    public void bresenhamLine(int x, int y, int x2, int y2) {
        int w = x2 - x;
        int h = y2 - y;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
        if (w < 0) dx1 = -1;
        else if (w > 0) dx1 = 1;
        if (h < 0) dy1 = -1;
        else if (h > 0) dy1 = 1;
        if (w < 0) dx2 = -1;
        else if (w > 0) dx2 = 1;
        int longest = Math.abs(w);
        int shortest = Math.abs(h);
        if (!(longest > shortest)) {
            longest = Math.abs(h);
            shortest = Math.abs(w);
            if (h < 0) dy2 = -1;
            else if (h > 0) dy2 = 1;
            dx2 = 0;
        }
        int numerator = longest >> 1;
        for (int i = 0; i <= longest; i++) {
            drawPoint(x, y, currentlyColor);
            ;
            numerator += shortest;
            if (!(numerator < longest)) {
                numerator -= longest;
                x += dx1;
                y += dy1;
            } else {
                x += dx2;
                y += dy2;
            }
        }
    }

    private void swap(int x, int y) {
        int t = x;
        x = y;
        y = t;
    }

    private void drawPoint(int x, int y, Color c) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            currentlyImage.setRGB(x, y, c.getRGB());
        }
    }

    public Image drawPointing(int x, int y, Color c) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            currentlyImage.setRGB(x, y, c.getRGB());
        }

        WritableImage image = SwingFXUtils.toFXImage(currentlyImage, null);
        return image;
    }

    private void bresenhamCircle(int x0, int y0, int radius) {
        int x = radius;
        int y = 0;
        int radiusError = 1 - x;
        while (x >= y) {
            drawPoint(x + x0, y + y0, currentlyColor);
            drawPoint(y + x0, x + y0, currentlyColor);
            drawPoint(-x + x0, y + y0, currentlyColor);
            drawPoint(-y + x0, x + y0, currentlyColor);
            drawPoint(-x + x0, -y + y0, currentlyColor);
            drawPoint(-y + x0, -x + y0, currentlyColor);
            drawPoint(x + x0, -y + y0, currentlyColor);
            drawPoint(y + x0, -x + y0, currentlyColor);
            y++;
            if (radiusError < 0) {
                radiusError += 2 * y + 1;
            } else {
                x--;
                radiusError += 2 * (y - x + 1);
            }
        }
    }

    public Image InvertColors() {
        for (int i = 0; i < currentlyImage.getWidth(); i++) {
            for (int j = 0; j < currentlyImage.getHeight(); j++) {
                int neg = (0xFFFFFF - currentlyImage.getRGB(i, j)) | 0xFF000000;
                currentlyImage.setRGB(i, j, neg);
            }
        }
        WritableImage image = SwingFXUtils.toFXImage(currentlyImage, null);
        return image;
    }

    public Image imageInit() {
        for (int i = 0; i < currentlyImage.getWidth(); i++) {
            for (int j = 0; j < currentlyImage.getHeight(); j++) {
                currentlyImage.setRGB(i, j, 0xFFFFFFFF);
            }
        }

        return SwingFXUtils.toFXImage(currentlyImage, null);
    }

    public void setImage(BufferedImage img) {
        currentlyImage = img;
    }

    public Image getContrast() {
        int minRed = 255;
        int minGreen = 255;
        int minBlue = 255;
        int min = 0;
        for (int i = 0; i < currentlyImage.getWidth(); i++) {
            for (int j = 0; j < currentlyImage.getHeight(); j++) {
                int red = ((currentlyImage.getRGB(i, j)) >> 16) & 0xFF;
                int green = ((currentlyImage.getRGB(i, j)) >> 8) & 0xFF;
                int blue = currentlyImage.getRGB(i, j) & 0xFF;

                minRed = (minRed > red && red != 0) ? red : minRed;
                minGreen = (minGreen > green && green != 0) ? green : minGreen;
                minBlue = (minBlue > blue && blue != 0) ? blue : minBlue;
//                minRed = (minRed > red) ? red : minRed;
//                minGreen = (minGreen > green) ? green : minGreen;
//                minBlue = (minBlue > blue) ? blue : minBlue;

                //  min = (minBlue > red + green + blue) ? red + green + blue : min;
            }
        }

        for (int i = 0; i < currentlyImage.getWidth(); i++) {
            for (int j = 0; j < currentlyImage.getHeight(); j++) {
                int red = ((currentlyImage.getRGB(i, j)) >> 16) & 0xFF;
                int green = ((currentlyImage.getRGB(i, j)) >> 8) & 0xFF;
                int blue = currentlyImage.getRGB(i, j) & 0xFF;

                int redResult = (((red - minRed) < 0) ? 0 : red - minRed) * (minRed + red) / 255;//* ((byte) minRed / (byte) 255));
                int greenResult = ((green - minGreen < 0) ? 0 : green - minGreen) * (minGreen + green) / 255;
                ;// * ((byte) minGreen/ (byte) 255));
                int blueResult = ((blue - minBlue < 0) ? 0 : blue - minBlue) * (minBlue + blue) / 255;// * ((byte) minBlue / (byte) 255));

                int rgb = redResult;
                rgb <<= 8;
                rgb += greenResult;
                rgb <<= 8;
                rgb += blueResult;
//                rgb += greenResult << 8;
//                rgb += blueResult << 16;
                currentlyImage.setRGB(i, j, rgb);
            }
        }
        return SwingFXUtils.toFXImage(currentlyImage, null);
    }

    public Image getChannel(int red, int green, int blue) {
        for (int i = 0; i < currentlyImage.getWidth(); i++) {
            for (int j = 0; j < currentlyImage.getHeight(); j++) {
                int redImg = ((currentlyImage.getRGB(i, j)) >> 16) & 0xFF;
                int greenImg = ((currentlyImage.getRGB(i, j)) >> 8) & 0xFF;
                int blueImg = currentlyImage.getRGB(i, j) & 0xFF;

                redImg &= red;
                greenImg &= green;
                blueImg &= blue;

                int rgb = redImg;
                rgb <<= 8;
                rgb += greenImg;
                rgb <<= 8;
                rgb += blueImg;
//                rgb += greenResult << 8;
//                rgb += blueResult << 16;
                currentlyImage.setRGB(i, j, rgb);
            }
        }
        return SwingFXUtils.toFXImage(currentlyImage, null);


    }

    public Image setText(String text) {
        int countOfText = text.length() * 16;
        if (countOfText > width * height || countOfText >= Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Such more text");
        }
        int place = 0;

        byte count4 = (byte) (countOfText >> 24);
        byteWriteToImg(count4, place);
        place += 8;
        byte count3 = (byte) (countOfText >> 16);
        byteWriteToImg(count3, place);
        place += 8;
        byte count2 = (byte) (countOfText >> 8);
        byteWriteToImg(count2, place);
        place += 8;

        byte count1 = (byte) (countOfText);
        byteWriteToImg(count1, place);
        place += 8;

        char[] chars = text.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            charWriteToImg(chars[i], place);
            place += 16;
        }

        return SwingFXUtils.toFXImage(currentlyImage, null);
    }

    private void bitWriteToImg(byte bit, int place) {
        int x = place % currentlyImage.getWidth();
        int y = (int) Math.ceil(place / currentlyImage.getWidth());
        System.out.println("1" + currentlyImage.getRGB(x, y));

        int redImg = ((currentlyImage.getRGB(x, y)) >> 16) & 0xFF;
        int temp = redImg;
        if (bit == 0) {
            if (redImg % 2 == 0) {

            } else {
                if (redImg == 255) {
                    redImg--;
                } else {
                    redImg++;
                }
            }
        } else {
            if (redImg % 2 == 0) {
                redImg++;
            } else {

            }
        }

        int greenImg = ((currentlyImage.getRGB(x, y)) >> 8) & 0xFF;
        int blueImg = currentlyImage.getRGB(x, y) & 0xFF;

        int rgb = redImg;
        rgb <<= 8;
        rgb += greenImg;
        rgb <<= 8;
        rgb += blueImg;

        System.out.println("place = " + place + " bit = " + bit + " old: " + temp + " new " + redImg);
        currentlyImage.setRGB(x, y, rgb);
        System.out.println("1" + currentlyImage.getRGB(x, y));
    }

    private void byteWriteToImg(byte b, int place) {
        byte firstByte = (byte) (b & 1);
        int count = 0;
        do {
            firstByte = (byte) Math.abs((byte) (b >> 7));

            bitWriteToImg(firstByte, place);
            b = (byte) (b << 1);
            place += 1;

            count++;
        } while (count < 8);
    }

    private void charWriteToImg(char b, int place) {
        int littleByte = b & 255;
        int bigByte = (b >> 8) & 255;
        byteWriteToImg((byte) bigByte, place);
        place += 8;
        byteWriteToImg((byte) littleByte, place);
    }

    public String readText() {
        boolean[] countOfCharsArray = new boolean[32];
        int countOfChars = 0;
        int place = 0;
        for (int i = 0; i < countOfCharsArray.length; i++) {
            countOfCharsArray[i] = readBit(place);
            place++;
        }

        for (int i = 0; i < countOfCharsArray.length; i++) {
            if (countOfCharsArray[i]) {
                countOfChars++;
            }

            if (i != countOfCharsArray.length - 1) {
                countOfChars = countOfChars << 1;
            }
        }

        boolean[] textBits = new boolean[countOfChars];
        for (int i = 0; i < countOfChars; i++) {
            textBits[i] = readBit(place);
            place++;
        }

        int count = 0;
        char[] textChar = new char[countOfChars/16];
        for (int i = 0; i < textChar.length; i++) {
            char temp = 0;
            for (int j = 0; j < 16; j++) {
                if (textBits[count]){
                    temp +=1 ;
                }
                if (j!=15) {
                    temp=(char)(temp<<1);
                }
                count++;
            }
            textChar[i] = temp;
        }

        return new String(textChar);
    }

    private boolean readBit(int place) {
        int x = place % currentlyImage.getWidth();
        int y = (int) Math.ceil(place / currentlyImage.getWidth());

        int redImg = ((currentlyImage.getRGB(x, y)) >> 16) & 0xFF;
        if (redImg % 2 == 0) {
            return false;
        } else {
            return true;
        }
    }
}

