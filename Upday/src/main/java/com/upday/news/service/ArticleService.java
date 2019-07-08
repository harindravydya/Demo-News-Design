package com.upday.news.service;

import com.upday.news.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
	List<Article> findAllArticlesByAuthor(String author);

	List<Article> findAllArticlesForAGivenPeriod();

	List<Article> findAllArticlesByKeywords(List<String> keywords);

	Optional<Article> findArticleById(Long id);

	Article saveArticle(Article article);
	
	void deleteArticleById(Long id);
}
