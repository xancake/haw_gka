package org.haw.lnielsen.gka.graphen.loader;

import static org.junit.Assert.*;
import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Before;
import org.junit.Test;

public abstract class GraphParserTest {
	private GraphParser_I myGraphParser;
	
	@Before
	public void setUp() {
		myGraphParser = createParser();
	}
	
	@Test
	public void testParseGraph_Undirected() throws Exception {
		Graph<Knoten, DefaultEdge> graph = myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/undirected.graph"));
		assertEquals(5, graph.vertexSet().size());
		assertTrue(graph.containsVertex(new Knoten("a")));
		assertTrue(graph.containsVertex(new Knoten("b")));
		assertTrue(graph.containsVertex(new Knoten("c")));
		assertTrue(graph.containsVertex(new Knoten("d")));
		assertTrue(graph.containsVertex(new Knoten("e")));
		assertEquals(5, graph.edgeSet().size());
		assertTrue(graph.containsEdge(new Knoten("a"), new Knoten("b")));
		assertTrue(graph.containsEdge(new Knoten("a"), new Knoten("c")));
		assertTrue(graph.containsEdge(new Knoten("b"), new Knoten("d")));
		assertTrue(graph.containsEdge(new Knoten("b"), new Knoten("e")));
		assertTrue(graph.containsEdge(new Knoten("d"), new Knoten("e")));
	}
	
	@Test
	public void testParseGraph_UndirectedWeighted() throws Exception {
		Graph<Knoten, DefaultEdge> graph = myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/undirected weighted.graph"));
		assertEquals(5, graph.vertexSet().size());
		assertTrue(graph.containsVertex(new Knoten("a")));
		assertTrue(graph.containsVertex(new Knoten("b")));
		assertTrue(graph.containsVertex(new Knoten("c")));
		assertTrue(graph.containsVertex(new Knoten("d")));
		assertTrue(graph.containsVertex(new Knoten("e")));
		assertEquals(5, graph.edgeSet().size());
		assertTrue(graph.containsEdge(new Knoten("a"), new Knoten("b")));
		assertTrue(graph.containsEdge(new Knoten("a"), new Knoten("c")));
		assertTrue(graph.containsEdge(new Knoten("b"), new Knoten("d")));
		assertTrue(graph.containsEdge(new Knoten("b"), new Knoten("e")));
		assertTrue(graph.containsEdge(new Knoten("d"), new Knoten("e")));
		assertEquals(10, graph.getEdgeWeight(graph.getEdge(new Knoten("a"), new Knoten("b"))), 0);
		assertEquals(50, graph.getEdgeWeight(graph.getEdge(new Knoten("a"), new Knoten("c"))), 0);
		assertEquals(10, graph.getEdgeWeight(graph.getEdge(new Knoten("b"), new Knoten("d"))), 0);
		assertEquals(20, graph.getEdgeWeight(graph.getEdge(new Knoten("b"), new Knoten("e"))), 0);
		assertEquals(40, graph.getEdgeWeight(graph.getEdge(new Knoten("d"), new Knoten("e"))), 0);
	}
	
	@Test
	public void testParseGraph_Directed() throws Exception {
		Graph<Knoten, DefaultEdge> graph = myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/directed.graph"));
		assertEquals(5, graph.vertexSet().size());
		assertTrue(graph.containsVertex(new Knoten("a")));
		assertTrue(graph.containsVertex(new Knoten("b")));
		assertTrue(graph.containsVertex(new Knoten("c")));
		assertTrue(graph.containsVertex(new Knoten("d")));
		assertTrue(graph.containsVertex(new Knoten("e")));
		assertEquals(7, graph.edgeSet().size());
		assertTrue(graph.containsEdge(new Knoten("a"), new Knoten("b")));
		assertTrue(graph.containsEdge(new Knoten("a"), new Knoten("c")));
		assertTrue(graph.containsEdge(new Knoten("b"), new Knoten("b")));
		assertTrue(graph.containsEdge(new Knoten("b"), new Knoten("d")));
		assertTrue(graph.containsEdge(new Knoten("c"), new Knoten("a")));
		assertTrue(graph.containsEdge(new Knoten("d"), new Knoten("e")));
		assertTrue(graph.containsEdge(new Knoten("e"), new Knoten("b")));
	}
	
	@Test
	public void testParseGraph_DirectedWeighted() throws Exception {
		Graph<Knoten, DefaultEdge> graph = myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/directed weighted.graph"));
		assertEquals(5, graph.vertexSet().size());
		assertTrue(graph.containsVertex(new Knoten("a")));
		assertTrue(graph.containsVertex(new Knoten("b")));
		assertTrue(graph.containsVertex(new Knoten("c")));
		assertTrue(graph.containsVertex(new Knoten("d")));
		assertTrue(graph.containsVertex(new Knoten("e")));
		assertEquals(7, graph.edgeSet().size());
		assertTrue(graph.containsEdge(new Knoten("a"), new Knoten("b")));
		assertTrue(graph.containsEdge(new Knoten("a"), new Knoten("c")));
		assertTrue(graph.containsEdge(new Knoten("b"), new Knoten("b")));
		assertTrue(graph.containsEdge(new Knoten("b"), new Knoten("d")));
		assertTrue(graph.containsEdge(new Knoten("c"), new Knoten("a")));
		assertTrue(graph.containsEdge(new Knoten("d"), new Knoten("e")));
		assertTrue(graph.containsEdge(new Knoten("e"), new Knoten("b")));
		assertEquals(10, graph.getEdgeWeight(graph.getEdge(new Knoten("a"), new Knoten("b"))), 0);
		assertEquals(50, graph.getEdgeWeight(graph.getEdge(new Knoten("a"), new Knoten("c"))), 0);
		assertEquals(30, graph.getEdgeWeight(graph.getEdge(new Knoten("b"), new Knoten("b"))), 0);
		assertEquals(10, graph.getEdgeWeight(graph.getEdge(new Knoten("b"), new Knoten("d"))), 0);
		assertEquals(30, graph.getEdgeWeight(graph.getEdge(new Knoten("c"), new Knoten("a"))), 0);
		assertEquals(40, graph.getEdgeWeight(graph.getEdge(new Knoten("d"), new Knoten("e"))), 0);
		assertEquals(20, graph.getEdgeWeight(graph.getEdge(new Knoten("e"), new Knoten("b"))), 0);
	}
	
	protected abstract GraphParser_I createParser();
}