import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        HashMap<String, Integer> map = new HashMap<>();

        double dijkstraTime, bellmanTime;
        String time, date;
        long startTime, endTime;

        System.out.print("Total number of points: ");
        int n = in.nextInt();
        Graph graph = new Graph(n);
        System.out.println("Write the name of the points: ");
        in.nextLine();
        for (int i = 0; i < n; i++) {
            String name = in.nextLine();
            map.put(name, i);
            graph.addPoint(name);
        }
        System.out.println("Write relations(A/B/distance): ");
        while(in.hasNextLine()){
            String[] arr;
            String text = in.nextLine();
            if(text.equals("")) break;
            arr = text.split("/");
            int a = map.get(arr[0]);
            int b = map.get(arr[1]);
            double c = Double.parseDouble(arr[2]);
            graph.addEdge(a, b, c);
        }

        System.out.print("Enter the date and time (01.01 00:00): ");
        date = in.next();
        time = in.next();

        Trie trie = new Trie();
        for (String str : graph.vertices) {
            trie.insert(str);
        }
        in.nextLine();
        System.out.print("Write start point: ");
        String str = in.nextLine();
        ArrayList<String> array = trie.autoComplete(str);
        for (int i = 0; i < array.size(); i++) {
            System.out.println((i + 1) + ". " + array.get(i));
        }
        int start = map.get(array.get(in.nextInt()-1));

        in.nextLine();
        System.out.print("Write end point: ");
        str = in.nextLine();
        array = trie.autoComplete(str);
        for (int i = 0; i < array.size(); i++) {
            System.out.println((i + 1) + ". " + array.get(i));
        }
        int finish = map.get(array.get(in.nextInt()-1));
//        graph.printVertices();
//        System.out.print("Choose start point: ");
//        System.out.print("Choose finish point: ");
//        int finish = in.nextInt()-1;

        startTime = System.currentTimeMillis();
        graph.Dijkstra(start, finish, date, time);
        endTime = System.currentTimeMillis();
        dijkstraTime = (endTime - startTime) / 1000.0;
        System.out.println("Time complexity: " + dijkstraTime);

        startTime = System.currentTimeMillis();
        graph.Bellman(start, finish, date, time);
        endTime = System.currentTimeMillis();
        bellmanTime = (endTime - startTime) / 1000.0;
        System.out.println("Time complexity: " + bellmanTime);

    }
}