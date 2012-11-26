package org.openprince.dat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Index {
	int count;
	List<IndexItem> items;

	Index(ByteBuffer buffer, Header header) throws IOException {
		buffer.position(header.indexOffset);
		count = buffer.getChar();

		if (count * 8 + 2 != header.indexSize) {
			throw new IOException("Invalid index size.");
		}

		items = new ArrayList<IndexItem>();
		for (int i = 0; i < count; i++) {
			items.add(new IndexItem(buffer));
		}
	}
}
