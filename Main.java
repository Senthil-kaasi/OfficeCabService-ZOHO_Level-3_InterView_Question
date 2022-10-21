import java.util.*;
import java.time.*;

class Cab{
    int cabId;
    String schemeName;
    int amountPerKM;
    
    public Cab(int cabId,String schemeName,int amount){
        this.cabId = cabId;
        this.schemeName = schemeName;
        this.amountPerKM = amount;
    }
    
    public void display(){
        System.out.println(this.cabId+" "+this.schemeName+" "+this.amountPerKM);
    }
    
    public int getAmountPerKM(){
        return this.amountPerKM;
    }
}

class Designation{
    int designationId;
    String name;
    Cab cab;
    
    public Designation(int id,String name,Cab cab){
        this.designationId = id;
        this.name = name;
        this.cab = cab;
    }
    
    public void display(){
        System.out.println(this.designationId+" "+this.name);
        cab.display();
    }
    public int getAmountPerKM(){
        return cab.getAmountPerKM();
    }
}

class Employee{
    int id;
    String name;
    String location;
    int distance;
    Designation designation;
    
    public Employee(int id,String name,String location,int distance,Designation designation){
        this.id = id;
        this.name = name;
        this.location = location;
        this.distance = distance;
        this.designation = designation;
    }
    
    public void display(){
        System.out.println(this.id+" "+this.name+" "+this.location+" "+this.distance);
        designation.display();
    }
    
    public int getAmountPerKM(){
        return designation.getAmountPerKM();
    }
}

class Trip{
    int tripId;
    String tripMonth;
    int employeeId;
    int tripCost;
    
    public Trip(int tripId,String tripMonth,int empId){
        this.tripId = tripId;
        this.tripMonth = tripMonth;
        this.employeeId = empId;
    }
    
    public void calculateTripCost(Employee emp){
            this.tripCost = (emp.distance * emp.getAmountPerKM());
            System.out.println("Trip booked");
            System.out.println(this.tripCost);

    }
    
    public void display(){
        System.out.println(this.tripId+" "+this.employeeId+" "+this.tripMonth+" "+this.tripCost);
    }
}


public class Main{
    static Scanner sc = new Scanner(System.in);
    static String[] monthArr = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    
    // for unique id's for classes
    static int empId = 1;
    static int cabId = 1;
    static int desigId = 1;
    static int tripId = 1;
    
    // Storing different objects in list 
    static ArrayList<Employee> employees = new ArrayList<Employee>();
    static ArrayList<Designation> designations = new ArrayList<Designation>();
    static ArrayList<Cab> cabs = new ArrayList<Cab>();
    static ArrayList<Trip> trips = new ArrayList<Trip>();
    
    // Creating Cab
    static Cab createCab(String schemeName){
        Cab cab = null;
        boolean flag = false;
        for(int i=0;i<cabs.size();i++){
            Cab c = cabs.get(i);
            if(c.schemeName.equals(schemeName)){
                flag = true;
                cab = c;
                break;
            }
        }
        if(!flag){
            System.out.println("Enter amount per km:");
            int amount = sc.nextInt();
            
            cab = new Cab(cabId,schemeName,amount);
            cabs.add(cab);
            cabId++;
        }
        return cab;
    }
    
    // Creating Designation
    static Designation createDesignation(String name){
        Designation designation = null;
        boolean flag = false;
        String lowerName = name.toLowerCase();
        for(int i=0;i<designations.size();i++){
            Designation d = designations.get(i);
            if(d.name.equals(lowerName)){
                flag = true;
                designation = d;
                break;
            }
        }
        if(!flag){
            System.out.println("Enter cab scheme:");
            String cabScheme = sc.nextLine();
        
            Cab cab = createCab(cabScheme);

            designation = new Designation(desigId,lowerName,cab);
            designations.add(designation);
            desigId++;
        }
        return designation;
    }
    
    // Method used to create Employee
    static void createEmployee(){
        Cab cab = null;
        Designation designationObj = null;
        Employee employee = null;
        
        System.out.println("Enter Employee name:");
        String name = sc.nextLine();
        
        System.out.println("Enter location:");
        String location = sc.nextLine();
        
        System.out.println("Enter distance:");
        int distance = sc.nextInt();
        sc.nextLine();
        
        System.out.println("Enter Designation:");
        String designation = sc.nextLine();
        designationObj = createDesignation(designation);
        
        employee = new Employee(empId,name,location,distance,designationObj);
        employees.add(employee);
        employee.display();
        
        // Increasing Id's
        empId++;
        
        System.out.println("Employee created Successfully");
    }
    
    // Booking a trip
    static void makeATrip(){
        Trip trip = null;
        Employee emp = null;
        System.out.println("Enter Employee Id");
        int employeeId = sc.nextInt();
        
        for(int i=0;i<employees.size();i++){
            Employee e = employees.get(i);
            if(e.id == employeeId){
                emp = e;
                break;
            }
        }
        
        if(emp != null){
            System.out.println("Enter Month [from 1 to 12]");
            int monthNum = sc.nextInt();
            if(isValidMonth(monthNum)){
                trip = new Trip(tripId,monthArr[monthNum - 1],employeeId);
                trip.calculateTripCost(emp);
                trips.add(trip);
                trip.display();
            }
        }else{
            System.out.println("Employee Not Found. Enter a valid employee id :(");
        }
        
    }
    
    static void seeTotalCostForAMonth(){
        System.out.println("Enter a Month [From 1 to 12]");
        int monthNum = sc.nextInt();
        int totalCostForaMonth = 0;
        if(isValidMonth(monthNum)){
            String month = monthArr[monthNum - 1];
            for(int i=0;i<trips.size();i++){
                Trip t = trips.get(i);
                if(t.tripMonth.equals(month)){
                    totalCostForaMonth += t.tripCost;
                }
            }
            System.out.println("Total trip cost for "+month+" is "+totalCostForaMonth);
        }else{
            System.out.println("Enter a valid Month :( ");
        }
    }
    
    static Employee getEmployeeById(int id){
        Employee e = null;
        for(int i=0;i<employees.size();i++){
            Employee emp = employees.get(i);
            if(emp.id == id){
                e = emp;
                break;
            }
        }
        return e;
    }
    
    static void seeTotalCostForEmployee(){
        System.out.println("Enter Employee id:");
        int eId = sc.nextInt();
        Employee e = getEmployeeById(eId);
        int totalCost = 0;
    
        if(e != null){
            for(int i=0;i<trips.size();i++){
                Trip t = trips.get(i);
                if(t.employeeId == eId){
                    totalCost += t.tripCost;
                }
            }
            System.out.println(e.name+"'s total trip cost is "+totalCost);
        }else{
            System.out.println("Employee Not Found. Enter a valid Employee Id :(");
        }
    }
    
    static void seeTotalCostForSpecific(){
        System.out.println("Enter Employee Id");
        int eId = sc.nextInt();
        
        System.out.println("Enter start Month [from 1 to 12]");
        int startMonth = sc.nextInt();
        
        System.out.println("Enter end Month [from 1 to 12]");
        int endMonth = sc.nextInt();
        
        Employee empObj = getEmployeeById(eId);
        
        int totalCost = 0;
        if(startMonth < endMonth && isValidMonth(startMonth) && isValidMonth(endMonth)){
            for(int i=startMonth;i<= endMonth;i++){
                String month = monthArr[i-1];
                for(int j=0;j<trips.size();j++){
                    Trip t = trips.get(j);
                    if(t.employeeId == eId && t.tripMonth.equals(month)){
                        totalCost += t.tripCost;
                    }
                }
            }
            System.out.println("Total cost from "+monthArr[startMonth-1]+" to "+monthArr[endMonth - 1]+" for "+empObj.name+" is "+totalCost);
        }else{
            System.out.println("End month is less than start month");
        }
    }
    
    static boolean isValidMonth(int num){
        if(num > 0 && num < 13){
            return true;
        }
        return false;
    }
    
    // Method used to show menu
    static void showMenu(){
        
        // Showing menu
        System.out.println("****** Welcome to Cab Service ******");
        System.out.println("1.Create Employee.");
        System.out.println("2.Make a trip.");
        System.out.println("3.See total cost for a month.");
        System.out.println("4.See total cost for a employee.");
        System.out.println("5.See total cost for a specific period for a specific employee.");
        System.out.println("6.Exit");
        System.out.println();
        System.out.println("Enter a choice :)");
        
        // Taking choice
        int option = sc.nextInt();
        sc.nextLine();
        
        // Making respective actions according to the option
        switch(option){
            case 1:
                createEmployee();
                showMenu();
                break;
            case 2:
                makeATrip();
                showMenu();
                break;
            case 3:
                seeTotalCostForAMonth();
                showMenu();
                break;
            case 4:
                seeTotalCostForEmployee();
                showMenu();
                break;
            case 5:
                seeTotalCostForSpecific();
                showMenu();
                break;
            case 6:
                break;
        }
    }
    
    public static void main(String[] args) {
        showMenu();
    }
}
