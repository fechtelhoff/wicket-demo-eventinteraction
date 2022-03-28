/*
 * Copyright 2022 IQTIG – Institut für Qualitätssicherung und Transparenz im Gesundheitswesen.
 * Dieser Code ist urheberrechtlich geschützt (Copyright). Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet, beim IQTIG.
 * Wer gegen das Urheberrecht verstößt, macht sich gem. § 106 ff Urhebergesetz strafbar. Er wird zudem kostenpflichtig abgemahnt und muss
 * Schadensersatz leisten.
 */
package de.koelle.christian.wicket.demo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import de.koelle.christian.wicket.demo.shared.common.A;
import de.koelle.christian.wicket.demo.shared.common.B;
import de.koelle.christian.wicket.demo.shared.common.C;

public class HomePageService implements Serializable {

	private static final Map<Enum<?>, Set<Enum<?>>> VALUE_MAPPING = Map.ofEntries(
		Map.entry(A.A1, new TreeSet<>(Set.of(B.B1, B.B2, B.B3))),
		Map.entry(A.A2, new TreeSet<>(Set.of(B.B4, B.B5, B.B6))),
		Map.entry(A.A3, new TreeSet<>(Set.of())),
		Map.entry(A.A4, new TreeSet<>(Set.of(B.B7))),

		Map.entry(B.B1, new TreeSet<>(Set.of(C.C1, C.C2))),
		Map.entry(B.B2, new TreeSet<>(Set.of(C.C3))),
		Map.entry(B.B3, new TreeSet<>(Set.of(C.C4, C.C5))),
		Map.entry(B.B4, new TreeSet<>(Set.of(C.C6))),
		Map.entry(B.B5, new TreeSet<>(Set.of(C.C7, C.C8, C.C9))),
		Map.entry(B.B6, new TreeSet<>(Set.of(C.C10))),
		Map.entry(B.B7, new TreeSet<>(Set.of(C.C11)))
	);

	public <S extends Enum, O extends Enum> List<O> getOrangesForForAnApple(S s) {
		if (s == null) {
			return List.of();
		}
		final Set<O> result = (Set<O>) VALUE_MAPPING.get(s);
		return (result == null || result.isEmpty()) ?
			List.of() :
			new ArrayList<>(result);

	}

	public String getStringRepresentation() {
		return toStringRepresentation(VALUE_MAPPING, null, 0);
	}

	private String toStringRepresentation(Map<Enum<?>, Set<Enum<?>>> values, Enum<?> value, int idention) {
		if (value == null) {
			StringBuilder builder = new StringBuilder();
			for (Enum<?> child : values.keySet().stream()
				.filter(i -> i instanceof A)
				.sorted(Comparator.comparing(i -> i.name()))
				.collect(Collectors.toList())) {
				builder.append(toStringRepresentation(values, child, idention));
			}
			return builder.toString();
		} else if (!values.containsKey(value)) {
			return singelStringRepresentation(value, idention);
		}
		StringBuilder builder = new StringBuilder();
		builder.append(singelStringRepresentation(value, idention));
		for (Enum<?> child : values.get(value)) {
			builder.append(toStringRepresentation(values, child, idention + 1));
		}
		return builder.toString();
	}

	private String singelStringRepresentation(final Enum<?> value, final int idention) {
		String identionString = (idention == 0) ?
			"" :
			StringUtils.leftPad("", (idention - 1) * 4, " ") + "|---";
		return String.format("%n%s%s", identionString, value);
	}

}
