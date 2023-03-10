package com.ebanma.cloud.sourceCode;

import org.junit.Test;

import java.io.*;

/**
 * @author 鹿胜宝
 * @date 2023/03/06
 */
public class CodeTest {

    @Test
    public void myArrayListTest() throws IOException, ClassNotFoundException {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\a.txt"));
        oos.writeObject(list);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:\\a.txt"));
        MyArrayList<Integer> integers = (MyArrayList<Integer>) ois.readObject();
        System.out.println(integers);
    }

}
