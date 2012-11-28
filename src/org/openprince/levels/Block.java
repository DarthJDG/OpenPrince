package org.openprince.levels;

import java.util.HashMap;
import java.util.Map;

public enum Block {
	EMPTY (0x00, BlockGroup.FREE),
	FLOOR (0x01, BlockGroup.FREE),
	SPIKES (0x02, BlockGroup.SPIKE),
	PILLAR (0x03, BlockGroup.NONE),
	GATE (0x04, BlockGroup.GATE),
	STUCK_BUTTON (0x05, BlockGroup.NONE),
	DROP_BUTTON (0x06, BlockGroup.EVENT),
	TAPESTRY (0x07, BlockGroup.TAPEST),
	BOTTOM_BIG_PILLAR (0x08, BlockGroup.NONE),
	TOP_BIG_PILLAR (0x09, BlockGroup.NONE),
	POTION (0x0a, BlockGroup.POTION),
	LOOSE_BOARD (0x0b, BlockGroup.NONE),
	TAPESTRY_TOP (0x0c, BlockGroup.TTOP),
	MIRROR (0x0d, BlockGroup.NONE),
	DEBRIS (0x0e, BlockGroup.NONE),
	RAISE_BUTTON (0x0f, BlockGroup.EVENT),
	EXIT_LEFT (0x10, BlockGroup.NONE),
	EXIT_RIGHT (0x11, BlockGroup.NONE),
	CHOPPER (0x12, BlockGroup.CHOMP),
	TORCH (0x13, BlockGroup.NONE),
	WALL (0x14, BlockGroup.WALL),
	SKELETON (0x15, BlockGroup.NONE),
	SWORD (0x16, BlockGroup.NONE),
	BALCONY_LEFT (0x17, BlockGroup.NONE),
	BALCONY_RIGHT (0x18, BlockGroup.NONE),
	LATTICE_PILLAR (0x19, BlockGroup.NONE),
	LATTICE_SUPPORT (0x1a, BlockGroup.NONE),
	SMALL_LATTICE (0x1b, BlockGroup.NONE),
	LATTICE_LEFT (0x1c, BlockGroup.NONE),
	LATTICE_RIGHT (0x1d, BlockGroup.NONE),
	TORCH_WITH_DEBRIS (0x1e, BlockGroup.NONE),
	NULL (0x1f, BlockGroup.NONE);

	public final int value;
	public final BlockGroup group;

	private Block(int value, BlockGroup group) {
		this.value = value;
		this.group = group;
	}

	private static final Map<Integer, Block> sMap = new HashMap<Integer, Block>();

	static {
		for (Block block : Block.values())
			sMap.put(block.value, block);
	}

	public static Block get(int value) {
		return sMap.get(value);
	}
}
