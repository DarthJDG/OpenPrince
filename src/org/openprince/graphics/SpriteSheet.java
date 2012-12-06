package org.openprince.graphics;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {
	private final int GAP = 1;

	private int width;
	private int height;

	ByteBuffer buffer;
	List<Rect> free;

	public SpriteSheet() {
		this(512, 512);
	}

	public SpriteSheet(int size) {
		this(size, size);
	}

	public SpriteSheet(int width, int height) {
		this.width = width;
		this.height = height;

		buffer = ByteBuffer.allocateDirect(4 * this.width * this.height);
		clearBuffer();

		free = new ArrayList<Rect>();
		free.add(new Rect(GAP, GAP, this.width - GAP * 2, this.height - GAP * 2));
	}

	public void clearBuffer() {
		for (int i = 0; i < buffer.limit(); i++) {
			buffer.put(i, (byte) 0);
		}
	}

	public void drawPixel(int x, int y, byte r, byte g, byte b, byte a) {
		if (x < 0)
			return;
		if (y < 0)
			return;
		if (x >= width)
			return;
		if (y >= height)
			return;

		int offset = (y * width + x) * 4;
		buffer.put(offset, r);
		buffer.put(offset + 1, g);
		buffer.put(offset + 2, b);
		buffer.put(offset + 3, a);
	}

	public void drawPixel(int x, int y, byte r, byte g, byte b) {
		drawPixel(x, y, r, g, b, (byte) 0xff);
	}

	public void drawPixel(Rect rect, int x, int y, byte r, byte g, byte b,
			byte a) {
		if (x < 0)
			return;
		if (y < 0)
			return;
		if (x >= rect.getWidth())
			return;
		if (x >= rect.getHeight())
			return;

		drawPixel(rect.x1 + x, rect.y1 + y, r, g, b, a);
	}

	public void drawPixel(Rect rect, int x, int y, byte r, byte g, byte b) {
		drawPixel(rect, x, y, r, g, b, (byte) 0xff);
	}

	public Rect allocateRect(int width, int height) {
		Rect result = null;

		for (Rect r : free) {
			if ((r.getWidth() >= width + GAP)
					&& (r.getHeight() >= height + GAP)) {
				result = r;
				break;
			}
		}

		if (result != null) {
			// If we have found a place for the sprite, remove it from the free
			// list, cut it out, and put the remaining pieces back. This
			// algorithm is quite inefficient at the moment.
			free.remove(result);

			// Make the first cut as small as possible to keep the second one as
			// large as we can.
			int nx, ny, nw, nh;
			if ((result.getWidth() - width - GAP) * (height + GAP) < (result
					.getHeight() - height - GAP) * (width + GAP)) {
				// Horizontal is smaller
				nx = result.x1 + width + GAP;
				ny = result.y1;
				nw = result.getWidth() - width - GAP;
				nh = height + GAP;

				if (nw > GAP && nh > GAP) {
					free.add(new Rect(nx, ny, nx + nw, ny + nh));
				}

				nx = result.x1;
				ny = result.y1 + height + GAP;
				nw = result.getWidth();
				nh = result.getHeight() - height - GAP;

				if (nw > GAP && nh > GAP) {
					free.add(new Rect(nx, ny, nx + nw, ny + nh));
				}
			} else {
				// Vertical is smaller
				nx = result.x1;
				ny = result.y1 + height + GAP;
				nw = width + GAP;
				nh = result.getHeight() - height - GAP;

				if (nw > GAP && nh > GAP) {
					free.add(new Rect(nx, ny, nx + nw, ny + nh));
				}

				nx = result.x1 + width + GAP;
				ny = result.y1;
				nw = result.getWidth() - width - GAP;
				nh = result.getHeight();

				if (nw > GAP && nh > GAP) {
					free.add(new Rect(nx, ny, nx + nw, ny + nh));
				}
			}
		}

		return result;
	}
}
