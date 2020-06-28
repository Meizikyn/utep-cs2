class Node_string{
    //attributes
    String data;
    Node_string next;

    Node_string(String s, Node_string n){
        data = s;
        next = n;
    }

    Node_string(String s){
        data = s;
        next = null;
    }

    Node_string(){
        data = "0";
        next = null;
    }
}
