package com.example.cookshop.items;


import java.text.ParseException;

/**
 * This interface prescribes a method to get the memento pattern of an Object
 * and another one to set all values of an object to the values given by a memento pattern.
 * <p>
 * This interface will be implemented by any {@link Item} class, witch are currently
 * {@link Article} and {@link Recipe}
 * <p>
 * Furthermore it provides final string values, which will be used
 * as delimiters  to separate memento pattern obj objects from different classes.
 *
 * @author willi
 */
public interface Memento
{
    /**
     * Delimiter which will be added before ech value of an recipe
     * <p>
     * Example : name++description++articleList++
     */
    String DELIMITER_RECIPES  = "++";
    /**
     * Delimiter which will be added before ech value of an article
     * <p>
     * Example : name++description++weight++amount++category++
     */
    String DELIMITER_ARTICLES = "||";
    /**
     * Delimiter which will be added after a whole item memento patter
     * this can be an complete {@link Article} or {@link Recipe}
     * <p>
     * Example :  article**article**recipe**
     * (Note: "article" and "recipe" are each their own memento pattern, which means they will use ++ or ** within them )
     */
    String DELIMITER_ITEMS    = "**";
    /**
     * Delimiter for {@link Step}
     */
    String DELIMITER_STEPS    = "::";


    /**
     * Returns a String witch all values which define the current state of an object
     *
     * @return mementoPattern
     */
    String getMementoPattern();

    /**
     * Sets all values of an Objects to the values given by the mementoPattern
     */
    void setObjectFromMementoPattern(String mementoPattern) throws ParseException;
}
