/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package costcalculator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author Foyin Ogbara
 */
public class CostCalculator {
    
    public static void main(String[] args) {  
        costOfTrip("QEW", "Dundas Street" );     
    }
    
    public static void costOfTrip(String d1, String d2){
        float distanceBetweenStops = 0;
        
        try (FileReader interchanges = new FileReader("src/costcalculator/interchanges.json")){
            JsonObject obj = (JsonObject) JsonParser.parseReader(interchanges);
            int stop = 0;
            JsonObject locations = (JsonObject) obj.get("locations");
   
            for(Object key : locations.keySet())
            {
                Object keyvalue = locations.get(key.toString());
                
                JsonObject index = (JsonObject) locations.get(key.toString());
                String name = index.get("name").getAsString();
                
                if(name.equals(d1) || name.equals(d2)){
                    stop++;
                     
                }
                
                if(stop == 1){
                    JsonArray routes = (JsonArray) index.get("routes");
                    JsonObject firstRoute = (JsonObject) routes.get(0);
                    float firstRouteDistance = firstRoute.get("distance").getAsFloat();
                    distanceBetweenStops += firstRouteDistance;
                }

                if(stop == 2){    
                    break;
                }
                   
            }
            
            if(stop == 0 || stop == 1){    
                System.out.println("Cant find route, possible typo");         
            }
            else{
                System.out.println("Distance: " + Math.round(distanceBetweenStops  *1000d)/1000d + "km");
                System.out.println("cost: $" + Math.round((distanceBetweenStops * 0.25) *100d)/100d);
            }
            //System.out.println("cost: $" + distanceBetweenStops * 0.25);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
    

    



