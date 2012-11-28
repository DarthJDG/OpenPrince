package org.openprince;

import java.io.IOException;

import org.openprince.dat.DefinitionManager;
import org.openprince.dat.Reader;

public class Main {
	public static void main(String[] args) {
		System.out.println("Loading definitions...");

		DefinitionManager dm = DefinitionManager.getInstance();
		try {
			dm.loadFromFile("def/pop.def");
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Loading resources...");

		try {
			new Reader("data/vdungeon.dat");
			new Reader("data/levels.dat");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("done.");
	}
}
