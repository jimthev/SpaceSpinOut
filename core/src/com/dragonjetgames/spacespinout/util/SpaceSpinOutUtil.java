package com.dragonjetgames.spacespinout.util;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.badlogic.gdx.Gdx;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

public class SpaceSpinOutUtil {

    public static boolean fileExists(String fileName) {
        if (Gdx.files.external(fileName).exists()) {
            return true;
        }
        return Gdx.files.internal(fileName).exists();
    }

    public static OutputStream getFileOutputStream(String fileName) {
        try {
            OutputStream os = Gdx.files.external(fileName).write(false);
//      System.out.println("f:"+ Gdx.files.external(fileName).file().getCanonicalPath());
            return os;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getFileAsStringArray(String fileName) {
        ArrayList strs = new ArrayList();

        try {
            InputStream is;

            if (Gdx.files.external(fileName).exists()) {
                is = Gdx.files.external(fileName).read();
            } else {
                is = Gdx.files.internal(fileName).read();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s;
            while ((s = br.readLine()) != null) {
                strs.add(s);
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return (String[]) strs.toArray(new String[0]);
    }

    public static String[] breakStringIntoStrings(String str, String delimeter) {
        try {
            ArrayList al = new ArrayList();

            int index = str.indexOf(delimeter);
            while (index >= 0) {
                String left = str.substring(0, index);
                al.add(left);
                str = str.substring(index + delimeter.length());
                index = str.indexOf(delimeter);
            }

            if (str != null && str.length() > 0) {
                al.add(str);
            }

            return (String[]) al.toArray(new String[0]);
        } catch (Exception e) {
        }

        return null;
    }

}
