public class PowerGrid {
    private class Edge {
        private int end, weight;
        private String name;
        private Edge(int end, int weight, String name) {
            this.end = end;
            this.weight = weight;
            this.name = name;
        }
    }
}
