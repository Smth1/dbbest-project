import java.util.Collections;
import java.util.List;

import java.util.ArrayList;

public class DijkstraAlgorithm {
    private List<List<List<Integer>>> graph; //weighted graph

    private List<Integer> distances;
    private List<Boolean> used;

    public DijkstraAlgorithm() {
        graph = new ArrayList<>();
        distances = new ArrayList<>();
        used = new ArrayList<>();
    }

    public void setGraph(List<List<List<Integer>>> graph) {
        this.graph = graph;
        distances = new ArrayList<>(Collections.nCopies(graph.size(),Integer.MAX_VALUE));
        this.used = new ArrayList<>(Collections.nCopies(graph.size(),false));
    }

    public List<Integer> shortestPath(int start) {
        distances = new ArrayList<>(Collections.nCopies(graph.size(),Integer.MAX_VALUE));
        distances.set(start, 0);

        for (int i=0; i < graph.size(); i++) {
            int v = -1;
            for (int j = 0; j < graph.size(); j++) {
                if (!used.get(j) && (v == -1 || distances.get(j) < distances.get(v)))
                    v = j;
            }

            if (distances.get(v) == Integer.MAX_VALUE) {
                break;
            }

            used.set(v,true);

            for (int j=0; j < graph.get(v).size(); j++) {
                int to = graph.get(v).get(j).get(0),
                        len = graph.get(v).get(j).get(1);
                if (distances.get(v) + len < distances.get(to)) {
                    distances.set(to, distances.get(v) + len);
                }
            }
        }

        return distances;
    }

    public int shortestPath(int x, int y) {
        this.shortestPath(x);

        return distances.get(y);
    }
}
