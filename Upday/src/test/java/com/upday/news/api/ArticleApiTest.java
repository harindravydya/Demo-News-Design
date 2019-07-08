package com.upday.news.api;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.upday.news.model.Article;
import com.upday.news.service.ArticleServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ArticleApiTest {

	@Mock
	private ArticleServiceImpl articleService;
	
	
	@InjectMocks
	private ArticleApi articleApi;
	
	private static String AUTHOR_NAME = "AuthorX";
	
	@Test
	public void testArticleApiforKeywordsWhenKeywordsAreNotPresent() {
		List<String> keywords = new ArrayList<>();
		ResponseEntity<List<Article>> articles = articleApi.findArtclesByKeywords(keywords);
		assertThat(articles.getStatusCode().value(),Matchers.is(400));
	}
	
	@Test
	public void testArticleApiForRequiredKeywords() {
		Set<String> keywords = new HashSet<>();
		keywords.add("CWC2019");
		when(articleService.findAllArticlesByKeywords( new ArrayList<String>(keywords))).thenReturn(getListOfArticlesForTest());
		ResponseEntity<List<Article>> articles = articleApi.findArtclesByKeywords(new ArrayList<String>(keywords));
		assertThat(articles.getStatusCode().value(),Matchers.is(200));
	}
	
	@Test
	public void testIfArticlesAreRetrievedWhenThereIsNoAuthorName() {
		assertThat(articleApi.findArticlesByAuthor(null).getStatusCode().value(), Matchers.is(400));
	}
	
	@Test
	public void testIfArticlesAreRetrievedIfUnKnownAuthorNameGiven() {
		when(articleService.findAllArticlesByAuthor(AUTHOR_NAME)).thenReturn(new ArrayList<>());
		assertThat(articleApi.findArticlesByAuthor(AUTHOR_NAME).getStatusCode().value(), Matchers.is(404));
	}
	
	@Test
	public void testIfArticlesAreRetrievedIfAuthorsGiven() {
		when(articleService.findAllArticlesByAuthor(AUTHOR_NAME)).thenReturn(getListOfArticlesForTest());
		ResponseEntity<List<Article>> articles = articleApi.findArticlesByAuthor(AUTHOR_NAME);
		assertThat(articles.getBody().size(), Matchers.is(1));
		assertThat(articles.getStatusCode().value(),Matchers.is(200));
	}
	
	@Test
	public void testIfArticleIsNotProvided() {
		assertThat(articleApi.findArticleById(null).getStatusCode().value(),Matchers.is(400));
	}
	
	@Test
	public void testIfArticleIsNotPresent() {
		Optional<Article> article = Optional.empty();
		when(articleService.findArticleById(1L)).thenReturn(article);
		assertThat(articleApi.findArticleById(1L).getStatusCode().value(),Matchers.is(404));
	}
	
	@Test
	public void testIfArticleIfPresent() {
		Article article = Article.builder().id(1L)
    			.header("TestHeader")
    			.shortDescription("short description")
    			.text("Article Text")
    			.publishDate(new Date())
    			.build();
		Optional<Article> articleDetails = Optional.of(article);
		when(articleService.findArticleById(1L)).thenReturn(articleDetails);
		assertThat(articleApi.findArticleById(1L).getStatusCode().value(), Matchers.is(200));
	}
	
	@Test
	public void testIfSaveArticleIsWorking() throws IOException {
		Set<String> keywords = new HashSet<>();
		keywords.add("tag1");
		Set<String> authors = new HashSet<>();
		authors.add("Author 1");
		Article article = Article.builder()
				.header("SampleHeader")
				.shortDescription("SampleDesc")
				.publishDate(new Date())
				.text("Sample Text")
				.keywords(keywords)
				.authors(authors)
				.build();
		Article savedArticle = Article.builder()
				.id(1L)
				.header("SampleHeader")
				.shortDescription("SampleDesc")
				.publishDate(new Date())
				.text("Sample Text")
				.keywords(keywords)
				.authors(authors)
				.build();
		when(articleService.saveArticle(article)).thenReturn(savedArticle);
		assertThat(articleApi.createArticle(article).getBody().getId(), Matchers.is(1L));
	}
	
	@Test
	public void testIfArticleIsUpdatedForUnknownId() {
		Optional<Article> article = Optional.empty();	
		Article updated = Article.builder().build();
		when(articleService.findArticleById(1L)).thenReturn(article);
		assertThat(articleApi.updateArticle(1L,updated).getStatusCode().value(), Matchers.is(404));
	}
	
	@Test
	public void testIfArticleIsUpdatedForAGivenId() {
		Article article = Article.builder().id(1L).header("Header1").build();
		Optional<Article> existingArticle = Optional.of(article);	
		Article updated = Article.builder().id(1L).header("Header").build();
		when(articleService.findArticleById(1L)).thenReturn(existingArticle);
		when(articleService.saveArticle(article)).thenReturn(updated);
		ResponseEntity<Article> updatedArticle = articleApi.updateArticle(1L,article);
		assertThat(updatedArticle.getStatusCode().value(), Matchers.is(200));
		assertThat(updatedArticle.getBody().getHeader(),Matchers.is("Header"));
	}
	
	private List<Article> getListOfArticlesForTest(){
		Set<String> keywords = new HashSet<>();
		keywords.add("CWC2019");
		Article article = Article.builder().id(1L)
    			.header("Header")
    			.shortDescription("short description")
    			.text("Text")
    			.keywords(keywords)
    			.publishDate(new Date())
    			.build();
		List<Article> articleList = new ArrayList<>();
		articleList.add(article);
		return articleList;
	}
}
