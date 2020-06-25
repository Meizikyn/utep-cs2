class Node_int{
    //attributes
    int data;
    Node_int next;
    
    Node_int(int d, Node_int n){
        data = d;
        next = n;
    }
    
    Node_int(int d){
        data = d;
        next = null;
    }
    
    Node_int(){
        data = 0;
        next = null;
    }
}