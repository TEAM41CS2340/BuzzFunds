package com.cs2340.buzzfunds;

import java.util.HashMap;
import java.util.Map;

/**
 * A singleton class implementation of Android Intent.
 * 
 * @author Sean Collins
 */
public class IntentSingleton {
	private static Map<String, String> stringMap = new HashMap<String, String>();
	private static Map<String, Boolean> booleanMap = new HashMap<String, Boolean>();
	private static Map<String, Account> accountMap = new HashMap<String, Account>();
	private static Map<String, User> userMap = new HashMap<String, User>();

	private static IntentSingleton instance = null;
	
	/**
	 * This class cannot be instantiated under normal means.
	 */
	protected IntentSingleton() {
		// No implementation here because all fields are public.
	}
	
	/**
	 * Returns the current instance of IntentSingleton, creating a 
	 * new instance if necessary.
	 * 
	 * @return The (only) running instance of IntentSingleton
	 */
	public IntentSingleton getInstance() {
		if (instance == null) {
			instance = new IntentSingleton();
		}
		
		return instance;
	}
	
	/**
	 * Adds a String to this IntentSingleton store.
	 * 
	 * @param key The String key associated with the String
	 * @param value The value of the String
	 */
	public static void putString(String key, String value) {
		stringMap.put(key, value);
	}
	
	/**
	 * Adds a boolean to this IntentSingleton store.
	 * 
	 * @param key The String key associated with the boolean
	 * @param value The value of the boolean
	 */
	public static void putBoolean(String key, boolean value) {
		booleanMap.put(key, value);
	}
	
	/**
	 * Returns the String associated with a given key.
	 * 
	 * @param key The String key of the value
	 * @return The value associated with the key, or null if none found
	 */
	public static String getString(String key) {
		return stringMap.get(key);
	}
	
	/**
	 * Returns the boolean associated with a given key.
	 * 
	 * @param key The String key of the value
	 * @return The value associated with the key, or null if none found
	 */
	public static boolean getBoolean(String key) {
		return booleanMap.get(key);
	}
	
	/**
	 * Checks if key exists in this IntentSingleton store.
	 * 
	 * @param key The key to check
	 * @return true if found; else false
	 */
	public static boolean keyExists(String key) {
		return (stringMap.containsKey(key) || 
				booleanMap.containsKey(key) || 
				accountMap.containsKey(key));
	}
	
	/**
	 * Adds an Account to this IntentSingleton store.
	 * 
	 * @param key The String key associated with the Account
	 * @param value The value of the Account
	 */
	public static void putAccount(String key, Account value) {
		accountMap.put(key, value);
	}

    /**
     * Adds a User to this IntentSingleton store.
     *
     * @param key The String key associated with the User
     * @param value The value of the User
     */
    public static void putUser(String key, User value) {
        userMap.put(key, value);
    }

    /**
     * Returns the User associated with a given key.
     *
     * @param key The String key associated with the User
     * @return The value associated with the key, or null if none found
     */
    public static User getUser(String key) {
        return userMap.get(key);
    }
	/**
	 * Returns the Account associated with a given key.
	 * 
	 * @param key The String key of the value
	 * @return The value associated with the key, or null if none found
	 */
	public static Account getAccount(String key) {
		return accountMap.get(key);
	}

}
