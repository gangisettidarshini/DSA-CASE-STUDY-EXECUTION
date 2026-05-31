import java.util.*;

public class CycleTopoSort {

    static final int WHITE = 0, GREY = 1, BLACK = 2;

    static class TopoResult {
        List<String> order;
        List<String> cycle;

        TopoResult(List<String> o, List<String> c) {
            order = o;
            cycle = c;
        }
    }

    static TopoResult topoSortWithCycle(
            Map<String, List<String>> adj) {

        Map<String, Integer> color = new HashMap<>();
        Map<String, String> parent = new HashMap<>();

        List<String> order = new ArrayList<>();
        List<String> cycle = new ArrayList<>();

        for (String v : adj.keySet())
            color.put(v, WHITE);

        for (String u : adj.keySet()) {

            if (color.get(u) == WHITE) {

                if (dfs(u, adj, color,
                        parent, order, cycle))

                    return new TopoResult(null, cycle);
            }
        }

        Collections.reverse(order);

        return new TopoResult(
                order,
                Collections.emptyList());
    }

    static boolean dfs(
            String u,
            Map<String, List<String>> adj,
            Map<String, Integer> color,
            Map<String, String> parent,
            List<String> order,
            List<String> cycle) {

        color.put(u, GREY);

        for (String v :
                adj.getOrDefault(
                        u,
                        Collections.emptyList())) {

            if (color.get(v) == WHITE) {

                parent.put(v, u);

                if (dfs(v, adj,
                        color,
                        parent,
                        order,
                        cycle))
                    return true;

            }

            else if (color.get(v) == GREY) {

                // Build cycle

                cycle.add(v);

                String cur = u;

                while (!cur.equals(v)) {
                    cycle.add(cur);
                    cur = parent.get(cur);
                }

                cycle.add(v);

                Collections.reverse(cycle);

                return true;
            }
        }

        color.put(u, BLACK);

        order.add(u);

        return false;
    }

    public static void main(String[] args) {

        Map<String, List<String>> adj =
                new HashMap<>();

        adj.put("auth",
                Arrays.asList("ledger"));

        adj.put("payments",
                Arrays.asList(
                        "auth",
                        "fraud"));

        adj.put("kyc",
                Arrays.asList("auth"));

        adj.put("ledger",
                Arrays.asList("fraud"));

        adj.put("fraud",
                Arrays.asList(
                        "notify",
                        "ledger"));

        adj.put("notify",
                Arrays.asList("gateway"));

        adj.put("admin-ui",
                Arrays.asList(
                        "payments",
                        "kyc"));

        adj.put("customer-ui",
                Arrays.asList("payments"));

        adj.put("gateway",
                Arrays.asList(
                        "admin-ui",
                        "customer-ui"));

        TopoResult result =
                topoSortWithCycle(adj);

        if (result.order == null) {

            System.out.println(
                    "Cycle Detected:");

            System.out.println(
                    result.cycle);

        } else {

            System.out.println(
                    result.order);
        }
    }
}