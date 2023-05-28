import java.util.*;

public class Graph {
    private int num;
    private ArrayList<Edge> edges;
    ArrayList<String> vertices;

    public Graph(int num) {
        this.num = num;
        this.edges = new ArrayList<>();
        this.vertices = new ArrayList<>();
    }

    public void addEdge(int A, int B, double distance) {
        edges.add(new Edge(A, B, distance));
    }

    public void addPoint(String name){
        vertices.add(name);
    }

    public void printVertices(){
        for (int i = 0; i < vertices.size(); i++) {
            System.out.println((i + 1) + ". " + vertices.get(i));
        }
    }

    public static String convertToTime(double hours) {
        int wholeHours = (int) hours;
        double fractionalPart = hours - wholeHours;

        int minutes = (int) (fractionalPart * 60);
        double fractionalPartMinutes = (fractionalPart * 60) - minutes;
        int seconds = (int) (fractionalPartMinutes * 60);
        double fractionalPartSeconds = (fractionalPartMinutes * 60) - seconds;
        int milliseconds = (int) (fractionalPartSeconds * 1000);

        return wholeHours+":"+minutes+":"+seconds+"."+milliseconds;
    }


    private int minDistance(double[] distances, boolean[] check) {
        double min = Double.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < distances.length; i++) {
            if (!check[i] && distances[i] <= min) {
                min = distances[i];
                index = i;
            }
        }

        return index;
    }

    private void printPath(int[] parent, int vertex) {
        if (parent[vertex] == -1) {
            System.out.print(vertices.get(vertex));
            return;
        }

        printPath(parent, parent[vertex]);
        System.out.print(" -> " + vertices.get(vertex));
    }

    private void relax(int A, int B, double dist, double[] distances, int[] parent) {
        if (distances[A] != Double.MAX_VALUE && distances[A] + dist < distances[B]) {
            distances[B] = distances[A] + dist;
            parent[B] = A;
        }
    }


    public void Dijkstra(int start, int finish, String date, String time) {
        double[] distances = new double[num];
        boolean[] check = new boolean[num];
        int[] parent = new int[num];

        Arrays.fill(distances, Double.MAX_VALUE);
        Arrays.fill(check, false);
        Arrays.fill(parent, -1);

        distances[start] = 0;

        for (int i = 0; i < num - 1; i++) {
            int A = minDistance(distances, check);
            check[A] = true;

            for (Edge edge : edges) {
                if (edge.getA() == A) {
                    int B = edge.getB();
                    double distance = edge.getDistance();

                    if (!check[B]) {
                        relax(A, B, distance, distances, parent);
                    }
                }
            }
        }

        System.out.println("\nDijkstra's algorithm: ");
        System.out.print("Shortest path from " + vertices.get(start) + " to " + vertices.get(finish) + ": ");
        printPath(parent, finish);
        System.out.println("\nDistance: " + distances[finish]);
        System.out.print("\nTime for each type of transport. ");
        Speed speed = new Speed();
        System.out.print("\nWalk: " +  convertToTime((distances[finish]/speed.walk) * speed.checkDate(date) * speed.checkTime(time)));
        System.out.print("\nCar: " + convertToTime((distances[finish]/speed.car) * speed.checkDate(date) * speed.checkTime(time)));
        System.out.print("\nBus: " + convertToTime((distances[finish]/speed.bus) * speed.checkDate(date) * speed.checkTime(time)));
        System.out.print("\nMetro: " + convertToTime((distances[finish]/speed.metro) * speed.checkDate(date) * speed.checkTime(time)));
        System.out.println("\n_______________________________________________________");
    }

    public void Bellman(int start, int finish, String date, String time) {
        double[] distances = new double[num];
        int[] parent = new int[num];

        Arrays.fill(distances, Double.MAX_VALUE);
        Arrays.fill(parent, -1);

        distances[start] = 0;

        for (int i = 1; i < num; i++) {
            for (Edge edge : edges) {
                int A = edge.getA();
                int B = edge.getB();
                double dist = edge.getDistance();

                relax(A, B, dist, distances, parent);
            }
        }

        for (Edge edge : edges) {
            int A = edge.getA();
            int B = edge.getB();
            double dist = edge.getDistance();

            if (distances[A] != Double.MAX_VALUE && distances[A] + dist < distances[B]) {
                System.out.println("Graph contains a negative cycle!");
                return;
            }
        }

        System.out.println("\nBellman-Ford's algorithm: ");
        System.out.print("Shortest path from " + vertices.get(start) + " to " + vertices.get(finish) + ": ");
        printPath(parent, finish);
        System.out.println("\nDistance: " + distances[finish]);
        System.out.print("\nTime for each type of transport. ");
        Speed speed = new Speed();
        System.out.print("\nWalk: " +  convertToTime((distances[finish]/speed.walk) * speed.checkDate(date) * speed.checkTime(time)));
        System.out.print("\nCar: " + convertToTime((distances[finish]/speed.car) * speed.checkDate(date) * speed.checkTime(time)));
        System.out.print("\nBus: " + convertToTime((distances[finish]/speed.bus) * speed.checkDate(date) * speed.checkTime(time)));
        System.out.print("\nMetro: " + convertToTime((distances[finish]/speed.metro) * speed.checkDate(date) * speed.checkTime(time)));
        System.out.println("\n_______________________________________________________");
    }
}
