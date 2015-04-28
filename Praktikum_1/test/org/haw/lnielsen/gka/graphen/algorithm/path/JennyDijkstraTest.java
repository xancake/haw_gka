package org.haw.lnielsen.gka.graphen.algorithm.path;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.graph.DefaultEdge;

public class JennyDijkstraTest extends ShortestPathTest_A {

	@Override
	protected ShortestPath_I<Knoten, DefaultEdge> createShortestPathAlgorithm() {
		return new JennyDijkstra<>();
	}

}
