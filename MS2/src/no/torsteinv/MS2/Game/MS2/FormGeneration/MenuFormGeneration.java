package no.torsteinv.MS2.Game.MS2.FormGeneration;

import java.awt.Dimension;
import java.awt.Point;

import no.torsteinv.MS2.AbstractMenu.ActionHandler;
import no.torsteinv.MS2.AbstractMenu.Form;
import no.torsteinv.MS2.AbstractMenu.Buttons.StandardButton;
import no.torsteinv.MS2.AbstractMenu.Labels.StandardLabel;
import no.torsteinv.MS2.Game.Engine.Lists.FormList;
import no.torsteinv.MS2.Game.MS2.PaintStates.MainPaintStateList;
import no.torsteinv.MS2.Main.Main;

public class MenuFormGeneration extends FormList {
	static StandardButton Champaign;
	static StandardButton Backup;
	static StandardButton Rename;
	static StandardButton NewGame;
	static StandardButton Load;
	static StandardButton Delete;
	static StandardButton Back;
	static boolean OPORGINTMFP = true;
	private static final String Text = "Do you want to save your game?";
	private static final String Option1 = "Yes, i want to save my game";
	private static final String Option2 = "No, just exit to menu";

	private static final void createForm(Form singleplayer) {
		Champaign = new StandardButton(new Point(120, 100), "Champaign",
				new Dimension(160, 40), false, "Start a new Campaign");
		Backup = new StandardButton(new Point(300, 100), "Backup",
				new Dimension(160, 40), false, "Backup your world");
		Rename = new StandardButton(new Point(480, 100), "Rename",
				new Dimension(160, 40), false, "Rename your save");
		NewGame = new StandardButton(new Point(120, 150), "New Game",
				new Dimension(160, 40), true, "Start a skirmish");
		Load = new StandardButton(new Point(300, 150), "Load", new Dimension(
				160, 40), false, "Load selected save");
		Delete = new StandardButton(new Point(480, 150), "Delete",
				new Dimension(160, 40), false, "Delete slected save");
		Back = new StandardButton(new Point(800 - 175, 600 - 50), "Back",
				new Dimension(160, 40), true, "Go back to main menu");
		NewGame.Handler = new ActionHandler() {
			@Override
			public void Handle() {
				Thread th = new Thread(new Runnable() {

					@Override
					public void run() {
						Main.setPaintState(MainPaintStateList.Generating);
						Main.Access.start(1);
					}

				});
				th.start();
			}
		};

		Back.Handler = new ActionHandler() {
			@Override
			public void Handle() {
				Main.setPaintState(MainPaintStateList.MainMenu);
			}
		};

		singleplayer.add(Champaign);
		singleplayer.add(Backup);
		singleplayer.add(Rename);
		singleplayer.add(NewGame);
		singleplayer.add(Load);
		singleplayer.add(Delete);
		singleplayer.add(Back);
	}

	@Override
	public void initForms() {
		int w = Main.Width;
		int h = Main.Height;
		// MainMenu
		Form MainMenuForm = new Form(MainPaintStateList.MainMenu);

		StandardButton StartButton = new StandardButton(new Point(
				(w - 200) / 2, (h - 40 - 200) / 2), "Singleplayer",
				new Dimension(200, 40), "Play alone");
		StartButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.setPaintState(MainPaintStateList.Singleplayer);
			}

		};
		MainMenuForm.add(StartButton);

		StandardButton QUIT = new StandardButton(new Point((w - 150) / 2,
				(h - 40 + 40) / 2), "Quit", new Dimension(150, 40), "Exit game");
		QUIT.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				System.exit(0);
			}

		};
		MainMenuForm.add(QUIT);

		StandardButton OptionsButton = new StandardButton(new Point(
				(w - 150) / 2, (h - 40 - 40) / 2), "Options", new Dimension(
				150, 40), "Change Options around");
		OptionsButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				OPORGINTMFP = true;
				Main.setPaintState(MainPaintStateList.Options);
			}

		};
		MainMenuForm.add(OptionsButton);

		StandardButton Multiplayer = new StandardButton(new Point(
				(w - 150) / 2, (h - 40 - 120) / 2), "Multiplayer",
				new Dimension(150, 40), "Play with others");
		Multiplayer.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.setPaintState(MainPaintStateList.Multiplayer);
			}

		};
		MainMenuForm.add(Multiplayer);

		// Options
		Form OptionsMenuForm = new Form(MainPaintStateList.Options);

		StandardButton OptionsBack = new StandardButton(new Point(
				(w - 200) / 2, (h - 40 - 200) / 2), "Back", new Dimension(200,
				40), "Back to main menu");
		OptionsBack.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.setPaintState(OPORGINTMFP ? MainPaintStateList.MainMenu
						: MainPaintStateList.Pause);
			}

		};
		OptionsMenuForm.add(OptionsBack);

		// Pause
		Form PauseMenuForm = new Form(MainPaintStateList.Pause);

		StandardButton PauseResume = new StandardButton(new Point(
				(w - 200) / 2, (h - 40 - 200) / 2), "Resume", new Dimension(
				200, 40), "Resume game");
		PauseResume.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.setPaintState(MainPaintStateList.Game);
			}

		};
		PauseMenuForm.add(PauseResume);

		StandardButton OptionsButtonPause = new StandardButton(new Point(
				(w - 150) / 2, (h - 40 - 120) / 2), "Options", new Dimension(
				150, 40), "Change options around");
		OptionsButtonPause.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				OPORGINTMFP = false;
				Main.setPaintState(MainPaintStateList.Options);
			}

		};
		PauseMenuForm.add(OptionsButtonPause);

		StandardButton Save = new StandardButton(new Point((w - 150) / 2,
				(h - 40 - 40) / 2), "Save", new Dimension(150, 40), false,
				"Save your game");
		Save.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.Access.Save();
			}

		};
		PauseMenuForm.add(Save);

		StandardButton Exit = new StandardButton(new Point((w - 150) / 2,
				(h - 40 + 40) / 2), "Exit to menu", new Dimension(150, 40),
				"Exit to main menu");
		Exit.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.setPaintState(MainPaintStateList.SaveConfirm);
			}

		};
		PauseMenuForm.add(Exit);

		// SaveConfirm
		Form SaveConfirmMenuForm = new Form(MainPaintStateList.SaveConfirm);

		StandardLabel l = new StandardLabel(new Point((w / 2) - 95,
				(h / 2) - 50), Text);
		StandardButton b = new StandardButton(new Point((w / 2), (h / 2)),
				Option1, false, "Save your game before exiting");
		StandardButton b1 = new StandardButton(
				new Point((w / 2) - 195, (h / 2)), Option2,
				"Don't save your game before exiting");

		b.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.Access.Save();
				Main.setPaintState(MainPaintStateList.MainMenu);
			}

		};

		b1.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.setPaintState(MainPaintStateList.MainMenu);
			}

		};

		SaveConfirmMenuForm.add(b1);
		SaveConfirmMenuForm.add(b);
		SaveConfirmMenuForm.add(l);

		// Singleplayer Form
		Form Singleplayer = new Form(MainPaintStateList.Singleplayer);

		createForm(Singleplayer);

		Form MultiplayerForm = new Form(MainPaintStateList.Multiplayer);

		StandardButton Join = new StandardButton(new Point(15,
				positionByIndexDefined(0, 50, 150, 35)), "Join", new Dimension(
				300, 150), "Join a server");
		StandardButton Host = new StandardButton(new Point(15,
				positionByIndexDefined(1, 50, 150, 35)), "Host", new Dimension(
				300, 150), "Host server");
		StandardButton MBack = new StandardButton(new Point(15,
				positionByIndexDefined(2, 50, 150, 35)), "Back", new Dimension(
				300, 150), "Back to main menu");
		StandardLabel PlayerName = new StandardLabel(new Point(450, 50),
				"Player Name:");

		MBack.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.setPaintState(MainPaintStateList.MainMenu);
			}

		};

		Host.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.setPaintState(MainPaintStateList.Host);
			}

		};

		Join.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.setPaintState(MainPaintStateList.Join);
			}

		};

		MultiplayerForm.add(Join);
		MultiplayerForm.add(Host);
		MultiplayerForm.add(MBack);
		MultiplayerForm.add(PlayerName);

		add(MultiplayerForm);
		add(Singleplayer);
		add(SaveConfirmMenuForm);
		add(PauseMenuForm);
		add(MainMenuForm);
		add(OptionsMenuForm);
	}
}
