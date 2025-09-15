import java.util.*;

public class CycleInDirectedGraph {
    static List<List<Integer>> graph;
    static boolean[] visited;
    static boolean[] recStack;
    static int[] parent;  // To reconstruct cycle

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int V = sc.nextInt();
        int E = sc.nextInt();

        graph = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < E; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            graph.get(u).add(v); // directed edge
        }

        visited = new boolean[V];
        recStack = new boolean[V];
        parent = new int[V];
        Arrays.fill(parent, -1);

        boolean hasCycle = false;
        List<Integer> cycle = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                cycle = dfs(i);
                if (!cycle.isEmpty()) {
                    hasCycle = true;
                    break;
                }
            }
        }

        if (hasCycle) {
            System.out.println("Yes");
            System.out.print("Cycle: ");
            for (int i = 0; i < cycle.size(); i++) {
                System.out.print(cycle.get(i));
                if (i < cycle.size() - 1) System.out.print(" -> ");
            }
            System.out.println();
        } else {
            System.out.println("No");
        }

        sc.close();
    }

    static List<Integer> dfs(int node) {
        visited[node] = true;
        recStack[node] = true;

        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                parent[neighbor] = node;
                List<Integer> cycle = dfs(neighbor);
                if (!cycle.isEmpty()) return cycle;
            } else if (recStack[neighbor]) {
                // Cycle detected → reconstruct path
                return buildCycle(node, neighbor);
            }
        }

        recStack[node] = false;
        return new ArrayList<>();
    }

    static List<Integer> buildCycle(int current, int start) {
        List<Integer> cycle = new ArrayList<>();
        cycle.add(start);

        int node = current;
        while (node != start) {
            cycle.add(node);
            node = parent[node];
        }
        cycle.add(start); // close the cycle
        Collections.reverse(cycle);
        return cycle;
    }
}
