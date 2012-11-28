package org.openprince.levels;

public enum BlockGroup {
	NONE (0x0000),
	FREE (0x0100),
	SPIKE (0x0200),
	GATE (0x0300),
	TAPEST (0x0400),
	POTION (0x0500),
	TTOP (0x0600),
	CHOMP (0x0700),
	WALL (0x0800),
	EVENT (0x0900);

	public final int modifierOffset;

	private BlockGroup(int offset) {
		modifierOffset = offset;
	}
}
