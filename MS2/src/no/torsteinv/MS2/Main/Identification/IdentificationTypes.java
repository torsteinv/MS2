package no.torsteinv.MS2.Main.Identification;

public enum IdentificationTypes {
	Sound("resource.sound."),
	Image("resource.image."),
	File("resource.file."),
	MenuElement("menu."),
	Item("item."),
	Building("building.");
	
	public String name = "";
	IdentificationTypes(String name){
		this.name = name;
	}
}
