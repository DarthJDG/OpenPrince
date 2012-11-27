package org.openprince.dat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

public class Reader {
	public int fileSize;
	ByteBuffer buffer;
	Header header;
	Index index;

	private HashMap<Integer, Resource> resources;

	public Reader(String filename) throws IOException {
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
}
