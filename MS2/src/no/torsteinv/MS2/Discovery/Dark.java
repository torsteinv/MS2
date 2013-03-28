package no.torsteinv.MS2.Discovery;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import no.torsteinv.MS2.Entities.Entity;
import no.torsteinv.MS2.Main.Main;
import no.torsteinv.MS2.Managers.Timing.Timer;
import no.torsteinv.MS2.Managers.Visualization.TransperentManager;

public class Dark {

	int transperentmask = 0xBC56AF;
	int subdiscovermask = 0xAFC67F;
	//how many milliseconds before adding a feathering discovery point. 0 for none
	public float MovableExplorationFeathering = 5f;

	public CopyOnWriteArrayList<Discoverpoint> discoverpoints = new CopyOnWriteArrayList<Discoverpoint>();
	public HashMap<Entity, Discoverpoint> entities = new HashMap<Entity, Discoverpoint>();

	public Dark() {

	}

	public void add(Discoverpoint d) {
		discoverpoints.add(d);
	}

	public void add(Entity e, boolean RoundOrSquare, int power) {
		entities.put(e, RoundOrSquare ? new DiscoverPointRound((int) e.getFloat("X"),
				(int) e.getFloat("Y"), power,-1337) : new DiscoverPointSquare((int) e.getFloat("X"),
				(int) e.getFloat("Y"), power,-1337));
	}
	
	public Image RenderMap() {
		Image img = Main.SolidColor(200, 200, Color.BLACK);
		Graphics g = img.getGraphics();
		Collection<Discoverpoint> entitiesCopy = new ArrayList<Discoverpoint>();
		entitiesCopy.addAll(entities.values());
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 200, 200);
		g.setColor(new Color(transperentmask));
		for (Discoverpoint d : discoverpoints) {
			g.setColor(new Color(transperentmask));
			if (d instanceof DiscoverPointSquare) {
				g.fillRect((d.x - (d.power / 2)) / 50,
						(d.y - (d.power / 2)) / 50, d.power / 50, d.power / 50);
			} else if (d instanceof DiscoverPointRound) {
				g.fillOval((d.x - (d.power / 2)) / 50,
						(d.y - (d.power / 2)) / 50, d.power / 50, d.power / 50);
			}
		}
		for (Discoverpoint d : entitiesCopy) {
			g.setColor(new Color(transperentmask));
			if (d instanceof DiscoverPointSquare) {
				g.fillRect((d.x - (d.power / 2)) / 50,
						(d.y - (d.power / 2)) / 50, d.power / 50, d.power / 50);
			} else if (d instanceof DiscoverPointRound) {
				g.fillOval((d.x - (d.power / 2)) / 50,
						(d.y - (d.power / 2)) / 50, d.power / 50, d.power / 50);
			}
		}
		return TransperentManager.makeColorTransparent(img, new Color(
				transperentmask));
	}

	public Image RenderGame() {
		for(Entity e : entities.keySet())
		{
			entities.get(e).x = (int)e.getFloat("X");
			entities.get(e).y = (int)e.getFloat("Y");
		}
		for(Discoverpoint dp : discoverpoints)
		{
			if(dp.lifetime == Discoverpoint.InfiniteLifeTime)
				continue;
			
			if(dp.lifetime < 0)
				discoverpoints.remove(dp);
			
			else
				dp.lifetime -= Timer.getDelta();
		}
		Image img = Main.SolidColor(800, 600, Color.BLACK);
		Graphics g = img.getGraphics();
		Collection<Discoverpoint> entitiesCopy = new ArrayList<Discoverpoint>();
		entitiesCopy.addAll(entities.values());
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
		g.setColor(new Color(transperentmask));
		for (Discoverpoint d : discoverpoints) {
			if (d instanceof DiscoverPointSquare) {
				g.setColor(new Color(transperentmask));
				g.fillRect((d.x - (d.power / 2)) - (int)Main.HorisontalAlignment,
						(d.y - (d.power / 2)) - (int)Main.VerticalAlignment,
						d.power, d.power);
			} else if (d instanceof DiscoverPointRound) {
				g.setColor(new Color(transperentmask));
				g.fillOval((d.x - (d.power / 2)) - (int)Main.HorisontalAlignment,
						(d.y - (d.power / 2)) - (int)Main.VerticalAlignment,
						d.power, d.power);
			}
		}
		for (Discoverpoint d : entitiesCopy) {
			if (d instanceof DiscoverPointSquare) {
				g.setColor(new Color(transperentmask));
				g.fillRect((d.x - (d.power / 2)) - (int)Main.HorisontalAlignment,
						(d.y - (d.power / 2)) - (int)Main.VerticalAlignment,
						d.power, d.power);
			} else if (d instanceof DiscoverPointRound) {
				g.setColor(new Color(transperentmask));
				g.fillOval((d.x - (d.power / 2)) - (int)Main.HorisontalAlignment,
						(d.y - (d.power / 2)) - (int)Main.VerticalAlignment,
						d.power, d.power);
			}
		}
		return TransperentManager.makeColorTransparent(img, new Color(
				transperentmask));
	}
}
