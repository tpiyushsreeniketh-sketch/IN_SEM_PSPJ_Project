
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

// Module 4: Class, object, constructor and encapsulation
class MenuItem {
    private int id;
    private String name;
    private int price;

    MenuItem(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    int getPrice() {
        return price;
    }

    public String toString() {
        return id + ". " + name + " - Rs." + price;
    }
}

public class RestaurantBilling15_2_2 {

    // Module 1: Constants and arrays
    static final String RESTAURANT = "BALAJI TIFFINS";

    static MenuItem[] menu = {
        new MenuItem(1, "Plain Dosa", 55),
        new MenuItem(2, "Masala Dosa", 70),
        new MenuItem(3, "Ghee Karam Dosa", 70),
        new MenuItem(4, "Upma Dosa", 65),
        new MenuItem(5, "Rava Dosa", 65),
        new MenuItem(6, "Set Dosa", 70),
        new MenuItem(7, "70mm Dosa", 80),
        new MenuItem(8, "Idli", 40),
        new MenuItem(9, "Rava Idli", 50),
        new MenuItem(10, "Sambar Idli", 50),
        new MenuItem(11, "Wada", 60),
        new MenuItem(12, "Poori", 70),
        new MenuItem(13, "Uthappam", 80),
        new MenuItem(14, "Pongal", 70),
        new MenuItem(15, "Tea", 30),
        new MenuItem(16, "Coffee", 30),
        new MenuItem(17, "Horlicks / Boost", 25),
        new MenuItem(18, "Cold Drinks", 25)
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("===== " + RESTAURANT + " =====");
        showMenu();

        // Module 2: Switch statement
        System.out.println("\n1. Dine In");
        System.out.println("2. Takeaway");
        System.out.print("Choose service: ");
        int service = sc.nextInt();

        switch (service) {
            case 1:
                System.out.println("Dine-In selected.");
                break;
            case 2:
                System.out.println("Takeaway selected.");
                break;
            default:
                System.out.println("Invalid service choice.");
                sc.close();
                return;
        }

        int[] orderedIds = new int[50];
        int[] quantities = new int[50];
        int count = 0;

        // Module 2: do-while, if, break and continue
        do {
            System.out.print(
                "\nEnter item number (1-18), or 0 to finish: "
            );
            int choice = sc.nextInt();

            if (choice == 0) {
                break;
            }

            MenuItem item = findItem(choice);

            if (item == null) {
                System.out.println("Invalid item number.");
                continue;
            }

            if (count >= orderedIds.length) {
                System.out.println("Order list is full.");
                break;
            }

            System.out.print("Enter quantity: ");
            int quantity = sc.nextInt();

            if (quantity <= 0) {
                System.out.println("Quantity must be positive.");
                continue;
            }

            orderedIds[count] = choice;
            quantities[count] = quantity;
            count++;

            System.out.println("Item added successfully.");

        } while (true);

        if (count > 0) {
            printBill(orderedIds, quantities, count);
        } else {
            System.out.println("No items ordered.");
        }

        sc.close();
    }

    // Module 3: Methods and enhanced for loop
    static void showMenu() {
        System.out.println("\n----------- MENU -----------");

        for (MenuItem item : menu) {
            System.out.println(item);
        }
    }

    // Search for a menu item
    static MenuItem findItem(int id) {
        for (MenuItem item : menu) {
            if (item.getId() == id) {
                return item;
            }
        }

        return null; // Module 4: null reference
    }

    // Module 3: Method to calculate one item's cost
    static int calculateTotal(int price, int quantity) {
        return price * quantity;
    }

    // Module 3: Method overloading
    static int calculateTotal(int[] totals, int count) {
        return calculateTotal(totals, count, 0);
    }

    // Module 3: Recursion with a base case
    static int calculateTotal(int[] totals, int count, int index) {
        if (index == count) {
            return 0;
        }

        return totals[index]
             + calculateTotal(totals, count, index + 1);
    }

    static void printBill(int[] ids, int[] quantities, int count) {
        int[] lineTotals = new int[count];
        String[] names = new String[count];

        System.out.println("\n========== FINAL BILL ==========");

        for (int i = 0; i < count; i++) {
            MenuItem item = findItem(ids[i]);

            names[i] = item.getName();
            lineTotals[i] = calculateTotal(
                item.getPrice(), quantities[i]
            );

            System.out.printf(
                "%-20s x %d = Rs.%d%n",
                names[i], quantities[i], lineTotals[i]
            );
        }

        int subtotal = calculateTotal(lineTotals, count);

        // Module 1: Relational operator and arithmetic
        double discount = subtotal >= 500 ? subtotal * 0.05 : 0;
        double finalAmount = subtotal - discount;

        System.out.println("--------------------------------");
        System.out.println("Subtotal: Rs." + subtotal);
        System.out.printf("Discount: Rs.%.2f%n", discount);
        System.out.printf("Amount to pay: Rs.%.2f%n", finalAmount);
        System.out.println("================================");

        saveBill(names, lineTotals, count, subtotal);

       /*  // Module 5: Sorting using a Java library method
        Arrays.sort(names);

        System.out.println("\nItems in alphabetical order:");
        for (String name : names) {
            System.out.println(name);
        }
        */

      /*   // Module 5: String methods
        String details = String.join(", ", names);
        System.out.println("\nOrdered items: " + details);

        String example = "  Restaurant Billing  ";
        System.out.println("Trim example: " + example.trim());

        String text = "Tea,Coffee,Idli";
        String[] parts = text.split(",");
        System.out.println("Split example:");
        for (String part : parts) {
            System.out.println(part);
        }
        */
    }

    // Module 5: Basic file handling and exception handling
    static void saveBill(
        String[] names, int[] totals, int count, int subtotal
    ) {
        try (FileWriter file =
                 new FileWriter("restaurant_orders.txt", true)) {

            file.write("\n" + RESTAURANT + "\n");

            for (int i = 0; i < count; i++) {
                file.write(
                    names[i] + " - Rs." + totals[i] + "\n"
                );
            }

            file.write("Subtotal: Rs." + subtotal + "\n");
            file.write("------------------------\n");

            System.out.println("Bill saved successfully.");

        } catch (IOException e) {
            System.out.println("Unable to save the bill.");
        }
    }
}
