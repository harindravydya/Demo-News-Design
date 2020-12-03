package com.enews.news.service;



import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.enews.news.model.Article;
import com.enews.news.repository.ArticleRepository;
import com.enews.news.service.ArticleServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {


	@Mock
	private ArticleRepository articleRepository;
	
	
	
	@InjectMocks
	private ArticleServiceImpl articleService;

   
    @Test
    public void testIfMockedKeywordsWillReturnArticles() {
    	Set<String> keywords = new HashSet<>();
    	keywords.add("Brand Builing");
    	keywords.add("Enterpreneur");
    	Set<String> keywordSet = new HashSet<>();
    	keywordSet.add("Brand Builing");
    	when(articleRepository.findAllArticlesByKeywords(keywords)).thenReturn(testArticleList());
    	List<Article> finalArticleList = articleService.findAllArticlesByKeywords(keywords);
    	assertThat(finalArticleList.size(), Matchers.is(1));
    	assertThat(finalArticleList.get(0).getKeywords().size(), Matchers.is (1));
    }
    
    @Test
    public void testIfNoKeywordsAreGiven() {
    	assertThat(articleService.findAllArticlesByKeywords(null).size(), Matchers.is(0));
    }
    
    @Test
    public void testIfNoArticlesArePresentForGivenKeywords() {
    	Set<String> keywords = new HashSet<>();
    	keywords.add("KeyX");
    	when(articleRepository.findAllArticlesByKeywords(keywords)).thenReturn(new ArrayList<>());
    	assertThat(articleService.findAllArticlesByKeywords(keywords).size(),Matchers.is(0));
    }
    
    @Test
    public void testForNoAuthorsAreGiven() {
    	assertThat(articleService.findAllArticlesByAuthor(null).size(), Matchers.is(0));
    }
    
    @Test
    public void testIfNotArticlesArePresentForGivenAuthors() {
    	when(articleRepository.findAllArticlesByAuthorName("AuthorX")).thenReturn(new ArrayList<>());
    	assertThat(articleService.findAllArticlesByAuthor("AuthorX").size(), Matchers.is(0));
    }
    
    @Test
    public void testIfArticlesAreRetrievedForAuthorName() {
    	when(articleRepository.findAllArticlesByAuthorName("John")).thenReturn(testArticleList());
    	List<Article> article = articleService.findAllArticlesByAuthor("John");
    	assertThat(article.get(0).getId(), Matchers.is(1L));
    }
    
    @Test
    public void verifyIfArticleSaved() {
    	Article article = Article.builder().header("testHeader").build();
    	Article savedArticle = Article.builder().header("testHeader").id(1L).build();
    	when(articleService.saveArticle(article)).thenReturn(savedArticle);
    	articleService.saveArticle(article);
    	assertThat(savedArticle.getHeader(), Matchers.is(article.getHeader()));
    	assertThat(savedArticle.getId(), Matchers.is(1L));
    	verify(articleRepository).save(article);
    }
    
    private List<Article> testArticleList(){
    	List<String> keywords = new ArrayList<>();
    	keywords.add("Brand Builing");
    	keywords.add("Enterpreneur");
    	List<Article> articles = new ArrayList<>();
    	Set<String> keywordSet = new HashSet<>();
    	keywordSet.add("Brand Builing");
    	Set<String> authors = new HashSet<>();
    	authors.add("John");
    	Article article = Article.builder()
    			.id(1L)
    			.header("Header")
    			.shortDescription("short description")
    			.text("Text")
    			.keywords(keywordSet)
    			.publishDate(new Date())
    			.authors(authors)
    			.build();
    	articles.add(article);
    	return articles;
    }
    
}
