package org.openprince.dat;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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

	public Resource getAs(ResourceType type) {
		if (getType() == type) {
			return this;
		}

		Resource res = this;

		switch (type) {
			case BINARY:
				res = new Resource(this);
				break;
			case IMAGE:
				res = new ImageResource(this);
				break;
			case LEVEL:
				res = new LevelResource(this);
				break;
			case MUSIC:
				// TODO: Music resource
				break;
			case PALETTE:
				res = new PaletteResource(this);
				break;
			case SOUND:
				// TODO: Sound resource
				break;
		}

		return res;
	}
}
