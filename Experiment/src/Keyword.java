public class Keyword {
    private String name;
    private double weight;

    public Keyword(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String toUpperCase() {
        return name.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "[" + name + "," + weight + "]";
    }
}