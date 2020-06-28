class Node_double{
    //attributes
    double data;
    Node_double next;

    Node_double(double d, Node_double n){
        data = d;
        next = n;
    }

    Node_double(double d){
        data = d;
        next = null;
    }

    Node_double(){
        data = 0;
        next = null;
    }
}
