package com.upwork.eas;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;


/**
 * Represent a graph.
 *
 * @author Eduardo Alfonso Sanchez
 * @since 1.0.0
 */
public class Network {

    private final boolean[][] matrix;

    /**
     * Constructs a network with more than 1 nodes
     * Represent an unguided graph.
     *
     * @param nodesNumber The number of nodes
     * @throws IllegalArgumentException if the number of nodes is less than or equal to 1
     * @since 1.0.0
     */
    public Network(int nodesNumber) {
        if (nodesNumber <= 1) {
            throw new IllegalArgumentException("The number of nodes must be greater than 1");
        }
        this.matrix = new boolean[nodesNumber][nodesNumber];
    }


    /**
     * Connect two different nodes in the current network.
     *
     * @param source The source node to connect. Begin in 1
     * @param target The target node to connect. Begin in 1
     * @throws IllegalArgumentException if:
     *                                  1. The source or target nodes are less than 1
     *                                  2. The source or target nodes are greater than the number of node, please see the constructor of {@link Network}
     *                                  3. The source and the target are equal. In other graph exist loop, but in this network we avoid that
     * @since 1.0.0
     */
    public void connect(int source, int target) {
        this.generalValidation(source, target);
        this.matrix[source - 1][target - 1] = this.matrix[target - 1][source - 1] = true;
    }

    /**
     * Verify if exist any path between source and target node
     *
     * @param source The source node. Begin in 1
     * @param target The target node. Begin in 1
     * @return true if exist any path between source and target node, false otherwise
     * @throws IllegalArgumentException if:
     *                                  1. The source or target nodes are less than 1
     *                                  2. The source or target nodes are greater than the number of node, please see the constructor of {@link Network}
     *                                  3. The source and the target are equal. In other graph exist loop, but in this network we avoid that
     */
    public boolean query(int source, int target) {
        this.generalValidation(source, target);
        source--;
        target--;

        if (this.matrix[source][target]) {
            return true;
        }

        if (this.hasAdjacent(source) && this.hasAdjacent(target)) {
            boolean[] visitedNode = new boolean[this.matrix.length];
            Queue<Integer> queue = new ArrayDeque<>();
            queue.add(source);
            visitedNode[source] = true;
            while (!queue.isEmpty()) {
                List<Integer> listAdj = this.getAdjacent(queue.poll());

                for (Integer node : listAdj) {
                    if (node.equals(target)) {
                        return true;
                    }
                    if (!queue.contains(node) && !visitedNode[node]) {
                        queue.add(node);
                        visitedNode[node] = true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Search the adjacent list nodes of one node.
     *
     * @param node The node for searching
     * @return The Adjacent List of nodes
     * @since 1.0.0
     */
    private List<Integer> getAdjacent(int node) {
        List<Integer> adjacent = new ArrayList<>();
        for (int i = 0; i < this.matrix[node].length; i++) {
            if (this.matrix[node][i]) {
                adjacent.add(i);
            }
        }
        return adjacent;
    }

    /**
     * Get a copy of the Adjacency matrix of the graph.
     * Immutability
     *
     * @return The Adjacency matrix of the graph
     * @since 1.0.0
     */
    public boolean[][] getMatrixCopy() {
        boolean[][] matrixCopy = new boolean[this.matrix.length][];
        for (int i = 0; i < this.matrix.length; i++) {
            matrixCopy[i] = Arrays.copyOf(this.matrix[i], this.matrix[i].length);
        }
        return matrixCopy;
    }

    /**
     * Validate general conditions for connect and query.
     *
     * @param source The source node to connect. Begin in 1
     * @param target The target node to connect. Begin in 1
     * @throws IllegalArgumentException if:
     *                                  1. The source or target nodes are less than 1
     *                                  2. The source or target nodes are greater than the number of node, please see the constructor of {@link Network}
     *                                  3. The source and the target are equal. In other graph exist loop, but in this network not
     * @since 1.0.0
     */
    private void generalValidation(int source, int target) {
        if (source < 1 || target < 1) {
            throw new IllegalArgumentException("The source and target nodes must be greater than or equal to 1");
        }

        if (source > this.matrix.length || target > this.matrix.length) {
            throw new IllegalArgumentException("The source and target nodes must be less than or equal to number of nodes");
        }

        if (source == target) {
            throw new IllegalArgumentException("The source and target are equals");
        }
    }

    /**
     * For knowing if the node has adjacent nodes.
     *
     * @return true if the node has any adjacent node
     * @since 1.0.0
     */
    private boolean hasAdjacent(int node) {
        for (int i = 0; i < this.matrix[node].length; i++) {
            if (this.matrix[node][i]) {
                return true;
            }

        }
        return false;
    }
}
