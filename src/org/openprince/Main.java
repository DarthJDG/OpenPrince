package org.openprince;

import java.io.IOException;

import org.openprince.dat.Reader;

public class Main {
	public static void main(String[] args) {
		System.out.println("Loading resources...");

		try {
			new Reader("data/vdungeon.dat");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("done.\nLoaded ");
	}
}
