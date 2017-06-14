package com.hatscripts.archergame.tests;

import java.util.concurrent.TimeUnit;

public final class Time {
	public static void main(String[] args) {
		System.out.println("formatSimple(0): " + formatSimple(0));
		System.out.println("formatSimple(1000): " + formatSimple(1000));
		System.out.println("formatSimple(10_000): " + formatSimple(10_000));
	}

	public static String formatSimple(long millis) {
		return String.format("%1$tH:%1$tM:%1$tS", millis);
	}

	/**
	 * Converts seconds to a {@code String} in the format of HH:MM:SS.
	 *
	 * @param t The time to format, in seconds
	 * @return The formatted {@code String}
	 */
	public static String format(long t) {
		if (t < 0) {
			throw new IllegalArgumentException("Duration must be greater than zero");
		}

		long day = TimeUnit.SECONDS.toDays(t);
		long hr = TimeUnit.SECONDS.toHours(t) - TimeUnit.DAYS.toHours(day);
		long min = TimeUnit.SECONDS.toMinutes(t) - TimeUnit.DAYS.toMinutes(day) -
				TimeUnit.HOURS.toMinutes(hr);
		long sec = TimeUnit.SECONDS.toSeconds(t) - TimeUnit.DAYS.toSeconds(day) -
				TimeUnit.HOURS.toSeconds(hr) - TimeUnit.MINUTES.toSeconds(min);

		StringBuilder sb = new StringBuilder();
		if (day > 0) {
			sb.append(String.format("%d:%02d", day, hr));
		} else {
			sb.append(String.format("%d", hr));
		}
		sb.append(String.format(":%02d:%02d", min, sec));
		return sb.toString();
	}
}