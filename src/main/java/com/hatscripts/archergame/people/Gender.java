package com.hatscripts.archergame.people;

import com.hatscripts.archergame.utils.Random;

public enum Gender {
	MALE,
	FEMALE;

	public static Gender getRandom() {
		return Random.nextBoolean() ? Gender.MALE : Gender.FEMALE;
	}

	public Name getRandomName() {
		String firstName = this == Gender.MALE
				? Names.getRandomMaleName()
				: Names.getRandomFemaleName();
		String lastName = Names.getRandomLastName();

		return new Name(firstName, lastName);
	}
}
