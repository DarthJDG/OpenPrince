package org.openprince.dat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DefinitionManager {
	private static DefinitionManager manager;
	private HashMap<String, Definition> definitions;

	private DefinitionManager() {
		definitions = new HashMap<String, Definition>();
	}

	public static DefinitionManager getInstance() {
		if (manager == null) {
			manager = new DefinitionManager();
		}

		return manager;
	}

	public Definition getById(String path, int id) {
		String[] chunks = path.split("/");
		return definitions.get(chunks[chunks.length - 1] + " " + id);
	}

	public void loadFromFile(String filename) throws FileNotFoundException,
			IOException, ParseException {
		JSONParser parser = new JSONParser();

		JSONArray defArray = (JSONArray) parser.parse(new FileReader(filename));
		Definition def;
		for (Object defObject : defArray) {
			def = new Definition((JSONObject) defObject);
			definitions.put(def.filename + " " + def.id, def);
		}
	}
}
