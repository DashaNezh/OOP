public class Publisher {
    private String name;
    private Address address;


    // Конструктор без параметров
    public Publisher() {
    }

    // Конструктор с параметрами
    public Publisher(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    // Getters and Setters

    public String getName() {
        return name != null ? name : "Несуществует";
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address != null ? address : new Address("Несуществует", "Несуществует");
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("Publisher: %s, Address: %s", getName(), getAddress());
    }
}
