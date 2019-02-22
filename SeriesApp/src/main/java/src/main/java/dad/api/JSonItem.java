package src.main.java.dad.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.*;
import java.util.List;

public abstract class JSonItem {


    @SuppressWarnings("unchecked")
    public JSonItem(JSONObject ob)  {
        if (ob == null) return;
        Field f = null;
        try {
            Field[] fields = this.getClass().getFields();
            for (int j=0; j<fields.length; j++) {
                f = fields[j];
                JsonData sp = f.getAnnotation(JsonData.class);
                if (sp == null) continue;
                if (ob.isNull(sp.name())) continue;                 // No inicializa
                f.set(this, decodeField(f.getGenericType(), ob, sp.name()));
            }
        } catch (Exception ex){
            throw new RuntimeException(String.format("Error JSON Clase '%s' => '%s' \n %s",
                    this.getClass().getName(), f.getName(), ex.getMessage()));
        }
    }

    /**
     * Extrae un objeto del objeto JSON con el tipo correcto
     * @param type Tipo del campo
     * @param ob
     * @param name
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private Object decodeField(Type type, JSONObject ob, String name) throws Exception {
        if (type == String.class) {
            return ob.getString(name);
        } else if (type == long.class) {
            return ob.getLong(name);
        } else if (type == int.class) {
            return ob.getInt(name);
        } else if (type == boolean.class) {
            return ob.getBoolean(name);
        } else if (type == float.class) {
            return (float)ob.getDouble(name);
        } else if (type == double.class) {
            return ob.getDouble(name);
        } else if (type == byte.class) {
            return (byte)ob.getInt(name);
        } else if (type instanceof ParameterizedType || type instanceof Class && ((Class)type).isArray()) {
            JSONArray jarr = ob.getJSONArray(name);
            boolean array = false;
            Class<?> listClass;
            Object obdata = null;
            if (type instanceof Class){
                obdata = Array.newInstance(listClass = ((Class<?>)type).getComponentType(), jarr.length());
                array = true;
            } else {
                listClass = ((Class<?>)((ParameterizedType) type).getActualTypeArguments()[0]);
                obdata = (List) ((Class<?>)((ParameterizedType) type).getRawType()).newInstance();
            }
            //Construir objetos de la lista
            for (int i = 0; i < jarr.length(); i++) {
                Object obj = decodeArrayItem(jarr, i, listClass);
                if (array){
                    Array.set(obdata, i, obj);
                }
                else {
                    ((List)obdata).add(obj);
                }
            }
            return obdata;
        } else if (JSonItem.class.isAssignableFrom((Class<?>) type)){
            Constructor<?> cons = (Constructor<?>) ((Class<?>) type).getDeclaredConstructor(JSONObject.class);
            return cons.newInstance(ob.getJSONObject(name));
        }
        throw new Exception("Tipo no controlado");
    }

    /**
     * Deecodifica internamente un elemento del array
     * @param arr Array donde irá el elemento
     * @param index índice donde habrá que colocarlo
     * @param type tipo a instanciar
     * @return Objeto instanciado
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private Object decodeArrayItem(JSONArray arr, int index,  Class<?> type) throws Exception {
        if (type == String.class) {
            return arr.getString(index);
        } else if (type == long.class) {
            return arr.getLong(index);
        } else if (type == int.class) {
            return arr.getInt(index);
        } else if (type == boolean.class) {
            return arr.getBoolean(index);
        } else if (type == float.class) {
            return (float)arr.getDouble(index);
        } else if (type == double.class) {
            return arr.getDouble(index);
        } else if (type == byte.class) {
            return (byte)arr.getInt(index);
        } else if (JSonItem.class.isAssignableFrom((Class<?>) type)){
            Constructor<?> cons = (Constructor<?>) ((Class<?>) type).getDeclaredConstructor(JSONObject.class);
            return cons.newInstance(arr.getJSONObject(index));
        }
        throw new Exception("Tipo no controlado");
    }



/*
    public JSonItem(JSONObject ob)  {
        if (ob == null) return;
        Field f = null;
        try {
            Field[] fields = this.getClass().getFields();
            for (int j=0; j<fields.length; j++) {
                f = fields[j];
                JsonData sp = f.getAnnotation(JsonData.class);
                if (sp == null || ob.isNull(sp.name())) continue;
                if (f.getType() == String.class) {
                    f.set(this, ob.isNull(sp.name()) ? null : ob.getString(sp.name()));
                } else if (f.getType() == long.class) {
                    f.set(this, ob.getLong(sp.name()));
                } else if (f.getType() == int.class) {
                    f.set(this, ob.getInt(sp.name()));
                } else if (f.getType() == boolean.class) {
                    f.set(this, ob.getBoolean(sp.name()));
                } else if (f.getType() == float.class) {
                    f.set(this, (float)ob.getDouble(sp.name()));
                } else if (f.getType() == double.class) {
                    f.set(this, ob.getDouble(sp.name()));
                } else if (f.getType() == byte.class) {
                    f.set(this, (byte)ob.getInt(sp.name()));
                } else if (JSonItem.class.isAssignableFrom(f.getType())){
                    Constructor<?> cont = (Constructor<?>) f.getType().getDeclaredConstructor(JSONObject.class);
                    f.set(this, ob.isNull(sp.name()) ? null :cont.newInstance(ob.getJSONObject(sp.name())));
                } else if (f.getType() == ArrayList.class){
                    JSONArray jarr = ob.getJSONArray(sp.name());
                    List ls = (ArrayList) f.getType().newInstance();
                    //Obtener el tipo y constructor
                    ParameterizedType pt = (ParameterizedType) f.getGenericType();
                    Class<?> listClass = (Class<?>) pt.getActualTypeArguments()[0];
                    if (listClass == String.class){
                        for (int i = 0; i < jarr.length(); i++) {
                            ls.add(jarr.getString(i));
                        }
                    }
                    else {
                        Constructor<?> cont = (Constructor<?>) listClass.getDeclaredConstructor(JSONObject.class);
                        //Construir objetos de la lista
                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject jso = jarr.getJSONObject(i);
                            Object obj = cont.newInstance(jso);
                            ls.add(obj);
                        }
                    }
                    f.set(this, ls);
                }
            }
        }catch (InstantiationException exx) {

        }catch (NoSuchMethodException exx) {

        } catch (InvocationTargetException exx){

        } catch (IllegalAccessException exx){

        } catch (JSONException ex){
            System.err.println("ERR: ******* " + this.getClass().getName() + "\n");
            ex.printStackTrace();
            //System.out.println(ob.toString());
            throw new RuntimeException(String.format("Error JSON Clase '%s' => '%s' \n %s \n %s",
                    this.getClass().getName(), f.getName(), ex.getMessage(), ob.toString()));
        }
    }
*/

}
