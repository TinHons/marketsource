/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.ejb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Part;
import marketsource.entity.Address;
import marketsource.entity.Article;
import marketsource.entity.Company;
import marketsource.entity.CompanyProfile;
import marketsource.entity.Dataproperty;
import marketsource.entity.ExternalArticle;
import marketsource.entity.InternalArticle;
import marketsource.entity.Latestcurrency;
import marketsource.entity.Person;
import marketsource.entity.Position;
import marketsource.entity.Trade;
import marketsource.entity.ZseCompanyProfile;
import marketsource.entity.Zsemarketindices;
import marketsource.entity.Zseprocesseddate;

/**
 * A bean to handle administrative functions for the application
 *
 * @author Titanium
 */
@Stateless
public class AdminBean {

    @PersistenceContext
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void addCompany(String name, String ISIN, String symbol) {

        System.out.println("From AdminBean: " + name + " " + ISIN + " " + symbol);

        Company c = new Company();
        c.setCompanyName(name);
        c.setIsin(ISIN);
        c.setSymbol(symbol);
        em.persist(c);

    }

    public void uploadDailySheet(Part uploadedFile) {

        try (InputStream is = uploadedFile.getInputStream();) {

//            String text = new Scanner(is).useDelimiter("\\A").next();
//            System.out.println(text);
            String separator = System.getProperty("line.separator");

            Scanner s = new Scanner(is).useDelimiter(separator);
            //String text = s.next();
            ArrayList<String> list = new ArrayList<String>();
            list.ensureCapacity(137);
            while (s.hasNext()) {
                list.add(s.next());

            }
            System.out.println(list.size());

            updateStatistics(list);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateStatistics(ArrayList<String> list) {
        ArrayList<Trade> tradesList = new ArrayList<Trade>();
        String record = null;

        //get the date of these records
        String dateRecord = list.get(7);
        String[] dateRecordArray = dateRecord.split("\\t");
        String dateString = dateRecordArray[17];
        String[] dateStringArray = dateString.split("/");
        int day = Integer.valueOf(dateStringArray[0]);
        int month = Integer.valueOf(dateStringArray[1]);
        int year = Integer.valueOf(dateStringArray[2]);
        System.out.println("The date is :" + day + " " + month + " " + year);
        //System.out.println(ZoneId.getAvailableZoneIds());
        //Time used is arbitrarily set to midday
        LocalDate date1 = LocalDate.of(year, month, day);
        Date date = Date.valueOf(date1);
        System.out.println(date);

        for (int i = 0; i < list.size(); i++) {
            record = list.get(i);
            System.out.println("RECORD: " + record);
            String[] values = record.split("\\t");

            System.out.println("SIZE OF VALUE: " + values.length + "INDEX: " + i);
            //Logging printout
            for (int n = 0; n < values.length; n++) {
                System.out.println(values[n] + "----" + n);
            }

            //skip over rows which do not represent trades after
            if (i > 17 && values.length == 0) {
                continue;
            }
            try{
            if (i > 17 && values[1].toString().trim().equals("")) {
                continue;
            }}catch(ArrayIndexOutOfBoundsException e){continue;}
            if (i > 17) {
                System.out.println(record);
                //print out values array for checking. Replace with logging in future
                for (int n = 0; n < values.length; n++) {
                    System.out.println(values[n] + " " + n);
                }

                String symbol = values[9];

                String closingPriceString = values[20].replaceAll(",", "");
                BigDecimal closingPrice = BigDecimal.valueOf(Double.valueOf(closingPriceString));

                String totalSharesTradedString = values[27].replaceAll(",", "");
                System.out.println("Just before the exception:  " + totalSharesTradedString);
                BigInteger totalSharesTraded = BigInteger.valueOf(Long.valueOf(totalSharesTradedString));

                String totalValueTradedString = values[30].replaceAll(",", "");
                BigDecimal totalValueTraded = BigDecimal.valueOf(Double.valueOf(totalValueTradedString));

                //Company company = new Company();
                //company.setSymbol(symbol);
                System.out.println("SYMBOL: " + symbol);
                Company company;
                try {
                    company = (Company) em.createNamedQuery("Company.findBySymbol").setParameter("symbol", symbol).getSingleResult();
                } catch (NoResultException e) {
                    System.out.println("A new counter has been detected in the source file.");
                    continue;
                }
                Trade trade = new Trade();
                trade.setCompany(company);
                trade.setDateTime(date);
                trade.setClosingPrice(closingPrice);
                trade.setTotalSharesTraded(totalSharesTraded);
                trade.setTotalValueTraded(totalValueTraded);

                System.out.println(trade);

                tradesList.add(trade);

                System.out.println(tradesList.size());

            }

        }

        for (Trade trade : tradesList) {
            em.persist(trade);
        }

        updateDatabaseMetaData(date1);

    }

    public void updateDatabaseMetaData(LocalDate date) {

        //persist date to ZSEprocesseddate table
        Date date1 = Date.valueOf(date);
        Zseprocesseddate processeddate = new Zseprocesseddate();
        processeddate.setProcessedDate(date1);
        em.persist(processeddate);

        //update dataproperty table
        Dataproperty property;
        LocalDate currentLatestDate;

        String dayOfMonthString;
        int dayOfMonth = date.getDayOfMonth();
        if (dayOfMonth < 10) {
            dayOfMonthString = "0" + dayOfMonth;
        } else {
            dayOfMonthString = "" + dayOfMonth;
        }

        Month month = date.getMonth();
        int year = date.getYear();
        String propertyDateString = dayOfMonthString + month + year;
        System.out.println(propertyDateString);

        try {
            property = (Dataproperty) em.createNamedQuery("Dataproperty.findByProperty").setParameter("property", "latestEntryDate").getSingleResult();
            String extractedDateString = property.getPropertyValue();
            int extractedDayOfMonth = Integer.valueOf(extractedDateString.substring(0, 2));
            String extractedMonth = extractedDateString.substring(2, extractedDateString.length() - 4);

            //match the extractedMonth value to an instance of the enum type Month
            Month[] monthArray = Month.values();
            String monthElementString;
            Month extractedMonthEnumElement = null;
            for (int i = 0; i < monthArray.length; i++) {

                monthElementString = monthArray[i].toString();
                System.out.println(monthElementString);
                if (monthElementString.equals(extractedMonth)) {
                    extractedMonthEnumElement = monthArray[i];
                    System.out.println(extractedMonthEnumElement);
                    break;
                }

            }

            int extractedYear = Integer.valueOf(extractedDateString.substring(extractedDateString.length() - 4, extractedDateString.length()));
            System.out.println("Extracted values: " + extractedDayOfMonth + " " + extractedMonth + " " + extractedYear);
            System.out.println(extractedMonthEnumElement);
            currentLatestDate = LocalDate.of(extractedYear, extractedMonthEnumElement, extractedDayOfMonth);
            System.out.println(currentLatestDate);

            //Set the value of property value to new date if it is later than stored date
            System.out.println("New Date: " + date);
            System.out.println("Old Date: " + currentLatestDate);
            if (date.isAfter(currentLatestDate)) {
                property.setPropertyValue(propertyDateString);
                em.persist(property);
            }

        } catch (NoResultException e) {
            System.out.println("There was no entry");
            //create an entry
            property = new Dataproperty();
            property.setProperty("latestEntryDate");
            property.setPropertyValue(propertyDateString);
            em.persist(property);
        }

    }

    public boolean isDateProcessed(Part uploadedFile) {
        boolean exists = false;

        try (InputStream is = uploadedFile.getInputStream();) {

//            String text = new Scanner(is).useDelimiter("\\A").next();
//            System.out.println(text);
            String separator = System.getProperty("line.separator");
            //System.out.println(separator + "SEPARATOR");
            Scanner s = new Scanner(is).useDelimiter(separator);
            //String text = s.next();
            ArrayList<String> list = new ArrayList<String>();
            list.ensureCapacity(137);
            while (s.hasNext()) {
                list.add(s.next());

            }
            System.out.println(list.size());

            //updateStatistics(list);
            String dateRecord = list.get(7);
            String[] dateRecordArray = dateRecord.split("\\t");
            String dateString = dateRecordArray[17];
            String[] dateStringArray = dateString.split("/");
            int day = Integer.valueOf(dateStringArray[0]);
            int month = Integer.valueOf(dateStringArray[1]);
            int year = Integer.valueOf(dateStringArray[2]);
            System.out.println("The date is :" + day + " " + month + " " + year);
            //System.out.println(ZoneId.getAvailableZoneIds());
            //Time used is arbitrarily set to midday
            LocalDate date1 = LocalDate.of(year, month, day);
            Date date = Date.valueOf(date1);
            System.out.println(date);

            //Extract dates stored in table Zseprocesseddate
            List processedDates = em.createNamedQuery("Zseprocesseddate.findAll").getResultList();

            Zseprocesseddate processedDate;
            Date processedDateValue;
            for (Object item : processedDates) {

                processedDate = (Zseprocesseddate) item;
                processedDateValue = processedDate.getProcessedDate();
                if (date.equals(processedDateValue)) {
                    exists = true;
                    break;

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return exists;
    }

    public void updateZSEIndices(Date date, double industrialIndex, double miningIndex, double allShareIndex, double top10Index) {

        System.out.println(date + " " + industrialIndex + " " + miningIndex);

        Zsemarketindices indices = new Zsemarketindices();
        indices.setDateOfTrade(date);
        indices.setIndustrialIndex(industrialIndex);
        indices.setMiningIndex(miningIndex);
        indices.setAllShareIndex(allShareIndex);
        indices.setTop10Index(top10Index);

        em.persist(indices);

    }

    public void updateCurrenciesData(String jsonData) {

        //delete all records currently in Latestcurrency table
        int deletedCount = em.createQuery("DELETE FROM Latestcurrency").executeUpdate();
        System.out.println("Number of records deleted: " + deletedCount);

        List<Latestcurrency> list = new ArrayList<Latestcurrency>();

        System.out.println("From Admin Bean: " + jsonData);

        JsonParser parser = Json.createParser(new StringReader(jsonData));

        JsonParser.Event event = null;

        //at Start_OBJECT
        System.out.println(parser.next().toString());
        
        //deprecation message key
        System.out.println(parser.next().toString());
        System.out.println(parser.getString());
        
        //deprecation message value
        System.out.println(parser.next().toString());
        System.out.println(parser.getString());

        //at KEY_NAME of key "base"
        System.out.println(parser.next().toString());
        System.out.println(parser.getString());
        //at VALUE_STRING i.e "USD"
        System.out.println(parser.next().toString());
        System.out.println(parser.getString());
        //at KEY_NAME of key "date"
        System.out.println(parser.next().toString());
        System.out.println(parser.getString());
        //at VALUE_STRING of key "date" ie the actual date
        System.out.println(parser.next().toString());
        String datey = parser.getString();
        String[] dateyArray = datey.split("-");
        int year = Integer.valueOf(dateyArray[0]);
        int month = Integer.valueOf(dateyArray[1]);
        int day = Integer.valueOf(dateyArray[2]);
        LocalDate datey_1 = LocalDate.of(year, month, day);
        Date datey_2 = Date.valueOf(datey_1);
        System.out.println(datey_1);
        //at KEY_NAME of key "rates"
        System.out.println(parser.next().toString());
        System.out.println(parser.getString());
        //at START_OBJECT
        parser.next();
        //
        String currency = null;
        BigDecimal rate;
        Latestcurrency currencyEntry;
        while (!(parser.next().toString().equals("END_OBJECT"))) {

            currency = parser.getString();
            parser.next();
            rate = parser.getBigDecimal();
            System.out.println(currency + " " + rate);
            currencyEntry = new Latestcurrency();
            currencyEntry.setDatey(datey_2);
            currencyEntry.setCurrency(currency);
            currencyEntry.setRate(rate);
            list.add(currencyEntry);

        }

        System.out.println(list);
        for (Latestcurrency row : list) {

            em.persist(row);

        }

    }

    public List<Company> retrieveZseCompaniesList() {

        List<Company> list = em.createNamedQuery("Company.findAll").getResultList();

        return list;
    }

    public void persistProfile(CompanyProfile profile) {
        try {

            em.persist(profile);
            //get persisted profile

            if (profile instanceof ZseCompanyProfile) {
                String profileName = profile.getName();
                CompanyProfile persitedProfile = (CompanyProfile) em.createNamedQuery("CompanyProfile.findByName")
                        .setParameter("name", profileName).getSingleResult();

                ZseCompanyProfile zseProfile = (ZseCompanyProfile) persitedProfile;
                //Extract company object. Note that the company object below is only characterised by the
                //company primary id "symbol" as generated in CompanyConverter.java. This object is used to extract the real company object
                //from the database
                Company company = zseProfile.getZseCompany();
                System.out.println(company);

                Company company_1 = (Company) em.createNamedQuery("Company.findBySymbol")
                        .setParameter("symbol", company.getSymbol()).getSingleResult();

                company_1.setProfile(zseProfile);
                em.persist(company_1);

            }
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        //em.persist(profile);
    }

    public List<CompanyProfile> retrieveCompanies() {

        List<CompanyProfile> list = em.createNamedQuery("CompanyProfile.findAll").getResultList();

        Collections.sort(list);

        return list;

    }

    public void updateCompanyProfile(CompanyProfile profile) {
        CompanyProfile storedProfile = em.find(CompanyProfile.class, profile.getRecordId());
        System.out.println(storedProfile.getRevenue());

        storedProfile.setCompanyOverview(profile.getCompanyOverview());
        storedProfile.setProducts(profile.getProducts());
        storedProfile.setRevenue(profile.getRevenue());
        storedProfile.setName(profile.getName());

        Address address = profile.getAddress();
        storedProfile.setAddress(address);

        if (profile instanceof ZseCompanyProfile) {
            ZseCompanyProfile storedProfile_1 = (ZseCompanyProfile) storedProfile;

            //get stored profile ZSE Company symbol which is the primary key in Company
            String storedProfileZseCompanySymbol = storedProfile_1.getZseCompany().getSymbol();

            //Get edited profile ZSE Company symbol
            String editedProfileZseCompanySymbol = ((ZseCompanyProfile) profile).getZseCompany().getSymbol();

            if (!storedProfileZseCompanySymbol.equals(editedProfileZseCompanySymbol)) {
                //nullify profile in current ZSE Company
                try {
                    Company currentCompany = (Company) em.createNamedQuery("Company.findBySymbol")
                            .setParameter("symbol", storedProfileZseCompanySymbol)
                            .getSingleResult();
                    currentCompany.setProfile(null);
                } catch (NoResultException e) {
                    e.printStackTrace();
                }

                //store new profile
                storedProfile_1.setZseCompany(((ZseCompanyProfile) profile).getZseCompany());
                em.persist(storedProfile_1);

                //retrieve the newly persited profile
                String profileName = storedProfile_1.getName();
                try {
                    CompanyProfile newlyPersisted = (CompanyProfile) em.createNamedQuery("CompanyProfile.findByName")
                            .setParameter("name", profileName)
                            .getSingleResult();

                    //set profile for the new company
                    Company newCompany = (Company) em.createNamedQuery("Company.findBySymbol")
                            .setParameter("symbol", editedProfileZseCompanySymbol)
                            .getSingleResult();
                    newCompany.setProfile((ZseCompanyProfile) newlyPersisted);
                } catch (NoResultException e) {
                    e.printStackTrace();
                }

            } else {

                em.persist(storedProfile);

            }

        } else {
            em.persist(storedProfile);
        }

    }

    public int deleteCompanyProfile(CompanyProfile profile) {

        if (profile instanceof ZseCompanyProfile) {

            String companySymbol = ((ZseCompanyProfile) profile).getZseCompany().getSymbol();
            int update = em.createQuery("UPDATE Company c SET c.profile = null WHERE c.symbol = :symbol")
                    .setParameter("symbol", companySymbol)
                    .executeUpdate();

        }

        Integer profileId = profile.getRecordId();

        return em.createQuery("DELETE FROM CompanyProfile p WHERE p.recordId = :profileId").setParameter("profileId", profileId).executeUpdate();

    }

    public void persistArticle(Article article, Part[] images, String[] captions) {

        em.persist(article);
        int id = article.getRecordId();
        System.out.println("The id is..." + article.getRecordId());

        Article retrievedArticle = (Article) em.createNamedQuery("Article.findByRecordId")
                .setParameter("recordId", id)
                .getSingleResult();

        int articleId = retrievedArticle.getRecordId();

        //write images to disk
        writeImages(images, captions, articleId);
        System.out.println(article);

    }

    public void writeImages(Part[] images, String[] captions, int articleId) {

        //final String path = "C:\\Users\\dell\\Desktop\\MarketData\\Images";
        final String path = "/opt/repo/home/marketsource/images";
        for (int i = 0; i <= 2; i++) {
            if (images[i] != null) {
                try (
                        InputStream inputStream = images[i].getInputStream();
                        FileOutputStream outputStream = new FileOutputStream(new File(path + File.separator
                                + articleId + "_image_" + (i + 1) + ".jpg"), false);
                        FileWriter writer = new FileWriter(path + File.separator
                                + articleId + "_caption_" + (i + 1) + ".txt", false);) {
                    System.out.println("Writing image....");
                    byte[] buffer = new byte[4096];
                    int bytesRead = 0;
                    while (true) {
                        bytesRead = inputStream.read(buffer);
                        if (bytesRead > 0) {
                            outputStream.write(buffer, 0, bytesRead);
                        } else {
                            break;
                        }
                    }

                    writer.write(captions[i]);
                    writer.write("\r\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public List<Article> retrieveArticles() {

        return em.createNamedQuery("Article.findAll").getResultList();

    }

    public void updateArticle(Article article, Part[] imageArray, String[] captionArray) {

        Article storedArticle = em.find(Article.class, article.getRecordId());

        storedArticle.setTitle(article.getTitle());
        storedArticle.setCartegory(article.getCartegory());

        //set publish time if the publish status is being changed to "PUBLISH" from another status
        //the NullPointerException occurs if storedArticle.getPublishStatus().equals("PUBLISH") is null
        try {
            if (!(storedArticle.getPublishStatus().equals("PUBLISH")) && article.getPublishStatus().equals("PUBLISH")) {

                LocalDateTime localDateTime = LocalDateTime.now();
                Timestamp dateTime = Timestamp.valueOf(localDateTime);
                storedArticle.setPublishDateTime(dateTime);

            }
        } catch (NullPointerException e) {

            if (article.getPublishStatus().equals("PUBLISH")) {
                LocalDateTime localDateTime = LocalDateTime.now();
                Timestamp dateTime = Timestamp.valueOf(localDateTime);
                storedArticle.setPublishDateTime(dateTime);
            }

        }

        storedArticle.setPublishStatus(article.getPublishStatus());

        storedArticle.setCaption(article.getCaption());

        if (article.getPriority().equals("HEADLINE")) {

            //invalidate currrent headline
            invalidateHeadline(article);
        }

        if (article.getPriority().equals("TOPSTORY")) {

            //invalidate current topstory
            invalidateTopStory(article);
        }

        storedArticle.setPriority(article.getPriority());

        if (article instanceof ExternalArticle) {

            ExternalArticle storedArticle_1 = (ExternalArticle) storedArticle;

            storedArticle_1.setUrl(((ExternalArticle) article).getUrl());

        } else {

            InternalArticle storedArticle_1 = (InternalArticle) storedArticle;

            storedArticle_1.setArticleText(((InternalArticle) article).getArticleText());

        }

        writeEditedImages(imageArray, captionArray, article.getRecordId());

    }

    public int deleteArticle(Article article) {

        Integer articleId = article.getRecordId();

        return em.createQuery("DELETE FROM Article a WHERE a.recordId = :recordId").setParameter("recordId", articleId).executeUpdate();

    }

    public void invalidateHeadline(Article article) {
        try {
            Article retrievedArticle = (Article) em.createNamedQuery("Article.findByPriority")
                    .setParameter("priority", "HEADLINE")
                    .getSingleResult();

            invalidateTopStory(article);
            retrievedArticle.setPriority("TOPSTORY");
        } catch (NoResultException e) {
        }

    }

    public void invalidateTopStory(Article article) {

        String cartegory = article.getCartegory();
        try {
            Article retrievedArticle = (Article) em.createQuery(
                    "SELECT a FROM Article a WHERE a.cartegory = :cart AND a.priority = :priority")
                    .setParameter("cart", cartegory).setParameter("priority", "TOPSTORY")
                    .getSingleResult();

            retrievedArticle.setPriority("ORDINARY");

        } catch (NoResultException e) {

        }

    }

    public void writeEditedImages(Part[] images, String[] captions, int articleId) {

        //final String path = "C:\\Users\\dell\\Desktop\\MarketData\\Images";
        final String path = "/opt/repo/home/marketsource/images";
        for (int i = 0; i <= 2; i++) {
            if (images[i] != null) {
                try (
                        InputStream inputStream = images[i].getInputStream();
                        FileOutputStream outputStream = new FileOutputStream(new File(path + File.separator
                                + articleId + "_image_" + (i + 1) + ".jpg"), false);) {
                    System.out.println("Writing image....");
                    byte[] buffer = new byte[4096];
                    int bytesRead = 0;
                    while (true) {
                        bytesRead = inputStream.read(buffer);
                        if (bytesRead > 0) {
                            outputStream.write(buffer, 0, bytesRead);
                        } else {
                            break;
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        for (int i = 0; i <= 2; i++) {
            if (!captions[i].equals("")) {
                try (FileWriter writer = new FileWriter(path + File.separator
                        + articleId + "_caption_" + (i + 1) + ".txt", false);) {
                    System.out.println("Writing image....");

                    writer.write(captions[i]);
                    writer.write("\r\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public String retrieveCaption(String fileName) {
        //String basePath = "C:\\Users\\dell\\Desktop\\MarketData\\Images";
        String basePath = "/opt/repo/home/marketsource/images";
        File file = new File(basePath + File.separator + fileName);
        String caption = null;
        if (file.exists()) {

            try {
                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);
                caption = bufferedReader.readLine();
                return caption;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            return null;
        }

    }

    public void persistPerson(String firstName, String secondName, String surname) {

        Person person = new Person();
        person.setFirstName(firstName);

        if (secondName != null) {
            person.setSecondName(secondName);

        }

        person.setSurname(surname);
        em.persist(person);

    }

    public void updatePerson(Person updatedPerson) {

        Person storedPerson = (Person) em.createNamedQuery("Person.findByPersonId")
                .setParameter("personId", updatedPerson.getPersonId())
                .getSingleResult();

        storedPerson.setFirstName(updatedPerson.getFirstName());
        storedPerson.setSurname(updatedPerson.getSurname());
        if (updatedPerson.getSecondName() != null) {
            storedPerson.setSecondName(updatedPerson.getSecondName());
        }

    }

    public List<Person> retrievePersons() {

        return em.createNamedQuery("Person.findAll").getResultList();

    }

    public boolean persistPosition(Position position) {

        Person person = position.getPerson();
        CompanyProfile profile = position.getProfile();

        List<Position> testList = em.createQuery("SELECT p FROM Position p WHERE p.profile = :profile AND p.person = :person")
                .setParameter("profile", profile)
                .setParameter("person", person)
                .getResultList();
        if (testList.size() > 0) {
            return false;
        }

        em.persist(position);
        return true;

    }

    public List<Position> retrieveProfilePositions(Integer id) {

        //Integer id = profile.getRecordId();
        return em.createQuery(
                "SELECT p FROM Position p WHERE p.profile.recordId = :profileId")
                .setParameter("profileId", id)
                .getResultList();

    }

    public int deletePosition(Integer positionId) {

        return em.createQuery("DELETE FROM Position p WHERE p.record_id = :recordId").setParameter("recordId", positionId).executeUpdate();
    }

    public int addPeer(String companySymbol, String addedPeerSymbol) {
        Company company = (Company) em.createNamedQuery("Company.findBySymbol")
                .setParameter("symbol", companySymbol)
                .getSingleResult();

        Company peer = (Company) em.createNamedQuery("Company.findBySymbol")
                .setParameter("symbol", addedPeerSymbol)
                .getSingleResult();

        Collection<Company> currentPeers = company.getPeers();

        currentPeers.add(peer);

        company.setPeers(currentPeers);

        return 1;
    }

    public void uploadAnyFile(Part uploadedFile) {

        try (InputStream is = uploadedFile.getInputStream();) {

//            String text = new Scanner(is).useDelimiter("\\A").next();
//            System.out.println(text);
            String separator = System.getProperty("line.separator");

            Scanner s = new Scanner(is).useDelimiter(separator);
            //String text = s.next();
            ArrayList<String> list = new ArrayList<String>();
            list.ensureCapacity(365);
            while (s.hasNext()) {
                list.add(s.next());

            }
            
            System.out.println(list.size());

            Consumer<String> processData = p -> {
                
                String[] dataArray = p.split(",");
                String dateString = dataArray[0].trim();
                String[] dateArray = dateString.split("/");
                int day = Integer.valueOf(dateArray[1].trim());
                int month = Integer.valueOf(dateArray[0].trim());
                int year = Integer.valueOf(dateArray[2].trim());
                
                double indIndex = Double.valueOf(dataArray[1].trim());
                double minIndex = Double.valueOf(dataArray[2].trim());
                
                LocalDate dateOfTrade = LocalDate.of(year, month, day);
                Date finalDate = Date.valueOf(dateOfTrade);
                
                Zsemarketindices indices = new Zsemarketindices();
                indices.setDateOfTrade(finalDate);
                indices.setIndustrialIndex(indIndex);
                indices.setMiningIndex(minIndex);
                
                System.out.println(indices);
               em.persist(indices);
                
            };
            
            list.forEach(processData);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
