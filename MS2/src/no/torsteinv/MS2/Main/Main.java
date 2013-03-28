package no.torsteinv.MS2.Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import no.torsteinv.MS2.AbstractMenu.AbstractElement;
import no.torsteinv.MS2.AbstractMenu.Form;
import no.torsteinv.MS2.AbstractMenu.Buttons.StandardButton;
import no.torsteinv.MS2.AbstractMenu.InputForms.StandardInputForm;
import no.torsteinv.MS2.AbstractMenu.Special.Scrollbar;
import no.torsteinv.MS2.Discovery.Dark;
import no.torsteinv.MS2.Discovery.DiscoverPointRound;
import no.torsteinv.MS2.Entities.Container;
import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Entities.Factory.FactoryElement;
import no.torsteinv.MS2.Entities.Items.BaseItem;
import no.torsteinv.MS2.Entities.Items.ItemStack;
import no.torsteinv.MS2.Entities.Movables.Movable;
import no.torsteinv.MS2.Entities.NatureObjects.IceLakes;
import no.torsteinv.MS2.Entities.NatureObjects.Mountain;
import no.torsteinv.MS2.Entities.NatureObjects.MountainType;
import no.torsteinv.MS2.Entities.NatureObjects.NatureObject;
import no.torsteinv.MS2.Entities.Sheets.Concrete;
import no.torsteinv.MS2.Game.Engine.GameLoading.Game;
import no.torsteinv.MS2.Game.Engine.Lists.FormList;
import no.torsteinv.MS2.Game.Engine.Others.PaintState;
import no.torsteinv.MS2.Game.Engine.Others.PlaceState;
import no.torsteinv.MS2.Game.MS2.Buildings.AssemblerBuilding;
import no.torsteinv.MS2.Game.MS2.Buildings.ContainerBuilding;
import no.torsteinv.MS2.Game.MS2.Buildings.FactoryBuilding;
import no.torsteinv.MS2.Game.MS2.Buildings.GunBuilding;
import no.torsteinv.MS2.Game.MS2.Buildings.MainBuilding;
import no.torsteinv.MS2.Game.MS2.FormGeneration.GameFormGeneration;
import no.torsteinv.MS2.Game.MS2.Items.MainItemList;
import no.torsteinv.MS2.Game.MS2.PaintStates.MainPaintStateList;
import no.torsteinv.MS2.Game.MS2.PlaceStates.MainPlaceStateList;
import no.torsteinv.MS2.Game.MS2.Vehicles.Explorer;
import no.torsteinv.MS2.Game.MS2.Vehicles.Transporter;
import no.torsteinv.MS2.Input.MouseHandler;
import no.torsteinv.MS2.Input.MouseMotionHandler;
import no.torsteinv.MS2.Main.Miscellaneous.Message;
import no.torsteinv.MS2.Main.Miscellaneous.UserSpecificVariables;
import no.torsteinv.MS2.Main.PostExcecution.Excecuter;
import no.torsteinv.MS2.Main.PostExcecution.BitSystem.BitDecypherer;
import no.torsteinv.MS2.Managers.Timing.Timer;
import no.torsteinv.MS2.Multiplayer.Server;
import no.torsteinv.MS2.Multiplayer.ServerClient;
import no.torsteinv.MS2.ParticleSystem.Emitter;
import no.torsteinv.MS2.ParticleSystem.ParticleSystem;
import no.torsteinv.MS2.ParticleSystem.Physics.Physics;
import no.torsteinv.MS2.Sound.Player;
import no.torsteinv.MS2.Sound.Sound;

/* TODO: Fix factory pipe and input transit.
 * TODO: GameEntity snap to grid. Shift for free placement.
 * TODO: AdvancedPipe addition
 * TODO: MOAR SUPPORT!!!!
 * TODO: Toggle switches needs right click to go back.
 * TODO: Pipe unselect not working
 */

/**
 * @author Torstein Vik
 * @version Beta 0.1
 */
public class Main {
	/*
	 * Player Formating
	 * 
	 * Special Indexes: 200: All 100 + Player Index: Ignore this player
	 * 
	 * All else (0 - 99) means this player only
	 */
	public static final int Width = 800;
	public static final int Height = 600;
	public static int player = 0;
	public static boolean multiplayer = false;
	public static ServerClient Client;
	public static Server server;
	public static boolean autofill = false;
	public static MouseHandler mouseHandler = null;

	public static UserSpecificVariables[] userSpecificVariables = null;
	public static CopyOnWriteArrayList<Entity> Entities = new CopyOnWriteArrayList<Entity>();
	public static CopyOnWriteArrayList<Message> Messages = new CopyOnWriteArrayList<Message>();
	public static HashMap<Entity, Emitter> Emitters = new HashMap<Entity, Emitter>();

	public static Dark Darkness = new Dark();

	public static float VerticalAlignment = 5000;
	public static float HorisontalAlignment = 5000;
	public static float zoom = 1;
	public static int Mouse_X = 0;
	public static int Mouse_Y = 0;
	public float aggregatorFuel = 0;

	public static boolean factory() {
		return PaintState == MainPaintStateList.Factory
				|| PaintState == MainPaintStateList.Crafter
				|| PaintState == MainPaintStateList.Input
				|| PaintState == MainPaintStateList.Machines;
	}

	public static boolean subFactory() {
		return PaintState == MainPaintStateList.Crafter
				|| PaintState == MainPaintStateList.Input;
	}

	public static boolean game() {
		return PaintState == MainPaintStateList.Game
				|| PaintState == MainPaintStateList.Explorer
				|| PaintState == MainPaintStateList.Transporter
				|| PaintState == MainPaintStateList.Gun;
	}

	public static boolean GUI() {
		return PaintState == MainPaintStateList.Factory
				|| PaintState == MainPaintStateList.MainGUI
				|| PaintState == MainPaintStateList.Container
				|| PaintState == MainPaintStateList.Assembler;
	}

	public static boolean FullGUI() {
		return PaintState == MainPaintStateList.Factory
				|| PaintState == MainPaintStateList.Crafter
				|| PaintState == MainPaintStateList.Input
				|| PaintState == MainPaintStateList.MainGUI
				|| PaintState == MainPaintStateList.Container
				|| PaintState == MainPaintStateList.Machines;
	}

	public static boolean menu() {
		return PaintState == MainPaintStateList.Host
				|| PaintState == MainPaintStateList.Join
				|| PaintState == MainPaintStateList.Lose
				|| PaintState == MainPaintStateList.MainMenu
				|| PaintState == MainPaintStateList.Options
				|| PaintState == MainPaintStateList.Pause
				|| PaintState == MainPaintStateList.SaveConfirm
				|| PaintState == MainPaintStateList.Singleplayer
				|| PaintState == MainPaintStateList.Win
				|| PaintState == MainPaintStateList.Generating
				|| PaintState == MainPaintStateList.Multiplayer;
	}

	public static boolean vehicle() {
		return PaintState == MainPaintStateList.Explorer
				|| PaintState == MainPaintStateList.Transporter;
	}

	public static enum PlaceStateTypes {
		None, Sheet, GameEntity, Factory, Pipe;
	}

	public static Image SolidColor(int w, int h, Color c) {
		Image img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();

		g.setColor(c);
		g.fillRect(0, 0, w, h);

		return img;
	}

	private static PaintState PaintState = MainPaintStateList.MainMenu;

	public static void setPaintState(PaintState paintState) {
		PaintState = paintState;
	}

	public static PaintState getPaintState() {
		return PaintState;
	}

	public static PlaceState PlaceState = MainPlaceStateList.None;
	public static boolean PlacingSheet = false;
	public static Rectangle PlacingRectangle = new Rectangle();
	public static Point PlacingOriginalPoint = new Point();
	public static Entity Interfaced = null;
	public static Main Access = null;
	public static boolean applet = false;
	public static double Loading_Progress = 0;

	public CopyOnWriteArrayList<Form> Forms = new CopyOnWriteArrayList<Form>();

	public void start(int players) {

		reset(players);
		multiplayer = players != 1;
		if (!multiplayer) {
			Thread th = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Generate();
						PaintState = MainPaintStateList.Game;
						Darkness.add(new DiscoverPointRound(
								((Point) userSpecificVariables[player].Properties
										.get("Spawn")).x,
								((Point) userSpecificVariables[player].Properties
										.get("Spawn")).y, 1000, -1337));
						VerticalAlignment = ((Point) Main.userSpecificVariables[Main.player].Properties
								.get("Spawn")).y - 300;
						HorisontalAlignment = ((Point) Main.userSpecificVariables[Main.player].Properties
								.get("Spawn")).x - 300;
						Loading_Progress = 0;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			});
			th.start();
		}

	}

	double ll = 0.148367;

	public void Progress() {
		Loading_Progress += this.ll;
	}

	public static String ProgressMessage = "Initializing generation";

	public void Progress(String message) {
		Loading_Progress += this.ll;
		ProgressMessage = message;
	}

	public int Progress_Steps() {
		return 674;
	}

	private void Generate() throws IOException {

		int x = 0;
		int y = 0;
		Random r = new Random();
		try {
			for (int i = 0; i < 325; i++) {
				boolean b = false;
				do {
					x = r.nextInt(10000);
					y = r.nextInt(10000);
					b = false;
					for (Entity m : Entities)
						if (m instanceof Mountain)
							if (new Rectangle(x, y, 50, 50)
									.intersects(new Rectangle((int) m.getX(),
											(int) m.getY(), 50, 50)))
								b = true;
				} while (b);
				Mountain m = new Mountain(x, y, MountainType.selectRandom());
				if (m.type == MountainType.Uran)
					ParticleSystem.addEmitter(ParticleSystem.UranMountain,
							(int) m.getX() + 25, (int) m.getY() + 25, m);
				addEntity(m);
				Progress("Adding mountains");
			}
			for (int i = 0; i < 325; i++) {
				boolean b;
				do {
					x = r.nextInt(10000);
					y = r.nextInt(10000);
					b = false;
					for (Entity m : Entities)
						if (m instanceof NatureObject)
							if (new Rectangle(x, y, 50, 50)
									.intersects(new Rectangle((int) m.getX(),
											(int) m.getY(), 50, 50)))
								b = true;
				} while (b);
				addEntity(new IceLakes(x, y));
				Progress("Adding icepockets");
			}
			int sx = r.nextInt(8000) + 1000;
			int sy = r.nextInt(8000) + 1000;
			setUserProperty("Spawn", new Point(sx, sy), Main.player);
			Progress("Creating Spawn");
			addEntity(new MainBuilding(sx, sy, 0));
			Progress();
			addEntity(new Concrete(sx - 100, sy - 100, 200, 200, 0));
			Progress("Removing nature around spawn");
			Rectangle rect = new Rectangle(sx - 100, sy - 100, 200, 200);
			for (Entity no : Main.Entities)
				if (no instanceof NatureObject
						&& new Rectangle((int) no.getX(), (int) no.getY(), 50,
								50).intersects(rect))
					try {
						removeEntity(no);
						if (no instanceof Mountain
								&& ((Mountain) no).type == MountainType.Uran)
							removeEmitter(Emitters.get(no));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			Progress("Removing nature around spawn");

			ContainerBuilding cb = new ContainerBuilding(sx - 50, sy, player);

			addEntity(cb);

			Progress("Adding items to start with");
			addItemStack(MainItemList.Fuel, 100, false);
			Progress();
			addItemStack(MainItemList.Stone, 100, false);
			Progress();
			addItemStack(MainItemList.Silicon, 100, false);
			Progress();
			addItemStack(MainItemList.Silicon_Alloy, 100, false);
			Progress();
			addItemStack(MainItemList.Silicon_Alloy_Type2, 100, false);
			Progress();
			addItemStack(MainItemList.Water, 100, false);
			Progress();
			addItemStack(MainItemList.Sand, 100, false);
			Progress();
			addItemStack(MainItemList.Iron_Dust, 100, false);
			Progress();
			addItemStack(MainItemList.Steel, 100, false);
			Progress();
			addItemStack(MainItemList.Iron_Machine_Base, 10, false);
			Progress();
			addItemStack(MainItemList.Igniter_Mechanism, 100, false);
			Progress();
			addItemStack(MainItemList.Bullet, 300, false);
			Progress();
			addItemStack(MainItemList.Iron_Tube, 300, false);
			Progress();
			addItemStack(MainItemList.Brick, 100, false);

			Progress("Adding Buildings");

			addEntity(new AssemblerBuilding(sx - 50, sy - 50, player));
			Progress();
			addEntity(new Explorer(sx + 100, sy + 100, player));
			Progress();
			addEntity(new Transporter(sx + 100, sy + 150, player));
			Progress();
			addEntity(new FactoryBuilding(sx, sy - 50, player));
			Progress();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reset(int players) {
		Darkness.discoverpoints.clear();
		Darkness.entities.clear();
		Entities.clear();
		Emitters.clear();
		this.aggregatorFuel = 0;
		userSpecificVariables = new UserSpecificVariables[players];
		PlaceState = MainPlaceStateList.None;
		autofill = false;
		PlacingSheet = false;
		PlacingRectangle = new Rectangle();
		PlacingOriginalPoint = new Point();
		Interfaced = null;
		for (int i = 0; i < players; i++)
			userSpecificVariables[i] = new UserSpecificVariables();
	}

	public Main() {
		Game.loadGames();
		Mountain.loadImages();
		if (Access == null)
			Access = this;
		FormList.ensureAdded();
	}

	public void Save() {

	}

	public static String getVersion() {
		return "Beta 0.1";
	}

	public void update() {
		if (!menu())
			updateGame();
	}

	int pipeUpdate = 0;

	private void updateGame() {
		if (this.pipeUpdate >= 2000) {
			for (Entity p : Main.Entities)
				if (p instanceof FactoryElement && p.getPlayer() == Main.player)
					((FactoryElement) p).transitAll();
			this.pipeUpdate = 0;
		} else {
			this.pipeUpdate += Timer.getDelta();
		}
		for (Entity v : Entities)
			if (v instanceof Movable)
				if (v.getPlayer() == player)
					((Movable) v).update();
		if (PaintState == MainPaintStateList.Gun
				&& Interfaced instanceof GunBuilding)
			GameFormGeneration.BulletCount.setText("Bullets : "
					+ ((GunBuilding) Interfaced).Properties.get("BulletCount"));
		for (Entity b : Entities)
			try {
				Object l = false;
				if (b instanceof GunBuilding
						&& ItemStack.has(new ItemStack(MainItemList.Bullet,
								new ContainerBuilding(Main.player, 0, 0),
								((((l = ((GunBuilding) b).Properties
										.get("Leave100")) == null ? false
										: (Boolean) l) ? 101 : 0)), 0, 0,false))) {
					try {
						removeItemStack(MainItemList.Bullet, 1);
						b.Properties.put("BulletCount",
								((int) b.Properties.get("BulletCount") + 1));
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		for (Entity ea : Entities)
			if (ea.isUpdateRequired())
				ea.onUpdate();
		Physics.update();
	}

	public void loopKeyInput(String s) {
		for (Form f : this.Forms)
			if (PaintState == f.BelongTo)
				for (AbstractElement ae : f.Elements)
					if (ae.FOCUS && ae instanceof StandardInputForm)
						if (s.equals("!BACKSPACE"))
							ae.setText(ae.getText().substring(0,
									ae.getText().length() - 1));
						else if (s.equals("!DONE")) {
							ae.FOCUS = false;
							ae.Handler.handle();
							try {
								Player.play(Sound.buttonClick);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else
							ae.setText(ae.getText() + s);
					else
						;
	}

	public void loopMouseMovement(int x, int y) {
		for (Form f : this.Forms)
			for (AbstractElement ae : f.Elements)
				if (new Rectangle(ae.getPos().x, ae.getPos().y,
						ae.getSize().width, ae.getSize().height).contains(x, y)
						&& (ae instanceof StandardButton ? (((StandardButton) ae).Active)
								: true))
					ae.HOVERED = true;
				else if (ae.HOVERED
						&& (ae instanceof StandardButton ? (((StandardButton) ae).Active)
								: true))
					ae.HOVERED = false;
		MouseMotionHandler.handle();
	}

	public void loopMouseClick(int x, int y) {
		for (Form f : this.Forms)
			for (AbstractElement ae : f.Elements)
				if (new Rectangle(ae.getPos().x, ae.getPos().y,
						ae.getSize().width, ae.getSize().height).contains(x, y)
						&& f.BelongTo.equals(PaintState))
					if (ae instanceof StandardInputForm) {
						ae.FOCUS = true;
						if (((StandardInputForm) ae).originalText) {
							ae.setText("");
							((StandardInputForm) ae).originalText = false;
						}
						return;
					} else if (ae instanceof StandardButton ? (((StandardButton) ae).Active)
							: true) {
						ae.Handler.handle();
						try {
							Player.play(Sound.buttonClick);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return;
					} else if (ae instanceof Scrollbar) {
						int relY = y - ae.getPos().y;
						((Scrollbar) ae).yAmount = relY;
						ae.Handler.handle();
					} else if (ae instanceof StandardInputForm && ae.FOCUS) {
						ae.FOCUS = false;
					}
	}

	public static void addItemStack(BaseItem Type, int quantity, boolean autoadd) {
		Container ish = (Container) findClosest(
				ContainerBuilding.class,
				((Point) userSpecificVariables[player].Properties.get("Spawn")).x,
				((Point) userSpecificVariables[player].Properties.get("Spawn")).y);
		addItemStack(Type, quantity, ish, autoadd);
	}

	public static void addItemStack(BaseItem Type, int quantity, Container ish,
			boolean autoadd) {
		int x = ItemStack.getPrefferdPosition(ish).x;
		int y = ItemStack.getPrefferdPosition(ish).y;
		addItemStack(Type, quantity, ish, x, y, autoadd);
	}

	public static void addItemStack(BaseItem Type, int quantity, Container ish,
			int x, int y, boolean autoadd) {
		try {
			addEntity(new ItemStack(Type, ish, quantity, x, y, autoadd));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void removeItemStack(BaseItem Type, int quantity)
			throws Exception {
		Container ish = ItemStack.findContainerWithTypeAndQuantityInContainer(
				Type, quantity);
		removeItemStack(Type, quantity, ish);
	}

	public static void removeItemStack(BaseItem Type, int quantity,
			Container ish) throws Exception {
		int x = getXOfItem(Type, quantity);
		int y = getYOfItem(Type, quantity);
		removeItemStack(Type, quantity, ish, x, y);
	}

	private static int getYOfItem(BaseItem type, int quantity) {
		return (int) ItemStack.findStackWithStuffInContainer(type, quantity)
				.getY();
	}

	private static int getXOfItem(BaseItem type, int quantity) {
		return (int) ItemStack.findStackWithStuffInContainer(type, quantity)
				.getX();
	}

	public static void removeItemStack(BaseItem Type, int quantity,
			Container ish, int x, int y) throws Exception {
		removeEntity(new ItemStack(Type, ish, quantity, x, y, false));
	}

	public static void addEntity(Entity e) throws Exception {
		sendPacket("!LADD -/Entity/" + e.toString() + "/200", true);
	}

	public static void removeEntity(Entity e) throws Exception {
		sendPacket("!LREMOVE -/Entity/" + e.toString() + "/200", true);
	}

	public static void setEntityProperty(Entity e, String propertyName,
			Object newPropertyValue) throws Exception {
		sendPacket("!LSET -/" + propertyName + "@"
				+ newPropertyValue.getClass().getName() + "/" + e.reference
				+ "/" + newPropertyValue.toString() + "/200", true);
	}

	public static void setEntityPropertyEntity(Entity e, String propertyName,
			Entity newEntityValue) throws Exception {
		sendPacket("!LASET -/" + propertyName + "@" + "/" + e.reference + "/"
				+ newEntityValue.toString() + "/200", true);
	}

	public static void addEmitter(Emitter e, Entity key) throws Exception {
		sendPacket("!LADD -/Emitter/" + e.toString() + "/" + key.reference
				+ "/200", true);
	}

	public static void removeEmitter(Emitter e) throws Exception {
		sendPacket("!LREMOVE -/Emitter/" + e.toString() + "/200", true);
	}

	public static void setUserProperty(String propertyName, Object value,
			int... players) throws Exception {
		sendPacket("!SET -/"
				+ propertyName
				+ "@"
				+ value.getClass().getName()
				+ "/"
				+ Entity.fromObject(value, false)
				+ "/"
				+ BitDecypherer.createBitSet(getHighestValue(players), players)
						.getData(), false);
	}

	public static void sendMessage(String msg, int... players) throws Exception {
		sendPacket(
				"!MSG -/"
						+ msg
						+ "/"
						+ BitDecypherer.createBitSet(getHighestValue(players),
								players).getData(), false);
	}

	public static int getHighestValue(int[] iar) {
		if (iar.length < 1)
			new Exception("Array must have atleast one entry!")
					.printStackTrace();
		int max = Integer.MIN_VALUE;
		for (int i : iar)
			if (i > max)
				max = i;
		return max;
	}

	/**
	 * 
	 * DO NOT USE!<br>
	 * <br>
	 * 
	 * This method should not be used as it may be removed.<br>
	 * Rather use other methods for sending information.<br>
	 * <br>
	 * 
	 * 
	 * @param msg
	 * @param oldPlayerSystem
	 * @throws Exception
	 */
	@Deprecated
	public static void sendPacket(String msg, boolean oldPlayerSystem)
			throws Exception {
		if (!multiplayer) {
			Excecuter.excecuteCommand(msg, oldPlayerSystem);
		} else {
			Client.Post(msg, oldPlayerSystem);
		}
	}

	public static Entity findClosestButPrioritizePropertyIsNot(
			Class<? extends Entity> clazz, int x, int y, String varname,
			Object isNot, int propertyImportance) {

		float max = Float.MAX_VALUE;
		Entity bestEntity = null;
		for (Entity e : Entities)
			if (clazz.isInstance(e)
					&& (Math.atan2(y < e.getY() ? e.getY() - y : y - e.getY(),
							x < e.getX() ? e.getX() - x : x - e.getX())
							+ (e.Properties.get(varname).equals(isNot) ? 0
									: propertyImportance) < max)) {
				bestEntity = e;
				max = (int) (Math.atan2(
						y < e.getY() ? e.getY() - y : y - e.getY(),
						x < e.getX() ? e.getX() - x : x - e.getX()) + (e.Properties
						.get(varname).equals(isNot) ? 0 : propertyImportance));
			}
		return bestEntity;
	}

	public static Entity findClosestWithPropertyIsNot(Class<Movable> clazz,
			int x, int y, String varname, Object isNot) {
		float max = Float.MAX_VALUE;
		Entity bestEntity = null;
		for (Entity e : Entities)
			if (clazz.isInstance(e)
					&& !e.Properties.get(varname).equals(isNot)
					&& Math.atan2(y < e.getY() ? e.getY() - y : y - e.getY(),
							x < e.getX() ? e.getX() - x : x - e.getX()) < max) {
				bestEntity = e;
				max = (int) (Math.atan2(
						y < e.getY() ? e.getY() - y : y - e.getY(),
						x < e.getX() ? e.getX() - x : x - e.getX()));
			}
		return bestEntity;
	}

	public static Entity findClosestButPrioritizeProperty(
			Class<? extends Entity> clazz, int x, int y, String varname,
			Object is, int propertyImportance) {

		float max = Float.MAX_VALUE;
		Entity bestEntity = null;
		for (Entity e : Entities)
			if (clazz.isInstance(e)
					&& (Math.atan2(y < e.getY() ? e.getY() - y : y - e.getY(),
							x < e.getX() ? e.getX() - x : x - e.getX())
							+ (e.Properties.get(varname).equals(is) ? propertyImportance
									: 0) < max)) {
				bestEntity = e;
				max = (int) (Math.atan2(
						y < e.getY() ? e.getY() - y : y - e.getY(),
						x < e.getX() ? e.getX() - x : x - e.getX()) + (e.Properties
						.get(varname).equals(is) ? propertyImportance : 0));
			}
		return bestEntity;
	}

	public static Entity findClosestWithProperty(Class<Movable> clazz, int x,
			int y, String varname, Object is) {
		float max = Float.MAX_VALUE;
		Entity bestEntity = null;
		for (Entity e : Entities)
			if (clazz.isInstance(e)
					&& e.Properties.get(varname).equals(is)
					&& (Math.atan2(y < e.getY() ? e.getY() - y : y - e.getY(),
							x < e.getX() ? e.getX() - x : x - e.getX()) < max)) {
				bestEntity = e;
				max = (int) Math.atan2(
						y < e.getY() ? e.getY() - y : y - e.getY(),
						x < e.getX() ? e.getX() - x : x - e.getX());
			}
		return bestEntity;
	}

	public static Entity findClosest(Class<? extends Entity> clazz, float x,
			float y) {
		float max = Float.MAX_VALUE;
		Entity bestEntity = null;
		for (Entity e : Entities)
			if (clazz.isInstance(e)
					&& Math.atan2(y < e.getY() ? e.getY() - y : y - e.getY(),
							x < e.getX() ? e.getX() - x : x - e.getX()) < max) {
				bestEntity = e;
				max = (int) Math.atan2(
						y < e.getY() ? e.getY() - y : y - e.getY(),
						x < e.getX() ? e.getX() - x : x - e.getX());
			}
		return bestEntity;
	}

	public static Entity getEntityAt(int i, int j) {
		for (Entity e : Entities)
			if (e.getX() - HorisontalAlignment == i
					&& e.getY() - VerticalAlignment == j)
				return e;
		return null;
	}
}
