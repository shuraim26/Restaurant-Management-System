package sample;

public class ModelDishTable {

    String dish_id,dish_name,cat_name,cat_id,dish_price;

    public ModelDishTable(String dish_id,String dish_name,String cat_id,String cat_name,String dish_price)
    {
        this.dish_id=dish_id;
        this.dish_name=dish_name;
        this.cat_name=cat_name;
        this.cat_id=cat_id;
        this.dish_price=dish_price;
    }

    public String getDish_id()
    {
        return dish_id;
    }

    public String getDish_name()
    {
        return dish_name;
    }

    public String getCat_name()
    {
        return cat_name;
    }

    public String getCat_id()
    {
        return cat_id;
    }

    public String getDish_price()
    {
        return dish_price;
    }
}
