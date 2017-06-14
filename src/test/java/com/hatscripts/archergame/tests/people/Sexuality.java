package com.hatscripts.archergame.tests.people;

import com.hatscripts.archergame.tests.Random;

public enum Sexuality {
	HETEROSEXUAL,
	HOMOSEXUAL,
	BISEXUAL;

	public static Sexuality getRandom() {
		int ran = Random.integer(0, 100);
		if (ran > 3) {
			return HETEROSEXUAL;
		} else if (ran > 1) {
			return HOMOSEXUAL;
		} else {
			return BISEXUAL;
		}
	}
}
