package cz.magix.maarifa.simple;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.tooling.GlobalGraphOperations;

public class LowLevelDbList {

	public LowLevelDbList() {
		EmbeddedGraphDatabase graphDb = new EmbeddedGraphDatabase("/tmp/neo4j");
		registerShutdownHook(graphDb);
		
		// List
		Iterable<Node> nodes = GlobalGraphOperations.at(graphDb).getAllNodes();
		for (Node node : nodes) {
			System.out.println("NodeID: " + node.getId());
			System.out.println("  Properties:");
			for (String key : node.getPropertyKeys()) {
				System.out.println("  Property: " + key + " = " + node.getProperty(key));
			}

			System.out.println("    Incoming relationships: ");
			for (Relationship relationship : node.getRelationships(Direction.INCOMING)) {
				System.out.println("      Relationship " + relationship.getType().name() + " from: " + relationship.getOtherNode(node).getId());
			}
			
			System.out.println("    Outgoing relationships: ");
			for (Relationship relationship : node.getRelationships(Direction.OUTGOING)) {
				System.out.println("      Relationship " + relationship.getType().name() + " to: " + relationship.getOtherNode(node).getId());
			}
		}
	}

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running example before it's completed)
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new LowLevelDbList();
	}
}
