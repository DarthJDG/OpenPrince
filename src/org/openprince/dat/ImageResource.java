package org.openprince.dat;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.openprince.graphics.SpriteSheet;

public class ImageResource extends Resource {
	public int width;
	public int height;
	public int bpp;
	public ImageCompression compression;
	public ImageDirection direction;

	public List<Byte> decompressed;

	public SpriteSheet sheet = null;
	public int sheetX = 0;
	public int sheetY = 0;

	public ImageResource(ByteBuffer buffer, IndexItem index) {
		super(buffer, index);
		getHeader();
		decompress();
	}

	public ImageResource(Resource res) {
		super(res);
		getHeader();
		decompress();
	}

	@Override
	public ResourceType getType() {
		return ResourceType.IMAGE;
	}

	private void getHeader() {
		buffer.rewind();
		height = buffer.getChar();
		width = buffer.getChar();
		buffer.get();

		int info = buffer.get();

		bpp = ((info & 0x30) >> 4) + 1;
		switch (info & 0x0F) {
			case 0:
				compression = ImageCompression.RAW;
				direction = ImageDirection.LEFT_RIGHT;
				break;
			case 1:
				compression = ImageCompression.RLE;
				direction = ImageDirection.LEFT_RIGHT;
				break;
			case 2:
				compression = ImageCompression.RLE;
				direction = ImageDirection.UP_DOWN;
				break;
			case 3:
				compression = ImageCompression.LZG;
				direction = ImageDirection.LEFT_RIGHT;
				break;
			case 4:
				compression = ImageCompression.LZG;
				direction = ImageDirection.UP_DOWN;
				break;
			default:
				width = 0;
				height = 0;
				compression = ImageCompression.UNKNOWN;
				direction = ImageDirection.LEFT_RIGHT;
		}
	}

	private void decompress() {
		decompressed = new ArrayList<Byte>();

		switch (compression) {
			case RLE:
				buffer.position(6);
				byte control;
				while (buffer.remaining() > 0) {
					control = buffer.get();
					if (control < 0) {
						byte value = buffer.get();
						while (control < 0) {
							decompressed.add(value);
							control++;
						}
					} else {
						while (control >= 0 && buffer.remaining() > 0) {
							decompressed.add(buffer.get());
							control--;
						}
					}
				}
				break;
			case LZG:
				final int LZG_WINDOW_SIZE = 1024;

				int oCursor;
				int iCursor = 0;
				int maskbyte = 0;
				int loc,
				rep,
				k;

				for (oCursor = 0; oCursor < LZG_WINDOW_SIZE; oCursor++) {
					decompressed.add((byte) 0);
				}

				buffer.position(6);
				while (iCursor < buffer.limit() - 6) {
					maskbyte = buffer.get(6 + iCursor);
					iCursor++;
					for (k = 8; k != 0 && (iCursor < buffer.limit() - 6); k--) {
						int bit = maskbyte & 1;
						maskbyte >>= 1;
						if (bit == 1) {
							decompressed.add(buffer.get(6 + iCursor++));
							oCursor++;
						} else {
							loc = 66 + ((buffer.get(6 + iCursor) & 0x03) << 8)
									+ (buffer.get(6 + iCursor + 1) & 0xff);
							rep = 3 + ((buffer.get(6 + iCursor) & 0xfc) >> 2);

							iCursor += 2;

							loc = (oCursor - loc) & 0x3ff;

							while (rep-- > 0) {
								decompressed.add(decompressed
										.get(oCursor - loc));
								oCursor++;
							}
						}
					}
				}

				for (k = 0; k < LZG_WINDOW_SIZE; k++) {
					decompressed.remove(0);
				}

				break;

			default:
				break;
		}
	}

	public void renderToSheet(SpriteSheet sheet, PaletteResource pal, int sx,
			int sy) {
		this.sheet = sheet;
		sheetX = sx;
		sheetY = sy;

		if (pal == null) {
			pal = new PaletteResource();
		}

		try {
			int bits = 0;
			int val = 0;
			int pos = 0;
			int pixel = 0;

			if (direction == ImageDirection.LEFT_RIGHT) {
				int xoffs = 8 / bpp - 1;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x += 8 / bpp) {
						val = decompressed.get(pos) & 0xff;
						xoffs = 8 / bpp - 1;
						bits = 8;

						while (x + xoffs >= width) {
							xoffs--;
							val >>= bpp;
							bits -= bpp;
						}

						while (bits > 0) {
							pixel = val & ((1 << bpp) - 1);
							val >>= bpp;
							bits -= bpp;

							if (pixel > 0) {
								sheet.drawPixel(xoffs + sx + x, sy + y,
										pal.r.get(pixel)
												.byteValue(), pal.g.get(pixel)
												.byteValue(),
										pal.b.get(pixel).byteValue());
							}

							xoffs--;
						}

						pos++;
					}
				}
			} else {
				int xoffs = 8 / bpp - 1;
				for (int x = 0; x < width; x += 8 / bpp) {
					val = decompressed.get(pos) & 0xff;
					bits = 8;
					for (int y = 0; y < height && xoffs >= 0;) {
						while (x + xoffs >= width) {
							xoffs--;
							val >>= bpp;
							bits -= bpp;
						}

						pixel = val & ((1 << bpp) - 1);
						val >>= bpp;
						bits -= bpp;

						if (pixel > 0) {
							sheet.drawPixel(xoffs + sx + x, sy + y, pal.r
									.get(pixel)
									.byteValue(), pal.g.get(pixel).byteValue(),
									pal.b.get(pixel).byteValue());
						}

						xoffs--;

						if (bits == 0) {
							pos++;
							if(pos < decompressed.size()) {
								val = decompressed.get(pos) & 0xff;
							}
							bits = 8;
							y++;
							xoffs = 8 / bpp - 1;
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception (" + id + "): " + e.getMessage());
		}
	}
}
