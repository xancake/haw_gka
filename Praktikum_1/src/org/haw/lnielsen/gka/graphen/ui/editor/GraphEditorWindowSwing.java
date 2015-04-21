package org.haw.lnielsen.gka.graphen.ui.editor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.ui.swing.GraphFileFilter;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphSelectionModel;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.graph.JGraphSimpleLayout;

import de.xancake.ui.mvc.window.SwingWindowView_A;

/**
 * Die Swing-Implementation des Hauptfensters der Benutzeroberfläche.
 * 
 * @author Lars Nielsen
 */
public class GraphEditorWindowSwing extends SwingWindowView_A<Graph<Knoten, DefaultEdge>, GraphEditorWindowListener_I> implements GraphEditorWindow_I {
	private JButton myNewButton;
	private JButton myLoadButton;
	private JButton myStoreButton;
	private JButton myShortestPathButton;
	private JButton myTraverseButton;
	
	private JFileChooser myChooser;
	private JGraph myGraphComponent;
	
	public GraphEditorWindowSwing() {
		super("Graph");
	}
	
	@Override
	protected void initComponents() {
		File chooserCurrentDirectory = null;
		try {
			chooserCurrentDirectory = new File(ClassLoader.getSystemResource("loader").toURI());
		} catch(URISyntaxException e) {}
		myChooser = new JFileChooser(chooserCurrentDirectory);
		myChooser.setMultiSelectionEnabled(false);
		myChooser.setFileFilter(new GraphFileFilter());
		myGraphComponent = new JGraph();
		myGraphComponent.setEditable(false);
		myNewButton = new JButton("Neu...");
		myLoadButton = new JButton("Laden");
		myStoreButton = new JButton("Speichern");
		myShortestPathButton = new JButton("Kürzester Weg");
		myTraverseButton = new JButton("Traversieren");
	}
	
	@Override
	protected void initLayout(Container content) {
		JToolBar toolbar = new JToolBar();
		toolbar.add(myNewButton);
		toolbar.add(myLoadButton);
		toolbar.add(myStoreButton);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(myNewButton);
		buttonPanel.add(myLoadButton);
		buttonPanel.add(myStoreButton);
		buttonPanel.add(Box.createHorizontalStrut(11));
		buttonPanel.add(myShortestPathButton);
		buttonPanel.add(myTraverseButton);
		buttonPanel.add(Box.createHorizontalGlue());
		
		content.add(buttonPanel, BorderLayout.PAGE_START);
		content.add(new JScrollPane(myGraphComponent), BorderLayout.CENTER);
		myFrame.setSize(800, 600);
		myFrame.setLocationRelativeTo(null);
	}
	
	@Override
	protected void initListeners() {
		myNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myListener.onNewGraph();
			}
		});
		myLoadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(myChooser.showOpenDialog(myFrame) == JFileChooser.APPROVE_OPTION) {
					myListener.onLoadGraph(myChooser.getSelectedFile());
				}
			}
		});
		myStoreButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(myChooser.showSaveDialog(myFrame) == JFileChooser.APPROVE_OPTION) {
					myListener.onStoreGraph(myChooser.getSelectedFile());
				}
			}
		});
		myShortestPathButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphSelectionModel selectionModel = myGraphComponent.getSelectionModel();
				if(selectionModel.getSelectionCount() == 2) {
					Object[] selectedElements = selectionModel.getSelectionCells();
					Knoten start = (Knoten)((DefaultGraphCell)selectedElements[0]).getUserObject();
					Knoten end = (Knoten)((DefaultGraphCell)selectedElements[1]).getUserObject();
					myListener.onCalculateShortestPath(start, end);
				} else {
					JOptionPane.showMessageDialog(myFrame, "Es kann nur der kürzeste Pfad zwischen zwei Knoten berechnet werden. Bitte wählen Sie genau zwei Knoten aus (Strg+Mausklick).", "Fehler", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		myTraverseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphSelectionModel selectionModel = myGraphComponent.getSelectionModel();
				if(selectionModel.getSelectionCount() == 1) {
					Object selectedElement = selectionModel.getSelectionCell();
					Knoten start = (Knoten)((DefaultGraphCell)selectedElement).getUserObject();
					myListener.onTraverse(start);
				} else {
					JOptionPane.showMessageDialog(myFrame, "Es kann nur ein Knoten als Startknoten ausgewählt werden. Bitte wählen Sie genau einen Knoten aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	@Override
	public void fillViewWithModel(Graph<Knoten, DefaultEdge> model) {
		myGraphComponent.clearSelection();
		myGraphComponent.setModel(model != null ? new JGraphModelAdapter<Knoten, DefaultEdge>(model) : null);
		myGraphComponent.setEnabled(model != null);
		
		if(model != null) {
			JGraphFacade facade = new JGraphFacade(myGraphComponent);
			JGraphLayout layout = new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_CIRCLE);
			layout.run(facade);
			myGraphComponent.getGraphLayoutCache().edit(facade.createNestedMap(true, true));
		}
	}
	
	@Override
	public void showPath(GraphPath<Knoten, DefaultEdge> path) {
		StringBuilder message = new StringBuilder();
		if(path != null) {
			Graph<Knoten, DefaultEdge> graph = path.getGraph();
			message.append("<html>Der kürzeste Weg von ");
			message.append(path.getStartVertex());
			message.append(" nach ");
			message.append(path.getEndVertex());
			message.append(" führt über die Kanten <ol>");
			Knoten start = path.getStartVertex();
			for(DefaultEdge edge : path.getEdgeList()) {
				Knoten edgeStart = graph.getEdgeSource(edge);
				Knoten edgeTarget = graph.getEdgeTarget(edge);
				message.append("<li>");
				message.append(start.equals(edgeStart) ? edgeStart : edgeTarget);
				message.append(" - ");
				message.append(start.equals(edgeStart) ? edgeTarget : edgeStart);
				message.append("</li>");
				start = (start.equals(edgeStart) ? edgeTarget : edgeStart);
			}
			message.append("</ol> und hat ein Gewicht von ");
			message.append((int)path.getWeight());
			message.append("</html>");
		} else {
			Object[] selectedElements = myGraphComponent.getSelectionModel().getSelectionCells();
			message.append("Es gibt keinen Pfad von ");
			message.append(selectedElements[0]);
			message.append(" nach ");
			message.append(selectedElements[1]);
		}
		JOptionPane.showMessageDialog(myFrame, message.toString(), "Kürzester Weg", JOptionPane.PLAIN_MESSAGE);
	}
	
	@Override
	public void showTraverseTrace(List<Knoten> trace) {
		StringBuilder message = new StringBuilder();
		message.append("<html>Der Graph wurde in folgender Knotenreihenfolge traversiert:<ol>");
		for(Knoten k : trace) {
			message.append("<li>");
			message.append(k);
			message.append("</li>");
		}
		message.append("</ol></html>");
		JOptionPane.showMessageDialog(myFrame, message.toString(), "Traversierung", JOptionPane.PLAIN_MESSAGE);
	}
	
	@Override
	public void showFehlermeldung(String message) {
		JOptionPane.showMessageDialog(myFrame, message, "Fehler", JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public void showFehlermeldung(Throwable exception, boolean showTrace) {
		String message = "<html>";
		while(exception != null) {
			message += exception.getMessage() + "<br />";
			if(showTrace) {
				for(StackTraceElement traceElement : exception.getStackTrace()) {
					message += traceElement.toString() + "<br />";
				}
			}
			exception = exception.getCause();
		}
		message += "</html>";
		showFehlermeldung(message);
	}
}
