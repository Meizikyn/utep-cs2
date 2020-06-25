import java.util.EmptyStackException;
class Stack_char{
    private Node_char top; //reference to the last we added
    //constructors
    Stack_char(){top = null;}

    //operations
    public boolean isEmpty(){return top==null;}

    public char peek(){
        if(!this.isEmpty()){return top.data;}
        else{
            throw new EmptyStackException();
        }
    }
    public void push(char newElem){
        //this is equivalent to prepend, insert at the 
        //beginning of linked list
        Node_char t =new Node_char(newElem, this.top);
        top = t;
    }
    public char pop(){
        if(!this.isEmpty()){
            char tmp = this.peek();
            this.top = this.top.next;
            return tmp;
        }
        else{
          throw new EmptyStackException();
        }
    }
}
