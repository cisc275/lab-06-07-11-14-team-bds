/**
 * View: Contains everything about graphics and images
 * Know size of world, which images to load etc
 *
 * has methods to
 * provide boundaries
 * use proper images for direction
 * load images for all direction (an image should only be loaded once!!! why?)
 **/
import java.lang.NullPointerException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;

public class View {
    private String file_location = "../resources/";

    private final int frameWidth;
    private final int frameHeight;
    private final int contentWidth;
    private final int contentHeight;
    
    private final int imgWidth;
    private final int imgHeight;

    private int frameCount = 10;
    private EnumMap<Direction, BufferedImage[]> pics;

    private Direction direction;
    private int frameNum = 0;
    private int xLoc;
    private int yLoc;

    private JFrame frame;
    private JButton button = new JButton();

    public View() {
        this(500, 600, 165, 165);
    }

    public View(int w, int h, int imgW, int imgH) {
        this.frameWidth = w;
        this.frameHeight = h;

        this.contentWidth = this.frameWidth;
        this.contentHeight = this.frameHeight / 2;
        this.imgWidth = imgW;
        this.imgHeight = imgH;

        this.loadImages(file_location);

        this.buildFrame();
    }

    public void update(int x, int y, Direction d) {
        if (this.xLoc != x || this.yLoc != y) {
            frameNum = (frameNum + 1) % frameCount;
        }

        this.xLoc = x;
        this.yLoc = y;
        this.direction = d;
        frame.repaint();
    }

    public int getWidth() { return this.contentWidth; }
    public int getHeight() { return this.contentHeight; }
    public int getImageWidth() { return this.imgWidth; }
    public int getImageHeight() { return this.imgHeight; }
    public JButton getButton() { return this.button; }

    private void loadImages(String filepath) {
        pics = new EnumMap<Direction, BufferedImage[]>(Direction.class);

        for (Direction d : Direction.values()) {
            String filename = "orc_forward_" + d.getName() +".png";
            File file = new File(filepath + filename );
            if (!file.isFile()) {
                continue;
            }
            BufferedImage img = createImage(file);
            pics.put(d, new BufferedImage[frameCount]);
            BufferedImage[] temp = pics.get(d);
            for(int j = 0; j < frameCount; j++)
                temp[j] = img.getSubimage(imgWidth*j, 0, imgWidth, imgHeight);
        }
    }
    private BufferedImage createImage(File file){
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(file);
            return bufferedImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("serial")
    private void buildFrame() {
        frame = new JFrame();
        frame.setBackground(Color.gray);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);
        frame.setLayout(new GridLayout(2,1));
        frame.getContentPane().add(new JPanel() {
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    try{
                        g.drawImage(pics.get(direction)[frameNum], xLoc, yLoc, Color.gray, this);
                    } catch(NullPointerException e) {
                        System.out.println("tbh im not sure");
                    }

                }
            });
        frame.add(button);
        frame.setVisible(true);
    }
}
