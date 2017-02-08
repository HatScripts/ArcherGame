package com.hatscripts.archergame.utils;

public final class Random {

	private static final java.util.Random RANDOM = new java.util.Random();

	public static boolean nextBoolean() {
		return RANDOM.nextBoolean();
	}

	public static int nextInt(int max) {
		return nextInt(0, max);
	}

	public static int nextInt(int min, int max) {
		if (max < min) {
			return max + RANDOM.nextInt(min - max);
		}
		return min + (max == min ? 0 : RANDOM.nextInt(max - min + 1));
	}

	public static double nextDouble(double min, double max) {
		return min + RANDOM.nextDouble() * (max - min);
	}

	public static double nextDouble() {
		return RANDOM.nextDouble();
	}

	public static int nextGaussian(int max) {
		return nextGaussian(0, max);
	}

	public static int nextGaussian(int min, int max) {
		return nextGaussian(min, max, min + (max - min) / 2);
	}

	public static int nextGaussian(int min, int max, int mean) {
		return nextGaussian(min, max, mean, (max + min) / 3);
	}

	public static int nextGaussian(int min, int max, int mean, int sd) {
		if (min == max) {
			return min;
		}
		int rand;
		do {
			rand = (int) (RANDOM.nextGaussian() * sd + mean);
		} while (rand < min || rand > max);
		return rand;
	}
}