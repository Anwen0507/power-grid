import java.util.*;

public class PowerGrid {
    private static class Edge {
        private int start, end, weight;
        private String name;
        private static final String[] LETTERS = {null, "a", "b", "c", "d", "e", "f"};

        private Edge(int start, int end, int weight, String name) {
            this.start = start;
            this.end = end;
            this.weight = weight;
            this.name = name;
        }

        @Override
        public String toString() {
            return LETTERS[start] + LETTERS[end];
        }
    }

    private static final int INFINITY = Integer.MAX_VALUE;
    public static List<Edge> mst(List<List<Edge>> graph) {
        Map<Integer, Integer[]> predecessorsAndCosts = new HashMap<>();
        final Integer[] init = {null, INFINITY};
        for (int i = 1; i < graph.size(); i++)
            predecessorsAndCosts.put(i, init);
        final Comparator<Integer> comparator = (x, y) -> {
            int xCost = predecessorsAndCosts.get(x)[1], yCost = predecessorsAndCosts.get(y)[1];
            return xCost != yCost
                ? xCost - yCost
                : x - y;
        };
        PriorityQueue<Integer> unvisited = new PriorityQueue<>(comparator);
        for (int i = 1; i < graph.size(); i++)
            unvisited.add(i);
        List<Integer> visited = new ArrayList<>();
        while (!unvisited.isEmpty()) {
            int min = unvisited.poll();
            visited.add(min);
            for (Edge e : graph.get(min)) {
                int vertex = e.end;
                if (!visited.contains(vertex)) {
                    int cost = predecessorsAndCosts.get(vertex)[1];
                    int weight = e.weight;
                    if (weight < cost) {
                        Integer[] predecessorAndCost = predecessorsAndCosts.get(vertex);
                        predecessorAndCost[0] = min;
                        predecessorAndCost[1] = weight;
                    }
                }
            }
        }
        List<Edge> mst = new ArrayList<>();
        for (int i = 1; i < graph.size(); i++) {
            int predecessor = predecessorsAndCosts.get(i)[0], weight = predecessorsAndCosts.get(i)[1];
            mst.add(new Edge(predecessor, i, weight, null));
        }
        return mst;
    }

    private static List<List<Edge>> init() {
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
        graph.add(null);
        graph.add(a);
        graph.add(b);
        graph.add(c);
        graph.add(d);
        graph.add(e);
        graph.add(f);
        return graph;
    }

    public static void main(String[] args) {
        List<List<Edge>> graph = init();
        System.out.println(mst(graph));
    }
}
