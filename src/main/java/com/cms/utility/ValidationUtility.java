package com.cms.utility;

import java.util.Collection;
import java.util.Map;

public class ValidationUtility {

    private ValidationUtility() {}

    /**
     * This method returns true if the collection is null or is empty.
     *
     * @param collection
     * @return true | false
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * This method returns true if the input string is null or its length is zero.
     *
     * @param string
     * @return true | false
     */
    public static boolean isEmpty(String text) {
        return text == null || text.length() == 0;
    }

    /**
     * This method returns true of the map is null or is empty.
     *
     * @param map
     * @return true | false
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * This method returns true if the object is null.
     *
     * @param object
     * @return true | false
     */
    public static boolean isEmpty(Object object) {
        return object == null;
    }

    /**
     * This method returns true if the input array is null or its length is zero.
     *
     * @param array
     * @return true | false
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * This method returns true if the Integer is null.
     *
     * @param object
     * @return true | false
     */
    public static boolean isEmpty(Integer value) {
        return value == null;
    }

    /**
     * This method returns true if the double is null.
     *
     * @param object
     * @return true | false
     */
    public static boolean isEmpty(Double value) {
        return value == null;
    }

    /**
     * This method returns true if the long is null.
     *
     * @param object
     * @return true | false
     */
    public static boolean isEmpty(Long value) {
        return value == null;
    }

    /**
     * This method returns true if the float is null.
     *
     * @param object
     * @return true | false
     */
    public static boolean isEmpty(Float value) {
        return value == null;
    }

    /**
     * This method returns true if the collection size is min size.
     *
     * @param collection
     * @return true | false
     */
    public static boolean isMin(Collection<?> collection, int min) {
        return collection != null && collection.size() >= min;
    }

    /**
     * This method returns true if the string size is min size.
     *
     * @param collection
     * @return true | false
     */
    public static boolean isMin(String text, int min) {
        return text != null && text.length() >= min;
    }

    /**
     * This method returns true if the collection size is max size.
     *
     * @param collection
     * @return true | false
     */
    public static boolean isMax(Collection<?> collection, int max) {
        return collection != null && collection.size() <= max;
    }

    /**
     * This method returns true if the string size is max size.
     *
     * @param collection
     * @return true | false
     */
    public static boolean isMax(String text, int max) {
        return text != null && text.length() <= max;
    }

    /**
     * This method returns true if the collection size between min & max size.
     *
     * @param collection
     * @return true | false
     */
    public static boolean isMinMax(Collection<?> collection, int min, int max) {
        return collection != null && collection.size() >= min && collection.size() <= max;
    }

    /**
     * This method returns true if the string size between min & max size.
     *
     * @param collection
     * @return true | false
     */
    public static boolean isMinMax(String text, int min, int max) {
        return text != null && text.length() >= min && text.length() <= max;
    }

    /**
     * This method returns true if the integer size between min & max size.
     *
     * @param collection
     * @return true | false
     */
    public static boolean isMinMax(Integer value, int min, int max) {
        return value != null && value >= min && value <= max;
    }

    /**
     * This method returns true if the integer size is max size.
     *
     * @param collection
     * @return true | false
     */
    public static boolean isMax(Integer value, int max) {
        return value != null && value <= max;
    }

    /**
     * This method returns true if the integer size is min size.
     *
     * @param collection
     * @return true | false
     */
    public static boolean isMin(Integer value, int min) {
        return value != null && value >= min;
    }

    /**
     * This method returns true if the double size between min & max size.
     *
     * @param collection
     * @return true | false
     */
    public static boolean isMinMax(Double value, double min, double max) {
        return value != null && value >= min && value <= max;
    }

    /**
     * This method returns true if the double size is max size.
     *
     * @param collection
     * @return true | false
     */
    public static boolean isMax(Double value, double max) {
        return value != null && value <= max;
    }

    /**
     * This method returns true if the double size is min size.
     *
     * @param collection
     * @return true | false
     */
    public static boolean isMin(Double value, double min) {
        return value != null && value >= min;
    }

    /**
     * This method returns true if the float size between min & max size.
     *
     * @param collection
     * @return true | false
     */
    public static boolean isMinMax(Float value, float min, float max) {
        return value != null && value >= min && value <= max;
    }

    /**
     * This method returns true if the double size is max size.
     *
     * @param collection
     * @return true | false
     */
    public static boolean isMax(Float value, float max) {
        return value != null && value <= max;
    }

    /**
     * This method returns true if the float size is min size.
     *
     * @param collection
     * @return true | false
     */
    public static boolean isMin(Float value, float min) {
        return value != null && value >= min;
    }
}
