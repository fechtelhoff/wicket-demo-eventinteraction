/*
 * Copyright 2022 IQTIG – Institut für Qualitätssicherung und Transparenz im Gesundheitswesen.
 * Dieser Code ist urheberrechtlich geschützt (Copyright). Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet, beim IQTIG.
 * Wer gegen das Urheberrecht verstößt, macht sich gem. § 106 ff Urhebergesetz strafbar. Er wird zudem kostenpflichtig abgemahnt und muss
 * Schadensersatz leisten.
 */
package de.koelle.christian.wicket.demo;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import de.koelle.christian.wicket.demo.shared.common.A;
import de.koelle.christian.wicket.demo.shared.common.B;
import de.koelle.christian.wicket.demo.shared.common.C;

class HomePageServiceTest {

	private final HomePageService service = new HomePageService();

	static Stream<Arguments> getOrangesForForAnAppleParams() {
		return Stream.of(
			Arguments.of(A.class),
			Arguments.of(B.class),
			Arguments.of(C.class)
		);
	}

	@SuppressWarnings("rawtypes")
	@MethodSource("getOrangesForForAnAppleParams")
	@ParameterizedTest
	void getOrangesForForAnApple(Class<Enum> x) {
		for (Enum value : x.getEnumConstants()) {
			var orangesForForAnApple = Assertions.assertDoesNotThrow(()-> service.getOrangesForForAnApple(value));
			System.out.printf("%-4s:%s\n", value, orangesForForAnApple);
		}
	}
	@Test
	void getStringRepresentation() {
		final String stringRepresentation = Assertions.assertDoesNotThrow(service::getStringRepresentation);
		System.out.println(stringRepresentation);
	}
}
