/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.ejb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import marketsource.entity.Article;
import marketsource.entity.Company;
import marketsource.entity.CompanyProfile;
import marketsource.entity.Currencydetail;
import marketsource.entity.Dataproperty;
import marketsource.entity.Latestcurrency;
import marketsource.entity.Person;
import marketsource.entity.Position;
import marketsource.entity.Trade;
import marketsource.entity.Zsemarketindices;
import marketsource.util.CurrencySheet;
import marketsource.util.IndicesSheet;
import marketsource.util.PriceSheetTrade;

/**
 *
 * @author dell
 */
@Stateless
public class BusinessBean {

    @PersistenceContext
    private EntityManager em;

    public List<PriceSheetTrade> retrieveDailyTradeStatistics(Date date) {

        Date latestTradingDate = date;

        Date previousTradingDate = new Date(0);

        //find the previousTradingDate ie the last day of trade before the latestTradingDate
        List<Trade> allTradesList
                = em.createNamedQuery("Trade.findAll").getResultList();

        Date tradeDate;
        for (Trade trade : allTradesList) {

            tradeDate = trade.getDateTime();
            if (tradeDate.after(previousTradingDate)) {
                if (tradeDate.equals(latestTradingDate) || tradeDate.after(latestTradingDate)) {
                    continue;
                }

                previousTradingDate = tradeDate;

            }

        }

        System.out.println(previousTradingDate.toLocalDate());

        //Now retrieve trades for  the found previusTradingDate
        List<Trade> previousDateList
                = em.createNamedQuery("Trade.findByDateTime")
                .setParameter("dateTime", previousTradingDate)
                .getResultList();
        System.out.println("Latest Date " + latestTradingDate.toLocalDate());
        System.out.println("Previous Date: " + previousTradingDate.toLocalDate());
        System.out.println("Previous Date List: " + previousDateList);
        //Retrieve data for latest trading date
        List<Trade> latestDateList
                = em.createNamedQuery("Trade.findByDateTime")
                .setParameter("dateTime", date)
                .getResultList();

//Now generate price sheet list
        List<PriceSheetTrade> priceSheetTradeList = new ArrayList<PriceSheetTrade>();
        Company company;
        BigDecimal previousSharePrice;
        BigDecimal latestSharePrice;
        int directionIndicator = 0;
        BigDecimal changePercentage1 = new BigDecimal(0);
        BigDecimal changePercentage = new BigDecimal(0);
        BigDecimal change = new BigDecimal(0);
        for (Trade trade : latestDateList) {

            company = trade.getCompany();
            latestSharePrice = trade.getClosingPrice();
            for (Trade previousTrade : previousDateList) {

                if (company.equals(previousTrade.getCompany())) {
                    previousSharePrice = previousTrade.getClosingPrice();
                    System.out.println(company + " previous close: " + previousSharePrice);
                    System.out.println("PreviousSharPRice: " + previousSharePrice);
                    System.out.println(company + " current close: " + latestSharePrice);
                    directionIndicator = latestSharePrice.compareTo(previousSharePrice);
                    change = latestSharePrice.subtract(previousSharePrice);
                    System.out.println(company + " change: " + change);
                    changePercentage1 = change.divide(previousSharePrice, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
                    //changePercentage = changePercentage1.setScale(2, RoundingMode.HALF_UP);
                    changePercentage = changePercentage1;
                    System.out.println(company + " change percentage: " + changePercentage);
                    break;

                }

            }

            PriceSheetTrade priceSheetTrade = new PriceSheetTrade();
            priceSheetTrade.setClosingPrice(latestSharePrice);
            priceSheetTrade.setTotalSharesTraded(trade.getTotalSharesTraded());
            priceSheetTrade.setTotalValueTraded(trade.getTotalValueTraded());
            priceSheetTrade.setChange(changePercentage);
            priceSheetTrade.setAbsoluteChange(change);
            priceSheetTrade.setDirectionIndicator(directionIndicator);
            priceSheetTrade.setCompany(company);

            priceSheetTradeList.add(priceSheetTrade);

        }

        System.out.println(priceSheetTradeList);

        return priceSheetTradeList;
    }

    public IndicesSheet retrieveDailyIndicesStatistics(Date date) {

        IndicesSheet sheet = null;

        Date latestTradingDate = date;

        Date previousTradingDate = new Date(0);

        List<Zsemarketindices> allIndicesList
                = em.createNamedQuery("Zsemarketindices.findAll").getResultList();

        Date tradeDate;
        for (Zsemarketindices indicesObject : allIndicesList) {

            tradeDate = indicesObject.getDateOfTrade();
            if (tradeDate.after(previousTradingDate)) {
                if (tradeDate.equals(latestTradingDate) || tradeDate.after(latestTradingDate)) {
                    continue;
                }

                previousTradingDate = tradeDate;

            }

        }

        System.out.println(previousTradingDate.toLocalDate());

        //retrive current date list. In reality only one object is retrieved since there is one object per day. So its a list of one. The list is used since the display mechanism, dtaTable works easily with a list
//        List<Zsemarketindices> latestDateList
//                = em.createNamedQuery("Zsemarketindices.findByDateOfTrade")
//                .setParameter("dateOfTrade", date)
//                .getResultList();
        //Retrieve previous day object
        Zsemarketindices latestDateIndices = null;
        Zsemarketindices previousDateIndices = null;

        try {

            latestDateIndices = (Zsemarketindices) em.createNamedQuery("Zsemarketindices.findByDateOfTrade")
                    .setParameter("dateOfTrade", date).getSingleResult();

        } catch (NoResultException e) {

            return null;

        }

        try {
            System.out.println("Indices Day Of Trade: " + date.toLocalDate());

            previousDateIndices = (Zsemarketindices) em.createNamedQuery("Zsemarketindices.findByDateOfTrade")
                    .setParameter("dateOfTrade", previousTradingDate).getSingleResult();

            //Industrial Index values
            Double latestIndustrialIndex = latestDateIndices.getIndustrialIndex();
            Double previousIndustrialIndex = previousDateIndices.getIndustrialIndex();

            Double industrialIndexChange = latestIndustrialIndex - previousIndustrialIndex;
            Double industrialIndexPercentageChange = industrialIndexChange / previousIndustrialIndex * 100;

            int industrialDirectionIndicator = latestIndustrialIndex.compareTo(previousIndustrialIndex);

//Mining Index values
            Double latestMiningIndex = latestDateIndices.getMiningIndex();
            Double previousMiningIndex = previousDateIndices.getMiningIndex();

            Double miningIndexChange = latestMiningIndex - previousMiningIndex;
            Double miningIndexPercentageChange = miningIndexChange / previousMiningIndex * 100;

            int miningDirectionIndicator = latestMiningIndex.compareTo(previousMiningIndex);

            //All Share Index Values
            Double latestAllShareIndex;
            Double previousAllShareIndex;

            Double allShareIndexChange;
            Double allShareIndexPercentageChange;

            int allShareDirectionIndicator;

            try {
                latestAllShareIndex = latestDateIndices.getAllShareIndex();
                previousAllShareIndex = previousDateIndices.getAllShareIndex();

                allShareIndexChange = latestAllShareIndex - previousAllShareIndex;
                allShareIndexPercentageChange = allShareIndexChange / previousAllShareIndex * 100;

                allShareDirectionIndicator = latestAllShareIndex.compareTo(previousAllShareIndex);
            } catch (NullPointerException e) {
                latestAllShareIndex = 0.0;
                previousAllShareIndex = 0.0;

                allShareIndexChange = 0.0;
                allShareIndexPercentageChange = 0.0;

                allShareDirectionIndicator = 0;

            }

            //Top Ten Index Values
            Double latestTop10Index;
            Double previousTop10Index;

            Double top10IndexChange;

            Double top10IndexPercentageChange;

            int top10DirectionIndicator;

            try {
                latestTop10Index = latestDateIndices.getTop10Index();
                previousTop10Index = previousDateIndices.getTop10Index();

                top10IndexChange = latestTop10Index - previousTop10Index;

                top10IndexPercentageChange = top10IndexChange / previousTop10Index * 100;

                top10DirectionIndicator = latestTop10Index.compareTo(previousTop10Index);
            } catch (NullPointerException e) {
                latestTop10Index = 0.0;
                previousTop10Index = 0.0;

                top10IndexChange = 0.0;

                top10IndexPercentageChange = 0.0;

                top10DirectionIndicator = 0;

            }

            sheet = new IndicesSheet();
            sheet.setDateOfTrade(date);

            sheet.setIndustrialIndex(latestIndustrialIndex);
            sheet.setMiningIndex(latestMiningIndex);
            sheet.setAllShareIndex(latestAllShareIndex);
            sheet.setTop10Index(latestTop10Index);

            sheet.setIndustrialIndexChange(industrialIndexPercentageChange);
            sheet.setMiningIndexChange(miningIndexPercentageChange);
            sheet.setAllShareIndexChange(allShareIndexPercentageChange);
            sheet.setTop10IndexChange(top10IndexPercentageChange);

            sheet.setIndustrialDirectionIndicator(industrialDirectionIndicator);
            sheet.setMiningDirectionIndicator(miningDirectionIndicator);
            sheet.setAllShareDirectionIndicator(allShareDirectionIndicator);
            sheet.setTop10DirectionIndicator(top10DirectionIndicator);

        } catch (NoResultException e) {

            Double latestIndustrialIndex = latestDateIndices.getIndustrialIndex();

            Double latestMiningIndex = latestDateIndices.getMiningIndex();

            Double latestAllShareIndex = latestDateIndices.getAllShareIndex();

            Double latestTop10Index = latestDateIndices.getTop10Index();

            sheet = new IndicesSheet();
            sheet.setDateOfTrade(date);

            sheet.setIndustrialIndex(latestIndustrialIndex);
            sheet.setMiningIndex(latestMiningIndex);
            sheet.setAllShareIndex(latestAllShareIndex);
            sheet.setTop10Index(latestTop10Index);

            sheet.setIndustrialIndexChange(null);
            sheet.setMiningIndexChange(null);
            sheet.setAllShareIndexChange(null);
            sheet.setTop10IndexChange(null);

            sheet.setIndustrialDirectionIndicator(0);
            sheet.setMiningDirectionIndicator(0);
            sheet.setAllShareDirectionIndicator(0);
            sheet.setTop10DirectionIndicator(0);

            System.out.println(sheet);

            return sheet;

        }
        //System.out.println(sheet);
        //return sheet;

//old code
//        List<Zsemarketindices> list
//                = em.createNamedQuery("Zsemarketindices.findByDateOfTrade")
//                .setParameter("dateOfTrade", date)
//                .getResultList();
        System.out.println(sheet);
        return sheet;
    }

    public List<Trade> retrieveChartData(Company company, java.util.Date dateTime) {

//        List<Trade> list
//                = em.createNamedQuery("Trade.findByCompany")
//                .setParameter("company", company)
//                .getResultList();
        List<Trade> list
                = em.createQuery(
                        "SELECT t FROM Trade t WHERE t.company = :company AND t.dateTime >= :dateTime order by t.dateTime")
                .setParameter("company", company)
                .setParameter("dateTime", dateTime)
                .getResultList();

        System.out.println("Retrieved trades: " + list);
        return list;
    }
    
   public List<Trade> retrieveChartData_2(String companyName, java.util.Date dateTime) {
System.out.println("The company name for the comparison is: "  + companyName);
//        List<Trade> list
//                = em.createNamedQuery("Trade.findByCompany")
//                .setParameter("company", company)
//                .getResultList();
        List<Trade> list
                = em.createQuery(
                        "SELECT t FROM Trade t WHERE t.company.companyName = :companyName AND t.dateTime >= :dateTime order by t.dateTime")
                .setParameter("companyName", companyName)
                .setParameter("dateTime", dateTime)
                .getResultList();

        System.out.println("Retrieved trades: " + list);
        return list;
    }

    public List<Zsemarketindices> retrieveIndicesChartData(java.util.Date startDate) {

//        List<Zsemarketindices> list
//                = em.createNamedQuery("Zsemarketindices.findAll").getResultList();
        List<Zsemarketindices> list
                = em.createQuery(
                        "SELECT z FROM Zsemarketindices z WHERE z.dateOfTrade >= :dateOfTrade order by z.dateOfTrade")
                .setParameter("dateOfTrade", startDate)
                .getResultList();

        return list;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public LocalDate setInitialDate() {

        Dataproperty property;
        LocalDate initialDate = null;

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

            initialDate = LocalDate.of(extractedYear, extractedMonthEnumElement, extractedDayOfMonth);
            System.out.println(initialDate);

            //Set the value of property value to new date if it is later than stored date
        } catch (NoResultException e) {

        }

        return initialDate;
    }

    public Company retrieveCompany(String symbol) {
        System.out.println("Symbol: " + symbol);
        Company company = (Company) em.createNamedQuery("Company.findBySymbol")
                .setParameter("symbol", symbol)
                .getSingleResult();
        System.out.println("Extracted company: " + company);
        return company;

    }

    public List<CurrencySheet> retrieveCurrencyRates() {

        List<Latestcurrency> list = em.createNamedQuery("Latestcurrency.findAll").getResultList();

        List<Currencydetail> detailList = em.createNamedQuery("Currencydetail.findAll").getResultList();

        String code = null;
        BigDecimal rate = null;
        Date datey = null;
        String currencyName = null;
        LocalDate datey_1 = null;
        List<CurrencySheet> sheetList = new ArrayList<CurrencySheet>();
        for (Latestcurrency currency : list) {
            code = currency.getCurrency();
            rate = currency.getRate();
            datey = currency.getDatey();
            for (Currencydetail detailObject : detailList) {
                if (code.equals(detailObject.getCurrency())) {
                    currencyName = detailObject.getCurrencyName();
                    break;
                }

            }

            CurrencySheet sheetObject = new CurrencySheet();
            sheetObject.setCode(code);
            sheetObject.setRate(rate);
            sheetObject.setName(currencyName);

            datey_1 = datey.toLocalDate();

            sheetObject.setDatey(datey_1);
            sheetList.add(sheetObject);

        }

        return sheetList;

    }

//  public CompanyProfile retrieveCompanyProfile(Integer profileId){
//      
//      CompanyProfile profile = null;
//      
//      try{
//  
//      profile = (CompanyProfile)em.createNamedQuery("CompanyProfile.findByRecordId")
//              .setParameter("recordId", profileId)
//              .getSingleResult();
//      }
//      catch(NoResultException e){
//          
//          e.printStackTrace();
//          
//      }
//          
//  return profile;
//  
//  }
    public List<CompanyProfile> retrieveCompanies() {

        List<CompanyProfile> list = em.createNamedQuery("CompanyProfile.findAll").getResultList();

        Collections.sort(list);

        return list;

    }

    public List<Article> retrieveNews() {

        return em.createNamedQuery("Article.findAll").getResultList();

    }

    public Article retrieveArticle(Integer articleId) {

        Article article = (Article) em.createNamedQuery("Article.findByRecordId").setParameter("recordId", articleId)
                .getSingleResult();

        return article;
    }

    public String retrieveImageCaption(String fileName) {

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

    public List<Position> retrieveProfilePositions(Integer id) {

        //Integer id = profile.getRecordId();
        return em.createQuery(
                "SELECT p FROM Position p WHERE p.profile.recordId = :profileId")
                .setParameter("profileId", id)
                .getResultList();

    }

    public List<Position> retrievePersonPositions(Person person) {

        return em.createQuery("SELECT p FROM Position p WHERE p.person = :person")
                .setParameter("person", person)
                .getResultList();
    }

    public List<Person> retrievePersons() {

        return em.createNamedQuery("Person.findAll").getResultList();

    }

    public List<Company> retrieveZseCompanies() {
        List<Company> list = em.createNamedQuery("Company.findAll").getResultList();

        //Collections.sort(list);

        return list;

    }
}
