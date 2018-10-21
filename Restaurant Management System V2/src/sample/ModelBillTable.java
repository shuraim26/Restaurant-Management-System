package sample;

public class ModelBillTable
{
    String id,name,cat,price,quantity,total;

    public ModelBillTable()
    {

    }

    public ModelBillTable(String id,String name,String cat,String price,String quantity)
    {
        this.id=id;
        this.name=name;
        this.cat=cat;
        this.price=price;
        this.quantity=quantity;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getCat()
    {
        return cat;
    }

    public String getPrice()
    {
        return price;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public String getTotal()
    {
        return Float.toString(Float.valueOf(price)*Integer.parseInt(quantity));
    }


}
