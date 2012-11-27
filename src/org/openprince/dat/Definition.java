package org.openprince.dat;

import org.json.simple.JSONObject;

public class Definition {
	public String filename;
	public int id;
	public ResourceType type;
	public String name;
	public int pal = 0;
	public int level = 0;

	public Definition(JSONObject obj) {
		filename = (String) obj.get("file");
		id = ((Long) obj.get("id")).intValue();
		name = (String) obj.get("name");

		Object o;

		o = obj.get("type");
		if (o != null) {
			String t = (String) o;
			if (t.equals("I"))
				type = ResourceType.IMAGE;
			else if (t.equals("S"))
				type = ResourceType.SOUND;
			else if (t.equals("M"))
				type = ResourceType.MUSIC;
			else if (t.equals("P"))
				type = ResourceType.PALETTE;
			else if (t.equals("L"))
				type = ResourceType.LEVEL;
			else
				type = ResourceType.BINARY;
		}

		o = obj.get("pal");
		if (o != null)
			pal = ((Long) o).intValue();

		o = obj.get("level");
		if (o != null)
			level = ((Long) o).intValue();
	}
}
