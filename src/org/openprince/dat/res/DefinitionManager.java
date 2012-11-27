package org.openprince.dat.res;

import java.util.HashMap;

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

	public Definition getById(String filename, int id) {
		return definitions.get(filename + " " + id);
	}

	public void loadFromFile(String filename) {
		// TODO: Load definition from file
	}
}
