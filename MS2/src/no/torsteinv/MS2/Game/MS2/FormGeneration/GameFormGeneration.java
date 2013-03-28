package no.torsteinv.MS2.Game.MS2.FormGeneration;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;

import no.torsteinv.MS2.AbstractMenu.ActionHandler;
import no.torsteinv.MS2.AbstractMenu.Form;
import no.torsteinv.MS2.AbstractMenu.Buttons.InvisibleButton;
import no.torsteinv.MS2.AbstractMenu.Buttons.StandardButton;
import no.torsteinv.MS2.AbstractMenu.Buttons.ToggleButton;
import no.torsteinv.MS2.AbstractMenu.InputForms.StandardInputForm;
import no.torsteinv.MS2.AbstractMenu.Labels.StandardLabel;
import no.torsteinv.MS2.Entities.Container;
import no.torsteinv.MS2.Entities.Factory.Template;
import no.torsteinv.MS2.Entities.Factory.TemplateDecoder;
import no.torsteinv.MS2.Entities.Factory.TemplateUser;
import no.torsteinv.MS2.Entities.Items.BaseItem;
import no.torsteinv.MS2.Entities.Items.ItemStack;
import no.torsteinv.MS2.Entities.Items.Recipe;
import no.torsteinv.MS2.Entities.Items.RecipeAction;
import no.torsteinv.MS2.Entities.Movables.CommandType;
import no.torsteinv.MS2.Entities.Movables.Vehicles.Vehicle;
import no.torsteinv.MS2.Entities.Sheets.Concrete;
import no.torsteinv.MS2.Game.Engine.Lists.FormList;
import no.torsteinv.MS2.Game.Engine.Lists.ItemList;
import no.torsteinv.MS2.Game.Engine.Lists.PlaceStateList;
import no.torsteinv.MS2.Game.Engine.Others.PaintStateHandler;
import no.torsteinv.MS2.Game.MS2.Buildings.AssemblerBuilding;
import no.torsteinv.MS2.Game.MS2.Buildings.ContainerBuilding;
import no.torsteinv.MS2.Game.MS2.Buildings.FactoryBuilding;
import no.torsteinv.MS2.Game.MS2.Buildings.GunBuilding;
import no.torsteinv.MS2.Game.MS2.Buildings.QuarryBuilding;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.Burner;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.Compressor;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.Crafter;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.InputMachine;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.Macerator;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.Mixer;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.OutputMachine;
import no.torsteinv.MS2.Game.MS2.Factory.Buildings.Refiner;
import no.torsteinv.MS2.Game.MS2.Items.Item;
import no.torsteinv.MS2.Game.MS2.Items.MainItemList;
import no.torsteinv.MS2.Game.MS2.PaintStates.MainPaintStateList;
import no.torsteinv.MS2.Game.MS2.Pipe.AdvancedPipe;
import no.torsteinv.MS2.Game.MS2.Pipe.BasicPipe;
import no.torsteinv.MS2.Game.MS2.PlaceStates.MainPlaceStateList;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Main.PostExcecution.BitSystem.BitDecypherer;

public class GameFormGeneration extends FormList {
	public static StandardButton FuelButton;
	public static StandardButton AutoFillButton;

	public static StandardButton ToggleItems;
	public static StandardLabel quantityText;

	public static StandardLabel BulletCount = new StandardLabel(new Point(
			Main.Width - 190, positionByIndex(0) + 10), "Bullets : 0",
			new Dimension(180, 20));

	public static StandardButton toggleleave100 = new StandardButton(new Point(
			Main.Width - 75, positionByIndex(1) - 20), "Yes", new Dimension(65,
			20), "Leave 100 bullets in the Container?");

	@Override
	@SuppressWarnings("unused")
	public void initForms() {
		int w = Main.Width;
		int h = Main.Height;
		Form Game1 = new Form(MainPaintStateList.Game);

		StandardButton ConcreateButton = new StandardButton(new Point(w - 190,
				positionByIndex(0)), "Concreate", new Dimension(180, 40),
				"Base for all buildings, " + Concrete.PrizePerPixel * (50 * 50)
						+ " Stone pr. square.");

		ConcreateButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.PlaceState = MainPlaceStateList.Concrete;
			}

		};

		Game1.add(ConcreateButton);

		FuelButton = new StandardButton(new Point(w - 190, positionByIndex(1)),
				"Fill With Fuel", new Dimension(180, 40),
				"Fill your tank on the left side of your screen. Costs 1 can of fuel.");

		FuelButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				try {
					if (ItemStack.has(new ItemStack(MainItemList.Fuel,
							(Container) Main.findClosest(
									ContainerBuilding.class,
									Main.userSpecificVariables[Main.player]
											.getSpawn().x,
									Main.userSpecificVariables[Main.player]
											.getSpawn().y), 1, 0, 0,false))
							&& Main.Access.aggregatorFuel - 50 > 0) {
						Main.removeItemStack(MainItemList.Fuel, 1);
						Main.Access.aggregatorFuel -= 50;
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		};

		Game1.add(FuelButton);

		final StandardInputForm ChatForm = new StandardInputForm(new Point(100,
				555), "Chat...", new Dimension(180, 20), "You can chat here.");

		ChatForm.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				try {
					Main.sendMessage(
							"Player " + Main.player + ": " + ChatForm.getText(),
							BitDecypherer.ALL);
				} catch (Exception e) {
					e.printStackTrace();
					try {
						Main.sendMessage("Could not send mesage!", Main.player);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				ChatForm.FOCUS = false;
				ChatForm.setText("");
			}

		};

		Game1.add(ChatForm);

		new GunBuilding(0, 0, 0);

		StandardButton GunBuildingButton = (StandardButton) getButtonForPlaceState(
				"Gun Building",
				new Point(w - 190, positionByIndex(2)),
				"Gun Building. Shoots enemies. Costs: "
						+ generateStringForFactoryButtons(GunBuilding.cost()),
				MainPlaceStateList.forName("GunBuilding")).setColor(
				Color.YELLOW.darker()).setSize(new Dimension(180, 40));

		StandardButton QuarryBuildingButton = (StandardButton) getButtonForPlaceState(
				"Quarry Building",
				new Point(w - 190, positionByIndex(3)),
				"Quarry Building. Digs up sand. Costs: "
						+ generateStringForFactoryButtons(QuarryBuilding.cost()),
				MainPlaceStateList.forName("QuarryBuilding")).setColor(
				Color.YELLOW.darker()).setSize(new Dimension(180, 40));

		Game1.add(GunBuildingButton);
		Game1.add(QuarryBuildingButton);

		StandardButton GUIButton = new StandardButton(
				new Point(w - 190, positionByIndex(10)),
				"GUI",
				new Dimension(180, 40),
				"Open the screen for the main building. There you can manage fuel and make stuff.");

		GUIButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.setPaintState(MainPaintStateList.MainGUI);
			}

		};

		Game1.add(GUIButton);

		// LOSE
		Form LoseGUI = new Form(MainPaintStateList.Lose);

		StandardButton RestartLost = new StandardButton(new Point(
				800 / 2 - 200, 600 / 2 - 25), "Restart?",
				new Dimension(400, 50), "Click to restart.");

		RestartLost.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.setPaintState(MainPaintStateList.MainMenu);
				Main.Access.reset(200);
			}

		};

		LoseGUI.add(RestartLost);

		// Main GUI
		Form MainGUI = new Form(MainPaintStateList.MainGUI);

		AutoFillButton = new StandardButton(new Point(50, 554),
				"Autofill fuel", new Dimension(180, 40),
				"Click to start automatic refill of fuel. Costs 100$");

		AutoFillButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				if (AutoFillButton.getText().equals("Autofill fuel - 100$")) {
					if (Main.userSpecificVariables[Main.player].getMoney() > 100) {
						try {
							Main.setUserProperty("Money",
									Main.userSpecificVariables[Main.player]
											.getMoney() - 100, Main.player);

						} catch (Exception e) {
							e.printStackTrace();
						}
						Main.autofill = true;
						AutoFillButton.setText("Refund 90$ (Stop autofill)");
					}
				} else {
					try {
						Main.setUserProperty("Money",
								Main.userSpecificVariables[Main.player]
										.getMoney() + 90, Main.player);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Main.autofill = false;
					AutoFillButton.setText("Autofill fuel - 100$");
				}
			}

		};

		MainGUI.add(AutoFillButton);
		int inb = 0;
		int inr = 0;
		int inm = 0;
		int inc = 0;
		for (final BaseItem i : Item.ItemList)
			if (i != null) {
				if (i.recipe == Recipe.NULL
						|| !(i.recipe.Action == RecipeAction.Burn
								|| i.recipe.Action == RecipeAction.Compress
								|| i.recipe.Action == RecipeAction.Mix || i.recipe.Action == RecipeAction.Refine))
					continue;
				StandardButton b = new StandardButton(
						positionByIndexAction(
								i.recipe.Action == RecipeAction.Burn ? inb
										: i.recipe.Action == RecipeAction.Compress ? inc
												: i.recipe.Action == RecipeAction.Refine ? inr
														: i.recipe.Action == RecipeAction.Mix ? inm
																: 0,
								i.recipe.Action), i.recipe.Result
								+ " "
								+ ItemList.getName(i).replace("_", " ")
								+ " for "
								+ i.recipe.quantities[0]
								+ " "
								+ i.recipe.items[0].toString()
										.replace("_", " ")
								+ ((i.recipe.items.length > 1) ? " and "
										+ i.recipe.quantities[1]
										+ " "
										+ i.recipe.items[1].toString().replace(
												"_", " ") : ""), new Dimension(
								(700 / 2) - 10, 30), "Click to "
								+ i.recipe.Action.toString().toLowerCase()
								+ " "
								+ i.toString().replace("_", " ").toLowerCase());
				b.Handler = new ActionHandler() {

					@Override
					public void Handle() {
						try {
							i.recipe.fulfil();
						} catch (Exception e) {
							try {
								Main.sendMessage("Can not afford!", Main.player);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}

				};
				MainGUI.add(b);
				switch (i.recipe.Action.IDN) {
				case 0:
					inb++;
					break;
				case 1:
					inc++;
					break;
				case 2:
					inr++;
					break;
				case 3:
					inm++;
					break;
				}
			}

		// Factory
		// Form definition
		Form FactoryForm = new Form(MainPaintStateList.Factory);

		// Buttons variable declaration
		StandardButton MoreMachinesButton = null;
		StandardButton PipeLineButton = null;
		StandardButton MixerButton = null;
		StandardButton RefinerButton = null;
		StandardButton CompresserButton = null;
		StandardButton BurnerButton = null;
		StandardButton InputButton = null;
		StandardButton OutputButton = null;
		StandardButton StoneButton = null;
		StandardButton FuelButton = null;
		StandardButton BrickButton = null;

		// Buttons variable definition
		try {
			PipeLineButton = new StandardButton(new Point(550,
					positionByIndexDefined(0, 10, 50, 50)), "Pipes",
					new Dimension(200, 50),
					"Pipes. Used to send things around. Costs "
							+ generateStringForFactoryButtons(BasicPipe
									.getCost()), new Color(200, 0, 0));
			new Mixer(0, 0, null, 0);
			MixerButton = new StandardButton(new Point(550,
					positionByIndexDefined(1, 0, 50, 50)), "Mixer",
					new Dimension(200, 50), "Mixer. Used to mix items. Costs "
							+ generateStringForFactoryButtons(Mixer.Cost()),
					new Color(0, 200, 0));
			new Refiner(0, 0, null, 0);
			RefinerButton = new StandardButton(new Point(550,
					positionByIndexDefined(2, 0, 50, 50)), "Refiner",
					new Dimension(200, 50),
					"Refiner. Used to refine items. Costs "
							+ generateStringForFactoryButtons(Refiner.Cost()),
					new Color(0, 200, 0));
			new Compressor(0, 0, null, 0);
			CompresserButton = new StandardButton(
					new Point(550, positionByIndexDefined(3, 0, 50, 50)),
					"Compressor",
					new Dimension(200, 50),
					"Compressor. Used to compress items. Costs "
							+ generateStringForFactoryButtons(Compressor.Cost()),
					new Color(0, 200, 0));
			new Burner(0, 0, null, 0);
			BurnerButton = new StandardButton(new Point(550,
					positionByIndexDefined(4, 0, 50, 50)), "Burner",
					new Dimension(200, 50), "Burner. Use to burn items. Costs "
							+ generateStringForFactoryButtons(Burner.Cost()),
					new Color(0, 200, 0));
			new InputMachine(0, 0, null, null, inc);
			InputButton = new StandardButton(new Point(550,
					positionByIndexDefined(5, 0, 50, 50)), "Input",
					new Dimension(200, 50),
					"Input. Used to retrive items. Can only be place on the left side. Costs "
							+ generateStringForFactoryButtons(InputMachine
									.Cost()), new Color(0, 200, 0));
			new OutputMachine(0, 0, null, 0);
			OutputButton = new StandardButton(new Point(550,
					positionByIndexDefined(6, 0, 50, 50)), "Output",
					new Dimension(200, 50),
					"Output. Used to send items. Can only be place on the right side. Costs "
							+ generateStringForFactoryButtons(OutputMachine
									.Cost()), new Color(0, 200, 0));
			StoneButton = new StandardButton(
					new Point(550, positionByIndexDefined(7, 0, 50, 50)),
					"Stone Generation Template",
					new Dimension(200, 50),
					"An already finished template for stone generation. Costs "
							+ generateStringForFactoryButtons(allCost(TemplateDecoder
									.decode(Template.Stone, null))), new Color(
							200, 200, 0));
			FuelButton = new StandardButton(
					new Point(550, positionByIndexDefined(8, 0, 50, 50)),
					"Fuel Generation Template",
					new Dimension(200, 50),
					"An already finished template for fuel generation. Costs "
							+ generateStringForFactoryButtons(allCost(TemplateDecoder
									.decode(Template.Fuel, null))), new Color(
							200, 200, 0));
			BrickButton = new StandardButton(
					new Point(550, positionByIndexDefined(9, 0, 50, 50)),
					"Brick Generation Template",
					new Dimension(200, 50),
					"An already finished template for brick generation. Costs "
							+ generateStringForFactoryButtons(allCost(TemplateDecoder
									.decode(Template.Brick, null))), new Color(
							200, 200, 0));
			MoreMachinesButton = new StandardButton(new Point(555, 28),
					"More Machines", new Dimension(195, 17),
					"Click for more machines.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Buttons added to interface
		FactoryForm.add(PipeLineButton);
		FactoryForm.add(MixerButton);
		FactoryForm.add(RefinerButton);
		FactoryForm.add(CompresserButton);
		FactoryForm.add(BurnerButton);
		FactoryForm.add(InputButton);
		FactoryForm.add(OutputButton);
		FactoryForm.add(StoneButton);
		FactoryForm.add(FuelButton);
		FactoryForm.add(BrickButton);
		FactoryForm.add(MoreMachinesButton);

		PipeLineButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.PlaceState = PlaceStateList.forName("BasicPipe");
			}

		};

		MoreMachinesButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.setPaintState(MainPaintStateList.Machines);
			}

		};

		BurnerButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.PlaceState = PlaceStateList.forName("Burner");
			}

		};

		CompresserButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.PlaceState = PlaceStateList.forName("Compressor");
			}

		};

		RefinerButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.PlaceState = PlaceStateList.forName("Refiner");
			}

		};

		MixerButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.PlaceState = PlaceStateList.forName("Mixer");
			}

		};

		InputButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.PlaceState = PlaceStateList.forName("Input");
			}

		};

		OutputButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.PlaceState = PlaceStateList.forName("Output");
			}

		};
		StoneButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				TemplateUser.use(Template.Stone,
						(FactoryBuilding) Main.Interfaced);
			}

		};
		FuelButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				TemplateUser.use(Template.Fuel,
						(FactoryBuilding) Main.Interfaced);
			}

		};
		BrickButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				TemplateUser.use(Template.Brick,
						(FactoryBuilding) Main.Interfaced);
			}

		};

		// More Machines Interface Begin

		// Form definition
		Form MachinesForm = new Form(MainPaintStateList.Machines);

		// Buttons variable declaration
		StandardButton CrafterButton;
		StandardButton MaceratorButton;
		StandardButton AdvancedPipeButton;
		StandardButton eb3;
		StandardButton eb4;
		StandardButton eb5;
		StandardButton eb6;
		StandardButton eb7;
		StandardButton eb8;
		StandardButton eb9;

		new Mixer(0, 0, null, 0);
		// Buttons variable definition
		CrafterButton = new StandardButton(new Point(550,
				positionByIndexDefined(0, 10, 50, 50)), "Crafter",
				new Dimension(200, 50), "Mixer. Used to mix items. Costs "
						+ generateStringForFactoryButtons(Crafter.Cost()));
		new Macerator(0, 0, null, 0);
		MaceratorButton = new StandardButton(new Point(550,
				positionByIndexDefined(1, 0, 50, 50)), "Macerator",
				new Dimension(200, 50),
				"Crusher. Turns sh*t into dusts. Costs "
						+ generateStringForFactoryButtons(Macerator.Cost()));
		AdvancedPipeButton = new StandardButton(new Point(550,
				positionByIndexDefined(2, 0, 50, 50)), "Advanced Pipe",
				new Dimension(200, 50),
				"Advanced Pipe. Basicly a pipe used for more advanced operations. Costs"
						+ generateStringForFactoryButtons(AdvancedPipe
								.getCost()));
		eb3 = new StandardButton(new Point(550, positionByIndexDefined(3, 0,
				50, 50)), "----------", new Dimension(200, 50), "Unused");
		eb4 = new StandardButton(new Point(550, positionByIndexDefined(4, 0,
				50, 50)), "----------", new Dimension(200, 50), "Unused");
		eb5 = new StandardButton(new Point(550, positionByIndexDefined(5, 0,
				50, 50)), "----------", new Dimension(200, 50), "Unused");
		eb6 = new StandardButton(new Point(550, positionByIndexDefined(6, 0,
				50, 50)), "----------", new Dimension(200, 50), "Unused");
		eb7 = new StandardButton(new Point(550, positionByIndexDefined(7, 0,
				50, 50)), "----------", new Dimension(200, 50), "Unused");
		eb8 = new StandardButton(new Point(550, positionByIndexDefined(8, 0,
				50, 50)), "----------", new Dimension(200, 50), "Unused");
		eb9 = new StandardButton(new Point(550, positionByIndexDefined(9, 0,
				50, 50)), "----------", new Dimension(200, 50), "Unused");

		// Buttons added to interface
		MachinesForm.add(CrafterButton);
		MachinesForm.add(MaceratorButton);
		MachinesForm.add(AdvancedPipeButton);
		MachinesForm.add(eb3);
		MachinesForm.add(eb4);
		MachinesForm.add(eb5);
		MachinesForm.add(eb6);
		MachinesForm.add(eb7);
		MachinesForm.add(eb8);
		MachinesForm.add(eb9);

		CrafterButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.PlaceState = PlaceStateList.forName("Crafter");
			}

		};

		MaceratorButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.PlaceState = PlaceStateList.forName("Macerator");
			}

		};

		AdvancedPipeButton.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				Main.PlaceState = PlaceStateList.forName("AdvancedPipe");
			}

		};

		eb3.Handler = new ActionHandler() {

			@Override
			public void Handle() {
			}

		};

		eb4.Handler = new ActionHandler() {

			@Override
			public void Handle() {
			}

		};

		eb5.Handler = new ActionHandler() {

			@Override
			public void Handle() {
			}

		};

		eb6.Handler = new ActionHandler() {

			@Override
			public void Handle() {
			}

		};

		eb7.Handler = new ActionHandler() {

			@Override
			public void Handle() {
			}

		};
		eb8.Handler = new ActionHandler() {

			@Override
			public void Handle() {
			}

		};
		eb9.Handler = new ActionHandler() {

			@Override
			public void Handle() {
			}

		};

		// More Machines Interface End

		// Input Interface Start

		Form FactoryInputForm = new Form(MainPaintStateList.Input);

		final StandardButton quantityUp;
		final StandardButton quantityDown;

		ToggleItems = new StandardButton(new Point(10 + 550, 10 + 55), "None",
				new Dimension(180, 50),
				"Click to toggle which item is retrived");
		quantityText = new StandardLabel(new Point(30 + 550, 90 + 55),
				"Quantity : " + 1, new Dimension(140, 20));
		quantityUp = new StandardButton(new Point(10 + 550, 70 + 55), "\u25B4",
				new Dimension(20, 20), "Click to increase quantity to import");
		quantityDown = new StandardButton(new Point(170 + 550, 70 + 55),
				"\u25BE", new Dimension(20, 20),
				"Click to decrease quantity to import");

		ToggleItems.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				((InputMachine) Main.Interfaced).Properties
						.put("In",
								InputMachine
										.Toggle((BaseItem) ((InputMachine) Main.Interfaced).Properties
												.get("In")));
				ToggleItems.setText(((InputMachine) Main.Interfaced).Properties
						.get("In").toString().replace("_", " "));
			}

		};
		quantityUp.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				if ((int) ((InputMachine) Main.Interfaced).Properties
						.get("Quantity") >= 10)
					return;
				((InputMachine) Main.Interfaced).Properties.put("Quantity",
						(int) ((InputMachine) Main.Interfaced).Properties
								.get("Quantity") + 1);
				quantityText.setText("Quantity : "
						+ ((InputMachine) Main.Interfaced).Properties
								.get("Quantity"));
			}

		};
		quantityDown.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				if ((int) ((InputMachine) Main.Interfaced).Properties
						.get("Quantity") <= 1)
					return;
				((InputMachine) Main.Interfaced).Properties.put("Quantity",
						(int) ((InputMachine) Main.Interfaced).Properties
								.get("Quantity") - 1);
				quantityText.setText("Quantity : "
						+ ((InputMachine) Main.Interfaced).Properties
								.get("Quantity"));
			}

		};

		FactoryInputForm.add(ToggleItems);
		FactoryInputForm.add(quantityText);
		FactoryInputForm.add(quantityDown);
		FactoryInputForm.add(quantityUp);

		// Begin form for crafter
		Form CrafterForm = new Form(MainPaintStateList.Crafter);

		// Variable declaration:
		StandardLabel RecipeBegining;
		final StandardButton ToggleOutput;
		final StandardLabel recipe1;
		final StandardLabel recipe2;
		final StandardLabel recipe3;
		final StandardLabel recipe4;
		final StandardLabel recipe5;

		// Variable initializing:
		ToggleOutput = new StandardButton(new Point(560, 60), "Make: None",
				new Dimension(180, 50),
				"Click to toggle which item should be made.");
		RecipeBegining = new StandardLabel(new Point(560, 120), "Recipe:",
				new Dimension(180, 30));
		recipe1 = new StandardLabel(new Point(560, (1 * 30) + 130), "",
				new Dimension(180, 30));
		recipe2 = new StandardLabel(new Point(560, (2 * 30) + 130), "",
				new Dimension(180, 30));
		recipe3 = new StandardLabel(new Point(560, (3 * 30) + 130), "",
				new Dimension(180, 30));
		recipe4 = new StandardLabel(new Point(560, (4 * 30) + 130), "",
				new Dimension(180, 30));
		recipe5 = new StandardLabel(new Point(560, (5 * 30) + 130), "",
				new Dimension(180, 30));

		// Functionality:
		ToggleOutput.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				// Toggle
				((Crafter) Main.Interfaced).Toggle();
				ToggleOutput.setText("Make: "
						+ ((Crafter) Main.Interfaced).getOutput().toString()
								.replace("_", " "));
				try {
					// Reset
					recipe1.setText("");
					recipe2.setText("");
					recipe3.setText("");
					recipe4.setText("");
					recipe5.setText("");

					// Recipe
					recipe1.setText(((Crafter) Main.Interfaced).getOutput().recipe.quantities[0]
							+ "x "
							+ ((Crafter) Main.Interfaced).getOutput().recipe.items[0]
									.toString().replace("_", " "));
					recipe2.setText(((Crafter) Main.Interfaced).getOutput().recipe.quantities[1]
							+ "x "
							+ ((Crafter) Main.Interfaced).getOutput().recipe.items[1]
									.toString().replace("_", " "));
					recipe3.setText(((Crafter) Main.Interfaced).getOutput().recipe.quantities[2]
							+ "x "
							+ ((Crafter) Main.Interfaced).getOutput().recipe.items[2]
									.toString().replace("_", " "));
					recipe4.setText(((Crafter) Main.Interfaced).getOutput().recipe.quantities[3]
							+ "x "
							+ ((Crafter) Main.Interfaced).getOutput().recipe.items[3]
									.toString().replace("_", " "));
					recipe5.setText(((Crafter) Main.Interfaced).getOutput().recipe.quantities[4]
							+ "x "
							+ ((Crafter) Main.Interfaced).getOutput().recipe.items[4]
									.toString().replace("_", " "));
				} catch (Exception e) {

				}
			}

		};

		// Items added to Interface:
		CrafterForm.add(ToggleOutput);
		CrafterForm.add(RecipeBegining);
		CrafterForm.add(recipe1);
		CrafterForm.add(recipe2);
		CrafterForm.add(recipe3);
		CrafterForm.add(recipe4);
		CrafterForm.add(recipe5);

		// End form for crafter

		// Begin form for gun

		Form GunForm = new Form(MainPaintStateList.Gun);

		StandardLabel Mode = new StandardLabel(new Point(Main.Width - 190,
				positionByIndex(1)), "Leave 100 bullets?", new Dimension(115,
				20));

		toggleleave100.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				((GunBuilding) Main.Interfaced).Properties.put("Leave100",
						!(Boolean) ((GunBuilding) Main.Interfaced).Properties
								.get("Leave100"));
				toggleleave100.setText(((Boolean) Main.Interfaced.Properties
						.get("Leave100")) ? "Yes" : "No");
			}

		};

		GunForm.add(toggleleave100);
		GunForm.add(Mode);
		GunForm.add(BulletCount);
		GunForm.add(ChatForm);

		// End form for gun

		// Begin form for explorer

		// Form initializing
		Form ExplorerForm = new Form(MainPaintStateList.Explorer);

		// Element Declaration
		StandardButton GoToSpawn;
		StandardButton StopCurrent;

		// Element Initializing
		GoToSpawn = new StandardButton(new Point(610, positionByIndexDefined(0,
				10, 40, 40)), "Go Back To Spawn", new Dimension(180, 40),
				"Click to make this vehicle go to spawn.");
		StopCurrent = new StandardButton(new Point(610, positionByIndexDefined(
				1, 10, 40, 40)), "Stop Current Command",
				new Dimension(180, 40),
				"Click to make this vehicle stop whatever it thinks it is doing.");

		// Element Action Initializing
		GoToSpawn.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				((Vehicle) Main.Interfaced).getCommandCenter().Perform(
						CommandType.Goto,
						"X",
						""
								+ Main.userSpecificVariables[Main.player]
										.getSpawn().x,
						"Y",
						""
								+ Main.userSpecificVariables[Main.player]
										.getSpawn().y);
			}

		};
		StopCurrent.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				((Vehicle) Main.Interfaced).getCommandCenter().Stop();
			}

		};

		// Element Addition To Form
		ExplorerForm.add(StopCurrent);
		ExplorerForm.add(GoToSpawn);
		ExplorerForm.add(ChatForm);

		// End form for explorer

		// Begin and initialaizing of Assembler form

		Form AssemblerForm = new Form(MainPaintStateList.Assembler);

		// Element declaring

		ToggleButton b1;
		ToggleButton b2;
		ToggleButton b3;
		ToggleButton b4;
		ToggleButton b5;
		ToggleButton b6;

		InvisibleButton inb0;
		InvisibleButton inb1;
		InvisibleButton inb2;
		InvisibleButton inb3;
		InvisibleButton inb4;
		InvisibleButton inb5;
		InvisibleButton inb6;
		InvisibleButton inb7;
		InvisibleButton inb8;
		InvisibleButton inb9;

		// Element initialaizing

		b1 = new ToggleButton(new Point(76, 176), "Keep Stocked", new Dimension(150,
				50), "Keep atleast one always in stock");
		b2 = new ToggleButton(new Point(76, 226), "llllll", new Dimension(150,
				50), "lllll");
		b3 = new ToggleButton(new Point(76, 276), "llllll", new Dimension(150,
				50), "lllll");
		b4 = new ToggleButton(new Point(76, 326), "llllll", new Dimension(150,
				50), "lllll");
		b5 = new ToggleButton(new Point(76, 376), "llllll", new Dimension(150,
				50), "lllll");
		b6 = new ToggleButton(new Point(76, 426), "llllll", new Dimension(150,
				50), "lllll");

		inb0 = new InvisibleButton(75, 75, 50, 50);
		inb1 = new InvisibleButton(125, 75, 50, 50);
		inb2 = new InvisibleButton(175, 75, 50, 50);
		inb3 = new InvisibleButton(225, 75, 50, 50);
		inb4 = new InvisibleButton(275, 75, 50, 50);
		inb5 = new InvisibleButton(325, 75, 50, 50);
		inb6 = new InvisibleButton(375, 75, 50, 50);
		inb7 = new InvisibleButton(425, 75, 50, 50);
		inb8 = new InvisibleButton(475, 75, 50, 50);
		inb9 = new InvisibleButton(525, 75, 50, 50);

		// Element logic

		inb0.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				((AssemblerBuilding) Main.Interfaced).setSelectedPosition(0);
			}

		};

		inb1.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				((AssemblerBuilding) Main.Interfaced).setSelectedPosition(1);
			}

		};

		inb2.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				((AssemblerBuilding) Main.Interfaced).setSelectedPosition(2);
			}

		};

		inb3.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				((AssemblerBuilding) Main.Interfaced).setSelectedPosition(3);
			}

		};

		inb4.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				((AssemblerBuilding) Main.Interfaced).setSelectedPosition(4);
			}

		};

		inb5.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				((AssemblerBuilding) Main.Interfaced).setSelectedPosition(5);
			}

		};

		inb6.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				((AssemblerBuilding) Main.Interfaced).setSelectedPosition(6);
			}

		};

		inb7.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				((AssemblerBuilding) Main.Interfaced).setSelectedPosition(7);
			}

		};
		inb8.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				((AssemblerBuilding) Main.Interfaced).setSelectedPosition(8);
			}

		};

		inb9.Handler = new ActionHandler() {

			@Override
			public void Handle() {
				((AssemblerBuilding) Main.Interfaced).setSelectedPosition(9);
			}

		};

		// Element adding

		AssemblerForm.add(b1);
		AssemblerForm.add(b2);
		AssemblerForm.add(b3);
		AssemblerForm.add(b4);
		AssemblerForm.add(b5);
		AssemblerForm.add(b6);

		AssemblerForm.add(inb0);
		AssemblerForm.add(inb1);
		AssemblerForm.add(inb2);
		AssemblerForm.add(inb3);
		AssemblerForm.add(inb4);
		AssemblerForm.add(inb5);
		AssemblerForm.add(inb6);
		AssemblerForm.add(inb7);
		AssemblerForm.add(inb8);
		AssemblerForm.add(inb9);

		PaintStateHandler.pa.provideItemSelectionButtonLayout(AssemblerForm,
				600, 75);

		// End form for explorer

		// Transporter
		// Form initializing
		Form TransporterForm = new Form(MainPaintStateList.Transporter);

		// Element Declaration

		// Element Initializing

		// Element login

		// Element Addition To Form
		TransporterForm.add(StopCurrent);
		TransporterForm.add(GoToSpawn);
		TransporterForm.add(ChatForm);

		// End form for transporter

		// Form listing

		add(AssemblerForm);
		add(ExplorerForm);
		add(GunForm);
		add(CrafterForm);
		add(FactoryInputForm);
		add(FactoryForm);
		add(MainGUI);
		add(Game1);
		add(LoseGUI);
		add(MachinesForm);
		add(TransporterForm);
	}
}
