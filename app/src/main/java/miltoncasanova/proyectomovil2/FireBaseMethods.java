package miltoncasanova.proyectomovil2;


import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class FireBaseMethods {

    private Firebase root;
    private String url;

    public FireBaseMethods(String url) {
        this.url = url;
        this.root = new Firebase(this.url);
    }

    public Firebase getRoot(){
        return this.root;
    }

    public void editValues(String ref, String key, String[] params, String[] values){

        Firebase objRef;
        if ("".equals(ref)){
            objRef = root.child(key);
        }else{
            objRef = root.child(ref).child(key);
        }
        Map<String, Object> obj = new HashMap<String, Object>();
        for (int i = 0; i < params.length; i++) {
            obj.put(params[i],values[i]);
        }
        objRef.updateChildren(obj);
    }

    public void addValue(String ref, Object obj){
        Firebase objRef;
        if ("".equals(ref)){
            objRef = root;
        }else{
            objRef = root.child(ref);
        }
        objRef.push().setValue(obj);
    }


}
