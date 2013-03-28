package no.torsteinv.MS2.Main.Client;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

import no.torsteinv.MS2.Input.KeyHandler;
import no.torsteinv.MS2.Input.MouseHandler;
import no.torsteinv.MS2.Input.MouseMotionHandler;
import no.torsteinv.MS2.Input.MouseWheelHandler;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Managers.Timing.Timer;
import no.torsteinv.MS2.Painting.MainPainter;

public class ClientF extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;

	public ClientF(){
		setTitle("Mars Settlement 2 - " + Main.getVersion());
		setResizable(false);
		setSize(Main.Width,Main.Height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Main.mouseHandler = new MouseHandler();
		addKeyListener(new KeyHandler());
		addMouseMotionListener(new MouseMotionHandler());
		addMouseListener(Main.mouseHandler);
		addMouseWheelListener(new MouseWheelHandler());
		
		Thread th = new Thread(this);
		th.start();
		
		setVisible(true);
	}
	@Override
	public void run() {
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		while(true){
			Main.Access.update();
			repaint();
			Timer.updateDelta();
			try{
				Thread.sleep(20);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}
	}
	private Image image;
	private Graphics graphics;
	private MainPainter p = new MainPainter();
	public void paint(Graphics g){
		image = createImage(Main.Width,Main.Height);
		graphics = image.getGraphics();
		
		p.paint(graphics);
		g.drawImage(image,0,0,null);
	}
	public static void main(String[] args){
		Main.Access = new Main();
		new ClientF();
	}
	
}
