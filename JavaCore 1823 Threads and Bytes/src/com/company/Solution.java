package com.company;

/*
1823 Threads and Bytes

Read the file names from the console until the word "exit" is entered.
Pass the file name to the ReadThread thread.
The ReadThread thread should find the byte that occurs in the file the maximum number of times, and add it to the resultMap dictionary,
where the String parameter is the file name, the Integer parameter is the desired byte.
Close streams.

Requirements:
1. The program should read the file names from the console until the word "exit" is entered.
2. For each file, create a ReadThread thread and run it.
3. After starting, each ReadThread thread should create its own thread for reading from the file.
4. Then, the threads should find the maximum byte in their file and add it to the resultMap dictionary.
5. The stream for reading from the file in each thread should be closed.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class Solution {
public static Map<String, Integer> resultMap = new HashMap<String, Integer>();

public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String s = null;
        while (true) {
        s = reader.readLine();
        if (s.equals("exit"))
        break;

        ReadThread th = new ReadThread(s);
        th.start();
        }
        }

public static class ReadThread extends Thread {
    private String fileName;
    private byte findedByte;

    public ReadThread(String fileName) {
        //implement constructor body
        this.fileName = fileName;
        //start();
    }

    // implement file reading here - реализуйте чтение из файла тут
    @Override
    public void run() {
        HashMap<Byte, Integer> freqMap = new HashMap<Byte, Integer>();

        try {
            FileInputStream f = new FileInputStream(this.fileName);
            while (f.available() > 0) {
                byte[] buf = new byte[f.available()];
                f.read(buf);

                for (byte b : buf) {
                    if (freqMap.containsKey(b))
                        freqMap.put(b, freqMap.get(b) + 1);
                    else
                        freqMap.put(b, 1);
                }
            }
            f.close();
        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }

        //Find Byte
        int max = 0;
        for (Map.Entry<Byte, Integer> pair : freqMap.entrySet()) {
            if (max < pair.getValue()) {
                this.findedByte = pair.getKey();
                max = pair.getValue();
            }
        }

        synchronized (resultMap) {
            resultMap.put(this.fileName, new Integer(this.findedByte));
        }
    }
}
}




