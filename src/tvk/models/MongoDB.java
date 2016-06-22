package tvk.models;

import com.mongodb.*;
import java.net.UnknownHostException;
import java.util.Set;

public class MongoDB {
    
    public static void main(String[] args){
        try {
            // conexión...
            MongoClient mongo = new MongoClient();
            // seleccionar base de datos..
            DB db = mongo.getDB("miblog");
            // lista de colecciones...
            Set<String> colls = db.getCollectionNames();
            for(String ss : colls){
                System.out.println(ss);
            }
            // seleccionar colección...
            DBCollection col = db.getCollection("users");
            // query... primer documento de la colección...
            DBObject doc = col.findOne();
            System.out.println(doc);
            // query... más documentos con un cursor...
            DBCursor cur = col.find();
            while(cur.hasNext()){
                System.out.println(cur.next());
                System.out.println(cur.next().get("_id"));
            }
            // Insertar documentos en una colección...
            /* BasicDBObject bdoc = new BasicDBObject("nombre", "Johan Pineda").
                    append("nombreusuario", "johan9789").
                    append("password", "12345678").
                    append("nacimiento", "1994-03-09").
                    append("direccion", new BasicDBObject("ciudad", "Lima").
                            append("provincia", "Lima"));
            col.insert(bdoc); */
            // contar documentos en la colección...
            System.out.println("count: " + col.count());
            // Obtener dato de un documento...
            System.out.println(doc.get("nombre"));
            System.out.println(doc.get("direccion"));
        } catch(UnknownHostException e){
            System.out.println(e);
        }
    }
    
}