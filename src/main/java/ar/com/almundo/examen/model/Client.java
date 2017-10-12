package ar.com.almundo.examen.model;

public class Client {

    private Integer clientNumber;
    private String firstName;
    private String lastName;

    public Client (Integer clientNumber, String firstName, String lastName){
        this.clientNumber = clientNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getClientNumber() { return clientNumber; }

    public void setClientNumber(Integer clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("[")
                .append("Numero de CLiente: ")
                .append(getClientNumber())
                .append(", ")
                .append("Nombre: ")
                .append(getFirstName())
                .append(", ")
                .append("Apellido: ")
                .append(getLastName())
                .append("]")
                .toString();
    }
}
