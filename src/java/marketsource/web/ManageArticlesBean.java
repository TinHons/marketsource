/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.Part;
import marketsource.ejb.AdminBean;
import marketsource.entity.Article;
import marketsource.entity.ExternalArticle;
import marketsource.entity.InternalArticle;
import marketsource.util.ArticleCartegoryEnum;
import marketsource.util.ArticlePriorityEnum;
import marketsource.util.ArticleStatusEnum;

/**
 *
 * @author dell
 */
@Named(value = "manageArticlesBean")
@SessionScoped
public class ManageArticlesBean implements Serializable {

    @EJB
    private AdminBean adminBean;

    private String selectedCartegory = "ALL";

    private List<Article> articles;

    private List<Article> selectedArticles;

    private Article article;

    private ArticleCartegoryEnum cartegory;

    private String url;
    
    private String articleText;
    
    private String currentImage1;
    
    private String currentImage2 ;
    
    private String currentImage3;

    
    private Part image1;

    private Part image2;

    private Part image3;

    private String caption1;

    private String caption2;

    private String caption3;
    
    

    /**
     * Creates a new instance of ManageArticlesBean
     */
    public ManageArticlesBean() {
    }

    public String getSelectedCartegory() {
        return selectedCartegory;
    }

    public void setSelectedCartegory(String selectedCartegory) {
        this.selectedCartegory = selectedCartegory;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Article> getSelectedArticles() {
        return selectedArticles;
    }

    public void setSelectedArticles(List<Article> selectedArticles) {
        this.selectedArticles = selectedArticles;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArticleCartegoryEnum getCartegory() {
        return cartegory;
    }

    public void setCartegory(ArticleCartegoryEnum cartegory) {
        this.cartegory = cartegory;
    }

    public Part getImage1() {
        return image1;
    }

    public void setImage1(Part image1) {
        this.image1 = image1;
    }

    public Part getImage2() {
        return image2;
    }

    public void setImage2(Part image2) {
        this.image2 = image2;
    }

    public Part getImage3() {
        return image3;
    }

    public void setImage3(Part image3) {
        this.image3 = image3;
    }

    public String getCaption1() {
        return caption1;
    }

    public void setCaption1(String caption1) {
        this.caption1 = caption1;
    }

    public String getCaption2() {
        return caption2;
    }

    public void setCaption2(String caption2) {
        this.caption2 = caption2;
    }

    public String getCaption3() {
        return caption3;
    }

    public void setCaption3(String caption3) {
        this.caption3 = caption3;
    }

    public String getCurrentImage1() {
        return currentImage1;
    }

    public void setCurrentImage1(String currentImage1) {
        this.currentImage1 = currentImage1;
    }

    public String getCurrentImage2() {
        return currentImage2;
    }

    public void setCurrentImage2(String currentImage2) {
        this.currentImage2 = currentImage2;
    }

    public String getCurrentImage3() {
        return currentImage3;
    }

    public void setCurrentImage3(String currentImage3) {
        this.currentImage3 = currentImage3;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }
    
    

    

   

    

    

    
    
    
    
    

    public void extractUrl() {

        setUrl(((ExternalArticle) article).getUrl());

    }
    
    public void extractArticleText() {

        setArticleText(((InternalArticle) article).getArticleText());

    }

    public ArticleCartegoryEnum[] getBasicCartegoryValues() {

        return ArticleCartegoryEnum.values();

    }

    public ArticleStatusEnum[] getStatusValues() {

        return ArticleStatusEnum.values();

    }

    public ArticlePriorityEnum[] getPriorityValues() {

        return ArticlePriorityEnum.values();
    }

    public void retrieveArticles() {

        setArticles(adminBean.retrieveArticles());

    }

    public String[] getCartegoryValues() {

        List<String> cartegoryValues = new ArrayList<String>();

        ArticleCartegoryEnum[] enumValues = ArticleCartegoryEnum.values();

        for (ArticleCartegoryEnum enumValue : enumValues) {
            cartegoryValues.add(enumValue.toString());

        }

        cartegoryValues.add("ALL");

        Object[] cartegoryValuesArray = cartegoryValues.toArray();

        String[] stringArray = Arrays.copyOf(cartegoryValuesArray, cartegoryValuesArray.length, String[].class);
        System.out.println("Printing array..." + stringArray);
        return stringArray;
    }

    public void retrieveSelectedArticles(String selectedCartegory) {
        System.out.println("Retrieving selected articles.....");
        if (selectedCartegory.equals("ALL")) {
            selectedArticles = articles;
        } else {

            //ArticleCartegoryEnum cartEnum = ArticleCartegoryEnum.valueOf(selectedCartegory);
            selectedArticles.removeAll(selectedArticles);

            for (Article articleElement : articles) {

                String cartegory = articleElement.getCartegory();
                if (cartegory.equals(selectedCartegory)) {
                    selectedArticles.add(articleElement);

                }

            }

            setSelectedArticles(selectedArticles);
            System.out.println(selectedArticles.size());
        }

    }

    public void onDateButtonClick() {
    }

    public String retrieveArticleInfo(Article article) {
        
        String returnValue;

        setArticle(article);
        if(article instanceof ExternalArticle){
        extractUrl();
        returnValue = "editExternal";
        }
        else {
        extractArticleText();
       returnValue = "editInternal";
        }
        System.out.println(this.article);
        
        setCurrentImage1(article.getRecordId() + "_image_1.jpg");
        setCurrentImage2(article.getRecordId() + "_image_2.jpg");
        setCurrentImage3(article.getRecordId() + "_image_3.jpg");
        
        setCaption1(adminBean.retrieveCaption(article.getRecordId() + "_caption_1.txt"));
        setCaption2(adminBean.retrieveCaption(article.getRecordId() + "_caption_2.txt"));
        setCaption3(adminBean.retrieveCaption(article.getRecordId() + "_caption_3.txt"));
        
        return returnValue;
    }

    public String updateArticle() {

        System.out.println(article);
        
       
        
        
        if (article instanceof ExternalArticle) {
            ((ExternalArticle) article).setUrl(url);

        }
        else{
        
         ((InternalArticle) article).setArticleText(articleText);
        
        }
        
        Part[] imageArray = {image1, image2, image3};
        
        String[] captionArray = {caption1, caption2, caption3};
        
        adminBean.updateArticle(article, imageArray, captionArray);
        return "success";
    }
    
    public void deleteArticle(Article article){
    
    adminBean.deleteArticle(article);
    
    }

}
