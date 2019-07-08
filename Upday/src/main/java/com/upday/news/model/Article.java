package com.upday.news.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String header;
    private String shortDescription;
    private String text;
    
    @Temporal(TemporalType.DATE)
    private Date publishDate;
    
    @ElementCollection
    @Builder.Default
    private Set<String> keywords = new HashSet<>();
    
    @ElementCollection
    @Builder.Default
    private Set<String> authors = new HashSet<>();
    	
}
