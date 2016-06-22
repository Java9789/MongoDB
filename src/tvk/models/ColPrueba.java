package tvk.models;

public class ColPrueba {
    private Object _id;
    private Object nombre;
    private Object app;
    private Object apm;
    private Object sexo;
    private Object cumpleaños;

    public ColPrueba(){}

    public ColPrueba(Object _id){
        this._id = _id;
    }

    public ColPrueba(Object _id, Object nombre, Object app, Object apm, Object sexo, Object cumpleaños){
        this._id = _id;
        this.nombre = nombre;
        this.app = app;
        this.apm = apm;
        this.sexo = sexo;
        this.cumpleaños = cumpleaños;
    }

    public Object getId(){
        return _id;
    }

    public void setId(Object _id){
        this._id = _id;
    }

    public Object getNombre(){
        return nombre;
    }

    public void setNombre(Object nombre){
        this.nombre = nombre;
    }

    public Object getApp(){
        return app;
    }

    public void setApp(Object app){
        this.app = app;
    }

    public Object getApm(){
        return apm;
    }

    public void setApm(Object apm){
        this.apm = apm;
    }

    public Object getSexo(){
        return sexo;
    }

    public void setSexo(Object sexo){
        this.sexo = sexo;
    }

    public Object getCumpleaños(){
        return cumpleaños;
    }

    public void setCumpleaños(Object cumpleaños){
        this.cumpleaños = cumpleaños;
    }
            
}