package general;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Myclass {
    
    public void insert(Connection con) throws Exception{
        Field[] listfield = this.getClass().getDeclaredFields();
        String req = "insert into "+this.getClass().getSimpleName()+" values (";
        
        for(int i=0; i<listfield.length; i++){
            Object value = listfield[i].get(this);
            if(value == null || i==0){
                if(listfield[i].getName().startsWith("id")){
                    if(listfield[i].getType().getSimpleName().equalsIgnoreCase("String")){
                        req +="'"+this.getClass().getSimpleName().substring(0,3).toUpperCase()+"' || nextval('seq"+this.getClass().getSimpleName()+"')";
                    } else {
                        req += "nextval('seq"+this.getClass().getSimpleName()+"')";
                    }
                } else {
                    req += "null";
                }
            }
            if(value != null && i>0){
                if(value instanceof String) req += "'"+String.valueOf(value)+"'";
                if(value instanceof Date){
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String pp = dateFormat.format((Date)value);
                    req += "TO_TIMESTAMP('"+pp+"','yyyy-mm-dd hh24:mi:ss')";  
               }
               if(value instanceof Number) req += String.valueOf(value);
            }
            if(i<listfield.length-1) req +=", ";
        }
        req += ")";
        
        try (java.sql.Statement stmt = con.createStatement()) {
            stmt.executeQuery(req);
            stmt.close();
        }
    }
    public void update(Connection con) throws Exception{
        Field[] listfield = this.getClass().getDeclaredFields();
        String req = "update "+this.getClass().getSimpleName()+" set ";
        Field id = null;
        for(int i=0; i<listfield.length; i++){
            Object value = listfield[i].get(this);
            if(value!=null){
                if(value instanceof String && !listfield[i].getName().equalsIgnoreCase("id_"+this.getClass().getSimpleName().toLowerCase())) req += listfield[i].getName()+" = '"+String.valueOf(value)+"'";
                if(value instanceof Date && !listfield[i].getName().equalsIgnoreCase("id_"+this.getClass().getSimpleName().toLowerCase())){
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String pp=dateFormat.format((Date)value);
                    req += listfield[i].getName()+" = "+"TO_TIMESTAMP('"+pp+"','yyyy-mm-dd hh24:mi:ss')";   
                }
                if(value instanceof Number && !listfield[i].getName().startsWith("id_")){
                    if(listfield[i].getType().getSimpleName().equalsIgnoreCase("Int")){
                        if((int)value != -1) req += listfield[i].getName()+" = "+String.valueOf((int)value);
                        if((int)value == -1) continue;
                    }
                    if(listfield[i].getType().getSimpleName().equalsIgnoreCase("Double")){
                        if((double)value != -1) req +=listfield[i].getName()+" = "+String.valueOf((double)value);
                        if((double)value == -1) continue;
                    }
                    if(listfield[i].getType().getSimpleName().equalsIgnoreCase("Float")){
                        if((float)value != -1) req +=listfield[i].getName()+" = "+String.valueOf((float)value);
                        if((float)value == -1) continue;
                      }
                }
                if(value != null && (!String.valueOf(value).equalsIgnoreCase("-1")) && i<listfield.length-1 && ! listfield[i].getName().equalsIgnoreCase("id_"+this.getClass().getSimpleName().toLowerCase())) req += ", ";
                if(listfield[i].getName().equalsIgnoreCase("id"+this.getClass().getSimpleName().toLowerCase())) id = listfield[i];
            }
        }
        req += " where 1<2 ";
        if(id != null){
            if(id.get(this) instanceof String) req += " and "+id.getName()+" = '"+id.get(this)+"'";
            else req += " and "+id.getName()+" = "+String.valueOf(id.get(this));
        }
        java.sql.Statement stmt = con.createStatement();
        stmt.executeQuery(req);
        stmt.close();
    }
    
    public ArrayList find(String req, Connection con) throws Exception {
        java.sql.Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery(req);

        String table = this.getClass().getName();
        Class encours = null;
        try {
            encours = Class.forName("classes."+table);
        } catch (ClassNotFoundException e) {
            encours = Class.forName(table);
        }
        
        Field [] listarg = encours.getDeclaredFields();
        Class [] listclassarg = new Class[listarg.length];
        for(int i=0; i<listclassarg.length; i++){
            listclassarg[i] = listarg[i].getType();
        }
        
        Constructor construct = encours.getConstructor(listclassarg);
        Object[] arg = new Object[listarg.length];        
        return this.getResultFromSet(res, listarg, listclassarg, construct);
    }
        
    public ArrayList find(Myclass cible, Connection con) throws Exception {
        Class c = cible.getClass();
        Field [] listfield = c.getDeclaredFields();
        String req = "select * from "+c.getSimpleName()+" where 1<2 ";
        for(int i=0; i<listfield.length; i++){
            Object value = listfield[i].get(cible);
            if(value != null){
                if(value instanceof String){
                    req +=" and ";
                    req +=listfield[i].getName()+"='"+String.valueOf(value)+"'";
                }
                if(value instanceof Date){
                   req +=" and ";
                   Date temp=(Date)value;
                   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   String pp=dateFormat.format(temp);
                   req += listfield[i].getName()+" = "+"TO_TIMESTAMP('"+pp+"','yyyy-mm-dd hh24:mi:ss')";  
                }
                if(value instanceof Number ){
                    if(listfield[i].getType().getSimpleName().equalsIgnoreCase("Int")){
                        int v = (int)value;
                        if(v != -1) req += " and "+listfield[i].getName()+" = "+v;
                    }
                    if(listfield[i].getType().getSimpleName().equalsIgnoreCase("Double")){
                        double v = (double)value;
                        if(v != -1) req += " and "+listfield[i].getName()+" = "+v;
                    }
                    if(listfield[i].getType().getSimpleName().equalsIgnoreCase("Float")){
                        float v=(float)value;
                        if(v!=-1) req +=" and "+listfield[i].getName()+" = "+v;
                    }
                }
            }
        }
        
        Field[] listarg = c.getDeclaredFields();
        Class[] listclassarg = new Class[listarg.length];
        for(int i=0;i<listclassarg.length;i++){
            listclassarg[i] = listarg[i].getType();
        }
        Constructor construct = c.getConstructor(listclassarg);
        java.sql.Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery(req);
        
        return this.getResultFromSet(res, listarg, listclassarg, construct);
    }
    
    public ArrayList getResultFromSet(ResultSet res, Field[] listarg, Class[] listclassarg, Constructor construct) throws Exception{
        ArrayList vectretour = new ArrayList();
        while(res.next()){
            Object [] arg = new Object[listarg.length];
            for(int ii=0;ii<arg.length;ii++) {
                if(listclassarg[ii].getSimpleName().equalsIgnoreCase("String")) arg[ii]=res.getString(ii+1);
                if(listclassarg[ii].getSimpleName().equalsIgnoreCase("Date")) arg[ii]=res.getDate(ii+1);
                else if(listclassarg[ii].getSimpleName().equalsIgnoreCase("Int")) arg[ii]=res.getInt(ii+1);
                else if(listclassarg[ii].getSimpleName().equalsIgnoreCase("Double")) arg[ii]=res.getDouble(ii+1);
                else if(listclassarg[ii].getSimpleName().equalsIgnoreCase("Float")) arg[ii]=res.getFloat(ii+1);
            }
            Object ret = construct.newInstance(arg);
            vectretour.add(ret);
        }        
        return vectretour ;
    }
    
    public void delete(Connection con)throws Exception{
        Class c = this.getClass();
        Field [] listfield = c.getDeclaredFields();
        String req = "delete from "+c.getSimpleName()+" where 1<2 ";
        
        Object value = listfield[0].get(this);
        if(value instanceof String) req +=" and "+listfield[0].getName()+" = '"+value+"'";
        else req +=" and "+listfield[0].getName()+" = "+String.valueOf(value);
        
        java.sql.Statement stmt = con.createStatement();
        stmt.executeQuery(req);
        stmt.close();
    }
    
    public Object getResult(String req, Connection con, int arg) throws Exception{
        Object result = null ;
        java.sql.Statement stmt = con.createStatement();
        
        if (arg == -1) {
            return stmt.executeUpdate(req);
        }
        else {
            ResultSet res = stmt.executeQuery(req);
            res.next();
            if (arg == 0){
                result = res.getString(1);
                if (String.valueOf(result).equalsIgnoreCase("NULL")) {
                    result = "0" ;
                }
            } else {
                result = res.getDate(1);
            }
        }
        stmt.close();
        return result;        
    }
    
    public String getsequence(Connection con)throws Exception{
        String req = "select nextval(seq"+this.getClass().getSimpleName()+")";   
        java.sql.Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery(req);
        res.next();
        String result = res.getString(1);
        stmt.close();
        return result;
        
    }
   
}