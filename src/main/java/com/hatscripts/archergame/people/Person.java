package com.hatscripts.archergame.people;

import com.hatscripts.archergame.utils.Random;

public class Person {

	private final Name name;
	private final int age;
	private final Gender gender;
	private final Sexuality sexuality;
	private final Profession profession;

	public Person(Name name, int age, Gender gender,
				  Sexuality sexuality, Profession profession) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.sexuality = sexuality;
		this.profession = profession;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			Person person = generate();

			System.out.println(person);
		}
	}

	public static Person generate() {
		Gender gender = Gender.getRandom();
		Name name = gender.getRandomName();
		int age = Random.nextInt(18, 60);
		Sexuality sexuality = Sexuality.getRandom();

		return new Person(name, age, gender, sexuality, Profession.UNEMPLOYED);
	}

	@Override
	public String toString() {
		return "Person{" +
				"name=" + name +
				", age=" + age +
				", gender=" + gender +
				", sexuality=" + sexuality +
				", profession=" + profession +
				'}';
	}
}