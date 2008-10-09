import java.util.*;

public class YangDerivedTypes {

    private Hashtable<String, String> deriveds = new Hashtable<String, String>();

    public void add(String derived, String basetype){
	deriveds.put(derived, basetype);

    }

}
