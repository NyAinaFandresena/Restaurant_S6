/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitement;

import java.util.HashMap;

/**
 *
 * @author asus 
 */
public class ModelView {

    private HashMap map ;
    private String url ;
    private String include;

    public ModelView(HashMap map, String url, String include) {
        this.map = map;
        this.url = url;
        this.include = include;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public HashMap getMap() {
        return map;
    }

    public void setMap(HashMap map) {
        this.map = map;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
