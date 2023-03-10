package com.ebanma.cloud.designPattern.adapter;

import java.util.List;

/**
 * @author 鹿胜宝
 * @date 2023/03/04
 */
public class AdapterTest {
    public static void main(String[] args) {
        MusicPlayer musicPlayer = new MusicPlayImpl();
        musicPlayer.play("mp3");
        musicPlayer.play("mp4");
        musicPlayer.play("mp5");
        musicPlayer.play("mp7");
        List<Integer> list = MyArrays.asList(1,2,3,4);
        System.out.println(list);
    }
}
