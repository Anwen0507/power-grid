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
            name = LETTERS[start] + LETTERS[end];
            return name;
        }
    }

    private static final int NIL = -1, INFINITY = Integer.MAX_VALUE;
    public static List<Edge> mst(List<List<Edge>> graph) {
        int[][] predecessorsAndCosts = new int[graph.size()][];
        for (int i = 1; i < graph.size(); i++)
            predecessorsAndCosts[i] = new int[]{NIL, INFINITY};
        final Comparator<Integer> vertexComparator = (x, y) -> {
            int xCost = predecessorsAndCosts[x][1], yCost = predecessorsAndCosts[y][1];
            return xCost != yCost
                ? xCost - yCost
                : x - y;
        };
        PriorityQueue<Integer> unvisited = new PriorityQueue<>(vertexComparator);
        for (int i = 1; i < graph.size(); i++)
            unvisited.add(i);
        boolean[] visited = new boolean[graph.size()];
        while (!unvisited.isEmpty()) {
            int min = unvisited.poll();
            visited[min] = true;
            for (Edge e : graph.get(min)) {
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
        List<Edge> mst = new ArrayList<>();
        for (int i = 2; i < graph.size(); i++) {
            int predecessor = predecessorsAndCosts[i][0];
            for (Edge e : graph.get(predecessor))
                if (e.end == i)
                    mst.add(e);
        }
        final Comparator<Edge> edgeComparator = (x, y) -> {
            return x.name.compareTo(y.name);
        };
        Collections.sort(mst, edgeComparator);
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
        return graph;
    }

    public static void main(String[] args) {
        List<List<Edge>> graph = init();
        System.out.println(graph);
        System.out.println(mst(graph));
    }
}
