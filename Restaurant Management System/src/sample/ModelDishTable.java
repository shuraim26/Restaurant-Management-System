package sample;

public class ModelDishTable {

    String dish_id,dish_name,cat_name,cat_id;

    public ModelDishTable(String dish_id,String dish_name,String cat_name,String cat_id)
    {
        this.dish_id=dish_id;
        this.dish_name=dish_name;
        this.cat_name=cat_name;
        this.cat_id=cat_id;
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
}
