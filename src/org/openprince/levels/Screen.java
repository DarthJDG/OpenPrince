package org.openprince.levels;

import java.util.ArrayList;
import java.util.List;

public class Screen {
	public int id;
	public int width;
	public int height;

	public List<Block> blocks;
	public List<Modifier> modifiers;

	public Screen(int id) {
		this(id, 10, 3);
	}

	public Screen(int id, int width, int height) {
		this.id = id;
		this.width = width;
		this.height = height;

		blocks = new ArrayList<Block>();
		modifiers = new ArrayList<Modifier>();
	}
}
