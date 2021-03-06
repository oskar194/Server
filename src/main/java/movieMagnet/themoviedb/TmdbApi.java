package movieMagnet.themoviedb;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.sym.Name;

import movieMagnet.config.KeyChainConfig;
import movieMagnet.themoviedb.model.Genres;
import movieMagnet.themoviedb.model.SearchResultTmdb;

@Service
public class TmdbApi implements TmdbApiInterface {
	private String key = "";
	private String readAccessToken = "";
	private static final String GENRES_URL = "https://api.themoviedb.org/3/genre/movie/list?api_key=%s&language=en-US";
	private static final String UPCOMING_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=%s&language=en-US&page=1";
	private static final String SEARCH_MOVIES_URL = "https://api.themoviedb.org/3/search/movie?api_key=%s&language=en-US&query=%s&page=%d&include_adult=%s";
	private static final String SEARCH_SERIES_URL = "https://api.themoviedb.org/3/search/tv?api_key=%s&language=en-US&query=%s&page=%d";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private KeyChainConfig keys;
	
	@PostConstruct
	public void prepareBean() {
		key = keys.getThemoviedbKey();
		readAccessToken = keys.getThemoviedbReadAccessToken();
	}
	
	@Override
	public SearchResultTmdb fetchNews() {
		String url = String.format(UPCOMING_URL, key);
		return restTemplate.getForObject(url, SearchResultTmdb.class);
	}

	@Override
	public SearchResultTmdb searchMovie(String query) {
		String url = String.format(SEARCH_MOVIES_URL, key, query, 1, false);
		return restTemplate.getForObject(url, SearchResultTmdb.class);
	}

	@Override
	public SearchResultTmdb searchTvShow(String query) {
		String url = String.format(SEARCH_SERIES_URL, key, query, 1);
		return restTemplate.getForObject(url, SearchResultTmdb.class);
	}

	@Override
	public Genres getGenresList() {
		String url = String.format(GENRES_URL, key);
		return restTemplate.getForObject(url, Genres.class);
	}
	


}
