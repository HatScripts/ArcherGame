package com.hatscripts.archergame.tests.people;

import com.hatscripts.archergame.tests.Random;

public enum Gender {
	MALE,
	FEMALE;

	public static Gender getRandom() {
		return Random.bool() ? Gender.MALE : Gender.FEMALE;
	}

	public Name getRandomName() {
		String firstName = this == Gender.MALE
				? Names.getRandomMaleName()
				: Names.getRandomFemaleName();
		String lastName = Names.getRandomLastName();

		return new Name(firstName, lastName);
	}
}
