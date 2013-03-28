package no.torsteinv.MS2.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import no.torsteinv.MS2.Entities.Factory.Buildings.Machine;
import no.torsteinv.MS2.Game.MS2.PaintStates.MainPaintStateList;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Sound.Player;
import no.torsteinv.MS2.Sound.Sound;

public class KeyHandler implements KeyListener {

	boolean isShift = false;
	static int Code = 0;

	@Override
	public void keyPressed(KeyEvent e) {
		Code = e.getKeyCode();
		if (e.getKeyCode() == KeyEvent.VK_SHIFT)
			this.isShift = true;
		else if (e.getKeyCode() == KeyEvent.VK_CAPS_LOCK)
			this.isShift = !this.isShift;
		else {
			Main.Access
					.loopKeyInput((e.getKeyCode() == KeyEvent.VK_BACK_SPACE) ? "!BACKSPACE"
							: (e.getKeyCode() == KeyEvent.VK_ENTER) ? "!DONE"
									: this.isShift ? (e.getKeyChar() + "")
											.toUpperCase()
											: (e.getKeyChar() + ""));
		}
		handle(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT)
			this.isShift = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	static int CameraSpeed = 10;

	public static void handle(KeyEvent e) {
		CameraSpeed = 10;
		if (Code == KeyEvent.VK_C) {
			Main.HorisontalAlignment = Main.userSpecificVariables[Main.player]
					.getSpawn().x;
			Main.VerticalAlignment = Main.userSpecificVariables[Main.player]
					.getSpawn().y;

		}
		if (e.isControlDown())
			CameraSpeed *= 10;
		if (Code == KeyEvent.VK_UP) {

			Main.VerticalAlignment -= CameraSpeed;

		}
		if (Code == KeyEvent.VK_DOWN) {

			Main.VerticalAlignment += CameraSpeed;

		}
		if (Code == KeyEvent.VK_LEFT) {

			Main.HorisontalAlignment -= CameraSpeed;

		}
		if (Code == KeyEvent.VK_RIGHT) {

			Main.HorisontalAlignment += CameraSpeed;

		} else if (Code == KeyEvent.VK_ESCAPE) {
			try {
				Player.play(Sound.buttonClick);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			if (Main.game()) {
				Main.setPaintState(MainPaintStateList.Pause);
				Code = 0;
			} else if (Main.GUI()) {
				Main.setPaintState(MainPaintStateList.Game);
				Code = 0;
			} else if (Main.subFactory()) {
				if (Main.Interfaced instanceof Machine)
					Main.Interfaced = ((Machine) Main.Interfaced).getHome();
				else
					;
				Main.setPaintState(MainPaintStateList.Factory);
				Code = 0;
			}
		}
	}
}
