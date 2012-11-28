package org.openprince.levels;

import java.util.HashMap;
import java.util.Map;

public enum Modifier {
	NONE_NONE (0x0000, BlockGroup.NONE),
	FREE_D_NOTHING_P_BLUE_LINE (0x0100, BlockGroup.FREE),
	FREE_D_SPOT1_P_NO_BLUE_LINE (0x0101, BlockGroup.FREE),
	FREE_D_SPOT2_P_DIAMOND (0x0102, BlockGroup.FREE),
	FREE_WINDOW (0x0103, BlockGroup.FREE),
	FREE_D_SPOT3_P_BLUE_LINE (0x01ff, BlockGroup.FREE),
	SPIKE_NORMAL (0x0200, BlockGroup.SPIKE),
	SPIKE_BARELY_OUT (0x0201, BlockGroup.SPIKE),
	SPIKE_HALF_OUT (0x0202, BlockGroup.SPIKE),
	SPIKE_FULLY_OUT_1 (0x0203, BlockGroup.SPIKE),
	SPIKE_FULLY_OUT_2 (0x0204, BlockGroup.SPIKE),
	SPIKE_OUT_1 (0x0205, BlockGroup.SPIKE),
	SPIKE_OUT_2 (0x0206, BlockGroup.SPIKE),
	SPIKE_HALF_OUT_2 (0x0207, BlockGroup.SPIKE),
	SPIKE_BARELY_OUT_2 (0x0208, BlockGroup.SPIKE),
	SPIKE_DISABLED (0x0209, BlockGroup.SPIKE),
	GATE_CLOSED (0x0300, BlockGroup.GATE),
	GATE_OPEN (0x0301, BlockGroup.GATE),
	TAPEST_P_WITH_LATTICE (0x0400, BlockGroup.TAPEST),
	TAPEST_P_ALT_DESIGN (0x0401, BlockGroup.TAPEST),
	TAPEST_P_NORMAL (0x0402, BlockGroup.TAPEST),
	TAPEST_P_BLACK (0x0403, BlockGroup.TAPEST),
	POTION_EMPTY (0x0500, BlockGroup.POTION),
	POTION_HEALTH (0x0501, BlockGroup.POTION),
	POTION_LIFE (0x0502, BlockGroup.POTION),
	POTION_FEATHER_FALL (0x0503, BlockGroup.POTION),
	POTION_INVERT (0x0504, BlockGroup.POTION),
	POTION_POISON (0x0505, BlockGroup.POTION),
	POTION_OPEN (0x0506, BlockGroup.POTION),
	TTOP_P_WITH_LATTICE (0x0600, BlockGroup.TTOP),
	TTOP_P_ALT_DESIGN (0x0601, BlockGroup.TTOP),
	TTOP_P_NORMAL (0x0602, BlockGroup.TTOP),
	TTOP_P_BLACK_1 (0x0603, BlockGroup.TTOP),
	TTOP_P_BLACK_2 (0x0604, BlockGroup.TTOP),
	TTOP_P_ALT_DESIGN_AND_BOTTOM (0x0605, BlockGroup.TTOP),
	TTOP_P_WITH_BOTTOM (0x0606, BlockGroup.TTOP),
	TTOP_P_WITH_WINDOW (0x0607, BlockGroup.TTOP),
	CHOMP_NORMAL (0x0700, BlockGroup.CHOMP),
	CHOMP_HALF_OPEN (0x0701, BlockGroup.CHOMP),
	CHOMP_CLOSED (0x0702, BlockGroup.CHOMP),
	CHOMP_PART_OPEN (0x0703, BlockGroup.CHOMP),
	CHOMP_EXTRA_OPEN (0x0704, BlockGroup.CHOMP),
	CHOMP_STUCK_OPEN (0x0705, BlockGroup.CHOMP),
	WALL_D_NORMAL_P_BLUE_LINE (0x0800, BlockGroup.WALL),
	WALL_D_NORMAL_P_NO_BLUE_LINE (0x0801, BlockGroup.WALL);

	public final int value;
	public final BlockGroup group;

	private Modifier(int value, BlockGroup group) {
		this.value = value;
		this.group = group;
	}

	private static final Map<Integer, Modifier> sMap = new HashMap<Integer, Modifier>();

	static {
		for (Modifier mod : Modifier.values())
			sMap.put(mod.value, mod);
	}

	public static Modifier get(int value) {
		return sMap.get(value);
	}

	public static Modifier get(BlockGroup group, int value) {
		return sMap.get(group.modifierOffset + value);
	}
}
