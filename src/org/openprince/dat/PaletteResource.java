package org.openprince.dat;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class PaletteResource extends Resource {
	public List<Integer> r;
	public List<Integer> g;
	public List<Integer> b;

	public PaletteResource(ByteBuffer buffer, IndexItem index) {
		super(buffer, index);
		getPalette();
	}

	public PaletteResource(Resource res) {
		super(res);
		getPalette();
	}

	public PaletteResource() {
		super();

		// Create default palette
		r = new ArrayList<Integer>();
		g = new ArrayList<Integer>();
		b = new ArrayList<Integer>();

		r.add(0);
		g.add(0);
		b.add(0);

		r.add(255);
		g.add(255);
		b.add(255);
	}

	@Override
	public ResourceType getType() {
		return ResourceType.PALETTE;
	}

	private void getPalette() {
		r = new ArrayList<Integer>();
		g = new ArrayList<Integer>();
		b = new ArrayList<Integer>();

		buffer.position(4);
		for (int i = 0; i < 16; i++) {
			r.add(buffer.get() << 2);
			g.add(buffer.get() << 2);
			b.add(buffer.get() << 2);
		}
	}
}
