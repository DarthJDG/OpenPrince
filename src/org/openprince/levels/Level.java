package org.openprince.levels;

import java.util.ArrayList;
import java.util.List;

public class Level {
	public List<Screen> screens;

	public Level() {
		screens = new ArrayList<Screen>();
	}

	public Screen addScreen() {
		int id = screens.size() + 1;
		Screen s = new Screen(id);
		screens.add(s);
		return s;
	}
}
