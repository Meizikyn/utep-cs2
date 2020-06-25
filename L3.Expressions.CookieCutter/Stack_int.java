import java.util.EmptyStackException;
class Stack_int{
    private Node_int top; //reference to the last we added
    //constructors
    Stack_int(){top = null;}

    //operations
    public boolean isEmpty(){return top==null;}
    public int peek(){
        if(!this.isEmpty()){return top.data;}
        else{
            throw new EmptyStackException();
        }
    }
    public void push(int newElem){
        //this is equivalent to prepend, insert at the 
        //beginning of linked list
        Node_int t =new Node_int(newElem, this.top);
        top = t;
    }
    public int pop(){
        if(!this.isEmpty()){
            int tmp = this.peek();
            this.top = this.top.next;
            return tmp;
        }
        else{
          throw new EmptyStackException();
        }
    }
}
