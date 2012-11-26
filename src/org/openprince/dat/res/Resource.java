package org.openprince.dat.res;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.openprince.dat.IndexItem;

public class Resource {
	public int id;
	public ByteBuffer buffer;

	public Resource(ByteBuffer buffer, IndexItem index) {
		id = index.id;

		// FIXME: Ignore first checksum byte for now
		buffer.position(index.offset + 1);
		this.buffer = buffer.slice();
		this.buffer.limit(index.size);
		this.buffer.order(ByteOrder.LITTLE_ENDIAN);
	}

	public Resource(Resource res) {
		id = res.id;
		buffer = res.buffer;
	}

	public ResourceType getType() {
		return ResourceType.BINARY;
	}
}
