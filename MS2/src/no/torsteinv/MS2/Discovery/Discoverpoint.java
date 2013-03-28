package no.torsteinv.MS2.Discovery;


public class Discoverpoint{
	public static final int InfiniteLifeTime = -1337;
	int x = 0,y = 0,power = 0;
	int lifetime = -1337;
	public Discoverpoint(int x,int y,int power, int subdiscover) {
		this.x = x;
		this.y = y;
		this.power = power;
		this.lifetime = subdiscover;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Discoverpoint))
			return false;
		Discoverpoint other = (Discoverpoint) obj;
		if (lifetime != other.lifetime)
			return false;
		if (power != other.power)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
