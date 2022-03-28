/*
 * Copyright 2022 IQTIG – Institut für Qualitätssicherung und Transparenz im Gesundheitswesen.
 * Dieser Code ist urheberrechtlich geschützt (Copyright). Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet, beim IQTIG.
 * Wer gegen das Urheberrecht verstößt, macht sich gem. § 106 ff Urhebergesetz strafbar. Er wird zudem kostenpflichtig abgemahnt und muss
 * Schadensersatz leisten.
 */
package de.koelle.christian.wicket.demo;

import org.apache.wicket.util.io.IClusterable;
import de.koelle.christian.wicket.demo.shared.common.A;
import de.koelle.christian.wicket.demo.shared.common.B;
import de.koelle.christian.wicket.demo.shared.common.C;

public class HomePageSelectionMO implements IClusterable {

	private A a;
	private B b;
	private C c;

	public String a() {
		return nullResolveString(a);
	}

	public String b() {
		return nullResolveString(b);
	}

	public String c() {
		return nullResolveString(c);
	}

	public A getA() {
		return a;
	}

	public B getB() {
		return b;
	}

	public C getC() {
		return c;
	}

	public void setA(final A a) {
		this.a = a;
	}

	public void setB(final B b) {
		this.b = b;
	}

	public void setC(final C c) {
		this.c = c;
	}

	private <T extends Enum> String nullResolveString(final T a) {
		return a == null ?
			"-" :
			a.toString();
	}
}
