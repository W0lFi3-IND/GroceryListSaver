package com.wolfie.grocerylistsaver;

public class curd {
private String item;
private String cat;
private String  quan;
private String qty;
private String pri;
private  String id;

 public curd(String id,String item, String cat, String quan, String qty, String pri) {
     this.id=id;
     this.item = item;
        this.cat = cat;
        this.quan = quan;
        this.qty = qty;
        this.pri = pri;
    }

    @Override
    public String toString() {
        return
                "Item Name= " + item +"\n" +"Category= "+ cat + "\n" + "Quantity= " + quan +" "+qty + "\n" +
                        "Priority= " + pri;

    }

    public curd()
{

}
    public String getId() {
        return id;
    }

    public String getItem() {
        return item;
    }

    public String getCat() {
        return cat;
    }

    public String getQuan() {
        return quan;
    }

    public String getQty() {
        return qty;
    }

    public String getPri() {
        return pri;
    }
}
