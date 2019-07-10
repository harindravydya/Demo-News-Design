package com.upday.news.service;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.upday.news.model.Article;

public interface ArticleService {
	List<Article> findAllArticlesByAuthor(String author);

	List<Article> findAllArticlesForAGivenPeriod(Date from,Date to);

	List<Article> findAllArticlesByKeywords(Set<String> keywords);

	Optional<Article> findArticleById(Long id);

	Article saveArticle(Article article);
	
	void deleteArticleById(Long id);
}
