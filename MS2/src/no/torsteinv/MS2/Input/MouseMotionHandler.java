package no.torsteinv.MS2.Input;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import no.torsteinv.MS2.Main.Main;

public class MouseMotionHandler implements MouseMotionListener{
	
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Main.Access.loopMouseMovement(e.getX(),e.getY());
		
		Main.Mouse_X = e.getX();
		Main.Mouse_Y = e.getY();
	}
	public static void handle() {
		if(Main.PlacingSheet)
		{
			Rectangle r = Main.PlacingRectangle;
			int x = Main.Mouse_X < r.x ? Main.Mouse_X : Main.PlacingOriginalPoint.x;
			int y = Main.Mouse_Y < r.y ? Main.Mouse_Y : Main.PlacingOriginalPoint.y;
			int w = Main.Mouse_X < r.x ? Main.PlacingOriginalPoint.x - Main.Mouse_X : Main.Mouse_X - Main.PlacingOriginalPoint.x;
			int h = Main.Mouse_Y < r.y ? Main.PlacingOriginalPoint.y - Main.Mouse_Y : Main.Mouse_Y - Main.PlacingOriginalPoint.y;
			Main.PlacingRectangle.setBounds(x,y,w,h);
		}
	}
}
