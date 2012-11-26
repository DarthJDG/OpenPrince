package org.openprince.dat;

import java.nio.ByteBuffer;

public class Header {
	int indexOffset;
	int indexSize;

	Header(ByteBuffer buffer) {
		buffer.rewind();
		indexOffset = buffer.getInt();
		indexSize = buffer.getChar();
	}
}
