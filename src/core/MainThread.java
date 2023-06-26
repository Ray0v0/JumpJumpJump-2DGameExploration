package core;

import core.background.Background;
import core.camera.Camera;
import core.character.Character;
import core.map.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;

public class MainThread extends JFrame implements KeyListener {
    //屏幕的分辨率
    public static int screen_w = 1024;
    public static int screen_h = 682;
    public static int half_screen_w = screen_w/2;
    public static int half_screen_h = screen_h/2;
    public static int screenSize = screen_w * screen_h;

    //用JPanel作为画板
    public static JPanel panel;

    //使用一个int数组存处屏幕上像素的数值
    public static int[] screen;

    //屏幕图像缓冲区。它提供了在内存中操作屏幕中图像的方法
    public static BufferedImage screenBuffer;

    //记载目前已渲染的 帧数
    public static int frameIndex;

    //希望达到的每频之间的间隔时间 (毫秒)
    public static int frameInterval = 11;

    //cpu睡眠时间，数字越小说明运算效率越高
    public static int sleepTime, averageSleepTime;

    //刷新率，及计算刷新率所用到一些辅助参数
    public static int framePerSecond;
    public static long lastDraw;
    public static double thisTime, lastTime;

    public static void main() {
        new MainThread();
    }

    public MainThread() {

        //弹出一个宽 为screen_w高为screen_h的Jpanel窗口，并把它放置屏幕中间。
        setTitle("Java3DPaint");
        panel= (JPanel) this.getContentPane();
        panel.setPreferredSize(new Dimension(screen_w, screen_h));
        panel.setMinimumSize(new Dimension(screen_w,screen_h));
        panel.setLayout(null);

        setResizable(false);
        pack();
        setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //用TYPE_INT_RGB来创建BufferedImage，然后把屏幕的像素数组指向BufferedImage中的DataBuffer。
        //这样通过改变屏幕的像素数组(screen[])中的数据就可以在屏幕中渲染出图像
        screenBuffer =  new BufferedImage(screen_w, screen_h, BufferedImage.TYPE_INT_RGB);
        DataBuffer dest = screenBuffer.getRaster().getDataBuffer();
        screen = ((DataBufferInt)dest).getData();


        addKeyListener(this);

        Map.init();

        while (true) {
            Background.main();

            Map.main();

            Character.main();

            Camera.main();

            fpsHandle();

            panel.getGraphics().drawImage(screenBuffer, 0, 0, this);
        }
    }

    public void fpsHandle() {
        //loop每运行一边，帧数就+1
        frameIndex++;
        int totalSpeedTime = 0;
        while(System.currentTimeMillis()-lastDraw<frameInterval){
            try {
                Thread.sleep(1);
                totalSpeedTime++;
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        sleepTime+=totalSpeedTime;
        lastDraw=System.currentTimeMillis();
        //计算当前的刷新率，并尽量让刷新率保持恒定。
        if(frameIndex%30==0){
            double thisTime = System.currentTimeMillis();
            framePerSecond = (int)(1000/((thisTime - lastTime)/30));
            lastTime = thisTime;
            averageSleepTime = sleepTime / 30;
            sleepTime = 0;
        }

        //显示当前刷新率
        Graphics2D g2 =(Graphics2D)screenBuffer.getGraphics();
        g2.setColor(Color.BLACK);
        g2.drawString("FPS: " + framePerSecond + "      "  +  "Thread Sleep: " + averageSleepTime +  "ms    ", 5, 15);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
            Character.jump = true;
        else if(e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
            Character.left = true;
        else if(e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
            Character.right = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
            Character.jump = false;
        else if(e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
            Character.left = false;
        else if(e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
            Character.right = false;

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }
}
