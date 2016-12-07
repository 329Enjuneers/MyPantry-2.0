package com.example.z.mypantry20;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Z on 10-Nov-16.
 */

public class PantryItem implements Serializable
{
    private String name;



    private Drawable photo;
    private float amountRemaining;
    private String amountRemainingUnit;

    // TODO: will be removed once we have the photo feature working. Placed for testing purposes
    // for other feature.
    PantryItem(String name, float amountRemaining, String amountRemainingUnit)
    {
        this.name = name;
        this.amountRemaining = amountRemaining;
        this.amountRemainingUnit = amountRemainingUnit;
    }

    PantryItem(String name, Drawable photo, float amountRemaining, String amountRemainingUnit)
    {
        this.name = name;
        this.photo = photo;
        this.amountRemaining = amountRemaining;
        this.amountRemainingUnit = amountRemainingUnit;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Drawable getPhoto()
    {
        return photo;
    }

    public void setPhoto(Drawable photo)
    {
        this.photo = photo;
    }

    public float getAmountRemaining()
    {
        return amountRemaining;
    }

    public void setAmountRemaining(float amountRemaining)
    {
        this.amountRemaining = amountRemaining;
    }

    public String getAmountRemainingUnit()
    {
        return amountRemainingUnit;
    }

    public void setAmountRemainingUnit(String amountRemainingUnit)
    {
        this.amountRemainingUnit = amountRemainingUnit;
    }

    /**
     * returns 'clear' if able to consume, 'unitMisMatch' if units don't match, 'notEnough' if don't have enough of the item to consume
     * @param amountToConsume
     * @param unitToConsume
     * @return
     */
    public String consume(float amountToConsume, String unitToConsume)
    {
//        String verdict;
        //If the unit the user is trying to consume matches the unit of the PantryItem
        if(unitToConsume.equals(amountRemainingUnit))
        {
            //The user is attempting to consume an amount less than or equal to amount remaining
            if(amountRemaining >= amountToConsume)
            {
                amountRemaining -= amountToConsume;
                System.out.println("*******Consumed successfully*******");
                return "clear";
            }
            //The user is attempting to consume more than they have
            else
            {
                //TODO
                System.out.println("********The user is attempting to consume more than they have*******");
                return "notEnough";
            }
        }
        //The unit the user is trying to consume DOES NOT match the unit of the PantryItem
        else
        {
            //TODO make a conversion? - don't allow?
            System.out.println("*******The unit the user is trying to consume DOES NOT match the unit of the PantryItem*******");

            return "unitMisMatch";

        }
//        return verdict;
    }

    @Override
    public String toString()
    {
        return name;
        //return "[Name: "  + name + ", Remaining: " + amountRemaining + ", Unit: " + amountRemainingUnit + "]";
    }
}