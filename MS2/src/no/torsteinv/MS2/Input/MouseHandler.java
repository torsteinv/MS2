package no.torsteinv.MS2.Input;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Placeable;
import no.torsteinv.MS2.Entities.PlaceableBuyer;
import no.torsteinv.MS2.Entities.Selectable;
import no.torsteinv.MS2.Entities.Buildings.Building;
import no.torsteinv.MS2.Entities.Buildings.ClickableBuilding;
import no.torsteinv.MS2.Entities.Factory.FactoryElement;
import no.torsteinv.MS2.Entities.Factory.Buildings.Machine;
import no.torsteinv.MS2.Entities.NatureObjects.NatureObject;
import no.torsteinv.MS2.Entities.Sheets.Concrete;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.MS2.Items.MainItemList;
import no.torsteinv.MS2.Game.MS2.PaintStates.MainPaintStateList;
import no.torsteinv.MS2.Game.MS2.Pipe.BasicPipe;
import no.torsteinv.MS2.Game.MS2.PlaceStates.MainPlaceStateList;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Main.Main.PlaceStateTypes;
import no.torsteinv.MS2.Sound.Player;
import no.torsteinv.MS2.Sound.Sound;

public class MouseHandler implements MouseListener {

	public int cooldown = 0;

	@Override
	public void mouseClicked(MouseEvent e) {
		if (this.cooldown > 0) {
			this.cooldown--;
			return;
		}
		if (e.getButton() == MouseEvent.BUTTON1) {
			Main.Access.loopMouseClick(e.getX(), e.getY());
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			BasicPipe.updateOthersDirections();
			for (Entity p : Main.Entities)
				if (p instanceof FactoryElement
						&& ((FactoryElement) p).getCanvasX() == (e.getX() - 50) / 50
						&& ((FactoryElement) p).getCanvasY() == (e.getY() - 50) / 50) {

					try {
						Main.removeEntity(p);
						BasicPipe.updateOthersDirections();
					} catch (Exception e1) {
						e1.printStackTrace();
						try {
							Main.sendMessage("Could not remove item!",
									Main.player);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			Main.PlaceState = MainPlaceStateList.None;
		}
		handle(e.getX(), e.getY(), e.getButton() == MouseEvent.BUTTON1, e);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (Main.PlaceState.Type == Main.PlaceStateTypes.Sheet) {
			Main.PlacingSheet = true;
			Main.PlacingRectangle.setLocation(e.getX(), e.getY());
			Main.PlacingOriginalPoint.setLocation(e.getX(), e.getY());
		}
	}

	Rectangle r = new Rectangle();

	@Override
	public void mouseReleased(MouseEvent e) {
		this.r.setBounds(Main.PlacingRectangle);
		if (Main.PlaceState.Type == Main.PlaceStateTypes.Sheet
				&& Concrete.canBePlaced(this.r.x
						+ (int) Main.HorisontalAlignment, this.r.y
						+ (int) Main.VerticalAlignment, this.r.width,
						this.r.height)) {
			try {
				Main.removeItemStack(MainItemList.Stone, Concrete
						.calcultePrize(this.r.x
								+ (int) Main.HorisontalAlignment, this.r.y
								+ (int) Main.VerticalAlignment, this.r.width,
								this.r.height));
				Main.addEntity(new Concrete(this.r.x
						+ (int) Main.HorisontalAlignment, this.r.y
						+ (int) Main.VerticalAlignment, this.r.width,
						this.r.height, Main.player));
				this.r.setLocation(this.r.x + (int) Main.HorisontalAlignment,
						this.r.y + (int) Main.VerticalAlignment);
				for (Entity no : Main.Entities)
					if (no instanceof NatureObject
							&& new Rectangle((int) no.getX(), (int) no.getY(),
									50, 50).intersects(this.r))
						try {
							Main.removeEntity(no);
							Main.removeEmitter(Main.Emitters.get(no));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
			} catch (Exception e1) {
				try {
					Main.sendMessage("Can not afford!", Main.player);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			Main.PlacingSheet = false;
			Main.PlaceState = MainPlaceStateList.None;
		}
	}

	static PaintState prev01 = MainPaintStateList.Game;
	static Entity prev01INTR = null;

	static boolean isOtherSelected(Selectable s) {
		for (Entity e : Main.Entities)
			if (e instanceof Selectable && ((Selectable) e).isSelected()
					&& !e.equals(s))
				return true;
		return false;
	}

	static void unSelectOthers(Selectable s) {
		for (Entity e : Main.Entities)
			if (e instanceof Selectable && !e.equals(s))
				((Selectable) e).setSelected(false);
	}

	public static void handle(int x, int y, boolean RL, MouseEvent me) {

		if (Select(x, y, RL, me))
			return;

		if (PerformCommand(x, y, RL, me))
			return;

		if (SelectionClearing(x, y, RL, me))
			return;

		if (BuildingOnClick(x, y, RL, me))
			return;

		if (FactoryOnClick(x, y, RL, me))
			return;

		if (PlaceShit(x, y, RL, me))
			return;

		if (CameraAlignmentShifting(x, y, RL, me))
			return;

		BasicPipe.updateOthersDirections();
	}

	public static boolean CameraAlignmentShifting(int x, int y, boolean RL,
			MouseEvent me) {
		if (new Rectangle(0, 0, 200, 200).contains(new Point(x, y))
				&& Main.game()) {
			Main.HorisontalAlignment = (x * 50) - 250;
			Main.VerticalAlignment = (y * 50) - 250;
			return true;
		}
		return false;
	}

	public static boolean Select(int x, int y, boolean RL, MouseEvent me) {
		if ((new Rectangle(0, 0, 600, 600).contains(new Point(x, y)) && !(new Rectangle(
				0, 0, 200, 200).contains(new Point(x, y)))) && (Main.game()))
			for (Entity v : Main.Entities)
				if (v instanceof Selectable
						&& v.getPlayer() == Main.player
						&& ((Selectable) v).getSelectionBox().contains(
								new Point(x + (int) Main.HorisontalAlignment,
										y + (int) Main.VerticalAlignment))) {
					if (!me.isControlDown() && isOtherSelected((Selectable) v))
						unSelectOthers((Selectable) v);
					((Selectable) v)
							.setSelected(!((Selectable) v).isSelected());
					if (((Selectable) v).isSelected()) {
						prev01 = Main.getPaintState();
						prev01INTR = Main.Interfaced;
						Main.setPaintState(((Selectable) v)
								.getAssosiatedInterface());
						Main.Interfaced = v;
						return true;
					} else {
						Main.setPaintState(prev01);
						Main.Interfaced = prev01INTR;
					}
					try {
						Player.play(Sound.buttonClick);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		return false;
	}

	public static boolean PerformCommand(int x, int y, boolean RL, MouseEvent me) {
		if ((new Rectangle(0, 0, 600, 600).contains(new Point(x, y)) && !(new Rectangle(
				0, 0, 200, 200).contains(new Point(x, y))))
				&& (Main.game())
				&& !RL) {
			for (Entity e : Main.Entities)
				if (e instanceof Selectable && e.getPlayer() == Main.player
						&& ((Selectable) e).isSelected() && Main.game()) {
					((Selectable) e).getCommandCenter().Perform(
							((Selectable) e).CommandAt(x
									+ (int) Main.HorisontalAlignment, y
									+ (int) Main.VerticalAlignment), "X",
							"" + (int) (x + Main.HorisontalAlignment), "Y",
							"" + (int) (y + Main.VerticalAlignment));
					try {
						Player.play(Sound.buttonClick);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					return true;
				} else
					;
		} else if (new Rectangle(0, 0, 200, 200).contains(new Point(x, y))
				&& Main.game() && !RL) {
			int ax = x * 50;
			int ay = y * 50;
			for (Entity e : Main.Entities)
				if (e instanceof Selectable && e.getPlayer() == Main.player
						&& ((Selectable) e).isSelected() && Main.game()) {
					((Selectable) e).getCommandCenter().Perform(
							((Selectable) e).CommandAt(ax, ay), "X", "" + ax,
							"Y", "" + ay);
					try {
						Player.play(Sound.buttonClick);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					return true;
				}
		}
		return false;
	}

	public static boolean SelectionClearing(int x, int y, boolean RL,
			MouseEvent me) {
		if ((new Rectangle(0, 0, 600, 600).contains(new Point(x, y)) && !(new Rectangle(
				0, 0, 200, 200).contains(new Point(x, y)))) && Main.game())
			for (Entity m : Main.Entities)
				if (m instanceof Selectable) {
					((Selectable) m).setSelected(false);
					Main.setPaintState(MainPaintStateList.Game);
					Main.Interfaced = null;
				}
		return false;
	}

	public static boolean BuildingOnClick(int x, int y, boolean RL,
			MouseEvent me) {
		if ((new Rectangle(0, 0, 600, 600).contains(new Point(x, y)) && !(new Rectangle(
				0, 0, 200, 200).contains(new Point(x, y)))) && (Main.game()))
			for (Entity b : Main.Entities)
				if (b instanceof ClickableBuilding
						&& b.getPlayer() == Main.player
						&& ((Building) b).getSelectionBox().contains(
								new Point(x + (int) Main.HorisontalAlignment,
										y + (int) Main.VerticalAlignment))) {
					((ClickableBuilding) b).OnClick();
					Main.Interfaced = b;
					try {
						Player.play(Sound.buttonClick);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return true;
				}
		return false;
	}

	public static boolean FactoryOnClick(int x, int y, boolean RL, MouseEvent me) {
		if ((new Rectangle(50, 50, 500, 500).contains(new Point(x, y)))
				&& Main.factory())
			for (Entity fe : Main.Entities)
				if (fe instanceof FactoryElement
						&& fe.getPlayer() == Main.player
						&& new Rectangle(
								(((FactoryElement) fe).getCanvasX() * 50) + 50,
								(((FactoryElement) fe).getCanvasY() * 50) + 50,
								50, 50).contains(new Point(x, y))
						&& fe instanceof Machine) {
					((Machine) fe).OnClick();
					try {
						Player.play(Sound.buttonClick);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return true;
				}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static boolean PlaceShit(int x, int y, boolean RL, MouseEvent me) {
		if (!RL)
			return false;
		if (Main.game()
				&& new Rectangle(0, 0, 200, 200).contains(new Point(x, y)))
			return false;
		if (Main.PlaceState.Type == PlaceStateTypes.GameEntity && Main.game()
				&& new Rectangle(0, 0, 600, 600).contains(new Point(x, y))
				&& !new Rectangle(0, 0, 200, 200).contains(new Point(x, y))) {

			if (Main.PlaceState.placementRestricion.check(x, y,
					Main.PlaceState.instance)) {

				Entity e = null;
				try {
					e = (Entity) Main.PlaceState.instance.getClass()
							.getMethod("create", Class.class)
							.invoke(null, Main.PlaceState.instance.getClass());
				} catch (IllegalAccessException e2) {
					e2.printStackTrace();
				} catch (IllegalArgumentException e2) {
					e2.printStackTrace();
				} catch (InvocationTargetException e2) {
					e2.printStackTrace();
				} catch (NoSuchMethodException e2) {
					e2.printStackTrace();
				} catch (SecurityException e2) {
					e2.printStackTrace();
				}

				e.setPlayer(Main.player);
				e.setX(x + Main.HorisontalAlignment - 25);
				e.setY(y + Main.VerticalAlignment - 25);
				e.Properties.put("Texture",
						Main.PlaceState.instance.getTexture());
				if (e instanceof Selectable) {
					((Selectable) e).setSelected(false);
					e.Properties.put("Hitbox",
							((Selectable) Main.PlaceState.instance)
									.getSelectionBox());
					e.Properties.put("CommandCenter",
							((Selectable) Main.PlaceState.instance)
									.getCommandCenter());
				}

				try {
					PlaceableBuyer.buy((Class<? extends Placeable>) e
							.getClass());
					Main.addEntity(e);
					((Placeable) e).OnPlaceBought(x
							+ (int) Main.HorisontalAlignment - 25, y
							+ (int) Main.VerticalAlignment - 25);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				try {
					Player.play(Sound.buttonClick);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			return true;
		} else if (Main.PlaceState.Type == PlaceStateTypes.Factory
				|| Main.PlaceState.Type == PlaceStateTypes.Pipe
				&& Main.factory()
				&& new Rectangle(50, 50, 550, 550).contains(new Point(x, y))) {

			if (Main.PlaceState.placementRestricion.check(x, y,
					Main.PlaceState.instance)) {

				Entity e = null;
				try {
					e = (Entity) Main.PlaceState.instance.getClass()
							.getMethod("create", Class.class)
							.invoke(null, Main.PlaceState.instance.getClass());
				} catch (IllegalAccessException e2) {
					e2.printStackTrace();
				} catch (IllegalArgumentException e2) {
					e2.printStackTrace();
				} catch (InvocationTargetException e2) {
					e2.printStackTrace();
				} catch (NoSuchMethodException e2) {
					e2.printStackTrace();
				} catch (SecurityException e2) {
					e2.printStackTrace();
				}

				e.setPlayer(Main.player);
				e.setX((x - 50) / 50);
				e.setY((y - 50) / 50);
				e.Properties.put("Texture",
						Main.PlaceState.instance.getTexture());
				e.Entities.put(
						"Home",
						Main.subFactory() ? ((FactoryElement) Main.Interfaced)
								.getHome() : Main.Interfaced);

				try {
					PlaceableBuyer.buy((Class<? extends Placeable>) e
							.getClass());
					((Placeable) e).OnPlaceBought((x - 50) / 50, (y - 50) / 50);
					Main.addEntity(e);
				} catch (Exception e2) {
					try {
						Main.sendMessage("Can't afford!", Main.player);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				try {
					Player.play(Sound.buttonClick);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			BasicPipe.updateOthersDirections();
			return true;
		}
		// Mouse Pressed and Released takes care of sheets
		return false;
	}
}