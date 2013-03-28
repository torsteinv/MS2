package no.torsteinv.MS2.Painting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import no.torsteinv.MS2.AbstractMenu.Form;
import no.torsteinv.MS2.AbstractMenu.Buttons.InvisibleButton;
import no.torsteinv.MS2.AbstractMenu.InputForms.StandardInputForm;
import no.torsteinv.MS2.AbstractMenu.Special.Scrollbar;
import no.torsteinv.MS2.Entities.Container;
import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Buildings.Building;
import no.torsteinv.MS2.Entities.EntityAttachments.EntityAttachment;
import no.torsteinv.MS2.Entities.Factory.FactoryElement;
import no.torsteinv.MS2.Entities.Factory.Buildings.Machine;
import no.torsteinv.MS2.Entities.Items.ItemStack;
import no.torsteinv.MS2.Entities.Movables.CommandType;
import no.torsteinv.MS2.Entities.Movables.Movable;
import no.torsteinv.MS2.Entities.NatureObjects.IceLakes;
import no.torsteinv.MS2.Entities.NatureObjects.Mountain;
import no.torsteinv.MS2.Entities.NatureObjects.NatureObject;
import no.torsteinv.MS2.Entities.Sheets.Concrete;
import no.torsteinv.MS2.Entities.Sheets.Sheet;
import no.torsteinv.MS2.Game.MS2.Buildings.AssemblerBuilding;
import no.torsteinv.MS2.Game.MS2.Buildings.FactoryBuilding;
import no.torsteinv.MS2.Game.MS2.Items.Item;
import no.torsteinv.MS2.Game.MS2.Pipe.BasicPipe;
import no.torsteinv.MS2.Game.MS2.PlaceStates.MainPlaceStateList;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Main.Miscellaneous.Message;
import no.torsteinv.MS2.Managers.Visualization.TransperentManager;
import no.torsteinv.MS2.ParticleSystem.Draw.Painter;

public class PainterAction {

	public void MiddleSeperator(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine(400, 0, 400, 600);
	}

	public void LooseText(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		Font f = g.getFont();
		g.setFont(Font.decode("Arial-BOLD-50"));
		g.drawString("YOU LOST! D:", (700 / 2) - 100, 200);
		g.setFont(f);
	}

	public void PaintChatMain(Graphics g) {
		for (Message msg : Main.Messages) {
			msg.setViewed(msg.getViewed() - 1);
			if (msg.getViewed() == 0)
				Main.Messages.remove(msg);
			if (Main.Messages.indexOf(msg) > 10)
				Main.Messages.remove(msg);
			g.setColor(new Color(50, 50, 50, msg.viewed));
			g.fillRect(
					100,
					PosByIndex(Main.Messages.indexOf(msg), Main.Messages.size()),
					(g.getFontMetrics().stringWidth(msg.message) + 10), 20);
			g.setColor(new Color(255, 255, 255, msg.viewed));
			g.drawString(
					msg.getMessage(),
					105,
					PosByIndex(Main.Messages.indexOf(msg), Main.Messages.size()) + 15);
		}
	}

	public int PosByIndex(int index, int Max) {
		return -((Max - index) * 20) + 555;
	}

	public void FactoryPipes(Graphics g) {
		for (Entity p : Main.Entities)
			if (p instanceof BasicPipe) {
				g.drawImage(TransperentManager.makeColorTransparent(
						((BasicPipe) p).GenerateTexture(), new Color(255, 0,
								255)),
						(((FactoryElement) p).getCanvasX() * 50) + 50,
						(((FactoryElement) p).getCanvasY() * 50) + 50, null);
				ItemStack[] conFix = ((Container) p).getContent().toArray(new ItemStack[]{});
				for (ItemStack s : conFix)
					try {
						g.drawImage(Item.Texture(s.getType().ID,
								Item.Scale_Default), ((FactoryElement) p)
								.getCanvasX() * 50 + 50, ((FactoryElement) p)
								.getCanvasY() * 50 + 50, null);
						g.drawString("" + s.getQuantity(),
								((FactoryElement) p).getCanvasX() * 50 + 85,
								((FactoryElement) p).getCanvasY() * 50 + 100);
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
	}

	public void factoryHover(Graphics g) {
		ExtraTools
				.createTooltip(
						((FactoryBuilding) (Main.Interfaced instanceof FactoryElement ? Main.Interfaced.Entities
								.get("Home") : Main.Interfaced))
								.getFactoryElementAtPos(
										(Main.Mouse_X - 50) / 50,
										(Main.Mouse_Y - 50) / 50) == null ? ""
								: ((FactoryBuilding) (Main.Interfaced instanceof FactoryElement ? Main.Interfaced.Entities
										.get("Home") : Main.Interfaced))
										.getFactoryElementAtPos(
												(Main.Mouse_X - 50) / 50,
												(Main.Mouse_Y - 50) / 50)
										.getClass().getSimpleName(),
						Main.Mouse_X, Main.Mouse_Y, g);
	}

	public void FactoryGUI(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		Font f = g.getFont();
		g.setFont(Font.decode("Arial-BOLD-22"));
		g.drawString("Factory", (700 / 2) - 10, 45);
		g.setFont(f);
		g.setColor(Color.GRAY);
		g.fillRect(50, 50, 700, 500);
		g.setColor(Color.DARK_GRAY);
		g.drawRect(50, 50, 700, 500);
		for (int i = 50; i < 600; i += 50)
			g.drawLine(i, 50, i, 550);
		for (int i = 50; i < 550; i += 50)
			g.drawLine(50, i, 750, i);
	}

	public void FactoryGUINL(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		Font f = g.getFont();
		g.setFont(Font.decode("Arial-BOLD-22"));
		g.drawString("Factory", (700 / 2) - 10, 45);
		g.setFont(f);
		g.setColor(Color.GRAY);
		g.fillRect(50, 50, 700, 500);
		g.setColor(Color.DARK_GRAY);
		g.drawRect(50, 50, 700, 500);
		for (int i = 50; i < 600; i += 50)
			g.drawLine(i, 50, i, 550);
		for (int i = 50; i < 550; i += 50)
			g.drawLine(50, i, 550, i);
	}

	public void FactoryBuildings(Graphics g) {
		for (Entity m : Main.Entities)
			if (m instanceof Machine)
				g.drawImage(TransperentManager.makeColorTransparent(
						m.getTexture(), new Color(255, 0, 255)),
						(((FactoryElement) m).getCanvasX() * 50) + 50,
						(((FactoryElement) m).getCanvasY() * 50) + 50, null);

	}

	public void FactoryExtras(Graphics g) {
		if (Main.PlaceState != MainPlaceStateList.None
				|| Main.PlaceState != null)
			g.drawImage(TransperentManager.makeColorTransparent(
					Main.PlaceState.Icon, new Color(255, 0, 255)),
					Main.Mouse_X - 25, Main.Mouse_Y - 25, null);
	}

	public void MainGui(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		Font f = g.getFont();
		g.setFont(Font.decode("Arial-BOLD-22"));
		g.drawString("Main Workspace", (700 / 2) - 10, 45);
		g.setFont(f);
		g.setColor(Color.GRAY);
		g.fillRect(50, 50, 700, 500);

		g.setColor(Color.DARK_GRAY);
		g.drawRect(50, 50, 700, 500);
		g.drawLine((700 / 2) + 50, 0 + 50, (700 / 2) + 50, 500 + 50);
		g.drawLine(0 + 50, (500 / 2) + 50, 700 + 50, (500 / 2) + 50);

		g.setColor(Color.WHITE);
		g.drawString("Burn", 0 + 5 + 50, 0 + 15 + 50);
		g.drawString("Refine", (700 / 2) + 5 + 50, 0 + 15 + 50);
		g.drawString("Mix", 0 + 5 + 50, (500 / 2) + 15 + 50);
		g.drawString("Compress", (700 / 2) + 5 + 50, (500 / 2) + 15 + 50);

		g.setColor(Color.DARK_GRAY);
		g.fillRect(50, 30, 90, 15);
		g.setColor(Color.WHITE);
		g.drawString(
				"Money: " + Main.userSpecificVariables[Main.player].getMoney()
						+ "$", 50, 40);

	}

	public void MainExtras(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(10, (125 + (int) (Main.Access.aggregatorFuel / 2.857f)), 30,
				350 - (int) (Main.Access.aggregatorFuel / 2.857f));
		g.setColor(Color.YELLOW);
		g.drawRect(10, 125, 30, 350);
	}

	public void PaintItems(Graphics g) {
		for (ItemStack i : ((Container) Main.Interfaced).getContent()) {
			try {
				g.drawImage(Item.Texture(i.getType().ID, 1),
						(int) (i.getX() * 50) + 50, (int) (i.getY() * 50) + 50,
						null);
				g.setColor(Color.WHITE);
				g.drawString(i.getQuantity() + "", (int) (i.getX() * 50) + 50,
						(int) (i.getY() * 50) + 100);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (Main.Mouse_X - 50 < Main.Width - 100
				|| Main.Mouse_Y - 50 < Main.Height - 100)
			ExtraTools
					.createTooltip(
							((Container) Main.Interfaced).getItemAtPos(
									(Main.Mouse_X - 50) / 50,
									(Main.Mouse_Y - 50) / 50) == null ? ""
									: ((Container) Main.Interfaced)
											.getItemAtPos(
													(Main.Mouse_X - 50) / 50,
													(Main.Mouse_Y - 50) / 50)
											.getType().toString()
											.replaceAll("_", " "),
							Main.Mouse_X, Main.Mouse_Y, g);
	}

	public void ContainerLayout(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		Font f = g.getFont();
		g.setFont(Font.decode("Arial-BOLD-22"));
		g.drawString("Container", (700 / 2) - 10, 45);
		g.setFont(f);
		g.setColor(Color.GRAY);
		g.fillRect(50, 50, 700, 500);
		g.setColor(Color.DARK_GRAY);
		g.drawRect(50, 50, 700, 500);
		for (int i = 50; i < 750; i += 50)
			g.drawLine(i, 50, i, 550);
		for (int i = 50; i < 550; i += 50)
			g.drawLine(50, i, 750, i);
	}

	public void map(Graphics g) {
		g.setColor(new Color(255, 125, 91));
		g.fillRect(0, 0, 200, 200);
		for (Entity m : Main.Entities)
			if (m instanceof NatureObject) {
				g.setColor(m instanceof Mountain ? new Color(0xC77826)
						: m instanceof IceLakes ? Color.WHITE : Color.BLACK);
				g.fillRect((int) m.getX() / 50 - 1, (int) m.getY() / 50 - 1, 2,
						2);
			}
		for (Entity s : Main.Entities)
			if (s instanceof Sheet) {
				g.setColor(s.getMapColor());
				g.fillRect((int) s.getX() / 50, (int) s.getY() / 50,
						((Sheet) s).getSelectionBox().width / 50,
						((Sheet) s).getSelectionBox().height / 50);
			}
		for (Entity b : Main.Entities)
			if (b instanceof Building) {
				g.setColor(b.getMapColor());
				g.fillRect((int) b.getX() / 50 - 1, (int) b.getY() / 50 - 1, 3,
						3);
			}
		for (Entity v : Main.Entities)
			if (v instanceof Movable) {
				g.setColor(v.getMapColor());
				g.fillRect((int) v.getX() / 50 - 1, (int) v.getY() / 50 - 1, 3,
						3);
			}
		for (Entity v : Main.Entities)
			if (v instanceof EntityAttachment) {
				g.setColor(v.getMapColor());
				g.fillRect((int) v.getX() / 50 - 1, (int) v.getY() / 50 - 1, 3,
						3);
			}
		g.drawImage(Main.Darkness.RenderMap(), 0, 0, null);
	}

	public void PaintMenu(Graphics g) {
		for (int i = 0; i < 200; i++)
			for (int j = 0; j < 200; j++)
				g.drawImage(MainPainter.MarsSand, i * 50, j * 50, null);
	}

	Rectangle r = new Rectangle();

	BufferedImage img = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
	Graphics2D g;

	public void paintGame(Graphics graphics) {
		this.g = (Graphics2D) this.img.getGraphics();

		for (int i = 0; i < 200; i++)
			for (int j = 0; j < 200; j++)
				this.g.drawImage(MainPainter.MarsSand, i * 50
						- (int) Main.HorisontalAlignment, j * 50
						- (int) Main.VerticalAlignment, null);
		for (Entity m : Main.Entities)
			if (m instanceof NatureObject)
				this.g.drawImage(
						m instanceof Mountain ? ((Mountain) m).getTexture()
								: m instanceof IceLakes ? MainPainter.IceLakes
										: null, (int) m.getX()
								- (int) Main.HorisontalAlignment,
						(int) m.getY() - (int) Main.VerticalAlignment, null);
		for (Entity m : Main.Entities)
			if (m instanceof Sheet) {
				this.g.setColor(((Sheet) m).gameColor());
				this.g.fillRect(
						(int) m.getX() - (int) Main.HorisontalAlignment,
						(int) ((Sheet) m).getY() - (int) Main.VerticalAlignment,
						((Sheet) m).getSelectionBox().width,
						((Sheet) m).getSelectionBox().height);
			}
		for (Entity m : Main.Entities)
			if (m instanceof Building)
				this.g.drawImage(((Building) m).getTexture(), (int) m.getX()
						- (int) Main.HorisontalAlignment, (int) m.getY()
						- (int) Main.VerticalAlignment, null);
		for (Entity m : Main.Entities)
			if (m instanceof Movable) {
				this.g.drawImage(((Movable) m).getTexture(), (int) m.getX()
						- (int) Main.HorisontalAlignment, (int) m.getY()
						- (int) Main.VerticalAlignment, null);
				this.g.setColor(Color.BLUE);
				if (((Movable) m).isSelected()
						&& ((Movable) m).getCommandCenter().Type
								.equals(CommandType.Goto)) {
					this.g.drawLine(
							(int) ((((Movable) m).getX() + (((Movable) m)
									.getTexture().getWidth(null) / 2)) - Main.HorisontalAlignment),
							(int) ((((Movable) m).getY() + (((Movable) m)
									.getTexture().getHeight(null) / 2)) - Main.VerticalAlignment),
							(int) (((Movable) m).getCommandCenter()
									.getVaribleAsInteger("X") - Main.HorisontalAlignment),
							(int) (((Movable) m).getCommandCenter()
									.getVaribleAsInteger("Y") - Main.VerticalAlignment));
					this.g.drawLine(((Movable) m).getCommandCenter()
							.getVaribleAsInteger("X")
							- (int) Main.HorisontalAlignment - 25,
							((Movable) m).getCommandCenter()
									.getVaribleAsInteger("Y")
									- (int) Main.VerticalAlignment - 25,
							((Movable) m).getCommandCenter()
									.getVaribleAsInteger("X")
									- (int) Main.HorisontalAlignment + 25,
							((Movable) m).getCommandCenter()
									.getVaribleAsInteger("Y")
									- (int) Main.VerticalAlignment + 25);

					this.g.drawLine(((Movable) m).getCommandCenter()
							.getVaribleAsInteger("X")
							- (int) Main.HorisontalAlignment - 25,
							((Movable) m).getCommandCenter()
									.getVaribleAsInteger("Y")
									- (int) Main.VerticalAlignment + 25,
							((Movable) m).getCommandCenter()
									.getVaribleAsInteger("X")
									- (int) Main.HorisontalAlignment + 25,
							((Movable) m).getCommandCenter()
									.getVaribleAsInteger("Y")
									- (int) Main.VerticalAlignment - 25);
				}
			}
		for (Entity m : Main.Entities)
			if (m instanceof EntityAttachment)
				this.g.drawImage(((EntityAttachment) m).getTexture(),
						(int) m.getX() - (int) Main.HorisontalAlignment,
						(int) m.getY() - (int) Main.VerticalAlignment, null);
		this.g.drawImage(Painter.generateOverlay(), 0, 0, null);
		this.g.drawImage(Main.Darkness.RenderGame(), 0, 0, null);
		if (Main.PlacingSheet) {
			this.r.setBounds(Main.PlacingRectangle);
			this.g.setColor(Concrete
					.canBePlaced(this.r.x + (int) Main.HorisontalAlignment,
							this.r.y + (int) Main.VerticalAlignment,
							this.r.width, this.r.height) ? Color.BLUE
					: Color.RED);
			this.g.drawRect(this.r.x, this.r.y, this.r.width, this.r.height);
		}

		graphics.drawImage(this.img, 0, 0, (800), (600), null);
	}

	public void Layout(Graphics g) {
		int w = Main.Width;
		int h = Main.Height;
		g.setColor(Color.GRAY);
		g.fillRect(w - 200, 0, 200, h);
		g.drawRect(0, 0, 201, 201);
		g.setColor(Color.DARK_GRAY);
		g.drawRect(0, 0, 202, 202);
		g.setColor(Color.LIGHT_GRAY);
		g.drawRect(0, 0, 200, 200);

		g.setColor(Color.GRAY);
		g.drawRect((int) Main.HorisontalAlignment / 50,
				(int) Main.VerticalAlignment / 50, 12, 12);

		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(w - 200, 0, w - 200, h);
		g.drawLine(w - 201, 0, w - 201, h);
		g.setColor(Color.DARK_GRAY);
		g.drawLine(w - 4, 0, w - 4, h);
		g.drawLine(w - 5, 0, w - 5, h);

		g.setColor(Color.BLUE);
		g.fillRect(25, (225 + (int) (Main.Access.aggregatorFuel / 2.857f)), 50,
				350 - (int) (Main.Access.aggregatorFuel / 2.857f));
		g.setColor(Color.YELLOW);
		g.drawRect(25, 225, 50, 350);

		g.setColor(Color.DARK_GRAY);
		g.fillRect(25, 205, 90, 15);
		g.setColor(Color.WHITE);
		g.drawString(
				"Money: " + Main.userSpecificVariables[Main.player].getMoney()
						+ "$", 30, 217);
		if (Main.PlaceState != MainPlaceStateList.None)
			g.drawImage(TransperentManager.makeColorTransparent(
					Main.PlaceState.Icon, new Color(255, 0, 255)),
					Main.Mouse_X - 25, Main.Mouse_Y - 25, null);
	}

	public void loadingScreen(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		Font f = g.getFont();
		g.setFont(Font.decode("Arial-BOLD-22"));
		g.drawString("Generating...", (700 / 2) - 10, 45);
		g.setFont(f);

		g.setColor(Color.GRAY);
		g.fillRect(200, 275, 400, 50);

		g.setColor(new Color(0, 200, 0));
		g.fillRect(200, 275, (int) (Main.Loading_Progress * 4), 50);

		g.setColor(Color.DARK_GRAY);
		g.drawRect(200, 275, 400, 50);
	}

	public void ItemSelectionScreen(Graphics g, int x, int y) {
		Color c = g.getColor();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y, 125, 400);
		g.setColor(Color.DARK_GRAY);
		g.drawRect(x, y, 125, 400);
		generateCanvas(x + 15, y + 37, 50, 350, 50, g);
		g.drawLine(x + 125, y, x + 125, y + 400);
		g.setColor(c);
	}

	public void provideItemSelectionButtonLayout(Form f, int x, int y) {
		StandardInputForm searchbar = new StandardInputForm(new Point(x, y),
				"Search...", new Dimension(125, 25), "Search for items");

		Scrollbar scrollbar = new Scrollbar(new Point(x, y + 37), 350);

		InvisibleButton b1 = new InvisibleButton(x + 15, y + 37, 50, 50);
		InvisibleButton b2 = new InvisibleButton(x + 15, y + 87, 50, 50);
		InvisibleButton b3 = new InvisibleButton(x + 15, y + 137, 50, 50);
		InvisibleButton b4 = new InvisibleButton(x + 15, y + 187, 50, 50);
		InvisibleButton b5 = new InvisibleButton(x + 15, y + 237, 50, 50);
		InvisibleButton b6 = new InvisibleButton(x + 15, y + 287, 50, 50);
		InvisibleButton b7 = new InvisibleButton(x + 15, y + 337, 50, 50);

		f.add(searchbar);

		f.add(scrollbar);

		f.add(b1);
		f.add(b2);
		f.add(b3);
		f.add(b4);
		f.add(b5);
		f.add(b6);
		f.add(b7);
	}

	public void AssemblerLayout(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		Font f = g.getFont();
		g.setFont(Font.decode("Arial-BOLD-22"));
		g.drawString("Assembler", (700 / 2) - 10, 45);
		g.setFont(f);

		g.setColor(Color.GRAY);
		g.fillRect(50, 50, 700, 500);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(75, 75, 500, 50);
		g.fillRect(275, 175, 300, 300);
		g.fillRect(75, 600, 150, 300);
		g.fillRect(400, 487, 50, 50);
		ItemSelectionScreen(g, 600, 75);
		g.setColor(Color.DARK_GRAY);
		g.drawRect(50, 50, 700, 500);
		g.fillRect(75, 175, 150, 300);
		generateCanvas(75, 75, 500, 50, 50, g);
		generateCanvas(275, 175, 300, 300, 50, g);
		generateCanvas(400, 487, 50, 50, 50, g);
		g.setColor(Color.BLUE);
		g.drawRect(76 + (50 * ((AssemblerBuilding) Main.Interfaced)
				.getSelectedPosition()), 76, 48, 48);
		g.setColor(new Color(150, 150, 255));
		g.drawRect(77 + (50 * ((AssemblerBuilding) Main.Interfaced)
				.getSelectedPosition()), 77, 46, 46);

	}

	public void generateCanvas(int x, int y, int width, int height,
			int squareSize, Graphics g) {
		for (int x1 = x; x1 < x + width + 1; x1 += squareSize)
			g.drawLine(x1, y, x1, y + height);
		for (int y1 = y; y1 < y + height + 1; y1 += squareSize)
			g.drawLine(x, y1, x + width, y1);
	}
}
