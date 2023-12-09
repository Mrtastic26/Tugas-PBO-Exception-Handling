import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SupermarketCashier {
    private Map<String, Purchasable> products;
    private Customer customer;

    public SupermarketCashier() {
        products = new HashMap<>();
        products.put("001", new Product("Buku", 4000.0));
        products.put("002", new Product("Pensil", 2000.0));
    }

    public void startTransaction() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Masukkan nama pelanggan: ");
            String customerName = scanner.nextLine();
            System.out.println("Masukkan nomor telepon pelanggan: ");
            String phoneNumber = scanner.nextLine();

            customer = new Customer(customerName, phoneNumber);

            double total = 0;
            String productCode;
            do {
                System.out.println("Masukkan No Faktur (selesai untuk selesai belanja): ");
                productCode = scanner.nextLine();

                if (products.containsKey(productCode)) {
                    Purchasable product = products.get(productCode);

                    System.out.println("Masukkan jumlah beli: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();

                    product.setQuantity(quantity);
                    total += product.getTotalPrice();

                    System.out.println("=== Detail Barang ===");
                    System.out.println(product);

                    ZonedDateTime currentDateTime = ZonedDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss z");
                    String formattedDateTime = currentDateTime.format(formatter);

                    System.out.println("Tanggal Pembelian: " + formattedDateTime);
                    System.out.println("Total Harga: " + product.getTotalPrice());
                } else if (!productCode.equalsIgnoreCase("selesai")) {
                    throw new ItemNotFoundException("Kode barang tidak valid.");
                }
            } while (!productCode.equalsIgnoreCase("selesai"));

           
            ZonedDateTime currentDateTime = ZonedDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss z");
            String formattedDateTime = currentDateTime.format(formatter);

            System.out.println("=== Struk Belanja ===");
            System.out.println("Tanggal Transaksi: " + formattedDateTime);
            System.out.println(customer);
            System.out.println("Total Pembayaran: " + total);
        } catch (ItemNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public static void main(String[] args) {
        SupermarketCashier cashier = new SupermarketCashier();
        cashier.startTransaction();
    }
}
