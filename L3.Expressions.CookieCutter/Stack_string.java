import java.util.EmptyStackException;
class Stack_string{
    private Node_string top; //reference to the last we added
    //constructors
    Stack_string(){top = null;}

    //operations
    public boolean isEmpty(){return top==null;}

    public String peek(){
        if(!this.isEmpty()){return top.data;}
        else{
            throw new EmptyStackException();
        }
    }
    public void push(String newElem){
        //this is equivalent to prepend, insert at the 
        //beginning of linked list
        Node_string t =new Node_string(newElem, this.top);
        top = t;
    }
    public String pop(){
        if(!this.isEmpty()){
            String tmp = this.peek();
            this.top = this.top.next;
            return tmp;
        }
        else{
          throw new EmptyStackException();
        }
    }
}
