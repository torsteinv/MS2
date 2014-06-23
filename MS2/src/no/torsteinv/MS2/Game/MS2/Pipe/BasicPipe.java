package no.torsteinv.MS2.Game.MS2.Pipe;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

import no.torsteinv.MS2.Annotations.CostMethod;
import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Selectable;
import no.torsteinv.MS2.Entities.Factory.FactoryElement;
import no.torsteinv.MS2.Entities.Factory.Pipes.Pipe;
import no.torsteinv.MS2.Entities.Items.BaseItem;
import no.torsteinv.MS2.Entities.Items.ItemStack;
import no.torsteinv.MS2.Entities.Movables.Command;
import no.torsteinv.MS2.Entities.Movables.CommandType;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.Engine.Others.PlacementRestriction;
import no.torsteinv.MS2.Game.MS2.Buildings.FactoryBuilding;
import no.torsteinv.MS2.Game.MS2.Items.MainItemList;
import no.torsteinv.MS2.Main.Main;

public class BasicPipe extends Pipe implements Selectable {
	// up, down, left, right
	public boolean[] dir = { false, false, false, false };

	public static Image[][] Pipes = {
			{ img(0, 0), img(0, 1), img(0, 2), img(0, 3) },
			{ img(1, 0), img(1, 1), img(1, 2), img(1, 3) },
			{ img(2, 0), img(2, 1), img(2, 2), img(2, 3) },
			{ img(3, 0), img(3, 1), img(3, 2), img(3, 3) } };

	public static Image img(int i, int y) {
		try {
			if (!Main.applet)
				return ImageIO.read(new File("images/Pipes.png")).getSubimage(
						50 * i, y * 50, 50, 50);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public BasicPipe(int X, int Y, int Player, FactoryBuilding Home) {
		super(X, Y, Player);
		this.Properties.put("CanvasX", X);
		this.Properties.put("CanvasY", Y);
		this.Properties.put("Player", Player);
		this.Entities.put("Home", Home);
		generateDirections();
		updateOthersDirections();
	}

	public BasicPipe(HashMap<String, Object> Properties,
			HashMap<String, Entity> Entities) {
		super(Properties, Entities);
	}

	public static void updateOthersDirections() {
		for (Entity p : Main.Entities)
			if (p instanceof BasicPipe)
				((BasicPipe) p).generateDirections();
	}

	private void generateDirections() {
		this.dir[0] = false;
		this.dir[1] = false;
		this.dir[2] = false;
		this.dir[3] = false;
		for (Entity p : Main.Entities) {
			if (p instanceof FactoryElement
					&& ((FactoryElement) p).getHome().equals(getHome())
					&& p.getPlayer() == getPlayer()) {
				if (((FactoryElement) p).getCanvasX() == getCanvasX()
						&& ((FactoryElement) p).getCanvasY() - 1 == getCanvasY())
					this.dir[1] = true;
				else if (((FactoryElement) p).getCanvasX() == getCanvasX()
						&& ((FactoryElement) p).getCanvasY() + 1 == getCanvasY())
					this.dir[0] = true;
				else if (((FactoryElement) p).getCanvasX() + 1 == getCanvasX()
						&& ((FactoryElement) p).getCanvasY() == getCanvasY())
					this.dir[2] = true;
				else if (((FactoryElement) p).getCanvasX() - 1 == getCanvasX()
						&& ((FactoryElement) p).getCanvasY() == getCanvasY())
					this.dir[3] = true;
			}
		}
	}

	public Image GenerateTexture() {
		Image[] rd = Pipes[0];
		Image[] rc = Pipes[1];
		Image[] rt = Pipes[2];
		Image[] ra = Pipes[3];
		int dirs = 0;
		for (int i = 0; i < 4; i++)
			if (this.dir[i])
				dirs++;
		if (dirs == 4)
			return ra[0];
		if (dirs == 3) {
			return rt[!this.dir[0] ? 1 : !this.dir[1] ? 3 : !this.dir[2] ? 0
					: 2];
		}
		if (dirs == 2) {
			if ((this.dir[0] && this.dir[1]) || (this.dir[2] && this.dir[3])) {
				return rd[!this.dir[0] ? 0 : 1];
			} else {
				return rc[!this.dir[0] && !this.dir[2] ? 0 : !this.dir[0]
						&& !this.dir[3] ? 3 : !this.dir[1] && !this.dir[2] ? 1
						: 2];
			}
		}
		if (dirs == 1) {
			return rd[!this.dir[3] && !this.dir[2] ? 1 : 0];
		}
		return rd[0];
	}

	public static boolean CanPlace(int x, int y) {
		boolean b = new Rectangle(50, 50, 500, 500).contains(new Point(x, y));
		boolean u = true;
		for (Entity p : Main.Entities)
			if (p instanceof FactoryElement
					&& ((FactoryElement) p).getCanvasX() == ((x - 50) / 50)
					&& ((FactoryElement) p).getCanvasY() == ((y - 50) / 50))
				u = false;

		return b && u;
	}

	public enum Direction {
		None(-1), Up(0), Down(1), Left(2), Right(3);

		public int dir = 2;

		public int getDirectrionInteger() {
			return this.dir;
		}

		public static Direction valueOf(int i) {
			switch (i) {
			case 0:
				return Up;
			case 1:
				return Down;
			case 2:
				return Left;
			case 3:
				return Right;
			default:
				return None;
			}
		}

		public static Direction valueOf(int xPos, int yPos, int xDir, int yDir) {
			int xdif = xDir - xPos;
			int ydif = yDir - yPos;

			switch (xdif + 10 * ydif) {
			case 1:
				return Right;
			case -1:
				return Left;
			case 10:
				return Down;
			case -10:
				return Up;
			default:
				return None;
			}
		}

		Direction(int dir) {
			this.dir = dir;
		}
	}

	@Override
	public void transitAll() {
		for (ItemStack e : getContent()) {
			Direction from = Direction.valueOf(getFrom());
			HashMap<Direction, FactoryElement> adjacentElements = new HashMap<Direction, FactoryElement>();
			for (Entity e1 : Main.Entities) {
				if (!(e1 instanceof FactoryElement))
					continue;
				FactoryElement fe = (FactoryElement) e1;

				if (fe.getHome().equals(getHome()) && !fe.equals(this)
						&& isAdjacentTo(fe))
					adjacentElements
							.put(Direction.valueOf((int) getX(), (int) getY(),
									(int) fe.getX(), (int) fe.getY()), fe);
			}

			if (adjacentElements.size() < 2)
				continue;
			Direction d = genRandomDir(from,
					adjacentElements.keySet().toArray(new Direction[] {}));
			try {
				adjacentElements.get(d).addContent(e);
				Main.setEntityProperty(adjacentElements.get(d), "From",
						d.getDirectrionInteger());
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	private boolean isAdjacentTo(FactoryElement fe) {
		int Xdif = fe.getCanvasX() - getCanvasX();
		int Ydif = fe.getCanvasY() - getCanvasY();

		if (((Xdif != 0) && (Ydif == 0)) || ((Ydif != 0) && (Xdif == 0)))
			return true;

		return false;
	}

	private static Direction genRandomDir(Direction from,
			Direction... direction) {
		if (direction.length >= 3)
			return from;
		Random r = new Random();
		int ri = r.nextInt(4);
		for (Direction i : direction)
			if (i.getDirectrionInteger() == ri)
				return genRandomDir(from, direction);
		return Direction.valueOf(ri);

	}

	@Override
	public Image getTexture() {
		return Pipes[0][0];
	}

	@Override
	public Class<? extends Entity> getIdentification() {
		return getClass();
	}

	@Override
	public Color decodeColor() {
		return Color.BLACK;
	}

	@CostMethod
	public static HashMap<BaseItem, Integer> getCost() {
		HashMap<BaseItem, Integer> hm = new HashMap<BaseItem, Integer>();
		hm.put(MainItemList.Iron_Tube, 1);
		return hm;
	}

	@Override
	public Command getCommandCenter() {
		return null;
	}

	@Override
	public Rectangle getSelectionBox() {
		return new Rectangle((int) getX(), (int) getY(), 1, 1);
	}

	@Override
	public boolean isSelected() {
		return false;
	}

	@Override
	public void setSelected(boolean Selected) {

	}

	@Override
	public PaintState getAssosiatedInterface() {
		return null;
	}

	@Override
	public CommandType CommandAt(int i, int j, boolean leftClick) {
		return CommandType.None;
	}

	@Override
	public PlacementRestriction getRestrictions() {
		return PlacementRestriction.NON_OVERLAP_FACTORY;
	}

}
