import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class DepthFirstSearch {
    private List<List<List<Integer>>> graph; //weighted graph

    private List<Boolean> visited;

    public DepthFirstSearch() {
        graph = new ArrayList<>();
        visited = new ArrayList<>();
    }

    public void setGraph(List<List<List<Integer>>> graph) {
        this.graph = graph;
        this.visited = new ArrayList<>(Collections.nCopies(graph.size(),false));
    }

    public List<Integer> dfs(int vertex){
        Stack<Integer> stack = new Stack<>();
        List<Integer> resList = new ArrayList<>();

        visited = new ArrayList<>(Collections.nCopies(graph.size(), false));
        stack.push(vertex);

        while(!stack.empty()) {
            vertex = stack.pop();
            visited.set(vertex,true);
            resList.add(vertex);

            List<List<Integer>> sublist = graph.get(vertex);

            for (List<Integer> tops : sublist) {
                if (!visited.get(tops.get(0))) {
                    stack.push(tops.get(0));
                }
            }
        }

        return resList;
    }

    public boolean isConnected(int x, int y) {
        List<Integer> tops = dfs(x);

        return tops.contains(y);
    }
}
