package org.openprince.dat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.openprince.dat.res.Resource;
import org.openprince.dat.res.ResourceManager;

public class Reader {
	public int fileSize;
	ByteBuffer buffer;
	Header header;
	Index index;

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
	}

	public void register() {
		ResourceManager manager = ResourceManager.getInstance();

		for (IndexItem item : index.items) {
			manager.register(new Resource(buffer, item));
		}
	}
}
