package com.example.cookshop.items;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.StringTokenizer;

public class Article extends Item implements Comparable<Article>, Cloneable
{
    /**
     * Parcelable creator
     */
    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>()
    {
        @Override
        public Article createFromParcel(Parcel in)
        {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size)
        {
            return new Article[size];
        }
    };

    /**
     * The {@link Category} of the article
     */
    private Category category;
    /**
     * The amount
     */
    private int      amount;


    //....Constructor..........
    /**
     * The weight
     */
    private double   weight;

    /**
     * Standard constructor generates a new Article with standard values
     */
    public Article()
    {
        this("Name", "Description", Category.OTHERS, 0, 0.0);
    }

    /**
     * Constructor for all parameters
     */
    public Article(String name, String description, Category category, int amount, double weight)
    {
        // Using setter method here because they check (and correct if necessary) the values
        this.setName(name);
        this.setDescription(description);
        this.setCategory(category);
        this.setAmount(amount);
        this.setWeight(weight);
    }

    /**
     * Constructor to set an Article from a memento pattern
     */
    public Article(String mementoPattern)
    {
        this.setObjectFromMementoPattern(mementoPattern);
    }


    //....Methods..........


    /**
     * Constructor to set an Article from a {@link Parcel}
     */
    public Article(Parcel in)
    {
        this.name = in.readString();
        this.amount = in.readInt();
        this.category = (Category) in.readSerializable();
        this.weight = in.readDouble();
        this.description = in.readString();
    }

    /**
     * Adds to the existing amount
     *
     * @param add
     *         the value to be added
     */
    public void addAmount(int add)
    {
        this.setAmount(this.amount + add);
    }

    /**
     * Adds to the existing weight
     *
     * @param add
     *         the value to be added
     */
    public void addWeight(double add)
    {
        this.setWeight(this.weight + add);
    }

    /**
     * Compares one article to another on
     *
     * @param other
     *         the article to compare wit
     * @return int
     *         1         - if this.name is > other.getName
     *         0         - if this and other have the same name
     *         -1        -  if this.name is <  other.getName
     */
    @Override
    public int compareTo(Article other)
    {
        if (this.getName().compareTo(other.getName()) < 0)
        {
            return 1;
        }
        if (this.getName().compareTo(other.getName()) > 0)
        {
            return -1;
        }
        return 0;
    }


    // ....Getter..........

    /**
     * Clones an Article
     */
    @Override
    public Article clone()
    {
        Article clone = null;
        try
        {
            clone = (Article) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            //Should never occur, thats why i catch it right here
        }
        return clone;
    }

    /**
     * Getter for the category
     *
     * @return the category of the article
     */
    public Category getCategory()
    {
        return category;
    }

    /**
     * Setter for the Category
     *
     * @param category
     *         the new category
     */
    public void setCategory(Category category)
    {
        this.category = category;
    }


    // ....Setter..........

    /**
     * Getter for the amount
     *
     * @return the amount
     */
    public int getAmount()
    {
        return amount;
    }

    /**
     * Sets the amount to every value between 0 and int max
     *
     * @param amount
     *         integer
     */
    public void setAmount(int amount)
    {
        if (amount > 0)
        {
            if (amount <= Integer.MAX_VALUE)
            {
                this.amount = amount;
            }
            else
            {
                this.amount = Integer.MAX_VALUE;
            }
        }
        else
        {
            this.amount = 0;
        }
    }

    /**
     * Getter for the weight
     *
     * @return the weight
     */
    public double getWeight()
    {
        return weight;
    }


    //....Memento..........

    /**
     * Sets the weight to every positive double value
     *
     * @param weight
     */
    public void setWeight(double weight)
    {
        if (weight > 0)
        {
            this.weight = weight;
        }
        else
        {
            this.weight = 0;
        }
    }

    /**
     * Returns a memento pattern
     *
     * @return memento Pattern
     * @see {@link Memento}
     */
    @Override
    public String getMementoPattern()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name + DELIMITER_ARTICLES);
        sb.append(this.description + DELIMITER_ARTICLES);
        sb.append(this.weight + DELIMITER_ARTICLES);
        sb.append(this.amount + DELIMITER_ARTICLES);
        if (this.category != null) // This if statement prevents some NullPointerExceptions
        {
            switch (this.category)
            {
                case FRUIT:
                    sb.append(Category.FRUIT.toString() + DELIMITER_ARTICLES);
                    break;
                case VEGETABLE:
                    sb.append(Category.VEGETABLE.toString() + DELIMITER_ARTICLES);
                    break;
                case DRINK:
                    sb.append(Category.DRINK.toString() + DELIMITER_ARTICLES);
                    break;
                case MEAT:
                    sb.append(Category.MEAT.toString() + DELIMITER_ARTICLES);
                    break;
                default:
                    sb.append(Category.OTHERS.toString() + DELIMITER_ARTICLES);
            }
        }
        else
        {
            sb.append(Category.OTHERS.toString() + DELIMITER_ARTICLES);
        }
        return sb.toString();
    }

    /**
     * Set all values to the ones given by mementoPattern
     *
     * @param mementoPattern
     * @see {@link Memento}
     */
    @Override
    public void setObjectFromMementoPattern(String mementoPattern)
    {
        StringTokenizer st = new StringTokenizer(mementoPattern, DELIMITER_ARTICLES);

        this.setName(st.nextToken(DELIMITER_ARTICLES));
        this.setDescription(st.nextToken(DELIMITER_ARTICLES));
        this.setWeight(Double.parseDouble(st.nextToken(DELIMITER_ARTICLES).trim()));
        this.setAmount(Integer.parseInt(st.nextToken(DELIMITER_ARTICLES).trim()));
        switch (st.nextToken(DELIMITER_ARTICLES))
        {
            case "Fruit":
                this.category = Category.FRUIT;
                break;
            case "Vegetable":
                this.category = Category.VEGETABLE;
                break;
            case "Drink":
                this.category = Category.DRINK;
                break;
            case "Meat":
                this.category = Category.MEAT;
                break;
            default:
                this.category = Category.OTHERS;
        }
    }



    //....Parcelable..........


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(name);
        dest.writeInt(amount);
        dest.writeSerializable(category);
        dest.writeDouble(weight);
        dest.writeString(description);
    }

    public static Article deserialize(byte[] bytes) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream b      = new ByteArrayInputStream(bytes);
        ObjectInputStream o      = new ObjectInputStream(b);
        return   (Article) o.readObject();
    }
}
