package org.openprince.dat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

import org.openprince.dat.res.Resource;

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

		for (IndexItem item : index.items) {
			resources.put(item.id, new Resource(buffer, item));
		}
	}
}
