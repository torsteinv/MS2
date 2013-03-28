package no.torsteinv.MS2.Game.MS2.PaintStates;

import java.awt.Color;
import java.awt.Graphics;

import no.torsteinv.MS2.Game.Engine.Lists.PaintStateList;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.Engine.Others.PaintStateHandler;
import no.torsteinv.MS2.Game.Engine.Others.PaintStateType;
import no.torsteinv.MS2.Main.Main;

public class MainPaintStateList extends PaintStateList {
	public static final PaintState MainMenu = new PaintState(
			PaintStateType.MENU);
	public static final PaintState Game = new PaintState(PaintStateType.GAME);
	public static final PaintState Win = new PaintState(PaintStateType.MENU);
	public static final PaintState Lose = new PaintState(PaintStateType.MENU);
	public static final PaintState Options = new PaintState(PaintStateType.MENU);
	public static final PaintState SaveConfirm = new PaintState(
			PaintStateType.MENU);
	public static final PaintState Singleplayer = new PaintState(
			PaintStateType.MENU);
	public static final PaintState Pause = new PaintState(PaintStateType.MENU);
	public static final PaintState Multiplayer = new PaintState(
			PaintStateType.MENU);
	public static final PaintState Container = new PaintState(
			PaintStateType.GUI);
	public static final PaintState MainGUI = new PaintState(PaintStateType.GUI);
	public static final PaintState Factory = new PaintState(PaintStateType.GUI);
	public static final PaintState Host = new PaintState(PaintStateType.MENU);
	public static final PaintState Join = new PaintState(PaintStateType.MENU);
	public static final PaintState Input = new PaintState(
			PaintStateType.SUB_FACTORY);
	public static final PaintState Machines = new PaintState(
			PaintStateType.SUB_FACTORY);
	public static final PaintState Crafter = new PaintState(
			PaintStateType.SUB_FACTORY);
	public static final PaintState Gun = new PaintState(
			PaintStateType.SELECTABLE);
	public static final PaintState Explorer = new PaintState(
			PaintStateType.SELECTABLE);
	public static final PaintState Generating = new PaintState(
			PaintStateType.MENU);
	public static final PaintState Assembler = new PaintState(
			PaintStateType.GUI);
	public static final PaintState SandQuarry = new PaintState(
			PaintStateType.GUI);
	public static final PaintState Transporter = new PaintState(
			PaintStateType.SELECTABLE);

	@Override
	public void loadEntities() {
		addFunctionality();
		load(MainMenu, "MainMenu");
		load(Game, "Game");
		load(Win, "Win");
		load(Lose, "Lose");
		load(Options, "Options");
		load(SaveConfirm, "SaveConfirm");
		load(Singleplayer, "Singleplayer");
		load(Pause, "Pause");
		load(Multiplayer, "Multiplayer");
		load(Container, "Container");
		load(MainGUI, "MainGUI");
		load(Factory, "Factory");
		load(Host, "Host");
		load(Join, "Join");
		load(Input, "Input");
		load(Machines, "Machines");
		load(Crafter, "Crafter");
		load(Gun, "Gun");
		load(Explorer, "Explorer");
		load(Generating, "Generating");
		load(Assembler, "Assembler");
		load(SandQuarry,"SandQuarry");
	}

	public void addFunctionality() {
		MainMenu.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.PaintMenu(g);
			}
		});
		Game.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.paintGame(g);
				pa.map(g);
				pa.Layout(g);
				pa.PaintChatMain(g);
			}
		});
		Win.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.PaintMenu(g);
			}
		});
		Lose.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.PaintMenu(g);
				pa.LooseText(g);
			}
		});
		Options.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.PaintMenu(g);
			}
		});
		SaveConfirm.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.PaintMenu(g);
			}
		});
		Singleplayer.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.PaintMenu(g);
			}
		});
		Pause.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.PaintMenu(g);
			}
		});
		Multiplayer.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.PaintMenu(g);
				pa.MiddleSeperator(g);
			}
		});
		Container.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.paintGame(g);
				pa.ContainerLayout(g);
				pa.PaintItems(g);
				pa.PaintChatMain(g);
			}
		});
		MainGUI.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.paintGame(g);
				pa.MainGui(g);
				pa.MainExtras(g);
				pa.PaintChatMain(g);
			}
		});
		Factory.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.paintGame(g);
				pa.FactoryGUI(g);
				pa.FactoryBuildings(g);
				pa.FactoryPipes(g);
				pa.FactoryExtras(g);
				pa.factoryHover(g);
			}
		});
		Host.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.PaintMenu(g);
			}
		});
		Join.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.PaintMenu(g);
			}
		});
		Input.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.paintGame(g);
				pa.FactoryGUINL(g);
				pa.FactoryBuildings(g);
				pa.FactoryPipes(g);
				pa.FactoryExtras(g);
				pa.factoryHover(g);
			}
		});
		Machines.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.paintGame(g);
				pa.FactoryGUI(g);
				pa.FactoryBuildings(g);
				pa.FactoryPipes(g);
				pa.FactoryExtras(g);
				pa.factoryHover(g);
			}
		});
		Crafter.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.paintGame(g);
				pa.FactoryGUINL(g);
				pa.FactoryBuildings(g);
				pa.FactoryPipes(g);
				pa.FactoryExtras(g);
				pa.factoryHover(g);
			}
		});
		Gun.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.paintGame(g);
				pa.map(g);
				pa.Layout(g);
				pa.PaintChatMain(g);
			}
		});
		Explorer.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.paintGame(g);
				pa.map(g);
				pa.Layout(g);
				pa.PaintChatMain(g);
			}
		});
		Generating.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.PaintMenu(g);
				pa.loadingScreen(g);
				g.setColor(Color.BLACK);
				g.drawString(Main.ProgressMessage, 400 - (g.getFontMetrics().stringWidth(Main.ProgressMessage) / 2), 400);
			}
		});
		Assembler.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.paintGame(g);
				pa.AssemblerLayout(g);
				pa.PaintChatMain(g);
			}
		});
		SandQuarry.setHandler(new PaintStateHandler(){

			@Override
			public void paint(Graphics g) {
				pa.paintGame(g);
				pa.map(g);
				pa.Layout(g);
				pa.PaintChatMain(g);
			}
			
		});
		Transporter.setHandler(new PaintStateHandler() {

			@Override
			public void paint(Graphics g) {
				pa.paintGame(g);
				pa.map(g);
				pa.Layout(g);
				pa.PaintChatMain(g);
			}
		});
	}
}