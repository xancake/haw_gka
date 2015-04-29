package org.haw.lnielsen.gka.graphen.ui.generator.factory;

import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.generate.StarGraphGenerator;

public class StarGraphGeneratorFactory<V, E> extends GraphGeneratorFactory<V, E, V> {
	@Override
	protected String[] initParameterNames() {
		return new String[] {"Vertex-Count"};
	}
	
	@Override
	protected GraphGenerator<V, E, V> createGeneratorImpl(Integer... parameter) {
		return new StarGraphGenerator<>(parameter[0]);
	}
	
	@Override
	public String toString() {
		return "Star Graph Generator";
	}
}
