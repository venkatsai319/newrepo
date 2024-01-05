import java.util.*;

class Movie {
    String name;
    int totalSeats;
    int bookedSeats;

    public Movie(String name, int totalSeats) {
        this.name = name;
        this.totalSeats = totalSeats;
        this.bookedSeats = 0;
    }

    public String getName() {
        return name;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return totalSeats - bookedSeats;
    }

    public boolean bookSeats(int numSeats) {
        if (numSeats <= getAvailableSeats()) {
            bookedSeats += numSeats;
            return true;
        }
        return false;
    }
}

class Booking {
    Movie movie;
    int numSeats;
    double amount;

    public Booking(Movie movie, int numSeats) {
        this.movie = movie;
        this.numSeats = numSeats;
        this.amount = calculateAmount();
    }

    private double calculateAmount() {
        // Assuming a fixed price per seat for simplicity
        double seatPrice = 295.00;
        return numSeats * seatPrice;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public double getAmount() {
        return amount;
    }
}

class FrontDesk {
    private String username;
    private String password;
    private List<Booking> bookings;

    public FrontDesk(String username, String password) {
        this.username = username;
        this.password = password;
        this.bookings = new ArrayList<>();
    }

    public boolean login(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password updated successfully.");
    }

    public void viewSeatingArrangement(String date, String showTime) {
        // Implement logic to display seating arrangement based on date and show time
        System.out.println("Seating arrangement for " + date + " at " + showTime + ":");
        // ... (Display seating arrangement logic)
    }

    public void bookTicket(Movie movie, int numSeats) {
        if (movie.bookSeats(numSeats)) {
            Booking booking = new Booking(movie, numSeats);
            bookings.add(booking);
            System.out.println("Booking successful!");
            System.out.println("Movie: " + booking.getMovie().getName());
            System.out.println("Number of Seats: " + booking.getNumSeats());
            System.out.println("----------------------");
            System.out.println("Amount: Rs." + booking.getAmount());
            System.out.println("----------------------");
        } else {
            System.out.println("Booking failed. Not enough seats available.");
        }
    }

    public void checkBookingStatus() {
        System.out.println("Booking Status:");
        for (Booking booking : bookings) {
            System.out.println("Movie: " + booking.getMovie().getName());
            System.out.println("Number of Seats: " + booking.getNumSeats());
            System.out.println("----------------------");
            System.out.println("Amount: Rs." + booking.getAmount());
            System.out.println("----------------------");
        }
    }
}

public class MovieTicket {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create movies
        Movie movie1 = new Movie("Avengers: Endgame", 100);
        Movie movie2 = new Movie("RRR", 80);

        List<Movie> movies = new ArrayList<>(Arrays.asList(movie1, movie2));

        // Create front desk
        FrontDesk frontDesk = new FrontDesk("admin", "password");

        // Front desk login
        System.out.print("Enter username: ");
        String enteredUsername = scanner.next();
        System.out.print("Enter password: ");
        String enteredPassword = scanner.next();

        if (frontDesk.login(enteredUsername, enteredPassword)) {
            System.out.println("----------------------");
            System.out.println("Login successful!");
            System.out.println("----------------------");

            // Front desk menu
            int choice;
            do {
                System.out.println("\nFront Desk Menu:");
                System.out.println("1. Update Password");
                System.out.println("2. View Seating Arrangement");
                System.out.println("3. Book Ticket");
                System.out.println("4. Check Booking Status");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter new password: ");
                        String newPassword = scanner.next();
                        frontDesk.updatePassword(newPassword);
                        break;

                    case 2:
                        System.out.print("Enter date (e.g., DD-MM-YYYY): ");
                        String date = scanner.next();
                        System.out.print("Enter show time: ");
                        String showTime = scanner.next();
                        frontDesk.viewSeatingArrangement(date, showTime);
                        break;

                    case 3:
                        // Display available movies for booking
                        System.out.println("Available Movies for Booking:");
                        for (int i = 0; i < movies.size(); i++) {
                            Movie movie = movies.get(i);
                            System.out.println((i + 1) + ". " + movie.getName() + " - Available Seats: "
                                    + movie.getAvailableSeats());
                        }

                        // Select a movie for booking
                        System.out.print("Enter the number of the movie you want to book: ");
                        int selectedMovieIndex = scanner.nextInt() - 1;

                        if (selectedMovieIndex < 0 || selectedMovieIndex >= movies.size()) {
                            System.out.println("Invalid selection. Try again.");
                            break;
                        }

                        Movie selectedMovie = movies.get(selectedMovieIndex);

                        // Enter the number of seats to book
                        System.out.print("Enter the number of seats you want to book: ");
                        int numSeats = scanner.nextInt();

                        // Book seats
                        frontDesk.bookTicket(selectedMovie, numSeats);
                        break;

                    case 4:
                        frontDesk.checkBookingStatus();
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }

            } while (choice != 5);

        } else {
            System.out.println("Login failed. Exiting...");
        }
        scanner.close();
    }
}
