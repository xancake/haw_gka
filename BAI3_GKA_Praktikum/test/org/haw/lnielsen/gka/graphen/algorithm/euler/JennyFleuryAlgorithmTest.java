package org.haw.lnielsen.gka.graphen.algorithm.euler;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.graph.DefaultEdge;

public class JennyFleuryAlgorithmTest extends EulerAlgorithmTest_A {
	@Override
	protected EulerAlgorithm_I<Knoten, DefaultEdge> createEulerAlgorithm() {
		return new JennyFleuryAlgorithm<>();
	}
}
