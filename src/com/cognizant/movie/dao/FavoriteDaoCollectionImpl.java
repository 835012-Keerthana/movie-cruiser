package com.cognizant.movie.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.cognizant.movie.model.Favorite;
import com.cognizant.movie.model.Movie;

public class FavoriteDaoCollectionImpl implements FavoriteDao {
    private static HashMap<Long, Favorite> userFavorite; // instance variable

    public FavoriteDaoCollectionImpl() {
        if (userFavorite == null) {
            HashMap<Long, Favorite> favorites = new HashMap<Long, Favorite>();
            userFavorite = favorites;
        }
    }

    public void addFavoritesById(long userId, long movieId) {
        MovieDao movies = new MovieDaoCollectionImpl();
        Movie movie = movies.getMovieById(movieId);
        if (userFavorite.containsKey(userId)) {
            userFavorite.get(userId).getFavoriteList().add(movie);
        } else {
            Favorite favorite = new Favorite();
            List<Movie> listMenu = new ArrayList<Movie>();
            listMenu.add(movie);
            favorite.setFavoriteList(listMenu);
            userFavorite.put(userId, favorite);
        }
    }

    public void removeFavoritesById(long userId, long movieId) {
        List<Movie> list = userFavorite.get(userId).getFavoriteList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMovieId() == movieId) {
                list.remove(i);
                break;
            }
        }
    }

    public Favorite getAllFavorites(long userId) throws FavoriteEmptyException {
        Favorite favorite = userFavorite.get(userId);
        if (favorite == null || favorite.getFavoriteList().isEmpty()) {
            throw new FavoriteEmptyException();
            // break;
        }
        List<Movie> movie = favorite.getFavoriteList();
        int noOfFavorites = 0;
        noOfFavorites = movie.size();
        favorite.setNoOfFavorites(noOfFavorites);
        return favorite;
    }
}
