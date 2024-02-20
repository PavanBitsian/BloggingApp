package com.nanosoft.BloggingApp;

import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@RestController
@CrossOrigin
public class DBController {

    PreparedStatement ps;
    Connection con;
    String sql;

    public String executeQuery(String sql,String where1,String where2) throws SQLException {
        PreparedStatement ps;
        ResultSet myRs;
        JSONArray categorylist = new JSONArray();
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/test?allowPublicKeyRetrieval=true",
                    "postgres", "password");
            ps = con.prepareStatement(sql);
            if(where1 != null){
                ps.setString(1, where1);
            }
            if(where2 !=null){
                ps.setString(1, where1);
                ps.setString(2, where2);
            }
            myRs = ps.executeQuery();
            while (where1==null && where2==null && myRs.next()) {
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("categoryCode",
                        myRs.getString("categoryCode")
                                .toString()
                                .trim());
                jsonobj.put("categoryName",
                        myRs.getString("categoryName")
                                .toString()
                                .trim());
                categorylist.add(jsonobj);
            }

            while (where2==null && myRs.next()) {
                JSONObject jsontaluk = new JSONObject();
                jsontaluk.put("districtcode",
                        myRs.getString("distcode")
                                .toString()
                                .trim());
                jsontaluk.put("talukcode",
                        myRs.getString("talukcode")
                                .toString()
                                .trim());
                jsontaluk.put("talukname",
                        myRs.getString("name")
                                .toString()
                                .trim());
                categorylist.add(jsontaluk);
            }
            while (where1!=null && where2!=null && myRs.next()) {
                JSONObject jsonvillage = new JSONObject();
                jsonvillage.put("districtcode",
                        myRs.getString("distcode")
                                .toString()
                                .trim());
                jsonvillage.put("talukcode",
                        myRs.getString("talukcode")
                                .toString()
                                .trim());
                jsonvillage.put(
                        "villagecode",
                        myRs.getString("villagecode")
                                .toString()
                                .trim());
                jsonvillage.put("villagename",
                        myRs.getString("name")
                                .toString()
                                .trim());
                categorylist.add(jsonvillage);
            }
            System.out.println("categorylist" + categorylist.size());
            close(con, ps, myRs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       return categorylist.toString();
    }

    public String getCategoryList(){
        JSONArray categorylist = new JSONArray();
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("categorycode","mixie");
        jsonobj.put("categoryname","MixerGrinder");
        categorylist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("categorycode","alcooker");
        jsonobj.put("categoryname","Aluminium Pressure Cookers");
        categorylist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("categorycode","stove");
        jsonobj.put("categoryname","Gas Stoves");
        categorylist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("categorycode","steelcooker");
        jsonobj.put("categoryname","Steel Pressure Cookers");
        categorylist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("categorycode","ricecooker");
        jsonobj.put("categoryname","Electric Rice Cookers");
        categorylist.add(jsonobj);
        return categorylist.toString();
    }

    @GetMapping("/categories")
    @CrossOrigin
    public String saylistDistrict(){
        boolean localDb = true;
        String res="";
        if(localDb){
            res = getCategoryList();
        }else {
            sql = "SELECT distcode, name FROM district";
            try {
                res = executeQuery(sql, null, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    @RequestMapping(value = "/brands", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public String ListTaluk(@RequestParam String categoryCod){
        String categoryCode = categoryCod;
        boolean localDb = true;
        String res="";
        if(localDb){
            res = getBrandList(categoryCode);
        }else {
            sql = "select * from taluk where distcode=?";
            try {
                res = executeQuery(sql, categoryCode, null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return (res);
    }

    public String getBrandList(String categoryCode){
        Map brands = new HashMap();
        JSONArray mixielist = new JSONArray();
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("brandCode","pres");
        jsonobj.put("brandName","Prestige");
        mixielist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("brandCode","pige");
        jsonobj.put("brandName","Pigeon");
        mixielist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("brandCode","pree");
        jsonobj.put("brandName","Preethi");
        mixielist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("brandCode","pana");
        jsonobj.put("brandName","Panasonic");
        mixielist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("brandCode","gc");
        jsonobj.put("brandName","Greenchef");
        mixielist.add(jsonobj);
        brands.put("mixie",mixielist);

        JSONArray cookerlist = new JSONArray();
        jsonobj = new JSONObject();
        jsonobj.put("pres","Prestige");
        cookerlist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("pige","Pigeon");
        cookerlist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("hawk","Hawkins");
        cookerlist.add(jsonobj);
        jsonobj = new JSONObject();
        brands.put("alcooker",cookerlist);

        JSONArray stovelist = new JSONArray();
        jsonobj = new JSONObject();
        jsonobj.put("pres","Prestige");
        stovelist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("pige","Pigeon");
        stovelist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("bf","Butterfly");
        stovelist.add(jsonobj);
        jsonobj = new JSONObject();
        brands.put("stove",stovelist);

        JSONArray steelcookerlist = new JSONArray();
        jsonobj = new JSONObject();
        jsonobj.put("pres","Prestige");
        steelcookerlist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("haw","Hawkins");
        steelcookerlist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("gc","Greenchef");
        steelcookerlist.add(jsonobj);
        jsonobj = new JSONObject();
        brands.put("steelcooker",steelcookerlist);

        JSONArray riceookerlist = new JSONArray();
        jsonobj = new JSONObject();
        jsonobj.put("pres","Prestige");
        jsonobj = new JSONObject();
        riceookerlist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("pree","Preethi");
        riceookerlist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("gc","Greenchef");
        riceookerlist.add(jsonobj);

        brands.put("ricecooker",riceookerlist);


        return brands.get(categoryCode).toString();
    }

    @RequestMapping(value = "/models", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public String Listvillage(@RequestParam String categoryCod, @RequestParam String brand)
            throws ParseException {
        String categoryCode = categoryCod;
        String brandName = brand;
        boolean localDb = true;
        String res="";
        if(localDb){
            res = getModelList(brandName);
        }else {
            sql = "select * from village where distcode=? and talukcode=?";
            try {
                res = executeQuery(sql, categoryCod, brandName);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return res;
    }

    public String getModelList(String categoryCode) {
        Map models = new HashMap();
        JSONArray presmodellist = new JSONArray();
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("modelCode","pop");
        jsonobj.put("modelName", "Popular");
        presmodellist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("modelCode","del");
        jsonobj.put("modelName", "Deluxe");
        presmodellist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("modelCode","swac");
        jsonobj.put("modelName", "Swacch");
        presmodellist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("modelCode","ha");
        jsonobj.put("modelName", "HardAnodized");
        presmodellist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("modelCode","pops");
        jsonobj.put("modelName", "Popular Swacch");
        presmodellist.add(jsonobj);

        models.put("pres", presmodellist);

        JSONArray hawmodellist = new JSONArray();
        jsonobj = new JSONObject();
        jsonobj.put("clas", "Classic");
        hawmodellist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("mm", "Miss Mary");
        hawmodellist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("cont", "Contura");
        hawmodellist.add(jsonobj);
        jsonobj = new JSONObject();
        models.put("hawk", hawmodellist);

        JSONArray deflist = new JSONArray();
        jsonobj = new JSONObject();
        jsonobj.put("def", "Default");
        deflist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("def", "Default");
        deflist.add(jsonobj);
        jsonobj = new JSONObject();
        jsonobj.put("def", "Default");
        deflist.add(jsonobj);

       return models.get(categoryCode)!=null?models.get(categoryCode).toString():deflist.toString();
    }

    private static void close(Connection myConn,
                              Statement myStmt,
                              ResultSet myRs)
    {
        try {
            if (myRs != null) {
                myRs.close();
            }
            if (myStmt != null) {
                myStmt.close();
            }
            if (myConn != null) {
                myConn.close();
            }
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}