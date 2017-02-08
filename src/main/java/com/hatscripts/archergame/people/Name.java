package com.hatscripts.archergame.people;

public class Name {

	private final String firstName;
	private final String[] middleNames;
	private final String lastName;

	public Name(String firstName, String lastName) {
		this(firstName, null, lastName);
	}

	public Name(String firstName, String[] middleNames, String lastName) {
		this.firstName = firstName;
		this.middleNames = middleNames;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(firstName);
		if (middleNames != null) {
			for (String middleName : middleNames) {
				sb.append(' ');
				sb.append(middleName);
			}
		}
		sb.append(' ');
		sb.append(lastName);

		return sb.toString();
	}
}
