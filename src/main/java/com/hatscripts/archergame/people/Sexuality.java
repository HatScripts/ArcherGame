package com.hatscripts.archergame.people;

import com.hatscripts.archergame.utils.Random;

public enum Sexuality {
	HETEROSEXUAL,
	HOMOSEXUAL,
	BISEXUAL;

	public static Sexuality getRandom() {
		int ran = Random.nextInt(0, 100);
		if (ran > 3) {
			return HETEROSEXUAL;
		} else if (ran > 1) {
			return HOMOSEXUAL;
		} else {
			return BISEXUAL;
		}
	}
}
