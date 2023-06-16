/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.persistence.Entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
/**
 *
 * @author user
 */
 @Entity
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double money;

    public Client() {
      
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client(String name, double money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name=" + name + ", money=" + money + '}';
    }

   

   public void buyShoe(Shoe shoe) {
        if (shoe.getPrice() <= money) {
            money -= shoe.getPrice();
            System.out.println(name + " bought " + shoe.getBrand() + " shoe for $" + shoe.getPrice());
        } else {
            System.out.println(name + " does not have enough money to buy the shoe.");
        }
    }

    
}
