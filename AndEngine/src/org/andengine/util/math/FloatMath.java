package org.andengine.util.math;

public final class FloatMath {
	private FloatMath() {
		// Utility class.
	}

	public static float sin(final float value) {
		return (float) Math.sin(value);
	}

	public static float cos(final float value) {
		return (float) Math.cos(value);
	}

	public static float sqrt(final float value) {
		return (float) Math.sqrt(value);
	}

	public static float floor(final float value) {
		return (float) Math.floor(value);
	}

	public static float ceil(final float value) {
		return (float) Math.ceil(value);
	}
}
