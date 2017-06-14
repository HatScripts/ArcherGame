package com.hatscripts.archergame.tests;

public final class Random {
	private static final java.util.Random RANDOM = new java.util.Random();

	public static boolean bool() {
		return RANDOM.nextBoolean();
	}

	public static int integer(int max) {
		return integer(0, max);
	}

	public static int integer(int min, int max) {
		if (max < min) {
			return max + RANDOM.nextInt(min - max);
		}
		return min + (max == min ? 0 : RANDOM.nextInt(max - min + 1));
	}

	public static double dbl() {
		return RANDOM.nextDouble();
	}

	public static double dbl(double min, double max) {
		return min + RANDOM.nextDouble() * (max - min);
	}

	public static double gaussian(double max) {
		return gaussian(0, max);
	}

	public static double gaussian(double min, double max) {
		return gaussian(min, max, min + (max - min) / 2);
	}

	public static double gaussian(double min, double max, double mean) {
		return gaussian(min, max, mean, (max + min) / 3);
	}

	public static double gaussian(double min, double max, double mean, double sd) {
		if (min == max) {
			return min;
		}
		double n;
		do {
			n = RANDOM.nextGaussian() * sd + mean;
		} while (n < min || n > max);
		return n;
	}
}