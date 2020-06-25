class Node_char{
    //attributes
    char data;
    Node_char next;
    
    Node_char(char d, Node_char n){
        data = d;
        next = n;
    }
    
    Node_char(char d){
        data = d;
        next = null;
    }
    
    Node_char(){
        data = '0';
        next = null;
    }
}