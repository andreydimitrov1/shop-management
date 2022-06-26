package ConvenienceStore;

import java.util.UUID;

public class Employee {
    private String id;
    private String name;
    private int salary;

    public Employee(String name, int salary) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.salary = salary;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getSalary() {return this.salary;}
}
