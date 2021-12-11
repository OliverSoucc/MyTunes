package gui.model;

import be.Joins;
import be.PlayList;
import be.Song;
import bll.MyTunesManager;
import bll.exceptions.JoinsException;
import bll.exceptions.PlayListException;
import bll.exceptions.SongException;
import dal.dao.*;
import gui.controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class MainModel {
    private ObservableList<PlayList>allPlayLists;
    private ObservableList<Song>allSongs;
    private ObservableList<Joins> allSongsForGivenPlayList;


    MyTunesManager manager;
    ArtistsDAO artistsDAO;
    CategoriesDAO categoriesDAO;
    PlayListsDAO playListsDAO;
    SongDAO songDAO;

    public MainModel(){
        manager = new MyTunesManager();
        artistsDAO = new ArtistsDAO();
        categoriesDAO = new CategoriesDAO();
        playListsDAO=new PlayListsDAO();
        songDAO = new SongDAO();
    }



    public void moveSongDown(Joins joins, PlayListsDAO playListsDAO,PlayList playList) throws JoinsException {
        manager.moveSongDown(joins,playListsDAO);
    }

    public void moveSongUp(Joins joins, PlayListsDAO playListsDAO) throws JoinsException {
        manager.moveSongUp(joins,playListsDAO);
    }

    public void deleteSong(Song song, JoinsDAO joinsDAO, ArtistsDAO artistsDAO, CategoriesDAO categoriesDAO,PlayListsDAO playListsDAO,SongDAO songDAO) throws SongException {
        manager.deleteSong(song, joinsDAO,artistsDAO,categoriesDAO,playListsDAO,songDAO);
        allSongs.remove(song);
    }

    public void deleteSongFromGivenPlayList(Joins joins,PlayList playList,PlayListsDAO playListsDAO,SongDAO songDAO) throws PlayListException {
         manager.removeSongFromPlayList(joins,playListsDAO,playList,songDAO);
            allSongsForGivenPlayList.remove(joins);
    }


    public ObservableList getAllPlayLists() throws PlayListException {
        allPlayLists= FXCollections.observableArrayList();
        allPlayLists.addAll(manager.getAllPlayLists());
        return allPlayLists;
    }
    public ObservableList getAllSongs() throws SongException {
        //allSongs.clear();
        allSongs=FXCollections.observableArrayList();
        allSongs.addAll(manager.getAllSongs(artistsDAO,categoriesDAO));
        return allSongs;
    }

    public void deletePlayList(PlayList playList) throws PlayListException {
        manager.deletePlayList(playList);
        allPlayLists.remove(playList);
    }

    public ObservableList getAllSongsForGivenPlayList(PlayList playList) throws JoinsException {
        allSongsForGivenPlayList=FXCollections.observableArrayList();
        allSongsForGivenPlayList.addAll(manager.getAllJoinsPlayList(playList));
        return allSongsForGivenPlayList;
    }

    public void addSongToGivenPlayList(Song song, PlayList playList) throws JoinsException {
        allSongsForGivenPlayList.add(manager.createJoins(song,playList,playListsDAO));

    }

    public Song getSongByID(int songId) throws SongException {
        return  manager.getSongByID(songId);
    }
}
