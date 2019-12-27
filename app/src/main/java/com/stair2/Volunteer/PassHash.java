package com.stair2.Volunteer;

import android.util.Log;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;

/**
 * Static class allows for passwords to be hashed
 * @author Zach Taylor
 */
final class PassHash {

    /**
     * Convert a password string into a 64 length hash using sha-256
     * @param input Password String to hash
     * @return Hashed secure hash
     */
    static String HashPassword(String input)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); //get SHA 256 algorithm for hashing
            byte[] digest = md.digest(input.getBytes()); //create the sha digest from the password

            return byteToHex(digest); //convert into finalized hash

        }
        catch(NoSuchAlgorithmException e)
        {
            Log.e("PassHash:HashPassword", "NoSuchAlgorithm Exception Thrown", e);
        }

        return null;
    }

    /***
     * Convert a byte array to a string in hexadecimal format
     * @param input byte array of string to convert
     * @return string in hexadecimal format
     */
    private static String byteToHex(byte[] input)
    {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < input.length; i++)
        {
            builder.append(Integer.toString((input[i] & 0xff) + 0x100, 16).substring(1)); //convert byte to hex
        }

        return builder.toString();
    }
}
