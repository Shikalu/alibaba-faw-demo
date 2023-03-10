package com.ebanma.cloud.designPattern.adapter;

/**
 * @author 鹿胜宝
 * @date 2023/03/04
 */
public class MusicPlayImpl implements MusicPlayer {
    private MusicAdapter musicAdapter;

    public MusicPlayImpl() {
        this.musicAdapter = new MusicAdapter();
    }

    @Override
    public void play(String type) {
        if ("mp3".equals(type)) {
            System.out.println("Playing mp3 file.");
            return;
        }
        if ("mp4".equals(type) || "mp5".equals(type)) {
            musicAdapter.play(type);
            return;
        }
        System.out.println("Invalid music format! " + type + " format is not supported.");
    }
}
