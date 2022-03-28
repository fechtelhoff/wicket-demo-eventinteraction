package de.koelle.christian.wicket.demo.shared.common;

public final class Preconditions {

	private Preconditions(){
		// intentionally blank
	}

	public static boolean checkArgument(final boolean expression) {
		if (!expression) throw new IllegalArgumentException();
		return true;
	}
}
