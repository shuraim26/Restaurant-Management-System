package sample;

public class ModelCatTable {

    String cat_id,cat_name;

    public ModelCatTable(String cat_id,String cat_name)
    {
        this.cat_id=cat_id;
        this.cat_name=cat_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }
}
