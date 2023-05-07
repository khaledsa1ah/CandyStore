/*import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Catalog {
    private List<Candy> candies;

    public Catalog() {
        candies = new ArrayList<>();
        readCatalogFromFile();
    }

    public List<Candy> getCandies() {
        return candies;
    }

    public void readCatalogFromFile() {
        File file = new File("catalog.txt");

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(":");

                String name = parts[0];
                float price = Float.parseFloat(parts[1]);

                Candy candy = new Candy(name, price);
                candies.add(candy);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/
/*
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Catalog {
    private List<Candy> candies = new ArrayList<>();

    public void readCatalogFromFile() {
        File file = new File("catalog.txt");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(":");

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                float price = Float.parseFloat(parts[2]);

                Candy candy = new Candy(id, name, price);
                candies.add(candy);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Candy getCandyById(int id) {
        for (Candy candy : candies) {
            if (candy.getId() == id) {
                return candy;
            }
        }
        return null;
    }

    public void printCatalog() {
        readCatalogFromFile();
        System.out.println("Catalog:");
        for (Candy candy : candies) {
            System.out.println(candy.getId() + ": " + candy.getName() + " - LE." + candy.getPrice());
        }
    }
}*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Catalog {
    private List<Candy> candies;

    public Catalog() {
        candies = new ArrayList<>();
        readCatalogFromFile();
    }

    public List<Candy> getCandies() {
        return candies;
    }

    public Candy getCandyById(int id) {
        for (Candy candy : candies) {
            if (candy.getId() == id) {
                return candy;
            }
        }
        return null;
    }

    public void printCatalog() {
        System.out.println("Catalog:");
        for (Candy candy : candies) {
            System.out.println(candy.getId() + ": " + candy.getName() + " - LE." + candy.getPrice());
        }
    }

    private void readCatalogFromFile() {
        File file = new File("catalog.txt");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(":");

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                float price = Float.parseFloat(parts[2]);

                Candy candy = new Candy(id, name, price);
                candies.add(candy);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}