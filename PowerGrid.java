import java.io.*;
import java.util.*;

public class PowerGrid {
    private static class Edge {
        private int start, end, weight;
        private String name;
        // private static final String[] LETTERS = {null, "a", "b", "c", "d", "e", "f"};

        private Edge(int start, int end, int weight, String name) {
            this.start = start;
            this.end = end;
            this.weight = weight;
            this.name = name;
        }
    }

    private static final int NIL = -1, INFINITY = Integer.MAX_VALUE, MAX_VERTICES = 1000;
    private static long totalWireLength;
    public static List<Edge> mst(List<Edge>[] graph) {
        int[][] predecessorsAndCosts = new int[graph.length][];
        predecessorsAndCosts[1] = new int[]{NIL, 0};
        for (int i = 2; i < graph.length; i++)
            predecessorsAndCosts[i] = new int[]{NIL, INFINITY};
        final Comparator<Integer> vertexComparator = (x, y) -> {
            int xCost = predecessorsAndCosts[x][1], yCost = predecessorsAndCosts[y][1];
            return xCost != yCost
                ? xCost - yCost
                : x - y;
        };
        PriorityQueue<Integer> unvisited = new PriorityQueue<>(vertexComparator);
        for (int i = 1; i < graph.length; i++)
            unvisited.add(i);
        boolean[] visited = new boolean[graph.length];
        while (!unvisited.isEmpty()) {
            int min = unvisited.poll();
            visited[min] = true;
            if (graph[min] == null)
                continue;
            for (Edge e : graph[min]) {
                int vertex = e.end;
                if (!visited[vertex]) {
                    int cost = predecessorsAndCosts[vertex][1];
                    int weight = e.weight;
                    if (weight < cost) {
                        unvisited.remove(vertex);
                        int[] predecessorAndCost = predecessorsAndCosts[vertex];
                        predecessorAndCost[0] = min;
                        predecessorAndCost[1] = weight;
                        unvisited.add(vertex);
                    }
                }
            }
        }
        for (int i = 1; i < visited.length; i++)
            if (predecessorsAndCosts[i][1] == INFINITY) {
                System.err.println("No solution.");
                System.exit(0);
            }
        List<Edge> mst = new ArrayList<>();
        for (int i = 2; i < graph.length; i++) {
            int predecessor = predecessorsAndCosts[i][0];
            for (Edge e : graph[predecessor])
                if (e.end == i) {
                    mst.add(e);
                    totalWireLength += e.weight;
                }
        }
        final Comparator<Edge> edgeComparator = (x, y) -> {
            int lexicographicalOrder = x.name.compareTo(y.name);
            return lexicographicalOrder != 0
                ? lexicographicalOrder
                : x.weight - y.weight;
        };
        Collections.sort(mst, edgeComparator);
        return mst;
    }

    @SuppressWarnings("unchecked")
    private static List<Edge>[] init() {
        List<List<Edge>> graph = new ArrayList<>();
        List<Edge> a = new ArrayList<>(), b = new ArrayList<>(), c = new ArrayList<>(), d = new ArrayList<>(), e = new ArrayList<>(), f = new ArrayList<>();
        Edge ab = new Edge(1, 2, 3, null), ba = new Edge(2, 1, 3, null);
        a.add(ab);
        b.add(ba);
        Edge af = new Edge(1, 6, 5, null), fa = new Edge(6, 1, 5, null);
        a.add(af);
        f.add(fa);
        Edge ae = new Edge(1, 5, 6, null), ea = new Edge(5, 1, 6, null);
        a.add(ae);
        e.add(ea);
        Edge bf = new Edge(2, 6, 4, null), fb = new Edge(6, 2, 4, null);
        b.add(bf);
        f.add(fb);
        Edge ef = new Edge(5, 6, 2, null), fe = new Edge(6, 5, 2, null);
        e.add(ef);
        f.add(fe);
        Edge bc = new Edge(2, 3, 1, null), cb = new Edge(3, 2, 1, null);
        b.add(bc);
        c.add(cb);
        Edge cf = new Edge(3, 6, 4, null), fc = new Edge(6, 3, 4, null);
        c.add(cf);
        f.add(fc);
        Edge cd = new Edge(3, 4, 6, null), dc = new Edge(4, 3, 6, null);
        c.add(cd);
        d.add(dc);
        Edge de = new Edge(4, 5, 8, null), ed = new Edge(5, 4, 8, null);
        d.add(de);
        e.add(ed);
        Edge df = new Edge(4, 6, 5, null), fd = new Edge(6, 4, 5, null);
        d.add(df);
        f.add(fd);
        graph.add(null);
        graph.add(a);
        graph.add(b);
        graph.add(c);
        graph.add(d);
        graph.add(e);
        graph.add(f);
        return (List<Edge>[]) graph.toArray();
    }
    
    private static boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java PowerGrid <input file>");
            System.exit(1);
        }
        try (Scanner s = new Scanner(new File(args[0]))) {
            String firstLine = s.nextLine();
            if (!isNumber(firstLine)) {
                System.err.println("Error: Invalid number of vertices '" + firstLine + "' on line 1.");
                System.exit(1);
            }
            int numberOfVertices = Integer.parseInt(firstLine);
            if (numberOfVertices > MAX_VERTICES || numberOfVertices < 1) {
                System.err.println("Error: Invalid number of vertices '" + numberOfVertices + "' on line 1.");
                System.exit(1);
            }
            List<Edge>[] graph = (List<Edge>[]) new List[numberOfVertices + 1];
            boolean[][] connected = new boolean[numberOfVertices + 1][numberOfVertices + 1];
            for (int i = 2; s.hasNextLine(); i++) {
                String line = s.nextLine().trim();
                String[] components = line.split(",", -1);
                if (components.length != 4) {
                    System.err.println("Error: Invalid edge data '" + line + "' on line " + i + ".");
                    System.exit(1);
                }
                if (!isNumber(components[0])) {
                    System.err.println("Error: Starting vertex '" + components[0] + "' on line " + i + " is not among valid values 1-" + numberOfVertices + ".");
                    System.exit(1);
                }
                if (!isNumber(components[1])) {
                    System.err.println("Error: Ending vertex '" + components[1] + "' on line " + i + " is not among valid values 1-" + numberOfVertices + ".");
                    System.exit(1);
                }
                int start = Integer.parseInt(components[0]);
                if (start < 1 || start > numberOfVertices) {
                    System.err.println("Error: Starting vertex '" + start + "' on line " + i + " is not among valid values 1-" + numberOfVertices + ".");
                    System.exit(1);
                }
                int end = Integer.parseInt(components[1]);
                if (end < 1 || end > numberOfVertices) {
                    System.err.println("Error: Ending vertex '" + end + "' on line " + i + " is not among valid values 1-" + numberOfVertices + ".");
                    System.exit(1);
                }
                if (!isNumber(components[2])) {
                    System.err.println("Error: Invalid edge weight '" + components[2] + "' on line " + i + ".");
                    System.exit(1);
                }
                int weight = Integer.parseInt(components[2]);
                if (weight <= 0) {
                    System.err.println("Error: Invalid edge weight '" + weight + "' on line " + i + ".");
                    System.exit(1);
                }
                String name = components[3];
                if (connected[start][end] || connected[end][start]) {
                    System.err.println("Error: Duplicate edge '" + line + "' found on line " + i + ".");
                    System.exit(1);
                }
                Edge edge = new Edge(start, end, weight, name);
                if (graph[start] == null)
                    graph[start] = new ArrayList<>();
                graph[start].add(edge);
                Edge reverseEdge = new Edge(end, start, weight, name);
                if (graph[end] == null)
                    graph[end] = new ArrayList<>();
                graph[end].add(reverseEdge);
                connected[start][end] = true;
                connected[end][start] = true;
            }
            IOException ioException = s.ioException();
            if (ioException != null)
                throw ioException;
            List<Edge> mst = mst(graph);
            System.out.println("Total wire length (meters): " + totalWireLength);
            for (Edge e : mst)
                System.out.println(e.name + " [" + e.weight + "]");
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.err.println("Error: Cannot open file '" + args[0] + "'.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error: An I/O error occurred reading '" + args[0] + "'.");
            System.exit(1);
        }
    }
}
