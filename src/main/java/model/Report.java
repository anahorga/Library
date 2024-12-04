package model;

public class Report {

   private Long userId;
    private String username;
    private int stock;
    private int price;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString()
    {
        return "Angajatul cu id-ul " +
                userId + " si username "+ username
                +" a vandut luna aceasta "+
                stock +" carti in valoare totala de "
                +price +" lei.";
    }
}
