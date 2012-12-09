package org.openprince;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
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

		Reader dat = null;

		try {
			dat = new Reader("data/prince.dat");
			dat.allocateSprites();

			System.out.println("Sheets: " + dat.sheets.size());
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("done.");

		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		int texId = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
				GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
				GL11.GL_LINEAR);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 512, 512, 0,
				GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, dat.sheets.get(0).buffer);

		// init OpenGL
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 600, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		while (!Display.isCloseRequested()) {
			// Clear the screen and depth buffer
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			// set the color of the quad (R,G,B,A)
			GL11.glColor3f(1.0f, 1.0f, 1.0f);

			// draw quad
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(0, 0);
			GL11.glTexCoord2f(1.0f, 0);
			GL11.glVertex2f(512, 0);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex2f(512, 512);
			GL11.glTexCoord2f(0, 1.0f);
			GL11.glVertex2f(0, 512);

			GL11.glEnd();

			Display.sync(30);
			Display.update();
		}

		Display.destroy();
	}
}
