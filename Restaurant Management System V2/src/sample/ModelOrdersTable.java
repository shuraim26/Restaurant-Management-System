package sample;

public class ModelOrdersTable
{
    String ord_no;

    public ModelOrdersTable(String ord_no)
    {
        this.ord_no=ord_no;
    }

    public String getOrd_no()
    {
        return "Order No "+ord_no;
    }

    public String getRawOrder()
    {
        return ord_no;
    }
}
