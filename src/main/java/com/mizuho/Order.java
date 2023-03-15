package com.mizuho;


public class Order {
    private long id;
    private double price;
    private char side;
    private long size;

    public Order(long id, double price, char side, long size){
        this.id = id;
        this.price = price;
        this.side = side;
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public char getSide() {
        return side;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;
        if (obj instanceof Order) {
            Order order = (Order) obj;
            return (order.id == this.id && order.price == this.price && order.side == this.side && order.size == this.size);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode()
    {
        final int PRIME = 31;
        int result = 1;
        result = (int) (PRIME * result + id+Double.valueOf(price).hashCode()
                        +Character.valueOf(side).hashCode()+Long.valueOf(size).hashCode());
        return result;
    }

}
