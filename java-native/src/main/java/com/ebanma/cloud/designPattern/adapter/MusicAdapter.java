package com.ebanma.cloud.designPattern.adapter;

/**
 * @author 鹿胜宝
 * @date 2023/03/04
 */
public class MusicAdapter implements MusicPlayer{

    private ImprovedMusicPlayerImpl improvedMusicPlayer;

    @Override
    public void play(String type) {
        improvedMusicPlayer = new ImprovedMusicPlayerImpl();
        if("mp4".equals(type)) {
            improvedMusicPlayer.playMp4();
            return;
        }
        if ("mp5".equals(type)){
            improvedMusicPlayer.playMp5();
        }
    }
}
