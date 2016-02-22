import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Created by Vikno on 2/19/2016.
 */

/*
Клас для знаходження мінімального шляху між вершинами за алгоритмом Дейкстри
 */
class Graph {
    private final Map<String, Vertex> graph; //Карта для збереження вершин і шляхів
    public static String resPath = "";//Остаточний шлях
    public static int distance;//Загальна відстань

    /*
    Ребро графа
     */
    public static class Edge {
        public final String v1, v2;
        public final int dist;
        public Edge(String v1, String v2, int dist) {
            this.v1 = v1;
            this.v2 = v2;
            this.dist = dist;
        }
    }

    /*
    Вершина графа
     */
    public static class Vertex implements Comparable<Vertex> {
        public final String name;
        public int dist = Integer.MAX_VALUE;
        public Vertex previous = null;
        public final Map<Vertex, Integer> neighbours = new HashMap<Vertex,Integer>();

        public Vertex(String name) {
            this.name = name;
        }

        /*
        Вивід шляху
         */
        private void printPath() {
            if (this == this.previous) {
                System.out.printf("%s", this.name);
                resPath += this.name+" ";
          } else if (this.previous == null) {
                System.out.printf("%s(unreached)", this.name);
            } else {
                this.previous.printPath();
                System.out.printf(" -> %s(%d)", this.name, this.dist);
                resPath += this.name+" ";
                distance = this.dist;
            }


        }

        public int compareTo(Vertex other) {
            return Integer.compare(dist, other.dist);
        }
    }

    /*
    Ініціалізація графа множиною ребер
     */
    public Graph(Edge[] edges) {
        graph = new HashMap<String,Vertex>(edges.length);

        for (Edge e : edges) {
            if (!graph.containsKey(e.v1)) graph.put(e.v1, new Vertex(e.v1));
            if (!graph.containsKey(e.v2)) graph.put(e.v2, new Vertex(e.v2));
        }

        for (Edge e : edges) {
            graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
            //graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); //Для неорієнтованого графа
        }
    }

    /*
    Обчислення мінімально шляху алгоритмом дейкстри
     */
    public void dijkstra(String startName) {
        if (!graph.containsKey(startName)) {
            System.err.printf("Граф не має початкової вершини \"%s\"\n", startName);
            return;
        }
        final Vertex source = graph.get(startName);
        NavigableSet<Vertex> q = new TreeSet<Vertex>();

        for (Vertex v : graph.values()) {
            v.previous = v == source ? source : null;
            v.dist = v == source ? 0 : Integer.MAX_VALUE;
            q.add(v);
        }

        dijkstra(q);
    }
    /*
    Обчмслення мінімально шляху використовуючибінарний хіп
     */
    private void dijkstra(final NavigableSet<Vertex> q) {
        Vertex u, v;
        while (!q.isEmpty()) {

            u = q.pollFirst();
            if (u.dist == Integer.MAX_VALUE) break;
            for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
                v = a.getKey();

                final int alternateDist = u.dist + a.getValue();
                if (alternateDist < v.dist) {
                    q.remove(v);
                    v.dist = alternateDist;
                    v.previous = u;
                    q.add(v);
                }
            }
        }
    }

    /*
    Вивід шляху для однієї вершини
     */
    public void printPath(String endName) {
        if (!graph.containsKey(endName)) {
            System.err.printf("Граф не має кінцевої вершини \"%s\"\n", endName);
            return;
        }

        graph.get(endName).printPath();
        System.out.println();
    }
    /*
    Вивід шляху для всіх вершин
     */
    public void printAllPaths() {
        for (Vertex v : graph.values()) {
            v.printPath();
            System.out.println();
        }
    }
}