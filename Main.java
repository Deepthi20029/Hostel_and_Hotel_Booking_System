
import java.util.*;
import java.util.regex.*;

class Colors 
{
    static final String reset = "\u001B[0m";
    static final String black = "\u001B[30m";
    static final String red = "\u001B[31m";
    static final String green = "\u001B[32m";
    static final String yellow = "\u001B[33m";
    static final String blue = "\u001B[34m";
    static final String purple = "\u001B[35m";
    static final String cyan = "\u001B[36m";
    static final String white = "\u001B[37m";
    static final String whiteBold = "\u001B[1;37m";
    static String bold = "\u001B[1m";
    static final String bgBrightBlack = "\u001B[100m";
    static final String bgBrightRed = "\u001B[101m";
    static final String bgBrightGreen = "\u001B[102m";
    static final String bgBrightYellow = "\u001B[103m";
    static final String bgBrightBlue = "\u001B[104m";
    static final String bgBrightPurple = "\u001B[105m";
    static final String bgBrightCyan = "\u001B[106m";
    static final String bgBrightWhite = "\u001B[107m";
    static String menuPadding = "";
    static String menuPadding1 = "";
    static String menuPadding2 = "";

    public static void typeWriter(String text, int delay) 
    {
        for (char c : text.toCharArray()) 
        {
            System.out.print(c);
            try 
            {
                Thread.sleep(delay);
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }
    }

    static void printSlow(String text, long delay)
    {
        for (char c : text.toCharArray()) 
        {
            System.out.print(c);
            try 
            {
                Thread.sleep(delay);
            } 
            catch (InterruptedException e) 
            {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }
}

class Mobile extends Colors 
{
    void message(int otp)
    {
        System.out.println(bold + "Your OTP: " + otp + reset);
    }
}

class Details extends Colors 
{
    static Scanner sc = new Scanner(System.in);
    private static String UserName;
    private static String Password;
    private static String Phno;
    private static String Email;
    private static boolean accountCreated = false;

    void setName(String UserName) 
    {
        this.UserName = UserName;
    }

    void setPass(String Password) 
    {
        this.Password = Password;
    }

    void setEmail(String Email)
    {
        this.Email = Email;
    }

    void setPhno(String phno) 
    {
        this.Phno = phno;
    }

    String getName() 
    {
        return UserName;
    }

    String getPass() 
    {
        return Password;
    }

    String getPhno()
    {
        return Phno;
    }

    String getEmail()
    {
        return Email;
    }

    static boolean isAccountCreated()
    {
        return accountCreated;
    }

    static void setAccountCreated(boolean status) 
    {
        accountCreated = status;
    }

    int otpGenerator() 
    {
        int otp = 1000 + (int) (Math.random() * 8999);
        loadingAnimation("Sending OTP");
        new Mobile().message(otp);
        return otp;
    }

    static void loadingAnimation(String message)
    {
        System.out.print("\u001B[36m" + bold + message);
        for (int i = 0; i < 3; i++)
        {
            try 
            {
                Thread.sleep(500);
                System.out.print(".");
            } 
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("\u001B[0m");
    }
}

class User extends Colors 
{
    static Scanner sc = new Scanner(System.in);
    static Details x = new Details();

    boolean isValidEmail(String email)
    {
        return email.contains("@gmail.com");
    }

    boolean isValidPassword(String password)
    {
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return Pattern.matches(regex, password);
    }

    void SignUp() 
    {
        System.out.println(green + bold + "--- SIGN UP ---" + reset);
        System.out.print(bold+"Enter your Name: ");
        x.setName(sc.next());
        while (true) 
        {
            System.out.print(bold+"Enter your Email: ");
            String email = sc.next();
            if (isValidEmail(email)) 
            {
                x.setEmail(email);
                break;
            } 
            else
            {
                System.out.println(red + bold + "Invalid email. Must contain @gmail.com" + reset);
            }
        }
        while (true)
        {
            System.out.print(bold+"Enter your Password: ");
            String password = sc.next();
            if (isValidPassword(password))
            {
                x.setPass(password);
                break;
            } else 
            {
                System.out.println(red + bold
                        + "Weak password! Must contain 8+ chars, uppercase, lowercase, digit, and special char."
                        + reset);
            }
        }
        while (true) 
        {
            System.out.print(bold+"Enter your Phone Number: ");
            String phno = sc.next();
            if (phno.length() == 10 && phno.charAt(0) >= '6' && phno.charAt(0) <= '9') 
            {
                x.setPhno(phno);
                int otp = x.otpGenerator();
                System.out.print(bold + "Enter OTP: " + reset);
                int ot = sc.nextInt();
                if (ot == otp) 
                {
                    System.out.println(green + bold + "Account created successfully!" + reset);
                    Details.setAccountCreated(true);
                    break;
                } 
                else 
                {
                    System.out.println(red + bold + "Invalid OTP. Signup failed." + reset);
                    return;
                }
            } 
            else
            {
                System.out.println(red + bold + "Invalid Phone Number. Must start with 6-9 and have 10 digits." + reset);
            }
        }
    }

    void login()
    {
        if (!Details.isAccountCreated()) 
        {
            System.out.println(red + bold + "No account found. Please sign up first." + reset);
            return;
        }
        System.out.println(green + bold + "--- LOGIN ---" + reset);
        System.out.print("Username: ");
        String username = sc.next();
        System.out.print("Password: ");
        String password = sc.next();
        if (username.equals(x.getName()) && password.equals(x.getPass())) 
        {
            System.out.println(green + bold + "Login successful!" + reset);
        }
        else
        {
            System.out.println(red + bold + "Invalid credentials." + reset);
            System.out.print(bold + "Press 1 to reset username, 2 to reset password" + reset);
            String choice = sc.next();
            if (choice.equals("1")) 
            {
                System.out.print(bold+"Enter new username: ");
                x.setName(sc.next());
                System.out.println(bold+"Your new username: " + x.getName());
                System.out.print(bold + "Press 1 to go for login" + reset);
                if (sc.next().equals("1"))
                    login();
                else
                    System.out.println(green + bold + "Thank you :) " + reset);
            } 
            else if (choice.equals("2"))
            {
                System.out.print(bold+"Enter new password: ");
                x.setPass(sc.next());
                System.out.println(bold+"Your new password: " + x.getPass());
                System.out.print(bold + "Press 1 to go for login " + reset);
                if (sc.next().equals("1"))
                    login();
                else
                    System.out.println(green + bold + "Thank you :) " + reset);
            }
            else
            {
                System.out.println(green + bold + "Thank you :) " + reset);
            }
        }
    }
}

abstract class LocationDetails
{
    abstract String getMetro();

    abstract String getBusStop();

    abstract String getInstitution1();

    abstract String getInstitution2();

    abstract String getInstitution3();

    abstract String getInstitution4();
}

class KphbLocation extends LocationDetails
{
    private String metro = " Colony Metro Station (1 km)";
    private String busStop = "KPHB Bus Stop (0.5 km)";
    private String institution1 = "JNTU Hyderabad (1 km)";
    private String institution2 = "Gokaraju Rangaraju Institute of Engineering and Technology (3 km)";
    private String institution3 = "Vasavi College of Engineering (2 km)";
    private String institution4 = "Sri Chaitanya Junior College (1.5 km)";

    String getMetro() 
    {
        return metro;
    }

    String getBusStop() 
    {
        return busStop;
    }

    String getInstitution1() 
    {
        return institution1;
    }

    String getInstitution2()
    {
        return institution2;
    }

    String getInstitution3() 
    {
        return institution3;
    }

    String getInstitution4() 
    {
        return institution4;
    }
}

class AmeerpetLocation extends LocationDetails
{
    private String metro = "Ameerpet Metro Station (0.5 km)";
    private String busStop = "Ameerpet Bus Stop (0.3 km)";
    private String institution1 = "Sri Chaitanya Educational Institutions (1 km)";
    private String institution2 = "Narayana Junior College (0.8 km)";
    private String institution3 = "FIITJEE (1.2 km)";
    private String institution4 = "Maitreyi Degree College (0.7 km)";

    String getMetro()
    {
        return metro;
    }

    String getBusStop()
    {
        return busStop;
    }

    String getInstitution1()
    {
        return institution1;
    }

    String getInstitution2()
    {
        return institution2;
    }

    String getInstitution3()
    {
        return institution3;
    }

    String getInstitution4()
    {
        return institution4;
    }
}

class SRNagarLocation extends LocationDetails 
{
    private String metro = "SR Nagar Metro Station (0.4 km)";
    private String busStop = "SR Nagar Bus Stop (0.2 km)";
    private String institution1 = "Gokaraju Rangaraju Institute of Engineering and Technology (2.5 km)";
    private String institution2 = "Kasturba Gandhi Degree & PG College for Women (1.5 km)";
    private String institution3 = "Little Flower Junior College (1 km)";
    private String institution4 = "Narayana Junior College (0.9 km)";

    String getMetro()
    {
        return metro;
    }

    String getBusStop() 
    {
        return busStop;
    }

    String getInstitution1()
    {
        return institution1;
    }

    String getInstitution2() 
    {
        return institution2;
    }

    String getInstitution3()
    {
        return institution3;
    }

    String getInstitution4() 
    {
        return institution4;
    }
}

class KukatpallyLocation extends LocationDetails 
{
    private String metro = "Kukatpally Metro Station (0.6 km)";
    private String busStop = "Kukatpally Bus Stop (0.4 km)";
    private String institution1 = "Jawaharlal Nehru Technological University (JNTU) (1.5 km)";
    private String institution2 = "Gokaraju Rangaraju Institute of Engineering and Technology (3 km)";
    private String institution3 = "Malla Reddy College of Engineering & Technology (4 km)";
    private String institution4 = "CMR College of Engineering & Technology (2.5 km)";

    String getMetro() 
    {
        return metro;
    }

    String getBusStop() 
    {
        return busStop;
    }

    String getInstitution1()
    {
        return institution1;
    }

    String getInstitution2()
    {
        return institution2;
    }

    String getInstitution3()
    {
        return institution3;
    }

    String getInstitution4()
    {
        return institution4;
    }
}

class MadhapurLocation extends LocationDetails 
{
    private String metro = "Madhapur Metro Station (0.8 km)";
    private String busStop = "Madhapur Bus Stop (0.5 km)";
    private String institution1 = "Indian School of Business (ISB) (3 km)";
    private String institution2 = "University of Hyderabad (5 km)";
    private String institution3 = "International Institute of Information Technology (IIIT) (2 km)";
    private String institution4 = "Birla Institute of Technology and Science (BITS) Pilani Hyderabad Campus (4 km)";

    String getMetro() 
    {
        return metro;
    }

    String getBusStop()
    {
        return busStop;
    }

    String getInstitution1()
    {
        return institution1;
    }

    String getInstitution2() 
    {
        return institution2;
    }

    String getInstitution3() 
    {
        return institution3;
    }

    String getInstitution4() 
    {
        return institution4;
    }
}

class GachibowliLocation extends LocationDetails
{
    private String metro = "Raidurg Metro Station (2.77 km)";
    private String busStop = "Gachibowli Bus Stop (0.5 km)";
    private String institution1 = "University of Hyderabad (2 km)";
    private String institution2 = "International Institute of Information Technology Hyderabad (IIIT-H) (1.5 km)";
    private String institution3 = "Indian School of Business (ISB) (3 km)";
    private String institution4 = "Woxsen University (4 km)";

    String getMetro()
    {
        return metro;
    }

    String getBusStop()
    {
        return busStop;
    }

    String getInstitution1()
    {
        return institution1;
    }

    String getInstitution2()
    {
        return institution2;
    }

    String getInstitution3()
    {
        return institution3;
    }

    String getInstitution4()
    {
        return institution4;
    }
}

class KondapurLocation extends LocationDetails
{
    private String metro = "Raidurg Metro Station (2.95 km)";
    private String busStop = "Kondapur Bus Stop (0.6 km)";
    private String institution1 = "Chirec International School (1 km)";
    private String institution2 = "Arbor International School (1.5 km)";
    private String institution3 = "Jain Heritage a Cambridge School (2 km)";
    private String institution4 = "Sanskriti School (1.2 km)";

    String getMetro()
    {
        return metro;
    }

    String getBusStop()
    {
        return busStop;
    }

    String getInstitution1()
    {
        return institution1;
    }

    String getInstitution2()
    {
        return institution2;
    }

    String getInstitution3()
    {
        return institution3;
    }

    String getInstitution4() 
    {
        return institution4;
    }
}

class BanjaraHillsLocation extends LocationDetails
{
    private String metro = "Errum Manzil Metro Station (1.68 km)";
    private String busStop = "Banjara Hills Bus Stop (0.4 km)";
    private String institution1 = "Meridian School (1 km)";
    private String institution2 = "Kalpa School (1.2 km)";
    private String institution3 = "Delhi School of Excellence (0.8 km)";
    private String institution4 = "Lovedale International School (1.5 km)";

    String getMetro()
    {
        return metro;
    }

    String getBusStop()
    {
        return busStop;
    }

    String getInstitution1()
    {
        return institution1;
    }

    String getInstitution2()
    {
        return institution2;
    }

    String getInstitution3()
    {
        return institution3;
    }

    String getInstitution4()
    {
        return institution4;
    }
}

class BegumpetLocation extends LocationDetails
{
    private String metro = "Begumpet Metro Station (0.5 km)";
    private String busStop = "Begumpet Bus Stop (0.3 km)";
    private String institution1 = "Gowtham Model School (1 km)";
    private String institution2 = "Kendriya Vidyalaya AFS Begumpet (1.5 km)";
    private String institution3 = "The Hyderabad Public School (2 km)";
    private String institution4 = "Chinmaya Vidyalaya (0.8 km)";

    String getMetro() 
    {
        return metro;
    }

    String getBusStop()
    {
        return busStop;
    }

    String getInstitution1()
    {
        return institution1;
    }

    String getInstitution2()
    {
        return institution2;
    }

    String getInstitution3() 
    {
        return institution3;
    }

    String getInstitution4()
    {
        return institution4;
    }
}

class DilsukhnagarLocation extends LocationDetails
{
    private String metro = "Dilsukhnagar Metro Station (0.2 km)";
    private String busStop = "Dilsukhnagar Bus Station (0.1 km)";
    private String institution1 = "Oakwood International School (1 km)";
    private String institution2 = "Dilsukhnagar Public School (0.5 km)";
    private String institution3 = "Ravindra Bharathi School (0.8 km)";
    private String institution4 = "Gowtham Model School (1.2 km)";

    String getMetro()
    {
        return metro;
    }

    String getBusStop()
    {
        return busStop;
    }

    String getInstitution1()
    {
        return institution1;
    }

    String getInstitution2()
    {
        return institution2;
    }

    String getInstitution3()
    {
        return institution3;
    }

    String getInstitution4()
    {
        return institution4;
    }
}

class Hostel extends Colors
{
    static Scanner sc = new Scanner(System.in);
    static FacilitySupportSystem obj = new FacilitySupportSystem();

    static boolean welcomeScreen()
    {
        while (true)
        {
            System.out.println( cyan + bold
                    + "=====================================================================" + reset);
            System.out.println(green + bold + " WELCOME TO HYDERABAD - YOUR TRUSTED HOSTEL BOOKING COMPANION"
                    + reset);
            System.out.println( cyan + bold
                    + "=====================================================================" + reset);
            System.out.println(yellow + bold + "1. Start Booking" + reset);
            System.out.println(yellow + bold + "2. Exit" + reset);
            System.out.print(bold+"Enter your choice: ");
            int choice = sc.nextInt();
            if (choice == 1)
                return true;
            else if (choice == 2) 
            {
                System.out.println(menuPadding + red + bold + "Thank you for visiting! Goodbye!" + reset);
                return false;
            } 
            else
            {
                System.out.println(menuPadding + red + bold + "Invalid choice. Please try again." + reset);
            }
        }
    }

    static String location()
    {
        while (true) 
        {
            System.out.println(
                    cyan + bold + " Please select the location where we have to proceed with booking:" + reset);
            System.out.println(menuPadding + yellow + bold + "+---------- LOCATION LIST ---------------+");
            for (int i = 1; i <= 10; i++)
            {
                String loc = "";
                if (i == 1)
                    loc = "Kphb";
                if (i == 2)
                    loc = "Ameerpet";
                if (i == 3)
                    loc = "SR Nagar";
                if (i == 4)
                    loc = "Kukatpally";
                if (i == 5)
                    loc = "Madhapur";
                if (i == 6)
                    loc = "Gachibowli";
                if (i == 7)
                    loc = "Kondapur";
                if (i == 8)
                    loc = "Banjara Hills";
                if (i == 9)
                    loc = "Begumpet";
                if (i == 10)
                    loc = "Dilsukhnagar";
                System.out.println(menuPadding + "  " + i + ". " + loc + "      ");
            }
            System.out.println(menuPadding + "+----------------------------------------+");
            System.out.print(reset +bold+ "Enter your location choice: "+reset);
            int locChoice = sc.nextInt();
            if (locChoice >= 1 && locChoice <= 10) 
            {
                String selectedLocation = "";
                if (locChoice == 1)
                    selectedLocation = "Kphb";
                if (locChoice == 2)
                    selectedLocation = "Ameerpet";
                if (locChoice == 3)
                    selectedLocation = "SR Nagar";
                if (locChoice == 4)
                    selectedLocation = "Kukatpally";
                if (locChoice == 5)
                    selectedLocation = "Madhapur";
                if (locChoice == 6)
                    selectedLocation = "Gachibowli";
                if (locChoice == 7)
                    selectedLocation = "Kondapur";
                if (locChoice == 8)
                    selectedLocation = "Banjara Hills";
                if (locChoice == 9)
                    selectedLocation = "Begumpet";
                if (locChoice == 10)
                    selectedLocation = "Dilsukhnagar";
                System.out.println(menuPadding + green + bold + "You selected: " + selectedLocation + reset);
                return selectedLocation;
            }
            else
            {
                System.out.println(red + bold + "Invalid choice. Please re-select the location" + reset);
            }
        }
    }

    static String hostelCategorySelection()
    {
        while (true) 
        {
            System.out.println();
            System.out.println(menuPadding + cyan + bold + "Choose Hostel Category:" + reset);
            System.out.println(menuPadding + yellow + bold + "====== CATEGORY LIST ========");
            System.out.println(menuPadding +bold+ " 1. Boys ");
            System.out.println(menuPadding +bold+ " 2. Girls ");
            System.out.println(menuPadding +bold+ " 3. Co-living ");
            System.out.println(menuPadding +bold+ "+=============================");
            System.out.print(reset +bold+ "Enter your category choice: "+reset);
            int catChoice = sc.nextInt();
            if (catChoice >= 1 && catChoice <= 3)
            {
                String selectedCategory = "";
                if (catChoice == 1)
                    selectedCategory = "Boys";
                if (catChoice == 2)
                    selectedCategory = "Girls";
                if (catChoice == 3)
                    selectedCategory = "Co-living";
                System.out.println(menuPadding + green + bold + "You selected: " + selectedCategory + reset);
                return selectedCategory;
            } 
            else 
            {
                System.out.println(menuPadding + red + bold + "Invalid choice. Please select again." + reset);
            }
        }
    }

    static void showHostels(String location, String category) 
    {
        System.out.println();
        System.out.println(
                menuPadding + cyan + bold + "Hostels in " + location + " (" + category + " Category):" + reset);
        String hostel1 = "", hostel2 = "", hostel3 = "", hostel4 = "", hostel5 = "";
        String hostel6 = "", hostel7 = "", hostel8 = "", hostel9 = "", hostel10 = "";
        int hostelCount = 0;

        if (location.equals("Kphb"))
        {
            if (category.equals("Boys")) 
            {
                hostel1 = "Sunrise Boys KPHB";
                hostel2 = "Green View Boys KPHB";
                hostel3 = "Blue Sky PG KPHB";
                hostel4 = "Metro Comfort Boys KPHB";
                hostel5 = "Prime Stay Boys KPHB";
                hostel6 = "Urban Nest Boys KPHB";
                hostel7 = "City Heart Boys KPHB";
                hostel8 = "Comfort Inn Boys KPHB";
                hostel9 = "Skyline Boys KPHB";
                hostel10 = "Elite Boys Hostel KPHB";
                hostelCount = 10;
            } 
            else if (category.equals("Girls")) 
            {
                hostel1 = "Pink Blossom Girls KPHB";
                hostel2 = "Queen’s Stay Girls KPHB";
                hostel3 = "Grace Villa Girls KPHB";
                hostel4 = "Golden Nest Girls KPHB";
                hostel5 = "Dreamland Girls KPHB";
                hostel6 = "Royal Comfort Girls KPHB";
                hostel7 = "Pearl Stay Girls KPHB";
                hostel8 = "City Pearl Girls KPHB";
                hostel9 = "Blossom Stay Girls KPHB";
                hostel10 = "Elite Girls Hostel KPHB";
                hostelCount = 10;
            } 
            else
            {
                hostel1 = "Metro Co-living KPHB";
                hostel2 = "Urban Hub Co-living KPHB";
                hostel3 = "Shared Comfort Co-living KPHB";
                hostel4 = "Elite Co-living KPHB";
                hostel5 = "The Hive Co-living KPHB";
                hostelCount = 5;
            }
        } 
        else if (location.equals("Ameerpet"))
        {
            if (category.equals("Boys")) 
            {
                hostel1 = "Bhavani Delux Pg For Mens";
                hostel2 = "Vijetha Mens Hostel";
                hostel3 = "Green View Boys Hostel";
                hostel4 = "Sri Sai Krishna Gents Hostel";
                hostel5 = "Royal Comforts Men’s PG";
                hostel6 = "Blue Heaven Boys Hostel";
                hostel7 = "Om Sai Deluxe Mens PG";
                hostel8 = "Sunshine Gents Hostel";
                hostel9 = "Galaxy Boys Hostel";
                hostel10 = "Elite Comforts Mens PG";
                hostelCount = 10;
            } 
            else if (category.equals("Girls"))
            {
                hostel1 = "Lakshmi Deluxe Women’s Hostel";
                hostel2 = "Rose Villa Girls Hostel";
                hostel3 = "Sri Durga Ladies PG";
                hostel4 = "Harmony Women’s Hostel";
                hostel5 = "Golden Nest Girls PG";
                hostel6 = "Pink Blossom Women’s Hostel";
                hostel7 = "Queen’s Stay Ladies PG";
                hostel8 = "Shree Comforts Girls Hostel";
                hostel9 = "Dreamland Women’s PG";
                hostel10 = "Elegant Stay Girls Hostel";
                hostelCount = 10;
            } 
            else
            {
                hostel1 = "Urban Stays Co-living";
                hostel2 = "The Hive Co-living";
                hostel3 = "StayAbode Shared Living";
                hostel4 = "Zolo Co-living";
                hostel5 = "CoHo Co-living";
                hostelCount = 5;
            }
        }
        else if (location.equals("SR Nagar")) 
        {
            if (category.equals("Boys")) 
            {
                hostel1 = "Sai Krishna Mens PG";
                hostel2 = "Royal Comforts Boys Hostel";
                hostel3 = "Elite Comforts Men’s Hostel";
                hostel4 = "Sunshine Mens PG";
                hostel5 = "Vijetha Gents Hostel";
                hostel6 = "Blue Heaven Men’s Hostel";
                hostel7 = "Green View Gents Hostel";
                hostel8 = "Galaxy Men’s PG";
                hostel9 = "Om Sai Deluxe Mens Hostel";
                hostel10 = "Bhavani Deluxe Boys PG";
                hostelCount = 10;
            } 
            else if (category.equals("Girls"))
            {
                hostel1 = "Dreamland Women’s PG";
                hostel2 = "Queen’s Stay Girls Hostel";
                hostel3 = "Harmony Women’s Hostel";
                hostel4 = "Rose Villa Ladies PG";
                hostel5 = "Golden Nest Girls Hostel";
                hostel6 = "Pink Blossom Ladies Hostel";
                hostel7 = "Elegant Stay Girls Hostel";
                hostel8 = "Sri Durga Ladies PG";
                hostel9 = "Shree Comforts Women’s Hostel";
                hostel10 = "Lakshmi Deluxe Women’s Hostel";
                hostelCount = 10;
            } 
            else
            { // Co-living
                hostel1 = "CoHo Co-living";
                hostel2 = "Zolo Co-living";
                hostel3 = "Urban Stays Co-living";
                hostel4 = "StayAbode Shared Living";
                hostel5 = "The Hive Co-living";
                hostelCount = 5;
            }
        } 
        else if (location.equals("Kukatpally"))
        {
            if (category.equals("Boys")) 
            {
                hostel1 = "Blue Haven Gents PG";
                hostel2 = "Sri Sai Men’s Hostel";
                hostel3 = "Elite Comforts Mens PG";
                hostel4 = "Om Shanti Boys Hostel";
                hostel5 = "Royal Comforts Men’s Hostel";
                hostel6 = "Sunrise Gents Hostel";
                hostel7 = "Green Valley Mens PG";
                hostel8 = "Galaxy Deluxe Boys Hostel";
                hostel9 = "Shree Comforts Mens PG";
                hostel10 = "Bhavani Deluxe Gents Hostel";
                hostelCount = 10;
            } 
            else if (category.equals("Girls"))
            {
                hostel1 = "Queen’s Stay Ladies Hostel";
                hostel2 = "Elegant Stay Women’s PG";
                hostel3 = "Golden Nest Ladies Hostel";
                hostel4 = "Dreamland Girls Hostel";
                hostel5 = "Harmony Women’s PG";
                hostel6 = "Rose Villa Ladies Hostel";
                hostel7 = "Pink Blossom Girls PG";
                hostel8 = "Sri Durga Women’s Hostel";
                hostel9 = "Shree Comforts Girls PG";
                hostel10 = "Lakshmi Deluxe Women’s Hostel";
                hostelCount = 10;
            } 
            else
            { // Co-living
                hostel1 = "Urban Stays Co-living";
                hostel2 = "CoHo Co-living";
                hostel3 = "The Hive Co-living";
                hostel4 = "StayAbode Shared Living";
                hostel5 = "Zolo Co-living";
                hostelCount = 5;
            }
        } 
        else if (location.equals("Madhapur"))
        {
            if (category.equals("Boys")) 
            {
                hostel1 = "Sunrise Gents Hostel";
                hostel2 = "Elite Comforts Mens PG";
                hostel3 = "Royal Comforts Men’s Hostel";
                hostel4 = "Sri Sai Men’s Hostel";
                hostel5 = "Om Shanti Boys Hostel";
                hostel6 = "Green Valley Mens PG";
                hostel7 = "Galaxy Deluxe Boys Hostel";
                hostel8 = "Bhavani Deluxe Gents Hostel";
                hostel9 = "Shree Comforts Mens PG";
                hostel10 = "Blue Haven Gents PG";
                hostelCount = 10;
            } 
            else if (category.equals("Girls"))
            {
                hostel1 = "Pink Blossom Girls PG";
                hostel2 = "Elegant Stay Women’s PG";
                hostel3 = "Rose Villa Ladies Hostel";
                hostel4 = "Sri Durga Women’s Hostel";
                hostel5 = "Golden Nest Ladies Hostel";
                hostel6 = "Queen’s Stay Ladies Hostel";
                hostel7 = "Harmony Women’s PG";
                hostel8 = "Lakshmi Deluxe Women’s Hostel";
                hostel9 = "Shree Comforts Girls PG";
                hostel10 = "Dreamland Girls Hostel";
                hostelCount = 10;
            }
            else
            { // Co-living
                hostel1 = "The Hive Co-living";
                hostel2 = "Zolo Co-living";
                hostel3 = "Urban Stays Co-living";
                hostel4 = "CoHo Co-living";
                hostel5 = "StayAbode Shared Living";
                hostelCount = 5;
            }
        } 
        else if (location.equals("Gachibowli"))
        {
            if (category.equals("Boys"))
            {
                hostel1 = "Green Valley Mens PG";
                hostel2 = "Om Shanti Boys Hostel";
                hostel3 = "Shree Comforts Mens PG";
                hostel4 = "Galaxy Deluxe Boys Hostel";
                hostel5 = "Royal Comforts Men’s Hostel";
                hostel6 = "Sri Sai Men’s Hostel";
                hostel7 = "Sunrise Gents Hostel";
                hostel8 = "Elite Comforts Mens PG";
                hostel9 = "Blue Haven Gents PG";
                hostel10 = "Bhavani Deluxe Gents Hostel";
                hostelCount = 10;
            } 
            else if (category.equals("Girls")) 
            {
                hostel1 = "Harmony Women’s PG";
                hostel2 = "Queen’s Stay Ladies Hostel";
                hostel3 = "Lakshmi Deluxe Women’s Hostel";
                hostel4 = "Golden Nest Ladies Hostel";
                hostel5 = "Pink Blossom Girls PG";
                hostel6 = "Rose Villa Ladies Hostel";
                hostel7 = "Elegant Stay Women’s PG";
                hostel8 = "Dreamland Girls Hostel";
                hostel9 = "Sri Durga Women’s Hostel";
                hostel10 = "Shree Comforts Girls PG";
                hostelCount = 10;
            } 
            else 
            { // Co-living
                hostel1 = "CoHo Co-living";
                hostel2 = "Zolo Co-living";
                hostel3 = "Urban Stays Co-living";
                hostel4 = "The Hive Co-living";
                hostel5 = "StayAbode Shared Living";
                hostelCount = 5;
            }
        } 
        else if (location.equals("Kondapur"))
        {
            if (category.equals("Boys")) 
            {
                hostel1 = "Royal Comforts Mens PG";
                hostel2 = "Elite Comforts Men’s Hostel";
                hostel3 = "Sunrise Gents PG";
                hostel4 = "Sri Sai Men’s PG";
                hostel5 = "Blue Haven Boys Hostel";
                hostel6 = "Shree Comforts Mens Hostel";
                hostel7 = "Om Shanti Gents Hostel";
                hostel8 = "Galaxy Deluxe Men’s PG";
                hostel9 = "Bhavani Comforts Mens PG";
                hostel10 = "Green Valley Boys Hostel";
                hostelCount = 10;
            } 
            else if (category.equals("Girls")) 
            {
                hostel1 = "Queen’s Stay Ladies PG";
                hostel2 = "Rose Villa Girls Hostel";
                hostel3 = "Elegant Stay Women’s Hostel";
                hostel4 = "Sri Durga Ladies PG";
                hostel5 = "Golden Nest Women’s Hostel";
                hostel6 = "Harmony Girls PG";
                hostel7 = "Pink Blossom Ladies Hostel";
                hostel8 = "Dreamland Women’s Hostel";
                hostel9 = "Lakshmi Comforts Girls PG";
                hostel10 = "Shree Comforts Women’s Hostel";
                hostelCount = 10;
            } 
            else 
            { // Co-living
                hostel1 = "Urban Stays Co-living";
                hostel2 = "CoHo Co-living";
                hostel3 = "StayAbode Shared Living";
                hostel4 = "The Hive Co-living";
                hostel5 = "Zolo Co-living";
                hostelCount = 5;
            }
        } 
        else if (location.equals("Banjara Hills"))
        {
            if (category.equals("Boys"))
            {
                hostel1 = "Sunrise Gents Hostel";
                hostel2 = "Sri Sai Men’s Hostel";
                hostel3 = "Royal Comforts Men’s PG";
                hostel4 = "Elite Comforts Gents PG";
                hostel5 = "Blue Haven Men’s Hostel";
                hostel6 = "Om Shanti Boys Hostel";
                hostel7 = "Galaxy Deluxe Mens PG";
                hostel8 = "Green Valley Gents PG";
                hostel9 = "Bhavani Deluxe Boys Hostel";
                hostel10 = "Shree Comforts Mens Hostel";
                hostelCount = 10;
            }
            else if (category.equals("Girls"))
            {
                hostel1 = "Pink Blossom Girls PG";
                hostel2 = "Dreamland Women’s Hostel";
                hostel3 = "Queen’s Stay Girls Hostel";
                hostel4 = "Golden Nest Ladies PG";
                hostel5 = "Harmony Women’s PG";
                hostel6 = "Rose Villa Girls PG";
                hostel7 = "Elegant Stay Women’s Hostel";
                hostel8 = "Lakshmi Comforts Girls Hostel";
                hostel9 = "Sri Durga Women’s PG";
                hostel10 = "Shree Comforts Girls Hostel";
                hostelCount = 10;
            } 
            else
            { // Co-living
                hostel1 = "StayAbode Shared Living";
                hostel2 = "Urban Stays Co-living";
                hostel3 = "The Hive Co-living";
                hostel4 = "CoHo Co-living";
                hostel5 = "Zolo Co-living";
                hostelCount = 5;
            }
        } 
        else if (location.equals("Begumpet")) 
        {
            if (category.equals("Boys"))
            {
                hostel1 = "Sunrise Deluxe Men’s Hostel";
                hostel2 = "Om Shanti Gents PG";
                hostel3 = "Green Valley Mens PG";
                hostel4 = "Shree Comforts Men’s Hostel";
                hostel5 = "Sri Sai Deluxe Gents Hostel";
                hostel6 = "Blue Haven Boys PG";
                hostel7 = "Royal Comforts Men’s PG";
                hostel8 = "Galaxy Deluxe Boys Hostel";
                hostel9 = "Elite Comforts Mens PG";
                hostel10 = "Bhavani Gents Hostel";
                hostelCount = 10;
            } 
            else if (category.equals("Girls")) 
            {
                hostel1 = "Queen’s Stay Women’s PG";
                hostel2 = "Golden Nest Ladies Hostel";
                hostel3 = "Lakshmi Comforts Girls PG";
                hostel4 = "Dreamland Women’s Hostel";
                hostel5 = "Rose Villa Girls PG";
                hostel6 = "Elegant Stay Women’s PG";
                hostel7 = "Pink Blossom Ladies Hostel";
                hostel8 = "Harmony Women’s PG";
                hostel9 = "Shree Comforts Girls Hostel";
                hostel10 = "Sri Durga Ladies Hostel";
                hostelCount = 10;
            } 
            else 
            { // Co-living
                hostel1 = "CoHo Co-living";
                hostel2 = "Urban Stays Co-living";
                hostel3 = "Zolo Co-living";
                hostel4 = "The Hive Co-living";
                hostel5 = "StayAbode Shared Living";
                hostelCount = 5;
            }
        } 
        else if (location.equals("Dilsukhnagar"))
        {
            if (category.equals("Boys"))
            {
                hostel1 = "Shree Comforts Gents PG";
                hostel2 = "Sri Sai Men’s Hostel";
                hostel3 = "Sunrise Deluxe Boys Hostel";
                hostel4 = "Blue Haven Gents Hostel";
                hostel5 = "Royal Comforts Men’s PG";
                hostel6 = "Bhavani Deluxe Gents PG";
                hostel7 = "Om Shanti Men’s Hostel";
                hostel8 = "Elite Comforts Mens PG";
                hostel9 = "Green Valley Boys PG";
                hostel10 = "Galaxy Deluxe Gents Hostel";
                hostelCount = 10;
            }
            else if (category.equals("Girls"))
            {
                hostel1 = "Pink Blossom Girls Hostel";
                hostel2 = "Queen’s Stay Women’s Hostel";
                hostel3 = "Dreamland Ladies Hostel";
                hostel4 = "Golden Nest Girls PG";
                hostel5 = "Lakshmi Deluxe Women’s PG";
                hostel6 = "Elegant Stay Ladies Hostel";
                hostel7 = "Rose Villa Girls Hostel";
                hostel8 = "Harmony Women’s PG";
                hostel9 = "Sri Durga Girls Hostel";
                hostel10 = "Shree Comforts Ladies PG";
                hostelCount = 10;
            } 
            else
            { // Co-living
                hostel1 = "Urban Stays Co-living";
                hostel2 = "CoHo Co-living";
                hostel3 = "Zolo Co-living";
                hostel4 = "StayAbode Shared Living";
                hostel5 = "The Hive Co-living";
                hostelCount = 5;
            }
        }
        else
        {
            System.out.println(red + bold + "Location not supported yet. Returning to location selection..." + reset);
            location();
            return;
        }

        while (true)
        {
            System.out.println(menuPadding + yellow + bold + "================ HOSTEL LIST ================");
            if (hostelCount >= 1)
                System.out.println(menuPadding +bold+ " 1. " + hostel1 + " ");
            if (hostelCount >= 2)
                System.out.println(menuPadding +bold+ " 2. " + hostel2 + " ");
            if (hostelCount >= 3)
                System.out.println(menuPadding+bold + " 3. " + hostel3 + " ");
            if (hostelCount >= 4)
                System.out.println(menuPadding+bold + " 4. " + hostel4 + " ");
            if (hostelCount >= 5)
                System.out.println(menuPadding+bold + " 5. " + hostel5 + " ");
            if (hostelCount >= 6)
                System.out.println(menuPadding+bold + " 6. " + hostel6 + " ");
            if (hostelCount >= 7)
                System.out.println(menuPadding+bold + " 7. " + hostel7 + " ");
            if (hostelCount >= 8)
                System.out.println(menuPadding+bold + " 8. " + hostel8 + " ");
            if (hostelCount >= 9)
                System.out.println(menuPadding +bold+ " 9. " + hostel9 + " ");
            if (hostelCount >= 10)
                System.out.println(menuPadding +bold+ " 10. " + hostel10 + " ");
            System.out.println(menuPadding + "=============================================");
            System.out.print(reset +bold+ "Enter choice: ");
            int choice = sc.nextInt();
            if (choice >= 1 && choice <= hostelCount) 
            {
                String selectedHostel = "";
                if (choice == 1)
                    selectedHostel = hostel1;
                if (choice == 2)
                    selectedHostel = hostel2;
                if (choice == 3)
                    selectedHostel = hostel3;
                if (choice == 4)
                    selectedHostel = hostel4;
                if (choice == 5)
                    selectedHostel = hostel5;
                if (choice == 6)
                    selectedHostel = hostel6;
                if (choice == 7)
                    selectedHostel = hostel7;
                if (choice == 8)
                    selectedHostel = hostel8;
                if (choice == 9)
                    selectedHostel = hostel9;
                if (choice == 10)
                    selectedHostel = hostel10;
                showHostelDetails(selectedHostel, location, category);
                break;
            } 
            else
            {
                System.out.println(red +bold+ "Invalid choice! Please try again." + reset);
            }
        }
    }

    static LocationDetails getLocationDetails(String location)
    {
        if (location.equals("Kphb"))
            return new KphbLocation();
        if (location.equals("Ameerpet"))
            return new AmeerpetLocation();
        if (location.equals("SR Nagar"))
            return new SRNagarLocation();
        if (location.equals("Kukatpally"))
            return new KukatpallyLocation();
        if (location.equals("Madhapur"))
            return new MadhapurLocation();
        if (location.equals("Gachibowli"))
            return new GachibowliLocation();
        if (location.equals("Kondapur"))
            return new KondapurLocation();
        if (location.equals("Banjara Hills"))
            return new BanjaraHillsLocation();
        if (location.equals("Begumpet"))
            return new BegumpetLocation();
        if (location.equals("Dilsukhnagar"))
            return new DilsukhnagarLocation();
        return new KphbLocation(); // Fallback
    }

    static void showHostelDetails(String hostelName, String location, String category) {
        System.out.println();
        System.out.println(menuPadding + bold + cyan + "=====" + hostelName + " ====" + reset);
        int rate1 = 0, rate2 = 0, rate3 = 0, rate4 = 0, rate5 = 0;
        double rating = 0.0;

        // KPHB Boys PGs
        if (hostelName.equals("Sunrise Boys KPHB")) 
        {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.2;
        }
        else if(hostelName.equals("Green View Boys KPHB")) 
        {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 4.0;
        } 
        else if (hostelName.equals("Blue Sky PG KPHB")) 
        {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.8;
        }
        else if (hostelName.equals("Metro Comfort Boys KPHB")) 
        {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.7;
        } 
        else if (hostelName.equals("Prime Stay Boys KPHB"))
        {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.6;
        } 
        else if (hostelName.equals("Urban Nest Boys KPHB"))
        {
            rate1 = 8500;
            rate2 = 6500;
            rate3 = 5500;
            rate4 = 5000;
            rate5 = 4500;
            rating = 3.5;
        } 
        else if (hostelName.equals("City Heart Boys KPHB")) 
        {
            rate1 = 8400;
            rate2 = 6400;
            rate3 = 5400;
            rate4 = 4900;
            rate5 = 4400;
            rating = 3.4;
        } 
        else if (hostelName.equals("Comfort Inn Boys KPHB"))
        {
            rate1 = 8300;
            rate2 = 6300;
            rate3 = 5300;
            rate4 = 4800;
            rate5 = 4300;
            rating = 3.3;
        } 
        else if (hostelName.equals("Skyline Boys KPHB"))
        {
            rate1 = 8200;
            rate2 = 6200;
            rate3 = 5200;
            rate4 = 4700;
            rate5 = 4200;
            rating = 3.2;
        } 
        else if (hostelName.equals("Elite Boys Hostel KPHB")) 
        {
            rate1 = 8100;
            rate2 = 6100;
            rate3 = 5100;
            rate4 = 4600;
            rate5 = 4100;
            rating = 3.1;
        }
        // Girls PGs
        else if (hostelName.equals("Pink Blossom Girls KPHB"))
        {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.2;
        } 
        else if (hostelName.equals("Queen’s Stay Girls KPHB"))
        {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 4.0;
        } else if (hostelName.equals("Grace Villa Girls KPHB")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.8;
        } else if (hostelName.equals("Golden Nest Girls KPHB")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.7;
        } else if (hostelName.equals("Dreamland Girls KPHB")) {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.6;
        } else if (hostelName.equals("Royal Comfort Girls KPHB")) {
            rate1 = 8500;
            rate2 = 6500;
            rate3 = 5500;
            rate4 = 5000;
            rate5 = 4500;
            rating = 3.5;
        } else if (hostelName.equals("Pearl Stay Girls KPHB")) {
            rate1 = 8400;
            rate2 = 6400;
            rate3 = 5400;
            rate4 = 4900;
            rate5 = 4400;
            rating = 3.4;
        } else if (hostelName.equals("City Pearl Girls KPHB")) {
            rate1 = 8300;
            rate2 = 6300;
            rate3 = 5300;
            rate4 = 4800;
            rate5 = 4300;
            rating = 3.3;
        } else if (hostelName.equals("Blossom Stay Girls KPHB")) {
            rate1 = 8200;
            rate2 = 6200;
            rate3 = 5200;
            rate4 = 4700;
            rate5 = 4200;
            rating = 3.2;
        } else if (hostelName.equals("Elite Girls Hostel KPHB")) {
            rate1 = 8100;
            rate2 = 6100;
            rate3 = 5100;
            rate4 = 4600;
            rate5 = 4100;
            rating = 3.1;
        }
        // Co-living PGs
        else if (hostelName.equals("Metro Co-living KPHB")) {
            rate1 = 9000;
            rate2 = 8000;
            rate3 = 7000;
            rate4 = 6500;
            rate5 = 6000;
            rating = 4.1;
        } else if (hostelName.equals("Urban Hub Co-living KPHB")) {
            rate1 = 8800;
            rate2 = 7800;
            rate3 = 6800;
            rate4 = 6300;
            rate5 = 5800;
            rating = 4.0;
        } else if (hostelName.equals("Shared Comfort Co-living KPHB")) {
            rate1 = 8600;
            rate2 = 7600;
            rate3 = 6600;
            rate4 = 6100;
            rate5 = 5600;
            rating = 3.9;
        } else if (hostelName.equals("Elite Co-living KPHB")) {
            rate1 = 8500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 3.8;
        } else if (hostelName.equals("The Hive Co-living KPHB")) {
            rate1 = 8400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 3.7;
        }
        // Ameerpet PGs
        else if (hostelName.equals("Bhavani Delux Pg For Mens")) {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.2;
        } else if (hostelName.equals("Vijetha Mens Hostel")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 4.0;
        } else if (hostelName.equals("Green View Boys Hostel")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.8;
        } else if (hostelName.equals("Sri Sai Krishna Gents Hostel")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.7;
        } else if (hostelName.equals("Royal Comforts Men’s PG")) {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.6;
        } else if (hostelName.equals("Blue Heaven Boys Hostel")) {
            rate1 = 8500;
            rate2 = 6500;
            rate3 = 5500;
            rate4 = 5000;
            rate5 = 4500;
            rating = 3.5;
        } else if (hostelName.equals("Om Sai Deluxe Mens PG")) {
            rate1 = 8400;
            rate2 = 6400;
            rate3 = 5400;
            rate4 = 4900;
            rate5 = 4400;
            rating = 3.4;
        } else if (hostelName.equals("Sunshine Gents Hostel")) {
            rate1 = 8300;
            rate2 = 6300;
            rate3 = 5300;
            rate4 = 4800;
            rate5 = 4300;
            rating = 3.3;
        } else if (hostelName.equals("Galaxy Boys Hostel")) {
            rate1 = 8200;
            rate2 = 6200;
            rate3 = 5200;
            rate4 = 4700;
            rate5 = 4200;
            rating = 3.2;
        } else if (hostelName.equals("Elite Comforts Mens PG")) {
            rate1 = 8100;
            rate2 = 6100;
            rate3 = 5100;
            rate4 = 4600;
            rate5 = 4100;
            rating = 3.1;
        }
        // Ameerpet Girls PGs
        else if (hostelName.equals("Lakshmi Deluxe Women’s Hostel")) {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.2;
        } else if (hostelName.equals("Rose Villa Girls Hostel")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 4.0;
        } else if (hostelName.equals("Sri Durga Ladies PG")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.8;
        } else if (hostelName.equals("Harmony Women’s Hostel")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.7;
        } else if (hostelName.equals("Golden Nest Girls PG")) {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.6;
        } else if (hostelName.equals("Pink Blossom Women’s Hostel")) {
            rate1 = 8500;
            rate2 = 6500;
            rate3 = 5500;
            rate4 = 5000;
            rate5 = 4500;
            rating = 3.5;
        } else if (hostelName.equals("Queen’s Stay Ladies PG")) {
            rate1 = 8400;
            rate2 = 6400;
            rate3 = 5400;
            rate4 = 4900;
            rate5 = 4400;
            rating = 3.4;
        } else if (hostelName.equals("Shree Comforts Girls Hostel")) {
            rate1 = 8300;
            rate2 = 6300;
            rate3 = 5300;
            rate4 = 4800;
            rate5 = 4300;
            rating = 3.3;
        } else if (hostelName.equals("Dreamland Women’s PG")) {
            rate1 = 8200;
            rate2 = 6200;
            rate3 = 5200;
            rate4 = 4700;
            rate5 = 4200;
            rating = 3.2;
        } else if (hostelName.equals("Elegant Stay Girls Hostel")) {
            rate1 = 8100;
            rate2 = 6100;
            rate3 = 5100;
            rate4 = 4600;
            rate5 = 4100;
            rating = 3.1;
        }
        // Ameerpet Co-living
        else if (hostelName.equals("Urban Stays Co-living")) {
            rate1 = 9000;
            rate2 = 8000;
            rate3 = 7000;
            rate4 = 6500;
            rate5 = 6000;
            rating = 4.1;
        } else if (hostelName.equals("The Hive Co-living")) {
            rate1 = 8800;
            rate2 = 7800;
            rate3 = 6800;
            rate4 = 6300;
            rate5 = 5800;
            rating = 4.0;
        } else if (hostelName.equals("StayAbode Shared Living")) {
            rate1 = 8600;
            rate2 = 7600;
            rate3 = 6600;
            rate4 = 6100;
            rate5 = 5600;
            rating = 3.9;
        } else if (hostelName.equals("Zolo Co-living")) {
            rate1 = 8500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 3.8;
        } else if (hostelName.equals("CoHo Co-living")) {
            rate1 = 8400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 3.7;
        }

        // ================== SR Nagar Boys ==================
        else if (hostelName.equals("Sai Krishna Mens PG")) {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.2;
        } else if (hostelName.equals("Royal Comforts Boys Hostel")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 4.0;
        } else if (hostelName.equals("Elite Comforts Men’s Hostel")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.8;
        } else if (hostelName.equals("Sunshine Mens PG")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.7;
        } else if (hostelName.equals("Vijetha Gents Hostel")) {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.6;
        } else if (hostelName.equals("Blue Heaven Men’s Hostel")) {
            rate1 = 8500;
            rate2 = 6500;
            rate3 = 5500;
            rate4 = 5000;
            rate5 = 4500;
            rating = 3.5;
        } else if (hostelName.equals("Green View Gents Hostel")) {
            rate1 = 8400;
            rate2 = 6400;
            rate3 = 5400;
            rate4 = 4900;
            rate5 = 4400;
            rating = 3.4;
        } else if (hostelName.equals("Galaxy Men’s PG")) {
            rate1 = 8300;
            rate2 = 6300;
            rate3 = 5300;
            rate4 = 4800;
            rate5 = 4300;
            rating = 3.3;
        } else if (hostelName.equals("Om Sai Deluxe Mens Hostel")) {
            rate1 = 8200;
            rate2 = 6200;
            rate3 = 5200;
            rate4 = 4700;
            rate5 = 4200;
            rating = 3.2;
        } else if (hostelName.equals("Bhavani Deluxe Boys PG")) {
            rate1 = 8100;
            rate2 = 6100;
            rate3 = 5100;
            rate4 = 4600;
            rate5 = 4100;
            rating = 3.1;
        }

        // ================== SR Nagar Girls ==================
        else if (hostelName.equals("Dreamland Women’s PG")) {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.2;
        } else if (hostelName.equals("Queen’s Stay Girls Hostel")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 4.0;
        } else if (hostelName.equals("Harmony Women’s Hostel")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.8;
        } else if (hostelName.equals("Rose Villa Ladies PG")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.7;
        } else if (hostelName.equals("Golden Nest Girls Hostel")) {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.6;
        } else if (hostelName.equals("Pink Blossom Ladies Hostel")) {
            rate1 = 8500;
            rate2 = 6500;
            rate3 = 5500;
            rate4 = 5000;
            rate5 = 4500;
            rating = 3.5;
        } else if (hostelName.equals("Elegant Stay Girls Hostel")) {
            rate1 = 8400;
            rate2 = 6400;
            rate3 = 5400;
            rate4 = 4900;
            rate5 = 4400;
            rating = 3.4;
        } else if (hostelName.equals("Sri Durga Ladies PG")) {
            rate1 = 8300;
            rate2 = 6300;
            rate3 = 5300;
            rate4 = 4800;
            rate5 = 4300;
            rating = 3.3;
        } else if (hostelName.equals("Shree Comforts Women’s Hostel")) {
            rate1 = 8200;
            rate2 = 6200;
            rate3 = 5200;
            rate4 = 4700;
            rate5 = 4200;
            rating = 3.2;
        } else if (hostelName.equals("Lakshmi Deluxe Women’s Hostel")) {
            rate1 = 8100;
            rate2 = 6100;
            rate3 = 5100;
            rate4 = 4600;
            rate5 = 4100;
            rating = 3.1;
        }

        // ================== SR Nagar Co-living ==================
        else if (hostelName.equals("CoHo Co-living")) {
            rate1 = 9000;
            rate2 = 8000;
            rate3 = 7000;
            rate4 = 6500;
            rate5 = 6000;
            rating = 4.1;
        } else if (hostelName.equals("Zolo Co-living")) {
            rate1 = 8800;
            rate2 = 7800;
            rate3 = 6800;
            rate4 = 6300;
            rate5 = 5800;
            rating = 4.0;
        } else if (hostelName.equals("Urban Stays Co-living")) {
            rate1 = 8600;
            rate2 = 7600;
            rate3 = 6600;
            rate4 = 6100;
            rate5 = 5600;
            rating = 3.9;
        } else if (hostelName.equals("StayAbode Shared Living")) {
            rate1 = 8500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 3.8;
        } else if (hostelName.equals("The Hive Co-living")) {
            rate1 = 8400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 3.7;
        }

        // ================== Kukatpally Boys ==================
        else if (hostelName.equals("Blue Haven Gents PG")) {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.2;
        } else if (hostelName.equals("Sri Sai Men’s Hostel")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 4.0;
        } else if (hostelName.equals("Elite Comforts Mens PG")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.8;
        } else if (hostelName.equals("Om Shanti Boys Hostel")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.7;
        } else if (hostelName.equals("Royal Comforts Men’s Hostel")) {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.6;
        } else if (hostelName.equals("Sunrise Gents Hostel")) {
            rate1 = 8500;
            rate2 = 6500;
            rate3 = 5500;
            rate4 = 5000;
            rate5 = 4500;
            rating = 3.5;
        } else if (hostelName.equals("Green Valley Mens PG")) {
            rate1 = 8400;
            rate2 = 6400;
            rate3 = 5400;
            rate4 = 4900;
            rate5 = 4400;
            rating = 3.4;
        } else if (hostelName.equals("Galaxy Deluxe Boys Hostel")) {
            rate1 = 8300;
            rate2 = 6300;
            rate3 = 5300;
            rate4 = 4800;
            rate5 = 4300;
            rating = 3.3;
        } else if (hostelName.equals("Shree Comforts Mens PG")) {
            rate1 = 8200;
            rate2 = 6200;
            rate3 = 5200;
            rate4 = 4700;
            rate5 = 4200;
            rating = 3.2;
        } else if (hostelName.equals("Bhavani Deluxe Gents Hostel")) {
            rate1 = 8100;
            rate2 = 6100;
            rate3 = 5100;
            rate4 = 4600;
            rate5 = 4100;
            rating = 3.1;
        }

        // ================== Kukatpally Girls ==================
        else if (hostelName.equals("Queen’s Stay Ladies Hostel")) {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.2;
        } else if (hostelName.equals("Elegant Stay Women’s PG")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 4.0;
        } else if (hostelName.equals("Golden Nest Ladies Hostel")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.8;
        } else if (hostelName.equals("Dreamland Girls Hostel")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.7;
        } else if (hostelName.equals("Harmony Women’s PG")) {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.6;
        } else if (hostelName.equals("Rose Villa Ladies Hostel")) {
            rate1 = 8500;
            rate2 = 6500;
            rate3 = 5500;
            rate4 = 5000;
            rate5 = 4500;
            rating = 3.5;
        } else if (hostelName.equals("Pink Blossom Girls PG")) {
            rate1 = 8400;
            rate2 = 6400;
            rate3 = 5400;
            rate4 = 4900;
            rate5 = 4400;
            rating = 3.4;
        } else if (hostelName.equals("Sri Durga Women’s Hostel")) {
            rate1 = 8300;
            rate2 = 6300;
            rate3 = 5300;
            rate4 = 4800;
            rate5 = 4300;
            rating = 3.3;
        } else if (hostelName.equals("Shree Comforts Girls PG")) {
            rate1 = 8200;
            rate2 = 6200;
            rate3 = 5200;
            rate4 = 4700;
            rate5 = 4200;
            rating = 3.2;
        } else if (hostelName.equals("Lakshmi Deluxe Women’s Hostel")) {
            rate1 = 8100;
            rate2 = 6100;
            rate3 = 5100;
            rate4 = 4600;
            rate5 = 4100;
            rating = 3.1;
        }

        // ================== Kukatpally Co-living ==================
        else if (hostelName.equals("Urban Stays Co-living")) {
            rate1 = 9000;
            rate2 = 8000;
            rate3 = 7000;
            rate4 = 6500;
            rate5 = 6000;
            rating = 4.1;
        } else if (hostelName.equals("CoHo Co-living")) {
            rate1 = 8800;
            rate2 = 7800;
            rate3 = 6800;
            rate4 = 6300;
            rate5 = 5800;
            rating = 4.0;
        } else if (hostelName.equals("The Hive Co-living")) {
            rate1 = 8600;
            rate2 = 7600;
            rate3 = 6600;
            rate4 = 6100;
            rate5 = 5600;
            rating = 3.9;
        } else if (hostelName.equals("StayAbode Shared Living")) {
            rate1 = 8500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 3.8;
        } else if (hostelName.equals("Zolo Co-living")) {
            rate1 = 8400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 3.7;
        }
        // ================== Madhapur Boys ==================
        else if (hostelName.equals("Sunrise Gents Hostel")) {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.2;
        } else if (hostelName.equals("Elite Comforts Mens PG")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 4.0;
        } else if (hostelName.equals("Royal Comforts Men’s Hostel")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.8;
        } else if (hostelName.equals("Sri Sai Men’s Hostel")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.7;
        } else if (hostelName.equals("Om Shanti Boys Hostel")) {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.6;
        } else if (hostelName.equals("Green Valley Mens PG")) {
            rate1 = 8500;
            rate2 = 6500;
            rate3 = 5500;
            rate4 = 5000;
            rate5 = 4500;
            rating = 3.5;
        } else if (hostelName.equals("Galaxy Deluxe Boys Hostel")) {
            rate1 = 8400;
            rate2 = 6400;
            rate3 = 5400;
            rate4 = 4900;
            rate5 = 4400;
            rating = 3.4;
        } else if (hostelName.equals("Bhavani Deluxe Gents Hostel")) {
            rate1 = 8300;
            rate2 = 6300;
            rate3 = 5300;
            rate4 = 4800;
            rate5 = 4300;
            rating = 3.3;
        } else if (hostelName.equals("Shree Comforts Mens PG")) {
            rate1 = 8200;
            rate2 = 6200;
            rate3 = 5200;
            rate4 = 4700;
            rate5 = 4200;
            rating = 3.2;
        } else if (hostelName.equals("Blue Haven Gents PG")) {
            rate1 = 8100;
            rate2 = 6100;
            rate3 = 5100;
            rate4 = 4600;
            rate5 = 4100;
            rating = 3.1;
        }

        // ================== Madhapur Girls ==================
        else if (hostelName.equals("Pink Blossom Girls PG")) {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.2;
        } else if (hostelName.equals("Elegant Stay Women’s PG")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 4.0;
        } else if (hostelName.equals("Rose Villa Ladies Hostel")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.8;
        } else if (hostelName.equals("Sri Durga Women’s Hostel")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.7;
        } else if (hostelName.equals("Golden Nest Ladies Hostel")) {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.6;
        } else if (hostelName.equals("Queen’s Stay Ladies Hostel")) {
            rate1 = 8500;
            rate2 = 6500;
            rate3 = 5500;
            rate4 = 5000;
            rate5 = 4500;
            rating = 3.5;
        } else if (hostelName.equals("Harmony Women’s PG")) {
            rate1 = 8400;
            rate2 = 6400;
            rate3 = 5400;
            rate4 = 4900;
            rate5 = 4400;
            rating = 3.4;
        } else if (hostelName.equals("Lakshmi Deluxe Women’s Hostel")) {
            rate1 = 8300;
            rate2 = 6300;
            rate3 = 5300;
            rate4 = 4800;
            rate5 = 4300;
            rating = 3.3;
        } else if (hostelName.equals("Shree Comforts Girls PG")) {
            rate1 = 8200;
            rate2 = 6200;
            rate3 = 5200;
            rate4 = 4700;
            rate5 = 4200;
            rating = 3.2;
        } else if (hostelName.equals("Dreamland Girls Hostel")) {
            rate1 = 8100;
            rate2 = 6100;
            rate3 = 5100;
            rate4 = 4600;
            rate5 = 4100;
            rating = 3.1;
        }

        // ================== Madhapur Co-living ==================
        else if (hostelName.equals("The Hive Co-living")) {
            rate1 = 9000;
            rate2 = 8000;
            rate3 = 7000;
            rate4 = 6500;
            rate5 = 6000;
            rating = 4.1;
        } else if (hostelName.equals("Zolo Co-living")) {
            rate1 = 8800;
            rate2 = 7800;
            rate3 = 6800;
            rate4 = 6300;
            rate5 = 5800;
            rating = 4.0;
        } else if (hostelName.equals("Urban Stays Co-living")) {
            rate1 = 8600;
            rate2 = 7600;
            rate3 = 6600;
            rate4 = 6100;
            rate5 = 5600;
            rating = 3.9;
        } else if (hostelName.equals("CoHo Co-living")) {
            rate1 = 8500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 3.8;
        } else if (hostelName.equals("StayAbode Shared Living")) {
            rate1 = 8400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 3.7;
        }
        // ================== Gachibowli Boys ==================
        else if (hostelName.equals("Green Valley Mens PG")) {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.2;
        } else if (hostelName.equals("Om Shanti Boys Hostel")) {
            rate1 = 9400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 4.1;
        } else if (hostelName.equals("Shree Comforts Mens PG")) {
            rate1 = 9300;
            rate2 = 7300;
            rate3 = 6300;
            rate4 = 5800;
            rate5 = 5300;
            rating = 4.0;
        } else if (hostelName.equals("Galaxy Deluxe Boys Hostel")) {
            rate1 = 9200;
            rate2 = 7200;
            rate3 = 6200;
            rate4 = 5700;
            rate5 = 5200;
            rating = 3.9;
        } else if (hostelName.equals("Royal Comforts Men’s Hostel")) {
            rate1 = 9100;
            rate2 = 7100;
            rate3 = 6100;
            rate4 = 5600;
            rate5 = 5100;
            rating = 3.8;
        } else if (hostelName.equals("Sri Sai Men’s Hostel")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 3.7;
        } else if (hostelName.equals("Sunrise Gents Hostel")) {
            rate1 = 8900;
            rate2 = 6900;
            rate3 = 5900;
            rate4 = 5400;
            rate5 = 4900;
            rating = 3.6;
        } else if (hostelName.equals("Elite Comforts Mens PG")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.5;
        } else if (hostelName.equals("Blue Haven Gents PG")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.4;
        } else if (hostelName.equals("Bhavani Deluxe Gents Hostel")) {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.3;
        }

        // ================== Gachibowli Girls ==================
        else if (hostelName.equals("Harmony Women’s PG")) {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.2;
        } else if (hostelName.equals("Queen’s Stay Ladies Hostel")) {
            rate1 = 9400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 4.1;
        } else if (hostelName.equals("Lakshmi Deluxe Women’s Hostel")) {
            rate1 = 9300;
            rate2 = 7300;
            rate3 = 6300;
            rate4 = 5800;
            rate5 = 5300;
            rating = 4.0;
        } else if (hostelName.equals("Golden Nest Ladies Hostel")) {
            rate1 = 9200;
            rate2 = 7200;
            rate3 = 6200;
            rate4 = 5700;
            rate5 = 5200;
            rating = 3.9;
        } else if (hostelName.equals("Pink Blossom Girls PG")) {
            rate1 = 9100;
            rate2 = 7100;
            rate3 = 6100;
            rate4 = 5600;
            rate5 = 5100;
            rating = 3.8;
        } else if (hostelName.equals("Rose Villa Ladies Hostel")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 3.7;
        } else if (hostelName.equals("Elegant Stay Women’s PG")) {
            rate1 = 8900;
            rate2 = 6900;
            rate3 = 5900;
            rate4 = 5400;
            rate5 = 4900;
            rating = 3.6;
        } else if (hostelName.equals("Dreamland Girls Hostel")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.5;
        } else if (hostelName.equals("Sri Durga Women’s Hostel")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.4;
        } else if (hostelName.equals("Shree Comforts Girls PG")) {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.3;
        }

        // ================== Gachibowli Co-living ==================
        else if (hostelName.equals("CoHo Co-living")) {
            rate1 = 9000;
            rate2 = 8000;
            rate3 = 7000;
            rate4 = 6500;
            rate5 = 6000;
            rating = 4.1;
        } else if (hostelName.equals("Zolo Co-living")) {
            rate1 = 8800;
            rate2 = 7800;
            rate3 = 6800;
            rate4 = 6300;
            rate5 = 5800;
            rating = 4.0;
        } else if (hostelName.equals("Urban Stays Co-living")) {
            rate1 = 8600;
            rate2 = 7600;
            rate3 = 6600;
            rate4 = 6100;
            rate5 = 5600;
            rating = 3.9;
        } else if (hostelName.equals("The Hive Co-living")) {
            rate1 = 8500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 3.8;
        } else if (hostelName.equals("StayAbode Shared Living")) {
            rate1 = 8400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 3.7;
        }
        // ================== Kondapur Boys ==================
        else if (hostelName.equals("Royal Comforts Mens PG")) {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.2;
        } else if (hostelName.equals("Elite Comforts Men’s Hostel")) {
            rate1 = 9400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 4.1;
        } else if (hostelName.equals("Sunrise Gents PG")) {
            rate1 = 9300;
            rate2 = 7300;
            rate3 = 6300;
            rate4 = 5800;
            rate5 = 5300;
            rating = 4.0;
        } else if (hostelName.equals("Sri Sai Men’s PG")) {
            rate1 = 9200;
            rate2 = 7200;
            rate3 = 6200;
            rate4 = 5700;
            rate5 = 5200;
            rating = 3.9;
        } else if (hostelName.equals("Blue Haven Boys Hostel")) {
            rate1 = 9100;
            rate2 = 7100;
            rate3 = 6100;
            rate4 = 5600;
            rate5 = 5100;
            rating = 3.8;
        } else if (hostelName.equals("Shree Comforts Mens Hostel")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 3.7;
        } else if (hostelName.equals("Om Shanti Gents Hostel")) {
            rate1 = 8900;
            rate2 = 6900;
            rate3 = 5900;
            rate4 = 5400;
            rate5 = 4900;
            rating = 3.6;
        } else if (hostelName.equals("Galaxy Deluxe Men’s PG")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.5;
        } else if (hostelName.equals("Bhavani Comforts Mens PG")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.4;
        } else if (hostelName.equals("Green Valley Boys Hostel")) {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.3;
        }

        // ================== Kondapur Girls ==================
        else if (hostelName.equals("Queen’s Stay Ladies PG")) {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.2;
        } else if (hostelName.equals("Rose Villa Girls Hostel")) {
            rate1 = 9400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 4.1;
        } else if (hostelName.equals("Elegant Stay Women’s Hostel")) {
            rate1 = 9300;
            rate2 = 7300;
            rate3 = 6300;
            rate4 = 5800;
            rate5 = 5300;
            rating = 4.0;
        } else if (hostelName.equals("Sri Durga Ladies PG")) {
            rate1 = 9200;
            rate2 = 7200;
            rate3 = 6200;
            rate4 = 5700;
            rate5 = 5200;
            rating = 3.9;
        } else if (hostelName.equals("Golden Nest Women’s Hostel")) {
            rate1 = 9100;
            rate2 = 7100;
            rate3 = 6100;
            rate4 = 5600;
            rate5 = 5100;
            rating = 3.8;
        } else if (hostelName.equals("Harmony Girls PG")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 3.7;
        } else if (hostelName.equals("Pink Blossom Ladies Hostel")) {
            rate1 = 8900;
            rate2 = 6900;
            rate3 = 5900;
            rate4 = 5400;
            rate5 = 4900;
            rating = 3.6;
        } else if (hostelName.equals("Dreamland Women’s Hostel")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.5;
        } else if (hostelName.equals("Lakshmi Comforts Girls PG")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.4;
        } else if (hostelName.equals("Shree Comforts Women’s Hostel")) {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.3;
        }

        // ================== Kondapur Co-living ==================
        else if (hostelName.equals("Urban Stays Co-living")) {
            rate1 = 9000;
            rate2 = 8000;
            rate3 = 7000;
            rate4 = 6500;
            rate5 = 6000;
            rating = 4.1;
        } else if (hostelName.equals("CoHo Co-living")) {
            rate1 = 8800;
            rate2 = 7800;
            rate3 = 6800;
            rate4 = 6300;
            rate5 = 5800;
            rating = 4.0;
        } else if (hostelName.equals("StayAbode Shared Living")) {
            rate1 = 8600;
            rate2 = 7600;
            rate3 = 6600;
            rate4 = 6100;
            rate5 = 5600;
            rating = 3.9;
        } else if (hostelName.equals("The Hive Co-living")) {
            rate1 = 8500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 3.8;
        } else if (hostelName.equals("Zolo Co-living")) {
            rate1 = 8400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 3.7;
        }

        // ================== Banjara Hills Boys ==================
        else if (hostelName.equals("Sunrise Gents Hostel")) {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.2;
        } else if (hostelName.equals("Sri Sai Men’s Hostel")) {
            rate1 = 9400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 4.1;
        } else if (hostelName.equals("Royal Comforts Men’s PG")) {
            rate1 = 9300;
            rate2 = 7300;
            rate3 = 6300;
            rate4 = 5800;
            rate5 = 5300;
            rating = 4.0;
        } else if (hostelName.equals("Elite Comforts Gents PG")) {
            rate1 = 9200;
            rate2 = 7200;
            rate3 = 6200;
            rate4 = 5700;
            rate5 = 5200;
            rating = 3.9;
        } else if (hostelName.equals("Blue Haven Men’s Hostel")) {
            rate1 = 9100;
            rate2 = 7100;
            rate3 = 6100;
            rate4 = 5600;
            rate5 = 5100;
            rating = 3.8;
        } else if (hostelName.equals("Om Shanti Boys Hostel")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 3.7;
        } else if (hostelName.equals("Galaxy Deluxe Mens PG")) {
            rate1 = 8900;
            rate2 = 6900;
            rate3 = 5900;
            rate4 = 5400;
            rate5 = 4900;
            rating = 3.6;
        } else if (hostelName.equals("Green Valley Gents PG")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.5;
        } else if (hostelName.equals("Bhavani Deluxe Boys Hostel")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.4;
        } else if (hostelName.equals("Shree Comforts Mens Hostel")) {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.3;
        }

        // ================== Banjara Hills Girls ==================
        else if (hostelName.equals("Pink Blossom Girls PG")) {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.2;
        } else if (hostelName.equals("Dreamland Women’s Hostel")) {
            rate1 = 9400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 4.1;
        } else if (hostelName.equals("Queen’s Stay Girls Hostel")) {
            rate1 = 9300;
            rate2 = 7300;
            rate3 = 6300;
            rate4 = 5800;
            rate5 = 5300;
            rating = 4.0;
        } else if (hostelName.equals("Golden Nest Ladies PG")) {
            rate1 = 9200;
            rate2 = 7200;
            rate3 = 6200;
            rate4 = 5700;
            rate5 = 5200;
            rating = 3.9;
        } else if (hostelName.equals("Harmony Women’s PG")) {
            rate1 = 9100;
            rate2 = 7100;
            rate3 = 6100;
            rate4 = 5600;
            rate5 = 5100;
            rating = 3.8;
        } else if (hostelName.equals("Rose Villa Girls PG")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 3.7;
        } else if (hostelName.equals("Elegant Stay Women’s Hostel")) {
            rate1 = 8900;
            rate2 = 6900;
            rate3 = 5900;
            rate4 = 5400;
            rate5 = 4900;
            rating = 3.6;
        } else if (hostelName.equals("Lakshmi Comforts Girls Hostel")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.5;
        } else if (hostelName.equals("Sri Durga Women’s PG")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.4;
        } else if (hostelName.equals("Shree Comforts Girls Hostel")) {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.3;
        }

        // ================== Banjara Hills Co-living ==================
        else if (hostelName.equals("StayAbode Shared Living")) {
            rate1 = 9000;
            rate2 = 8000;
            rate3 = 7000;
            rate4 = 6500;
            rate5 = 6000;
            rating = 4.1;
        } else if (hostelName.equals("Urban Stays Co-living")) {
            rate1 = 8800;
            rate2 = 7800;
            rate3 = 6800;
            rate4 = 6300;
            rate5 = 5800;
            rating = 4.0;
        } else if (hostelName.equals("The Hive Co-living")) {
            rate1 = 8600;
            rate2 = 7600;
            rate3 = 6600;
            rate4 = 6100;
            rate5 = 5600;
            rating = 3.9;
        } else if (hostelName.equals("CoHo Co-living")) {
            rate1 = 8500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 3.8;
        } else if (hostelName.equals("Zolo Co-living")) {
            rate1 = 8400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 3.7;
        }

        // ================== Begumpet Boys ==================
        else if (hostelName.equals("Sunrise Deluxe Men’s Hostel")) {
            rate1 = 9600;
            rate2 = 7600;
            rate3 = 6600;
            rate4 = 6100;
            rate5 = 5600;
            rating = 4.2;
        } else if (hostelName.equals("Om Shanti Gents PG")) {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.1;
        } else if (hostelName.equals("Green Valley Mens PG")) {
            rate1 = 9400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 4.0;
        } else if (hostelName.equals("Shree Comforts Men’s Hostel")) {
            rate1 = 9300;
            rate2 = 7300;
            rate3 = 6300;
            rate4 = 5800;
            rate5 = 5300;
            rating = 3.9;
        } else if (hostelName.equals("Sri Sai Deluxe Gents Hostel")) {
            rate1 = 9200;
            rate2 = 7200;
            rate3 = 6200;
            rate4 = 5700;
            rate5 = 5200;
            rating = 3.8;
        } else if (hostelName.equals("Blue Haven Boys PG")) {
            rate1 = 9100;
            rate2 = 7100;
            rate3 = 6100;
            rate4 = 5600;
            rate5 = 5100;
            rating = 3.7;
        } else if (hostelName.equals("Royal Comforts Men’s PG")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 3.6;
        } else if (hostelName.equals("Galaxy Deluxe Boys Hostel")) {
            rate1 = 8900;
            rate2 = 6900;
            rate3 = 5900;
            rate4 = 5400;
            rate5 = 4900;
            rating = 3.5;
        } else if (hostelName.equals("Elite Comforts Mens PG")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.4;
        } else if (hostelName.equals("Bhavani Gents Hostel")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.3;
        }

        // ================== Begumpet Girls ==================
        else if (hostelName.equals("Queen’s Stay Women’s PG")) {
            rate1 = 9600;
            rate2 = 7600;
            rate3 = 6600;
            rate4 = 6100;
            rate5 = 5600;
            rating = 4.2;
        } else if (hostelName.equals("Golden Nest Ladies Hostel")) {
            rate1 = 9500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 4.1;
        } else if (hostelName.equals("Lakshmi Comforts Girls PG")) {
            rate1 = 9400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 4.0;
        } else if (hostelName.equals("Dreamland Women’s Hostel")) {
            rate1 = 9300;
            rate2 = 7300;
            rate3 = 6300;
            rate4 = 5800;
            rate5 = 5300;
            rating = 3.9;
        } else if (hostelName.equals("Rose Villa Girls PG")) {
            rate1 = 9200;
            rate2 = 7200;
            rate3 = 6200;
            rate4 = 5700;
            rate5 = 5200;
            rating = 3.8;
        } else if (hostelName.equals("Elegant Stay Women’s PG")) {
            rate1 = 9100;
            rate2 = 7100;
            rate3 = 6100;
            rate4 = 5600;
            rate5 = 5100;
            rating = 3.7;
        } else if (hostelName.equals("Pink Blossom Ladies Hostel")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 3.6;
        } else if (hostelName.equals("Harmony Women’s PG")) {
            rate1 = 8900;
            rate2 = 6900;
            rate3 = 5900;
            rate4 = 5400;
            rate5 = 4900;
            rating = 3.5;
        } else if (hostelName.equals("Shree Comforts Girls Hostel")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.4;
        } else if (hostelName.equals("Sri Durga Ladies Hostel")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.3;
        }

        // ================== Begumpet Co-living ==================
        else if (hostelName.equals("CoHo Co-living")) {
            rate1 = 9000;
            rate2 = 8000;
            rate3 = 7000;
            rate4 = 6500;
            rate5 = 6000;
            rating = 4.1;
        } else if (hostelName.equals("Urban Stays Co-living")) {
            rate1 = 8800;
            rate2 = 7800;
            rate3 = 6800;
            rate4 = 6300;
            rate5 = 5800;
            rating = 4.0;
        } else if (hostelName.equals("Zolo Co-living")) {
            rate1 = 8600;
            rate2 = 7600;
            rate3 = 6600;
            rate4 = 6100;
            rate5 = 5600;
            rating = 3.9;
        } else if (hostelName.equals("The Hive Co-living")) {
            rate1 = 8500;
            rate2 = 7500;
            rate3 = 6500;
            rate4 = 6000;
            rate5 = 5500;
            rating = 3.8;
        } else if (hostelName.equals("StayAbode Shared Living")) {
            rate1 = 8400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 3.7;
        }

        // ================== Dilsukhnagar Boys ==================
        else if (hostelName.equals("Shree Comforts Gents PG")) {
            rate1 = 9400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 4.0;
        } else if (hostelName.equals("Sri Sai Men’s Hostel")) {
            rate1 = 9300;
            rate2 = 7300;
            rate3 = 6300;
            rate4 = 5800;
            rate5 = 5300;
            rating = 3.9;
        } else if (hostelName.equals("Sunrise Deluxe Boys Hostel")) {
            rate1 = 9200;
            rate2 = 7200;
            rate3 = 6200;
            rate4 = 5700;
            rate5 = 5200;
            rating = 3.8;
        } else if (hostelName.equals("Blue Haven Gents Hostel")) {
            rate1 = 9100;
            rate2 = 7100;
            rate3 = 6100;
            rate4 = 5600;
            rate5 = 5100;
            rating = 3.7;
        } else if (hostelName.equals("Royal Comforts Men’s PG")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 3.6;
        } else if (hostelName.equals("Bhavani Deluxe Gents PG")) {
            rate1 = 8900;
            rate2 = 6900;
            rate3 = 5900;
            rate4 = 5400;
            rate5 = 4900;
            rating = 3.5;
        } else if (hostelName.equals("Om Shanti Men’s Hostel")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.4;
        } else if (hostelName.equals("Elite Comforts Mens PG")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.3;
        } else if (hostelName.equals("Green Valley Boys PG")) {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.2;
        } else if (hostelName.equals("Galaxy Deluxe Gents Hostel")) {
            rate1 = 8500;
            rate2 = 6500;
            rate3 = 5500;
            rate4 = 5000;
            rate5 = 4500;
            rating = 3.1;
        }

        // ================== Dilsukhnagar Girls ==================
        else if (hostelName.equals("Pink Blossom Girls Hostel")) {
            rate1 = 9400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 4.0;
        } else if (hostelName.equals("Queen’s Stay Women’s Hostel")) {
            rate1 = 9300;
            rate2 = 7300;
            rate3 = 6300;
            rate4 = 5800;
            rate5 = 5300;
            rating = 3.9;
        } else if (hostelName.equals("Dreamland Ladies Hostel")) {
            rate1 = 9200;
            rate2 = 7200;
            rate3 = 6200;
            rate4 = 5700;
            rate5 = 5200;
            rating = 3.8;
        } else if (hostelName.equals("Golden Nest Girls PG")) {
            rate1 = 9100;
            rate2 = 7100;
            rate3 = 6100;
            rate4 = 5600;
            rate5 = 5100;
            rating = 3.7;
        } else if (hostelName.equals("Lakshmi Deluxe Women’s PG")) {
            rate1 = 9000;
            rate2 = 7000;
            rate3 = 6000;
            rate4 = 5500;
            rate5 = 5000;
            rating = 3.6;
        } else if (hostelName.equals("Elegant Stay Ladies Hostel")) {
            rate1 = 8900;
            rate2 = 6900;
            rate3 = 5900;
            rate4 = 5400;
            rate5 = 4900;
            rating = 3.5;
        } else if (hostelName.equals("Rose Villa Girls Hostel")) {
            rate1 = 8800;
            rate2 = 6800;
            rate3 = 5800;
            rate4 = 5300;
            rate5 = 4800;
            rating = 3.4;
        } else if (hostelName.equals("Harmony Women’s PG")) {
            rate1 = 8700;
            rate2 = 6700;
            rate3 = 5700;
            rate4 = 5200;
            rate5 = 4700;
            rating = 3.3;
        } else if (hostelName.equals("Sri Durga Girls Hostel")) {
            rate1 = 8600;
            rate2 = 6600;
            rate3 = 5600;
            rate4 = 5100;
            rate5 = 4600;
            rating = 3.2;
        } else if (hostelName.equals("Shree Comforts Ladies PG")) {
            rate1 = 8500;
            rate2 = 6500;
            rate3 = 5500;
            rate4 = 5000;
            rate5 = 4500;
            rating = 3.1;
        }

        // ================== Dilsukhnagar Co-living ==================
        else if (hostelName.equals("Urban Stays Co-living")) {
            rate1 = 8800;
            rate2 = 7800;
            rate3 = 6800;
            rate4 = 6300;
            rate5 = 5800;
            rating = 4.0;
        } else if (hostelName.equals("CoHo Co-living")) {
            rate1 = 8600;
            rate2 = 7600;
            rate3 = 6600;
            rate4 = 6100;
            rate5 = 5600;
            rating = 3.9;
        } else if (hostelName.equals("Zolo Co-living")) {
            rate1 = 8400;
            rate2 = 7400;
            rate3 = 6400;
            rate4 = 5900;
            rate5 = 5400;
            rating = 3.8;
        } else if (hostelName.equals("StayAbode Shared Living")) {
            rate1 = 8300;
            rate2 = 7300;
            rate3 = 6300;
            rate4 = 5800;
            rate5 = 5300;
            rating = 3.7;
        } else if (hostelName.equals("The Hive Co-living")) {
            rate1 = 8200;
            rate2 = 7200;
            rate3 = 6200;
            rate4 = 5700;
            rate5 = 5200;
            rating = 3.6;
        }

        System.out.println(menuPadding + red + bold + "--- PG Accommodation Costs ---");
        System.out.println(reset+ bold  + menuPadding + "1-Sharing: Rs" + rate1 + "/month");
        System.out.println(menuPadding+ bold  + "2-Sharing: Rs" + rate2 + "/month");
        System.out.println(menuPadding+ bold  + "3-Sharing: Rs" + rate3 + "/month");
        System.out.println(menuPadding+ bold  + "4-Sharing: Rs" + rate4 + "/month");
        System.out.println(menuPadding+ bold  + "5-Sharing: Rs" + rate5 + "/month");
        System.out.println(menuPadding+ bold  + red + bold + "--- PG Rating ---"+reset);
        System.out.println(menuPadding+ bold  + "Rating: " + rating);

        LocationDetails locDetails = getLocationDetails(location);
        System.out.println(menuPadding + red + bold + "--- Nearby Amenities ---");
        System.out.println(reset + menuPadding + "Metro Station: " + locDetails.getMetro());
        System.out.println(menuPadding + bold + "Bus Stop: " + locDetails.getBusStop());
        System.out.println(menuPadding + bold + "Nearby Institutions:");
        System.out.println(menuPadding + bold + "1. " + locDetails.getInstitution1());
        System.out.println(menuPadding + bold + "2. " + locDetails.getInstitution2());
        System.out.println(menuPadding+ bold  + "3. " + locDetails.getInstitution3());
        String inst4 = locDetails.getInstitution4();
        if (!inst4.isEmpty()) 
        {
            printSlow(menuPadding + "4. " + inst4, 1);
        }

        System.out.println(menuPadding + red + bold + "--- Rules & Regulations ---");
        System.out.println(reset + menuPadding+ bold  + "1. No smoking or alcohol inside the PG.");
        System.out.println(menuPadding + bold + "2. Visitors allowed");
        System.out.println(menuPadding + bold + "3. Maintain cleanliness in rooms and common areas.");
        System.out.println(menuPadding+ bold  + "4. Keep noise levels low, especially after 10 PM.");
        System.out.println(menuPadding + bold + "5. Pay monthly rent and other charges on time.");
        System.out.println(menuPadding + bold + "6. Any damage to PG property must be reported immediately.");
        System.out.println(menuPadding+ bold  + "7. Guests are responsible for their personal belongings.");
        System.out.println(menuPadding + bold + red + bold + "--- Features ---");
        System.out.println(reset + menuPadding + "1. Wi-Fi");
        System.out.println(menuPadding + bold + "2. Homely Meals (Veg/Non-Veg options)");
        System.out.println(menuPadding+ bold  + "3. 24/7 Security");
        System.out.println(menuPadding+ bold  + "4. Day by Day Cleaning");
        System.out.println(menuPadding+ bold  + "5. CCTV Surveillance");
        System.out.println(menuPadding + bold + "6. Hot Water");
        System.out.println(menuPadding + bold + "7. Washing Machines");

        // Category Selection
        String category1 = "";
        while (true) {
            System.out.print(bold+"Enter category (1 - Student, 2 - Other): ");
            int categoryChoice = sc.nextInt();
            if (categoryChoice == 1) {
                category1 = "student";
                break;
            } else if (categoryChoice == 2) {
                category1 = "other";
                break;
            } else {
                System.out.println(red+bold+"Invalid choice! Please enter 1 or 2."+reset);
            }
        }

        // Room Sharing Selection
        int sharing;
        while (true) {
            System.out.print(bold + "Enter the room sharing type (1-5): " + reset);
            sharing = sc.nextInt();
            if (sharing >= 1 && sharing <= 5) break;
            System.out.println(red + bold + "Invalid room type! Please enter between 1-5." + reset);
        }

        double baseFee = (sharing == 1) ? rate1 :
                         (sharing == 2) ? rate2 :
                         (sharing == 3) ? rate3 :
                         (sharing == 4) ? rate4 : rate5;

        // Proceed Option
        while (true) {
            System.out.println(bold+blue +"Do you want to:"+reset);
            System.out.println(bold +"1. Proceed to check total price");
            System.out.println(bold +"2. Cancel and Exit");
            System.out.print(bold +"Enter choice: "+reset);
            int option = sc.nextInt();
            if (option == 1) {
                billing(baseFee, category1, hostelName, location);
                break;
            } else if (option == 2) {
                System.out.println(red+bold+"Booking cancelled. Exiting..."+reset);
                return;
            } else {
                System.out.println(red+bold+"Invalid option. Try again."+reset);
            }
        }
    }

    public static void billing(double baseFee, String category1, String hostelName, String location) {
        double discountPercent = category1.equalsIgnoreCase("student") ? 10.0 : 0.0;
        double discountAmount = baseFee * (discountPercent / 100.0);
        double totalFee = baseFee - discountAmount;

        // Bill
        System.out.println(bold+"\n===== HOSTEL BILL =====");
        System.out.printf("%-15s : Rs%.2f%n", "Base Fee", baseFee);
        System.out.printf("%-15s : %.1f%%%n", "Discount", discountPercent);
        System.out.printf("%-15s : Rs%.2f%n", "Total Fee", totalFee);
        System.out.println("=======================\n"+reset);

        System.out.print(bold+"Would you like to book this hostel? (y/n): "+reset);
        char confirm = sc.next().charAt(0);
        if (confirm != 'y' && confirm != 'Y') {
            System.out.println(red+bold+"Booking cancelled. Returning..."+reset);
            return;
        }
        sc.nextLine();
        String name;
        while (true) 
        {
            System.out.print(bold+"Enter your name: ");
            name = sc.nextLine().trim();
            if (!name.isEmpty()) break;
            System.out.println(red+bold+"Name cannot be empty."+reset);
        }
        String phone;
        while (true) {
            System.out.print(bold+"Enter phone number:");
            phone = sc.next();
            if (phone.matches("[6-9][0-9]{9}")) break;
            System.out.println(bold+red+"Invalid phone number. Try again."+reset);
        }
        int months;
        while (true) 
        {
            System.out.print(bold+"How many months to book? (1-12): ");
            months = sc.nextInt();
            if (months >= 1 && months <= 12) break;
            System.out.println(red+bold+"Invalid months. Try again."+reset);
        }

        double monthlyFee = totalFee;
        double finalAmount = monthlyFee * months;

        System.out.println(bold+"Monthly payable (after discount): Rs" + Math.round(monthlyFee));
        System.out.println("Total payable: Rs" + Math.round(finalAmount));

        // Advance Payment (min 20%)
        double minAdvance = finalAmount * 0.20;
        double advance = 0;
        while (true) {
            System.out.print("Enter advance amount (Minimum 20% = Rs" + Math.round(minAdvance) + "): ");
            advance = sc.nextDouble();
            if (advance < minAdvance) {
                System.out.println("Advance cannot be less than Rs" + Math.round(minAdvance));
            } else if (advance > finalAmount) {
                System.out.println(red+bold+"Advance cannot exceed total. Try again."+reset);
            }
            else 
            {
                break;
            }
        }
        double remaining = finalAmount - advance;

        // Payment Method
        while (true) {
            System.out.println(bold+"\nChoose Payment Method:");
            System.out.println("1. PhonePe");
            System.out.println("2. Card");
            System.out.print("Enter choice: ");
            int payChoice = sc.nextInt();

            if (payChoice == 1) {
                if (phonePePayment(advance)) break;
            } else if (payChoice == 2) {
                if (cardPayment(advance)) break;
            } else {
                System.out.println(red+bold+"Invalid option. Try again."+reset);
            }
        }

        // OTP Verification
        String otp = String.valueOf(1000 + (int) (Math.random() * 8999));
        System.out.println(blue+"Generated OTP : " + otp);
        while (true) {
            System.out.print(white+bold+"Enter the OTP sent to your phone: ");
            String enteredOtp = sc.next();
            if (enteredOtp.equals(otp)) {
                System.out.println(green+bold+"OTP Verified! Booking confirmed."+reset);
                break;
            } else {
                System.out.println(red+bold+"Invalid OTP. Try again."+reset);
            }
        }

        // Receipt
        String bookingID = "BKG" + (1000 + (int) (Math.random() * 9000));
        System.out.println(cyan+bold+"\n===== BOOKING RECEIPT ====="+reset);
        System.out.println(bold+"Booking ID : " + bookingID);
        System.out.println("Guest      : " + name);
        System.out.println("Phone      : " + phone);
        System.out.println("City/Area  : " + location);
        System.out.println("Hostel     : " + hostelName);
        System.out.println("Months     : " + months);
        System.out.printf("Monthly Fee: Rs%.2f%n", monthlyFee);
        System.out.printf("Subtotal   : Rs%.2f%n", monthlyFee * months);
        System.out.println("Discount   : " + discountPercent + "%");
        System.out.printf("Total Fee  : Rs%.2f%n", finalAmount);
        System.out.printf("Advance Paid : Rs%.2f%n", advance);
        System.out.printf("Remaining    : Rs%.2f%n", remaining);
        System.out.println(cyan+bold+"============================"+reset);
        System.out.println(green+bold+"Thank you! Your booking is confirmed."+reset);
        System.out.println(menuPadding + red + bold + "Note: Carry ID proof at check-in. Refunds subject to hostel policy."
                + reset);
        System.out.print(bold + "Would you like to provide feedback for your booking experience? (y/n): " + reset);
        sc.nextLine(); // Clear buffer
        
        char feedbackChoice = sc.nextLine().charAt(0);
        if (feedbackChoice == 'y' || feedbackChoice == 'Y') 
        {
            obj.mainCollectFeedback();
        }
        System.out.println(menuPadding + bold + green + "\nThank you! Your booking is confirmed." + reset);
    }

    // PhonePe Payment Validation
    public static boolean phonePePayment(double amount) {
        while (true) {
            System.out.print(bold+"Enter PhonePe mobile number: ");
            String mobile = sc.next();
            if (!mobile.matches("[6-9][0-9]{9}")) {
                System.out.println(red+bold+"Invalid number. Try again."+reset);
                continue;
            }
            System.out.print(bold+"Enter PhonePe PIN (4-6 digits): ");
            String pin = sc.next();
            if (!pin.matches("[0-9]{4,6}")) {
                System.out.println(red+bold+"Invalid PIN. Try again."+reset);
                continue;
            }
            System.out.println(bold+"phonePe Payment of Rs" + Math.round(amount) + " Successful.");
            return true;
        }
    }

    // Card Payment Validation
    public static boolean cardPayment(double amount) {
        while (true) {
            System.out.print(bold+"Enter Card Number (12 digits): ");
            String card = sc.next();
            if (!card.matches("[0-9]{12}")) { 
                System.out.println(red+bold+"Invalid card number. Try again."+reset);
                continue;
            }
            System.out.print(bold+"Enter Card PIN (4 digits): ");
            String pin = sc.next();
            if (!pin.matches("[0-9]{4}")) {   
                System.out.println(red+bold+"Invalid PIN. Try again."+reset);
                continue;
            }
            System.out.println(bold+" Card Payment of Rs" + Math.round(amount) + " Successful.");
            return true;
        }
    }

}

class Hotel extends Colors
{
    String name;
    int acRooms;
    int nonAcRooms;
    double acPrice;
    double nonAcPrice;
    double rating;

    Hotel(String name, int acRooms, int nonAcRooms, double acPrice, double nonAcPrice, double rating) 
    {
        this.name = name;
        this.acRooms = acRooms;
        this.nonAcRooms = nonAcRooms;
        this.acPrice = acPrice;
        this.nonAcPrice = nonAcPrice;
        this.rating = rating;
    }

    void displayDetails()
    {
        System.out.println(bold+Colors.green + "Hotel Name: " + name + Colors.reset);
        System.out.println(bold+Colors.yellow + "Rating: " + rating + Colors.reset);
        System.out.println(bold+"1. AC Suite - Rs" + acPrice + "/night | Available: " + acRooms);
        System.out.println("2. Non-AC Suite - Rs" + nonAcPrice + "/night | Available: " + nonAcRooms);
    }

    boolean bookRoom(int choice)
    {
        if (choice == 1)
        {
            if (acRooms > 0) {
                acRooms--;
                return true;
            } 
            else
            {
                System.out.println(bold+Colors.red + "No AC rooms available!" + Colors.reset);
                return false;
            }
        }
        else if (choice == 2) 
        {
            if (nonAcRooms > 0)
            {
                nonAcRooms--;
                return true;
            } 
            else
            {
                System.out.println(bold+Colors.red + "No Non-AC rooms available!" + Colors.reset);
                return false;
            }
        } 
        else
        {
            System.out.println(bold+Colors.red + "Invalid room type choice." + Colors.reset);
            return false;
        }
    }
}

class HotelBooking extends Colors
{
    static FacilitySupportSystem obj = new FacilitySupportSystem();
    static Scanner sc = new Scanner(System.in);

    static void hotelBooking()
    {
        if (Hostel.welcomeScreen()) 
        {
            String loc = Hostel.location();
            while (true)
            {
                if (loc.equals("back")) 
                {
                    loc = Hostel.location();
                    continue;
                }
                displayHotelList(loc);
                System.out.print(reset+bold + "Enter your hotel choice: ");
                int hotelChoice = sc.nextInt();
                if (hotelChoice == 0)
                {
                    loc = "back";
                    continue;
                }
                Hotel selectedHotel = getHotel(loc, hotelChoice);
                if (selectedHotel != null) 
                {
                    System.out.println(menuPadding +bold+ green + "You selected: " + selectedHotel.name + reset);
                    LocationDetails locDetails = Hostel.getLocationDetails(loc);
                    System.out.println(menuPadding + red + bold + "--- Nearby Amenities ---" + reset);
                    System.out.println(menuPadding +bold+ "Metro Station: " + locDetails.getMetro());
                    System.out.println(menuPadding+bold + "Bus Stop: " + locDetails.getBusStop());
                    rulesAndInstructions();
                    selectedHotel.displayDetails();
                    System.out.print(bold+"Choose room type (1 for AC, 2 for Non-AC): ");
                    int roomChoice = sc.nextInt();
                    double baseFee = (roomChoice == 1) ? selectedHotel.acPrice
                            : (roomChoice == 2) ? selectedHotel.nonAcPrice : 0;
                    String roomType = (roomChoice == 1) ? "AC Suite" : (roomChoice == 2) ? "Non-AC Suite" : "";
                    if (baseFee == 0)
                    {
                        System.out.println(bold+red + "Invalid room type choice. Please try again." + reset);
                        continue;
                    }
                    if (selectedHotel.bookRoom(roomChoice)) 
                    {
                        System.out.println(bold+"Enter category (1 - Visitor, 2 - Other): ");
                        int categoryChoice = sc.nextInt();
                        String category = categoryChoice == 1 ? "visitor" : categoryChoice == 2 ? "other" : "";
                        if (category.isEmpty())
                        {
                            System.out.println(red+bold+"Invalid category! Returning to hotel selection..."+reset);
                            continue;
                        }
                        billing(baseFee, category, sc, selectedHotel.name, loc, roomType);
                        break;
                    }
                } 
                else
                {
                    System.out.println(bold+red + "Invalid hotel choice. Please try again." + reset);
                }
            }
        }
    }

    static void displayHotelList(String location) 
    {
        System.out.println();
        System.out.println(menuPadding+bold + yellow + "+-------------------------" + reset + red + " HOTELS " + reset + yellow
                + "-----------------------+" + reset);
        if (location.equals("Kphb")) 
        {
            System.out.println(menuPadding +bold+ yellow + "| 1. Hotel Green Park (4.2)                             |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 2. Hotel Sunshine (3.8)                               |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 3. Hotel Paradise (4.5)                               |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 4. Hotel Comfort Inn (4.0)                            |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 5. Hotel Royal Stay (4.8)                             |" + reset);
        } 
        else if (location.equals("Ameerpet"))
        {
            System.out.println(menuPadding +bold+ yellow + "| 1. Hotel Blue Moon (4.1)                              |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 2. Hotel Star Light (4.3)                             |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 3. Hotel City Palace (3.9)                            |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 4. Hotel Ruby Grand (4.0)                             |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 5. Hotel Midtown (4.6)                                |" + reset);
        } 
        else if (location.equals("SR Nagar")) 
        {
            System.out.println(menuPadding +bold+ yellow + "| 1. Hotel SR Residency (3.9)                           |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 2. Hotel Lotus Park (4.2)                             |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 3. Hotel Maple Leaf (4.1)                             |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 4. Hotel Elite Inn (4.4)                              |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 5. Hotel Grand Tulip (4.7)                            |" + reset);
        } 
        else if (location.equals("Kukatpally"))
        {
            System.out.println(menuPadding +bold+ yellow + "| 1. Hotel Sapphire (4.0)                               |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 2. Hotel Pearl Inn (3.7)                              |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 3. Hotel Emerald Bay (4.3)                            |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 4. Hotel Diamond Stay (4.1)                           |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 5. Hotel Platinum Inn (4.5)                           |" + reset);
        } 
        else if (location.equals("Madhapur")) 
        {
            System.out.println(menuPadding +bold+ yellow + "| 1. Hotel Silicon Stay (4.2)                           |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 2. Hotel IT Suites (4.0)                              |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 3. Hotel Cloud Nine (4.5)                             |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 4. Hotel Skyline (4.3)                                |" + reset);
            System.out.println(menuPadding+bold + yellow + "| 5. Hotel Galaxy (4.8)                                 |" + reset);
        } 
        else if (location.equals("Gachibowli"))
        {
            System.out.println(menuPadding +bold+ yellow + "| 1. Hotel Sports City (4.0)                            |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 2. Hotel Lake View (4.1)                              |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 3. Hotel Hilltop (3.8)                                |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 4. Hotel Tech Park (4.4)                              |" + reset);
            System.out.println(menuPadding+bold + yellow + "| 5. Hotel Silver Leaf (4.6)                            |" + reset);
        } 
        else if (location.equals("Kondapur")) 
        {
            System.out.println(menuPadding+bold + yellow + "| 1. Hotel Green Valley (4.2)                           |" + reset);
            System.out.println(menuPadding+bold + yellow + "| 2. Hotel Crystal Inn (4.0)                            |" + reset);
            System.out.println(menuPadding+bold + yellow + "| 3. Hotel Blue Wave (3.9)                              |" + reset);
            System.out.println(menuPadding+bold + yellow + "| 4. Hotel Rosewood (4.5)                               |" + reset);
            System.out.println(menuPadding+bold + yellow + "| 5. Hotel Palm Grove (4.3)                             |" + reset);
        }
        else if (location.equals("Banjara Hills"))
        {
            System.out.println(menuPadding +bold+ yellow + "| 1. Hotel Royal Orchid (4.6)                           |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 2. Hotel King’s Court (4.8)                           |" + reset);
            System.out.println(menuPadding+bold + yellow + "| 3. Hotel Palace Inn (4.7)                             |" + reset);
            System.out.println(menuPadding+bold + yellow + "| 4. Hotel Crown Plaza (4.5)                            |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 5. Hotel Heritage (4.9)                               |" + reset);
        } 
        else if (location.equals("Begumpet")) 
        {
            System.out.println(menuPadding +bold+ yellow + "| 1. Hotel Airport View (4.1)                           |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 2. Hotel Central Park (4.3)                           |" + reset);
            System.out.println(menuPadding+bold + yellow + "| 3. Hotel City Heart (4.0)                             |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 4. Hotel Metro Plaza (4.5)                            |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 5. Hotel Grand Royal (4.6)                            |" + reset);
        } 
        else 
        { // Dilsukhnagar
            System.out.println(menuPadding +bold+ yellow + "| 1. Hotel Urban Stay (4.0)                             |" + reset);
            System.out.println(menuPadding+bold + yellow + "| 2. Hotel Comfort Palace (4.1)                         |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 3. Hotel Sunrise (4.2)                                |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 4. Hotel City View (4.3)                              |" + reset);
            System.out.println(menuPadding +bold+ yellow + "| 5. Hotel Grand Vista (4.4)                            |" + reset);
        }
        System.out.println(menuPadding +bold+ yellow + "| 0. Go Back                                             |" + reset);
        System.out.println(menuPadding +bold+ yellow + "+--------------------------------------------------------+" + reset);
    }

    static Hotel getHotel(String location, int choice)
    {
        if (location.equals("Kphb")) 
        {
            if (choice == 1)
                return new Hotel("Hotel Green Park", 5, 5, 2000, 1200, 4.2);
            if (choice == 2)
                return new Hotel("Hotel Sunshine", 4, 6, 1800, 1000, 3.8);
            if (choice == 3)
                return new Hotel("Hotel Paradise", 3, 5, 2200, 1300, 4.5);
            if (choice == 4)
                return new Hotel("Hotel Comfort Inn", 6, 8, 1900, 1100, 4.0);
            if (choice == 5)
                return new Hotel("Hotel Royal Stay", 5, 4, 2500, 1500, 4.8);
        } 
        else if (location.equals("Ameerpet"))
        {
            if (choice == 1)
                return new Hotel("Hotel Blue Moon", 4, 5, 2100, 1250, 4.1);
            if (choice == 2)
                return new Hotel("Hotel Star Light", 5, 6, 2000, 1200, 4.3);
            if (choice == 3)
                return new Hotel("Hotel City Palace", 3, 7, 2300, 1350, 3.9);
            if (choice == 4)
                return new Hotel("Hotel Ruby Grand", 4, 5, 1950, 1100, 4.0);
            if (choice == 5)
                return new Hotel("Hotel Midtown", 5, 5, 2400, 1400, 4.6);
        } 
        else if (location.equals("SR Nagar")) 
        {
            if (choice == 1)
                return new Hotel("Hotel SR Residency", 3, 6, 2000, 1200, 3.9);
            if (choice == 2)
                return new Hotel("Hotel Lotus Park", 5, 5, 2100, 1250, 4.2);
            if (choice == 3)
                return new Hotel("Hotel Maple Leaf", 4, 5, 2200, 1300, 4.1);
            if (choice == 4)
                return new Hotel("Hotel Elite Inn", 6, 4, 2300, 1400, 4.4);
            if (choice == 5)
                return new Hotel("Hotel Grand Tulip", 5, 5, 2500, 1500, 4.7);
        } 
        else if (location.equals("Kukatpally")) 
        {
            if (choice == 1)
                return new Hotel("Hotel Sapphire", 4, 6, 2000, 1200, 4.0);
            if (choice == 2)
                return new Hotel("Hotel Pearl Inn", 3, 7, 2100, 1250, 3.7);
            if (choice == 3)
                return new Hotel("Hotel Emerald Bay", 5, 5, 2200, 1300, 4.3);
            if (choice == 4)
                return new Hotel("Hotel Diamond Stay", 4, 5, 2300, 1350, 4.1);
            if (choice == 5)
                return new Hotel("Hotel Platinum Inn", 6, 6, 2400, 1400, 4.5);
        }
        else if (location.equals("Madhapur"))
        {
            if (choice == 1)
                return new Hotel("Hotel Silicon Stay", 4, 6, 2100, 1300, 4.2);
            if (choice == 2)
                return new Hotel("Hotel IT Suites", 5, 5, 2000, 1250, 4.0);
            if (choice == 3)
                return new Hotel("Hotel Cloud Nine", 6, 4, 2300, 1400, 4.5);
            if (choice == 4)
                return new Hotel("Hotel Skyline", 4, 5, 2400, 1500, 4.3);
            if (choice == 5)
                return new Hotel("Hotel Galaxy", 5, 5, 2500, 1600, 4.8);
        } 
        else if (location.equals("Gachibowli")) 
        {
            if (choice == 1)
                return new Hotel("Hotel Sports City", 5, 5, 2000, 1200, 4.0);
            if (choice == 2)
                return new Hotel("Hotel Lake View", 4, 6, 2100, 1300, 4.1);
            if (choice == 3)
                return new Hotel("Hotel Hilltop", 3, 7, 2200, 1350, 3.8);
            if (choice == 4)
                return new Hotel("Hotel Tech Park", 6, 5, 2300, 1400, 4.4);
            if (choice == 5)
                return new Hotel("Hotel Silver Leaf", 5, 5, 2400, 1500, 4.6);
        } 
        else if (location.equals("Kondapur"))
        {
            if (choice == 1)
                return new Hotel("Hotel Green Valley", 5, 5, 2000, 1200, 4.2);
            if (choice == 2)
                return new Hotel("Hotel Crystal Inn", 4, 5, 2100, 1250, 4.0);
            if (choice == 3)
                return new Hotel("Hotel Blue Wave", 3, 7, 2200, 1300, 3.9);
            if (choice == 4)
                return new Hotel("Hotel Rosewood", 6, 4, 2300, 1350, 4.5);
            if (choice == 5)
                return new Hotel("Hotel Palm Grove", 5, 6, 2400, 1400, 4.3);
        } 
        else if (location.equals("Banjara Hills"))
        {
            if (choice == 1)
                return new Hotel("Hotel Royal Orchid", 4, 6, 2500, 1500, 4.6);
            if (choice == 2)
                return new Hotel("Hotel King’s Court", 5, 5, 2600, 1600, 4.8);
            if (choice == 3)
                return new Hotel("Hotel Palace Inn", 6, 4, 2700, 1700, 4.7);
            if (choice == 4)
                return new Hotel("Hotel Crown Plaza", 4, 6, 2800, 1800, 4.5);
            if (choice == 5)
                return new Hotel("Hotel Heritage", 5, 5, 3000, 2000, 4.9);
        } 
        else if (location.equals("Begumpet"))
        {
            if (choice == 1)
                return new Hotel("Hotel Airport View", 4, 6, 2100, 1300, 4.1);
            if (choice == 2)
                return new Hotel("Hotel Central Park", 5, 5, 2200, 1350, 4.3);
            if (choice == 3)
                return new Hotel("Hotel City Heart", 3, 7, 2300, 1400, 4.0);
            if (choice == 4)
                return new Hotel("Hotel Metro Plaza", 6, 4, 2400, 1500, 4.5);
            if (choice == 5)
                return new Hotel("Hotel Grand Royal", 5, 5, 2500, 1600, 4.6);
        } 
        else
        { // Dilsukhnagar
            if (choice == 1)
                return new Hotel("Hotel Urban Stay", 4, 5, 2000, 1200, 4.0);
            if (choice == 2)
                return new Hotel("Hotel Comfort Palace", 5, 6, 2100, 1250, 4.1);
            if (choice == 3)
                return new Hotel("Hotel Sunrise", 3, 7, 2200, 1300, 4.2);
            if (choice == 4)
                return new Hotel("Hotel City View", 6, 4, 2300, 1350, 4.3);
            if (choice == 5)
                return new Hotel("Hotel Grand Vista", 5, 5, 2400, 1400, 4.4);
        }
        return null;
    }

    static void rulesAndInstructions()
    {
        System.out.println(menuPadding1+bold + blue + "================================" + red + " RULES & INSTRUCTIONS "
                + reset + blue +bold+ "=================================" + reset);
        System.out.println(menuPadding+bold + "- All bookings are for a single night.");
        System.out.println(menuPadding + "- Prices are in Rs (Indian Rupees) and are per night.");
        System.out.println(menuPadding + "- Please select from the available options using the corresponding numbers.");
        System.out.println(menuPadding + "- Once a room is booked, it cannot be canceled or changed.");
        System.out.println(menuPadding + "- Your booking is confirmed instantly upon successful selection.");
        System.out.println(menuPadding1 + blue
                + "======================================================================================\n" + reset);
        System.out.print(yellow +bold+ "Press Enter to continue..." + reset);
        sc.nextLine(); // Wait for user to press Enter
    }

static void billing(double baseFee, String category, Scanner sc, String hotelName, String location, String roomType) {
    // Discount
    double discountPercent = category.equalsIgnoreCase("Visitor") ? 10 : 0;
    double discountAmount = baseFee * (discountPercent / 100);
    double totalFee = baseFee - discountAmount;

    System.out.println(bold+"===== HOTEL BILL =====");
    System.out.printf("Original Fee       : Rs%.2f\n", baseFee);
    System.out.printf("Discount Applied   : %.0f%%\n", discountPercent);
    System.out.printf("Fee After Discount : Rs%.2f\n", totalFee);
    System.out.println("======================");

    // Confirm booking
    System.out.print(bold+"Would you like to book this hotel? (y/n): ");
    char confirm = sc.next().charAt(0);
    if (confirm != 'y' && confirm != 'Y') {
        System.out.println(red+bold+"Booking cancelled. Returning to hotel selection..."+reset);
        hotelBooking(); // your method
        return;
    }

    sc.nextLine(); // Clear buffer
    System.out.print(bold+"Enter your name: ");
    String name = sc.nextLine();

    // Nights validation
    int nights = 0;
    while (true) {
        System.out.print(bold+"How many nights to book? (1-30): ");
        nights = sc.nextInt();
        if (nights >= 1 && nights <= 30) break;
        System.out.println(bold+red+"Invalid number of nights. Enter again."+reset);
    }

    double nightlyFee = totalFee; // after discount
    double finalAmount = nightlyFee * nights;

    System.out.println(bold+"Nightly payable (after discount): Rs" + Math.round(nightlyFee));
    System.out.println("Total payable (incl. GST): Rs" + Math.round(finalAmount));

    // Advance Payment - minimum 30%
    double minAdvance = finalAmount * 0.30;
    double advance = 0;
    while (true) {
        System.out.print("Enter advance amount (min 30% of total = Rs" + Math.round(minAdvance) + "): Rs");
        advance = sc.nextDouble();
        if (advance < minAdvance) System.out.println(red+bold+"Cannot pay less than Rs" + Math.round(minAdvance)+reset);
        else if (advance > finalAmount) {
            System.out.println(bold+"Advance cannot exceed total. Setting to total amount.");
            advance = finalAmount;
            break;
        } else break;
    }
    double remaining = finalAmount - advance;

    // Payment Method selection
    sc.nextLine(); // Clear buffer
    int paymentOption = 0;
    while (true) {
        System.out.println(bold+"Choose payment method: \n 1 = Card \n 2 = PhonePe ");
        if (sc.hasNextInt()) {
            paymentOption = sc.nextInt();
            if (paymentOption == 1 || paymentOption == 2) break;
        } else sc.next(); // clear invalid input
        System.out.println(red+bold+"Invalid option. Please enter 1 or 2."+reset);
    }

    String phone = "";
    if (paymentOption == 1) {
        // Card payment validation
        String cardNumber, cardPin;
        while (true) {
            System.out.print("Enter 12-digit card number: ");
            cardNumber = sc.next();
            if (!cardNumber.matches("[0-9]{12}")) {
                System.out.println(red+bold+"Invalid card number. Try again."+reset);
                continue;
            }
            System.out.print(bold+"Enter 4-digit card PIN: ");
            cardPin = sc.next();
            if (!cardPin.matches("[0-9]{4}")) {
                System.out.println(red+bold+"Invalid PIN. Try again."+reset);
                continue;
            }
            break;
        }
        System.out.println(bold+"Card payment successful!");
        System.out.print("Enter phone number (for receipt): ");
        phone = sc.next();
    } else {
        // PhonePe validation
        String mobile, pin;
        while (true) {
            System.out.print("Enter mobile number (starts 6-9, 10 digits): ");
            mobile = sc.next();
            if (!mobile.matches("[6-9][0-9]{9}")) {
                System.out.println(red+bold+"Invalid mobile number. Try again."+reset);
                continue;
            }
            System.out.print(bold+"Enter 4-digit PIN: ");
            pin = sc.next();
            if (!pin.matches("[0-9]{4}")) {
                System.out.println(red+bold+"Invalid PIN. Try again."+reset);
                continue;
            }
            break;
        }
        System.out.println(bold+"PhonePe payment successful!");
        phone = mobile;
    }

    // OTP Verification
    String otp = String.valueOf(1000 + (int) (Math.random() * 9000));
    System.out.println("Generated OTP: " + otp);
    String enteredOtp;
    while (true) {
        System.out.print("Enter OTP sent to your phone: ");
        enteredOtp = sc.next();
        if (enteredOtp.equals(otp)) {
            System.out.println("OTP Verified! Proceeding to booking receipt...\n");
            break;
        } else System.out.println(red+bold+"Invalid OTP. Try again."+reset);
    }

    // Booking Receipt
    String bookingID = "BKG" + (1000 + (int) (Math.random() * 9000));
    System.out.println(bold+"------------------------------------------------------------");
    System.out.println("                    BOOKING RECEIPT");
    System.out.println("Booking ID : " + bookingID);
    System.out.println("Guest      : " + name);
    System.out.println("Phone      : " + phone);
    System.out.println("City/Area  : " + location);
    System.out.println("Hotel      : " + hotelName);
    System.out.println("Room Type  : " + roomType);
    System.out.println("Nights     : " + nights);
    System.out.printf("Nightly Fee (after discount) : Rs%.2f\n", nightlyFee);
    System.out.printf("Subtotal (Nightly × %d)     : Rs%.2f\n", nights, nightlyFee * nights);
    System.out.println("Discount Applied             : " + discountPercent + "%");
    System.out.printf("Total (incl. GST & discounts): Rs%.2f\n", finalAmount);
    System.out.printf("Advance Paid                 : Rs%.2f\n", advance);
    System.out.printf("Remaining                    : Rs%.2f\n", remaining);
    System.out.println("------------------------------------------------------------");
    System.out.println(red+bold+"Note: Carry ID proof at check-in. Refunds subject to hotel policy.");
    System.out.println(green+bold+"Thank you! Your booking is confirmed.");
    System.out.println(menuPadding + red + bold + "Note: Carry ID proof at check-in. Refunds subject to hostel policy."
                + reset);
        System.out.print(bold + "Would you like to provide feedback for your booking experience? (y/n): " + reset);
        sc.nextLine(); // Clear buffer
        
        char feedbackChoice = sc.nextLine().charAt(0);
        if (feedbackChoice == 'y' || feedbackChoice == 'Y') 
        {
            obj.mainCollectFeedback();
        }
        System.out.println(menuPadding + bold + green + "\nThank you! Your booking is confirmed." + reset);
}


}

class FacilitySupportSystem extends Colors
{
    static Scanner scanner = new Scanner(System.in);
    static String complaint;
    static String maintenanceRequest;

    static void mainCollectFeedback()
    {
        System.out.println(blue + bold + "Welcome to Facility Support System" + reset);

        while (true) 
        {
            System.out.println(yellow + "\nChoose an option:" + reset);
            System.out.println(bold + "1. Submit Feedback" + reset);
            System.out.println(bold + "2. Exit" + reset);

            int choice = getIntInput(green + "Enter your choice: " + reset);
            if (choice == 1) 
            {
                collectFeedback();
            }
            else if (choice == 2)
            {
                System.out.println(red + bold + " Visit Again" + reset);
                break;
            } 
            else
            {
                System.out.println(red + bold + "Invalid choice." + reset);
            }
        }
    }

    static void collectFeedback() 
    {
        System.out.println(yellow + bold + "\n--- Feedback Form ---" + reset);

        System.out.print("Leave a comment: ");
        String comment = scanner.nextLine();

        System.out.println(green + bold + "\nThank you for your feedback!" + reset);
        System.out.println("Comment: " + comment);
    }


    static int getIntInput(String prompt) 
    {
        int value = -1;
        while (true)
        {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.matches("\\d+")) 
            { 
                value = Integer.parseInt(input);
                break;
            } 
            else
            {
                System.out.println(red + bold + "Please enter a valid number." + reset);
            }
        }
        return value;
    }
}

public class Main extends Colors
{
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);

        System.out.println(menuPadding2 + green + bold+ "===================================================================================================="+ reset);
        String[] asciiLines = {
                "##      ## ######## ##        ######   #######  ##     ## ########    ########  #######     ##     ## ##    ## ########  ######## ########     ###    ########     ###    ########  ",
                "##  ##  ## ##       ##       ##    ## ##     ## ###   ### ##             ##    ##     ##    ##     ##  ##  ##  ##     ## ##       ##     ##   ## ##   ##     ##   ## ##   ##     ## ",
                "##  ##  ## ##       ##       ##       ##     ## #### #### ##             ##    ##     ##    ##     ##   ####   ##     ## ##       ##     ##  ##   ##  ##     ##  ##   ##  ##     ## ",
                "##  ##  ## ######   ##       ##       ##     ## ## ### ## ######         ##    ##     ##    #########    ##    ##     ## ######   ########  ##     ## ########  ##     ## ##     ## ",
                "##  ##  ## ##       ##       ##       ##     ## ##     ## ##             ##    ##     ##    ##     ##    ##    ##     ## ##       ##   ##   ######### ##     ## ######### ##     ## ",
                "##  ##  ## ##       ##       ##    ## ##     ## ##     ## ##             ##    ##     ##    ##     ##    ##    ##     ## ##       ##    ##  ##     ## ##     ## ##     ## ##     ## ",
                " ###  ###  ######## ########  ######   #######  ##     ## ########       ##     #######     ##     ##    ##    ########  ######## ##     ## ##     ## ########  ##     ## ########  "
        };

        // Assign different colors per line
        String[] colors = { red, green, yellow, blue, purple, cyan, white };

        for (int i = 0; i < asciiLines.length; i++) 
        {
            printSlow(bold + colors[i % colors.length] + asciiLines[i] + reset, 2); // delay 2ms
        }

        System.out.println(menuPadding2 + green + bold
                + "===================================================================================================="
                + reset);

        System.out.println(green + bold + "We help you find the perfect place for your stay!" + reset);
        System.out.println();
        User u = new User();
        boolean loggedIn = false;
        while (true) 
        {
            System.out.println(yellow + bold + "1 - Signup\n2 - Login\n3 - Exit" + reset);
            int input = sc.nextInt();
            if (input == 1)
            {
                u.SignUp();
            } 
            else if (input == 2)
            {
                u.login();
                if (Details.isAccountCreated() && u.isValidEmail(u.x.getEmail()) && u.isValidPassword(u.x.getPass())) 
                {
                    loggedIn = true;
                    break;
                }
            } 
            else if (input == 3) 
            {
                System.out.println(green + bold + "Thank you for using our system!" + reset);
                sc.close();
                return;
            }
            else 
            {
                System.out.println(red + bold + "Invalid choice!" + reset);
            }
        }
        if (loggedIn)
        {
            System.out.println(blue + bold + "Please select your purpose of visit:" + reset);
            System.out.println(cyan + bold + "1. Coaching" + reset);
            System.out.println(cyan + bold + "2. Visiting" + reset);
            System.out.print("Enter choice (1 or 2): ");
            int choice = sc.nextInt();
            switch (choice)
            {
                case 1:
                    System.out.println(bold + cyan + "\nRedirecting to Coaching Hostel Booking Module..." + reset);
                    if (Hostel.welcomeScreen()) 
                    {
                        String loc = Hostel.location();
                        String cat = Hostel.hostelCategorySelection();
                        Hostel.showHostels(loc, cat);
                    }
                    break;
                case 2:
                    System.out.println(bold + cyan + "\nRedirecting to Visitor Hotel Booking Module..." + reset);
                    HotelBooking.hotelBooking();
                    break;
                default:
                    System.out.println(red + bold + "Invalid choice! Please restart the program." + reset);
            }
        }

        sc.close();
    }
}