package tvk.models;

import com.mongodb.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AccessColPrueba {
    private MongoClient mongo;
    private DB database;
    private DBCollection collection;
    private DBCursor cursor;
    
    public AccessColPrueba(){
        try {
            mongo = new MongoClient();
            database = mongo.getDB("crud_prueba");
            collection = database.getCollection("col_prueba");
        } catch(UnknownHostException e){
            JOptionPane.showMessageDialog(null, "Error de servidor desconocido: " + e);
            System.exit(0);
        }
    }        
    
    public Object[][] verColeccion(){        
        cursor = collection.find();        
        int row = cursor.count();
        Object[][] data = new Object[row][6];
        int x = 0;
        while(cursor.hasNext()){
            DBObject dbo = cursor.next();
            data[x][0] = dbo.get("_id");
            data[x][1] = dbo.get("nombre");
            data[x][2] = dbo.get("app");
            data[x][3] = dbo.get("apm");
            data[x][4] = dbo.get("sexo");
            data[x][5] = dbo.get("cumpleaños");
            x++;
        }    
        return data;
    }
    
    public List<ColPrueba> verColleccion(){
        List<ColPrueba> col_prueba = new ArrayList<>();
        cursor = collection.find();
        while(cursor.hasNext()){
            DBObject dbo = cursor.next();
            col_prueba.add(new ColPrueba(dbo.get("_id"), dbo.get("nombre"), dbo.get("app"), dbo.get("apm"), dbo.get("sexo"), dbo.get("cumpleaños")));
        }
        return col_prueba;
    }
    
    public void insertarDocumento(String nombre, String app, String apm, String sexo, String cumpleaños){
        BasicDBObject insert = new BasicDBObject("nombre", nombre).
            append("app", app).
            append("apm", apm).
            append("sexo", sexo).
            append("cumpleaños", cumpleaños);
        collection.insert(insert);
    }
    
    public void eliminarDocumento(String _id, String nombre){
        BasicDBObject doc = new BasicDBObject();
        doc.put("_id", _id);
        doc.put("nombre", nombre);
        collection.remove(doc);
    }
    
    public void actualizarDocumento(String _id, String nombre, String app, String apm, String sexo, String cumpleaños){
        BasicDBObject doc = new BasicDBObject();
        doc.put("_id", _id);        
        BasicDBObject upd = new BasicDBObject();
        upd.put("nombre", nombre);
        upd.put("app", app);
        upd.put("apm", apm);
        upd.put("sexo", sexo);
        upd.put("cumpleaños", cumpleaños);
        collection.update(doc, upd);        
    }
    
    public static void print_r(Object[][] array){
        String arr = "Array\n";
        arr += "(\n";
        for(int i=0;i<array.length;i++){
            arr += "    [" + i + "] => Array\n";
            arr += "        (\n"; 
            for(int j=0;j<array[i].length;j++){
                arr += "            [" + j + "] => " + array[i][j] + "\n";
            }
            arr += "        )\n";
        }
        arr += ")";
        System.out.println(arr);
    }
    
}