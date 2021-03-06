package gui.model;

import be.PlayList;
import bll.MyTunesManager;
import bll.exceptions.PlayListException;
import bll.exceptions.SongException;



public class PlaylistModel {

    private MyTunesManager manager;
    MainModel mainModel;
    PlayList playList;

    public PlaylistModel() {
        manager = new MyTunesManager();
        mainModel = new MainModel();
    }

    public void createPlaylist(String name) throws PlayListException, SongException {
        playList = manager.createPlayList(name);
        mainModel.getAllSongs().removeAll();
        mainModel.getAllSongs().addAll(manager.getAllPlayLists());
    }


    public void updatePlayList(PlayList playList,String name,int song,String time) throws  PlayListException {
        manager.updatePlayList(playList,name,song,time);
    }
}
