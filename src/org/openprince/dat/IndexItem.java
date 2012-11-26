package org.openprince.dat;

import java.nio.ByteBuffer;

public class IndexItem {
	public int id;
	public int offset;
	public int size;

	public IndexItem(ByteBuffer buffer) {
		id = buffer.getChar();
		offset = buffer.getInt();
		size = buffer.getChar();
	}
}
