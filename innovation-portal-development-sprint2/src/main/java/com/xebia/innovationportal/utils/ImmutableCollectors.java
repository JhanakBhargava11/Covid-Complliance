package com.xebia.innovationportal.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;

public class ImmutableCollectors {

	public static <t> Collector<t, List<t>, List<t>> toImmutableList() {
		return Collector.of(ArrayList::new, List::add, (left, right) -> {
			left.addAll(right);
			return left;
		}, Collections::unmodifiableList);
	}

	public static <t> Collector<t, Set<t>, Set<t>> toImmutableSet() {
		return Collector.of(LinkedHashSet::new, Set::add, (left, right) -> {
			left.addAll(right);
			return left;
		}, Collections::unmodifiableSet);
	}

}
