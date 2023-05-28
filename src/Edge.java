public class Edge {
    private int A;
    private int B;
    private double distance;

    public Edge(int A, int B, double distance) {
        this.A = A;
        this.B = B;
        this.distance = distance;
    }

    public int getA() {
        return A;
    }

    public int getB() {
        return B;
    }

    public double getDistance() {
        return distance;
    }
}
