/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import marketsource.ejb.BusinessBean;
import marketsource.entity.Article;
import marketsource.entity.ExternalArticle;
import marketsource.entity.InternalArticle;

/**
 *
 * @author dell
 */
@Named(value = "newsBean")
@SessionScoped
public class NewsBean implements Serializable {

    @EJB
    private BusinessBean businessBean;

    private List<Article> newsList;

    private List<Article> topNews;

    private Article headline;

    private String headlineImage;

    private Integer articleId;

    private InternalArticle displayedArticle;

    /**
     * Creates a new instance of NewsBean
     */
    public NewsBean() {

    }

    public List<Article> getTopNews() {
        return topNews;
    }

    public void setTopNews(List<Article> topNews) {
        this.topNews = topNews;
    }

    public List<Article> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<Article> newsList) {
        this.newsList = newsList;
    }

    public Article getHeadline() {
        return headline;
    }

    public void setHeadline(Article headline) {
        this.headline = headline;
    }

    public String getHeadlineImage() {
        return headlineImage;
    }

    public void setHeadlineImage(String headlineImage) {
        this.headlineImage = headlineImage;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public InternalArticle getDisplayedArticle() {
        return displayedArticle;
    }

    public void setDisplayedArticle(InternalArticle displayedArticle) {
        this.displayedArticle = displayedArticle;
    }

    public void retrieveNews() {

        setNewsList(businessBean.retrieveNews());
        System.out.println("Size of news: " + newsList.size());
        retrieveTopNews();
    }

    public void retrieveTopNews() {

        List<Article> list = new ArrayList<Article>();
        for (Article article : newsList) {
            System.out.println(article);
            if (article.getPriority() != null && article.getPublishStatus() != null) {
                if (article.getPriority().equals("TOPNEWS") && article.getPublishStatus().equals("PUBLISH")) {

                    list.add(article);

                }

                if (article.getPriority().equals("HEADLINE") && article.getPublishStatus().equals("PUBLISH")) {

                    setHeadline(article);
                    setHeadlineImage(headline.getRecordId() + "_image_1.jpg");
                    continue;
                }

            }

        }
        Collections.sort(list);
        Collections.reverse(list);
        setTopNews(list);
        System.out.println("Size of top news: " + topNews.size());
    }

    public void displayArticle() {

        Article article = businessBean.retrieveArticle(articleId);

        if (article instanceof ExternalArticle) {

            String url = ((ExternalArticle) article).getUrl();
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            setDisplayedArticle((InternalArticle) article);

        }

    }

    public String retrieveImageCaption(Article article) {

        return businessBean.retrieveImageCaption(article.getRecordId() + "_caption_1.txt");

    }

}
