package view.model;


import javafx.beans.property.*;

public class BookDTO {

    private StringProperty author;

    public void setAuthor(String author) {
        authorProperty().set(author);
    }

    public StringProperty authorProperty() {
        if(author==null)
            author=new SimpleStringProperty(this,"author");
        return author;
    }
    public String getAuthor()
    {
        return authorProperty().get();
    }

    private StringProperty title;

    public void setTitle(String title) {
       titleProperty().set(title);
    }

    public StringProperty titleProperty() {
        if(title==null)
            title=new SimpleStringProperty(this,"title");
        return title;
    }
    public String getTitle()
    {
        return titleProperty().get();
    }

    private LongProperty id;
    public void setId(Long id)
    {
        idProperty().set(id);
    }
    public LongProperty idProperty()
    {
        if(id==null)
            id=new SimpleLongProperty(this,"id");
        return id;
    }
    public Long getId()
    {
        return idProperty().get();
    }

    private IntegerProperty stock;
    public void setStock(int stock)
    {
        stockProperty().set(stock);
    }
    public IntegerProperty stockProperty()
    {
        if(stock==null)
            stock=new SimpleIntegerProperty(this,"stock");
        return stock;
    }
    public int getStock()
    {
        return stockProperty().get();
    }

    private IntegerProperty price;

    public void setPrice(int price)
    {
        priceProperty().set(price);
    }
    public IntegerProperty priceProperty()
    {
        if(price==null)
            price=new SimpleIntegerProperty(this,"price");
        return price;
    }
    public int getPrice()
    {
        return priceProperty().get();
    }
}
