package bll;

import be.Category;
import be.Joins;
import be.PlayList;
import be.Song;
import bll.exceptions.CategoriesException;
import bll.exceptions.JoinsException;
import bll.exceptions.PlayListException;
import bll.exceptions.SongException;
import dal.dao.*;

import java.sql.SQLException;
import java.util.List;

public class MyTunesManager implements OwsLogicFacade {

    ArtistsDAO artistsDAO;
    CategoriesDAO categoriesDAO;
    PlayListsDAO playListDAO;
    JoinsDAO joinsDAO;
    SongDAO songDAO;

    public MyTunesManager() {
        artistsDAO = new ArtistsDAO();
        categoriesDAO = new CategoriesDAO();
        playListDAO = new PlayListsDAO();
        joinsDAO = new JoinsDAO();
        songDAO = new SongDAO();
    }


    @Override
    public PlayList createPlayList(String name) throws PlayListException {
        try {

            return playListDAO.createPlayList(name);
        } catch (SQLException e) {
            throw new PlayListException("Playlist already exists", e);
        } catch (PlayListException e) {
            throw e;
        }
    }

    @Override
    public void deletePlayList(PlayList playList) throws PlayListException {
        try {
            playListDAO.deletePlayList(playList);
        } catch (SQLException e) {
            throw new PlayListException("Unable to delete playlist", e);
        }
    }

    @Override
    public List<PlayList> getAllPlayLists() throws PlayListException {
        try {
            return playListDAO.getAllPlayLists();
        } catch (SQLException e) {
            throw new PlayListException("Couldn't get all playlists", e);
        }
    }

    @Override
    public void removeSongFromPlayList(Joins joins, PlayList playList) throws PlayListException {
        try {
            joinsDAO.removeJoins(joins, playList);
        } catch (SQLException e) {
            throw new PlayListException("Couldn't remove song from playlist", e);
        }
    }

    public List<Joins> getAllJoinsPlayList(PlayList playList) throws JoinsException {
        try {
            return joinsDAO.getAllJoinsPlayList(playList);
        } catch (SQLException e) {
            throw new JoinsException("Couldn't get all songs in playlist", e);
        }
    }

    @Override
    public void moveSongUp(Joins joins) throws JoinsException {
        try {
            joinsDAO.moveSongUp(joins);
        } catch (SQLException e) {
            throw new JoinsException("Couldn't move song up", e);
        }
    }

    @Override
    public void moveSongDown(Joins joins) throws JoinsException {
        try {
            joinsDAO.moveSongDown(joins);
        } catch (SQLException e) {
            throw new JoinsException("Couldn't move song down", e);
        }
    }

    @Override
    public List<Song> getAllSongs() throws SongException {
        try {
            return songDAO.getAllSongs();
        } catch (SQLException e) {
            throw new SongException("Couldn't get all song", e);
        }
    }

    @Override
    public Song createSong(String title, String artist, String category, String filePath, String time) throws SongException {
        try {
            return songDAO.createSong(title, artist, category, filePath, time);
        } catch (SQLException e) {
            throw new SongException("Song with this name already exists", e);
        }
    }

    @Override
    public void deleteSong(Song song) throws SongException {
        try {
            songDAO.deleteSong(song);
        } catch (SQLException e) {
            throw new SongException("Couldn't delete song", e);
        }
    }

    @Override
    public void updateSong(String title, Song song, String newArtist, String newCategory) throws SongException {
        try {
            songDAO.updateSong(title, song, newArtist, newCategory);
        } catch (SQLException e) {
            throw new SongException("Couldn't update song", e);
        }
    }

    @Override
    public Song getSongByID(int songId) throws SongException {
        try {
            return songDAO.getSongById(songId);
        } catch (SQLException e) {
            throw new SongException("Couldn't get song with this id", e);
        }
    }

    @Override
    public List<Category> getAllCategories() throws CategoriesException {
        try {
            return categoriesDAO.getAllCategories();
        } catch (SQLException e) {
            throw new CategoriesException("Connection failed", e);
        }
    }

    @Override
    public void updateCategory(Category category, String name) throws CategoriesException {
        try {
            categoriesDAO.updateCategory(category, name);
        } catch (SQLException e) {
            throw new CategoriesException("Error", e);
        }

    }

    @Override
    public int createNewCategory(String category) throws CategoriesException {
        try {
            return categoriesDAO.createNewCategory(category);
        } catch (SQLException e) {
            throw new CategoriesException("Error", e);
        } catch (CategoriesException e) {
            throw e;
        }
    }

    @Override
    public void deleteCategory(Category category) throws CategoriesException {
        try {
            categoriesDAO.deleteCategory(category);
        } catch (SQLException e) {
            throw new CategoriesException("Song dependency.", e);
        }
    }

    public Joins createJoins(Song song, PlayList playList) throws JoinsException {
        try {
            return joinsDAO.createJoin(song, playList);
        } catch (SQLException e) {
            throw new JoinsException("Join already exists", e);
        }
    }


}
