package com.ebanma.cloud.designPattern.adapter;

/**
 * @author 鹿胜宝
 * @date 2023/03/04
 */
public class ImprovedMusicPlayerImpl implements ImprovedMusicPlayer {
    @Override
    public void playMp4() {
        System.out.println("Playing mp4 file.");
    }

    @Override
    public void playMp5() {
        System.out.println("Playing mp5 file.");
    }
}
