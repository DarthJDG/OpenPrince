package org.openprince.dat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openprince.graphics.Rect;
import org.openprince.graphics.SpriteSheet;

public class Reader {
	private String filename;
	public int fileSize;
	ByteBuffer buffer;
	Header header;
	Index index;

	private HashMap<Integer, Resource> resources;

	public List<SpriteSheet> sheets;

	public Reader(String filename) throws IOException {
		this.filename = filename;
		sheets = new ArrayList<SpriteSheet>();

		File file = new File(filename);
		FileInputStream fin = new FileInputStream(file);
		fileSize = (int) file.length();
		byte content[] = new byte[fileSize];
		fin.read(content);
		fin.close();

		buffer = ByteBuffer.wrap(content);
		buffer.order(ByteOrder.LITTLE_ENDIAN);

		header = new Header(buffer);
		if (header.indexOffset + header.indexSize > fileSize) {
			throw new IOException("Invalid header info.");
		}

		index = new Index(buffer, header);

		resources = new HashMap<Integer, Resource>();

		Resource res;
		ResourceType type;
		Definition def;
		DefinitionManager dm = DefinitionManager.getInstance();

		for (IndexItem item : index.items) {
			res = new Resource(buffer, item);
			type = res.getType();
			def = dm.getById(filename, res.id);
			if (def != null) {
				type = def.type;
			}
			resources.put(item.id, res.getAs(type));
		}
	}

	public Resource getById(int id) {
		return resources.get(id);
	}

	public void allocateSprites() {
		DefinitionManager dm = DefinitionManager.getInstance();

		for (Resource res : resources.values()) {
			if (res.getType() == ResourceType.IMAGE) {
				ImageResource image = (ImageResource) res;
				SpriteSheet selectedSheet = null;
				Rect rect = null;

				for (SpriteSheet sheet : sheets) {
					rect = sheet.allocateRect(image.width, image.height);
					selectedSheet = sheet;
					if (rect != null)
						break;
				}

				if (rect == null) {
					selectedSheet = new SpriteSheet();
					sheets.add(selectedSheet);
					rect = selectedSheet
							.allocateRect(image.width, image.height);
				}

				if (rect != null && selectedSheet != null) {
					Definition def = dm.getById(filename, res.id);
					if (def != null) {
						PaletteResource pal = (PaletteResource) getById(def.pal);
						image.renderToSheet(selectedSheet, pal, rect.x1,
								rect.y1);
					}
				}
			}
		}
	}
}
