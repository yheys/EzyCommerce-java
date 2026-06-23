package ezycommerce.models;


public class User {

    
    private int id;
    private String name;
    private String email;
    private String password;
    private static int totalUsers = 0;

    
    public User() {    //  Default Constructor
        totalUsers++;
    }

    
    public User(int id, String name, String email, String password) {
        this.id       = id;
        this.name     = name;
        this.email    = email;
        this.password = password;
        totalUsers++;
    }


    public int getId()            { return id; }
    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public String getPassword()   { return password; }
    public static int getTotalUsers() { return totalUsers; }

    
    public void setId(int id)             { this.id = id; }
    public void setName(String name)      { this.name = name; }
    public void setEmail(String email)    { this.email = email; }
    public void setPassword(String pass)  { this.password = pass; }

    
    public void displayInfo() {
    System.out.println("ID    : " + id);
    System.out.println("Name  : " + name);
    System.out.println("Email : " + email);
}

public void displayInfo(boolean showId) {
    if (showId) System.out.println("ID    : " + id);
    System.out.println("Name  : " + name);
    System.out.println("Email : " + email);
}

    
    @Override
    public String toString() {
        return "User[id=" + id + ", name=" + name + ", email=" + email + "]";
    }
}