package com.inn.store.utils;

import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.inn.store.exception.UserExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class StoreUtils  {
    // generate methode which can be used  in any service central classes or in any classes

    private  StoreUtils(){

    }
    public static ResponseEntity<String> getResponseEntity(String responseMessage , HttpStatus httpStatus){

        return new ResponseEntity<String>("{\"message\": \""+responseMessage+"\"}", httpStatus);
    }

    // hadi kat 3tik id unique  b time
    public static String getUuid(){
        Date date = new Date();
        long time = date.getTime();
        return "Bill-"+time;
    }

    public static JSONArray getJsonArrayFromString(String data) throws JSONException{
        return new JSONArray(data);
    }

    //La désérialisation de données à partir de leur représentation JSON se fait en utilisant une des surcharges de la méthode fromJson() de la classe Gson
    public static Map<String,Object> getMapFromJson(String data){
        if(!Strings.isNullOrEmpty(data)){
            return new Gson().fromJson(data,new TypeToken<Map<String,Object>>(){}.getType());
            //final Boolean booleen = gson.fromJson("false", Boolean.class);
            //    System.out.println(booleen);
            // Result : false
        }
        return new HashMap<>();
    }

    public static Boolean isFileExiste(String path){
            log.info("Inside isFileExiste {}",path);
        try{
            File file = new File(path);
            return (file!=null && file.exists()) ? Boolean.TRUE : Boolean.FALSE;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
